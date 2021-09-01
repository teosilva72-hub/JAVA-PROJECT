package br.com.tracevia.webapp.model.global;

/**
 * Esta classe destinada a contar variáveis que farão a conexão entre 
 * frontend e o backend.
 */

public class ReportSelection {
		
	public String[] equipments;	
	private String[] vehicles;	
	private String[] directions;
	private String[] axles;
	private String[] classes;
	private String equipment;
	private String period;
	private String startDate; 
	private String endDate;
	private String year;
	private String month;	
	
	public ReportSelection() {}


	public String[] getEquipments() {
		return equipments;
	}


	public void setEquipments(String[] equipments) {
		this.equipments = equipments;
	}


	public String[] getVehicles() {
		return vehicles;
	}


	public void setVehicles(String[] vehicles) {
		this.vehicles = vehicles;
	}


	public String[] getDirections() {
		return directions;
	}


	public void setDirections(String[] directions) {
		this.directions = directions;
	}


	public String[] getAxles() {
		return axles;
	}


	public void setAxles(String[] axles) {
		this.axles = axles;
	}


	public String[] getClasses() {
		return classes;
	}


	public void setClasses(String[] classes) {
		this.classes = classes;
	}


	public String getEquipment() {
		return equipment;
	}


	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}


	public String getPeriod() {
		return period;
	}


	public void setPeriod(String period) {
		this.period = period;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}

	
}
