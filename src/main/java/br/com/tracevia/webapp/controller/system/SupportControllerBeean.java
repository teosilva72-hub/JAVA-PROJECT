

package br.com.tracevia.webapp.controller.system;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import br.com.tracevia.webapp.dao.system.SupportDAO;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.occ.OccurrencesData;
import br.com.tracevia.webapp.model.system.Support;
import br.com.tracevia.webapp.util.EmailUtil;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name="supportController")
public class SupportControllerBeean {
		
	

	FacesContext facesContext;
	ExternalContext externalContext;
	
	
	private Support data;
	SupportDAO dao;
	
	public Support getData() {
		return data;
	}

	public void setData(Support data) {
		this.data = data;
	}


	private List<Support> supports;

	public List<Support> getSupports() {
		return supports;
	}

	public void setSupports(List<Support> supports) {
		this.supports = supports;
	}	
	
	
	public void cadastrar() throws Exception {
		
		boolean sucess = false;
		
		SupportDAO dao = new SupportDAO();
		
		supports = dao.listarSupport(); // List occurrences
		
			}
@PostConstruct
	public void init() {
	
	
	try {
		cadastrar();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	

	public void save() {
		
		boolean save = false , email=false;	
		
		facesContext = FacesContext.getCurrentInstance(); // instânciar objeto facesContext
		externalContext = facesContext.getExternalContext();
	
		Map<String, String> params = externalContext.getRequestParameterMap(); 
				
			SupportDAO dao = new SupportDAO(); 
			Support support = new Support();
		
			support.setUsername((String)SessionUtil.getParam("user")); 
			support.setRoad_concessionaire(RoadConcessionaire.roadConcessionaire);
			support.setEmail(params.get("email"));
			support.setCategory(params.get("category"));
			support.setOthers(params.get("others"));
			support.setSubject(params.get("subject"));
			support.setMessage(params.get("message"));
			
			save = dao.save(support);
				
			if(save) {	
				
				email=EmailUtil.sendEmailHtml("wellington.silva@tracevia.com.br",support.getSubject(), support.getMessage());
				
				if(email)
				  RequestContext.getCurrentInstance().execute("$('#success').removeClass('d-none').html('Mensagem enviada com sucesso'); setTimeout(function () {$('#success').addClass('d-none').html('')}, 3000)");
		   
				else 
					
					RequestContext.getCurrentInstance().execute("$('#error').removeClass('d-none').html('Erro ao enviar mensagem'); setTimeout(function () {$('#error').addClass('d-none').html('')}, 3000)");
		     	
		     	
			} else {
				
				RequestContext.getCurrentInstance().execute("$('#error').removeClass('d-none').html('Erro r"
							+ "egistrar dados'); setTimeout(function () {$('#error').addClass('d-none').html('')}, 3000)");
	}}
	


	}