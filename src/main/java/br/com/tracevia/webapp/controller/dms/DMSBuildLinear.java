package br.com.tracevia.webapp.controller.dms;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.dao.dms.MessagesDAO;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.dms.Messages;
import br.com.tracevia.webapp.model.global.Equipments;

@ManagedBean(name="dmsMapsView")
@ViewScoped
public class DMSBuildLinear {
	
	List<? extends Equipments> dmsList; 
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
		
	public List<? extends Equipments> getDmsList() {
		return dmsList;
	}
	
	public void setDmsList(List<? extends Equipments> dmsList) {
		this.dmsList = dmsList;
	}

	@PostConstruct
	public void initalize() {
		
		CreateLinearEquipment();
		
	}
	
	public void CreateLinearEquipment() {
				
		dmsList = new ArrayList<DMS>();
		messagesDisplay = new ArrayList<Messages>();
		
		try {
			
			MessagesDAO messageDao = new MessagesDAO();
			
			DMS dms = new DMS();
			dmsList = dms.listEquipments("pmv");			
			
			messagesDisplay = messageDao.selectActivesMessages();
						
			letter = new char[dmsList.size()][3][12];
			image = new String[dmsList.size()];
			
			try {
			
            if(!messagesDisplay.isEmpty()) {
				
				int e = 0;
														
				for(Messages m : messagesDisplay){
																																		
				  image[e] = m.getImagem();	
				  				  					  					  						 																			
				  for(int n = 0; n < m.getTexto1().length(); n++) 
						  letter[e][0][n] = m.getTexto1().charAt(n);        
				  					   
			      for(int n = 0; n < m.getTexto2().length(); n++) 
						   letter[e][1][n] = m.getTexto2().charAt(n);
									   
				  for(int n = 0; n < m.getTexto3().length(); n++) 
						    letter[e][2][n] = m.getTexto3().charAt(n);
				  
				  e++; // incrementa equipamento		
				 
				} 
				
      		} // System.out.println("none");
				
            }catch(IndexOutOfBoundsException ex) {}
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
				
	}

}
