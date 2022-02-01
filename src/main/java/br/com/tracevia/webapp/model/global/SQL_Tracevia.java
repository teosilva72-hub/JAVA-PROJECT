package br.com.tracevia.webapp.model.global;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.context.FacesContext;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import br.com.tracevia.webapp.model.global.ResultSql.MapResult;

public class SQL_Tracevia {
	private interface SQL_Interface extends Library {
	    Pointer start(int conn);
	    void close(Pointer ptr);
	    void prepare(Pointer ptr, String query);
	    void prepare_ms(Pointer ptr, String query);
	    void prepare_my(Pointer ptr, String query);
	    Pointer execute_json(Pointer ptr);
	    ResultSql execute_query(Pointer ptr);
	    long execute_update(Pointer ptr);
	    void set_int(Pointer ptr, int idx, int val);
	    void set_float(Pointer ptr, int idx, float val);
	    void set_double(Pointer ptr, int idx, double val);
	    void set_string(Pointer ptr, int idx, String val);
	}
	
	private SQL_Interface inner;
	private Pointer ptr_conn;


	public SQL_Tracevia() {
		String current = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
		Path path = Paths.get(current, "resources", "dll", "sql_service.dll");
		
		inner = Native.load(path.toString(), SQL_Interface.class);
	}
	
	public boolean start(int conn) {
		ptr_conn = inner.start(conn);
		return true;
	}

	public void close() {
		inner.close(ptr_conn);
	}
	
	public void prepare(String query) {
		inner.prepare(ptr_conn, query);
	}
	
	public void prepare_ms(String query) {
		inner.prepare_ms(ptr_conn, query);
	}
	
	public void prepare_my(String query) {
		inner.prepare_my(ptr_conn, query);
	}
	
	public void setInt(int idx, int val) {
		inner.set_int(ptr_conn, idx, val);
	}
	
	public void setFloat(int idx, float val) {
		inner.set_float(ptr_conn, idx, val);
	}
	
	public void setDouble(int idx, double val) {
		inner.set_double(ptr_conn, idx, val);
	}
	
	public void setBoolean(int idx, boolean val) {
		inner.set_int(ptr_conn, idx, val ? 1 : 0);
	}
	
	public void setString(int idx, String val) {
		inner.set_string(ptr_conn, idx, val);
	}
	
	public MapResult executeQuery() throws IOException {
		return inner.execute_query(ptr_conn).to_map();
	}
	
	public long executeUpdate() throws IOException {
		return inner.execute_update(ptr_conn);
	}
}