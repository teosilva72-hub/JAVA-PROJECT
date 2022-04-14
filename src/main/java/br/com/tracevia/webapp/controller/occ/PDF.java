package br.com.tracevia.webapp.controller.occ;

import br.com.tracevia.webapp.model.occ.TuxpanOccModel;

public class PDF {

	public String selectPdf(String id, TuxpanOccModel data) {
		System.out.println(data);
		if(data.getType_report().equals("1"))
			pdfOcc(data);
		else if(data.getType_report().equals("2"))
			pdfSin(data);
		//else
			//msg erro
		
		
		return id;
	}

	public boolean pdfOcc(TuxpanOccModel data) {
		System.out.println(data.getType_report());
		return check;
	}

	public boolean pdfSin(TuxpanOccModel data) {
		System.out.println(data.getType_report());
		return check;
	}

	private boolean check = false;
}