package br.com.tracevia.webapp.cfg;

public enum RoadConcessionairesEnum {
	
	AlternativasViales("Alternativas Viales"),
	Ausn("AUSN"),
	BahiaNorte("Bahia Norte"),
	CardelPozaRica("Cardel Poza Rica"),
	Eco101("Eco 101"),
	EcoviasAraguaia("Ecovias Araguaia"),
	LitoralSul("Litoral Sul"),
	Tuxpan("Tuxpan Tampico"),
	ViaAmericas("Via Americas"),
	ViaPaulista("Via Paulista"),
	ViaRondon("Via Rondon"),
	ViaSul("Via Sul"),	
	Tracevia("Tracevia"),
	tester("Tester");
			
	private String concessionaire; 	 
	 
    RoadConcessionairesEnum(String concessionaire) {
        this.concessionaire = concessionaire;
    }
 
    public String getConcessionaire() {
        return concessionaire;
    }	

}
