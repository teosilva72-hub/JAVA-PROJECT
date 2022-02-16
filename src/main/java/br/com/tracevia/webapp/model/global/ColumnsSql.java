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
        
        public String getString(String string) throws Exception {
        	return getString(map.get(string) + 1);
        }

		public String getString(int integer) throws Exception {
        	RowSql row = cols[integer - 1];
			switch (row.type) {
				case 1: // String
				case 4: // Date
				case 7: // Time
					return new String(row.getBytes(), StandardCharsets.UTF_8);
				case 2: // Int
				case 3: // UInt
					return String.valueOf(getInt(integer));
				case 5: // Double
					return String.valueOf(getDouble(integer));
				case 6: // Float
					return String.valueOf(getFloat(integer));
				case 8: // Bytes
					return String.valueOf(getBytes(integer));
				default:
					return null;
			}
        }
        
        public int getInt(String string) throws Exception {
			return getInt(map.get(string) + 1);
        }
        
        public int getInt(int integer) throws Exception {
        	RowSql row = cols[integer - 1];
        	byte[] b = row.getBytes();
        	switch (row.type) {
        		case 0:
        			return 0;
        		case 1:
					String value = new String(row.getBytes(), StandardCharsets.UTF_8);
				
					return Integer.parseInt(value.contains(".") ? value.split("\\.")[0] : value);
	        	default:
	        		return ByteBuffer.wrap(new byte[] {b[3], b[2], b[1], b[0]}).getInt();
        	}
        }
        
        public float getFloat(String string) throws Exception {
			return getFloat(map.get(string) + 1);
        }
		
        public float getFloat(int integer) throws Exception {
        	RowSql row = cols[integer - 1];
			switch (row.type) {
        		case 0:
        			return 0;
        		case 1:
					return Float.parseFloat(new String(row.getBytes(), StandardCharsets.UTF_8));
	        	default:
					return ByteBuffer.wrap(Arrays.copyOfRange(row.getBytes(), 0, 4)).getFloat();
        	}
        }
        
        public double getDouble(String string) throws Exception {
			return getDouble(map.get(string) + 1);
        }

        public double getDouble(int integer) throws Exception {
        	RowSql row = cols[integer - 1];
			switch (row.type) {
        		case 0:
        			return 0;
        		case 1:
					return Double.parseDouble(new String(row.getBytes(), StandardCharsets.UTF_8));
	        	default:
					return ByteBuffer.wrap(Arrays.copyOfRange(row.getBytes(), 0, 8)).getDouble();
        	}
        }
        
        private boolean checkBoolean(byte[] bytes) {
        	int sum = 0;
			for (byte b : bytes) {
				sum += b;
			}
			
			return sum != 0;
        }
        
        public boolean getBoolean(String string) throws Exception {
			return getBoolean(map.get(string) + 1);
        }
		
        public boolean getBoolean(int integer) throws Exception {
        	RowSql row = cols[integer - 1];
			switch (row.type) {
				case 0:
					return false;
				case 1:
					String value = new String(row.getBytes(), StandardCharsets.UTF_8);
					return Boolean.parseBoolean(value) || "1".equals(value);
				default:
					return checkBoolean(row.getBytes());
			}
        }
        
        public byte[] getBytes(String string) throws Exception {
			return getBytes(map.get(string) + 1);
        }

        public byte[] getBytes(int integer) throws Exception {
        	return cols[integer - 1].getBytes();
        }

		public String[] getKeys() {
			return map.keySet().toArray(new String[map.size()]);
		}
        
    }
}