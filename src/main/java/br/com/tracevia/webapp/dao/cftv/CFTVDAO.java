package br.com.tracevia.webapp.dao.cftv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.cftv.CFTV;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class CFTVDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;


	public List<CFTV> Status() throws Exception {

		List<CFTV> list = new ArrayList<CFTV>();		

		String select = "SELECT equip_id, equip_name, equip_status FROM connection_monitor WHERE equip_type = 'CFTV' ";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(select);			

			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					CFTV cftv = new CFTV();

					cftv.setEquip_id(rs.getInt("equip_id"));
					cftv.setNome(rs.getString("equip_name"));
					cftv.setStatus(rs.getInt("equip_status"));

					list.add(cftv);
				}				
			}			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

		return list;

	}	
	public Equipments getTotalId() throws Exception {
		Equipments list = new Equipments();	
		String query = "SELECT MAX(equip_id) FROM cftv_equipment";
		try {
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			ps = conn.prepareStatement(query);			
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					list.setEquip_id(rs.getInt(1));
				}				
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}
		return list;
	}
}