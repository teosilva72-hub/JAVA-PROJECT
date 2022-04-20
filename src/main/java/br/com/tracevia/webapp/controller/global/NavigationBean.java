package br.com.tracevia.webapp.controller.global;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "navigationController", eager = true)
@RequestScoped
public class NavigationBean implements Serializable {
		
	/**
	 * SERIAL ID
	 */
	private static final long serialVersionUID = 1L;
	
	private String navigation_page;
	private String sidebar_page;
		
	public String getNavigation_page() {
		return navigation_page;
	}

	public void setNavigation_page(String navigation_page) {
		this.navigation_page = navigation_page;
	}

	public String getSidebar_page() {
		return sidebar_page;
	}

	public void setSidebar_page(String sidebar_page) {
		this.sidebar_page = sidebar_page;
	}

	@PostConstruct
	public void initialize(){
		
		sidebarSource();

	}
	
	// ----------------------------------------------------------------
	
	public String redirectToURL(String URL) {

		navigation_page = URL.concat("?faces-redirect=true");		
		
		return navigation_page;
	}
		
	// ----------------------------------------------------------------
		
    /* SAT REPORTS */

	public String sidebarSource() {

		sidebar_page = "";

		FacesContext context = FacesContext.getCurrentInstance();		  
		int role = (int) context.getExternalContext().getSessionMap().get("nivel");

		switch(role) {

		case 1:
		case 6: sidebar_page = "/template/sidebar/sidebar-admin.xhtml" ; break;

		case 2:
		case 3:
		case 4:
		case 5:
		case 7:   
		case 8:  
		case 9: sidebar_page = "/template/sidebar/sidebar-user.xhtml" ; break;	   

		}

		return sidebar_page;

	}

	
	// ----------------------------------------------------------------
		
}
