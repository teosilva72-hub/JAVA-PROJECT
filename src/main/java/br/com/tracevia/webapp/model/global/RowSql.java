package br.com.tracevia.webapp.model.global;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class RowSql extends Structure implements Structure.ByReference {
	public Pointer value;
	public int size;
	public byte type;
	public Pointer key;
	
	public RowSql() { super(); }
	public RowSql(Pointer p) {
		super(p);
		read();
	}
	
	byte[] getBytes() {
		return value.getByteArray(0, size);
	}

	String getKey() {
		return key.getString(0);
	}
	
	@Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[] {"value", "size", "type","key"});
    }
}