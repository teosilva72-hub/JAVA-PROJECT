package br.com.tracevia.webapp.controller.globalPDF;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.faces.bean.ManagedBean;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

@ManagedBean(name="GlobalPDF")
public class GlobalPDF {
	public Document merge(List<byte[]> files) {
		
		Document document = new Document();
		
		try {
			PdfCopy copy = new PdfCopy(document, new ByteArrayOutputStream());
	
	        document.open();
	        for (byte[] file : files){
	            PdfReader reader = new PdfReader(file);
	            copy.addDocument(reader);
	            copy.freeReader(reader);
	            reader.close();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
		
		return document;
	}
}
