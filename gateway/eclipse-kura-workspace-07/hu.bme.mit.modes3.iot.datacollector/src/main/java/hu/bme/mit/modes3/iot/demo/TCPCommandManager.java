package hu.bme.mit.modes3.iot.demo;

/*
 * Ez az objektum felel a parancsok tov�bb�t�s��rt
 * A parancsok kiad�sa �gy zajlik, hogy miut�n TCP-vel kapcsol�dik az aktu�tor (kliens)
 * ut�na a mester elk�ld egy json-t ami tartalmazza kulcs-�rt�k p�rokk�nt, hogy kinek mit kell csin�lni
 */


import java.net.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.kura.cloud.CloudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
//Ha ezeket bennthagyod akkor egy�ltal�n nem indul el a bundle a Kura-n
/*
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
*/
public class TCPCommandManager extends Thread /*implements MqttCallback*/{
	

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	
	/*
	 * TCP variables
	 */

    public  int serverPort=5503;
    public  int masterPort=5505;    //ez nem fontos
    protected ServerSocket serverSocket;
    protected ServerSocket masterSocket;
    //enumokban t�rolom, hogy az egyes aktu�toroknak milyen �llapotban kell lenni�k
    LampState lampState=LampState.STOP;// a vas�ti l�mpa alap�rtelmezettk�nt piros
    LCDState lcdState=LCDState.OKAY;
    LightState lightState=LightState.OFF;//a k�zvil�g�t�s alap�rtelmezettk�nt ki van kapcsolva
    //ScheduledExecutorService m_worker;
    
    /*
     * MQTT variables
     * most feleslegesek
     */
    

    private final String topic        = "LampCommand";
    private final String content      = "";
    private final int qos             = 2;
    private final String broker       = "tcp://localhost:1883";
    private final String clientId     = "CommandManager";
    //MemoryPersistence persistence = new MemoryPersistence();
    
    
    private void notifylog() {
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    }
    
    public void setLampState(LampState ls) {
    	this.lampState=ls;
    }
    
    public TCPCommandManager(/*ScheduledExecutorService worker*/) {
    	
    	/*m_worker=worker;*/
    	//szerver inicializ�l�sa
    	logger.info("creating webcontrol server...");
    	initServer();
    	logger.info("creating webcontrol server...Done");
    	//initMQTT();
    	
    	logger.info("TCP Command Manager has successfully initalised :)");
    	
    }
    
    
    private void initServer() {

    	logger.info("initalising TCP Data Manager...");
    	try {
    		serverSocket=new ServerSocket(serverPort);
    		//masterSocket=new ServerSocket(masterPort);
    		logger.info("Server socket is created. :");
    		logger.info("Port is:"+Integer.toString(serverSocket.getLocalPort()));
    	}catch(IOException e) {
    		notifylog();
    		logger.info(e.toString());
    	}
    	
    	
    }
    
   //port be�ll�t�sa �s szerver �jrainicializ�l�sa
    public void setPort(int port) {
    	logger.info("set server port...");
    	serverPort=port;
    	initServer();
    	logger.info("set server port...Done");
    }
    
    
    public void run() {

        //Thread.currentThread().setName(getClass().getSimpleName());
    	//figyelik, hogy j�n e �j kliens �s hozz�kapcsol egy klienskezel�t ami �sszze�ll�tja neki a parancs json-t �s elk�ldi neki	
    	while(true) {
    		try {
    			Socket connection=serverSocket.accept();
    			//Socket masterConnection=masterSocket.accept();
    			ClientHandler client=new ClientHandler(connection);
    			//MasterHandler master=new MasterHandler(masterConnection);
    			
    			//m_worker.schedule(client, 1, TimeUnit.MILLISECONDS);
    			client.start();
    			//master.start();
    		}catch(IOException e) {
    			notifylog();
    			logger.info(e.toString());
    		}
    	}
    	
    }
    
    
    
    
    
    private class ClientHandler extends Thread{
		Socket m_connection;

		
		
		public ClientHandler(Socket connection) {
			
			logger.info("new client has connected...");
			logger.info(connection.getInetAddress().toString());
			
			m_connection=connection;
			
		}
		
		
		public void run() {
            //Thread.currentThread().setName(getClass().getSimpleName());
			try {
				OutputStream os=m_connection.getOutputStream();
				
				JsonObject commands=new JsonObject();
				//a gateway azonos�t�ja a kliensnek
				commands.addProperty("GatewayID", "5521");
				
				//l�mpa �llapot�t�l f�gg�en �rja be a parancsot
				switch(lampState) {
				case START:
					commands.addProperty("LampState", "START");
				break;
				case STOP:
					commands.addProperty("LampState", "STOP");
				break;
				case WARNING:
					commands.addProperty("LampState", "WARNING");
				break;
				default:
					commands.addProperty("LampState", "STOP");
				break;
				}
				
				switch(lcdState) {
				case COLD:
					commands.addProperty("LCDState", "COLD");
				break;
				case HOT:
					commands.addProperty("LCDState", "HOT");
				break;
				case OKAY:
					commands.addProperty("LCDState", "OKAY");
				break;
				default:
					commands.addProperty("LCDState", "OKAY");
				break;
				
				}
				
				switch(lightState) {
				case ON:
					commands.addProperty("LightState", "ON");
				break;
				case OFF:
					commands.addProperty("LightState", "OFF");
				break;
		
				default:
					commands.addProperty("LightState", "OFF");
				break;
				
				}
				
				//elk�di a kliensnek az �ssze�ll�tott parancsot a kliensnek �s z�rja a kapcsolatot
				os.write(commands.toString().getBytes());
				os.write('$');
				
				m_connection.close();
				
			}catch(IOException e) {
				logger.info(e.toString());
			}
		}
    }
		//most m�g nincs haszn�lva
		private class MasterHandler extends Thread{
			Socket m_connection;
			StringBuffer inBuffer=new StringBuffer("");

			public static final char startSign='{';
			public static final char stopSign='}';
			int jsonLimit=1024;
			
			
			public MasterHandler(Socket connection) {
				
				logger.info("new client has connected...");
				logger.info(connection.getInetAddress().toString());
				
				m_connection=connection;
				
			}
			
			
			public void run() {
	            Thread.currentThread().setName(getClass().getSimpleName());
				try {
					//OutputStream os=m_connection.getOutputStream();
					//m_connection.close();
					InputStream is=m_connection.getInputStream();
					int counter=0;
					while(true) {
						while((char)(is.read())!=startSign);
						logger.info("incoming json is caught-----------------------------");
						inBuffer.append('{');
						char in;
						do {
							in=(char) is.read();
							inBuffer.append(in);
						}while(in!=stopSign && counter<jsonLimit);
						logger.info("reading incoming json is successful-----------------");
						logger.info(inBuffer.toString());
						
						m_connection.close();

						Gson gson=new Gson();
						
						Command command=gson.fromJson(inBuffer.toString(), Command.class);

						if(command.Command.compareTo("ON")==0) {
							lightState=LightState.ON;
						}
						
						if(command.Command.compareTo("OFF")==0) {
							lightState=LightState.OFF;
						}
						
						
					}
				}catch(IOException e) {
					logger.info(e.toString());
				}
			}
		
		}
	





}
