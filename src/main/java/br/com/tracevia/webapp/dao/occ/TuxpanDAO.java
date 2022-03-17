package br.com.tracevia.webapp.dao.occ;

import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.occ.TuxpanOccModel;

public class TuxpanDAO{
	SQL_Tracevia conn = new SQL_Tracevia();
	
	public String registerOcc(TuxpanOccModel data) throws Exception {
		String saveOcc = null;
		String query = "INSERT INTO occ_tuxpan(cobro, folio_secuencial, reporte, siniestro, fecha, direccion, km_reg, km_inicial, km_final, poliza,"+
		"hora_reg_cab, hora_arr_aju, semoviente, trabajo_cons, tiempo, neblina, vandalismo, otro, obs) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {

			conn.start(1);
			conn.prepare(query);
			
			conn.setString(1, data.getPlz_cobro());
			conn.setString(2, data.getFolio_sec());
			conn.setString(3, data.getReporte());
			conn.setString(4, data.getSiniestro());
			conn.setString(5, data.getFecha());
			conn.setString(6, data.getDireccion());
			conn.setString(7, data.getKm_reg());
			conn.setString(8, data.getKm_inicial());
			conn.setString(9, data.getKm_final());
			conn.setString(10, data.getPoliza());
			conn.setString(11, data.getFecha_cab());
			conn.setString(12, data.getHora_ajust());
			conn.setString(13, data.getSemoviente());
			conn.setString(14, data.getTrab_conserv());
			conn.setString(15, data.getLluvia_granizo());
			conn.setString(16, data.getNeblina());
			conn.setString(17, data.getVandalismo());
			conn.setString(18, data.getOtro());
			conn.setString(19, data.getObs_occ());
			
			long res = conn.executeUpdate();
			System.out.println(res);
			/*if(res > 0) {

				MapResult result = conn.executeQuery();

				if(result.hasNext()) {
					for (RowResult rs : result) {	

					}
				}   

			}*/
			
		}catch (Exception e){
			throw new Exception("Erro ao inserir dados: " + e);
		}finally {
			conn.close();
		}
		
		return saveOcc;
	}
}