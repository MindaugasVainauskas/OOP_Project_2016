package project.client;

import java.net.*;
import java.io.*;

public class Connection{
	private Socket s;
	private ObjectOutputStream out;
	private ObjectInputStream in;		
	
	private String hName;
	private int hPort;
	private String directory;
	private String fileName;
	
	
	
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

	
	//getters/setters for directory and file name for downloading file
	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
				DownloadFile(directory, fileName);				
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
		String  FILE_PATH = directory+"/"+fName; //set the location for file to be downloaded to.
		int buffer = 1024;//1KB file buffer size
		int bytesRead;//int variable to receive incoming byte array 
		int currentRead = 0;//variable to show how many bytes have been read already. Initialise it 0 as its empty at the moment.
		
		String request = "downloadFile";
		
		try {			
			out.writeObject(request);//send request to download file
			out.flush();// need to make sure that message has been sent
						
			//File name is sent back with subsequent request
			String request2 = fName;
			out.writeObject(request2);
			out.flush();
			
			String response2 = (String)in.readObject();
			System.out.println(response2);
			
			System.out.println("Path for downloaded file: "+FILE_PATH);
			
			
			long fileSize = in.readLong();
			System.out.println(fileSize);
			//receive the file
			byte [] byteArray = new byte[(int) fileSize];
			FileOutputStream fos = new FileOutputStream(FILE_PATH);
		
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bytesRead = in.read(byteArray, 0, byteArray.length);
			currentRead = bytesRead;
			
			//while there is data to be read, keep reading input stream of bytes into bytes read variable.
			do{
				bytesRead = in.read(byteArray, currentRead, (byteArray.length-currentRead));
				if(bytesRead >= 0){
					currentRead += bytesRead;//update currently read byte amount with bytes read.
					fileSize -= bytesRead;
				}
			}while(bytesRead > -1 && fileSize > 0);
			
			bos.write(byteArray, 0, currentRead);//write the file into new location
			bos.flush();//make sure all bytes written at new location.
			bos.close();//close at the end			
			fos.close();
			
			System.out.println("File successfully downloaded");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
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
