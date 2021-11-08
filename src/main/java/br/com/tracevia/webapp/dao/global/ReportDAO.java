package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class ReportDAO {
	
    private Connection conn;	
	private PreparedStatement ps;
	private ResultSet rs;

    private boolean custom;

    public List<String> columnName;
    public List<String[]> lines;
    public List<Pair<String, List<String[]>>> secondaryLines;
    public List<String> IDs;

    public ReportDAO(List<String> columnName) throws Exception {
        
        conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

        if (columnName == null)
        	columnName = new ArrayList<>();
        
    	custom = !columnName.isEmpty();
    	
        this.columnName = columnName;
        
        lines = new ArrayList<>();
        
    }

    public void getReport(String query, String id, String[] division) throws Exception {
       
    	List<String[]> lines = new ArrayList<>();
    	List<String> field = new ArrayList<>();
    	List<String[]> allOptions = new ArrayList<>();

        if (division != null) {
            String search = "";
            String[] div = Arrays.copyOfRange(division, 1, division.length);
            allOptions = this.getOtherElementTable(division[0], div);

            for (String[] option : allOptions) {
                search += String.format(", '%s'", option[0]);
            }

            query = query.replace("@division", search.substring(2));
        }

        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();

        if (!custom)
            this.columnName.clear();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
               
            	ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                String[] row = new String[columnsNumber];
                
                for (int idx = 1; idx <= columnsNumber; idx++) {
                    String name = rsmd.getColumnName(idx);
                    if (!custom)
                        this.columnName.add(name);

                    String value = rs.getString(idx);
                    
                    row[idx - 1] = value != null && value != "" ? value : "-";
                    if (name.equals(id) && !field.contains(value))
                        field.add(value);
                }

                lines.add(row);
            }
        }

        this.lines = lines;
        this.IDs = field;

        if (division != null)
            this.completeSecondary(query, allOptions);
    }

    private void completeSecondary(String query, List<String[]> fields) throws Exception {     
    	List<Pair<String, List<String[]>>> secondaryLines = new ArrayList<>();
    	
        for (int index = 0; index < fields.size(); index++) {
            List<String[]> secondaryList = new ArrayList<>();
            String[] division = fields.get(index);

            String newQuery = query.replace("@division", String.format("'%s'", division[0]));

            System.out.println(newQuery);
            
            ps = conn.prepareStatement(newQuery);
            rs = ps.executeQuery();
    
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                   
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnsNumber = rsmd.getColumnCount();
                    String[] column = new String[columnsNumber];
                    
                    for (int idx = 1; idx <= columnsNumber; idx++) {
    
                        String value = rs.getString(idx);
                        
                        column[idx - 1] = value != null && value != "" ? value : "-";
                    }
    
                    secondaryList.add(column);
                }
            }
            
            secondaryLines.add(new Pair<String, List<String[]>>(division[1], secondaryList));
        }
        
        this.secondaryLines = secondaryLines;
    }

    public List<String[]> getOtherElementTable(String table, String column) throws SQLException {
        return getOtherElementTable(table, new String[]{ column, column });
    }
    
    public List<String[]> getOtherElementTable(String table, String[] column) throws SQLException {
    	List<String[]> fields = new ArrayList<>();
    	List<List<String>> fieldsTemp = new ArrayList<>();
        String select = "";
        for (String col : column) {
            select += String.format(", %s", col);
            
        	fieldsTemp.add(new ArrayList<>());
        }

        int tempSize = fieldsTemp.size();
        String query = String.format("SELECT DISTINCT %s FROM %s GROUP BY %1$s", select.substring(2), table);

        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                for (int i = 0; i < tempSize; i++) {
                    String value = rs.getString(i + 1);
                    if (value == null)
                        value = "";
                    if ((!fieldsTemp.get(i).contains(value) && !value.isEmpty() || i > 0))
                    	fieldsTemp.get(i).add(value);
                    else
                    	break;
                }

            }
        }
        
        int size = fieldsTemp.get(0).size();
        for (int i = 0; i < size; i++) {
            String[] val = new String[tempSize];
            for (int r = 0; r < tempSize; r++) {
                List<String> temp = fieldsTemp.get(r);
                val[r] = temp.get(i);
            }
        	fields.add(val);
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