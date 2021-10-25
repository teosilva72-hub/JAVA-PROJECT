package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class ReportDAO {
	
    private Connection conn;	
	private PreparedStatement ps;
	private ResultSet rs;

    private boolean custom;

    public List<String> columnName;
    public List<String[]> lines;

    public ReportDAO(List<String> columnName) throws Exception {
        
    	custom = !columnName.isEmpty();
        
        if (custom)
            this.columnName = columnName;
        
        lines = new ArrayList<>();
        
    }

    public void getReport(String query) throws Exception {
       
    	List<String[]> lines = new ArrayList<>();
    	    	
        conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();

        if (!custom)
            this.columnName.clear();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
               
            	ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                String[] column = new String[columnsNumber];
                
                for (int idx = 1; idx <= columnsNumber; idx++) {
                    if (!custom)
                        this.columnName.add(rsmd.getColumnName(idx));
                    
                    column[idx - 1] = rs.getString(idx);
                }

                lines.add(column);
            }

            this.lines = lines;
        }
    }

   
	public List<String> getColumnName() {
		return columnName;
	}

	public List<String[]> getLines() {
		return lines;
	}
	
	
	
	
}