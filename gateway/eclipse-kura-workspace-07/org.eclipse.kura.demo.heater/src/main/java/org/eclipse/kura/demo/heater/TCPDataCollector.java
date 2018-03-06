package org.eclipse.kura.demo.heater;


import java.net.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import java.io.*;


public class TCPDataCollector extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(Heater.class);
    public static final int serverPort=9862;
    protected ServerSocket serverSocket;
    private static final int jsonLimit=1024;
    
    
    private DataPublisher dataPublisher;
    
    private void notifylog() {
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    	logger.info("******************notify******************");
    }
    
    
    
    public TCPDataCollector() {
    	logger.info("creating TCP Data Collector...");
    	try {
    		serverSocket=new ServerSocket(serverPort);
    	}catch(IOException e) {
    		notifylog();
    		logger.info(e.toString());
    	}
    	
    	try {
    		logger.info("creating Publisher...");
    		dataPublisher=new DataPublisher();
    		logger.info("creating succ");
    	}catch(Exception e) {
    		notifylog();
    		logger.info(e.toString());
    	}
    }
    
    
    public void run() {
    		
    	while(true) {
    		try {
    			Socket connection=serverSocket.accept();
    			ClientHandler client=new ClientHandler(connection);
    			client.start();
    		}catch(IOException e) {
    			notifylog();
    			logger.info(e.toString());
    		}
    	}
    	
    }
    
    
    
    
    
	private class ClientHandler extends Thread{
		Socket m_connection;
		StringBuffer inBuffer=new StringBuffer("");

		public static final char startSign='{';
		public static final char stopSign='}';
		
		
		public ClientHandler(Socket connection) {
			
			logger.info("new client has connected...");
			logger.info(connection.getInetAddress().toString());
			
			m_connection=connection;
			
		}
		
		
		public void run() {
			try {
				//OutputStream os=m_connection.getOutputStream();
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
					Gson gson=new Gson();
					SensorData inData=gson.fromJson(inBuffer.toString(), SensorData.class);

					logger.info(inData.Type.toString());
					logger.info(new Integer(inData.SensorID).toString());
					
					try {
						dataPublisher.publishData(inData);
					}catch(Exception e) {
					
						logger.info(e.toString());
					}
				}
			}catch(IOException e) {
				logger.info(e.toString());
			}
		}
		
		
	}
	
}