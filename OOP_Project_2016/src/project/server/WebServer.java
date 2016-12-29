package project.server;

//imports to handle IO and network requests
import java.io.*;
import java.net.*;

import project.client.Connection;

public class WebServer {
	private ServerSocket ss;
	private Socket cs;
	
	private volatile boolean keepRunning = true; // Boolean variable to serve as loop condition
	
	private static final int SERVER_PORT = 7777;// name the port number that server is going to run on.
	//for above I used server number required in the project description
	
	public WebServer(){
		
		try {
			ss = new ServerSocket(SERVER_PORT);// start up new socket connection on given port number.
			cs = null;
			
			Thread server = new Thread(new Listener(), "Listener thread ");//listener thread to listen for connections
			server.setPriority(Thread.MAX_PRIORITY);
			server.start();
			
			System.out.println("Server up and running at port "+SERVER_PORT);
		} catch (IOException e) {
			System.out.println("Error: "+e.getMessage());
			e.printStackTrace();
		}
		
	
	}
	
	private class Listener implements Runnable{
		public void run(){
			int counter = 0;
			
			while(keepRunning){
				try {
					Socket s = ss.accept();
					new Thread(new HTTPRequest(s), "Thread "+(counter+1)).start();//listener thread spawns new worker thread to do the job
					counter++;
					System.out.println("Connection count at "+counter);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}// End of Listener class
	
	
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
				
				String message = "Working connection";
				ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
				out.writeObject(message);
				out.flush();
				out.close();
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
