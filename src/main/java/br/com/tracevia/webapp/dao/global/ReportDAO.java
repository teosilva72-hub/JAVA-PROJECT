package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
        
        conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

    	custom = !columnName.isEmpty();
        
        if (custom)
            this.columnName = columnName;
        
        lines = new ArrayList<>();
        
    }

    public void getReport(String query) throws Exception {
       
    	List<String[]> lines = new ArrayList<>();

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

                    String value = rs.getString(idx);
                    
                    column[idx - 1] = value != null && value != "" ? value : "-";
                }

                lines.add(column);
            }

            this.lines = lines;
        }
    }

    public List<String> getOtherElementTable(String table, String column) throws SQLException {
        List<String> fields = new ArrayList<>();

        String query = String.format("SELECT %s FROM %s", column, table);

        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                String value = rs.getString(1);
                if (value == null)
                    value = "";
                if (!fields.contains(value) && !value.isEmpty())
                    fields.add(value);
            }
        } 
        return fields;
    }

   
	public List<String> getColumnName() {
		return columnName;
	}

	public List<String[]> getLines() {
		return lines;
	}
	
	
	
	
}