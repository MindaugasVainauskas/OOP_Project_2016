//This is the class that will parse client configuration parameters
package project.client.config;

import javax.xml.parsers.*; // Import parsers to read xml files
import org.w3c.dom.*; // Import DOM model to work with documents

public class XMLParser {
	
	private Context ctx;

	public XMLParser(Context ctx) {
		super();
		this.ctx = ctx;
	}
	
	public void initialise() throws Throwable{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(Context.CONF_FILE);
		
		Element root = doc.getDocumentElement();
		NodeList children = root.getChildNodes();
		
		//for loop to find the elements of host, port and directory
		for(int i = 0; i < children.getLength(); i++){
			Node next = children.item(i);
			
			//check for Element instance
			if(next instanceof Element){
				Element e = (Element)next;
				
				if(e.getNodeName().equals("server-host")){					
					ctx.setHost(e.getFirstChild().getNodeValue());						
				}else if(e.getNodeName().equals("server-port")){
					ctx.setPort(Integer.parseInt(e.getFirstChild().getNodeValue())); // need to remember to parse this string as Integer as value in ctx variable is int
				}else if(e.getNodeName().equals("download-dir")){
					ctx.setDirectory(e.getFirstChild().getNodeValue());
				}
			}//end of if statement to check if node is instance of Element type
		}//end of for loop that loops over children nodes
	}//end of initialise method

	//getter and setter for context
	public Context getCtx() {
		return ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}
		
}//end of XMLParser class
