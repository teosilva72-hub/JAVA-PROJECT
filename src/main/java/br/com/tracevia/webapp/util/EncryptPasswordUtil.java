package br.com.tracevia.webapp.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptPasswordUtil {
	
	MessageDigest mDigest;
	
	public String encryptPassword(String password) {

		try { 
			
			//Instanciar HASH MD5
			mDigest = MessageDigest.getInstance("MD5");

			//Converte a String valor para um array de bytes em MD5
			byte[] passwordMD5 = mDigest.digest(password.getBytes("UTF-8"));

			//Convertemos os bytes para hexadecimal
			//Salvar no banco 
			//Comparar senhas

			StringBuffer sb = new StringBuffer();
			for (byte b : passwordMD5){
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

}
