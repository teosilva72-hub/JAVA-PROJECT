package br.com.tracevia.webapp.controller.occ;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import br.com.tracevia.webapp.dao.occ.TuxpanDAO;
import br.com.tracevia.webapp.model.occ.TuxpanOccModel;

@ManagedBean(name="OccController")
public class TuxpanOcc{
	
	private TuxpanOccModel data;
	public TuxpanOccModel getData() {
		return data;
	}
	public void setData(TuxpanOccModel data) {
		this.data = data;
	}
	
	@PostConstruct
	public void init() {
		data = new TuxpanOccModel();
	}
	public void saveOcc() {
		
		TuxpanDAO dao = new TuxpanDAO();
		System.out.println(data.getPlz_cobro());
		System.out.println(data.getFolio_sec());
		System.out.println(data.getReporte());
		System.out.println(data.getSiniestro());
		System.out.println(data.getFecha());
		System.out.println(data.getHora());

		try {
			
			dao.registerOcc(data);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}