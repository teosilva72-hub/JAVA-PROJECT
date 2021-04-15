package br.com.tracevia.webapp.cfg;

public enum ModulesEnum {
	
	CFTV("CFTV"),
	COLAS("COLAS"),
	COMMS("COMMS"),
	DAI("DAI"),
	LPR("LPR"),
	MTO("MTO"),
	OCC("OCC"),
	PMV("PMV"),
	RS("RS"),
	SAT("SAT"),
	SOS("SOS"),
	SPEED("SPEED"),
	VS("VS"),
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
