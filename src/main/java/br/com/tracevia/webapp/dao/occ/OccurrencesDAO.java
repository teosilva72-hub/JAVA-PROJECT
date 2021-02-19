package br.com.tracevia.webapp.dao.occ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.model.SelectItem;

import br.com.tracevia.webapp.controller.global.UserAccountBean;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.occ.OccurrencesData;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class OccurrencesDAO {
	private Connection conn;
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps;
	private ResultSet rs;

	public OccurrencesDAO() throws Exception {

		try {
			this.conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro: \n" + e.getMessage());
		}
	}

	/**
	 * @param data
	 * @return
	 * @throws Exception
	 */



	public String cadastroOcorrencia (OccurrencesData data ) throws Exception {

		String occ_number = null;

		String query = "INSERT INTO occ_data(occ_number, type, origin, state_occurrence, start_date, start_hour, start_minute, end_date, end_hour, end_minute, cause, cause_description, kilometer, highway, "
				+ "local_state, direction, lane, others, local_condition, traffic, characteristic, interference, signaling, conductor_condition, descrTitleDescr, descrDescr, envolvTypo, "
				+ "envolvWhen, envolvDescr, procedureName, procedureDescr, trafficHour, trafficMinute, trafficKm, trafficTrackInterrupted, damageDate, damegeType, damageGravity, damageDescr, "
				+ "actionType, actionStart, actionEnd, actionDuration, actionDescr, actionStartData, actionStartHour, actionStartMinute, actionEndData, actionEndHour, actionEndMinute, trackStartDate, "
				+ "trackStartHour, trackStartMinute, trackEndData, trackEndHour, trackEndMinute, damageDescriptionInternal, causeDescrInter, descriptionInter, involvedInter, actionInter, "
				+ "damage_amount, statusAction, damageUnitySelect) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		DateTimeApplication dtm = new DateTimeApplication();

		try {

			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(query);

			ps.setString(1, data.getData_number());
			ps.setString(2, data.getType());
			ps.setString(3, data.getOrigin());
			ps.setString(4, data.getState_occurrences());
			ps.setString(5, data.getStart_date());
			ps.setString(6, data.getStart_hour());
			ps.setString(7, data.getStart_minute());
			ps.setString(8, data.getEnd_date());
			ps.setString(9, data.getEnd_hour());
			ps.setString(10, data.getEnd_minute());
			ps.setString(11, data.getCause());
			ps.setString(12, data.getCause_description());
			ps.setString(13, data.getKilometer());
			ps.setString(14, data.getHighway());
			ps.setString(15, data.getLocal_state());
			ps.setString(16, data.getDirection());
			ps.setString(17, data.getLane());
			ps.setString(18, data.getOthers());
			ps.setString(19, data.getLocal_condition());
			ps.setString(20, data.getTraffic());
			ps.setString(21, data.getCharacteristic());
			ps.setString(22, data.getInterference());
			ps.setString(23, data.getSignaling());
			ps.setString(24, data.getConductor_condition());
			ps.setString(25, data.getDescription_title());
			ps.setString(26, data.getDescription_text());
			ps.setString(27, data.getInvolved_type());
			ps.setString(28, data.getInvolved_When());
			ps.setString(29, data.getInvolved_description());
			ps.setString(30, data.getProcedure_name());
			ps.setString(31, data.getProcedure_description());
			ps.setString(32, data.getTraffic_hours());
			ps.setString(33, data.getTraffic_minutes());
			ps.setString(34, data.getTraffic_extension());
			ps.setString(35, data.getTraffic_stopped());
			ps.setString(36, data.getDamage_date());
			ps.setString(37, data.getDamage_type_damage());
			ps.setString(38, data.getDamage_gravity());
			ps.setString(39, data.getDemage_description());
			ps.setString(40, data.getAction_type());
			ps.setString(41, data.getAction_start());
			ps.setString(42, data.getAction_end());
			ps.setString(43, data.getAction_duration());
			ps.setString(44, data.getAction_description());
			ps.setString(45, data.getActionStartData()); 
			ps.setString(46, data.getActionStartHour()); 
			ps.setString(47, data.getActionStartMinute()); 
			ps.setString(48, data.getActionEndData()); 
			ps.setString(49, data.getActionEndHour()); 
			ps.setString(50, data.getActionEndMinute()); 
			ps.setString(51, data.getTrackStartDate()); 
			ps.setString(52, data.getTrackStartHour()); 
			ps.setString(53, data.getTrackStartMinute()); 
			ps.setString(54, data.getTrackEndDate()); 
			ps.setString(55, data.getTrackEndHour()); 
			ps.setString(56, data.getTrackEndMinute()); 
			ps.setString(57, data.getDamageDescriptionInternal());
			ps.setString(58, data.getCauseDescrInter());
			ps.setString(59, data.getDescriptionInter());
			ps.setString(60, data.getInvolvedInter());
			ps.setString(61, data.getActionInter());
			ps.setString(62, data.getDamage_amount()); 
			ps.setString(63, data.getStatusAction()); 
			ps.setString(64, data.getDamageUnity()); 

			int res = ps.executeUpdate();

			if(res > 0) {

				String query2 = "Select MAX(occ_number) AS last_Id  FROM occ_data";

				ps = conn.prepareStatement(query2);

				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()) {	

						occ_number = rs.getString("last_Id");

					}
				}   	 

			}

		}catch (SQLException inserirOcorrencia){
			throw new Exception("Erro ao inserir dados: " + inserirOcorrencia);
		}finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return occ_number;

	}

	public ArrayList<SelectItem> dropDownFieldValues (String field) throws Exception {	

		String query = "SELECT detail_id, value_ FROM tracevia_app.occ_details WHERE field = ? and active = 1";

		ArrayList<SelectItem> listarDropDownValue = new ArrayList<SelectItem>();
		/*System.out.println(query);*/

		try {

			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(query);
			ps.setString(1, field);

			rs = ps.executeQuery();

			if(rs != null) {
				while(rs.next()) {
					SelectItem listarDrop = new SelectItem();
					listarDrop.setValue(rs.getInt(1));
					listarDrop.setLabel(rs.getString(2));

					listarDropDownValue.add(listarDrop);
				}
			}

		}finally {

			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		return listarDropDownValue;
	}


	public boolean atualizarOcorrencia(OccurrencesData data) throws Exception {
		// System.out.println("DATA: "+data.getData_number()+"\nType: "+data.getAction_type());
		boolean status = false;

		String query = "UPDATE occ_data SET type = ? , origin = ?, state_occurrence = ?, start_date = ?, start_hour = ?, " +
				"start_minute = ?, end_date = ?, end_hour = ?, end_minute = ?, cause = ?, cause_description = ?, kilometer = ?, highway = ?, " +
				"local_state = ?, direction = ?, lane = ?, others = ?, local_condition = ?, traffic = ?, characteristic = ?, interference = ?, " +
				"signaling = ?, conductor_condition = ?, descrTitleDescr = ?, descrDescr = ?, envolvTypo = ?, envolvWhen = ?, envolvDescr = ?, " +
				"procedureName = ?, procedureDescr = ?, trafficHour = ?, trafficMinute = ?, trafficKm = ?, trafficTrackInterrupted= ?, damageDate= ?, " + 
				" damegeType = ?, damageGravity = ?, damageDescr = ?, actionType = ?, actionStart = ?, actionEnd = ?, actionDuration = ?, actionDescr = ?, " +
				"actionStartData = ?, actionStartHour = ?, actionStartMinute = ?, actionEndData = ?, actionEndHour = ?, actionEndMinute = ?, trackStartDate = ?, " +
				"trackStartHour = ?, trackStartMinute = ?, trackEndData = ?, trackEndHour = ?, trackEndMinute = ?, damageDescriptionInternal = ?, causeDescrInter = ?, "+
				"descriptionInter = ?, involvedInter = ?, actionInter = ?, damage_amount = ?, statusAction = ?, damageUnitySelect = ? WHERE occ_number = ?";

		DateTimeApplication dtm = new DateTimeApplication();
		System.out.println(data.getActionStartData());
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(query);

			ps.setString(1, data.getType());
			ps.setString(2, data.getOrigin());
			ps.setString(3, data.getState_occurrences());
			ps.setString(4, data.getStart_date());
			ps.setString(5, data.getStart_hour());
			ps.setString(6, data.getStart_minute());
			ps.setString(7, data.getEnd_date());
			ps.setString(8, data.getEnd_hour());
			ps.setString(9, data.getEnd_minute());
			ps.setString(10, data.getCause());
			ps.setString(11, data.getCause_description());
			ps.setString(12, data.getKilometer());
			ps.setString(13, data.getHighway());
			ps.setString(14, data.getLocal_state());
			ps.setString(15, data.getDirection());
			ps.setString(16, data.getLane());
			ps.setString(17, data.getOthers());
			ps.setString(18, data.getLocal_condition());
			ps.setString(19, data.getTraffic());
			ps.setString(20, data.getCharacteristic());
			ps.setString(21, data.getInterference());
			ps.setString(22, data.getSignaling());
			ps.setString(23, data.getConductor_condition());
			ps.setString(24, data.getDescription_title());
			ps.setString(25, data.getDescription_text());
			ps.setString(26, data.getInvolved_type());
			ps.setString(27, data.getInvolved_When());
			ps.setString(28, data.getInvolved_description());
			ps.setString(29, data.getProcedure_name());
			ps.setString(30, data.getProcedure_description());
			ps.setString(31, data.getTraffic_hours());
			ps.setString(32, data.getTraffic_minutes());
			ps.setString(33, data.getTraffic_extension());
			ps.setString(34, data.getTraffic_stopped());
			ps.setString(35, data.getDamage_date());
			ps.setString(36, data.getDamage_type_damage());
			ps.setString(37, data.getDamage_gravity());
			ps.setString(38, data.getDemage_description());
			ps.setString(39, data.getAction_type());
			ps.setString(40, data.getAction_start());
			ps.setString(41, data.getAction_end());
			ps.setString(42, data.getAction_duration());
			ps.setString(43, data.getAction_description());
			ps.setString(44, data.getActionStartData());
			ps.setString(45, data.getActionStartHour());
			ps.setString(46, data.getActionStartMinute());
			ps.setString(47, data.getActionEndData());
			ps.setString(48, data.getActionEndHour());
			ps.setString(49, data.getActionEndMinute());
			ps.setString(50, data.getTrackStartDate());
			ps.setString(51, data.getTrackStartHour());
			ps.setString(52, data.getTrackStartMinute());
			ps.setString(53, data.getTrackEndDate());
			ps.setString(54, data.getTrackEndHour());
			ps.setString(55, data.getTrackEndMinute());
			ps.setString(56, data.getDamageDescriptionInternal());
			ps.setString(57, data.getCauseDescrInter());
			ps.setString(58, data.getDescriptionInter());
			ps.setString(59, data.getInvolvedInter());
			ps.setString(60, data.getActionInter());
			ps.setString(61, data.getDamage_amount());
			ps.setString(62, data.getStatusAction());
			ps.setString(63, data.getDamageUnity());
			ps.setString(64, data.getData_number());

			int answer = ps.executeUpdate();

			if(answer > 0) 
				status = true;

		}catch (SQLException alterarOcorrencia){
			throw new Exception("Erro ao alterar dados: " + alterarOcorrencia);
		}finally {
			ConnectionFactory.closeConnection(conn, ps);
		}		

		return status;
	}

	private char[] actionStartData() {
		// TODO Auto-generated method stub
		return null;
	}
	public int GetId() throws Exception{

		String sql = "SELECT max(occ_number) FROM occ_data";

		int value = 0; 

		try {

			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if(rs != null) {
				while(rs.next()) {

					 value = rs.getInt(1);	   			
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return value;

	}
	public OccurrencesData submitPdf(int PdfGet) throws Exception {

		OccurrencesData occ = new OccurrencesData();

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

			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(pdf);
			ps.setInt(1, PdfGet);
			rs = ps.executeQuery();

			if(rs != null) {
				while(rs.next()) {
					
					occ.setType(rs.getString(1));
					occ.setOrigin(rs.getString(2));
					occ.setState_occurrences(rs.getString(3));
					occ.setCause(rs.getString(4));
					occ.setHighway(rs.getString(5));
					occ.setLocal_state(rs.getString(6));
					occ.setDirection(rs.getString(7));
					occ.setLane(rs.getString(8));
					occ.setLocal_condition(rs.getString(9));
					occ.setTraffic(rs.getString(10));
					occ.setInterference(rs.getString(11));
					occ.setSignaling(rs.getString(12));
					occ.setConductor_condition(rs.getString(13));
					occ.setInvolved_type(rs.getString(14));
					occ.setTraffic_stopped(rs.getString(15));
					occ.setDamage_type_damage(rs.getString(16));
					occ.setDamage_gravity(rs.getString(17));
					occ.setDamageUnity(rs.getString(18));
					occ.setAction_type(rs.getString(19));
					occ.setStatusAction(rs.getString(20));
					occ.setCharacteristic(rs.getString(21));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return occ;
	}
	public UserAccountBean user_id(int user) throws Exception {

		UserAccountBean occ = new UserAccountBean();
		String userId = "SELECT user_id from users_register ;";
		
		try {

			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(userId);
			ps.setInt(1, user);
			rs = ps.executeQuery();

			if(rs != null) {
				while(rs.next()) {
					
					occ.setUser_id(rs.getString(1));
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return occ;
	}
	public ArrayList<OccurrencesData> listarOcorrencias() throws Exception {

		String query = "SELECT d.occ_number, dt.value_, " +
				"CONCAT(start_date, '-', d.start_hour, ':', d.start_minute) 'datetime', " +
				"dt1.value_, dt2.value_ FROM occ_data d " +
				"INNER JOIN occ_details dt ON d.type = dt.detail_id " +
				"INNER JOIN occ_details dt1 ON d.cause = dt1.detail_id " +
				"INNER JOIN occ_details dt2 ON d.state_occurrence = dt2.detail_id";	

		ArrayList<OccurrencesData> listarOcc = new ArrayList<OccurrencesData>();
		System.out.println(query);
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if(rs != null) {
				while(rs.next()) {
					OccurrencesData occ = new OccurrencesData();
					occ.setData_number(String.valueOf(rs.getInt(1)));		
					occ.setType(rs.getString(2));
					occ.setDate_time(rs.getString(3));
					occ.setCause(rs.getString(4));
					occ.setState_occurrences(rs.getString(5));

					listarOcc.add(occ);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		return listarOcc;
	}

	public boolean updateFilePath(String path, String occNumber) throws Exception {

		boolean status = false;

		String query = "UPDATE occ_data SET local_files = ? WHERE occ_number = ?";	

		try {

			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(query);

			ps.setString(1, path);
			ps.setString(2, occNumber);

			int res = ps.executeUpdate();

			if(res > 0)
				status = true;

		}catch (SQLException alterarPath){
			throw new Exception("Erro ao alterar path: " + alterarPath);
		}finally {
			ConnectionFactory.closeConnection(conn, ps);
		}	

		return status;
	}

	public OccurrencesData buscarOcorrenciaPorId(int id) throws Exception {

		OccurrencesData occ = new OccurrencesData();

		String query = "SELECT occ_number, type, origin, state_occurrence, start_date, start_hour, start_minute, end_date, end_hour, " +
				"end_minute, cause, cause_description, kilometer, highway, local_state, direction, lane, others, local_condition, " +
				"traffic, characteristic, interference, signaling, conductor_condition, descrTitleDescr, descrDescr, " +
				"envolvTypo, envolvWhen, envolvDescr, procedureName, procedureDescr, trafficHour, trafficMinute, " +
				"trafficKm, trafficTrackInterrupted, damageDate, damegeType, damageGravity, damageDescr, actionType, actionStart, actionEnd, " +
				"actionDuration, actionDescr, actionStartData, actionStartHour, actionStartMinute, actionEndData, actionEndHour, actionEndMinute, trackStartDate, " + 
				"trackStartHour, trackStartMinute, trackEndData, trackEndHour, trackEndMinute, damageDescriptionInternal, causeDescrInter, descriptionInter, " + 
				"involvedInter, actionInter, damage_amount, statusAction, damageUnitySelect, local_files " +
				"FROM occ_data WHERE occ_number = ?";
		DateTimeApplication dtm = new DateTimeApplication();

		try {

			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if(rs != null) {
				while(rs.next()) {

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
					occ.setLocalFiles(rs.getString(65));

				}

			}
		}catch(SQLException sqlbuscar) {
			sqlbuscar.printStackTrace();

		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return occ;

	}
}