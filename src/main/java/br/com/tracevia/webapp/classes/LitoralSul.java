package br.com.tracevia.webapp.classes;

public enum LitoralSul {
	
	LIGHT_VEHICLES("CAT1"),         // Autos (Utili�rios, Passeio)
	MOTORCYCLES("CAT9"),            // Motos
	BUS_2AXLES("CAT2"),             // �nibus 2 Eixos
	BUS_3AXLES("CAT4"),             // �nibus 3 Eixos
	SEMI_TRAILER("CAT3"),           // Semirreboque
	TRAILER("CAT5"),                // Reboque
	TRUCK_2AXLES("CAT2"),           // Caminh�o 2 Eixos
	TRUCK_3AXLES("CAT4"),           // Caminh�o 3 Eixos
	TRUCK_4AXLES("CAT6"),           // Caminh�o 4 Eixos
	TRUCK_5AXLES("CAT7"),           // Caminh�o 5 Eixos
	TRUCK_6AXLES("CAT8"),           // Caminh�o 6 Eixos
	TRUCK_7AXLES("CAT10"),          // Caminh�o 7 Eixos
	TRUCK_8AXLES("CAT11"),          // Caminh�o 8 Eixos
	TRUCK_9AXLES("CAT12"),          // Caminh�o 9 Eixos
	TRUCK_10AXLES("10N"),	        // Caminh�o 10 Eixos
	NOT_ID_CLASS_2AXLES("2N"),      // Classe n�o identificada 2 Eixos
	NOT_ID_CLASS_3AXLES("3N"),      // Classe n�o identificada 3 Eixos
	NOT_ID_CLASS_4AXLES("4N"),      // Classe n�o identificada 4 Eixos
	NOT_ID_CLASS_5AXLES("5N"),      // Classe n�o identificada 5 Eixos
	NOT_ID_CLASS_6AXLES("6N"),      // Classe n�o identificada 6 Eixos
	NOT_ID_CLASS_7AXLES("7N"),      // Classe n�o identificada 7 Eixos
	NOT_ID_CLASS_8AXLES("8N"),      // Classe n�o identificada 8 Eixos
	NOT_ID_CLASS_9AXLES("9N"),      // Classe n�o identificada 9 Eixos
	UNKNOWN_CLASS("UC");            // Classe desconhecida
	
	 private String selectClass; 	 
	 
	    LitoralSul(String classe) {
	        this.selectClass = classe;
	    }
	 
	    public String getClasse() {
	        return selectClass;
	    }	


}
