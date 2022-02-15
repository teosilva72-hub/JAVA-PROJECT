package br.com.tracevia.webapp.dao.cftv;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.cftv.CFTV;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;

public class CFTVDAO {

	SQL_Tracevia conn = new SQL_Tracevia();


	public List<CFTV> Status() throws Exception {

		List<CFTV> list = new ArrayList<CFTV>();		

		String select = "SELECT equip_id, equip_name, equip_status FROM connection_monitor WHERE equip_type = 'CFTV' ";

		try {

			conn.start(1);

			conn.prepare(select);			

			MapResult result = conn.executeQuery();

			if (result.hasNext()) {
				for (RowResult rs : result) {

					CFTV cftv = new CFTV();

					cftv.setEquip_id(rs.getInt("equip_id"));
					cftv.setNome(rs.getString("equip_name"));
					cftv.setStatus(rs.getInt("equip_status"));

					list.add(cftv);
				}				
			}			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}

		return list;

	}	
	public Equipments getTotalId() throws Exception {
		Equipments list = new Equipments();	
		String query = "SELECT MAX(equip_id) FROM cftv_equipment";
		try {
			conn.start(1);
			conn.prepare(query);			
			MapResult result = conn.executeQuery();
			if (result.hasNext()) {
				for (RowResult rs : result) {
					list.setEquip_id(rs.getInt(1));
				}				
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return list;
	}
}