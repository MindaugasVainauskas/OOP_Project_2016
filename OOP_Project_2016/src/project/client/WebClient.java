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
		
		
		//while loop to show menu to user		
		do{
			menu.showMenuOptions();  //show the menu options to user
			userChoice = scan.nextInt();  //choice read
				
			//switch to act on user's decision
			switch(userChoice){
				case 1:
					cn = new Connection();
					System.out.println("Making connection to server");
					System.out.println("Connection details: "+hostName+"; Port: "+hostPort);
					cn.connectToServer(hostName, hostPort);
					break;
				case 2:
					System.out.println("Printing file listing");
					break;
				case 3:
					System.out.println("Downloading a file");
					break;
				case 4:
					System.out.println("Application is exiting. Good Bye");
					keepRunning = false;					
					break;			
			}
			
		}while(keepRunning);
	}
	

}
