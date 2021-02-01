package br.com.tracevia.webapp.util;

public class GeneratePasswordUtil {
	
	private static final String[] CHARACTERS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
			"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
			"y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "!", "@", "$", "%", "&", "+", "-", "/", "*" };
		
	private String password;
	
	
	public String generatePassword() {
		
		password = "";

		for (int x = 0; x < 12; x++) {
			int j = (int) (Math.random() * CHARACTERS.length);
			password += CHARACTERS[j];
		}

		return password;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
}
