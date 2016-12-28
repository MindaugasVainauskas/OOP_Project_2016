package project.server;

//imports to handle IO and network requests
import java.io.*;
import java.net.*;

import project.client.Connection;

public class WebServer {
	private ServerSocket ss;
	private Socket cs;
	
	private volatile boolean keepRunning = true;
	private int counter;
	private static final int SERVER_PORT = 7777;// name the port number that server is going to run on.
	//for above I used server number required in the project description
	
	private WebServer(){
		counter = 0;
		try {
			ss = new ServerSocket(SERVER_PORT);// start up new socket connection on given port number.
			cs = null;
			
			
			
			System.out.println("Server up and running at port "+SERVER_PORT);
		} catch (IOException e) {
			System.out.println("Error: "+e.getMessage());
			e.printStackTrace();
		}
		
		while(keepRunning){
			try {
				Thread server = new Thread(new Connection(cs), "Thread "+(counter+1));
				server.setPriority(Thread.MAX_PRIORITY);
				server.start();
				counter++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	private class HTTPRequest implements Runnable{
		private Socket sock;
		
		private HTTPRequest(Socket request){
			this.sock = request;
		}
		
		public void run(){
			try {
				ObjectInputStream ins = new ObjectInputStream(sock.getInputStream());
				Object command = ins.readObject();
				System.out.println(command);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}//End of WebServer class
