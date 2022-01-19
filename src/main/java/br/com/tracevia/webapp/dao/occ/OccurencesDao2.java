package br.com.tracevia.webapp.dao.occ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.tracevia.webapp.controller.global.UserAccountBean;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.occ.OccurenceData2;
import br.com.tracevia.webapp.model.occ.OccurrencesData;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class OccurencesDao2 {
	
		private Connection conn;
		private PreparedStatement ps;
		private ResultSet rs;

		//CREATE OCCURRENCE
		public String cadastroOcorrencia ( OccurenceData2 data ) throws Exception {

			String occ_number = null;
			
			//script BD
			String query = "INSERT INTO occ_data2(date, hora, pedagio, folio, report, sinistro, direcao, "
					+ "kmregistro, kminicial, kmfinal, horaregistro, horachega, politica, "
					+ " tipoveic, quantidade, numero, marca, tipo_veic, modelo, color, placa, telefone, nome, "
					+ "idade, saude, motivo,observacao "
					+  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			try {

				conn = ConnectionFactory.connectToTraceviaApp();
				ps = conn.prepareStatement(query);
				
				//passando valores para os atributos BD
				ps.setString(1, data.getDate());
				ps.setString(2, data.getHora());
				ps.setString(3, data.getPedagio());
				ps.setString(4, data.getFolio());
				ps.setString(5, data.getReport());
				ps.setString(6, data.getSinistro());
				ps.setString(7, data.getDirecao());
				ps.setString(8, data.getKmregistro());
				ps.setString(9, data.getKminicial());
				ps.setString(10, data.getKmfinal());
				ps.setString(11, data.getHrReg());
				ps.setString(12, data.getHrchega());
				ps.setString(13, data.getPolitica());
				ps.setString(14, data.getTipo_veic());
				ps.setString(15, data.getQuantidade());
				ps.setString(16, data.getNumveiculo());
				ps.setString(17, data.getMarca());	
				ps.setString(18, data.getTipo_veic());
				ps.setString(19, data.getModelo());
				ps.setString(20, data.getCor());
				ps.setString(21, data.getPlaca());
				ps.setString(22, data.getTelefone());
				ps.setString(23, data.getNome());
				ps.setString(24, data.getIdade());
				ps.setString(25, data.getSaude());
				ps.setString(26, data.getMotivo());
				ps.setString(26, data.getObservacao());
				
			int res = ps.executeUpdate();
			
				//se a vari�vel for maior do que 0, acessamos a essa fun��o
				if(res > 0) {
					
					//pengando o �ltimo valor do banco de dados
					String query2 = "Select MAX(occ_number) AS last_Id  FROM occ_data2";

					ps = conn.prepareStatement(query2);

					rs = ps.executeQuery();

					if(rs != null) {
						while(rs.next()) {	
							//atribuindo para a variavel occ_number o �ltimo valor do banco de dados
							occ_number = rs.getString("last_Id");

						}
					}   	 

				}
				
				

			}catch (SQLException inserirOcorrencia){
				throw new Exception("Erro ao inserir dados: " + inserirOcorrencia);
			}finally {
				ConnectionFactory.closeConnection(conn, ps);
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

				conn = ConnectionFactory.connectToTraceviaApp();
				ps = conn.prepareStatement(query);
				ps.setString(1, field);

				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()) {
						SelectItem listarDrop = new SelectItem();
						listarDrop.setValue(rs.getInt(1));
						listarDrop.setLabel(occTranslation.occurrencesTranslator(rs.getString(2)));
																
						listarDropDownValue.add(listarDrop);
					}
				}

			}finally {

				ConnectionFactory.closeConnection(conn, ps, rs);
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
				conn = ConnectionFactory.connectToTraceviaApp();

				ps = conn.prepareStatement(query);

				ps.setBoolean(1, editTable);
				ps.setString(2, name_user);
				ps.setInt(3, accessLevel);
				ps.setString(4, id);

				ps.executeUpdate();


			}catch (SQLException alterarOcorrencia){

				throw new Exception("Erro ao alterar dados: " + alterarOcorrencia);

			}finally {

				ConnectionFactory.closeConnection(conn, ps);

			}		

			return status;
			}
		
		
		public boolean lastUser(String id, String lastData, String user) throws Exception {
			
			boolean status = false;
			
			String query = "UPDATE occ_data2 SET lastDateUser = ?, lastUser = ? WHERE occ_number = ?";
			DateTimeApplication dtm = new DateTimeApplication();
			try {
				conn = ConnectionFactory.connectToTraceviaApp();
				ps = conn.prepareStatement(query);
				ps.setString(1, lastData);
				ps.setString(2, user);
				ps.setString(3, id);
				
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

		
		//m�todo atualizar ocorr�ncia 
		public boolean atualizarOcorrencia(OccurenceData2 data) throws Exception {
			// System.out.println("DATA: "+data.getData_number()+"\nType: "+data.getAction_type());
			boolean status = false;
			//script dos atributos que ser�o atualizados as informa�oes do banco de dados
			String query = "UPDATE occ_data SET data = ? , hora = ?, pedagio = ?, folio = ?, report = ?, " +
					"sinistro = ?, direcao = ?, kmregistro = ?, kminicial = ?, kmfinal = ?, hrReg = ?, hrchega = ?, politica = ?, " +
					"tipo_veic = ?, modelo = ?, cor = ?, placa = ?, telefone = ?, nome = ?, idade = ?, saude = ?, " +
					"motivo = ?, observacao = ?";
				
			try {
				//atributos que ser�o atualizados quando o m�todo for chamado
				conn = ConnectionFactory.connectToTraceviaApp();
				ps = conn.prepareStatement(query);
				
				ps.setString(1, data.getDate());
				ps.setString(2, data.getHora());
				ps.setString(3, data.getPedagio());
				ps.setString(4, data.getFolio());
				ps.setString(5, data.getReport());
				ps.setString(6, data.getSinistro());
				ps.setString(7, data.getDirecao());
				ps.setString(8, data.getKmregistro());
				ps.setString(9, data.getKminicial());
				ps.setString(10, data.getKmfinal());
				ps.setString(11, data.getHrReg());
				ps.setString(12, data.getHrchega());
				ps.setString(13, data.getPolitica());
				ps.setString(14, data.getTipo_veic());
				ps.setString(15, data.getQuantidade());
				ps.setString(16, data.getNumveiculo());
				ps.setString(17, data.getMarca());	
				ps.setString(18, data.getTipo_veic());
				ps.setString(19, data.getModelo());
				ps.setString(20, data.getCor());
				ps.setString(21, data.getPlaca());
				ps.setString(22, data.getTelefone());
				ps.setString(23, data.getNome());
				ps.setString(24, data.getIdade());
				ps.setString(25, data.getSaude());
				ps.setString(26, data.getMotivo());
				ps.setString(26, data.getObservacao());

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
		
		public OccurenceData2 submitPdf(int PdfGet) throws Exception {
			TranslationMethods trad = new TranslationMethods();
			
			OccurenceData2 occ = new OccurenceData2();

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

				conn = ConnectionFactory.connectToTraceviaApp();
				ps = conn.prepareStatement(pdf);
				ps.setInt(1, PdfGet);
				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()) {
						
						
						
						occ.setDate(rs.getString(1));
						occ.setHora(rs.getString(2));
						occ.setPedagio(rs.getString(3));
						occ.setFolio(rs.getString(4));
						occ.setReport(rs.getString(5));
						occ.setSinistro(rs.getString(6));
						occ.setDirecao(rs.getString(7));
						occ.setKmregistro(rs.getString(8));
						occ.setKminicial(rs.getString(9));
						occ.setKmfinal(rs.getString(10));
						occ.setHrReg(rs.getString(11));
						occ.setHrchega(rs.getString(12));
						occ.setPolitica(rs.getString(13));
						occ.setTipo_veic(rs.getString(14));
						occ.setQuantidade(rs.getString(15));
						occ.setNumveiculo(rs.getString(16));
						occ.setMarca(rs.getString(17));	
						occ.setTipo_veic(rs.getString(18));
						occ.setModelo(rs.getString(19));
						occ.setCor(rs.getString(20));
						occ.setPlaca(rs.getString(21));
						occ.setTelefone(rs.getString(22));
						occ.setNome(rs.getString(23));
						occ.setIdade(rs.getString(24));
						occ.setSaude(rs.getString(25));
						occ.setMotivo(rs.getString(26));
						occ.setObservacao(rs.getString(27));
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				ConnectionFactory.closeConnection(conn, ps, rs);
			}	
			//passando os valores dos atributos para dentro da var
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

					occ.getUser().setUser_id(rs.getInt(1));

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return occ;} 
	

		public ArrayList<OccurenceData2> listarOcorrencias() throws Exception {

			String query = "SELECT d.occ_number, dt.value_, " +
					"CONCAT(start_date, '-', d.start_hour, ':', d.start_minute, d.typeHour1) 'datetime', " +
					"dt1.value_, dt2.value_ FROM occ_data d " +
					"INNER JOIN occ_details dt ON d.type = dt.detail_id " +
					"INNER JOIN occ_details dt1 ON d.cause = dt1.detail_id " +
					"INNER JOIN occ_details dt2 ON d.state_occurrence = dt2.detail_id";	

			ArrayList<OccurenceData2> listarOcc = new ArrayList<OccurenceData2>();
			//System.out.println(query);
			try {
				TranslationMethods occTranslation = new TranslationMethods();
				conn = ConnectionFactory.connectToTraceviaApp();
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()) {
						OccurrencesData occ = new OccurrencesData();
						occ.setData_number(String.valueOf(rs.getInt(1)));		
						occ.setType(occTranslation.listOcc(rs.getString(2)));
						occ.setDate_time(rs.getString(3));
						occ.setCause(occTranslation.listOcc(rs.getString(4)));
						occ.setState_occurrences(occTranslation.listOcc(rs.getString(5)));

//						listarOcc.add(occ);
						//System.out.println(rs.getString(5));
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
		
		public OccurenceData2 buscarOcorrenciaPorId(int id) throws Exception {

			OccurenceData2 occ = new OccurenceData2();
			
			//Script dos atributos que as infor��es ser�o requisitadas
			String query = "SELECT occ_number, data = ? , hora = ?, pedagio = ?, folio = ?, report = ?, \" +\r\n" + 
					"sinistro = ?, direcao = ?, kmregistro = ?, kminicial = ?, kmfinal = ?, hrReg = ?, hrchega = ?, politica = ?, \" +\r\n" + 
					"tipo_veic = ?, modelo = ?, cor = ?, placa = ?, telefone = ?, nome = ?, idade = ?, saude = ?, \" +\r\n" + 
					"motivo = ?, observacao = ?";
			DateTimeApplication dtm = new DateTimeApplication();

			try {

				conn = ConnectionFactory.connectToTraceviaApp();
				ps = conn.prepareStatement(query);
				ps.setInt(1, id);
				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()) {
						//atributos onde as informa��es est�o armazenadas
						occ.setDate(rs.getString(1));
						occ.setHora(rs.getString(2));
						occ.setPedagio(rs.getString(3));
						occ.setFolio(rs.getString(4));
						occ.setReport(rs.getString(5));
						occ.setSinistro(rs.getString(6));
						occ.setDirecao(rs.getString(7));
						occ.setKmregistro(rs.getString(8));
						occ.setKminicial(rs.getString(9));
						occ.setKmfinal(rs.getString(10));
						occ.setHrReg(rs.getString(11));
						occ.setHrchega(rs.getString(12));
						occ.setPolitica(rs.getString(13));
						occ.setTipo_veic(rs.getString(14));
						occ.setQuantidade(rs.getString(15));
						occ.setNumveiculo(rs.getString(16));
						occ.setMarca(rs.getString(17));	
						occ.setTipo_veic(rs.getString(18));
						occ.setModelo(rs.getString(19));
						occ.setCor(rs.getString(20));
						occ.setPlaca(rs.getString(21));
						occ.setTelefone(rs.getString(22));
						occ.setNome(rs.getString(23));
						occ.setIdade(rs.getString(24));
						occ.setSaude(rs.getString(25));
						occ.setMotivo(rs.getString(26));
						occ.setObservacao(rs.getString(27));
					}
				}

				}catch(SQLException sqlbuscar) {
					sqlbuscar.printStackTrace();

				}finally {
					ConnectionFactory.closeConnection(conn, ps, rs);
				}
				//passando os valores dos atributos para a vari�vel occ
				return occ;

			}
		
		public int GetId() throws Exception{

			String sql = "SELECT max(occ_number) FROM occ_data2";

			int value = 0; 
			
			//tentar
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
			
			//retornando o valor do �ltimo a id para a variavel (value)
			return value;

		}
		
		}
