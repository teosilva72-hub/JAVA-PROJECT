package br.com.tracevia.webapp.cfg;

public enum RoadConcessionairesEnum {
	
	EcoviasAraguaia("Ecovias Araguaia"),
	Ausn("AUSN"),
	BahiaNorte("Bahia Norte"),
	CardelPozaRica("Cardel Poza Rica"),
	LitoralSul("Litoral Sul"),
	Tuxpan("Tuxpan Tampico"),
	ViaAmericas("Via Americas"),
	ViaPaulista("Via Paulista"),
	ViaRondon("Via Rondon"),
	ViaSul("Via Sul"),
	Eco101("Eco 101"),
	Tracevia("Tracevia");
			
	private String concessionaire; 	 
	 
    RoadConcessionairesEnum(String concessionaire) {
        this.concessionaire = concessionaire;
    }
 
    public String getConcessionaire() {
        return concessionaire;
    }	

}
