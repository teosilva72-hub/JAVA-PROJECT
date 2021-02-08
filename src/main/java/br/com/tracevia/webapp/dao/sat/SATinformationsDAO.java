package br.com.tracevia.webapp.dao.sat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tracevia.webapp.cfg.RoadConcessionairesEnum;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class SATinformationsDAO {
	
	private Connection conn;		
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps;
	private ResultSet rs;
		
	public List<SAT> RealTimeSATinfo() throws Exception {
			
		List<SAT> list = new ArrayList<SAT>();
		DateTimeApplication dta = new DateTimeApplication();
		
		String currentDate = null;
			
		Calendar calendar = Calendar.getInstance();	
		int minute = calendar.get(Calendar.MINUTE);
		
		//Obter datas formatadas para os dados
		currentDate = dta.getCurrentDateDados15(calendar, minute);
		
		System.out.println(currentDate);
					
		String select = "SELECT d.NOME_ESTACAO, d.DATA_HORA, " +
		
		"CASE " +
	    "WHEN eq.number_lanes = 2 OR eq.number_lanes = 3  THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1, d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL )), 0), 0) " +
		"WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VOLUME_TOTAL , NULL )), 0), 0) " +

	    "ELSE 0 " +
	    "END 'VOLUME_TOTAL_S1', " +

	    "CASE " +
	    "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 , d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7 OR d.NOME_FAIXA = 8, d.VOLUME_TOTAL , NULL )), 0), 0) " +

	    "ELSE 0 " +
	    "END 'VOLUME_TOTAL_S2', " +

	    "CASE " +
	    "WHEN eq.number_lanes = 2 OR eq.number_lanes = 3 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 1, d.VEL_MEDIA_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
		"WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +

	    "ELSE 0 " +
	    "END 'VEL_MEDIA_TOTAL_S1', " +

	    "CASE " +
	    "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 , d.VEL_MEDIA_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7 OR d.NOME_FAIXA = 8, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +

	    "ELSE 0 " +
	    "END 'VEL_MEDIA_TOTAL_S2' " +
				    
	    "FROM tracevia_app.sat_dados_15 d " +
	    "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
	    "WHERE DATA_HORA = DATE_SUB( ? , INTERVAL 15 MINUTE) AND eq.visible = 1 " +
	    "GROUP BY d.NOME_ESTACAO " +
	    "ORDER BY d.DATA_HORA ASC ";
					
	  try {
			
		  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);			
			ps.setString(1, currentDate);		
						
			rs = ps.executeQuery();
			
			System.out.println(select);
			
			if (rs != null) {
				while (rs.next()) {
					
					SAT sat = new SAT();

					sat.setEquip_id(rs.getInt("d.NOME_ESTACAO"));
					sat.setQuantidadeS1(rs.getInt("VOLUME_TOTAL_S1"));						
					sat.setVelocidadeS1(rs.getInt("VEL_MEDIA_TOTAL_S1"));	
					sat.setQuantidadeS2(rs.getInt("VOLUME_TOTAL_S2"));	
					sat.setVelocidadeS2(rs.getInt("VEL_MEDIA_TOTAL_S2"));	
															
					list.add(sat);
				}				
			 }			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return list;
		
	}
	
	
	public List<SAT> RealTimeSATinfo15Before() throws Exception {
		
		List<SAT> list = new ArrayList<SAT>();
		DateTimeApplication dta = new DateTimeApplication();
		
		String currentDate = null;
			
		Calendar calendar = Calendar.getInstance();	
		int minute = calendar.get(Calendar.MINUTE);
		
		//Obter datas formatadas para os dados
		currentDate = dta.getCurrentDateDados15(calendar, minute);
		
		System.out.println(currentDate);
					
		String select = "SELECT d.NOME_ESTACAO, d.DATA_HORA, " +
		
		"CASE " +
	    "WHEN eq.number_lanes = 2 OR eq.number_lanes = 3  THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1, d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL )), 0), 0) " +
		"WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VOLUME_TOTAL , NULL )), 0), 0) " +

	    "ELSE 0 " +
	    "END 'VOLUME_TOTAL_S1', " +

	    "CASE " +
	    "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 , d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7 OR d.NOME_FAIXA = 8, d.VOLUME_TOTAL , NULL )), 0), 0) " +

	    "ELSE 0 " +
	    "END 'VOLUME_TOTAL_S2', " +

	    "CASE " +
	    "WHEN eq.number_lanes = 2 OR eq.number_lanes = 3 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 1, d.VEL_MEDIA_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
		"WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +

	    "ELSE 0 " +
	    "END 'VEL_MEDIA_TOTAL_S1', " +

	    "CASE " +
	    "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 , d.VEL_MEDIA_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7 OR d.NOME_FAIXA = 8, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +

	    "ELSE 0 " +
	    "END 'VEL_MEDIA_TOTAL_S2' " +
				    
	    "FROM tracevia_app.sat_dados_15 d " +
	    "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
	    "WHERE DATA_HORA = DATE_SUB( ? , INTERVAL 30 MINUTE) AND eq.visible = 1 " +
	    "GROUP BY d.NOME_ESTACAO " +
	    "ORDER BY d.DATA_HORA ASC";
					
	  try {
			
		  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
		  
			ps = conn.prepareStatement(select);			
			ps.setString(1, currentDate);		
						
			rs = ps.executeQuery();
			
			System.out.println("30MINALL: "+select);
			
			if (rs != null) {
				while (rs.next()) {
					
					SAT sat = new SAT();

					sat.setEquip_id(rs.getInt("d.NOME_ESTACAO"));
					sat.setQuantidadeS1(rs.getInt("VOLUME_TOTAL_S1"));						
					sat.setVelocidadeS1(rs.getInt("VEL_MEDIA_TOTAL_S1"));	
					sat.setQuantidadeS2(rs.getInt("VOLUME_TOTAL_S2"));	
					sat.setVelocidadeS2(rs.getInt("VEL_MEDIA_TOTAL_S2"));	
															
					list.add(sat);
				}				
			 }			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return list;
		
	}
	
	public SAT RealTimeSATinfo15Data(int equip) throws Exception {
		
		SAT sat = new SAT();
		DateTimeApplication dta = new DateTimeApplication();
		
		String currentDate = null;
			
		Calendar calendar = Calendar.getInstance();	
		int minute = calendar.get(Calendar.MINUTE);
		
		//Obter datas formatadas para os dados
		currentDate = dta.getCurrentDateDados15(calendar, minute);
		
		System.out.println(currentDate);
					
		String select = "SELECT d.NOME_ESTACAO, d.DATA_HORA, " +
		
		"CASE " +
	    "WHEN eq.number_lanes = 2 OR eq.number_lanes = 3  THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1, d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL )), 0), 0) " +
		"WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VOLUME_TOTAL , NULL )), 0), 0) " +

	    "ELSE 0 " +
	    "END 'VOLUME_TOTAL_S1', " +

	    "CASE " +
	    "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 , d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VOLUME_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7 OR d.NOME_FAIXA = 8, d.VOLUME_TOTAL , NULL )), 0), 0) " +

	    "ELSE 0 " +
	    "END 'VOLUME_TOTAL_S2', " +

	    "CASE " +
	    "WHEN eq.number_lanes = 2 OR eq.number_lanes = 3 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 1, d.VEL_MEDIA_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
		"WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +

	    "ELSE 0 " +
	    "END 'VEL_MEDIA_TOTAL_S1', " +

	    "CASE " +
	    "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 , d.VEL_MEDIA_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +
	    "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7 OR d.NOME_FAIXA = 8, d.VEL_MEDIA_TOTAL , NULL )), 0), 0) " +

	    "ELSE 0 " +
	    "END 'VEL_MEDIA_TOTAL_S2' " +
				    
	    "FROM tracevia_app.sat_dados_15 d " +
	    "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
	    "WHERE eq.equip_id = ? AND DATA_HORA = DATE_SUB( ? , INTERVAL 30 MINUTE) AND eq.visible = 1 ";
	  					
	  try {
			
		  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);
			ps.setInt(1, equip);	
			ps.setString(2, currentDate);		
						
			rs = ps.executeQuery();
			
			System.out.println("30MINSOL: "+select);
			
			if (rs != null) {
				while (rs.next()) {
				
					sat.setEquip_id(rs.getInt("d.NOME_ESTACAO"));
					sat.setQuantidadeS1(rs.getInt("VOLUME_TOTAL_S1"));						
					sat.setVelocidadeS1(rs.getInt("VEL_MEDIA_TOTAL_S1"));	
					sat.setQuantidadeS2(rs.getInt("VOLUME_TOTAL_S2"));	
					sat.setVelocidadeS2(rs.getInt("VEL_MEDIA_TOTAL_S2"));	
									
				}				
			 }			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return sat;
		
	}
		
	public List<SAT> SATstatus15() throws Exception {
		
		List<SAT> list = new ArrayList<SAT>();
		DateTimeApplication dta = new DateTimeApplication();
		
		String currentDate = null, currentDateSub = null;
	
		Calendar calendar = Calendar.getInstance();	
		int minute = calendar.get(Calendar.MINUTE);
		
		//Obter datas formatadas para os dados
		currentDate = dta.getCurrentDateDados15(calendar, minute);
		
		//Obter datas formatadas para os dados
		currentDateSub = dta.getCurrentDateSubDados15(calendar, minute);
				
		String select = "SELECT s.EQ_ID, SUM(s.ONLINE_STATUS) 'STATUS' FROM tracevia_app.sat_status s " +
		"INNER JOIN sat_equipment eq on (eq.equip_id = s.EQ_ID) " +
		"WHERE s.DATA_HORA between DATE_SUB( ? , INTERVAL 15 MINUTE) AND ? AND eq.visible = 1 " +
		"GROUP BY s.EQ_ID " +
		"ORDER BY s.DATA_HORA ASC ";
				
		    try {
			
		    	conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
						
			ps = conn.prepareStatement(select);			
			ps.setString(1, currentDate);		
			ps.setString(2, currentDateSub);
			
			System.out.println(currentDate+"\n"+currentDateSub);
					
			rs = ps.executeQuery();
			
			System.out.println(select);
			
			if (rs != null) {
				while (rs.next()) {
					
					SAT sat = new SAT();

					sat.setEquip_id(rs.getInt("s.EQ_ID"));					
					sat.setStatus(rs.getInt("STATUS"));	
					
					System.out.println(sat.getStatus());
																		
					list.add(sat);
				}				
			 }			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return list;		
	}
	
public List<SAT> SATstatus30() throws Exception {
		
		List<SAT> list = new ArrayList<SAT>();
		DateTimeApplication dta = new DateTimeApplication();
		
		String currentDate = null, currentDateSub = null;
	
		Calendar calendar = Calendar.getInstance();	
		int minute = calendar.get(Calendar.MINUTE);
		
		//Obter datas formatadas para os dados
		currentDate = dta.getCurrentDateDados15(calendar, minute);
		
		//Obter datas formatadas para os dados
		currentDateSub = dta.getCurrentDateSubDados30(calendar, minute);
				
		String select = "SELECT s.EQ_ID, SUM(s.ONLINE_STATUS) 'STATUS' FROM tracevia_app.sat_status s " +
		"INNER JOIN sat_equipment eq on (eq.equip_id = s.EQ_ID) " +
		"WHERE s.DATA_HORA between DATE_SUB( ? , INTERVAL 30 MINUTE) AND ? AND eq.visible = 1 " +
		"GROUP BY s.EQ_ID " +
		"ORDER BY s.DATA_HORA ASC ";
				
		    try {
			
		    	conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);			
			ps.setString(1, currentDate);		
			ps.setString(2, currentDateSub);
			
			System.out.println(currentDate+"\n"+currentDateSub);
					
			rs = ps.executeQuery();
			
			System.out.println("30 min: "+select);
			
			if (rs != null) {
				while (rs.next()) {
					
					SAT sat = new SAT();

					sat.setEquip_id(rs.getInt("s.EQ_ID"));					
					sat.setStatus(rs.getInt("STATUS"));	
					
					System.out.println(sat.getStatus());
																		
					list.add(sat);
				}				
			 }			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return list;		
	}
	
	//FOR CCR
	public SAT SATstatus15Before(int equip) throws Exception {
		
		SAT sat = new SAT();
		DateTimeApplication dta = new DateTimeApplication();
		
		String currentDate = null, currentDateSub = null;
	
		Calendar calendar = Calendar.getInstance();	
		int minute = calendar.get(Calendar.MINUTE);
		
		//Obter datas formatadas para os dados
		currentDate = dta.getCurrentDateDados15(calendar, minute);
		
		//Obter datas formatadas para os dados
		currentDateSub = dta.getCurrentDateSubDados30(calendar, minute);
				
		String select = "SELECT s.EQ_ID, SUM(s.ONLINE_STATUS) 'STATUS' FROM tracevia_app.sat_status s " +
		"INNER JOIN sat_equipment eq on (eq.equip_id = s.EQ_ID) " +
		"WHERE eq.equip_id = ? AND s.DATA_HORA between DATE_SUB( ? , INTERVAL 30 MINUTE) AND ? AND eq.visible = 1 ";
						
		    try {
			
		    	conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);	
			ps.setInt(1, equip);	
			ps.setString(2, currentDate);		
			ps.setString(3, currentDateSub);
			
			System.out.println("BEF: "+currentDate+"\nBEF: "+currentDateSub);
					
			rs = ps.executeQuery();
			
			System.out.println("15BEFORE: "+select);
			
			if (rs != null) {
				while (rs.next()) {
					
					sat.setEquip_id(rs.getInt("s.EQ_ID"));					
					sat.setStatus(rs.getInt("STATUS"));	
				
				}		
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return sat;		
	}
		
}
