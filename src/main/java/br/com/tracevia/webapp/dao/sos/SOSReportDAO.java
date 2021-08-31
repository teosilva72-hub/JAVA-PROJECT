package br.com.tracevia.webapp.dao.sos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.sos.SOSReports;
import br.com.tracevia.webapp.util.ConnectionFactory;
import br.com.tracevia.webapp.util.LocaleUtil;

public class SOSReportDAO {
	
	private Connection conn;	
	private PreparedStatement ps;
	private ResultSet rs;
	
	LocaleUtil label;
		
	public List<SOSReports> callRegisters(String query)
		      throws Exception{		
		      
		List<SOSReports> lista = new ArrayList<SOSReports>();
														
		try {

			//GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
															
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();			
					
			//RESULT IN RESULTSET	
			  if (rs.isBeforeFirst()) {
				  while (rs.next()) { //Linhas

					SOSReports sos = new SOSReports();
					
					sos.setDate(rs.getString(1));
					sos.setCallReceived(rs.getString(2));
					sos.setCallAnswered(rs.getString(3));
					sos.setCallEnded(rs.getString(4));
					sos.setCallMissed(rs.getString(5));
					sos.setCallError(rs.getString(6));
					sos.setCallDuration(rs.getString(7));
					sos.setOperator(rs.getString(8));
					
					lista.add(sos);								
				}												
			} 

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps);}

		
		return lista;
	}
	
	// ------------------------------------------------------------------------------------------
	
	public List<SOSReports> callAlarms(String query)
		      throws Exception{		
		      
		List<SOSReports> lista = new ArrayList<SOSReports>();
		
		label = new LocaleUtil();
		label.getResourceBundle(LocaleUtil.LABELS_SOS);
														
		try {

			//GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
															
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();			
					
			 // RESULT IN RESULTSET	
			  if (rs.isBeforeFirst()) {
				  while (rs.next()) { // Linhas

					SOSReports sos = new SOSReports();
					
					sos.setDate(rs.getString(1));
					
					// ------------------------------------------------------------------------------
					
					if(rs.getString(2).equals("2"))				
					    sos.setSosStatusType(label.getStringKey("sos_type_name_top_door"));
					
					else sos.setSosStatusType(label.getStringKey("sos_type_name_bottom_door"));
					
					// ------------------------------------------------------------------------------
												
					 sos.setSosStatusOpened(rs.getString(3));					
					 sos.setSosStatusClosed(rs.getString(4)); 	
					 sos.setSosStatusTime(rs.getString(5)); 
								
					// ------------------------------------------------------------------------------
								
					lista.add(sos);		
					
				}												
			} 

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps);}

		
		return lista;
	}
	
  // -----------------------------------------------------------------------------------------------
	
	
}
