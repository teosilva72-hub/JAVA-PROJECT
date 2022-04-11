package br.com.tracevia.webapp.dao.colas;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.colas.Colas;
import br.com.tracevia.webapp.model.colas.ColasQueue;
import br.com.tracevia.webapp.model.colas.ColasData;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;

public class ColasDAO {

	SQL_Tracevia conn = new SQL_Tracevia();
	
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
		String query = "SELECT device, update_date, lane, local, km FROM colas_history h INNER JOIN colas_equipment "
				+ "WHERE update_date >= '"
				+ date
				+ " 00:00:00' "
				+ "AND update_date < '"
				+ date
				+ " 23:59:59'";

		if (laneS > 0)
			query += " AND lane = " + laneS;
		if (deviceS > 0)
			query += " AND device = " + deviceS;

		try {
			conn.start(1);

			conn.prepare(query);
			
			MapResult result = conn.executeQuery();

			if (result.hasNext()) {
				for (RowResult rs : result) {

					int device = rs.getInt("device");
					int lane = rs.getInt("lane");
					int local = rs.getInt("local");
					String km = rs.getString("km");

					ColasQueue colas = new ColasQueue(device, lane, local, km);

					SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:MM");

					colas.setDate(format.parse(rs.getString("update_date")));

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

	public List<Colas> Status() throws Exception {


		List<Colas> list = new ArrayList<Colas>();		

		String select = "SELECT equip_id, equip_name, equip_status FROM connection_monitor WHERE equip_type = 'COLAS' ";

		try {

			conn.start(1);

			conn.prepare(select);			

			MapResult result = conn.executeQuery();

			if (result.hasNext()) {
				for (RowResult rs : result) {

					Colas colas = new Colas();

					colas.setEquip_id(rs.getInt("equip_id"));
					colas.setNome(rs.getString("equip_name"));
					colas.setStatus(rs.getInt("equip_status"));

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
