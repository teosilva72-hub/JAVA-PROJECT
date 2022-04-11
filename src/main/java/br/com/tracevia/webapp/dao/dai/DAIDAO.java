package br.com.tracevia.webapp.dao.dai;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.dai.DAI;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;

public class DAIDAO {
	
	SQL_Tracevia conn = new SQL_Tracevia();
	
	public List<DAI> Status() throws Exception {
		
		
		List<DAI> list = new ArrayList<DAI>();		
						
		String select = "SELECT equip_id, equip_name, equip_status FROM connection_monitor WHERE equip_type = 'DAI' ";
									
		try {
			
			conn.start(1);
			
			conn.prepare(select);			
									
			MapResult result = conn.executeQuery();
			
			if (result.hasNext()) {
				for (RowResult rs : result) {
					
					DAI dai = new DAI();

						dai.setEquip_id(rs.getInt("equip_id"));
						dai.setNome(rs.getString("equip_name"));
						dai.setStatus(rs.getInt("equip_status"));
																				
					list.add(dai);
				}				
				}			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
				
		return list;
		
	}
		

	public DAI getEquipment(String name) throws Exception {
		DAI dai = new DAI();
		String select = "SELECT equip_id, name, km FROM dai_equipment WHERE name = ? ";

		try {
			
			conn.start(1);
			
			conn.prepare(select);
			conn.setString(0, name);
									
			MapResult result = conn.executeQuery();
			
			if (result.hasNext()) {
				RowResult rs = result.first();
					

				dai.setEquip_id(rs.getInt("equip_id"));
				dai.setNome(rs.getString("name"));
				dai.setKm(rs.getString("km"));
			}			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
				
		return dai;
		
	}

}
