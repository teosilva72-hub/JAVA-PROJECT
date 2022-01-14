package br.com.tracevia.webapp.dao.occ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.occ.Occurence2;
import br.com.tracevia.webapp.model.occ.OccurrencesData;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class OccurencesDao2 {
	
		private Connection conn;
		private PreparedStatement ps;
		private ResultSet rs;

		//CREATE OCCURRENCE
		public String cadastroOcorrencia ( Occurence2 data ) throws Exception {

			String occ_number = null;
			
			//script BD
			String query = "INSERT INTO occ_data2( data, hora, pedagio, folio, report, sinistro, direcao, kmregistro, kminicial, kmfinal, horaregistro, horachega, politica, "
					+ " tipoveic, quantidade, numero, marca, tipo_veic, modelo, color, placa, telefone, nome, idade, saude, motivo,observacao "
					+  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			try {

				conn = ConnectionFactory.connectToTraceviaApp();
				ps = conn.prepareStatement(query);
				
				//passando valores para os atributos BD
				ps.setString(1, data.getData());
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
				ps.setInt(24, data.getIdade());
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
		
		//m�todo atualizar ocorr�ncia 
		public boolean atualizarOcorrencia(Occurence2 data) throws Exception {
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
				
				conn = ConnectionFactory.connectToTraceviaApp();
				ps = conn.prepareStatement(query);
				
				ps.setString(1, data.getData());
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
				ps.setInt(24, data.getIdade());
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
}
