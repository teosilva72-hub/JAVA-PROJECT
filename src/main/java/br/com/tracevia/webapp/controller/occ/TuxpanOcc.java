package br.com.tracevia.webapp.controller.occ;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import br.com.tracevia.webapp.model.occ.TuxpanOccModel;

@ManagedBean(name="OccController")
public class TuxpanOcc{
	@PostConstruct
	public void init() {
		data = new TuxpanOccModel();
	}
	private TuxpanOccModel data;
	public TuxpanOccModel getData() {
		return data;
	}
	public void setData(TuxpanOccModel data) {
		this.data = data;
	}
	
}