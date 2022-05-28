package br.com.tracevia.webapp.classes;

public enum BahiaNorte {
	
	LIGHT_VEHICLES("CAT1"),         // Autos (Utilitários, Passeio)
	MOTORCYCLES("CAT9"),            // Motos
	BUS_2AXLES("CAT2"),             // Ônibus 2 Eixos
	BUS_3AXLES("CAT4"),             // Ônibus 3 Eixos
	SEMI_TRAILER("CAT3"),           // Semirreboque
	TRAILER("CAT5"),                // Reboque
	TRUCK_2AXLES("CAT2"),           // Caminhão 2 Eixos
	TRUCK_3AXLES("CAT4"),           // Caminhão 3 Eixos
	TRUCK_4AXLES("CAT6"),           // Caminhão 4 Eixos
	TRUCK_5AXLES("CAT7"),           // Caminhão 5 Eixos
	TRUCK_6AXLES("CAT8"),           // Caminhão 6 Eixos
	TRUCK_7AXLES("CAT61"),          // Caminhão 7 Eixos
	TRUCK_8AXLES("CAT62"),          // Caminhão 8 Eixos
	TRUCK_9AXLES("CAT63"),          // Caminhão 9 Eixos
	TRUCK_10AXLES("CAT64"),	        // Caminhão 10 Eixos
	NOT_ID_CLASS_2AXLES("2N"),      // Classe não identificada 2 Eixos
	NOT_ID_CLASS_3AXLES("3N"),      // Classe não identificada 3 Eixos
	NOT_ID_CLASS_4AXLES("4N"),      // Classe não identificada 4 Eixos
	NOT_ID_CLASS_5AXLES("5N"),      // Classe não identificada 5 Eixos
	NOT_ID_CLASS_6AXLES("6N"),      // Classe não identificada 6 Eixos
	NOT_ID_CLASS_7AXLES("7N"),      // Classe não identificada 7 Eixos
	NOT_ID_CLASS_8AXLES("8N"),      // Classe não identificada 8 Eixos
	NOT_ID_CLASS_9AXLES("9N"),      // Classe não identificada 9 Eixos
	UNKNOWN_CLASS("UC");            // Classe desconhecida
	
	
	 private String selectClass; 	 
	 
	    BahiaNorte(String classe) {
	        this.selectClass = classe;
	    }
	 
	    public String getClasse() {
	        return selectClass;
	    }	


}
