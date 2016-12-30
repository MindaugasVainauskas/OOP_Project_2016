package project.client;

import java.net.*;
import java.io.*;

public class Connection{
	protected Socket s;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	//method to connect to server
	protected void connectToServer(String hName, int hPort){
		String message = "Hello Server";
		new Thread(new Runnable(){
			public void run(){
				try {
					s = new Socket(hName, hPort);// set up socket connection with server at given hostname and port
					System.out.println("Connection details in Connection class: "+hName+"; "+hPort);
					out = new ObjectOutputStream(s.getOutputStream());
					out.writeObject(message);
					out.flush();// need to make sure that message has been sent
					
					//read response from server
					in = new ObjectInputStream(s.getInputStream());
					//check for response from server
					try {
						String message = (String)in.readObject();
						System.out.println(message);
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
				//if socket or object streams are still active close them down at the end
				finally{
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
			
		}).start();
		
	}

	//method to list files on server
	protected void ListFiles(){
		File fileLocation = new File("./File_Source");// set up new file location of file folder at given path
		File[] fileList = fileLocation.listFiles();//set up list of files in that folder
		
		//do a check to see if the folder is empty or not
		if(fileList.length == 0){
			System.out.println("File directory is empty!");// Message to display if file folder is empty.
		}
		else{
			System.out.println("Files currently in directory:");
			//if folder is not empty then list of files in it is printed out
			for(int i = 0; i<fileList.length; i++){
				if(fileList[i].isFile()){
					System.out.println(fileList[i].getName());
				}
			}
			System.out.println("\n");
		}//end of if/else statement
		
		
	}//end of ListFiles method
	
	//method to download a file. Method takes in download directory name and file name as parameters.
	protected void DownloadFile(String directory, String fName){
		final String  FILE_TO_GET = "./"+directory+"/"+fName; //set the location for file to be downloaded to.
		
		
		
	}
}
