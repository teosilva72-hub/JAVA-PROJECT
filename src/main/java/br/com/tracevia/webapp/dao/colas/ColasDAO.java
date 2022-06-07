package br.com.tracevia.webapp.dao.colas;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.colas.ColasData;
import br.com.tracevia.webapp.model.colas.ColasQueue;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;

public class ColasDAO {

	SQL_Tracevia  conn;
	
	
	public ColasDAO() {
		
		conn = new SQL_Tracevia();
	}
	
	
	public List<ColasData> cameraGet() throws Exception{
		
		List<ColasData> list = new ArrayList<ColasData>();
		String query = "SELECT name FROM colas_equipment";
		try {

			conn.start(1);

			conn.prepare(query);			

			MapResult result = conn.executeQuery();

			if (result.hasNext()) {
				for (RowResult rs : result) {

					ColasData colas = new ColasData();

					colas.setCam(rs.getString(1));

					list.add(colas);
				}				
			}			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}

		return list;
		
	}

	public List<ColasQueue> history_queue(String date, int deviceS, int laneS) throws Exception {

		List<ColasQueue> list = new ArrayList<>();
		String query = "SELECT h.device, h.update_date, h.lane, h.local, c.km FROM colas_history h "
				+ "INNER JOIN colas_equipment c ON h.device = c.equip_id "
				+ "WHERE h.update_date >= '"
				+ date
				+ " 00:00:00' "
				+ "AND h.update_date < '"
				+ date
				+ " 23:59:59'";

		if (laneS > 0)
			query += " AND h.lane = " + laneS;
		if (deviceS > 0)
			query += " AND h.device = " + deviceS;

		try {
			conn.start(1);

			conn.prepare(query);
			
			MapResult result = conn.executeQuery();

			if (result.hasNext()) {
				for (RowResult rs : result) {

					int device = rs.getInt(1);
					int lane = rs.getInt(3);
					int local = rs.getInt(4);
					String km = rs.getString(5);

					ColasQueue colas = new ColasQueue(device, lane, local, km);

					SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:MM");

					colas.setDate(format.parse(rs.getString(2)));

					list.add(colas);
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
