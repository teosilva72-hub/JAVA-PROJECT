package br.com.tracevia.webapp.methods;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UsuarioMethods {
	
	public String gerarSenha() {

		String[] caractere = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
				"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
				"y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z", "!", "@", "$", "%", "&", "+", "-", "/", "*" };

		String password = "";

		for (int x = 0; x < 12; x++) {
			int j = (int) (Math.random() * caractere.length);
			password += caractere[j];
		}

		return password;
	}

	public String convertStringToMd5(String password) {

		MessageDigest mDigest;

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
