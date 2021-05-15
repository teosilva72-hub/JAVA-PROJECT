package br.com.tracevia.webapp.controller.sat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.model.global.Notifications;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.util.LocaleUtil;
@ManagedBean(name="maintenanceSatView")
@ViewScoped
public class SatMaintenanceController{
private int hora, idEquip;
private String[] hours;
private String name, city, road, km, totalLane,
sense1, sense2, sense3, sense4, sense5, sense6,
sense7, sense8;
EquipmentsDAO dao = new EquipmentsDAO();
private List<SAT> sat;
LocaleUtil locale;	
public EquipmentsDAO getDao() {
	return dao;
}
public void setDao(EquipmentsDAO dao) {
	this.dao = dao;
}
public List<SAT> getSat() {
	return sat;
}
public void setSat(SAT sat) {
	this.sat = (List<SAT>) sat;
}
public int getHora() {
	return hora;
}
public void setHora(int hora) {
	this.hora = hora;
}
public int getIdEquip() {
	return idEquip;
}
public void setIdEquip(int idEquip) {
	this.idEquip = idEquip;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getRoad() {
	return road;
}
public void setRoad(String road) {
	this.road = road;
}
public String getKm() {
	return km;
}
public void setKm(String km) {
	this.km = km;
}
public String getTotalLane() {
	return totalLane;
}
public void setTotalLane(String totalLane) {
	this.totalLane = totalLane;
}
public String getSense1() {
	return sense1;
}
public void setSense1(String sense1) {
	this.sense1 = sense1;
}
public String getSense2() {
	return sense2;
}
public void setSense2(String sense2) {
	this.sense2 = sense2;
}
public String getSense3() {
	return sense3;
}
public void setSense3(String sense3) {
	this.sense3 = sense3;
}
public String getSense4() {
	return sense4;
}
public void setSense4(String sense4) {
	this.sense4 = sense4;
}
public String getSense5() {
	return sense5;
}
public void setSense5(String sense5) {
	this.sense5 = sense5;
}
public String getSense6() {
	return sense6;
}
public void setSense6(String sense6) {
	this.sense6 = sense6;
}
public String getSense7() {
	return sense7;
}
public void setSense7(String sense7) {
	this.sense7 = sense7;
}
public String getSense8() {
	return sense8;
}
public void setSense8(String sense8) {
	this.sense8 = sense8;
}
public String[] getHours() {
	return hours;
}
public void setHours(String[] hours) {
	this.hours = hours;
}

	@PostConstruct
	public void initializer() {
		try {
			hora =  LocalDateTime.now().getHour();
			hours();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void infoEquip() throws Exception{
		sat = new ArrayList<SAT>();
		sat =  dao.buildSatEquipmentsInterface();
			   SAT not = new SAT();
			   sat.add(not);
			   System.out.println(not.getNome()+" < aqui");		   				 
					
	}
	public void hours() throws Exception{
		infoEquip();
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
	}//horas sat
	
	/*public void enableUser() throws Exception {
	TimeUnit.SECONDS.sleep(1);
	RequestContext request = RequestContext.getCurrentInstance();
	request.execute("btnEnable();");
	hours();
}//atualiza a tela*/
}
