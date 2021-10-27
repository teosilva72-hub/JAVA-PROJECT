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
    public List<String> IDs;

    public ReportDAO(List<String> columnName) throws Exception {
        
        conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

    	custom = !columnName.isEmpty();
        
        if (custom)
            this.columnName = columnName;
        
        lines = new ArrayList<>();
        
    }

    public void getReport(String query, String id) throws Exception {
       
    	List<String[]> lines = new ArrayList<>();
    	List<String> field = new ArrayList<>();

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
                    String name = rsmd.getColumnName(idx);
                    if (!custom)
                        this.columnName.add(name);

                    String value = rs.getString(idx);
                    
                    column[idx - 1] = value != null && value != "" ? value : "-";
                    if (id == name)
                        field.add(value);
                }

                lines.add(column);
            }

            this.lines = lines;
            this.IDs = field;
        }
    }

    public List<String[]> getOtherElementTable(String table, String column) throws SQLException {
        return getOtherElementTable(table, new String[]{ column, column });
    }
    
    public List<String[]> getOtherElementTable(String table, String[] column) throws SQLException {
    	List<String[]> fields = new ArrayList<>();
    	List<List<String>> fieldsTemp = new ArrayList<>();
        boolean doubleField = !column[0].equals(column[1]);
        for (int i = 0; i < (doubleField ? 2 : 1); i++)
        	fieldsTemp.add(new ArrayList<>());

        String query = String.format("SELECT %s FROM %s", doubleField ? String.format("%s, %s", column[0], column[1]) : column[0], table);

        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                for (int i = 0; i < (doubleField ? 2 : 1); i++) {
                    String value = rs.getString(i + 1);
                    if (value == null)
                        value = "";
                    if (!fieldsTemp.get(i).contains(value) && !value.isEmpty())
                    	fieldsTemp.get(i).add(value);
                    else if (i > 0)
                    	fieldsTemp.get(0).remove(0);
                    else
                    	break;
                }

            }
        }
        
        int size = fieldsTemp.get(0).size();
        for (int i = 0; i < size; i++) {
        	String value = fieldsTemp.get(0).get(i);
        	fields.add(new String[] { value, doubleField ? fieldsTemp.get(1).get(i) : value });
		}
        
        return fields;
    }

   
	public List<String> getColumnName() {
		return columnName;
	}

	public List<String[]> getLines() {
		return lines;
	}
	
	public List<String> getIDs() {
		return IDs;
	}
	
	
	
}