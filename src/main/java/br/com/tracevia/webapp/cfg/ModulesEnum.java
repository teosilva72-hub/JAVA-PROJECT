package br.com.tracevia.webapp.cfg;

public enum ModulesEnum {
	
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
	SV("SV"),
	VIDEOWALL("VIDEOWALL"),
	WIM("WIM");
		
	 private String module; 	 
	 
    ModulesEnum(String module) {
        this.module = module;
    }
 
    public String getModule() {
        return module;
    }	

}
