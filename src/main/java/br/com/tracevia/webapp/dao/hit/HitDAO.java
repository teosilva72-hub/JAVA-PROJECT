package br.com.tracevia.webapp.dao.hit;

import java.util.ArrayList;

import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.hit.HIT;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.EquipmentDataSource;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.occ.TuxpanOccModel;

public class HitDAO {
	
	SQL_Tracevia conn = new SQL_Tracevia();
	
	public ArrayList<EquipmentDataSource> listHit() throws Exception {
		String query = "SELECT equip_id, name, km FROM hit_equipment";	

		ArrayList<EquipmentDataSource> listarHit = new ArrayList<EquipmentDataSource>();
		//System.out.println(query);
		try {
			TranslationMethods occTranslation = new TranslationMethods();
			conn.start(1);
			conn.prepare(query);
			MapResult result = conn.executeQuery();

			if(result.hasNext()) {
				for (RowResult rs : result) {
					EquipmentDataSource dao = new EquipmentDataSource();
					dao.setEquipId(rs.getInt(1));
					dao.setEquipName(rs.getString(2));
					dao.setKm(rs.getString(3));
					listarHit.add(dao);
				}
			}
		}finally {
			conn.close();
		}
		return listarHit;
	}
	public HIT listMsg(int id) throws Exception {
		String query = "SELECT C.msg1 , C.msg2, C.msg3, C.img, P.name FROM hit_message AS C JOIN hit_equipment AS P ON C.equip_id = '"+id+"' AND P.equip_id = '"+id+"'";	
		
		HIT listarHit = new HIT();
		try {
			TranslationMethods occTranslation = new TranslationMethods();
			conn.start(1);
			conn.prepare(query);
			MapResult result = conn.executeQuery();

			if(result.hasNext()) {
				for (RowResult rs : result) {
					
					listarHit.setMsg1(rs.getString(1));
					listarHit.setMsg2(rs.getString(2));
					listarHit.setMsg3(rs.getString(3));
					listarHit.setImg(rs.getString(4));
					listarHit.setEquip_type(rs.getString(5));
				}
			}
		}finally {
			conn.close();
		}
		return listarHit;
	}

}
