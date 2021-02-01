package br.com.tracevia.webapp.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "eixosConverter")
public class EixosConverter implements Converter{

	 @Override
	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	  
		    	
	      if (value != null
	                && !"".equals(value)) {	 
	    	  
	    	  String selected = value;
	            return selected;
	      }
	      
	      return value;
	    }

	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object value) {
	        return value.toString();
	    }

}
