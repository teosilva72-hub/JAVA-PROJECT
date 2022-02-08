package br.com.tracevia.webapp.dao.global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.util.LocaleUtil;

public class ReportDAO {
	
    SQL_Tracevia conn = new SQL_Tracevia();

    private boolean custom;

    public List<String> columnName;
    public List<String[]> lines;
    public List<Pair<String, List<String[]>>> secondaryLines;
    public List<String> IDs;
    
    LocaleUtil localeReport, localeDirection;
    
    public ReportDAO(List<String> columnName) throws Exception {
    	
    	localeReport = new LocaleUtil();
    	localeReport.getResourceBundle(LocaleUtil.LABELS_REPORTS);
    	
    	localeDirection = new LocaleUtil();
    	localeDirection.getResourceBundle(LocaleUtil.LABELS_DIRECTIONS);

        if (columnName == null)
        	columnName = new ArrayList<>();
        
    	custom = !columnName.isEmpty();
    	
        this.columnName = columnName;
        
        lines = new ArrayList<>();
        
    }

    public void getReport(String query, String forMS, String id, String[] division) throws Exception {
       
        int count = 0;
    	String newQuery = query;
    	String newQueryMS = forMS.replace("$period", "MSperiod");
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
            if (forMS != null)
                newQueryMS = forMS.replace("@division", search.substring(2));
        }


        do {
            try {
                count++;
                if (!conn.start(count))
                    break;
        
                conn.prepare(newQuery);
                if (newQueryMS != null)
                    conn.prepare_ms(newQueryMS);
                MapResult result = conn.executeQuery();
        
                if (!custom)
                    this.columnName.clear();
        
                if (result.hasNext()) {
                    for (RowResult rs : result) {
                       
                        String[] keys = rs.getKeys();
                        int columnsNumber = keys.length;
                        String[] row = new String[columnsNumber];
                        
                        for (int idx = 1; idx <= columnsNumber; idx++) {
                            String name = keys[idx];
                            if (!custom)
                                this.columnName.add(name);
        
                            String value = translateValues(rs.getString(idx)); // TO DO SPECIFIC TRANSLATIONS IN DATA PRESENTATION
                                                
                            row[idx - 1] = value != null && value != "" ? value : "-";
                            if (name.equals(id) && !field.contains(value))
                                field.add(value);
                        }
        
                        lines.add(row);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        } while (lines.isEmpty());

        this.lines = lines;
        this.IDs = field;

        if (division != null)
            this.completeSecondary(query, forMS, allOptions);
    }

    private void completeSecondary(String query, String forMS, List<String[]> fields) throws Exception {     
    	List<Pair<String, List<String[]>>> secondaryLines = new ArrayList<>();
    	
        for (int index = 0; index < fields.size(); index++) {
            List<String[]> secondaryList = new ArrayList<>();
            String[] division = fields.get(index);

            String newQuery = query.replace("@division", String.format("'%s'", division[0]));

            try {
                conn.start(1);
                
                conn.prepare(newQuery);
                if (forMS != null)
                    conn.prepare_ms(forMS.replace("@division", String.format("'%s'", division[0])));
                MapResult result = conn.executeQuery();
        
                if (result.hasNext()) {
                    for (RowResult rs : result) {
                       
                        String[] keys = rs.getKeys();
                        int columnsNumber = keys.length;
                        String[] column = new String[columnsNumber];
                        
                        for (int idx = 1; idx <= columnsNumber; idx++) {
        
                            String value = rs.getString(idx);
                            
                            column[idx - 1] = value != null && value != "" ? value : "-";
                        }
        
                        secondaryList.add(column);
                    }
                }
                
                secondaryLines.add(new Pair<String, List<String[]>>(division[1], secondaryList));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }
        
        this.secondaryLines = secondaryLines;
    }

    public List<String[]> getOtherElementTable(String table, String column) {
        return getOtherElementTable(table, new String[]{ column, column }, "");
    }

    public List<String[]> getOtherElementTable(String table, String column, String where) {
        return getOtherElementTable(table, new String[]{ column, column }, where);
    }

    public List<String[]> getOtherElementTable(String table, String[] column) {
        return getOtherElementTable(table, column, "");
    }
    
    public List<String[]> getOtherElementTable(String table, String[] column, String where) {
    	
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
        String query = String.format("SELECT DISTINCT %s FROM %s%s GROUP BY %1$s", select.substring(2), table, where.isEmpty() ? "" : String.format(" WHERE %s", where));

        try {
            conn.start(1);
            conn.prepare(query);
            MapResult result = conn.executeQuery();
    
            if (result.hasNext()) {
                for (RowResult rs : result) {
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
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
    		case "lane 1": return locale.getStringKey("reports_filter_lane1_option"); 
    		case "lane 2": return locale.getStringKey("reports_filter_lane2_option"); 
    		case "lane 3": return locale.getStringKey("reports_filter_lane3_option"); 
    		case "lane 4": return locale.getStringKey("reports_filter_lane4_option"); 
    		case "lane 5": return locale.getStringKey("reports_filter_lane5_option"); 
    		case "lane 6": return locale.getStringKey("reports_filter_lane6_option"); 
    		case "lane 7": return locale.getStringKey("reports_filter_lane7_option"); 
    		case "lane 8": return locale.getStringKey("reports_filter_lane8_option"); 
    		case "max": return locale.getStringKey("reports_filter_battery_max_option");
    		case "avg": return locale.getStringKey("reports_filter_battery_avg_option");
    		case "min": return locale.getStringKey("reports_filter_battery_min_option");
    		
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
	
	// ------------------------------------------------------------------------------------
	
	  private String translateValues(String value) {
		  				  					    	
	    	switch (value == null ? "" : value) {
	    	
				case "N": return localeDirection.getStringKey("directions_north");
				case "S": return localeDirection.getStringKey("directions_south");
				case "L": return localeDirection.getStringKey("directions_east");
				case "O": return localeDirection.getStringKey("directions_west");		
				case "Top door": return localeReport.getStringKey("reports_value_top_door_option");		
				case "Bottom door": return localeReport.getStringKey("reports_value_bottom_door_option");
				case "Driving on the Lane Line": return localeReport.getStringKey("reports_value_driving_lane_line_option");
	    		case "Illegal Parking": return localeReport.getStringKey("reports_value_illegal_parking_option");
	    		case "Wrong-Way Driving": return localeReport.getStringKey("reports_value_wrong_way_driving_option");
	    		case "Congestion": return localeReport.getStringKey("reports_value_congestion_option");
	    		case "Pedestrain": return localeReport.getStringKey("reports_value_pedestrain_option");
	    		case "Thrown Object": return localeReport.getStringKey("reports_value_thrown_object_option");
	    		case "Roadblock": return localeReport.getStringKey("reports_value_roadblock_option");
	    		case "Illegal Lane Change": return localeReport.getStringKey("reports_value_illegal_lane_change_option");
	    		case "Two-way": return localeReport.getStringKey("reports_value_two-way_option");
	    		case "Up": return localeReport.getStringKey("reports_value_up_option");
	    		case "Down": return localeReport.getStringKey("reports_value_down_option");
	    		case " 0 minute": return localeReport.getStringKey("reports_value_zero_minute_option");
	    		case " < 1 minute": return localeReport.getStringKey("reports_value_less_than_1_minute");
	    		case " > 1 minute and < 2 minutes": return localeReport.getStringKey("reports_value_between_1_and_2_minutes_option");
	    		case " > 3 minutes": return localeReport.getStringKey("reports_value_greater_than_3_minutes_option");
	    			    						    		    		
	    		default: return value;
	    	}
	    			  
	    }
	  
	  // ------------------------------------------------------------------------------------
	
}