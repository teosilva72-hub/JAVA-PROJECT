package br.com.tracevia.webapp.controller.ocr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean(name="OcrDetails")
public class OcrDetails{
	private String noImage;

	public String getNoImage() {
		try {
			Path path = Paths.get(noImage);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		} catch (IOException e) {
			return "";
		}
	}
	public void setNoImage(String noImage) {
		this.noImage = noImage;
	}


	@PostConstruct
	public void initialize() {
		noImage = "C:\\teste\\tracevia.jpg";
	System.out.println("aqui");
	}
}