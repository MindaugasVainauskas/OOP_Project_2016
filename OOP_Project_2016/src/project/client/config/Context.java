//This is a bean class that will hold the information needed for client to connect to the servers
package project.client.config;

public class Context {
	
	//Name of configuration xml file for parsing
	private final String CONF_FILE = "conf.xml";
	//variables needed 
	private String host;
	private int port;
	private String directory;
	
	public Context(){
		super();
	}
	
	public String getConfigFile(){
		return CONF_FILE;
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
