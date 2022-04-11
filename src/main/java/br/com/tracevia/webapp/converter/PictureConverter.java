package br.com.tracevia.webapp.converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.tracevia.webapp.model.dms.Picture;

@FacesConverter(value = "pictureConverter")
public class PictureConverter implements Converter {
	
	 public static List<Picture> pictureDB;
	
	 static {
		 		 
		 pictureDB = new ArrayList<Picture>();
		 		 
	     for(int i = 0; i < 10; i++)		 
		     pictureDB.add(new Picture(i, "00"+i+"_6464.bmp"));
	     
	     for(int i = 10; i <= 99; i++)		 
			 pictureDB.add(new Picture(i, "0"+i+"_6464.bmp"));
	     
	     for(int i = 100; i <= 105; i++)		 
			 pictureDB.add(new Picture(i, i+"_6464.bmp"));
	     	     	     
	        pictureDB.add(new Picture(106, "logo ATT.bmp"));
	        pictureDB.add(new Picture(107, "logo ATT2.bmp"));
				 		 
	 }

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		
		 if (submittedValue.trim().equals("")) {
	            return null;
	        
		 } else {
	            try {
	                int id = Integer.parseInt(submittedValue);

	                for (Picture p : pictureDB) {
	                    if (p.getId() == id) {	                        	                        
	                        return p;
	                    }	                    	                    
	                }
	                
	            } catch(NumberFormatException exception) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
	            }
	        }
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		
		 if (value == null || value.equals("")) {
	            return "";
	        } else {	        	
	        	return String.valueOf(((Picture) value).getId());
	        }
	   }


}