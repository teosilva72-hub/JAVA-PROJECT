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
import br.com.tracevia.webapp.util.LocaleUtil;

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
       
    	String newQuery = query;
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

            newQuery = query.replace("@division", search.substring(2));
        }

        ps = conn.prepareStatement(newQuery);
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

          //  System.out.println(newQuery);
            
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
    	
    	LocaleUtil locale = new LocaleUtil();
    	locale.getResourceBundle(LocaleUtil.LABELS_REPORTS);
    	    	    	
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
                    String value = translateFilters(locale, rs.getString(i + 1));
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
    
    // ------------------------------------------------------------------------------------
    
    private String translateFilters(LocaleUtil locale, String value) {
    	
    	switch (value) {
    	
			case "2 axles": return locale.getStringKey("reports_filter_2_axles_option");
			case "3 axles": return locale.getStringKey("reports_filter_3_axles_option");
			case "4 axles": return locale.getStringKey("reports_filter_4_axles_option");
			case "5 axles": return locale.getStringKey("reports_filter_5_axles_option");
			case "6 axles": return locale.getStringKey("reports_filter_6_axles_option");
			case "7 axles": return locale.getStringKey("reports_filter_7_axles_option");
			case "8 axles": return locale.getStringKey("reports_filter_8_axles_option");
			case "9 axles": return locale.getStringKey("reports_filter_9_axles_option");
			case "10 axles": return locale.getStringKey("reports_filter_10_axles_option");
    		case "light": return locale.getStringKey("reports_filter_light_class_option");
    		case "moto": return locale.getStringKey("reports_filter_moto_class_option");
    		case "heavy": return locale.getStringKey("reports_filter_heavy_vehicles_option");
    		case "trailer": return locale.getStringKey("reports_filter_trailer_class_option");
    		case "semi-trailer": return locale.getStringKey("reports_filter_semi_trailer_class_option");      		
    		case "heavy 2 axles": return locale.getStringKey("reports_filter_heavy_2_axles_class_option"); 
    		case "heavy 3 axles": return locale.getStringKey("reports_filter_heavy_3_axles_class_option");
    		case "heavy 4 axles": return locale.getStringKey("reports_filter_heavy_4_axles_class_option");
    		case "heavy 5 axles": return locale.getStringKey("reports_filter_heavy_5_axles_class_option");
    		case "heavy 6 axles": return locale.getStringKey("reports_filter_heavy_6_axles_class_option");
    		case "heavy 7 axles": return locale.getStringKey("reports_filter_heavy_7_axles_class_option");
    		case "heavy 8 axles": return locale.getStringKey("reports_filter_heavy_8_axles_class_option");
    		case "heavy 9 axles": return locale.getStringKey("reports_filter_heavy_9_axles_class_option");
    		case "heavy 10 axles": return locale.getStringKey("reports_filter_heavy_10_axles_class_option");
    		case "2 axle bus": return locale.getStringKey("reports_filter_bus_2_axles_class_option");
    		case "3 axle bus": return locale.getStringKey("reports_filter_bus_3_axles_class_option");
    		case "4 axle bus": return locale.getStringKey("reports_filter_bus_4_axles_class_option");
    		case "5 axle bus": return locale.getStringKey("reports_filter_bus_5_axles_class_option");
    		case "6 axle bus": return locale.getStringKey("reports_filter_bus_6_axles_class_option");
    		case "2 axle truck": return locale.getStringKey("reports_filter_truck_2_axles_class_option");
    		case "3 axle truck": return locale.getStringKey("reports_filter_truck_3_axles_class_option");
    		case "4 axle truck": return locale.getStringKey("reports_filter_truck_4_axles_class_option");
    		case "5 axle truck": return locale.getStringKey("reports_filter_truck_5_axles_class_option");
    		case "6 axle truck": return locale.getStringKey("reports_filter_truck_6_axles_class_option");
    		case "7 axle truck": return locale.getStringKey("reports_filter_truck_7_axles_class_option");
    		case "8 axle truck": return locale.getStringKey("reports_filter_truck_8_axles_class_option");
    		case "9 axle truck": return locale.getStringKey("reports_filter_truck_9_axles_class_option");
    		case "10 axle truck": return locale.getStringKey("reports_filter_truck_10_axles_class_option");
    		    		
    		default: return value;
    	}
    }

    // ------------------------------------------------------------------------------------
   
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