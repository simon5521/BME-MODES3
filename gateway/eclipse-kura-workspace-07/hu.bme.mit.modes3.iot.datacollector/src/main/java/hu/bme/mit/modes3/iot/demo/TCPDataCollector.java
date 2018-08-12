package hu.bme.mit.modes3.iot.demo;


/*
 * Ez az oszt�ly felel�s az adatok gy�jt�s�nek �s feldolgoz�s�nak megval�s�t�s��rt
 */

import java.net.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.kura.KuraException;
import org.eclipse.kura.cloud.CloudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import java.io.*;


public class TCPDataCollector extends Thread {
	
	//ez az objektummal lehet loggolni
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    //TCP szerver portja
    public  int serverPort=5502;
    //szerver socket
    protected ServerSocket serverSocket;
    //Mekkora lehet a buffer maxim�lis m�rete
    private static final int jsonLimit=1024;
    //private ScheduledExecutorService m_worker;
    
    
    //Adatok Kapua-ra val� tov�bbk�ld�s��rt felel
    private DataPublisher dataPublisher;
    //Hib�k, anom�li�k, vesz�lyek Kapua-ra t�rt�n� tov�bbk�ld�s��rt felel
    private ErrorPublisher errorPublisher;
    
    //nagy jelz�s a log-ban
    private void notifylog() {
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    }
    
    
    
    public TCPDataCollector(CloudService cloudService, ScheduledExecutorService worker) {
    	//m_worker=worker;
    	//inicializ�lom a tov�bbk�ld� objektumokat
    	try {
    		logger.info("creating Publishers...");
    		dataPublisher=new DataPublisher(cloudService);
    		errorPublisher=new ErrorPublisher(cloudService);
    		logger.info("creating Publishers...Done");
    	}catch(Exception e) {
    		notifylog();
    		logger.info(e.toString());
    	}
    	
    	//l�trehozom az adatgy�jt� TCP socket-et
    	logger.info("data collector server...");
    	initServer();
    	logger.info("data collector server...Done");
    	

    	
    }
    
    
    private void initServer() {

    	logger.info("initalising TCP Data Collector...");
    	//szerver socket inicializ�l�sa
    	try {
    		serverSocket=new ServerSocket(serverPort);
    		logger.info("Server socket is created. :");
    		logger.info("Port is:"+Integer.toString(serverSocket.getLocalPort()));
    	}catch(IOException e) {
    		notifylog();
    		logger.info(e.toString());
    	}
    	
    	
    }
    
    //port be�ll�t�sa �s szerver �rainicializ�l�sa
    public void setPort(int port) {
    	logger.info("set server port...");
    	serverPort=port;
    	initServer();
    	logger.info("set server port...Done");
    }
    
    
    public void run() {
        //Thread.currentThread().setName(getClass().getSimpleName());
    	//folyamatosan figyelem, hogy j�n-e �j kliens �s ha j�n akkor hozz�kapcsolok egy klienskezel� objektumot	
    	while(true) {
    		try {
    			Socket connection=serverSocket.accept();
    			ClientHandler client=new ClientHandler(connection);
    			//m_worker.schedule(client, 0, TimeUnit.MILLISECONDS);
    			client.start();
    		}catch(IOException e) {
    			notifylog();
    			logger.info(e.toString());
    		}
    	}
    	
    }
    
    
    
    
    //klienskezel� objektum
	private class ClientHandler extends Thread{
		Socket m_connection;
		StringBuffer inBuffer=new StringBuffer("");
		
		//ezzel kezd�dik az �zenet
		public static final char startSign='{';
		//ezzel v�gz�dik az �zenet
		public static final char stopSign='}';
		
		
		public ClientHandler(Socket connection) {
			
			logger.info("new client has connected...");
			//kliens c�m�nek ki�r�sa
			logger.info(connection.getInetAddress().toString());
			//kapcsolat be�r�sa
			m_connection=connection;
			
		}
		
		
		public void run() {
            Thread.currentThread().setName(getClass().getSimpleName());
			try {
				//OutputStream os=m_connection.getOutputStream();
				//m_connection.close();
				//bej�v� adatfolyam
				InputStream is=m_connection.getInputStream();
				int counter=0;
				while(true) {
					//v�rok am�g nem j�n a kezd� karakter
					while((char)(is.read())!=startSign);
					logger.info("incoming json is caught-----------------------------");
					inBuffer.append('{');
					char in;
					do {
						//az adatokat bufferbe teszem
						in=(char) is.read();
						inBuffer.append(in);
						//ezt addig csin�lom ameddig nem j�n a json v�g�t jelz� karakter
					}while(in!=stopSign && counter<jsonLimit);
					logger.info("reading incoming json is successful-----------------");
					logger.info(inBuffer.toString());
					
					m_connection.close();
					
					//elk�ld�n a bej�tt adatot
					publishData(inBuffer.toString());
					
				}
			}catch(IOException e) {
				logger.info(e.toString());
			}
		}
		
		
		private void publishData(String inData) {
			//json olvas�shoz ezt haszn�lom
			Gson gson=new Gson();
			
			try {
				
				//�soszt�lyk�nt olvasom ki, hogy megtudjam a t�pus�t
				Data indat=gson.fromJson(inData, Data.class);
				
				//t�pust�l f�gg�en olvasom be a megfelel� objektumba �s k�ld�m tov�bb a Kapua-ra a publisher objektumokkal
				if(indat.Type.compareTo("WatchmanData")==0) {
					logger.info("type has recognised");
					WatchmanData data=gson.fromJson(inData, WatchmanData.class);
					dataPublisher.publishData(data);
					errorPublisher.checkData(data);
				}
				
				if(indat.Type.compareTo("SolarData")==0) {
					logger.info("type has recognised");
					SolarData data=gson.fromJson(inData, SolarData.class);
					dataPublisher.publishData(data);
					errorPublisher.checkData(data);
				}

				if(indat.Type.compareTo("AccData")==0) {
					logger.info("type has recognised");
					AccData data=gson.fromJson(inData,AccData.class);
					dataPublisher.publishData(data);
					errorPublisher.checkData(data);
				}
				
				if(indat.Type.compareTo("CameraData")==0) {
					logger.info("type has recognised");
					CameraData data=gson.fromJson(inData,CameraData.class);
					dataPublisher.publishData(data);
					errorPublisher.checkData(data);
				}
				
				if(indat.Type.compareTo("DHTData")==0) {
					logger.info("type has recognised");
					DHTData data=gson.fromJson(inData,DHTData.class);
					dataPublisher.publishData(data);
					errorPublisher.checkData(data);
				}
				
				if(indat.Type.compareTo("OnboardData")==0) {
					logger.info("type has recognised");
					OnboardData data=gson.fromJson(inData, OnboardData.class);
					dataPublisher.publishData(data);
				}
				
			} catch (Exception e) {
				logger.info(e.toString());
				//BufferedWriter file;
				/*try {
					file = new BufferedWriter(new FileWriter("/data/data.txt"));
					file.write(inData);
					file.newLine();
				} catch (IOException e1) {
					logger.info(e.toString());
				}*/
			}
			
		}

		/*public void onConnection() {
			try {
				BufferedReader bfr=new BufferedReader(new FileReader("/data/data.txt"));
				
				publishData(bfr.readLine());
				
			}catch(Exception e) {
				logger.info(e.toString());
				
			}
		}*/
		
		
	}
	
}
