package br.com.tracevia.webapp.cfg;

public enum NotificationsTypeEnum {
	
	CFTV("CFTV"),
	COLAS("COLAS"),
	COMMS("COMMS"),
	DAI("DAI"),
	LPR("LPR"),
	MTO("MTO"),
	OCC("OCC"),
	PMV("PMV"),
	SAT("SAT"),
	SOS("SOS"),
	SPEED("SPEED"), 
	VIDEOWALL("VIDEOWALL"),
	WIM("WIM");
		
	 private String type; 	 
	 
    NotificationsTypeEnum(String type) {
        this.type = type;
    }
 
    public String getType() {
        return type;
    }	


}
