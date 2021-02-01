package br.com.tracevia.webapp.methods;

import br.com.tracevia.webapp.model.global.Classes;

public class ClassName {	
	
	Classes cs;
	
	public Classes getClassName(String var) {		
		
		 cs = new Classes();	
		 
		if(var.equals("Tracevia") || var.equals("Via Paulista")){		
								 
			  cs.setClassRideCar("1"); // ViaPaulista Autos
			  cs.setClassMotorcycle("9"); // ViaPaulista Motos	 	
			  cs.setClassBus2Axl("2A"); // ViaPaulista (Ônibus 2 Eixos)  	
			  cs.setClassBus3Axl("4A"); // ViaPaulista (Ônibus 3 Eixos)			  			  
			  cs.setClassSemiTrailer("3"); // ViaPaulista (Semirreboque)
			  cs.setClassTrailer("5");	// ViaPaulista (Reboque) 
			  cs.setClassTruck2Axl("2"); // ViaPaulista Caminhões 2 Eixos 
			  cs.setClassTruck3Axl("4"); // ViaPaulista Caminhões 3 Eixos 
			  cs.setClassTruck4Axl("6"); // ViaPaulista Caminhões 4 Eixos 
			  cs.setClassTruck5Axl("7"); // ViaPaulista Caminhões 5 Eixos 
			  cs.setClassTruck6Axl("8"); // ViaPaulista Caminhões 6 Eixos 
			  cs.setClassTruck7Axl("10"); // ViaPaulista Caminhões 7 Eixos 
			  cs.setClassTruck8Axl("11"); // ViaPaulista Caminhões 8 Eixos 
			  cs.setClassTruck9Axl("E9"); // ViaPaulista Caminhões 9 Eixos 
			  cs.setClassTruck10Axl("10N"); // ViaPaulista Caminhões 10 Eixos
			  			  
			  cs.setClassNotIdentifiedAxl2("2N"); // ViaPaulista não identificadas 2 Eixos 
			  cs.setClassNotIdentifiedAxl3("3N"); // ViaPaulista não identificadas 3 Eixos 
			  cs.setClassNotIdentifiedAxl4("4N"); // ViaPaulista não identificadas 4 Eixos 
			  cs.setClassNotIdentifiedAxl5("5N"); // ViaPaulista não identificadas 5 Eixos 
			  cs.setClassNotIdentifiedAxl6("6N"); // ViaPaulista não identificadas 6 Eixos 
			  cs.setClassNotIdentifiedAxl7("7N"); // ViaPaulista não identificadas 7 Eixos 
			  cs.setClassNotIdentifiedAxl8("8N"); // ViaPaulista não identificadas 8 Eixos 
			  cs.setClassNotIdentifiedAxl9("9N"); // ViaPaulista não identificadas 9 Eixos 
			  cs.setUnknowClass("UC"); // Classe desconhecida	
						 	  
		  }	
		
		
		if(var.equals("Concessionária Bahia Norte")){
			
			  cs.setClassRideCar("CAT1"); 
			  cs.setClassMotorcycle("CAT9"); 	 	
			  cs.setClassBus2Axl("CAT2");   	
			  cs.setClassBus3Axl("CAT4"); 			  			  
			  cs.setClassSemiTrailer("CAT3"); 
			  cs.setClassTrailer("CAT5");	 
			  cs.setClassTruck2Axl("CAT2"); 
			  cs.setClassTruck3Axl("CAT4"); 
			  cs.setClassTruck4Axl("CAT6"); 
			  cs.setClassTruck5Axl("CAT7"); 
			  cs.setClassTruck6Axl("CAT8");  
			  cs.setClassTruck7Axl("CAT61");  
			  cs.setClassTruck8Axl("CAT62");  
			  cs.setClassTruck9Axl("CAT63");  
			  cs.setClassTruck10Axl("CAT64"); 
			  			  
			  cs.setClassNotIdentifiedAxl2("2N"); 
			  cs.setClassNotIdentifiedAxl3("3N");  
			  cs.setClassNotIdentifiedAxl4("4N");  
			  cs.setClassNotIdentifiedAxl5("5N");  
			  cs.setClassNotIdentifiedAxl6("6N");  
			  cs.setClassNotIdentifiedAxl7("7N");  
			  cs.setClassNotIdentifiedAxl8("8N");  
			  cs.setClassNotIdentifiedAxl9("9N");  
			  cs.setUnknowClass("UC"); 
						 
		}
		
		if(var.equals("Litoral Sul")){
			
			  cs.setClassRideCar("CAT1"); 
			  cs.setClassMotorcycle("CAT9"); 	 	
			  cs.setClassBus2Axl("CAT2");   	
			  cs.setClassBus3Axl("CAT4"); 			  			  
			  cs.setClassSemiTrailer("CAT3"); 
			  cs.setClassTrailer("CAT5");	 
			  cs.setClassTruck2Axl("CAT2");  
			  cs.setClassTruck3Axl("CAT4");  
			  cs.setClassTruck4Axl("CAT6"); 
			  cs.setClassTruck5Axl("CAT7"); 
			  cs.setClassTruck6Axl("CAT8");  
			  cs.setClassTruck7Axl("CAT10");  
			  cs.setClassTruck8Axl("CAT11");  
			  cs.setClassTruck9Axl("CAT12"); 
			  cs.setClassTruck10Axl("10N"); 
			  			  
			  cs.setClassNotIdentifiedAxl2("2N");  
			  cs.setClassNotIdentifiedAxl3("3N");  
			  cs.setClassNotIdentifiedAxl4("4N"); 
			  cs.setClassNotIdentifiedAxl5("5N"); 
			  cs.setClassNotIdentifiedAxl6("6N");  
			  cs.setClassNotIdentifiedAxl7("7N"); 
			  cs.setClassNotIdentifiedAxl8("8N"); 
			  cs.setClassNotIdentifiedAxl9("9N"); 
			  cs.setUnknowClass("UC");			
		}			
		
		if(var.equals("Via Rondon")){
			
			  cs.setClassRideCar("CAT1"); 
			  cs.setClassMotorcycle("CAT9"); 	 	
			  cs.setClassBus2Axl("CAT2");   	
			  cs.setClassBus3Axl("CAT4"); 			  			  
			  cs.setClassSemiTrailer("CAT3"); 
			  cs.setClassTrailer("CAT5");	 
			  cs.setClassTruck2Axl("CAT2");  
			  cs.setClassTruck3Axl("CAT4");  
			  cs.setClassTruck4Axl("CAT6"); 
			  cs.setClassTruck5Axl("CAT7"); 
			  cs.setClassTruck6Axl("CAT8");  
			  cs.setClassTruck7Axl("CAT10");  
			  cs.setClassTruck8Axl("CAT11");  
			  cs.setClassTruck9Axl("CAT12"); 
			  cs.setClassTruck10Axl("10N"); 
			  			  
			  cs.setClassNotIdentifiedAxl2("2N");  
			  cs.setClassNotIdentifiedAxl3("3N");  
			  cs.setClassNotIdentifiedAxl4("4N"); 
			  cs.setClassNotIdentifiedAxl5("5N"); 
			  cs.setClassNotIdentifiedAxl6("6N");  
			  cs.setClassNotIdentifiedAxl7("7N"); 
			  cs.setClassNotIdentifiedAxl8("8N"); 
			  cs.setClassNotIdentifiedAxl9("9N"); 
			  cs.setUnknowClass("Z1");			
		}	
		
		if(var.equals("Via Sul")){		
			 
			  cs.setClassRideCar("1"); // ViaPaulista Autos
			  cs.setClassMotorcycle("9"); // ViaPaulista Motos	 	
			  cs.setClassBus2Axl("2A"); // ViaPaulista (Ônibus 2 Eixos)  	
			  cs.setClassBus3Axl("4A"); // ViaPaulista (Ônibus 3 Eixos)			  			  
			  cs.setClassSemiTrailer("3"); // ViaPaulista (Semirreboque)
			  cs.setClassTrailer("5");	// ViaPaulista (Reboque) 
			  cs.setClassTruck2Axl("2"); // ViaPaulista Caminhões 2 Eixos 
			  cs.setClassTruck3Axl("4"); // ViaPaulista Caminhões 3 Eixos 
			  cs.setClassTruck4Axl("6"); // ViaPaulista Caminhões 4 Eixos 
			  cs.setClassTruck5Axl("7"); // ViaPaulista Caminhões 5 Eixos 
			  cs.setClassTruck6Axl("8"); // ViaPaulista Caminhões 6 Eixos 
			  cs.setClassTruck7Axl("10"); // ViaPaulista Caminhões 7 Eixos 
			  cs.setClassTruck8Axl("11"); // ViaPaulista Caminhões 8 Eixos 
			  cs.setClassTruck9Axl("E9"); // ViaPaulista Caminhões 9 Eixos 
			  cs.setClassTruck10Axl("10N"); // ViaPaulista Caminhões 10 Eixos
			  
			  cs.setCcr_autos("1");
			  cs.setCcr_motos("10");
			  cs.setCcr_semi_trailer("11");
			  cs.setCcr_trailer("12");
			  cs.setCcr_bus_2axles("13");
			  cs.setCcr_bus_3axles("14");
			  cs.setCcr_bus_4axles("15");
			  cs.setCcr_bus_5axles("16");  
			  cs.setCcr_bus_6axles("17");			
			  cs.setCcr_truck_2axles_simple1("18");
			  cs.setCcr_truck_2axles_simple2("19");
			  cs.setCcr_truck_2axles("2");
			  cs.setCcr_truck_3axles("3");
			  cs.setCcr_truck_4axles("4");
			  cs.setCcr_truck_5axles("5");
			  cs.setCcr_truck_6axles("6");
			  cs.setCcr_truck_7axles("7");
			  cs.setCcr_truck_8axles("8");
			  cs.setCcr_truck_9axles("9");
			  cs.setCcr_truck_10axles("20");			
			  			  
			  cs.setClassNotIdentifiedAxl2("2N"); // ViaPaulista não identificadas 2 Eixos 
			  cs.setClassNotIdentifiedAxl3("3N"); // ViaPaulista não identificadas 3 Eixos 
			  cs.setClassNotIdentifiedAxl4("4N"); // ViaPaulista não identificadas 4 Eixos 
			  cs.setClassNotIdentifiedAxl5("5N"); // ViaPaulista não identificadas 5 Eixos 
			  cs.setClassNotIdentifiedAxl6("6N"); // ViaPaulista não identificadas 6 Eixos 
			  cs.setClassNotIdentifiedAxl7("7N"); // ViaPaulista não identificadas 7 Eixos 
			  cs.setClassNotIdentifiedAxl8("8N"); // ViaPaulista não identificadas 8 Eixos 
			  cs.setClassNotIdentifiedAxl9("9N"); // ViaPaulista não identificadas 9 Eixos 
			  cs.setUnknowClass("UC"); // Classe desconhecida	
						 	  
		  }	
				
		
		return cs;		
	}
}

