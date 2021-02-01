package br.com.tracevia.webapp.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "sentidoConverter")
public class SentidoConverter implements Converter {
	
	  @Override
	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	    	
       String sentido = "";
	    	
	      if (value != null
	                && !"".equals(value)) {	    		 	    	
	    	sentido = value;        
	      }
	      
	      return sentido;
	    }

	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object value) {
	        return value.toString();
	    }
	
	
	
	
	
	
	

}
