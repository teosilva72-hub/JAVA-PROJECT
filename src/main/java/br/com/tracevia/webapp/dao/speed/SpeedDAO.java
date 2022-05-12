package br.com.tracevia.webapp.dao.speed;

import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.speed.Speed;

public class SpeedDAO {

	SQL_Tracevia conn = new SQL_Tracevia();

	public SpeedDAO(){
		
		 conn = new SQL_Tracevia();
		
	}


	public List<Speed> getSpeeds() throws Exception {

		List<Speed> list = new ArrayList<Speed>();

		String select = "SELECT equip_id, name, km FROM speed_equipment WHERE visible != 0 ";

		try {
			conn.start(1);

			conn.prepare(select);

			MapResult result = conn.executeQuery();

			if (result != null) {
				for (RowResult rs : result) {

					Speed speed = new Speed();

					speed.setEquip_id(rs.getInt("equip_id"));
					speed.setNome(rs.getString("name"));
					speed.setKm(rs.getString("km"));
					speed.setTable_id("speed");

					list.add(speed);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return list;

	}
}