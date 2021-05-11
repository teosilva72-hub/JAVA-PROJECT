package br.com.tracevia.webapp.util;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskManager {
	
			
	public TaskManager() {}
		
	public void emailNotificationService() {
		
		final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		
		Runnable tester = new Runnable() {
			  public void run() {
			  
				 // taskCount = 1;
			    			  
				  Calendar calendar = Calendar.getInstance();
				  
				
				  
			    	System.out.println("HERE");
			       // exec.shutdown();
						  	    
			  }
		  };
		
		  //if(taskCount == 0)
		  exec.scheduleAtFixedRate(tester, 0, 5, TimeUnit.SECONDS);
	}
	
}
