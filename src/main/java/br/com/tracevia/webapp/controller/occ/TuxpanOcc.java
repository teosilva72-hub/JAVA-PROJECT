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
	public boolean saveOcc() {
		boolean checked = false;
		TuxpanDAO dao = new TuxpanDAO();
		try {
			String check = dao.registerOcc(data);
			if(check.equals("null")) {
				checked = false;
				System.out.println(checked);
			}else {
				checked = true;
				System.out.println(checked);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checked;
	}	
}