package br.com.tracevia.webapp.model.ocr;

public class OcrData{
	private String id;
	private String dataHour;
	private String cam;
	private String placa;
	
	public OcrData(String id, String dataHour, String cam, String placa) {
		this.id = id;
		this.dataHour = dataHour;
		this.cam = cam;
		this.placa = placa;
	}
	public OcrData() {
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataHour() {
		return dataHour;
	}
	public void setDataHour(String dataHour) {
		this.dataHour = dataHour;
	}
	public String getCam() {
		return cam;
	}
	public void setCam(String cam) {
		this.cam = cam;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
}