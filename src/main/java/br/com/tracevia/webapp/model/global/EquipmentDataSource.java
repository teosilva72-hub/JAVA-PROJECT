package br.com.tracevia.webapp.model.global;

public class EquipmentDataSource {
	
	private int moduleID;	
	private int equipId;	
	private int configId;	
	private int dmsDriver;
	private int port; 
	private int model;
	private int numLanes;
	private int width;
	private double latitude;
	private double longitude;
	private String table;			
	private String datetime;	
	private String username;		
	private String equipName;
	private String ipAddress; 
	private String ipAddressIndicator;
	private String ipAddressRadar;
	private String city;	
	private String road;	
	private String equipType;									
	private String km;			
	private String direction;
	private String directionTo;
	private String sip;
	private String serviceFlow;
	private String lane1;
	private String lane2;
	private String lane3;
	private String lane4;
	private String lane5;
	private String lane6;
	private String lane7;
	private String lane8;
	
	public EquipmentDataSource(int moduleID, int equipId, int configId, int dmsDriver, int port, int model, int numLanes, int width,
			double latitude, double longitude, String table, String datetime, String username, String equipName,
			String ipAddress, String ipAddressIndicator, String ipAddressRadar, String city, String road,
			String equipType, String km, String direction, String directionTo, String sip, String serviceFlow, String lane1, String lane2, String lane3,
			String lane4, String lane5, String lane6, String lane7, String lane8) {
			
		this.moduleID = moduleID;
		this.equipId = equipId;
		this.configId = configId;
		this.dmsDriver = dmsDriver;
		this.port = port;
		this.model = model;
		this.numLanes = numLanes;
		this.width = width;
		this.latitude = latitude;
		this.longitude = longitude;
		this.table = table;
		this.datetime = datetime;
		this.username = username;
		this.equipName = equipName;
		this.ipAddress = ipAddress;
		this.ipAddressIndicator = ipAddressIndicator;
		this.ipAddressRadar = ipAddressRadar;
		this.city = city;
		this.road = road;
		this.equipType = equipType;
		this.km = km;
		this.direction = direction;
		this.directionTo = directionTo;
		this.sip = sip;
		this.serviceFlow = serviceFlow;
		this.lane1 = lane1;
		this.lane2 = lane2;
		this.lane3 = lane3;
		this.lane4 = lane4;
		this.lane5 = lane5;
		this.lane6 = lane6;
		this.lane7 = lane7;
		this.lane8 = lane8;
	}
		
	public EquipmentDataSource() {}

	public int getModuleID() {
		return moduleID;
	}

	public void setModuleID(int moduleID) {
		this.moduleID = moduleID;
	}


	public int getEquipId() {
		return equipId;
	}


	public void setEquipId(int equipId) {
		this.equipId = equipId;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public int getDmsDriver() {
		return dmsDriver;
	}


	public void setDmsDriver(int dmsDriver) {
		this.dmsDriver = dmsDriver;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public int getModel() {
		return model;
	}


	public void setModel(int model) {
		this.model = model;
	}


	public int getNumLanes() {
		return numLanes;
	}


	public void setNumLanes(int numLanes) {
		this.numLanes = numLanes;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public String getTable() {
		return table;
	}


	public void setTable(String table) {
		this.table = table;
	}


	public String getDatetime() {
		return datetime;
	}


	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEquipName() {
		return equipName;
	}


	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public String getIpAddressIndicator() {
		return ipAddressIndicator;
	}


	public void setIpAddressIndicator(String ipAddressIndicator) {
		this.ipAddressIndicator = ipAddressIndicator;
	}


	public String getIpAddressRadar() {
		return ipAddressRadar;
	}


	public void setIpAddressRadar(String ipAddressRadar) {
		this.ipAddressRadar = ipAddressRadar;
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


	public String getEquipType() {
		return equipType;
	}


	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}


	public String getKm() {
		return km;
	}


	public void setKm(String km) {
		this.km = km;
	}


	public String getDirection() {
		return direction;
	}


	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public String getDirectionTo() {
		return directionTo;
	}

	public void setDirectionTo(String directionTo) {
		this.directionTo = directionTo;
	}

	public String getSip() {
		return sip;
	}


	public void setSip(String sip) {
		this.sip = sip;
	}
	
	public String getServiceFlow() {
		return serviceFlow;
	}

	public void setServiceFlow(String serviceFlow) {
		this.serviceFlow = serviceFlow;
	}

	public String getLane1() {
		return lane1;
	}


	public void setLane1(String lane1) {
		this.lane1 = lane1;
	}


	public String getLane2() {
		return lane2;
	}


	public void setLane2(String lane2) {
		this.lane2 = lane2;
	}


	public String getLane3() {
		return lane3;
	}


	public void setLane3(String lane3) {
		this.lane3 = lane3;
	}


	public String getLane4() {
		return lane4;
	}


	public void setLane4(String lane4) {
		this.lane4 = lane4;
	}


	public String getLane5() {
		return lane5;
	}


	public void setLane5(String lane5) {
		this.lane5 = lane5;
	}


	public String getLane6() {
		return lane6;
	}


	public void setLane6(String lane6) {
		this.lane6 = lane6;
	}


	public String getLane7() {
		return lane7;
	}


	public void setLane7(String lane7) {
		this.lane7 = lane7;
	}


	public String getLane8() {
		return lane8;
	}


	public void setLane8(String lane8) {
		this.lane8 = lane8;
	}
		

}
