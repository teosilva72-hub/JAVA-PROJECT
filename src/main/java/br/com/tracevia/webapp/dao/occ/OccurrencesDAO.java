package br.com.tracevia.webapp.dao.occ;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

import br.com.tracevia.webapp.controller.global.UserAccountBean;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.occ.OccurrencesData;
import br.com.tracevia.webapp.model.sos.SOS;

public class OccurrencesDAO {

	SQL_Tracevia conn = new SQL_Tracevia();

	//CREATE OCCURRENCE
	public String cadastroOcorrencia (OccurrencesData data ) throws Exception {

		String occ_number = null;

		//script BD
		String query = "INSERT INTO occ_data(occ_number, type, origin, state_occurrence, start_date, start_hour, start_minute, end_date, end_hour, end_minute, cause, cause_description, kilometer, highway, "
				+ "local_state, direction, lane, others, local_condition, traffic, characteristic, interference, signaling, conductor_condition, descrTitleDescr, descrDescr, envolvTypo, "
				+ "envolvWhen, envolvDescr, procedureName, procedureDescr, trafficHour, trafficMinute, trafficKm, trafficTrackInterrupted, damageDate, damegeType, damageGravity, damageDescr, "
				+ "actionType, actionStart, actionEnd, actionDuration, actionDescr, actionStartData, actionStartHour, actionStartMinute, actionEndData, actionEndHour, actionEndMinute, trackStartDate, "
				+ "trackStartHour, trackStartMinute, trackEndData, trackEndHour, trackEndMinute, damageDescriptionInternal, causeDescrInter, descriptionInter, involvedInter, actionInter, "
				+ "damage_amount, statusAction, damageUnitySelect) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		DateTimeApplication dtm = new DateTimeApplication();

		try {

			conn.start(1);
			conn.prepare(query);

			//passando valores para os atributos BD
			conn.setString(1, data.getData_number());
			conn.setString(2, data.getType());
			conn.setString(3, data.getOrigin());
			conn.setString(4, data.getState_occurrences());
			conn.setString(5, data.getStart_date());
			conn.setString(6, data.getStart_hour());
			conn.setString(7, data.getStart_minute());
			conn.setString(8, data.getEnd_date());
			conn.setString(9, data.getEnd_hour());
			conn.setString(10, data.getEnd_minute());
			conn.setString(11, data.getCause());
			conn.setString(12, data.getCause_description());
			conn.setString(13, data.getKilometer());
			conn.setString(14, data.getHighway());
			conn.setString(15, data.getLocal_state());
			conn.setString(16, data.getDirection());
			conn.setString(17, data.getLane());
			conn.setString(18, data.getOthers());
			conn.setString(19, data.getLocal_condition());
			conn.setString(20, data.getTraffic());
			conn.setString(21, data.getCharacteristic());
			conn.setString(22, data.getInterference());
			conn.setString(23, data.getSignaling());
			conn.setString(24, data.getConductor_condition());
			conn.setString(25, data.getDescription_title());
			conn.setString(26, data.getDescription_text());
			conn.setString(27, data.getInvolved_type());
			conn.setString(28, data.getInvolved_When());
			conn.setString(29, data.getInvolved_description());
			conn.setString(30, data.getProcedure_name());
			conn.setString(31, data.getProcedure_description());
			conn.setString(32, data.getTraffic_hours());
			conn.setString(33, data.getTraffic_minutes());
			conn.setString(34, data.getTraffic_extension());
			conn.setString(35, data.getTraffic_stopped());
			conn.setString(36, data.getDamage_date());
			conn.setString(37, data.getDamage_type_damage());
			conn.setString(38, data.getDamage_gravity());
			conn.setString(39, data.getDemage_description());
			conn.setString(40, data.getAction_type());
			conn.setString(41, data.getAction_start());
			conn.setString(42, data.getAction_end());
			conn.setString(43, data.getAction_duration());
			conn.setString(44, data.getAction_description());
			conn.setString(45, data.getActionStartData()); 
			conn.setString(46, data.getActionStartHour()); 
			conn.setString(47, data.getActionStartMinute()); 
			conn.setString(48, data.getActionEndData()); 
			conn.setString(49, data.getActionEndHour()); 
			conn.setString(50, data.getActionEndMinute()); 
			conn.setString(51, data.getTrackStartDate()); 
			conn.setString(52, data.getTrackStartHour()); 
			conn.setString(53, data.getTrackStartMinute()); 
			conn.setString(54, data.getTrackEndDate()); 
			conn.setString(55, data.getTrackEndHour()); 
			conn.setString(56, data.getTrackEndMinute()); 
			conn.setString(57, data.getDamageDescriptionInternal());
			conn.setString(58, data.getCauseDescrInter());
			conn.setString(59, data.getDescriptionInter());
			conn.setString(60, data.getInvolvedInter());
			conn.setString(61, data.getActionInter());
			conn.setString(62, data.getDamage_amount()); 
			conn.setString(63, data.getStatusAction()); 
			conn.setString(64, data.getDamageUnity());

			long res = conn.executeUpdate();

			//se a vari�vel for maior do que 0, acessamos a essa fun��o
			if(res > 0) {

				//pengando o �ltimo valor do banco de dados
				String query2 = "Select MAX(occ_number) AS last_Id  FROM occ_data";

				conn.prepare(query2);

				MapResult result = conn.executeQuery();

				if(result.hasNext()) {
					for (RowResult rs : result) {	
						//atribuindo para a variavel occ_number o �ltimo valor do banco de dados
						occ_number = rs.getString("last_Id");

					}
				}   	 

			}

		}catch (Exception inserirOcorrencia){
			throw new Exception("Erro ao inserir dados: " + inserirOcorrencia);
		}finally {
			conn.close();
		}
		//retordo o valor do m�todo como o �ltimo id do banco de dados
		return occ_number;

	}

	public ArrayList<SelectItem> dropDownFieldValues (String field) throws Exception {	

		String query = "SELECT detail_id, value_ FROM tracevia_app.occ_details WHERE field = ? and active = 1";

		ArrayList<SelectItem> listarDropDownValue = new ArrayList<SelectItem>();
		/*System.out.println(query);*/
		TranslationMethods occTranslation = new TranslationMethods();

		try {

			conn.start(1);
			conn.prepare(query);
			conn.setString(1, field);

			MapResult result = conn.executeQuery();

			if(result.hasNext()) {
				for (RowResult rs : result) {
					SelectItem listarDrop = new SelectItem();
					listarDrop.setValue(rs.getInt(1));
					listarDrop.setLabel(occTranslation.occurrencesTranslator(rs.getString(2)));

					listarDropDownValue.add(listarDrop);
				}
			}

		}finally {

			conn.close();
		}
		return listarDropDownValue;
	}

	//m�todo editTable, esse � o m�todo onde passamos os valores para os atributos para fazer o bloqueio e desbloqueio da tabela
	public boolean editTable(boolean editTable, String name_user, int accessLevel, String id ) throws Exception {

		boolean status = false;

		//script para pegar os valores e passar valores
		String query = "UPDATE occ_data SET editTable = ?, nameUser = ?, accessLevel = ? WHERE occ_number = ?";

		DateTimeApplication dtm = new DateTimeApplication();

		try {
			//passando ou pegando os valores dos atributos
			conn.start(1);

			conn.prepare(query);

			conn.setBoolean(1, editTable);
			conn.setString(2, name_user);
			conn.setInt(3, accessLevel);
			conn.setString(4, id);

			conn.executeUpdate();


		}catch (Exception alterarOcorrencia){

			throw new Exception("Erro ao alterar dados: " + alterarOcorrencia);

		}finally {

			conn.close();

		}		

		return status;

	}
	public boolean lastUser(String id, String lastData, String user) throws Exception {

		boolean status = false;

		String query = "UPDATE occ_data SET lastDateUser = ?, lastUser = ? WHERE occ_number = ?";
		DateTimeApplication dtm = new DateTimeApplication();
		try {
			conn.start(1);
			conn.prepare(query);
			conn.setString(1, lastData);
			conn.setString(2, user);
			conn.setString(3, id);

			long answer = conn.executeUpdate();

			if(answer > 0) 
				status = true;
		}catch (Exception alterarOcorrencia){
			throw new Exception("Erro ao alterar dados: " + alterarOcorrencia);
		}finally {
			conn.close();
		}	

		return status;
	}
	//m�todo atualizar ocorr�ncia 
	public boolean atualizarOcorrencia(OccurrencesData data) throws Exception {
		// System.out.println("DATA: "+data.getData_number()+"\nType: "+data.getAction_type());
		boolean status = false;
		//script dos atributos que ser�o atualizados as informa�oes do banco de dados
		String query = "UPDATE occ_data SET type = ? , origin = ?, state_occurrence = ?, start_date = ?, start_hour = ?, " +
				"start_minute = ?, end_date = ?, end_hour = ?, end_minute = ?, cause = ?, cause_description = ?, kilometer = ?, highway = ?, " +
				"local_state = ?, direction = ?, lane = ?, others = ?, local_condition = ?, traffic = ?, characteristic = ?, interference = ?, " +
				"signaling = ?, conductor_condition = ?, descrTitleDescr = ?, descrDescr = ?, envolvTypo = ?, envolvWhen = ?, envolvDescr = ?, " +
				"procedureName = ?, procedureDescr = ?, trafficHour = ?, trafficMinute = ?, trafficKm = ?, trafficTrackInterrupted= ?, damageDate= ?, " + 
				"damegeType = ?, damageGravity = ?, damageDescr = ?, actionType = ?, actionStart = ?, actionEnd = ?, actionDuration = ?, actionDescr = ?, " +
				"actionStartData = ?, actionStartHour = ?, actionStartMinute = ?, actionEndData = ?, actionEndHour = ?, actionEndMinute = ?, trackStartDate = ?, " +
				"trackStartHour = ?, trackStartMinute = ?, trackEndData = ?, trackEndHour = ?, trackEndMinute = ?, damageDescriptionInternal = ?, causeDescrInter = ?, "+
				"descriptionInter = ?, involvedInter = ?, actionInter = ?, damage_amount = ?, statusAction = ?, damageUnitySelect = ?, typeHour1 = ?, "+
				"typeHour2 = ?, typeHour3 = ?, typeHour4 = ?, typeHour5 = ?, typeHour6 = ? WHERE occ_number = ?";

		DateTimeApplication dtm = new DateTimeApplication();

		try {
			//atributos que ser�o atualizados quando o m�todo for chamado

			conn.start(1);
			conn.prepare(query);

			conn.setString(1, data.getType());
			conn.setString(2, data.getOrigin());
			conn.setString(3, data.getState_occurrences());
			conn.setString(4, data.getStart_date());
			conn.setString(5, data.getStart_hour());
			conn.setString(6, data.getStart_minute());
			conn.setString(7, data.getEnd_date());
			conn.setString(8, data.getEnd_hour());
			conn.setString(9, data.getEnd_minute());
			conn.setString(10, data.getCause());
			conn.setString(11, data.getCause_description());
			conn.setString(12, data.getKilometer());
			conn.setString(13, data.getHighway());
			conn.setString(14, data.getLocal_state());
			conn.setString(15, data.getDirection());
			conn.setString(16, data.getLane());
			conn.setString(17, data.getOthers());
			conn.setString(18, data.getLocal_condition());
			conn.setString(19, data.getTraffic());
			conn.setString(20, data.getCharacteristic());
			conn.setString(21, data.getInterference());
			conn.setString(22, data.getSignaling());
			conn.setString(23, data.getConductor_condition());
			conn.setString(24, data.getDescription_title());
			conn.setString(25, data.getDescription_text());
			conn.setString(26, data.getInvolved_type());
			conn.setString(27, data.getInvolved_When());
			conn.setString(28, data.getInvolved_description());
			conn.setString(29, data.getProcedure_name());
			conn.setString(30, data.getProcedure_description());
			conn.setString(31, data.getTraffic_hours());
			conn.setString(32, data.getTraffic_minutes());
			conn.setString(33, data.getTraffic_extension());
			conn.setString(34, data.getTraffic_stopped());
			conn.setString(35, data.getDamage_date());
			conn.setString(36, data.getDamage_type_damage());
			conn.setString(37, data.getDamage_gravity());
			conn.setString(38, data.getDemage_description());
			conn.setString(39, data.getAction_type());
			conn.setString(40, data.getAction_start());
			conn.setString(41, data.getAction_end());
			conn.setString(42, data.getAction_duration());
			conn.setString(43, data.getAction_description());
			conn.setString(44, data.getActionStartData());
			conn.setString(45, data.getActionStartHour());
			conn.setString(46, data.getActionStartMinute());
			conn.setString(47, data.getActionEndData());
			conn.setString(48, data.getActionEndHour());
			conn.setString(49, data.getActionEndMinute());
			conn.setString(50, data.getTrackStartDate());
			conn.setString(51, data.getTrackStartHour());
			conn.setString(52, data.getTrackStartMinute());
			conn.setString(53, data.getTrackEndDate());
			conn.setString(54, data.getTrackEndHour());
			conn.setString(55, data.getTrackEndMinute());
			conn.setString(56, data.getDamageDescriptionInternal());
			conn.setString(57, data.getCauseDescrInter());
			conn.setString(58, data.getDescriptionInter());
			conn.setString(59, data.getInvolvedInter());
			conn.setString(60, data.getActionInter());
			conn.setString(61, data.getDamage_amount());
			conn.setString(62, data.getStatusAction());
			conn.setString(63, data.getDamageUnity());
			conn.setString(64, data.getTypeHour1());
			conn.setString(65, data.getTypeHour2());
			conn.setString(66, data.getTypeHour3());
			conn.setString(67, data.getTypeHour4());
			conn.setString(68, data.getTypeHour5());
			conn.setString(69, data.getTypeHour6());
			conn.setString(70, data.getData_number());

			long answer = conn.executeUpdate();

			if(answer > 0) 
				status = true;

		}catch (Exception alterarOcorrencia){
			throw new Exception("Erro ao alterar dados: " + alterarOcorrencia);
		}finally {
			conn.close();
		}		

		return status;
	}

	private char[] actionStartData() {
		// TODO Auto-generated method stub
		return null;
	}

	//pegando o �ltimo registro do banco de dados
	public int GetId() throws Exception{

		String sql = "SELECT max(occ_number) FROM occ_data";

		int value = 0; 

		//tentar
		try {

			conn.start(1);
			conn.prepare(sql);
			MapResult result = conn.executeQuery();

			if(result.hasNext()) {
				for (RowResult rs : result) {

					value = rs.getInt(1);	   			
				}
			}
		}finally {
			conn.close();
		}

		//retornando o valor do �ltimo a id para a variavel (value)
		return value;

	}

	//m�todo PDF
	public OccurrencesData submitPdf(int PdfGet) throws Exception {
		TranslationMethods trad = new TranslationMethods();
		OccurrencesData occ = new OccurrencesData();

		//script onde pegamos os valores dos atributos
		String pdf = "SELECT  dt.value_, dt1.value_, dt2.value_, dt3.value_, dt4.value_, dt5.value_, dt6.value_, dt7.value_, dt8.value_, dt9.value_, dt10.value_, " + 
				"dt11.value_, dt12.value_, dt13.value_, dt14.value_, dt15.value_, dt16.value_, dt17.value_, dt18.value_, dt19.value_, dt20.value_ "+ 
				"FROM occ_data d "+ 
				"LEFT JOIN occ_details dt ON d.type = dt.detail_id " + 
				"LEFT JOIN occ_details dt1 ON d.origin = dt1.detail_id " + 
				"LEFT JOIN occ_details dt2 ON d.state_occurrence = dt2.detail_id " + 
				"LEFT JOIN occ_details dt3 ON d.cause = dt3.detail_id " + 
				"LEFT JOIN occ_details dt4 ON d.highway = dt4.detail_id " + 
				"LEFT JOIN occ_details dt5 ON d.local_state = dt5.detail_id " + 
				"LEFT JOIN occ_details dt6 ON d.direction = dt6.detail_id " + 
				"LEFT JOIN occ_details dt7 ON d.lane = dt7.detail_id " + 
				"LEFT JOIN occ_details dt8 ON d.local_condition = dt8.detail_id " + 
				"LEFT JOIN occ_details dt9 ON d.traffic = dt9.detail_id " + 
				"LEFT JOIN occ_details dt10 ON d.interference = dt10.detail_id " + 
				"LEFT JOIN occ_details dt11 ON d.signaling = dt11.detail_id " + 
				"LEFT JOIN occ_details dt12 ON d.conductor_condition = dt12.detail_id " + 
				"LEFT JOIN occ_details dt13 ON d.envolvTypo = dt13.detail_id " + 
				"LEFT JOIN occ_details dt14 ON d.trafficTrackInterrupted = dt14.detail_id " + 
				"LEFT JOIN occ_details dt15 ON d.damegeType = dt15.detail_id " + 
				"LEFT JOIN occ_details dt16 ON d.damageGravity = dt16.detail_id " + 
				"LEFT JOIN occ_details dt17 ON d.damageUnitySelect = dt17.detail_id " + 
				"LEFT JOIN occ_details dt18 ON d.actionType = dt18.detail_id " + 
				"LEFT JOIN occ_details dt19 ON d.statusAction = dt19.detail_id "  +
				"LEFT JOIN occ_details dt20 ON d.characteristic = dt20.detail_id " +
				"WHERE occ_number = ? ";

		try {

			conn.start(1);
			conn.prepare(pdf);
			conn.setInt(1, PdfGet);
			MapResult result = conn.executeQuery();

			if(result.hasNext()) {
				for (RowResult rs : result) {
					//valores dos atributos dentro do banco de dados
					occ.setType(rs.getString(1));
					occ.setOrigin(rs.getString(2));
					occ.setState_occurrences(rs.getString(3));
					occ.setCause(rs.getString(4));
					occ.setHighway(trad.occurrencesTranslator(rs.getString(5)));
					occ.setLocal_state(rs.getString(6));
					occ.setDirection(trad.occurrencesTranslator(rs.getString(7)));
					occ.setLane(rs.getString(8));
					occ.setLocal_condition(trad.occurrencesTranslator(rs.getString(9)));
					occ.setTraffic(trad.occurrencesTranslator(rs.getString(10)));
					occ.setInterference(trad.occurrencesTranslator(rs.getString(11)));
					occ.setSignaling(trad.occurrencesTranslator(rs.getString(12)));
					occ.setConductor_condition(trad.occurrencesTranslator(rs.getString(13)));
					occ.setInvolved_type(trad.occurrencesTranslator(rs.getString(14)));
					occ.setTraffic_stopped(trad.occurrencesTranslator(rs.getString(15)));
					occ.setDamage_type_damage(trad.occurrencesTranslator(rs.getString(16)));
					occ.setDamage_gravity(trad.occurrencesTranslator(rs.getString(17)));
					occ.setDamageUnity(trad.occurrencesTranslator(rs.getString(18)));
					occ.setAction_type(trad.occurrencesTranslator(rs.getString(19)));
					occ.setStatusAction(trad.occurrencesTranslator(rs.getString(20)));
					occ.setCharacteristic(rs.getString(21));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.close();
		}	
		//passando os valores dos atributos para dentro da var
		return occ;
	}
	public UserAccountBean user_id(int user) throws Exception {

		UserAccountBean occ = new UserAccountBean();
		String userId = "SELECT user_id from users_register ;";

		try {

			conn.start(1);
			conn.prepare(userId);
			conn.setInt(1, user);
			MapResult result = conn.executeQuery();

			if(result.hasNext()) {
				for (RowResult rs : result) {

					occ.getUser().setUser_id(rs.getInt(1));

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.close();
		}

		return occ;
	}
	public ArrayList<OccurrencesData> listarOcorrencias() throws Exception {
		String query = "SELECT d.occ_number, dt.value_, " +
				"CONCAT(start_date, '-', d.start_hour, ':', d.start_minute, d.typeHour1) 'datetime', " +
				"dt1.value_, dt2.value_ FROM occ_data d " +
				"INNER JOIN occ_details dt ON d.type = dt.detail_id " +
				"INNER JOIN occ_details dt1 ON d.cause = dt1.detail_id " +
				"INNER JOIN occ_details dt2 ON d.state_occurrence = dt2.detail_id";	

		ArrayList<OccurrencesData> listarOcc = new ArrayList<OccurrencesData>();
		//System.out.println(query);
		try {
			TranslationMethods occTranslation = new TranslationMethods();
			conn.start(1);
			conn.prepare(query);
			MapResult result = conn.executeQuery();

			if(result.hasNext()) {
				for (RowResult rs : result) {
					OccurrencesData occ = new OccurrencesData();
					occ.setData_number(String.valueOf(rs.getInt(1)));		
					occ.setType(occTranslation.listOcc(rs.getString(2)));
					occ.setDate_time(rs.getString(3));
					occ.setCause(occTranslation.listOcc(rs.getString(4)));
					occ.setState_occurrences(occTranslation.listOcc(rs.getString(5)));

					listarOcc.add(occ);
					//System.out.println(rs.getString(5));
				}
			}
		}finally {
			conn.close();
		}
		return listarOcc;
	}

	public boolean updateFilePath(String path, String occNumber) throws Exception {

		boolean status = false;

		String query = "UPDATE occ_data SET local_files = ? WHERE occ_number = ?";	

		try {

			conn.start(1);
			conn.prepare(query);

			conn.setString(1, path);
			conn.setString(2, occNumber);

			long res = conn.executeUpdate();

			if(res > 0)
				status = true;

		}catch (Exception alterarPath){
			throw new Exception("Erro ao alterar path: " + alterarPath);
		}finally {
			conn.close();
		}	

		return status;
	}
	public OccurrencesData rodovia(String id) {
		
		OccurrencesData occ = new OccurrencesData();
		String query = "SELECT road FROM dai_equipment INNER JOIN concessionaire_roads WHERE equip_id ='"+id+"' LIMIT 1";
		try {
			conn.start(1);
			conn.prepare(query);
			MapResult result = conn.executeQuery();
			
			if(result.hasNext()) {
				for (RowResult rs : result) {
					occ.setHighway(rs.getString(1));
				}	
			}

		}catch(Exception sqlbuscar) {
			sqlbuscar.printStackTrace();

		}finally {
			conn.close();
		}
		return occ;
	}
	public OccurrencesData direction(String name)throws Exception {
		String query = "SELECT direction FROM dai_equipment WHERE name = '"+name+"' ";
		OccurrencesData occ = new OccurrencesData();
		try {
			conn.start(1);
			conn.prepare(query);
			MapResult result = conn.executeQuery();
			
			if(result.hasNext()) {
				for (RowResult rs : result) {
					occ.setDirection(rs.getString(1));
				}	
			}

		}catch(Exception sqlbuscar) {
			sqlbuscar.printStackTrace();

		}finally {
			conn.close();
		}
		
		return occ;
	}
	public SOS getExternal(String id, String type) throws Exception {
		String query = "";
		if(type.equals("sos")) {
			query = "SELECT max(equip.equip_id) AS ID, name, city, road, km, direction, max(start_date) AS start_data, max(end_date) end_start"
					+ " FROM sos_equipment equip"
					+ " INNER JOIN sos_calls"
					+ " ON equip.equip_id ="+id+" and  sos_calls.equip_id ="+id;
		}/*else if(type.equals("dai")){
			System.out.println("dai aqui");
		}*/
		SOS sos = new SOS();

		try {
			conn.start(1);
			conn.prepare(query);
			MapResult result = conn.executeQuery();

			if(result.hasNext()) {
				for (RowResult rs : result) {

					sos.setEquip_ip(rs.getString(1));
					sos.setNome(rs.getString(2));
					sos.setCidade(rs.getString(3));
					sos.setEstrada(rs.getString(4));
					sos.setKm(rs.getString(5));
					sos.setDirection(rs.getString(6));
					sos.setStart_data(rs.getString(7));
					sos.setEnd_data(rs.getString(8));

				}	
			}

		}catch(Exception sqlbuscar) {
			sqlbuscar.printStackTrace();

		}finally {
			conn.close();
		}

		return sos;

	}
	//m�todo buscar ocorr�ncia por id
	public OccurrencesData buscarOcorrenciaPorId(int id) throws Exception {

		OccurrencesData occ = new OccurrencesData();

		//Script dos atributos que as infor��es ser�o requisitadas
		String query = "SELECT occ_number, type, origin, state_occurrence, start_date, start_hour, start_minute, end_date, end_hour, " +
				"end_minute, cause, cause_description, kilometer, highway, local_state, direction, lane, others, local_condition, " +
				"traffic, characteristic, interference, signaling, conductor_condition, descrTitleDescr, descrDescr, " +
				"envolvTypo, envolvWhen, envolvDescr, procedureName, procedureDescr, trafficHour, trafficMinute, " +
				"trafficKm, trafficTrackInterrupted, damageDate, damegeType, damageGravity, damageDescr, actionType, actionStart, actionEnd, " +
				"actionDuration, actionDescr, actionStartData, actionStartHour, actionStartMinute, actionEndData, actionEndHour, actionEndMinute, trackStartDate, " + 
				"trackStartHour, trackStartMinute, trackEndData, trackEndHour, trackEndMinute, damageDescriptionInternal, causeDescrInter, descriptionInter, " + 
				"involvedInter, actionInter, damage_amount, statusAction, damageUnitySelect, typeHour1, typeHour2, typeHour3, typeHour4, typeHour5, typeHour6, local_files, editTable, nameUser, lastDateUser " +
				"FROM occ_data WHERE occ_number = ?";
		DateTimeApplication dtm = new DateTimeApplication();

		try {

			conn.start(1);
			conn.prepare(query);
			conn.setInt(1, id);
			MapResult result = conn.executeQuery();

			if(result.hasNext()) {
				for (RowResult rs : result) {
					//atributos onde as informa��es est�o armazenadas
					occ.setData_number(rs.getString(1));
					occ.setType(rs.getString(2));
					occ.setOrigin(rs.getString(3)); 
					occ.setState_occurrences(rs.getString(4));
					occ.setStart_date(rs.getString(5));
					occ.setStart_hour(rs.getString(6));
					occ.setStart_minute(rs.getString(7));
					occ.setEnd_date(rs.getString(8));
					occ.setEnd_hour(rs.getString(9));
					occ.setEnd_minute(rs.getString(10));
					occ.setCause(rs.getString(11));
					occ.setCause_description(rs.getString(12));
					occ.setKilometer(rs.getString(13));
					occ.setHighway(rs.getString(14));
					occ.setLocal_state(rs.getString(15));
					occ.setDirection(rs.getString(16));
					occ.setLane(rs.getString(17));
					occ.setOthers(rs.getString(18));
					occ.setLocal_condition(rs.getString(19));
					occ.setTraffic(rs.getString(20));
					occ.setCharacteristic(rs.getString(21));
					occ.setInterference(rs.getString(22));
					occ.setSignaling(rs.getString(23));
					occ.setConductor_condition(rs.getString(24));
					occ.setDescription_title(rs.getString(25));
					occ.setDescription_text(rs.getString(26));
					occ.setInvolved_type(rs.getString(27));
					occ.setInvolved_When(rs.getString(28));
					occ.setInvolved_description(rs.getString(29));
					occ.setProcedure_name(rs.getString(30));
					occ.setProcedure_description(rs.getString(31));
					occ.setTraffic_hours(rs.getString(32));
					occ.setTraffic_minutes(rs.getString(33));
					occ.setTraffic_extension(rs.getString(34));
					occ.setTraffic_stopped(rs.getString(35));
					occ.setDamage_date(rs.getString(36));
					occ.setDamage_type_damage(rs.getString(37));
					occ.setDamage_gravity(rs.getString(38));
					occ.setDemage_description(rs.getString(39));
					occ.setAction_type(rs.getString(40));
					occ.setAction_start(rs.getString(41));
					occ.setAction_end(rs.getString(42));
					occ.setAction_duration(rs.getString(43));
					occ.setAction_description(rs.getString(44));
					occ.setActionStartData(rs.getString(45));
					occ.setActionStartHour(rs.getString(46));
					occ.setActionStartMinute(rs.getString(47));
					occ.setActionEndData(rs.getString(48));
					occ.setActionEndHour(rs.getString(49));
					occ.setActionEndMinute(rs.getString(50));
					occ.setTrackStartDate(rs.getString(51));
					occ.setTrackStartHour(rs.getString(52));
					occ.setTrackStartMinute(rs.getString(53));
					occ.setTrackEndDate(rs.getString(54));
					occ.setTrackEndHour(rs.getString(55));
					occ.setTrackEndMinute(rs.getString(56));
					occ.setDamageDescriptionInternal(rs.getString(57));
					occ.setCauseDescrInter(rs.getString(58));
					occ.setDescriptionInter(rs.getString(59));
					occ.setInvolvedInter(rs.getString(60));
					occ.setActionInter(rs.getString(61));
					occ.setDamage_amount(rs.getString(62));
					occ.setStatusAction(rs.getString(63));
					occ.setDamageUnity(rs.getString(64));
					occ.setTypeHour1(rs.getString(65));
					occ.setTypeHour2(rs.getString(66));
					occ.setTypeHour3(rs.getString(67));
					occ.setTypeHour4(rs.getString(68));
					occ.setTypeHour5(rs.getString(69));
					occ.setTypeHour6(rs.getString(70));
					occ.setLocalFiles(rs.getString(71));
					occ.setEditTable(rs.getBoolean(72));
					occ.setNameUser(rs.getString(73));
					occ.setLastDateHour(rs.getString(74));

				}

			}
		}catch(Exception sqlbuscar) {
			sqlbuscar.printStackTrace();

		}finally {
			conn.close();
		}
		//passando os valores dos atributos para a vari�vel occ
		return occ;

	}
}