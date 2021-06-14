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
	private String seqN, axlNumber, axl1W, axl2W, axl3W, axl4W, axl5W, axl6W, axl7W, axl8W, axl9W,
	axl2D, axl3D, axl4D, axl5D, axl6D, axl7D, axl8D, axl9D;
	
	private boolean status;

	public WimData(int id, String serialNumber, String data, String hour, String classe, String numberAxes, String tableAxes,
			String speed, String pbtTotal, String size, String weight, String dstAxes, boolean status, String seqN, String axlNumber,
			String axl1W, String axl2W, String axl3W, String axl4W, String axl5W, String axl6W, String axl7W, String axl8W, String axl9W,
			String axl2D, String axl3D, String axl4D, String axl5D, String axl6D, String axl7D, String axl8D, String axl9D) {
		this.axl1W = axl1W;
		this.axl2W = axl2W;
		this.axl3W = axl3W;
		this.axl4W = axl4W;
		this.axl5W = axl5W;
		this.axl6W = axl6W;
		this.axl7W = axl7W;
		this.axl8W = axl8W;
		this.axl9W = axl9W;
		this.axl2D = axl2D;
		this.axl3D = axl3D;
		this.axl4D = axl4D;
		this.axl5D = axl5D;
		this.axl6D = axl6D;
		this.axl7D = axl7D;
		this.axl8D = axl8D;
		this.axl9D = axl9D;
		this.seqN = seqN;
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
		this.status = status;
	}
	public WimData() {
		
	}
	
	public String getSeqN() {
		return seqN;
	}
	public void setSeqN(String seqN) {
		this.seqN = seqN;
	}
	public String getAxlNumber() {
		return axlNumber;
	}
	public void setAxlNumber(String axlNumber) {
		this.axlNumber = axlNumber;
	}
	public String getAxl1W() {
		return axl1W;
	}
	public void setAxl1W(String axl1w) {
		axl1W = axl1w;
	}
	public String getAxl2W() {
		return axl2W;
	}
	public void setAxl2W(String axl2w) {
		axl2W = axl2w;
	}
	public String getAxl3W() {
		return axl3W;
	}
	public void setAxl3W(String axl3w) {
		axl3W = axl3w;
	}
	public String getAxl4W() {
		return axl4W;
	}
	public void setAxl4W(String axl4w) {
		axl4W = axl4w;
	}
	public String getAxl5W() {
		return axl5W;
	}
	public void setAxl5W(String axl5w) {
		axl5W = axl5w;
	}
	public String getAxl6W() {
		return axl6W;
	}
	public void setAxl6W(String axl6w) {
		axl6W = axl6w;
	}
	public String getAxl7W() {
		return axl7W;
	}
	public void setAxl7W(String axl7w) {
		axl7W = axl7w;
	}
	public String getAxl8W() {
		return axl8W;
	}
	public void setAxl8W(String axl8w) {
		axl8W = axl8w;
	}
	public String getAxl9W() {
		return axl9W;
	}
	public void setAxl9W(String axl9w) {
		axl9W = axl9w;
	}
	public String getAxl2D() {
		return axl2D;
	}
	public void setAxl2D(String axl2d) {
		axl2D = axl2d;
	}
	public String getAxl3D() {
		return axl3D;
	}
	public void setAxl3D(String axl3d) {
		axl3D = axl3d;
	}
	public String getAxl4D() {
		return axl4D;
	}
	public void setAxl4D(String axl4d) {
		axl4D = axl4d;
	}
	public String getAxl5D() {
		return axl5D;
	}
	public void setAxl5D(String axl5d) {
		axl5D = axl5d;
	}
	public String getAxl6D() {
		return axl6D;
	}
	public void setAxl6D(String axl6d) {
		axl6D = axl6d;
	}
	public String getAxl7D() {
		return axl7D;
	}
	public void setAxl7D(String axl7d) {
		axl7D = axl7d;
	}
	public String getAxl8D() {
		return axl8D;
	}
	public void setAxl8D(String axl8d) {
		axl8D = axl8d;
	}
	public String getAxl9D() {
		return axl9D;
	}
	public void setAxl9D(String axl9d) {
		axl9D = axl9d;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
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