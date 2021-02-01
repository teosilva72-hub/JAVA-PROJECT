package br.com.tracevia.webapp.cfg;

public enum RoadConcessionairesEnum {
	
	BahiaNorte("Bahia Norte"),
	CardelPozaRica("Cardel Poza Rica"),
	LitoralSul("Litoral Sul"),
	Tuxpan("Tuxpan Tampico"),
	ViaPaulista("Via Paulista"),
	ViaRondon("Via Rondon"),
	ViaSul("Via Sul"),
	Tracevia("Tracevia");
			
	private String concessionaire; 	 
	 
    RoadConcessionairesEnum(String concessionaire) {
        this.concessionaire = concessionaire;
    }
 
    public String getConcessionaire() {
        return concessionaire;
    }	

}
