//This class is meant as a runner class and as such will only contain main method.
package project.client;

public class ClientRunner {

	public static void main(String[] args) throws Throwable {				
		//show the menu and menu choices		
		WebClient client = new WebClient();		
		client.MakeChoice();
	}

}
