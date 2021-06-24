package br.com.tracevia.webapp.controller.ocr;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="ocrBean")
@ViewScoped
public class Ocr_bean {
	private String[] dados;
	public String[] getDados() {
		return dados;
	}
	public void setDados(String[] dados) {
		this.dados = dados;
	}
	@PostConstruct
	public void initalize(){
		System.out.println("passamos");
		info();
		
	}
	public void info() {
		System.out.println("Chegamos");
		dados = new String [3];
		dados [0] = "1525";
		dados [1] = "2021-01-01 15:01:25";
		dados [2] = "CAM3";
		dados [3] = "CXD-0025";
	}
}