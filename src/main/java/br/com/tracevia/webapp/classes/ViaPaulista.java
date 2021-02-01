package br.com.tracevia.webapp.classes;

public enum ViaPaulista {
	
	LIGHT_VEHICLES("1"),         // Autos (Utiliários, Passeio)
	MOTORCYCLES("9"),            // Motos
	BUS_2AXLES("2A"),            // Ônibus 2 Eixos
	BUS_3AXLES("4A"),            // Ônibus 3 Eixos
	SEMI_TRAILER("3"),           // Semirreboque
	TRAILER("5"),                // Reboque
	TRUCK_2AXLES("2"),           // Caminhão 2 Eixos
	TRUCK_3AXLES("4"),           // Caminhão 3 Eixos
	TRUCK_4AXLES("6"),           // Caminhão 4 Eixos
	TRUCK_5AXLES("7"),           // Caminhão 5 Eixos
	TRUCK_6AXLES("8"),           // Caminhão 6 Eixos
	TRUCK_7AXLES("10"),          // Caminhão 7 Eixos
	TRUCK_8AXLES("11"),          // Caminhão 8 Eixos
	TRUCK_9AXLES("E9"),          // Caminhão 9 Eixos
	TRUCK_10AXLES("10N"),	     // Caminhão 10 Eixos
	NOT_ID_CLASS_2AXLES("2N"),   // Classe não identificada 2 Eixos
	NOT_ID_CLASS_3AXLES("3N"),   // Classe não identificada 3 Eixos
	NOT_ID_CLASS_4AXLES("4N"),   // Classe não identificada 4 Eixos
	NOT_ID_CLASS_5AXLES("5N"),   // Classe não identificada 5 Eixos
	NOT_ID_CLASS_6AXLES("6N"),   // Classe não identificada 6 Eixos
	NOT_ID_CLASS_7AXLES("7N"),   // Classe não identificada 7 Eixos
	NOT_ID_CLASS_8AXLES("8N"),   // Classe não identificada 8 Eixos
	NOT_ID_CLASS_9AXLES("9N"),   // Classe não identificada 9 Eixos
	UNKNOWN_CLASS("UC");         // Classe desconhecida
		
	 private String selectClass; 	 
	 
	    ViaPaulista(String classe) {
	        this.selectClass = classe;
	    }
	 
	    public String getClasse() {
	        return selectClass;
	    }	

}
