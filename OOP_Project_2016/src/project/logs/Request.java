package project.logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//main class for handling request logs
public class Request implements Serializable{
	private String command;
	private String host;
	private Date dt;
	
	public Request(String command, String host, Date d){
		this.command = command;
		this.host = host;
		this.dt = d;
	}

	// ------------- Getters and setters for the variables used ----------------------------
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}
	
	// set up blocking queue of request types.
	BlockingQueue<Request> q = new ArrayBlockingQueue<>(7);
	
	//put in inner poison request class
	public class PoisonRequest extends Request{

		public PoisonRequest(String command, String host, Date d) {
			super(command, host, d);			
		}
		
	}
	
	//inner request logger class(will see if I can separate them later)
	public class RequestLogger implements Runnable{
		
		private BlockingQueue<Request> bq;
		private volatile boolean keepRunning = true;
		private FileWriter fw;

		public RequestLogger(BlockingQueue<Request> q){
			this.bq = q;
			
			try {
				fw = new FileWriter(new File("RequestLogs.txt"));
			} catch (IOException e) {			
				e.printStackTrace();
			}
		}
		
		public void run() {
			while(keepRunning){
				Request r;
				try {
					r = bq.take();
					if(r instanceof PoisonRequest)
						keepRunning = false;
					
					fw.write(r.toString()+"\n");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
