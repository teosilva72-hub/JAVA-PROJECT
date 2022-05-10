package br.com.tracevia.webapp.dao.dai;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.DirectionModel;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;

public class DAIDAO {
	
	SQL_Tracevia conn;
	
	public DAIDAO() {
		
		conn = new SQL_Tracevia();
		
	}
	
	public List<DirectionModel> getDirectionsList() throws Exception {
				
		List<DirectionModel> list = new ArrayList<DirectionModel>();
						
		String select = "SELECT equip_id, direction FROM dai_direction";
									
		try {
			
			conn.start(1);
			
			conn.prepare(select);	
												
			MapResult result = conn.executeQuery();
			
			if (result.hasNext()) {
				for (RowResult rs : result) {
					
					DirectionModel model = new DirectionModel();
					
					model.setEquipId(rs.getInt("equip_id"));
					model.setDirection(rs.getString("direction"));
																				
					list.add(model);
				}				
			}			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
				
		return list;
		
	}
		

	
}
