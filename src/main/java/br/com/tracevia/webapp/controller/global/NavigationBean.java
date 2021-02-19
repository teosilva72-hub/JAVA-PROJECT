package br.com.tracevia.webapp.controller.global;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "navigationController", eager = true)
@RequestScoped
public class NavigationBean {
	
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
		
		redirectToDashboard();	
		sidebarSource();
		
	}
	
	public String redirectToDashboard() {
		
		navigation_page = "/dashboard/dashboard.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
	
    public String redirectToRealTime() {
		
		navigation_page = "/realtime/realtime.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
	
    /* USER INTERFACE */
    
    //DMS
    
    public String redirectToAvailableMessages() {
		
    	navigation_page = "/dms/messages/message-available.xhtml?faces-redirect=true";		
 		
 		return navigation_page;
 	}
    
    
    public String redirectToActiveMessages() {
		
 		navigation_page = "/dms/messages/message-activate.xhtml?faces-redirect=true";		
 		
 		return navigation_page;
 	}
    
    //MTO
    
     public String redirectToWeatherPanel() {
		
 		navigation_page = "/mto/panel/weather_panel.xhtml?faces-redirect=true";		
 		
 		return navigation_page;
 	}
    
    /* USER INTERFACE */
     
    /* USERS */
    
   public String redirectToRegister() {
		
 		navigation_page = "/users/register_user.xhtml?faces-redirect=true";		
 		
 		return navigation_page;
 	}
   
   public String redirectToUpdate() {
		
		navigation_page = "/users/update_user.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
   
   public String redirectToPanelUsers() {
		
 		navigation_page = "/users/panel_user.xhtml?faces-redirect=true";		
 		
 		return navigation_page;
 	}
   
   public String redirectToChangePassword() {
		
		navigation_page = "/reset/reset_password.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
   
   /* USERS */ 
   
     /* MTO REPORTS */
   
     public String redirectToWeatherYear() {
		
    	 navigation_page = "/mto/reports/weather_year.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
     
     public String redirectToWeatherMonth() {
 		
    	 navigation_page = "/mto/reports/weather_month.xhtml?faces-redirect=true";		
 		
 		return navigation_page;
 	}
     
     public String redirectToWeatherPeriod() {
 		
 		navigation_page = "/mto/reports/weather_periods.xhtml?faces-redirect=true";		
 		
 		return navigation_page;
 	}
    
     /* MTO REPORTS */
        
    /* SAT REPORTS */
   
   public String redirectToCountVehicles() {
		
		navigation_page = "/sat/reports/sat_vehicle_count.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
   
   public String redirectToCountVehiclesFlow() {
		
		navigation_page = "/sat/reports/sat_vehicle_count_flow.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
      
   public String redirectToMonthlyFlow() {
		
		navigation_page = "/sat/reports/sat_monthly_flow.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
   
   public String redirectToPeriodFlow() {
		
		navigation_page = "/sat/reports/sat_period_flow.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
  
   public String redirectToWeighing() {
		
		navigation_page = "/sat/reports/sat_weighing.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
   
   public String redirectToTypeAxle() {
		
		navigation_page = "/sat/reports/sat_axle_type.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
   
   public String redirectToTypeClass() {
		
		navigation_page = "/sat/reports/sat_class_type.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
   
   public String redirectToSpeed() {
		
		navigation_page = "/sat/reports/sat_speed.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
      
   public String redirectToVBVs() {
		
		navigation_page = "/sat/reports/sat_vbvs.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
   
   public String redirectToOccurrences() {
		
		navigation_page = "/occurrence/occurrences.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
   
   public String redirectToMap() {
		
		navigation_page = "/map/map.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
   
   //******************* CCR REPORTS **************************** //
   
   public String redirectToCCRClasses() {
		
 		navigation_page = "/sat/ccr_reports/ccr_classe.xhtml?faces-redirect=true";		
 		
 		return navigation_page;
 	}
    
   
   public String redirectToCCRTipo() {
		
		navigation_page = "/sat/ccr_reports/ccr_tipo.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
   
   
   public String redirectToCCRVelocidade() {
		
		navigation_page = "/sat/ccr_reports/ccr_velocidade.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
   
   public String redirectToCCRAllClasses() {
		
		navigation_page = "/sat/ccr_reports/ccr_all_classes.xhtml?faces-redirect=true";		
		
		return navigation_page;
	}
   
   
   
   
   
   
   
   //******************* CCR REPORTS **************************** //
   
   /* SAT REPORTS */
    
   public String sidebarSource() {
	   
	   sidebar_page = "";
	   	   	   
	   FacesContext context = FacesContext.getCurrentInstance();		  
	   int role = (int) context.getExternalContext().getSessionMap().get("nivel");
	   
	   switch(role) {
	   
	   case 1: sidebar_page = "/template/sidebar/sidebar-admin.xhtml" ; break;
	   case 2: sidebar_page = "/template/sidebar/sidebar-user.xhtml" ; break;	
	   case 3: sidebar_page = "/template/sidebar/sidebar-user.xhtml" ; break;	
	   case 4: sidebar_page = "/template/sidebar/sidebar-user.xhtml" ; break;	
	   case 5: sidebar_page = "/template/sidebar/sidebar-user.xhtml" ; break;	
	   case 6: sidebar_page = "/template/sidebar/sidebar-admin.xhtml" ; break;
	   case 7: sidebar_page = "/template/sidebar/sidebar-user.xhtml" ; break;	   
	   
	   }
	   
	   
	   return sidebar_page;
	   
   }
	
}
