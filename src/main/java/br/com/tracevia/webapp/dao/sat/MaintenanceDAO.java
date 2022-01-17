package br.com.tracevia.webapp.dao.sat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.log.SystemLog;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.sat.Maintenance;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class MaintenanceDAO {
				
		private Connection conn;			
		private PreparedStatement ps;
		private ResultSet rs;
				
		// --------------------------------------------------------------------------------------------------------------
		
		// LOGS FOLDER
		
		private final String errorFolder = SystemLog.ERROR.concat("maintenance-dao\\");
		
		// --------------------------------------------------------------------------------------------------------------
			
		public MaintenanceDAO() {
			
			SystemLog.createLogFolder(errorFolder);			
			
		}
		
		// --------------------------------------------------------------------------------------------------------------
		
		/** Método para obter status dos equipamentos (SAT)
		 * @author Wellington 13/01/2021 
		 * @version 1.0
		 * @since 1.0		
		 * @return lista de objetos do tipo Maintenance com status de cada equipamento
		 *  
		 */	
		
		public List<Maintenance> getDadosOn(String[] hours, String[] days){

			List<Maintenance> lista = new ArrayList<Maintenance>();
			HashMap<Integer, Maintenance> map = new HashMap<>();
			Maintenance dados;
			
			DateTimeApplication dta = new DateTimeApplication();
													
			String currentDate = null;
				
			Calendar calendar = Calendar.getInstance();	
			int minute = calendar.get(Calendar.MINUTE);
			
			// Obter datas formatadas para os dados
			currentDate = dta.getCurrentDateDados15(calendar, minute);
					
			int[] stateZero = new int[24];
			int[] stateFifteen = new int[24];
			int[] stateThirty = new int[24];
			int[] stateFortyFive = new int[24];
						
			int day = 0;
			int hour = 0;
			int min = 0;
			int hourIndex = 0;
							
			String select = "SELECT DATE_FORMAT(d.DATA_HORA,\"%Y-%m-%d\") AS data, " +
						"DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(d.DATA_HORA) - TIME_TO_SEC(d.DATA_HORA)%(15*60))),\"%H:%i\") AS intervals, " +
						"d.EQ_ID 'siteId', " +
					    "SUM(d.ONLINE_STATUS) 'Status', eq.number_lanes 'lanes' " +
					    "FROM tb_status d " +
						"INNER JOIN sat_equipment eq on eq.equip_id = d.EQ_ID " +
						"WHERE d.DATA_HORA BETWEEN DATE_SUB(?, INTERVAL '23:59' HOUR_MINUTE) AND ? AND eq.visible = 1 " +
						"GROUP BY data, intervals, siteId " +
						"ORDER BY data ASC";
				
			try {
				
				System.out.println(currentDate);
				
				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				
				ps = conn.prepareStatement(select);			
				ps.setString(1, currentDate);		
				ps.setString(2, currentDate);
				
				rs = ps.executeQuery();

				if (rs.isBeforeFirst()) {
					while (rs.next()) {
						int id = rs.getInt(3);

						if (map.containsKey(id)) {
							dados = map.get(id);

							stateZero = dados.getStatusZero();
							stateFifteen = dados.getStatusFifteen();
							stateThirty = dados.getStatusThirty();
							stateFortyFive = dados.getStatusFortyFive();
						} else {
							dados = new Maintenance();
						}
						dados.setData(rs.getString(1));
						dados.setHora(rs.getString(2));
						dados.setSiteId(rs.getInt(3));					
						dados.setNumberLanes(rs.getInt(5));	

						day = Integer.parseInt(dados.getData().substring(8, 10));
						hour = Integer.parseInt(dados.getHora().substring(0, 2));
						min =  Integer.parseInt(dados.getHora().substring(3, 5));
																	
						for(int h = 0; h < 24; h++)
							if(Integer.parseInt(hours[h]) == hour && Integer.parseInt(days[h]) == day)
								hourIndex = h;
										
						if(rs.getInt(4) >= 8) {
							if(min == 0) {
								stateZero[hourIndex] = 1;
													
							}if(min == 15) {							
								stateFifteen[hourIndex] = 1;							
							
							}if(min == 30) {								
								stateThirty[hourIndex] = 1;	
															
							}if(min == 45) {								
								stateFortyFive[hourIndex] = 1;	
							}
						} else {
							if(min == 0) {
								stateZero[hourIndex] = 0;
														
							}if(min == 15) {								
								stateFifteen[hourIndex] = 0;
									
							
							}if(min == 30) {								
								stateThirty[hourIndex] = 0;									
							
							}if(min == 45) {								
								stateFortyFive[hourIndex] = 0;	
							}															
						}
							
						dados.setStatusZero(stateZero.clone());
						dados.setStatusFifteen(stateFifteen.clone());
						dados.setStatusThirty(stateThirty.clone());
						dados.setStatusFortyFive(stateFortyFive.clone());

						map.put(id, dados);
					}		
					lista.addAll(map.values());
				}

			} catch (SQLException sqle) {
				
				StringWriter errors = new StringWriter();
				sqle.printStackTrace(new PrintWriter(errors));
				
				SystemLog.logErrorSQL(errorFolder.concat("error_get_status"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
								
			} finally {
				
				ConnectionFactory.closeConnection(conn, ps, rs);
			}

			return lista;
		}

	// -------------------------------------------------------------------------------------------------------------------------------------------------
		
		/** Método para obter status dos dados por faixa dos equipamentos (SAT)
		 * @author Wellington 13/01/2021 
		 * @version 1.0
		 * @since 1.0		
		 * @return lista de objetos do tipo Maintenance contendo status dos dados por faixa
		 *  
		 */	
		public ArrayList<Maintenance> getDados15(String[] hours, String[] days) {

			ArrayList<Maintenance> lista = new ArrayList<Maintenance>();
			
			DateTimeApplication dta = new DateTimeApplication();
			
			String currentDate = null;
				
			Calendar calendar = Calendar.getInstance();	
			int minute = calendar.get(Calendar.MINUTE);
			
			// Obter datas formatadas para os dados
			currentDate = dta.getCurrentDateDados15(calendar, minute);	
			
			int[][] laneZero = new int[8][24];
			int[][] laneFifteen = new int[8][24];
			int[][] laneThirty = new int[8][24];
			int[][] laneFortyFive = new int[8][24];
			
			int day = 0;
			int hour = 0;
			int min = 0;
			int hourIndex = 0;
					
			String select = "SELECT DATE_FORMAT(d.DATA_HORA,\"%Y-%m-%d\") AS data, " +
					"DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(d.DATA_HORA)- TIME_TO_SEC(d.DATA_HORA)%(15*60))),\"%H:%i\") AS intervals, d.NOME_ESTACAO 'siteId', " +
					"d.NOME_FAIXA 'faixa', (d.VOLUME_TOTAL) 'volume' " +
					"FROM tb_dados15 d " +
					"INNER JOIN sat_equipment eq on eq.equip_id = d.NOME_ESTACAO " +
					"WHERE d.DATA_HORA BETWEEN DATE_SUB( ?, INTERVAL '23:59' HOUR_MINUTE) AND ? AND eq.visible = 1 " +
					"GROUP BY data, intervals, siteId, faixa " +
					"ORDER BY data ASC";
			
			try {
				
				  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
					
					ps = conn.prepareStatement(select);			
					ps.setString(1, currentDate);		
					ps.setString(2, currentDate);
					
					rs = ps.executeQuery();
					
					//System.out.println(select);
																		
					if (rs.isBeforeFirst()) {
						   while (rs.next()) {
						
						Maintenance dados = new Maintenance();
						
						dados.setData(rs.getString(1));
						dados.setHora(rs.getString(2));
						dados.setSiteId(rs.getInt(3));
						dados.setLane(rs.getInt(4));
						dados.setVolume(rs.getInt(5));	
																		
						// PROCESSAR DADOS PARA FRONT END
						
						day = Integer.parseInt(dados.getData().substring(8, 10));
						hour = Integer.parseInt(dados.getHora().substring(0, 2));
						min =  Integer.parseInt(dados.getHora().substring(3, 5));
																		
						  for(int h = 0; h < 24; h++) {
						     
							 if(Integer.parseInt(hours[h]) == hour && Integer.parseInt(days[h]) == day)
						         hourIndex = h;	
							
						 }			
						  
						//  System.out.println(min+" "+hourIndex);
											
						switch(dados.getLane()) {
																			
							case 1: 
																
								if(dados.getVolume() > 0) {
									
									if(min == 0) 									
										laneZero[0][hourIndex] = 1;
										
									 if(min == 15) 							
										 laneFifteen[0][hourIndex] = 1;							
									
									 if(min == 30) 								
										 laneThirty[0][hourIndex] = 1;	
																	
									 if(min == 45) 								
										 laneFortyFive[0][hourIndex] = 1;																
																									
								}else {
									
									  if(min == 0) 
											laneZero[0][hourIndex] = 0;
																
									  if(min == 15)								
											laneFifteen[0][hourIndex] = 0;											
									
									  if(min == 30)								
											laneThirty[0][hourIndex] = 0;									
									
									  if(min == 45) 								
											laneFortyFive[0][hourIndex] = 0;	
																										
								   }
								
							break;	
							
							// -------------------------------------
							
							case 2: 
															
								if(dados.getVolume() > 0) {
																											
									if(min == 0) 									
										laneZero[1][hourIndex] = 1;
										
									 if(min == 15) 							
										 laneFifteen[1][hourIndex] = 1;							
									
									 if(min == 30) 								
										 laneThirty[1][hourIndex] = 1;	
																	
									 if(min == 45) 								
										 laneFortyFive[1][hourIndex] = 1;	
									 																																
								}else {
									
									  if(min == 0) 
										   laneZero[1][hourIndex] = 0;
																
									  if(min == 15)								
										   laneFifteen[1][hourIndex] = 0;											
									
									  if(min == 30)								
										   laneThirty[1][hourIndex] = 0;									
									
									  if(min == 45) 								
										   laneFortyFive[1][hourIndex] = 0;	
																										
								   }
								
							break;
							
							// -------------------------------------
																	
							case 3: 
								
								if(dados.getVolume() > 0) {
									
									if(min == 0) 									
										laneZero[2][hourIndex] = 1;
										
									 if(min == 15) 							
										 laneFifteen[2][hourIndex] = 1;							
									
									 if(min == 30) 								
										 laneThirty[2][hourIndex] = 1;	
																	
									 if(min == 45) 								
										 laneFortyFive[2][hourIndex] = 1;																
																									
								}else {
									
									  if(min == 0) 
											laneZero[2][hourIndex] = 0;
																
									  if(min == 15)								
											laneFifteen[2][hourIndex] = 0;											
									
									  if(min == 30)								
											laneThirty[2][hourIndex] = 0;									
									
									  if(min == 45) 								
											laneFortyFive[2][hourIndex] = 0;	
																										
								   }
								
							break;
							
							// -------------------------------------
							
							case 4: 
	
								if(dados.getVolume() > 0) {
									
									if(min == 0) 									
										laneZero[3][hourIndex] = 1;
										
									 if(min == 15) 							
										 laneFifteen[3][hourIndex] = 1;							
									
									 if(min == 30) 								
										 laneThirty[3][hourIndex] = 1;	
																	
									 if(min == 45) 								
										 laneFortyFive[3][hourIndex] = 1;																
																									
								}else {
									
									  if(min == 0) 
											laneZero[3][hourIndex] = 0;
																
									  if(min == 15)								
											laneFifteen[3][hourIndex] = 0;											
									
									  if(min == 30)								
											laneThirty[3][hourIndex] = 0;									
									
									  if(min == 45) 								
											laneFortyFive[3][hourIndex] = 0;	
																										
								   }
								
							break;
							
							// -------------------------------------
							
							case 5: 
								
								if(dados.getVolume() > 0) {
									
									if(min == 0) 									
										laneZero[4][hourIndex] = 1;
										
									 if(min == 15) 							
										 laneFifteen[4][hourIndex] = 1;							
									
									 if(min == 30) 								
										 laneThirty[4][hourIndex] = 1;	
																	
									 if(min == 45) 								
										 laneFortyFive[4][hourIndex] = 1;																
																									
								}else {
									
									  if(min == 0) 
											laneZero[4][hourIndex] = 0;
																
									  if(min == 15)								
											laneFifteen[4][hourIndex] = 0;											
									
									  if(min == 30)								
											laneThirty[4][hourIndex] = 0;									
									
									  if(min == 45) 								
											laneFortyFive[4][hourIndex] = 0;	
																										
								   }
								
							break;
							
							// -------------------------------------
							
							case 6: 
								
								if(dados.getVolume() > 0) {
									
									if(min == 0) 									
										laneZero[5][hourIndex] = 1;
										
									 if(min == 15) 							
										 laneFifteen[5][hourIndex] = 1;							
									
									 if(min == 30) 								
										 laneThirty[5][hourIndex] = 1;	
																	
									 if(min == 45) 								
										 laneFortyFive[5][hourIndex] = 1;																
																									
								}else {
									
									  if(min == 0) 
											laneZero[5][hourIndex] = 0;
																
									  if(min == 15)								
											laneFifteen[5][hourIndex] = 0;											
									
									  if(min == 30)								
											laneThirty[5][hourIndex] = 0;									
									
									  if(min == 45) 								
											laneFortyFive[5][hourIndex] = 0;	
																										
								   }
								
							break;
							
							// -------------------------------------
							
							case 7: 
								
								if(dados.getVolume() > 0) {
									
									if(min == 0) 									
										laneZero[6][hourIndex] = 1;
										
									 if(min == 15) 							
										 laneFifteen[6][hourIndex] = 1;							
									
									 if(min == 30) 								
										 laneThirty[6][hourIndex] = 1;	
																	
									 if(min == 45) 								
										 laneFortyFive[6][hourIndex] = 1;																
																									
								}else {
									
									  if(min == 0) 
											laneZero[6][hourIndex] = 0;
																
									  if(min == 15)								
											laneFifteen[6][hourIndex] = 0;											
									
									  if(min == 30)								
											laneThirty[6][hourIndex] = 0;									
									
									  if(min == 45) 								
											laneFortyFive[6][hourIndex] = 0;	
																										
								   }
								
							break;
							
							// -------------------------------------
							
							case 8: 
								
								if(dados.getVolume() > 0) {
									
									if(min == 0) 									
										laneZero[7][hourIndex] = 1;
										
									 if(min == 15) 							
										 laneFifteen[7][hourIndex] = 1;							
									
									 if(min == 30) 								
										 laneThirty[7][hourIndex] = 1;	
																	
									 if(min == 45) 								
										 laneFortyFive[7][hourIndex] = 1;																
																									
								}else {
									
									  if(min == 0) 
											laneZero[7][hourIndex] = 0;
																
									  if(min == 15)								
											laneFifteen[7][hourIndex] = 0;											
									
									  if(min == 30)								
											laneThirty[7][hourIndex] = 0;									
									
									  if(min == 45) 								
											laneFortyFive[7][hourIndex] = 0;	
																										
								   }
								
							break;
																			
						}
						
						   dados.setLaneZero(laneZero);
						   dados.setLaneFifteen(laneFifteen);
						   dados.setLaneThirty(laneThirty);
						   dados.setLaneFortyFive(laneFortyFive);				

						lista.add(dados);
						
					}	
				}

			} catch (SQLException sqle) {
				
				StringWriter errors = new StringWriter();
				sqle.printStackTrace(new PrintWriter(errors));
				
				SystemLog.logErrorSQL(errorFolder.concat("error_get_data"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
					
				
			} finally {
				
				ConnectionFactory.closeConnection(conn, ps, rs);
				
			}

			return lista;
		}
	
		 // -------------------------------------------------------------------------------------------------------------------------------------------------
	}
