package br.com.tracevia.webapp.controller.dms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.controller.global.ListEquipments;
import br.com.tracevia.webapp.model.dms.Messages;

@ManagedBean(name="dmsMapsView")
@ViewScoped
public class DMSBuildMaps implements Serializable {
	
	/**
	 * SERIAL ID
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{listEquipsBean}")
	private ListEquipments equips;			
	
	List<Messages> messagesDisplay;
			
	private String[] image;
	private char[][][] letter;
			
	public String[] getImage() {
		return image;
	}

	public void setImage(String[] image) {
		this.image = image;
	}
	public char[][][] getLetter() {
		return letter;
	}
	public void setLetter(char[][][] letter) {
		this.letter = letter;
	}
		
	public ListEquipments getEquips() {
		return equips;
	}

	public void setEquips(ListEquipments equips) {
		this.equips = equips;
	}

	@PostConstruct
	public void initalize() {
		
		BuildDMS();
		
	}
	
	public void BuildDMS() {
				
		messagesDisplay = new ArrayList<Messages>();
		
		try {
								
						
			letter = new char[equips.getDmsList().size()][3][12];
			image = new String[equips.getDmsList().size()];
			
			try {
			
            if(!messagesDisplay.isEmpty()) {
				
				int e = 0;
														
				for(Messages m : messagesDisplay){
																																		
				  image[e] = "";	
				  				  					  					  						 																			
				  for(int n = 0; n < m.getPages().get(0).getText1().length(); n++) 
						  letter[e][0][n] = m.getPages().get(0).getText1().charAt(n);        
				  					   
			      for(int n = 0; n < m.getPages().get(0).getText2().length(); n++) 
						   letter[e][1][n] = m.getPages().get(0).getText2().charAt(n);
									   
				  for(int n = 0; n < m.getPages().get(0).getText3().length(); n++) 
						    letter[e][2][n] = m.getPages().get(0).getText3().charAt(n);
				  
				  e++; // incrementa equipamento		
				 
				} 
				
      		} // System.out.println("none");
				
            }catch(IndexOutOfBoundsException ex) {}
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
				
	}

}
