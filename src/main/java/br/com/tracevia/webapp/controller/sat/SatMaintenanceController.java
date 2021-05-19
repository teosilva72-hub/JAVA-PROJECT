package br.com.tracevia.webapp.controller.sat;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

@ManagedBean(name="maintenanceSatView")
@ViewScoped
public class SatMaintenanceController{
private int hora;
private String[] hours;


public String[] getHours() {
	return hours;
}

public void setHours(String[] hours) {
	this.hours = hours;
}


	@PostConstruct
	public void initializer() {
		try {
			//hora =  LocalDateTime.now().getHour();
			//hours();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void enableUser() throws Exception {
		TimeUnit.SECONDS.sleep(1);
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("btnEnable();");
		hours();
		
	}//atualiza a tela
	public void hours() throws Exception{
		
		int hour = (hora + 1); 
		hours = new String[24];

		 for(int h = 0; h < 24; h++){
			 if(hour > 23)
		        hour = 0;
			 	if(hour < 10)
			 		hours[h] ="0" + hour;
			 	
			 	else hours[h] = String.valueOf(hour);
			 	hour++;
       
		 }
	
	}
}
