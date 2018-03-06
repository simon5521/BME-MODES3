package org.eclipse.kura.demo.heater;

import java.util.Date;

import org.eclipse.kura.KuraException;
import org.eclipse.kura.cloud.CloudClient;
import org.eclipse.kura.cloud.CloudService;
import org.eclipse.kura.message.KuraPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataPublisher {
	
	public DataPublisher() throws KuraException {
	
		logger.info("creating publisher...");
		cloudClient=cloudService.newCloudClient(appID);
		logger.info("creating success");
	
	}	
	
	public String topic="SensorData";
	public int qos=0;// quality of service
	public boolean retain =false;
	public final String appID="DataCollector";
	
	private CloudClient cloudClient;
	private CloudService cloudService;
	
    private static final Logger logger = LoggerFactory.getLogger(Heater.class);
	
	public void publishData(SensorData data) throws Exception{
		
		logger.info("--------start to publishing data...");
		KuraPayload payload=new KuraPayload();

		logger.info("--------add data metric data...");
		payload.addMetric("Data", data);

		logger.info("--------publishing add gID metric data...");
		payload.addMetric("GatewayID", "KuraPi1");

		logger.info("--------publishing add date metric data...");
		payload.setTimestamp(new Date());

		logger.info("--------publishing data...");
		this.cloudClient.publish(topic,payload, qos, retain);
		logger.info("success!");
		
		
	}
	
	public void open() throws Exception {
	}
	
	public void close() {
		this.cloudClient.release();
	}

}