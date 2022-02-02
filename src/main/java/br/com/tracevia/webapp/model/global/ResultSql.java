package br.com.tracevia.webapp.model.global;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;

public class ResultSql extends Structure implements Structure.ByReference {
    	public int size;
    	public ColumnsSql ptr;

    	public ResultSql() { super(); }
		public ResultSql(Pointer p) {
			super(p);
			read();
		}
    	
    	MapResult to_map() throws IOException {
    		MapResult map = size > 0 ? new MapResult((ColumnsSql[]) ptr.toArray(size)) : new MapResult();
    		if (ptr != null)
    			ptr.clear();
    		this.clear();
    		return map;
    	}
    	
    	@Override
        protected List<String> getFieldOrder() {
            return Arrays.asList(new String[] {"size","ptr"});
        }
    	
    	public class MapResult implements Iterator<RowResult>, Iterable<RowResult> {
            private ColumnsSql[] result;
            private int idx = 0;
            private int size;

            MapResult() throws IOException {
            	this.size = 0;
            }
            
            MapResult(ColumnsSql[] result) throws IOException {
            	this.result = result;
            	this.size = result.length;
            }

    		public RowResult first() throws Exception {
    			if (size == 0)
    				throw new Exception("List void");
    			return result[0].to_row();
    		} 

    		public int len() {
    			return this.size;
    		} 		

    		@Override
    		public boolean hasNext() {
    			return idx < size;
    		}
    		@Override
    		public RowResult next() {
    			RowResult row = null;
    			if (hasNext()) {
    				row = result[idx].to_row();
    				result[idx].clear();
			    	idx++;
    			}
    			return row;
    		}
    		
    		@Override
    		public Iterator<RowResult> iterator() {
    			return this;
    		}
        }
    }