package br.com.tracevia.webapp.model.global;

public class VBV {
	
	private int vbv_id;
	private int siteID;	
	private int seqG;
	private int seqN;	
	private int axlNumber;
	private int gross;
	private int picDiff;
	private int speed;
	private int lane;
	private int deltaD1;
	private int deltaT1;
	private int axlT1;
	private int deltaD2;
	private int deltaT2;
	private int axlT2;
	private int temperature;	
	private int axl1W;
	private int axl2W;
	private int axl3W;
	private int axl4W;
	private int axl5W;
	private int axl6W;
	private int axl7W;
	private int axl8W;
	private int axl9W;
	private int axl10W;		
	private int axl2D;
	private int axl3D;
	private int axl4D;
	private int axl5D;
	private int axl6D;
	private int axl7D;
	private int axl8D;
	private int axl9D;
	private int axl10D;	
	private String datetime;
	private String date;
	private String interval;
	private String classDnit;
	private String classVBV;
	private String plate;
	
	public VBV(int vbv_id, int siteID, int seqG, int seqN, int axlNumber, int gross, int picDiff, int speed, int lane,
			int deltaD1, int deltaT1, int axlT1, int deltaD2, int deltaT2, int axlT2, int temperature, int axl1w,
			int axl2w, int axl3w, int axl4w, int axl5w, int axl6w, int axl7w, int axl8w, int axl9w, int axl10w,
			int axl2d, int axl3d, int axl4d, int axl5d, int axl6d, int axl7d, int axl8d, int axl9d, int axl10d,
			String datetime, String date, String interval, String classDnit, String classVBV, String plate) {
	
		this.vbv_id = vbv_id;
		this.siteID = siteID;
		this.seqG = seqG;
		this.seqN = seqN;
		this.axlNumber = axlNumber;
		this.gross = gross;
		this.picDiff = picDiff;
		this.speed = speed;
		this.lane = lane;
		this.deltaD1 = deltaD1;
		this.deltaT1 = deltaT1;
		this.axlT1 = axlT1;
		this.deltaD2 = deltaD2;
		this.deltaT2 = deltaT2;
		this.axlT2 = axlT2;
		this.temperature = temperature;
		axl1W = axl1w;
		axl2W = axl2w;
		axl3W = axl3w;
		axl4W = axl4w;
		axl5W = axl5w;
		axl6W = axl6w;
		axl7W = axl7w;
		axl8W = axl8w;
		axl9W = axl9w;
		axl10W = axl10w;
		axl2D = axl2d;
		axl3D = axl3d;
		axl4D = axl4d;
		axl5D = axl5d;
		axl6D = axl6d;
		axl7D = axl7d;
		axl8D = axl8d;
		axl9D = axl9d;
		axl10D = axl10d;
		this.datetime = datetime;
		this.date = date;
		this.interval = interval;
		this.classDnit = classDnit;
		this.classVBV = classVBV;
		this.plate = plate;
	}

	public VBV() {}

	public int getVbv_id() {
		return vbv_id;
	}

	public void setVbv_id(int vbv_id) {
		this.vbv_id = vbv_id;
	}

	public int getSiteID() {
		return siteID;
	}

	public void setSiteID(int siteID) {
		this.siteID = siteID;
	}

	public int getSeqG() {
		return seqG;
	}

	public void setSeqG(int seqG) {
		this.seqG = seqG;
	}

	public int getSeqN() {
		return seqN;
	}

	public void setSeqN(int seqN) {
		this.seqN = seqN;
	}

	public int getAxlNumber() {
		return axlNumber;
	}

	public void setAxlNumber(int axlNumber) {
		this.axlNumber = axlNumber;
	}

	public int getGross() {
		return gross;
	}

	public void setGross(int gross) {
		this.gross = gross;
	}

	public int getPicDiff() {
		return picDiff;
	}

	public void setPicDiff(int picDiff) {
		this.picDiff = picDiff;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getLane() {
		return lane;
	}

	public void setLane(int lane) {
		this.lane = lane;
	}

	public int getDeltaD1() {
		return deltaD1;
	}

	public void setDeltaD1(int deltaD1) {
		this.deltaD1 = deltaD1;
	}

	public int getDeltaT1() {
		return deltaT1;
	}

	public void setDeltaT1(int deltaT1) {
		this.deltaT1 = deltaT1;
	}

	public int getAxlT1() {
		return axlT1;
	}

	public void setAxlT1(int axlT1) {
		this.axlT1 = axlT1;
	}

	public int getDeltaD2() {
		return deltaD2;
	}

	public void setDeltaD2(int deltaD2) {
		this.deltaD2 = deltaD2;
	}

	public int getDeltaT2() {
		return deltaT2;
	}

	public void setDeltaT2(int deltaT2) {
		this.deltaT2 = deltaT2;
	}

	public int getAxlT2() {
		return axlT2;
	}

	public void setAxlT2(int axlT2) {
		this.axlT2 = axlT2;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getAxl1W() {
		return axl1W;
	}

	public void setAxl1W(int axl1w) {
		axl1W = axl1w;
	}

	public int getAxl2W() {
		return axl2W;
	}

	public void setAxl2W(int axl2w) {
		axl2W = axl2w;
	}

	public int getAxl3W() {
		return axl3W;
	}

	public void setAxl3W(int axl3w) {
		axl3W = axl3w;
	}

	public int getAxl4W() {
		return axl4W;
	}

	public void setAxl4W(int axl4w) {
		axl4W = axl4w;
	}

	public int getAxl5W() {
		return axl5W;
	}

	public void setAxl5W(int axl5w) {
		axl5W = axl5w;
	}

	public int getAxl6W() {
		return axl6W;
	}

	public void setAxl6W(int axl6w) {
		axl6W = axl6w;
	}

	public int getAxl7W() {
		return axl7W;
	}

	public void setAxl7W(int axl7w) {
		axl7W = axl7w;
	}

	public int getAxl8W() {
		return axl8W;
	}

	public void setAxl8W(int axl8w) {
		axl8W = axl8w;
	}

	public int getAxl9W() {
		return axl9W;
	}

	public void setAxl9W(int axl9w) {
		axl9W = axl9w;
	}

	public int getAxl10W() {
		return axl10W;
	}

	public void setAxl10W(int axl10w) {
		axl10W = axl10w;
	}

	public int getAxl2D() {
		return axl2D;
	}

	public void setAxl2D(int axl2d) {
		axl2D = axl2d;
	}

	public int getAxl3D() {
		return axl3D;
	}

	public void setAxl3D(int axl3d) {
		axl3D = axl3d;
	}

	public int getAxl4D() {
		return axl4D;
	}

	public void setAxl4D(int axl4d) {
		axl4D = axl4d;
	}

	public int getAxl5D() {
		return axl5D;
	}

	public void setAxl5D(int axl5d) {
		axl5D = axl5d;
	}

	public int getAxl6D() {
		return axl6D;
	}

	public void setAxl6D(int axl6d) {
		axl6D = axl6d;
	}

	public int getAxl7D() {
		return axl7D;
	}

	public void setAxl7D(int axl7d) {
		axl7D = axl7d;
	}

	public int getAxl8D() {
		return axl8D;
	}

	public void setAxl8D(int axl8d) {
		axl8D = axl8d;
	}

	public int getAxl9D() {
		return axl9D;
	}

	public void setAxl9D(int axl9d) {
		axl9D = axl9d;
	}

	public int getAxl10D() {
		return axl10D;
	}

	public void setAxl10D(int axl10d) {
		axl10D = axl10d;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getClassDnit() {
		return classDnit;
	}

	public void setClassDnit(String classDnit) {
		this.classDnit = classDnit;
	}

	public String getClassVBV() {
		return classVBV;
	}

	public void setClassVBV(String classVBV) {
		this.classVBV = classVBV;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}	
}
