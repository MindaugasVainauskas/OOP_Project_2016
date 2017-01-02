package project.server;

//imports to handle IO and network requests
import java.io.*;
import java.net.*;

public class WebServer {
	private ServerSocket ss;
	
	
	private volatile boolean keepRunning = true; // Boolean variable to serve as loop condition
	
	private static final int SERVER_PORT = 7777;// name the port number that server is going to run on.
	
	private static final String FILE_DIRECTORY = "/File_Source";
	//for above I used server number required in the project description
	private int counter;
	
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
					new Thread(new Request(s), "Thread "+(counter+1)).start();//listener thread spawns new worker thread to do the job
					counter++;
					System.out.println("Connection count at "+counter);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}// End of Listener class
	
	
	private class Request implements Runnable{
		private Socket sock;
		private volatile boolean stayConnected = true;
		private String message;
		
		private ObjectOutputStream out;
		private ObjectInputStream ins;
		
		private Request(Socket request){
			this.sock = request;
		}
		
		public void run(){
			try {
				ins = new ObjectInputStream(sock.getInputStream());	
				
			//keep connection up until user decides to disconnect. Has to take in at least one message so do/while loop is better suited than while loop here.
			do{
				Object command = ins.readObject();
				System.out.println(command);
				
				switch((String)command){
					case "Hello Server":
						//server response to client
						message = "Client "+Thread.currentThread().getName()+", connected on socket "+sock;//response message to show bidirectional communication between client and server
						out = new ObjectOutputStream(sock.getOutputStream());
						out.writeObject(message);
						out.flush();
						
						break;
					case "listFiles":
						listFiles();	
						break;
					case "endConnection":
						endConnection();
						break;
					default:
						System.out.println("Unknown Command: "+command);
						
				}//end of switch(command)				
				
			}while(stayConnected);//end of while loop				
				
				
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
			//out = new ObjectOutputStream(sock.getOutputStream());
			out.writeObject(message);
			out.flush();
			System.out.println(Thread.activeCount());
			
			//change control variable to false to end loop
			stayConnected = false;
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
			//out = new ObjectOutputStream(sock.getOutputStream());
			out.writeObject(response);
			out.flush();
			
		}
		
	}//End of Request class

}//End of WebServer class
