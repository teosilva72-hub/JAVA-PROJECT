package br.com.tracevia.webapp.controller.globalPDF;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.faces.bean.ManagedBean;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;

@ManagedBean(name="GlobalPDF")
public class GlobalPDF {
	
	static List<URL> files = new ArrayList<URL>(Arrays.asList(
			GlobalPDF.class.getResource("/example-001.pdf"),
			GlobalPDF.class.getResource("/example-002.pdf"),
            GlobalPDF.class.getResource("/example-003.pdf")
    ));
	
	public byte[] main(byte[] args) {
		
		Document document = new Document();
		
		GlobalPDF.class.getResource("/example-001.pdf");
		//GlobalPDF.class.getResource("/example-002.pdf");
		try {
			
			PdfCopy copy = new PdfCopy(document, new FileOutputStream("merge-pdf-result.pdf"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return args;
	}
}
