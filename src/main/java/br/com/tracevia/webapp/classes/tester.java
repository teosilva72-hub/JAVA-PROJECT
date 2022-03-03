package br.com.tracevia.webapp.classes;

public enum tester {
	
	LIGHT_VEHICLES("1"),         // Autos (Utili�rios, Passeio)
	MOTORCYCLES("9"),            // Motos
	BUS_2AXLES("2A"),            // �nibus 2 Eixos
	BUS_3AXLES("4A"),            // �nibus 3 Eixos
	SEMI_TRAILER("3"),           // Semirreboque
	TRAILER("5"),                // Reboque
	TRUCK_2AXLES("2"),           // Caminh�o 2 Eixos
	TRUCK_3AXLES("4"),           // Caminh�o 3 Eixos
	TRUCK_4AXLES("6"),           // Caminh�o 4 Eixos
	TRUCK_5AXLES("7"),           // Caminh�o 5 Eixos
	TRUCK_6AXLES("8"),           // Caminh�o 6 Eixos
	TRUCK_7AXLES("10"),          // Caminh�o 7 Eixos
	TRUCK_8AXLES("11"),          // Caminh�o 8 Eixos
	TRUCK_9AXLES("E9"),          // Caminh�o 9 Eixos
	TRUCK_10AXLES("10N"),	     // Caminh�o 10 Eixos
	NOT_ID_CLASS_2AXLES("2N"),   // Classe n�o identificada 2 Eixos
	NOT_ID_CLASS_3AXLES("3N"),   // Classe n�o identificada 3 Eixos
	NOT_ID_CLASS_4AXLES("4N"),   // Classe n�o identificada 4 Eixos
	NOT_ID_CLASS_5AXLES("5N"),   // Classe n�o identificada 5 Eixos
	NOT_ID_CLASS_6AXLES("6N"),   // Classe n�o identificada 6 Eixos
	NOT_ID_CLASS_7AXLES("7N"),   // Classe n�o identificada 7 Eixos
	NOT_ID_CLASS_8AXLES("8N"),   // Classe n�o identificada 8 Eixos
	NOT_ID_CLASS_9AXLES("9N"),   // Classe n�o identificada 9 Eixos
	UNKNOWN_CLASS("UC");         // Classe desconhecida
	
	 private String selectClass; 	 
	 
	    tester(String classe) {
	        this.selectClass = classe;
	    }
	 
	    public String getClasse() {
	        return selectClass;
	    }	
		

}
