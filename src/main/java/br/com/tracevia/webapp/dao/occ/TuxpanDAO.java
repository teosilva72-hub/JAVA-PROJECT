package br.com.tracevia.webapp.dao.occ;

import java.util.ArrayList;

import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.occ.OccurrencesData;
import br.com.tracevia.webapp.model.occ.TuxpanOccModel;

public class TuxpanDAO{
	SQL_Tracevia conn = new SQL_Tracevia();

	public boolean update(TuxpanOccModel data, int id) throws Exception {
		boolean check = false;
		
		String query = "UPDATE occ_tuxpan SET cobro = ?, folio_secuencial = ?, reporte = ?, siniestro = ?, fecha = ?, direccion = ?, km_reg = ?, km_inicial = ?, km_final = ?, poliza = ?,"+
				"hora_reg_cab = ?, hora_arr_aju = ?, semoviente = ?, trabajo_cons = ?, tiempo = ?, neblina = ?, vandalismo = ?, otro = ?, obs = ?, tipo_veh = ?,"+
				"tipo_veh_eje = ?, num_veh_inv = ?, marca_veh_inv = ?, tipo_veh_inv = ?, modelo_veh_inv = ?, color_veh_inv = ?, placa_est_veh_inv = ?, tel_veh_inv = ?, num_person = ?, nombre_person = ?,"+
				"edad_person = ?, condiccion_person = ?, hora = ? WHERE id ="+id+"";
		
		try {
			
					conn.start(1);
					conn.prepare(query);
					//conn.executeUpdate();			
			
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
					conn.setString(20, data.getTipo_veh_inv());
					conn.setString(21, data.getNum_eje_veh_inv());
					conn.setString(22, data.getNum_tp_veh());
					conn.setString(23, data.getMarca_tp_veh());
					conn.setString(24, data.getTipo_tp_veh());
					conn.setString(25, data.getModel_tp_veh());
					conn.setString(26, data.getColor());
					conn.setString(27, data.getPlaca_estado());
					conn.setString(28, data.getTel());
					conn.setString(29, data.getId_person());
					conn.setString(30, data.getNombre());
					conn.setString(31, data.getEdad());
					conn.setString(32, data.getCondiciones());
					conn.setString(33, data.getHora());
					
					long occ = conn.executeUpdate();
					if(occ > 0)
						check = true;
					System.out.println(occ);
				
			
		}catch (Exception e){
			throw new Exception("Erro ao alterar dados: " + e);
		}finally {
			conn.close();
		}
		
		return check;
	}
	
	public TuxpanOccModel select(String id) throws Exception {
		TuxpanOccModel report = new TuxpanOccModel();
		String query = "SELECT cobro, folio_secuencial, reporte, siniestro, fecha, direccion, km_reg, km_inicial, km_final, poliza,"+
				"hora_reg_cab, hora_arr_aju, semoviente, trabajo_cons, tiempo, neblina, vandalismo, otro, obs, tipo_veh, tipo_veh_eje,"+
				"num_veh_inv, marca_veh_inv, tipo_veh_inv, modelo_veh_inv, color_veh_inv, placa_est_veh_inv, tel_veh_inv, num_person,"+
				"nombre_person, edad_person, condiccion_person, hora, type_report, id FROM occ_tuxpan WHERE id ='"+id+"'";
		try {
			conn.start(1);
			conn.prepare(query);
			MapResult result = conn.executeQuery();
			if(result.hasNext()) {
				for (RowResult rs : result) {
					report.setPlz_cobro(rs.getString(1));
					report.setFolio_sec(rs.getString(2));
					report.setReporte(rs.getString(3));
					report.setSiniestro(rs.getString(4));
					report.setFecha(rs.getString(5));
					report.setDireccion(rs.getString(6));
					report.setKm_reg(rs.getString(7));
					report.setKm_inicial(rs.getString(8));
					report.setKm_final(rs.getString(9));
					report.setPoliza(rs.getString(10));
					report.setFecha_cab(rs.getString(11));
					report.setHora_ajust(rs.getString(12));
					report.setSemoviente(rs.getString(13));
					report.setTrab_conserv(rs.getString(14));
					report.setLluvia_granizo(rs.getString(15));
					report.setNeblina(rs.getString(16));
					report.setVandalismo(rs.getString(17));
					report.setOtro(rs.getString(18));
					report.setObs_occ(rs.getString(19));
					report.setTipo_veh_inv(rs.getString(20));
					report.setNum_eje_veh_inv(rs.getString(21));
					report.setNum_tp_veh(rs.getString(22));
					report.setMarca_tp_veh(rs.getString(23));
					report.setTipo_tp_veh(rs.getString(24));
					report.setModel_tp_veh(rs.getString(25));
					report.setColor(rs.getString(26));
					report.setPlaca_estado(rs.getString(27));
					report.setTel(rs.getString(28));
					report.setId_person(rs.getString(29));
					report.setNombre(rs.getString(30));
					report.setEdad(rs.getString(31));
					report.setCondiciones(rs.getString(32));
					report.setHora(rs.getString(33));
					report.setType_report(rs.getString(34));
					report.setId(rs.getString(35));
					
					long occ = conn.executeUpdate();
					System.out.println(occ);
				}
			}

		}catch (Exception e){
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return report;
	}

	public boolean registerOcc(TuxpanOccModel data, String type_report) throws Exception {
		boolean saveOcc = false;
		String query = "INSERT INTO occ_tuxpan(cobro, folio_secuencial, reporte, siniestro, fecha, direccion, km_reg, km_inicial, km_final, poliza,"+
				"hora_reg_cab, hora_arr_aju, semoviente, trabajo_cons, tiempo, neblina, vandalismo, otro, obs, tipo_veh, tipo_veh_eje,"+
				"num_veh_inv, marca_veh_inv, tipo_veh_inv, modelo_veh_inv, color_veh_inv, placa_est_veh_inv, tel_veh_inv, num_person,"+
				"nombre_person, edad_person, condiccion_person, hora, type_report)"+
				" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
			conn.setString(20, data.getTipo_veh_inv());
			conn.setString(21, data.getNum_eje_veh_inv());
			conn.setString(22, data.getNum_tp_veh());
			conn.setString(23, data.getMarca_tp_veh());
			conn.setString(24, data.getTipo_tp_veh());
			conn.setString(25, data.getModel_tp_veh());
			conn.setString(26, data.getColor());
			conn.setString(27, data.getPlaca_estado());
			conn.setString(28, data.getTel());
			conn.setString(29, data.getId_person());
			conn.setString(30, data.getNombre());
			conn.setString(31, data.getEdad());
			conn.setString(32, data.getCondiciones());
			conn.setString(33, data.getHora());
			conn.setString(34, type_report);
			long occ = conn.executeUpdate();
			System.out.println(occ);
			if(occ > 0) {
				saveOcc = true;
			}
		}catch (Exception e){
			throw new Exception("Erro ao inserir dados: " + e);
		}finally {
			conn.close();
		}
		return saveOcc;
	}
	public ArrayList<TuxpanOccModel> listarOcorrencias() throws Exception {
		String query = "SELECT id, folio_secuencial, reporte, siniestro, fecha, hora FROM occ_tuxpan";	

		ArrayList<TuxpanOccModel> listarOcc = new ArrayList<TuxpanOccModel>();
		//System.out.println(query);
		try {
			TranslationMethods occTranslation = new TranslationMethods();
			conn.start(1);
			conn.prepare(query);
			MapResult result = conn.executeQuery();

			if(result.hasNext()) {
				for (RowResult rs : result) {
					TuxpanOccModel occ = new TuxpanOccModel();
					occ.setId(rs.getString(1));		
					occ.setFolio_sec(ifEmpty(rs.getString(2)));
					occ.setReporte(ifEmpty(rs.getString(3)));;
					occ.setSiniestro(ifEmpty(rs.getString(4)));;
					occ.setFecha(ifEmpty(rs.getString(5)));
					occ.setHora(ifEmpty(rs.getString(6)));

					listarOcc.add(occ);
				}
			}
		}finally {
			conn.close();
		}
		return listarOcc;
	}

	public String ifEmpty(String val) {
		return val != null ? val.isEmpty() ? "----------" : val : "----------";
	}
}