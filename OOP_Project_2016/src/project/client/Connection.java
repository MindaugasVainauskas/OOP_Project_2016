package project.client;

import java.io.BufferedReader;
import java.net.Socket;

public class Connection implements Runnable{
	
	private Socket cs; // client socket is set up
	private BufferedReader in = null; // buffered reader to handle incoming traffic
	
	public Connection(Socket client){
		this.cs = client;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
