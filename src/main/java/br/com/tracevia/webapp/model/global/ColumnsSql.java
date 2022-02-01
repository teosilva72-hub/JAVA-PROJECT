package br.com.tracevia.webapp.model.global;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class ColumnsSql extends Structure implements Structure.ByReference {
	public int size;
	public RowSql ptr;

	public ColumnsSql() { super(); }
	public ColumnsSql(Pointer p) {
		super(p);
		read();
	}
	
	@Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[] {"size","ptr"});
    }

	public RowResult to_row() {
		RowResult row = new RowResult((RowSql[]) ptr.toArray(size));
		ptr.clear();
		return row;
	}
	
	public class RowResult {
        RowSql[] cols;
        private HashMap<String, Integer> map;

        RowResult(RowSql[] rows) {
        	HashMap<String, Integer> map = new HashMap<String, Integer>();
        	
        	for (int i = 0; i < rows.length; i++) {
    			map.put(rows[i].getKey(), i);
    		}
        	
        	this.cols = rows;
        	this.map = map;
        }
        
        public String getString(int integer) throws Exception {
        	return new String(cols[integer - 1].getBytes(), StandardCharsets.UTF_8);
        }
        
        public String getString(String string) throws Exception {
        	return new String(cols[map.get(string)].getBytes(), StandardCharsets.UTF_8);
        }
        
        public int getInt(int integer) throws Exception {
        	RowSql row = cols[integer - 1];
        	if (row.type == 1)
        		return Integer.parseInt(new String(row.getBytes(), StandardCharsets.UTF_8));
        	else
        		return ByteBuffer.wrap(Arrays.copyOfRange(row.getBytes(), 0, 4)).getInt();
        }
        
        public int getInt(String string) throws Exception {
        	RowSql row = cols[map.get(string)];
        	if (row.type == 1)
        		return Integer.parseInt(new String(row.getBytes(), StandardCharsets.UTF_8));
        	else
        		return ByteBuffer.wrap(Arrays.copyOfRange(row.getBytes(), 0, 4)).getInt();
        }

        public float getFloat(int integer) throws Exception {
        	RowSql row = cols[integer - 1];
        	if (row.type == 1)
        		return Float.parseFloat(new String(row.getBytes(), StandardCharsets.UTF_8));
        	else
        		return ByteBuffer.wrap(Arrays.copyOfRange(row.getBytes(), 0, 4)).getFloat();
        }
        
        public float getFloat(String string) throws Exception {
        	RowSql row = cols[map.get(string)];
        	if (row.type == 1)
        		return Float.parseFloat(new String(row.getBytes(), StandardCharsets.UTF_8));
        	else
        		return ByteBuffer.wrap(Arrays.copyOfRange(row.getBytes(), 0, 4)).getFloat();
        }
        
        public double getDouble(int integer) throws Exception {
        	RowSql row = cols[integer - 1];
        	if (row.type == 1)
        		return Double.parseDouble(new String(row.getBytes(), StandardCharsets.UTF_8));
        	else
        		return ByteBuffer.wrap(Arrays.copyOfRange(row.getBytes(), 0, 8)).getDouble();
        }
        
        public double getDouble(String string) throws Exception {
        	RowSql row = cols[map.get(string)];
        	if (row.type == 1)
        		return Double.parseDouble(new String(row.getBytes(), StandardCharsets.UTF_8));
        	else
        		return ByteBuffer.wrap(Arrays.copyOfRange(row.getBytes(), 0, 8)).getDouble();
        }
        
        private boolean checkBoolean(byte[] bytes) {
        	int sum = 0;
			for (byte b : bytes) {
				sum += b;
			}
			
			return sum != 0;
        }
        
        public boolean getBoolean(int integer) throws Exception {
        	RowSql row = cols[integer - 1];
        	if (row.type == 1) {
				String value = new String(row.getBytes(), StandardCharsets.UTF_8);
        		return Boolean.parseBoolean(value) || "1".equals(value);
			} else
        		return checkBoolean(row.getBytes());
        }
        
        public boolean getBoolean(String string) throws Exception {
        	RowSql row = cols[map.get(string)];
        	if (row.type == 1) {
				String value = new String(row.getBytes(), StandardCharsets.UTF_8);
        		return Boolean.parseBoolean(value) || "1".equals(value);
			} else
        		return checkBoolean(row.getBytes());
        }
        
        public byte[] getBytes(int integer) throws Exception {
        	return cols[integer - 1].getBytes();
        }
        
        public byte[] getBytes(String string) throws Exception {
        	return cols[map.get(string)].getBytes();
        }

		public String[] getKeys() {
			return (String[]) map.keySet().toArray();
		}
        
    }
}