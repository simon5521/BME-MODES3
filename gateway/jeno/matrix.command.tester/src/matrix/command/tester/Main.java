package matrix.command.tester;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		MuebArray cont=new MuebArray();
		System.out.println(cont.toJsonString());
		System.out.println("test started");
		try {
			RestApiService service=new RestApiService();
			service.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
		Commander comm=new Commander("get-status");
		System.out.println("initialisation is successful :)");
		for(int i=0;i<256;i++) {
			System.out.println("sending to ip address: 192.168.1."+Integer.toString(i));
			try {
				System.out.println(comm.send("192.168.1."+Integer.toString(i)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(Integer emelet=6;emelet<=18;emelet++) {
			for(Integer szoba=5;szoba<=12;szoba++) {
				String ip="10.6."+emelet.toString()+"."+szoba.toString();
				System.out.println("get status from ip address: "+ip);
				Mueb m=cont.getMueb(emelet, szoba);
				try {
					String valasz=comm.send(ip);
					m.status=valasz;
					m.valid=true;
					m.timeoutCount=0;
				}catch(Exception e) {
					m.valid=false;
					m.timeoutCount++;
				}
				cont.setMueb(emelet, szoba, m);
			}
		}
	}

}