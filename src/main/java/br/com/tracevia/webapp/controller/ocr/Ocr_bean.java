package br.com.tracevia.webapp.controller.ocr;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="ocrBean")
@ViewScoped
public class Ocr_bean {
	private String[] dados;
	private String path;
	public String[] getDados() {
		return dados;
	}
	public void setDados(String[] dados) {
		this.dados = dados;
	}
	
	@PostConstruct
	public void initialize(){
		info();
	}
	public void image() {
		
	}
	public void info() {
		System.out.println("Chegamos");
		dados = new String [4];
		dados [0] = "1525";
		dados [1] = "2021-01-01 15:01:25";
		dados [2] = "CAM3";
		dados [3] = "CXD-0025";
	}
}