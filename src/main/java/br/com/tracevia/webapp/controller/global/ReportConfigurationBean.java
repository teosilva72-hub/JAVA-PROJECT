package br.com.tracevia.webapp.controller.global;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ReportBuild;

/**
 * Classe para carregar métodos pré configurados para os relatórios
 * @author Wellington 26/10/2021
 * @version 1.0
 * @since 1.0
 *
 */

@ManagedBean(name="reportConfigBean")
@RequestScoped
public class ReportConfigurationBean {

	private ReportBuild build;

	List<? extends Equipments> listEquips;  

	public ReportBuild getBuild() {
		return build;
	}

	public void setBuild(ReportBuild build) {
		this.build = build;
	}

	public List<? extends Equipments> getListEquips() {
		return listEquips;
	}

	@PostConstruct
	public void initialize() {

		// Instantiate Objects - build and select class
		build = new ReportBuild();

		// ---------------------------------------------------------------------------

		// Disabled Buttons
		build.clearBool = true;
		build.excelBool = true;
		// build.chartBool = true;						

	}	

	// --------------------------------------------------------------------------------------------		

	/**
	 * Método para carregar equipamentos disponíveis para seleção
	 * @author Wellington 26/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param module - modulo que os equipamentosa devem ser carregados
	 */
	public void defineEquipmentsOption(String module) {

		try {

			EquipmentsDAO dao = new EquipmentsDAO();		 
			listEquips = dao.EquipmentSelectOptions(module);

			build.equipments = build.selectEquips(listEquips);

			// EQUIPMENTS
			// build.equipments = build.selectEquips(listSpeed);
			// select.equipments = new String[build.equipments.size()];  

		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------------------------------		

	/**
	 * Método para carregar períodos disponíveis para seleção
	 * @author Wellington 26/10/2021
	 * @version 1.0
	 * @since 1.0	
	 */
	public void definePeriodsOption() {

		try {

			build.periods = build.selectPeriods();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// --------------------------------------------------------------------------------------------	

}
