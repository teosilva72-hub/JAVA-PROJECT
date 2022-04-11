package br.com.tracevia.webapp.controller.occ;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@ManagedBean
@SessionScoped

public class uploadBean {
	private String fileName, fileNameDate;
	private String directory, directoryDate;
	
    public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileNameDate() {
		return fileNameDate;
	}
	public void setFileNameDate(String fileNameDate) {
		this.fileNameDate = fileNameDate;
	}
	public String getDirectoryDate() {
		return directoryDate;
	}
	public void setDirectoryDate(String directoryDate) {
		this.directoryDate = directoryDate;
	}
	
	//enviar arquivo quando alterar
	public String update(String mainPath, String way, Part file2)  throws ServletException,  IOException{
		
		directoryDate = mainPath.concat(way);
		
		 int second = LocalDateTime.now().getSecond();
 	    int minute = LocalDateTime.now().getMinute();

 	    for (Part part : getAllParts(file2)) {
 	    	
 	        fileNameDate = part.getSubmittedFileName();
 	        InputStream fileContent = part.getInputStream();
 	        //System.out.println("Arquivo "+fileName +"> "+file);
 	        Files.copy(fileContent, new File(directoryDate, minute+second+"_"+fileNameDate).toPath());
 	       
 	        System.out.println("Arquivo enviado com sucesso: "+minute+second+"-"+fileNameDate);
 	        
 	        //teste list files
 	        
 	    }

		
		return null;
	}
	
	//enviar novo arquivo para a pasta
	public String upload(Part file, String mainPath, String localPath)  throws ServletException,  IOException {
        					
    	//armazenando o arquivo pelo nome no local escolhido
    
    	    directory = mainPath.concat(localPath);
    	    
    	    //criando segundos dentro da variavel
    	    int second = LocalDateTime.now().getSecond();
    	    int minute = LocalDateTime.now().getMinute();

    	    for (Part part : getAllParts(file)) {
    	    	
    	        fileName = part.getSubmittedFileName();
    	        InputStream fileContent = part.getInputStream();
    	        //System.out.println("Arquivo "+fileName +"> "+file);
    	        Files.copy(fileContent, new File(directory, minute+second+"_"+fileName).toPath());
    	       
    	        System.out.println("Arquivo enviado com sucesso: "+minute+"-"+second+"-"+fileName);
    	        
    	        //teste list files
    	        
    	    }

        return null;
    }
    
   
    
    public static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
    	
    	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	
    	return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }

   
}
