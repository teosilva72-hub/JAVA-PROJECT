package br.com.tracevia.webapp.controller.global;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.global.ReportSelection;

@ManagedBean(name="prototipoBean")
@RequestScoped
public class PrototipoController implements Serializable{

	/**
	 * @author Wellington 20-08-2021
	 */
	private static final long serialVersionUID = 1L;
		
	private ReportSelection select;
	private ReportBuild build;
	
	static String module;
	
	public ReportSelection getSelect() {
		return select;
	}
		
	public void setSelect(ReportSelection select) {
		this.select = select;
	}
	
	public ReportBuild getBuild() {
		return build;
	}
	
	public void setBuild(ReportBuild build) {
		this.build = build;
	}
		
    // ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	
	@PostConstruct
	public void initialize() {
		
		// Instantiate Objects - build and select class
		
		build = new ReportBuild();
		select = new ReportSelection();	
		module = "sat";
		
		// ---------------------------------------------------------------------------
				   		
		// SELECT ITEMS LISTS		
        build.equipments = new ArrayList<SelectItem>();	  
        build.periods = new ArrayList<SelectItem>();	  
        
        try {
        	
        	// EQUIPMENTS
        	 build.equipments = build.selectEquips(module);
        	 select.equipments = new String[build.equipments.size()];  
        	 
        	 // PERIODS
        	 build.periods = build.selectPeriods();
        	 			
		} catch (Exception e) {			
			e.printStackTrace();
		}
                 	
    	// ----------------------------------------------------------------------------
        	
    	 // Disabled Buttons
    	 build.clearBool = true;
    	 build.excelBool = true;
    	 build.chartBool = true;						
	 
	 }	
	
    // ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para a seleção do cabeçalho do relatório
	 * @author Wellington
	 * @version 1.0
	 * @since version 1.0
	 * @param type - Identificação do tipo do relatório
	 * @return - vazio
	 */
	public void CreateFields(String type) {
		
		// FILTRO DO TIPO DE RELATÓRIO	
		
		switch(type) {
		
		case "1":  ;break;
		case "2":  ;break;
		case "3":  ;break;
		case "4":  ;break;
		case "5":  ;break;
		case "6":  ;break;
		case "7":  ;break;
		case "8":  ;break;
		case "9":  ;break;
		case "10":  ;break;
		case "11":  ;break;
		case "12":  ;break;
		case "13":  ;break;
		
		
		}		
		
	}
	
   // ----------------------------------------------------------------------------------------------------------------
   // ----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para resetar valores de formulários nos relatórios
	 * @return - vazio
	 */
	 public void resetForm() {
		
		// Limpa valores da sessão
		build.resetReportValues();
		
		// Reinicializa valores armazenados nas variáveis abaixo
		build = new ReportBuild();
		select = new ReportSelection();
		
	}
	
   // ----------------------------------------------------------------------------------------------------------------
   // ----------------------------------------------------------------------------------------------------------------
	
	

}
