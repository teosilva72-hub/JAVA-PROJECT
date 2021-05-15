package br.com.tracevia.webapp.model.wim;

public class WimData{
	private int id;
	private String serialNumber;
	private String data;
	private String hour;
	private String classe;
	private String numberAxes;
	private String tableAxes;
	private String speed;
	private String pbtTotal;
	private String size;
	private String weight;
	private String dstAxes;

	public WimData(int id, String serialNumber, String data, String hour, String classe, String numberAxes, String tableAxes,
			String speed, String pbtTotal, String size, String weight, String dstAxes) {
		this.id = id;
		this.serialNumber = serialNumber;
		this.data = data;
		this.hour = hour;
		this.classe = classe;
		this.numberAxes = numberAxes;
		this.tableAxes = tableAxes;
		this.speed = speed;
		this.pbtTotal = pbtTotal;
		this.size = size;
		this.weight = weight;
		this.dstAxes = dstAxes;
	}
	public WimData() {
		
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getNumberAxes() {
		return numberAxes;
	}
	public void setNumberAxes(String numberAxes) {
		this.numberAxes = numberAxes;
	}
	public String getTableAxes() {
		return tableAxes;
	}
	public void setTableAxes(String tableAxes) {
		this.tableAxes = tableAxes;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getPbtTotal() {
		return pbtTotal;
	}
	public void setPbtTotal(String pbtTotal) {
		this.pbtTotal = pbtTotal;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getDstAxes() {
		return dstAxes;
	}
	public void setDstAxes(String dstAxes) {
		this.dstAxes = dstAxes;
	}

}