package project.client;

import java.util.Scanner;

public class OptionChoice {
	//This class will use scanner to take in user chosen option
	private Scanner scan = new Scanner(System.in);
	
	//make new instance of UI menu to show to user
	UI menu = new UI();
	
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
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					keepRunning = false;					
					break;			
			}
			
		}while(keepRunning);
	}
	

}
