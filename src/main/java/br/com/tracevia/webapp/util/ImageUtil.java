package br.com.tracevia.webapp.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageUtil {
		
	/**
	 * Method to get the full path of a file inside project (image, icon and etc.)
	 * 
	 * @author Wellington 11/03/2022
	 * @version 1.0
	 * @since 1.0 
	 * 
	 * @param sourcePath folder path like images, icons ...
	 * @param targetPath folder where the file is located
	 * @param file filename with the extension
	 * @return full path where the file is located
	 * 
	 */
	
	public static String getInternalImagePath(String sourcePath, String targetPath, String file) { 
		
		String realPath = SessionUtil.getExternalContext().getRealPath("");
						
		Path path = Paths.get(realPath, "resources", sourcePath, targetPath, file);
		
		return path.toString();
	}
	

	// ---------------------------------------------------------------------------------------------
	
	/**
	 * Method to encode image to base 64
	 * 
	 * @author Wellington 11/03/2022
	 * @version 1.0
	 * @since 1.0 
	 * 
	 * @param file image to be encoded
	 * @return an image encoded in base64 format
	 * 
	 */
	
	public static String encodeToBase64(String file) {
		
		try {

			Path path = Paths.get(file);								
			  byte[] bytes = Files.readAllBytes(path);
			  return Base64.getEncoder().encodeToString(bytes);
			  
		} catch (IOException e) {											
			return "";
		}

	}
	
  // ---------------------------------------------------------------------------------------------
	
	/**
	 * Method to get image from a path and encode to base 64
	 * 
	 * @author Wellington 11/03/2022
	 * @version 1.0
	 * @since 1.0 
	 * 
	 * @param sourcePath folder path like images, icons ...
	 * @param targetPath folder where the file is located
	 * @param file filename with the extension
	 * @return an image encoded in base64 format
	 * 
	 */
	
	public static String getInternalImagePathAndEncodeToBase64(String sourcePath, String targetPath, String file) {
		
		String realPath = SessionUtil.getExternalContext().getRealPath("");
						
		Path path = Paths.get(realPath, "resources", sourcePath, targetPath, file);
		
		try {						
			  
			byte[] bytes = Files.readAllBytes(path);
			  return Base64.getEncoder().encodeToString(bytes);
			  
		} catch (IOException e) {											
			return "";
		}
		
	}
	

	// ---------------------------------------------------------------------------------------------
	
}
