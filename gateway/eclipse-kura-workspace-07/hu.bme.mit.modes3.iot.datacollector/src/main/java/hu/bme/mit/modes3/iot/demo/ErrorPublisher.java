package hu.bme.mit.modes3.iot.demo;

import java.io.FilterInputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

import org.eclipse.kura.KuraException;
import org.eclipse.kura.cloud.CloudClient;
import org.eclipse.kura.cloud.CloudService;
import org.eclipse.kura.message.KuraPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorPublisher {
	
	public ErrorPublisher(CloudService cloudService) throws KuraException {
	
		logger.info("creating publisher...");
		cloudClient=cloudService.newCloudClient(appID);
		logger.info("creating publisher...Done");
	
	}	
	
	
	public String topic="SensorData";
	public int qos=2;// quality of service
	public boolean retain =false;
	public final String appID="ErrorCollector";
	private double temperatureMinLimit=18.0;
	private double temperatureMaxLimit=30.0;
	private double accMaxLimit=5;
	
	private CloudClient cloudClient;
	
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    
    
	

	public void checkData(WatchmanData data) throws Exception{
		
		double acc=Math.sqrt(Math.pow(data.AccX, 2)+Math.pow(data.AccY, 2)+Math.pow(data.AccZ, 2));
		
		if(acc>accMaxLimit) {
			KuraPayload payload=new KuraPayload();
			
			payload.addMetric("SensorID", data.SensorID);
			payload.addMetric("MicroTimeStamp", data.MicroTimeStamp);

			payload.addMetric("Cause", "QUAKE");

			payload.setTimestamp(new Date());

			logger.info("--------publishing error...");
			this.cloudClient.publish(topic,payload, qos, retain);
			logger.info("success!");
			
		}
		
	}
	
	public void checkData(SolarData data) throws Exception{
		
	}

	public void checkData(AccData data) throws Exception{
		
		double acc=Math.sqrt(Math.pow(data.AccX, 2)+Math.pow(data.AccY, 2)+Math.pow(data.AccZ, 2));
		
		if(acc>accMaxLimit) {
			KuraPayload payload=new KuraPayload();
			
			payload.addMetric("SensorID", data.SensorID);
			payload.addMetric("MicroTimeStamp", data.MicroTimeStamp);

			payload.addMetric("Cause", "QUAKE");

			payload.setTimestamp(new Date());

			logger.info("--------publishing error...");
			this.cloudClient.publish(topic,payload, qos, retain);
			logger.info("success!");
			
		}
		
	}
	
	public void checkData(CameraData data) throws Exception{
		
		
		if((data.UpperBound>data.BoxBottom) && (data.LowerBound<data.BoxBottom) && (data.LabelText.compareTo("train")!=0)) {
			KuraPayload payload=new KuraPayload();
			
			payload.addMetric("SensorID", data.SensorID);
			payload.addMetric("MicroTimeStamp", data.MicroTimeStamp);

			payload.addMetric("Cause", "RAILBLOCKED");

			payload.setTimestamp(new Date());

			logger.info("--------publishing error...");
			this.cloudClient.publish(topic,payload, qos, retain);
			logger.info("success!");
			
		}
		
	}
	
	public void checkData(DHTData data) throws Exception{

		
		if(data.Temperature>temperatureMaxLimit) {
			
			
			KuraPayload payload=new KuraPayload();
			
			payload.addMetric("SensorID", data.SensorID);
			payload.addMetric("MicroTimeStamp", data.MicroTimeStamp);

			
			payload.addMetric("Cause", "HOT");

			logger.info("--------publishing add date metric data...");
			payload.setTimestamp(new Date());

			logger.info("--------publishing data...");
			this.cloudClient.publish(topic,payload, qos, retain);
			logger.info("success!");
			
		}
		
		if(data.Temperature<temperatureMinLimit) {
			
			
			KuraPayload payload=new KuraPayload();
			
			payload.addMetric("SensorID", data.SensorID);
			payload.addMetric("MicroTimeStamp", data.MicroTimeStamp);

			
			payload.addMetric("Cause", "COLD");

			logger.info("--------publishing add date metric data...");
			payload.setTimestamp(new Date());

			logger.info("--------publishing data...");
			this.cloudClient.publish(topic,payload, qos, retain);
			logger.info("success!");
			
		}
		
		
	}
	
	public void open() throws Exception {
	}
	
	public void close() {
		this.cloudClient.release();
	}
	
	
    

}