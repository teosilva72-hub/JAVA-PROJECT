package br.com.tracevia.webapp.util;

/**
 * Classe para gerar um password aleatório
 * @author Wellington 29/09/2021
 * @version 1.0
 * @since version 1.0
 *
 */

public class GeneratePasswordUtil {
	
	// --------------------------------------------------------------------------------------------
		
	// CHARACTERS TO CREATE PASSWORD
	
	private static final String[] CHARACTERS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
			"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
			"y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "!", "@", "$", "%", "&", "+", "-", "/", "*" };
			
	// --------------------------------------------------------------------------------------------
	
	/**
	 * Método para gerar senhas aleatóriamente
	 * @author Wellington 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @return um password aleatório
	 */		
	public static String generatePassword() {
		
		String password = "";

		for (int x = 0; x < 12; x++) {
			
			int j = (int) (Math.random() * CHARACTERS.length);
			
			password += CHARACTERS[j];
			
		}

		return password;
	}

	// --------------------------------------------------------------------------------------------

}
