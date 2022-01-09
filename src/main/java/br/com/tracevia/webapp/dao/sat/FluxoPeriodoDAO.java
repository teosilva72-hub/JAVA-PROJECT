package br.com.tracevia.webapp.dao.sat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.log.SystemLog;
import br.com.tracevia.webapp.model.sat.FluxoPeriodo;

public class FluxoPeriodoDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
		
	// --------------------------------------------------------------------------------------------------------------
		
	// LOGS FOLDER
	
	private final String errorFolder = SystemLog.ERROR.concat("period-flow-dao\\");
	
	// --------------------------------------------------------------------------------------------------------------

	public FluxoPeriodoDAO() throws Exception {

		SystemLog.createLogFolder(errorFolder);
		
	}	
	
	// --------------------------------------------------------------------------------------------------------------
	
	public List<FluxoPeriodo> getVehicles(String startDate, String endDate, String equipId, String period, String laneDir1) {		
		
		
		List<FluxoPeriodo> lista = new ArrayList<FluxoPeriodo>();
		
		
		return lista;
		
	}
	
	// --------------------------------------------------------------------------------------------------------------

}
