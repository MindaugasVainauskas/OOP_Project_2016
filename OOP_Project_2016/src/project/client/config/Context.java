//This is a bean class that will hold the information needed for client to connect to the servers
package project.client.config;

public class Context {
	
	//variables needed 
	public static final String CONF_FILE = "conf.xml";
	
	private String host;
	private int port;
	private String directory;
	
	public Context(){
		super();
	}

	//getters and setters for host, port and download directory
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	@Override
	public String toString() {
		return "Context [host=" + host + ", port=" + port + ", directory=" + directory + "]";
	}
	
	

}
