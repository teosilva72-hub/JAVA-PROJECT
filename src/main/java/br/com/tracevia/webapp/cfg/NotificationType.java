package br.com.tracevia.webapp.cfg;

public enum NotificationType {
	
	CFTV("CFTV"),
	COLAS("COLAS"),
	COMMS("COMMS"),
	DAI("DAI"),
	OCR("OCR"),
	MTO("MTO"),
	OCC("OCC"),
	PMV("PMV"),
	SAT("SAT"),
	SOS("SOS"),
	SPEED("SPEED"), 
	VIDEOWALL("VIDEOWALL"),
	WIM("WIM");
		
	 private String type; 	 
	 
    NotificationType(String type) {
        this.type = type;
    }
 
    public String getType() {
        return type;
    }	


}
