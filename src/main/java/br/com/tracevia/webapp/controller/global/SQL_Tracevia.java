package br.com.tracevia.webapp.controller.global;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

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

    private class MapResult implements Iterable<RowResult> {
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
    
    private class IteratorRow implements Iterator<RowResult> {
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
    
    private class RowResult {
        private JsonObject current;

        RowResult(JsonObject current) {
        	this.current = current;
        }

		public String getString(String string) {
			return current.get(string).getAsString();
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
	
	public void main() throws IOException {
		SQL_Tracevia service = new SQL_Tracevia();
        
        boolean connected = service.start(0);
        String teste = "SELECT * FROM speed_equipment";
        service.prepare(teste);
        MapResult response = service.execute();
        
        System.out.println("Connected: " + connected);
        for (RowResult result : response) {
    		String value = result.getString("name");
    		System.out.println("Response: " + value);
        }
    }
}