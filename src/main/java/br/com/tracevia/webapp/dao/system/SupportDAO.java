package br.com.tracevia.webapp.dao.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.speed.Speed;

public class SupportDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	 
	public List<Support> Status() throws Exception {
			
				List<Support> list = new ArrayList<Support>();		
								
				String select = "SELECT name, email, FROM connection_monitor WHERE equip_type = 'SPEED' ";
				
		