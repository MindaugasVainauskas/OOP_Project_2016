package project.client;

import java.util.Scanner;

public class OptionChoice {
	//This class will use scanner to take in user chosen option
	private Scanner scan = new Scanner(System.in);
	
	//make new instance of UI menu to show to user
	private UI menu = new UI();
	
	//method to get the choice made by user
	protected void MakeChoice(){
		//variables to use in this class
		int userChoice;
		boolean keepRunning = true;
		
		
		
		//while loop to show menu to user		
		do{
			menu.showMenuOptions();  //show the menu options to user
			userChoice = scan.nextInt();  //choice read
				
			//switch to act on user's decision
			switch(userChoice){
				case 1:
					System.out.println("Making connection to server");
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
