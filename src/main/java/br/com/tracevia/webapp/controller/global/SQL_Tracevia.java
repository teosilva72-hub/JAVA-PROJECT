package br.com.tracevia.webapp.controller.global;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

@ManagedBean(name="testDLL")
@ViewScoped
public class SQL_Tracevia {
	private interface SQL_Interface extends Library {
	    boolean start(int conn);
	    Void prepare(String query);
	    Pointer execute();
	}
	
	private SQL_Interface inner = Native.load("C:\\t\\sql_service.dll", SQL_Interface.class);

	public class MapResult implements Iterable<RowResult> {
        private JsonArray result;

        MapResult(JsonElement json) throws IOException {
            if (json.isJsonArray())
            	result = json.getAsJsonArray();
            else
            	throw new IOException("Not is valid json array");
        }

		@Override
		public Iterator<RowResult> iterator() {
			return new IteratorRow(result.iterator());
		}
    }
    
    public class IteratorRow implements Iterator<RowResult> {
        private Iterator<JsonElement> iterator;
        private JsonObject next;

        IteratorRow(Iterator<JsonElement> iterator) {
        	this.iterator = iterator;
        }

		@Override
		public boolean hasNext() {
            if (iterator.hasNext() && next == null) {
                JsonElement next = iterator.next();
                if (next.isJsonObject())
	        		this.next = next.getAsJsonObject();
            }
			return this.next != null;
		}

		@Override
		public RowResult next() {
			if (!this.hasNext())
				return null;
			
        	JsonObject next = this.next;
            this.next = null;
            return new RowResult(next);
		}
    }
    
    public class RowResult {
        private JsonObject current;
        private String[] keys;

        RowResult(JsonObject current) {
        	int size = current.size();
        	Object[] set = current.keySet().toArray();
        	
        	this.keys = new String[size];
        	for (int i = 0; i < size; i++)
        		keys[i] = (String) set[i];
        	this.current = current;
        }

        public String getString(int integer) throws Exception {
        	return current.get(keys[integer - 1]).getAsString();
        }
        
		public String getString(String string) throws Exception {
			return current.get(string).getAsString();
		}
		
		public int getInt(int integer) throws Exception {
			return current.get(keys[integer - 1]).getAsInt();
		}
		
		public int getInt(String string) throws Exception {
			return current.get(string).getAsInt();
		}
		
		public boolean getBoolean(int integer) throws Exception {
			return current.get(keys[integer - 1]).getAsBoolean();
		}
		
		public boolean getBoolean(String string) throws Exception {
			return current.get(string).getAsBoolean();
		}
		
		public double getDouble(int integer) throws Exception {
			return current.get(keys[integer - 1]).getAsDouble();
		}
		
		public double getDouble(String string) throws Exception {
			return current.get(string).getAsDouble();
		}
		
		public float getFloat(int integer) throws Exception {
			return current.get(keys[integer - 1]).getAsFloat();
		}
		
		public float getFloat(String string) throws Exception {
			return current.get(string).getAsFloat();
		}
    }
	
	public boolean start(int conn) {
		return inner.start(conn);
	}
	
	public Void prepare(String query) {
		return inner.prepare(query);
	}
	
	public MapResult execute() throws IOException {
		Pointer ptr = inner.execute();
		JsonElement result = ptr != null ? new Gson().fromJson(ptr.getString(0, "utf8"), JsonElement.class) : null;

		return ptr != null ? new MapResult(result) : null;
	}
	
	public void main() throws Exception {
		SQL_Tracevia service = new SQL_Tracevia();
        
        boolean connected = service.start(0);
        String teste = "SELECT name, creation_date, equip_id FROM speed_equipment";
        service.prepare(teste);
        MapResult response = service.execute();
        
        System.out.println("Connected: " + connected);
        for (RowResult result : response) {
        	int equipId = result.getInt(3);
    		String value = result.getString(1);
    		System.out.println("Response: " + equipId + " - " + value);
        }
    }
}