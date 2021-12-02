package br.com.tracevia.webapp.model.global;

import br.com.tracevia.webapp.cfg.RoadConcessionairesEnum;
import br.com.tracevia.webapp.cfg.tables.TraceviaTables;
import br.com.tracevia.webapp.cfg.tables.ViaSulTables;
import br.com.tracevia.webapp.classes.BahiaNorte;
import br.com.tracevia.webapp.classes.Br153;
import br.com.tracevia.webapp.classes.CardelPozaRica;
import br.com.tracevia.webapp.classes.LitoralSul;
import br.com.tracevia.webapp.classes.Tracevia;
import br.com.tracevia.webapp.classes.Tuxpan;
import br.com.tracevia.webapp.classes.ViaAmericas;
import br.com.tracevia.webapp.classes.ViaPaulista;
import br.com.tracevia.webapp.classes.ViaRondon;
import br.com.tracevia.webapp.classes.ViaSul;

/**
 * Classe para definir atributos de acordo com a concession�ria
 * @author Wellington 10/03/2020
 * @version 1.0
 * @since 1.0
 *
 */

public class RoadConcessionaire {
	
	public static int id;
	public static String serverAddress;
	public static String serverName;
	public static String roadConcessionaire;
	public static String classLight;
	public static String classMotorcycle;	
	public static String classUnknown;	
	public static String classTrailer;	
	public static String classSemiTrailer;	
	public static String classBus2Axles;	
	public static String classBus3Axles;	
	public static String classTruck2Axles;	
	public static String classTruck3Axles;	
	public static String classTruck4Axles;	
	public static String classTruck5Axles;	
	public static String classTruck6Axles;	
	public static String classTruck7Axles;	
	public static String classTruck8Axles;	
	public static String classTruck9Axles;	
	public static String classTruck10Axles;		
	public static String classCCRLight;
	public static String classCCRMotorcycle;	
	public static String classCCRTrailer;	
	public static String classCCRSemiTrailer;	
	public static String classCCRBus2Axles;	
	public static String classCCRBus3Axles;	
	public static String classCCRBus4Axles;	
	public static String classCCRBus5Axles;	
	public static String classCCRBus6Axles;	
	public static String classCCRTruckSimple2Axles1;	
	public static String classCCRTruckSimple2Axles2;	
	public static String classCCRTruck2Axles;	
	public static String classCCRTruck3Axles;	
	public static String classCCRTruck4Axles;	
	public static String classCCRTruck5Axles;	
	public static String classCCRTruck6Axles;	
	public static String classCCRTruck7Axles;	
	public static String classCCRTruck8Axles;	
	public static String classCCRTruck9Axles;	
	public static String classCCRTruck10Axles;
	public static String classNotIdentifiedAxl2;
	public static String classNotIdentifiedAxl3;
	public static String classNotIdentifiedAxl4;
	public static String classNotIdentifiedAxl5;
	public static String classNotIdentifiedAxl6;
	public static String classNotIdentifiedAxl7;
	public static String classNotIdentifiedAxl8;
	public static String classNotIdentifiedAxl9;
	public static String externalImagePath;	
	public static String externalDefaultLogo;
	public static String logo;
	public static String mapUI;
	public static String linearMapUI;
	public static String tableVBV;
	public static String tableLL;
	public static String tableCCR;
	public static String tableDados15;
	public static String tableStatus;
	public static String plaque;    
	public static boolean mapEnabled;
	public static boolean reportsLLEnabled;	
			
			
	// --------------------------------------------------------------------------------------------
		
	// CONSTRUTOR 
		
	public RoadConcessionaire() {
		
		externalImagePath = "C:\\Tracevia\\Software\\External\\Logo\\";
		externalDefaultLogo = "C:\\Tracevia\\Software\\External\\Logo\\tracevia.jpg";
		
		//System.getenv("APPDATA")+			
		
	}
	
	//--------------------------------------------------------------------------------------------
	
	/**
	 * M�todo para defini��o de configura��o de uma concession�ria a partir do nome
	 * @author Wellington 10/03/2020
	 * @version 1.0
	 * @since 1.0
	 * @param concessionaire - nome de concession�ria
	 * @return verdaeiro caso exista o nome da concession�ria
	 */
	
	public boolean defineConcessionarieValues(String concessionaire) {
						
		boolean checkRoadConcessionaire = false;
					
		roadConcessionaire = concessionaire;
		
		try {
				
		//Caso contrario n�o acontece nada
		 if(!roadConcessionaire.equals("")) 
			 checkRoadConcessionaire = true;	 
				
		if(roadConcessionaire.equals(RoadConcessionairesEnum.BahiaNorte.getConcessionaire())) {
						
			classLight = BahiaNorte.LIGHT_VEHICLES.getClasse();
			classMotorcycle = BahiaNorte.MOTORCYCLES.getClasse();
			classUnknown = BahiaNorte.UNKNOWN_CLASS.getClasse();	
			classTrailer = BahiaNorte.TRAILER.getClasse();	
			classSemiTrailer = BahiaNorte.SEMI_TRAILER.getClasse();			
			classBus2Axles = BahiaNorte.BUS_2AXLES.getClasse();	
			classBus3Axles = BahiaNorte.BUS_3AXLES.getClasse();
			classTruck2Axles = BahiaNorte.TRUCK_2AXLES.getClasse();	
			classTruck3Axles = BahiaNorte.TRUCK_3AXLES.getClasse();		
			classTruck4Axles = BahiaNorte.TRUCK_4AXLES.getClasse();	
			classTruck5Axles = BahiaNorte.TRUCK_5AXLES.getClasse();	
			classTruck6Axles = BahiaNorte.TRUCK_6AXLES.getClasse();	
		    classTruck7Axles = BahiaNorte.TRUCK_7AXLES.getClasse();	
		    classTruck8Axles = BahiaNorte.TRUCK_8AXLES.getClasse();	
			classTruck9Axles = BahiaNorte.TRUCK_9AXLES.getClasse();	
		    classTruck10Axles = BahiaNorte.TRUCK_10AXLES.getClasse();	
			classNotIdentifiedAxl2 = BahiaNorte.NOT_ID_CLASS_2AXLES.getClasse();
			classNotIdentifiedAxl5 = BahiaNorte.NOT_ID_CLASS_3AXLES.getClasse();
			classNotIdentifiedAxl3 = BahiaNorte.NOT_ID_CLASS_4AXLES.getClasse();
			classNotIdentifiedAxl4 = BahiaNorte.NOT_ID_CLASS_5AXLES.getClasse();
			classNotIdentifiedAxl6 = BahiaNorte.NOT_ID_CLASS_6AXLES.getClasse();
			classNotIdentifiedAxl7 = BahiaNorte.NOT_ID_CLASS_7AXLES.getClasse();
			classNotIdentifiedAxl8 = BahiaNorte.NOT_ID_CLASS_8AXLES.getClasse();
			classNotIdentifiedAxl9 = BahiaNorte.NOT_ID_CLASS_9AXLES.getClasse();
			externalImagePath += "bahianorte.jpg";
			logo = "";
			tableVBV = TraceviaTables.TraceviaVBV.getTable();
			tableDados15 = TraceviaTables.TraceviaDados15.getTable();
			tableStatus = TraceviaTables.TraceviaStatus.getTable();
			tableLL = TraceviaTables.TraceviaLL.getTable();
			tableCCR = TraceviaTables.TraceviaCCR.getTable();
			plaque = "";
			mapEnabled = false;
			reportsLLEnabled = false;
		}	
						
        if(roadConcessionaire.equals(RoadConcessionairesEnum.CardelPozaRica.getConcessionaire())) {
			
			classLight = CardelPozaRica.LIGHT_VEHICLES.getClasse();
			classMotorcycle = CardelPozaRica.MOTORCYCLES.getClasse();
			classUnknown = CardelPozaRica.UNKNOWN_CLASS.getClasse();	
			classTrailer = CardelPozaRica.TRAILER.getClasse();	
			classSemiTrailer = CardelPozaRica.SEMI_TRAILER.getClasse();
			classBus2Axles = CardelPozaRica.BUS_2AXLES.getClasse();	
			classBus3Axles = CardelPozaRica.BUS_3AXLES.getClasse();
			classTruck2Axles = CardelPozaRica.TRUCK_2AXLES.getClasse();	
			classTruck3Axles = CardelPozaRica.TRUCK_3AXLES.getClasse();		
			classTruck4Axles = CardelPozaRica.TRUCK_4AXLES.getClasse();	
			classTruck5Axles = CardelPozaRica.TRUCK_5AXLES.getClasse();	
			classTruck6Axles = CardelPozaRica.TRUCK_6AXLES.getClasse();	
			classTruck7Axles = CardelPozaRica.TRUCK_7AXLES.getClasse();	
			classTruck8Axles = CardelPozaRica.TRUCK_8AXLES.getClasse();	
			classTruck9Axles = CardelPozaRica.TRUCK_9AXLES.getClasse();	
			classTruck10Axles = CardelPozaRica.TRUCK_10AXLES.getClasse();
			classNotIdentifiedAxl2 = CardelPozaRica.NOT_ID_CLASS_2AXLES.getClasse();
			classNotIdentifiedAxl5 = CardelPozaRica.NOT_ID_CLASS_3AXLES.getClasse();
			classNotIdentifiedAxl3 = CardelPozaRica.NOT_ID_CLASS_4AXLES.getClasse();
			classNotIdentifiedAxl4 = CardelPozaRica.NOT_ID_CLASS_5AXLES.getClasse();
			classNotIdentifiedAxl6 = CardelPozaRica.NOT_ID_CLASS_6AXLES.getClasse();
			classNotIdentifiedAxl7 = CardelPozaRica.NOT_ID_CLASS_7AXLES.getClasse();
			classNotIdentifiedAxl8 = CardelPozaRica.NOT_ID_CLASS_8AXLES.getClasse();
			classNotIdentifiedAxl9 = CardelPozaRica.NOT_ID_CLASS_9AXLES.getClasse();
			externalImagePath += "cardel_poza_rica.png";
			logo = "/resources/images/home/cardelpozarica.webp";
			linearMapUI = "/resources/images/realTimeInterface/linear/cardel_poza_rica.png";
			mapUI = "/resources/images/map/poza-rica/poza-rica.webp";
			tableVBV = TraceviaTables.TraceviaVBV.getTable();
			tableDados15 = TraceviaTables.TraceviaDados15.getTable();
			tableStatus = TraceviaTables.TraceviaStatus.getTable();
			tableLL = TraceviaTables.TraceviaLL.getTable();
			tableCCR = TraceviaTables.TraceviaCCR.getTable();
			plaque = "";
			mapEnabled = false;
			reportsLLEnabled = false;
		}
		
		if(roadConcessionaire.equals(RoadConcessionairesEnum.LitoralSul.getConcessionaire())) {
			
			classLight = LitoralSul.LIGHT_VEHICLES.getClasse();
			classMotorcycle = LitoralSul.MOTORCYCLES.getClasse();
			classUnknown = LitoralSul.UNKNOWN_CLASS.getClasse();	
			classTrailer = LitoralSul.TRAILER.getClasse();	
			classSemiTrailer = LitoralSul.SEMI_TRAILER.getClasse();
			classBus2Axles = LitoralSul.BUS_2AXLES.getClasse();	
			classBus3Axles = LitoralSul.BUS_3AXLES.getClasse();
			classTruck2Axles = LitoralSul.TRUCK_2AXLES.getClasse();	
			classTruck3Axles = LitoralSul.TRUCK_3AXLES.getClasse();		
			classTruck4Axles = LitoralSul.TRUCK_4AXLES.getClasse();	
			classTruck5Axles = LitoralSul.TRUCK_5AXLES.getClasse();	
			classTruck6Axles = LitoralSul.TRUCK_6AXLES.getClasse();	
			classTruck7Axles = LitoralSul.TRUCK_7AXLES.getClasse();	
			classTruck8Axles = LitoralSul.TRUCK_8AXLES.getClasse();	
			classTruck9Axles = LitoralSul.TRUCK_9AXLES.getClasse();	
			classTruck10Axles = LitoralSul.TRUCK_10AXLES.getClasse();	
			classNotIdentifiedAxl2 = LitoralSul.NOT_ID_CLASS_2AXLES.getClasse();
			classNotIdentifiedAxl5 = LitoralSul.NOT_ID_CLASS_3AXLES.getClasse();
			classNotIdentifiedAxl3 = LitoralSul.NOT_ID_CLASS_4AXLES.getClasse();
			classNotIdentifiedAxl4 = LitoralSul.NOT_ID_CLASS_5AXLES.getClasse();
			classNotIdentifiedAxl6 = LitoralSul.NOT_ID_CLASS_6AXLES.getClasse();
			classNotIdentifiedAxl7 = LitoralSul.NOT_ID_CLASS_7AXLES.getClasse();
			classNotIdentifiedAxl8 = LitoralSul.NOT_ID_CLASS_8AXLES.getClasse();
			classNotIdentifiedAxl9 = LitoralSul.NOT_ID_CLASS_9AXLES.getClasse();
			externalImagePath += "litoralsul.png";		
			logo = "";
			tableVBV = TraceviaTables.TraceviaVBV.getTable();
			tableDados15 = TraceviaTables.TraceviaDados15.getTable();
			tableStatus = TraceviaTables.TraceviaStatus.getTable();
			tableLL = TraceviaTables.TraceviaLL.getTable();
			tableCCR = TraceviaTables.TraceviaCCR.getTable();
			plaque = "";
			mapEnabled = false;
			reportsLLEnabled = false;
						
		}
		
       if(roadConcessionaire.equals(RoadConcessionairesEnum.Tuxpan.getConcessionaire())) {
			
			classLight = Tuxpan.LIGHT_VEHICLES.getClasse();
			classMotorcycle = Tuxpan.MOTORCYCLES.getClasse();
			classUnknown = Tuxpan.UNKNOWN_CLASS.getClasse();	
			classTrailer = Tuxpan.TRAILER.getClasse();	
			classSemiTrailer = Tuxpan.SEMI_TRAILER.getClasse();
			classBus2Axles = Tuxpan.BUS_2AXLES.getClasse();	
			classBus3Axles = Tuxpan.BUS_3AXLES.getClasse();
			classTruck2Axles = Tuxpan.TRUCK_2AXLES.getClasse();	
			classTruck3Axles = Tuxpan.TRUCK_3AXLES.getClasse();		
			classTruck4Axles = Tuxpan.TRUCK_4AXLES.getClasse();	
			classTruck5Axles = Tuxpan.TRUCK_5AXLES.getClasse();	
			classTruck6Axles = Tuxpan.TRUCK_6AXLES.getClasse();	
			classTruck7Axles = Tuxpan.TRUCK_7AXLES.getClasse();	
			classTruck8Axles = Tuxpan.TRUCK_8AXLES.getClasse();	
			classTruck9Axles = Tuxpan.TRUCK_9AXLES.getClasse();	
			classTruck10Axles = Tuxpan.TRUCK_10AXLES.getClasse();	
			classNotIdentifiedAxl2 = Tuxpan.NOT_ID_CLASS_2AXLES.getClasse();
			classNotIdentifiedAxl3 = Tuxpan.NOT_ID_CLASS_3AXLES.getClasse();
			classNotIdentifiedAxl4 = Tuxpan.NOT_ID_CLASS_4AXLES.getClasse();
			classNotIdentifiedAxl5 = Tuxpan.NOT_ID_CLASS_5AXLES.getClasse();
			classNotIdentifiedAxl6 = Tuxpan.NOT_ID_CLASS_6AXLES.getClasse();
			classNotIdentifiedAxl7 = Tuxpan.NOT_ID_CLASS_7AXLES.getClasse();
			classNotIdentifiedAxl8 = Tuxpan.NOT_ID_CLASS_8AXLES.getClasse();
			classNotIdentifiedAxl9 = Tuxpan.NOT_ID_CLASS_9AXLES.getClasse();	
			externalImagePath += "tuxpan.png";
			logo = "/resources/images/home/tuxpan.png";
			linearMapUI = "/resources/images/realTimeInterface/linear/tuxpan_tampico.webp";
			mapUI = "/resources/images/map/tuxpan/tuxpan.webp";
			tableVBV = TraceviaTables.TraceviaVBV.getTable();
			tableDados15 = TraceviaTables.TraceviaDados15.getTable();
			tableStatus = TraceviaTables.TraceviaStatus.getTable();
			tableLL = TraceviaTables.TraceviaLL.getTable();
			tableCCR = TraceviaTables.TraceviaCCR.getTable();
			plaque = "tuxpan";
			mapEnabled = true;
			reportsLLEnabled = false;
													
		}
       
       if(roadConcessionaire.equals(RoadConcessionairesEnum.ViaAmericas.getConcessionaire())) {
			
			classLight = ViaAmericas.LIGHT_VEHICLES.getClasse();
			classMotorcycle = ViaAmericas.MOTORCYCLES.getClasse();
			classUnknown = ViaAmericas.UNKNOWN_CLASS.getClasse();	
			classTrailer = ViaAmericas.TRAILER.getClasse();	
			classSemiTrailer = ViaAmericas.SEMI_TRAILER.getClasse();
			classBus2Axles = ViaAmericas.BUS_2AXLES.getClasse();	
			classBus3Axles = ViaAmericas.BUS_3AXLES.getClasse();
			classTruck2Axles = ViaAmericas.TRUCK_2AXLES.getClasse();	
			classTruck3Axles = ViaAmericas.TRUCK_3AXLES.getClasse();		
			classTruck4Axles = ViaAmericas.TRUCK_4AXLES.getClasse();	
			classTruck5Axles = ViaAmericas.TRUCK_5AXLES.getClasse();	
			classTruck6Axles = ViaAmericas.TRUCK_6AXLES.getClasse();	
			classTruck7Axles = ViaAmericas.TRUCK_7AXLES.getClasse();	
			classTruck8Axles = ViaAmericas.TRUCK_8AXLES.getClasse();	
			classTruck9Axles = ViaAmericas.TRUCK_9AXLES.getClasse();	
			classTruck10Axles = ViaAmericas.TRUCK_10AXLES.getClasse();	
			classNotIdentifiedAxl2 = ViaAmericas.NOT_ID_CLASS_2AXLES.getClasse();
			classNotIdentifiedAxl3 = ViaAmericas.NOT_ID_CLASS_3AXLES.getClasse();
			classNotIdentifiedAxl4 = ViaAmericas.NOT_ID_CLASS_4AXLES.getClasse();
			classNotIdentifiedAxl5 = ViaAmericas.NOT_ID_CLASS_5AXLES.getClasse();
			classNotIdentifiedAxl6 = ViaAmericas.NOT_ID_CLASS_6AXLES.getClasse();
			classNotIdentifiedAxl7 = ViaAmericas.NOT_ID_CLASS_7AXLES.getClasse();
			classNotIdentifiedAxl8 = ViaAmericas.NOT_ID_CLASS_8AXLES.getClasse();
			classNotIdentifiedAxl9 = ViaAmericas.NOT_ID_CLASS_9AXLES.getClasse();	
			externalImagePath += "";
			logo = "";
			linearMapUI = "";
			mapUI = "/resources/images/map/americas/americas.webp";
			tableVBV = TraceviaTables.TraceviaVBV.getTable();
			tableDados15 = TraceviaTables.TraceviaDados15.getTable();
			tableStatus = TraceviaTables.TraceviaStatus.getTable();
			tableLL = TraceviaTables.TraceviaLL.getTable();
			tableCCR = TraceviaTables.TraceviaCCR.getTable();
			plaque = "";
			mapEnabled = true;
			reportsLLEnabled = false;
													
		}
       if(roadConcessionaire.equals(RoadConcessionairesEnum.BR153.getConcessionaire())) {
			
          	classLight = ViaSul.LIGHT_VEHICLES.getClasse();
   			classMotorcycle = ViaSul.MOTORCYCLES.getClasse();
   			classUnknown = ViaSul.UNKNOWN_CLASS.getClasse();	
   			classTrailer = ViaSul.TRAILER.getClasse();	
   			classSemiTrailer = ViaSul.SEMI_TRAILER.getClasse();
   			classBus2Axles = ViaSul.BUS_2AXLES.getClasse();	
   			classBus3Axles = ViaSul.BUS_3AXLES.getClasse();
   			classTruck2Axles = ViaSul.TRUCK_2AXLES.getClasse();	
   			classTruck3Axles = ViaSul.TRUCK_3AXLES.getClasse();		
   			classTruck4Axles = ViaSul.TRUCK_4AXLES.getClasse();	
   			classTruck5Axles = ViaSul.TRUCK_5AXLES.getClasse();	
   			classTruck6Axles = ViaSul.TRUCK_6AXLES.getClasse();	
   			classTruck7Axles = ViaSul.TRUCK_7AXLES.getClasse();	
   			classTruck8Axles = ViaSul.TRUCK_8AXLES.getClasse();	
   			classTruck9Axles = ViaSul.TRUCK_9AXLES.getClasse();	
   			classTruck10Axles = ViaSul.TRUCK_10AXLES.getClasse();	
   			
   			classCCRLight = ViaSul.CCR_LIGHT_VEHICLES.getClasse();
   			classCCRMotorcycle = ViaSul.CCR_MOTORCYCLES.getClasse();		
   			classCCRTrailer = ViaSul.CCR_TRAILER.getClasse();	
   			classCCRSemiTrailer = ViaSul.CCR_SEMI_TRAILER.getClasse();
   			classCCRTruck2Axles = ViaSul.CCR_TRUCK_2AXLES.getClasse();	
   			classCCRTruckSimple2Axles1 = ViaSul.CCR_TRUCK_2AXLES_SIMPLE_1.getClasse();				
   			classCCRTruckSimple2Axles2 = ViaSul.CCR_TRUCK_2AXLES_SIMPLE_2.getClasse();	
   			classCCRTruck3Axles = ViaSul.CCR_TRUCK_3AXLES.getClasse();		
   			classCCRTruck4Axles = ViaSul.CCR_TRUCK_4AXLES.getClasse();	
   			classCCRTruck5Axles = ViaSul.CCR_TRUCK_5AXLES.getClasse();	
   			classCCRTruck6Axles = ViaSul.CCR_TRUCK_6AXLES.getClasse();	
   			classCCRTruck7Axles = ViaSul.CCR_TRUCK_7AXLES.getClasse();	
   			classCCRTruck8Axles = ViaSul.CCR_TRUCK_8AXLES.getClasse();	
   			classCCRTruck9Axles = ViaSul.CCR_TRUCK_9AXLES.getClasse();	
   			classCCRTruck10Axles = ViaSul.CCR_TRUCK_10AXLES.getClasse();	
   			classCCRBus2Axles = ViaSul.CCR_BUS_2AXLES.getClasse();	
   			classCCRBus3Axles = ViaSul.CCR_BUS_3AXLES.getClasse();	
   			classCCRBus4Axles = ViaSul.CCR_BUS_4AXLES.getClasse();	
   			classCCRBus5Axles = ViaSul.CCR_BUS_5AXLES.getClasse();	
   			classCCRBus6Axles = ViaSul.CCR_BUS_6AXLES.getClasse();			
   			
   			classNotIdentifiedAxl2 = ViaSul.NOT_ID_CLASS_2AXLES.getClasse();
   			classNotIdentifiedAxl3 = ViaSul.NOT_ID_CLASS_3AXLES.getClasse();
   			classNotIdentifiedAxl4 = ViaSul.NOT_ID_CLASS_4AXLES.getClasse();
   			classNotIdentifiedAxl5 = ViaSul.NOT_ID_CLASS_5AXLES.getClasse();
   			classNotIdentifiedAxl6 = ViaSul.NOT_ID_CLASS_6AXLES.getClasse();
   			classNotIdentifiedAxl7 = ViaSul.NOT_ID_CLASS_7AXLES.getClasse();
   			classNotIdentifiedAxl8 = ViaSul.NOT_ID_CLASS_8AXLES.getClasse();
   			classNotIdentifiedAxl9 = ViaSul.NOT_ID_CLASS_9AXLES.getClasse();
   			externalImagePath += "tracevia.jpg";	
   			logo = "/resources/images/home/arteris-viapaulista.webp";
   			linearMapUI = "/resources/images/realTimeInterface/br153/br153_linear.webp";
   			mapUI = "/resources/images/map/br153/br153.webp";
   			tableVBV = TraceviaTables.TraceviaVBV.getTable();
   			tableDados15 = TraceviaTables.TraceviaDados15.getTable();
   			tableStatus = TraceviaTables.TraceviaStatus.getTable();
   			tableLL = TraceviaTables.TraceviaLL.getTable();
   			tableCCR = TraceviaTables.TraceviaCCR.getTable();
   			plaque = "";
   			mapEnabled = true;
   			reportsLLEnabled = false;
   													
   		}
       
		
        if(roadConcessionaire.equals(RoadConcessionairesEnum.ViaPaulista.getConcessionaire())) {
			
        	classLight = ViaPaulista.LIGHT_VEHICLES.getClasse();
			classMotorcycle = ViaPaulista.MOTORCYCLES.getClasse();
			classUnknown = ViaPaulista.UNKNOWN_CLASS.getClasse();
			classTrailer = ViaPaulista.TRAILER.getClasse();	
			classSemiTrailer = ViaPaulista.SEMI_TRAILER.getClasse();
			classBus2Axles = ViaPaulista.BUS_2AXLES.getClasse();	
			classBus3Axles = ViaPaulista.BUS_3AXLES.getClasse();
			classTruck2Axles = ViaPaulista.TRUCK_2AXLES.getClasse();	
			classTruck3Axles = ViaPaulista.TRUCK_3AXLES.getClasse();		
			classTruck4Axles = ViaPaulista.TRUCK_4AXLES.getClasse();	
			classTruck5Axles = ViaPaulista.TRUCK_5AXLES.getClasse();	
			classTruck6Axles = ViaPaulista.TRUCK_6AXLES.getClasse();	
			classTruck7Axles = ViaPaulista.TRUCK_7AXLES.getClasse();	
			classTruck8Axles = ViaPaulista.TRUCK_8AXLES.getClasse();	
			classTruck9Axles = ViaPaulista.TRUCK_9AXLES.getClasse();	
			classTruck10Axles = ViaPaulista.TRUCK_10AXLES.getClasse();	
			classNotIdentifiedAxl2 = ViaPaulista.NOT_ID_CLASS_2AXLES.getClasse();
			classNotIdentifiedAxl3 = ViaPaulista.NOT_ID_CLASS_3AXLES.getClasse();
			classNotIdentifiedAxl4 = ViaPaulista.NOT_ID_CLASS_4AXLES.getClasse();
			classNotIdentifiedAxl5 = ViaPaulista.NOT_ID_CLASS_5AXLES.getClasse();
			classNotIdentifiedAxl6 = ViaPaulista.NOT_ID_CLASS_6AXLES.getClasse();
			classNotIdentifiedAxl7 = ViaPaulista.NOT_ID_CLASS_7AXLES.getClasse();
			classNotIdentifiedAxl8 = ViaPaulista.NOT_ID_CLASS_8AXLES.getClasse();
			classNotIdentifiedAxl9 = ViaPaulista.NOT_ID_CLASS_9AXLES.getClasse();			
			externalImagePath += "viapaulista.png";
			logo = "";
			tableVBV = TraceviaTables.TraceviaVBV.getTable();
			tableDados15 = TraceviaTables.TraceviaDados15.getTable();
			tableStatus = TraceviaTables.TraceviaStatus.getTable();
			tableLL = TraceviaTables.TraceviaLL.getTable();
			tableCCR = TraceviaTables.TraceviaCCR.getTable();
			plaque = "";
			mapEnabled = false;
			reportsLLEnabled = false;
			
		}

        if(roadConcessionaire.equals(RoadConcessionairesEnum.ViaRondon.getConcessionaire())) {
	
        	classLight = ViaRondon.LIGHT_VEHICLES.getClasse();
			classMotorcycle = ViaRondon.MOTORCYCLES.getClasse();
			classUnknown = ViaRondon.UNKNOWN_CLASS.getClasse();
			classTrailer = ViaRondon.TRAILER.getClasse();	
			classSemiTrailer = ViaRondon.SEMI_TRAILER.getClasse();
			classBus2Axles = ViaRondon.BUS_2AXLES.getClasse();	
			classBus3Axles = ViaRondon.BUS_3AXLES.getClasse();
			classTruck2Axles = ViaRondon.TRUCK_2AXLES.getClasse();	
			classTruck3Axles = ViaRondon.TRUCK_3AXLES.getClasse();		
			classTruck4Axles = ViaRondon.TRUCK_4AXLES.getClasse();	
			classTruck5Axles = ViaRondon.TRUCK_5AXLES.getClasse();	
			classTruck6Axles = ViaRondon.TRUCK_6AXLES.getClasse();	
			classTruck7Axles = ViaRondon.TRUCK_7AXLES.getClasse();	
			classTruck8Axles = ViaRondon.TRUCK_8AXLES.getClasse();	
			classTruck9Axles = ViaRondon.TRUCK_9AXLES.getClasse();	
			classTruck10Axles = ViaRondon.TRUCK_10AXLES.getClasse();
			classNotIdentifiedAxl2 = ViaRondon.NOT_ID_CLASS_2AXLES.getClasse();
			classNotIdentifiedAxl3 = ViaRondon.NOT_ID_CLASS_3AXLES.getClasse();
			classNotIdentifiedAxl4 = ViaRondon.NOT_ID_CLASS_4AXLES.getClasse();
			classNotIdentifiedAxl5 = ViaRondon.NOT_ID_CLASS_5AXLES.getClasse();
			classNotIdentifiedAxl6 = ViaRondon.NOT_ID_CLASS_6AXLES.getClasse();
			classNotIdentifiedAxl7 = ViaRondon.NOT_ID_CLASS_7AXLES.getClasse();
		    classNotIdentifiedAxl8 = ViaRondon.NOT_ID_CLASS_8AXLES.getClasse();
			classNotIdentifiedAxl9 = ViaRondon.NOT_ID_CLASS_9AXLES.getClasse();
			externalImagePath += "viaRondon.jpg";
			logo = "";
			linearMapUI = "/resources/images/realTimeInterface/linear/via_rondon.png";
			tableVBV = TraceviaTables.TraceviaVBV.getTable();
			tableDados15 = TraceviaTables.TraceviaDados15.getTable();
			tableStatus = TraceviaTables.TraceviaStatus.getTable();
			tableLL = TraceviaTables.TraceviaLL.getTable();
			tableCCR = TraceviaTables.TraceviaCCR.getTable();
			plaque = "";
			mapEnabled = false;
			reportsLLEnabled = false;
        }
        
        if(roadConcessionaire.equals(RoadConcessionairesEnum.ViaSul.getConcessionaire())) {
			
        	classLight = ViaSul.LIGHT_VEHICLES.getClasse();
			classMotorcycle = ViaSul.MOTORCYCLES.getClasse();
			classUnknown = ViaSul.UNKNOWN_CLASS.getClasse();	
			classTrailer = ViaSul.TRAILER.getClasse();	
			classSemiTrailer = ViaSul.SEMI_TRAILER.getClasse();
			classBus2Axles = ViaSul.BUS_2AXLES.getClasse();	
			classBus3Axles = ViaSul.BUS_3AXLES.getClasse();
			classTruck2Axles = ViaSul.TRUCK_2AXLES.getClasse();	
			classTruck3Axles = ViaSul.TRUCK_3AXLES.getClasse();		
			classTruck4Axles = ViaSul.TRUCK_4AXLES.getClasse();	
			classTruck5Axles = ViaSul.TRUCK_5AXLES.getClasse();	
			classTruck6Axles = ViaSul.TRUCK_6AXLES.getClasse();	
			classTruck7Axles = ViaSul.TRUCK_7AXLES.getClasse();	
			classTruck8Axles = ViaSul.TRUCK_8AXLES.getClasse();	
			classTruck9Axles = ViaSul.TRUCK_9AXLES.getClasse();	
			classTruck10Axles = ViaSul.TRUCK_10AXLES.getClasse();	
			
			classCCRLight = ViaSul.CCR_LIGHT_VEHICLES.getClasse();
			classCCRMotorcycle = ViaSul.CCR_MOTORCYCLES.getClasse();		
			classCCRTrailer = ViaSul.CCR_TRAILER.getClasse();	
			classCCRSemiTrailer = ViaSul.CCR_SEMI_TRAILER.getClasse();
			classCCRTruck2Axles = ViaSul.CCR_TRUCK_2AXLES.getClasse();	
			classCCRTruckSimple2Axles1 = ViaSul.CCR_TRUCK_2AXLES_SIMPLE_1.getClasse();				
			classCCRTruckSimple2Axles2 = ViaSul.CCR_TRUCK_2AXLES_SIMPLE_2.getClasse();	
			classCCRTruck3Axles = ViaSul.CCR_TRUCK_3AXLES.getClasse();		
			classCCRTruck4Axles = ViaSul.CCR_TRUCK_4AXLES.getClasse();	
			classCCRTruck5Axles = ViaSul.CCR_TRUCK_5AXLES.getClasse();	
			classCCRTruck6Axles = ViaSul.CCR_TRUCK_6AXLES.getClasse();	
			classCCRTruck7Axles = ViaSul.CCR_TRUCK_7AXLES.getClasse();	
			classCCRTruck8Axles = ViaSul.CCR_TRUCK_8AXLES.getClasse();	
			classCCRTruck9Axles = ViaSul.CCR_TRUCK_9AXLES.getClasse();	
			classCCRTruck10Axles = ViaSul.CCR_TRUCK_10AXLES.getClasse();	
			classCCRBus2Axles = ViaSul.CCR_BUS_2AXLES.getClasse();	
			classCCRBus3Axles = ViaSul.CCR_BUS_3AXLES.getClasse();	
			classCCRBus4Axles = ViaSul.CCR_BUS_4AXLES.getClasse();	
			classCCRBus5Axles = ViaSul.CCR_BUS_5AXLES.getClasse();	
			classCCRBus6Axles = ViaSul.CCR_BUS_6AXLES.getClasse();			
			
			classNotIdentifiedAxl2 = ViaSul.NOT_ID_CLASS_2AXLES.getClasse();
			classNotIdentifiedAxl3 = ViaSul.NOT_ID_CLASS_3AXLES.getClasse();
			classNotIdentifiedAxl4 = ViaSul.NOT_ID_CLASS_4AXLES.getClasse();
			classNotIdentifiedAxl5 = ViaSul.NOT_ID_CLASS_5AXLES.getClasse();
			classNotIdentifiedAxl6 = ViaSul.NOT_ID_CLASS_6AXLES.getClasse();
			classNotIdentifiedAxl7 = ViaSul.NOT_ID_CLASS_7AXLES.getClasse();
			classNotIdentifiedAxl8 = ViaSul.NOT_ID_CLASS_8AXLES.getClasse();
			classNotIdentifiedAxl9 = ViaSul.NOT_ID_CLASS_9AXLES.getClasse();
			externalImagePath += "viaSul.jpg";
			logo = "";
			linearMapUI = "/resources/images/realTimeInterface/linear/via_sul.webp";
			mapUI = "";
			tableVBV = ViaSulTables.ViaSulVBV.getTable();
			tableDados15 = ViaSulTables.ViaSulDados15.getTable();
			tableStatus = ViaSulTables.ViaSulStatus.getTable();
			tableLL = ViaSulTables.ViaSulLL.getTable();
			tableCCR = ViaSulTables.ViaSulCCR.getTable();
			plaque = "";
			mapEnabled = false;
			reportsLLEnabled = true;
						
		}

       if(roadConcessionaire.equals(RoadConcessionairesEnum.Tracevia.getConcessionaire())) {
    	   
    	  classLight = Tracevia.LIGHT_VEHICLES.getClasse();
		  classMotorcycle = Tracevia.MOTORCYCLES.getClasse();
		  classUnknown = Tracevia.UNKNOWN_CLASS.getClasse();	
		  classTrailer = Tracevia.TRAILER.getClasse();	
		  classSemiTrailer = Tracevia.SEMI_TRAILER.getClasse();
		  classBus2Axles = Tracevia.BUS_2AXLES.getClasse();	
		  classBus3Axles = Tracevia.BUS_3AXLES.getClasse();
		  classTruck2Axles = Tracevia.TRUCK_2AXLES.getClasse();	
		  classTruck3Axles = Tracevia.TRUCK_3AXLES.getClasse();		
		  classTruck4Axles = Tracevia.TRUCK_4AXLES.getClasse();	
		  classTruck5Axles = Tracevia.TRUCK_5AXLES.getClasse();	
		  classTruck6Axles = Tracevia.TRUCK_6AXLES.getClasse();	
		  classTruck7Axles = Tracevia.TRUCK_7AXLES.getClasse();	
		  classTruck8Axles = Tracevia.TRUCK_8AXLES.getClasse();	
		  classTruck9Axles = Tracevia.TRUCK_9AXLES.getClasse();	
		  classTruck10Axles = Tracevia.TRUCK_10AXLES.getClasse();	
		  classNotIdentifiedAxl2 = Tracevia.NOT_ID_CLASS_2AXLES.getClasse();
		  classNotIdentifiedAxl3 = Tracevia.NOT_ID_CLASS_3AXLES.getClasse();
		  classNotIdentifiedAxl4 = Tracevia.NOT_ID_CLASS_4AXLES.getClasse();
		  classNotIdentifiedAxl5 = Tracevia.NOT_ID_CLASS_5AXLES.getClasse();
		  classNotIdentifiedAxl6 = Tracevia.NOT_ID_CLASS_6AXLES.getClasse();
		  classNotIdentifiedAxl7 = Tracevia.NOT_ID_CLASS_7AXLES.getClasse();
		  classNotIdentifiedAxl8 = Tracevia.NOT_ID_CLASS_8AXLES.getClasse();
		  classNotIdentifiedAxl9 = Tracevia.NOT_ID_CLASS_9AXLES.getClasse();
		  externalImagePath += "tracevia.jpg";		
		  logo = "";
		  linearMapUI = "/resources/images/realTimeInterface/linear/tracevia.png";
		  tableVBV = TraceviaTables.TraceviaVBV.getTable();
		  tableDados15 = TraceviaTables.TraceviaDados15.getTable();
		  tableStatus = TraceviaTables.TraceviaStatus.getTable();
		  tableLL = TraceviaTables.TraceviaLL.getTable();
	      tableCCR = TraceviaTables.TraceviaCCR.getTable();
	      plaque = "";
		  mapEnabled = false;
		  reportsLLEnabled = false;
				  
         }	
              		 
       
		}catch(Exception ex){ /* DO NOTHING */ }
       
		 return checkRoadConcessionaire;
    
	 }
	
	//--------------------------------------------------------------------------------------------
	
}
