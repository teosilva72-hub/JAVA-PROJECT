package br.com.tracevia.webapp.model.dms;

public class Picture {
 
    private int id; 
    private String image;
     
    public Picture(int id, String displayName, String image) {
        this.id = id;        
        this.image = image;
    }
    
    public Picture(int id, String image) {
        this.id = id;        
        this.image = image;
    }
    
    public Picture(String image) {              
        this.image = image;
    }
        
    public Picture(int id) {
        this.id = id;       
    }
   
    public Picture() {}
    
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}    
   
}