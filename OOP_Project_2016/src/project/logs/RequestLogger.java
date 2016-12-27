package project.logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import org.omg.CORBA.Request;

public class RequestLogger implements Runnable{
	private BlockingQueue bq;
	private boolean keepRunning = true;
	private FileWriter fw;
	
	public RequestLogger(BlockingQueue q){
		this.bq = q;
		
		try {
			fw = new FileWriter(new File("RequestLogs.txt"));
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(keepRunning){
			Request r = bq.take();
			if(r instanceof PoisonRequest)
				keepRunning = false;
			
			fw.write(r.toString()+"\n");
		}
		
		fw.close();
	}

}
