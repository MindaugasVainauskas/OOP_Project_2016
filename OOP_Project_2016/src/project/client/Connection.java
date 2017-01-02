package project.client;

import java.net.*;
import java.io.*;

public class Connection{
	private Socket s;
	private ObjectOutputStream out;
	private ObjectInputStream in;		
	
	private String hName;
	private int hPort;
	
	//getters/setters for host name and port number
	public String gethName() {
		return hName;
	}

	public void sethName(String hName) {
		this.hName = hName;
	}

	public int gethPort() {
		return hPort;
	}

	public void sethPort(int hPort) {
		this.hPort = hPort;
	}

	
	//execute method to handle user choices
	public void execute(int userChoice){		
		
			switch(userChoice){
			case 1:
				connectToServer(hName, hPort);
				break;
			case 2:
				ListFiles();
				break;
			case 3:
				System.out.println("Not implemented yet");
				break;
			case 4:
				endConnection();				
				break;
			default:
				System.out.println("Unknown choice");
			}
		
		
	}
	
	
	//method to connect to server
	protected void connectToServer(String hName, int hPort){			
			try {
				s = new Socket(hName, hPort);// set up socket connection with server at given hostname and port
				String message = "Hello Server";	
				out = new ObjectOutputStream(s.getOutputStream());
				out.writeObject(message);
				out.flush();// need to make sure that message has been sent
				//out.close();
				
				//read response from server
				in = new ObjectInputStream(s.getInputStream());					
				
				//check for response from server
				try {
					String response = (String)in.readObject();
					System.out.println(response);
					//in.close();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}	
	

	//method to list files on server
	protected void ListFiles(){
		String request = "listFiles";
		
		try {
			//out = new ObjectOutputStream(s.getOutputStream());
			out.writeObject(request);
			out.flush();// need to make sure that message has been sent
			
			
			//get server response to request
			//in = new ObjectInputStream(s.getInputStream());
			String response = (String)in.readObject();
			System.out.println(response);
			//in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}//end of ListFiles method
	
	//method to download a file. Method takes in download directory name and file name as parameters.
	protected void DownloadFile(String directory, String fName){
		//final String  FILE_TO_GET = "./"+directory+"/"+fName; //set the location for file to be downloaded to.
		
		
		
	}

	protected void endConnection() {
		
		String request = "endConnection";
		
		try {
			//out = new ObjectOutputStream(s.getOutputStream());
			out.writeObject(request);
			out.flush();// need to make sure that message has been sent
			//out.close();
			
			//get server response to request
			//in = new ObjectInputStream(s.getInputStream());
			String response = (String)in.readObject();
			System.out.println(response);
			//in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			//if socket or object streams are still active close them down at the end
			try {
				if(s != null){						
					s.close();						
				}
				if(out != null){
					out.close();
				}
				if(in != null){
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		
	}
}
