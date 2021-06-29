package br.com.tracevia.webapp.controller.ocr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.ocr.OcrDao;
import br.com.tracevia.webapp.model.ocr.OcrData;

@ManagedBean(name="OcrRealtime")
@ViewScoped
public class OcrBean{
	private String[] dado;
	private String filterCam;
	private List<SelectItem> cam;
	private OcrData data;
	private OcrData date = new OcrData();
	private OcrDao dao = new OcrDao();
	public List<SelectItem> getCam() {
		return cam;
	}
	public void setCam(List<SelectItem> cam) {
		this.cam = cam;
	}
	public String getFilterCam() {
		return filterCam;
	}
	public void setFilterCam(String filterCam) {
		this.filterCam = filterCam;
	}
	public String[] getDado() {
		return dado;
	}
	public void setDado(String[] dado) {
		this.dado = dado;
	}
	public List<OcrData> list;

	public List<OcrData> getList() {
		return list;
	}
	public void setList(List<OcrData> list) {
		this.list = list;
	}
	@PostConstruct
	public void initialize() {
		RequestContext.getCurrentInstance().execute("filtro()");

		System.out.println("Inicialização");
		updateView();

		//filtro câmera
		cam = new ArrayList<SelectItem>();
		cam.add(new SelectItem("Todos"));
		cam.add(new SelectItem("ocr_1"));
		cam.add(new SelectItem("ocr_2"));
		cam.add(new SelectItem("ocr_3"));
		cam.add(new SelectItem("ocr_4"));
		cam.add(new SelectItem("ocr_5"));
		cam.add(new SelectItem("ocr_6"));
		//final filtro câmera
		try {
			data = dao.lastRegister();
			dado = new String [4];
			dado[0] = data.getId();
			dado[1] = data.getDataHour();
			dado[2] = data.getCam();
			dado[3] = data.getPlaca();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void updateView() {

		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().execute("updateView()");
		System.out.println("atualizando");
		System.out.println(getFilterCam());
		try {

			String cam = getFilterCam();
			if(cam == null || cam.equals("Todos")) {
				data = dao.lastRegister();
				dado = new String [4];
				dado[0] = data.getId();
				dado[1] = data.getDataHour();
				dado[2] = data.getCam();
				dado[3] = data.getPlaca();
			}else {
				data = dao.searchCam(cam);
				dado = new String [4];
				dado[0] = data.getId();
				dado[1] = data.getDataHour();
				dado[2] = data.getCam();
				dado[3] = data.getPlaca();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void setFilter() {
		String x = getFilterCam();
		System.out.println("aquie");
		try {
			dao.searchCam(x);
			dado = new String [4];
			dado[0] = data.getId();
			dado[1] = data.getDataHour();
			dado[2] = data.getCam();
			dado[3] = data.getPlaca();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}