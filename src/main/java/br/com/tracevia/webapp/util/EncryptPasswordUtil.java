package br.com.tracevia.webapp.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe utilizada para criptografar senhas 
 * @version 1.0
 * @since 1.0
 * @author Wellington 29/09/2021
 *
 */

public class EncryptPasswordUtil {	
	
	/**
	 * Método para criptografar a senha de um usuário no formato HASH MD5
	 * @author Wellington 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @see https://docs.oracle.com/javase/7/docs/api/java/security/MessageDigest.html
	 * @param password - senha a ser criptografada
	 * @return o password criptografado
	 */
	
	public static String encryptPassword(String password) {

		try { 
			
			
			// FORMATOS SUPORTADOS
			// MD5
			// SHA-1
			// SHA-256
			
			// Instanciar o objeto para o formato HASH MD5
			MessageDigest mDigest = MessageDigest.getInstance("MD5");

			// Transformar password em bytes formatado em UFT-8
			byte[] passwordMD5 = mDigest.digest(password.getBytes("UTF-8"));			

			StringBuffer sb = new StringBuffer();
			
			//percorrer o array de bytes 
			for (byte b : passwordMD5){
				
				// Converter os bytes para hexadecimal
				sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1,3));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException e) {               
			e.printStackTrace();
			
			return null;
			
		} catch (UnsupportedEncodingException e) {             
			e.printStackTrace();
			
			return null;
		}
	}
	
	// --------------------------------------------------------------------------------------------

}
