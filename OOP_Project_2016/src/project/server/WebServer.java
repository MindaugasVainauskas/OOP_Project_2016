package project.server;

//imports to handle IO and network requests
import java.io.*;
import java.net.*;

public class WebServer {
	private ServerSocket ss;
	
	
	private volatile boolean keepRunning = true; // Boolean variable to serve as loop condition
	
	private static final int SERVER_PORT = 7777;// name the port number that server is going to run on.
	
	private static final String FILE_SOURCE = "./File_Source/";
	//for above I used server number required in the project description
	private int counter;
	private Object lock = new Object();
	
	public WebServer(){
		
		try {
			ss = new ServerSocket(SERVER_PORT);// start up new socket connection on given port number.
			
			//start listener thread
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
			counter = 0;
			
			while(keepRunning){
				try {
					Socket s = ss.accept();
					new Thread(new RequestHandler(s), "Thread "+(counter+1)).start();//listener thread spawns new worker thread to do the job
					incrementCounter();
					System.out.println("Connection count at "+counter);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}// End of Listener class
	
	
	private class RequestHandler implements Runnable{
		
		private Socket sock; // socket name to be used
		private volatile boolean stayConnected = true;//variable to manage threads and shut them down when finished.
		private String message;//message to send to client
		
		private ObjectOutputStream out;//output to client
		private ObjectInputStream ins;//input from client
		
		private RequestHandler(Socket request){
			this.sock = request;
		}
		
		public void run(){
			try {
				ins = new ObjectInputStream(sock.getInputStream());	
				
			//keep connection up until user decides to disconnect.
			while(stayConnected){
				Object command = ins.readObject();
				System.out.println(command);
				
				switch((String)command){
					case "Establish_Connection":
						//server response to client
						message = "Client "+Thread.currentThread().getName()+", connected on socket "+sock;//response message to show bidirectional communication between client and server
						out = new ObjectOutputStream(sock.getOutputStream());
						out.writeObject(message);
						out.flush();
						
						break;
					case "listFiles":
						listFiles();	
						break;
					case "downloadFile":
						//receive subsequent message with file name to download
						String fileName = (String)ins.readObject();//read in file name from client response
						System.out.println("file to download: "+fileName);
						DownloadFile(fileName);//pass file name into method to handle download
						break;
					case "endConnection":
						endConnection();
						break;
					default:
						System.out.println("Unknown Command: "+command);
						
				}//end of switch(command)				
				
			}//end of while loop				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//close the connections at the end.
			try {
				sock.close();
				out.close();
				ins.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		//method to end connection
		public void endConnection() throws IOException {
			//server response to client
			message = "Server disconnected. Good bye";
			
			out.writeObject(message);
			out.flush();
						
			//change control variable to false to end loop
			stayConnected = false;
			decrementCounter();//reduce counter for active threads as this thread is closing.
			System.out.println("Active client count at: "+counter);
			
		}

		//method to handle file listing
		public void listFiles() throws IOException {
			String response = "";
			//code for reading files go here
			File fileLocation = new File("./File_Source");// set up new file location of file folder at given path
			File[] fileList = fileLocation.listFiles();//set up list of files in that folder
			
			//do a check to see if the folder is empty or not
			if(fileList.length == 0){
				System.out.println("File directory is empty!");// Message to display if file folder is empty.
			}
			else{
				response = response+"Files currently in directory:\n";
				//if folder is not empty then list of files in it is printed out
				for(int i = 0; i<fileList.length; i++){
					if(fileList[i].isFile()){
						response = response+fileList[i].getName()+"\n";
					}
				}
				System.out.println("\n");
			}//end of if/else statement			
			out.writeObject(response);
			out.flush();
			
		}
		
		public void DownloadFile(String fileName){
			String message = "Downloading is about to commence. File selected: "+fileName;
			String filePath = FILE_SOURCE+fileName;//path to file in file_Source directory
			try {
				out.writeObject(message);//send message about start of download
				out.flush();
								
				
				File fileToSend = new File(filePath);//instantiate file at given location
				
				//check that file with given name exists and is a file, not a folder
				if(fileToSend.exists() && fileToSend.isFile()){
					out.writeLong(fileToSend.length());//send size of file first
					out.flush();
					
					byte[] byteArray = new byte[(int)fileToSend.length()];//set up byte array to send file. It is same length as file size in bytes.
					FileInputStream fins = new FileInputStream(fileToSend);//read in file from folder
					BufferedInputStream bins = new BufferedInputStream(fins);//store file in temporary storage
					bins.read(byteArray, 0, byteArray.length);//read file into temporary storage
					
					out.write(byteArray, 0, byteArray.length);//send file in output stream
					out.flush();
					bins.close();				
					System.out.println("Transfer completed!");					
				}//end if
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
	}//End of RequestHandler class
	
	//only allow one thread at a time access to changing counter amount. as such synchronization is needed.
	private void incrementCounter(){
		synchronized (lock){
			counter++;
		}
	}
	private void decrementCounter(){
		synchronized (lock){
			counter--;
		}
	}

}//End of WebServer class
