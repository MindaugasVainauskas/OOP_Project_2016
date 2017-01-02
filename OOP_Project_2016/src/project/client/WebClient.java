package project.client;

import java.util.Scanner;

import project.client.config.Context;
import project.client.config.XMLParser;

public class WebClient {
	//This class will use scanner to take in user chosen option
	private Scanner scan = new Scanner(System.in);
	Connection cn;
	
	//variables for client setup
	private String hostName;
	private int hostPort;
	private String directory;
	
	private String fileName; // File name to be downloaded in option 3 of menu.
	
	private volatile boolean keepRunning = true;
	
	//make new instance of UI menu to show to user
	private UI menu = new UI();
	
	//set the context and parser
	Context ctx = new Context();
	XMLParser cp = new XMLParser(ctx);
	
	
	private void configClient() throws Throwable{
		cp.initialise();
		hostName = ctx.getHost();
		hostPort = ctx.getPort();
		directory = ctx.getDirectory();
	}

	
	//method to get the choice made by user
	protected void MakeChoice() throws Throwable{
		//variables to use in this class
		int userChoice;		
		configClient();//works. variables are getting the values from xml parser without issues. 
		//works so test can proceed further
		//System.out.println(ctx);
		cn = new Connection();
		cn.sethName(hostName);
		cn.sethPort(hostPort);
		
		//while loop to show menu to user		
		do{
			menu.showMenuOptions();  //show the menu options to user
			userChoice = scan.nextInt();  //choice read
			
			//switch to act on user's decision
			switch(userChoice){
				case 1:					
					System.out.println("Making connection to server");						
					cn.execute(1);
					//cn.connectToServer(hostName, hostPort);
					break;
				case 2:
					System.out.println("Printing file listing\n");
					cn.execute(2);
					//cn.ListFiles();
					break;
				case 3:
					System.out.println("Please enter full file name with extension to download");
					fileName = scan.next(); // Get full file name 
					scan.nextLine();//flush the buffer
					
					System.out.println("Downloading a file "+fileName);
					cn.execute(3);
					//cn.DownloadFile(directory, fileName);
					break;
				case 4:
					System.out.println("Client side application is exiting.");
					cn.execute(4);
					//cn.endConnection();
					keepRunning = false;					
					break;			
			}
			
		}while(keepRunning);
	}
	

}
