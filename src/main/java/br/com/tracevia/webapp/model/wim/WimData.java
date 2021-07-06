package br.com.tracevia.webapp.model.wim;

public class WimData{
	
	private int id;
	private boolean status;
	private String serialNumber;
	private String datetime;
	private String classe;
	private String numberAxes;
	private String tableAxes;
	private String speed;
	private String pbtTotal;
	private String size;
	private String weight;
	private String dstAxes;
	private String axlNumber;
	private String seqN;	
	private String[] axlWeight, axlDist, axlType;	
			

	public WimData(int id, boolean status, String serialNumber, String datetime, String classe, String numberAxes,
			String tableAxes, String speed, String pbtTotal, String size, String weight, String dstAxes,
			String axlNumber, String seqN, String[] axlWeight, String[] axlDist, String[] axlType) {
		
		this.id = id;
		this.status = status;
		this.serialNumber = serialNumber;
		this.datetime = datetime;
		this.classe = classe;
		this.numberAxes = numberAxes;
		this.tableAxes = tableAxes;
		this.speed = speed;
		this.pbtTotal = pbtTotal;
		this.size = size;
		this.weight = weight;
		this.dstAxes = dstAxes;
		this.axlNumber = axlNumber;
		this.seqN = seqN;
		this.axlWeight = axlWeight;
		this.axlDist = axlDist;
		this.axlType = axlType;
	}

	public WimData() {}

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

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
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

	public String getAxlNumber() {
		return axlNumber;
	}

	public void setAxlNumber(String axlNumber) {
		this.axlNumber = axlNumber;
	}

	public String getSeqN() {
		return seqN;
	}

	public void setSeqN(String seqN) {
		this.seqN = seqN;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String[] getAxlWeight() {
		return axlWeight;
	}

	public void setAxlWeight(String[] axlWeight) {
		this.axlWeight = axlWeight;
	}

	public String[] getAxlDist() {
		return axlDist;
	}

	public void setAxlDist(String[] axlDist) {
		this.axlDist = axlDist;
	}

	public String[] getAxlType() {
		return axlType;
	}

	public void setAxlType(String[] axlType) {
		this.axlType = axlType;
	}

}