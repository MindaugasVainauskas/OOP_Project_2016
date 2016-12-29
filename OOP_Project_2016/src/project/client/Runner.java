//This class is meant as a runner class and as such will only contain main method.
package project.client;

import project.client.config.*;


public class Runner {

	public static void main(String[] args) throws Throwable {
//		//set the context and parser
//		Context ctx = new Context();
//		XMLParser cp = new XMLParser(ctx);
//		//check that context values are getting through
//		cp.initialise();
				
		//show the menu and menu choices		
		WebClient client = new WebClient();		
		client.MakeChoice();
				
//		System.out.println(ctx);

	}

}
