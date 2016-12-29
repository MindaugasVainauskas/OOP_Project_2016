package project.client;

import java.io.IOException;
import java.net.*;
import java.io.*;

public class Connection{
	
	//method to connect to server
	public void connectToServer(String hName, int hPort){
		String message = "Hello Server";
		new Thread(new Runnable(){
			public void run(){
				try {
					Socket s = new Socket(hName, hPort);// set up socket connection with server at given hostname and port
					System.out.println("Connection details in Connection class: "+hName+"; "+hPort);
					ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
					out.writeObject(message);
					out.flush();// need to make sure that message has been sent
					ObjectInputStream in = new ObjectInputStream(s.getInputStream());
					
					//close the connections. Not sure if these are at correct position
					out.close();
					in.close();
					s.close();
				
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}).start();
		
	}
	
}
