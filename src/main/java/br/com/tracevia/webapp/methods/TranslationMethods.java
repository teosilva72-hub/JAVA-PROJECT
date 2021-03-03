package br.com.tracevia.webapp.methods;

import br.com.tracevia.webapp.util.LocaleUtil;

public class TranslationMethods {
	
	LocaleUtil locale, localePeriod, localeSat, localeDirections, localeCalendar, localeOcc;
	
	public TranslationMethods() {
		
		locale = new LocaleUtil();		
		locale.getResourceBundle(LocaleUtil.LABELS_USERS);
		
		localePeriod = new LocaleUtil();
		localePeriod.getResourceBundle(LocaleUtil.LABELS_PERIODS);
		
		localeSat = new LocaleUtil();		
		localeSat.getResourceBundle(LocaleUtil.LABELS_SAT);
		
		localeDirections = new LocaleUtil();
		localeDirections.getResourceBundle(LocaleUtil.LABELS_DIRECTIONS);
		
		localeCalendar = new LocaleUtil();
		localeCalendar.getResourceBundle(LocaleUtil.LABELS_CALENDAR); 
		
		localeOcc = new LocaleUtil();
		localeOcc.getResourceBundle(LocaleUtil.LABELS_OCC);
	
     }
	
	//USERS PANEL TRANSLATION OCCURRENCES
	public String occurrencesTranslator(String occ) {
		
		String converteOcc = "";
		
		switch(occ){

		case "Autopista Tuxpam - Tampico" : converteOcc = localeOcc.getStringKey("highway1"); break;
		case "Autopista Cardel - Poza Rica" : converteOcc = localeOcc.getStringKey("highway2"); break;
		case "Autopista Siervo de la Nación" : converteOcc = localeOcc.getStringKey("highway3"); break;
		case "Veracruz" : converteOcc = localeOcc.getStringKey("estado_local1"); break;
		case "CDMX" : converteOcc = localeOcc.getStringKey("estado_local2"); break;
		case "Norte" : converteOcc = localeOcc.getStringKey("direction1"); break;
		case "Sur" : converteOcc = localeOcc.getStringKey("direction2"); break;
		case "Este" : converteOcc = localeOcc.getStringKey("direction3"); break;
		case "Oeste" : converteOcc = localeOcc.getStringKey("direction4"); break;
		case "1A" : converteOcc = localeOcc.getStringKey("lane1"); break;
		case "2A" : converteOcc = localeOcc.getStringKey("lane2"); break;
		case "1B" : converteOcc = localeOcc.getStringKey("lane3"); break;
		case "2B" : converteOcc = localeOcc.getStringKey("lane4"); break;
		
		case "Accidente con víctima" : converteOcc = localeOcc.getStringKey("type1"); break;
		case "Accidente con víctima grave" : converteOcc = localeOcc.getStringKey("type2"); break;
		case "Animal en la carretera" : converteOcc = localeOcc.getStringKey("type3"); break;
		case "Objeto en la carretera" : converteOcc = localeOcc.getStringKey("type4"); break;
		case "Accidente leve" : converteOcc = localeOcc.getStringKey("type5"); break;
		case "Accidente moderado" : converteOcc = localeOcc.getStringKey("type6"); break;
		case "Accidente grave" : converteOcc = localeOcc.getStringKey("type7"); break;
		case "Iluminación" : converteOcc = localeOcc.getStringKey("type8"); break;
		case "Otros" : converteOcc = localeOcc.getStringKey("type9"); break;

		case "Cámaras" : converteOcc = localeOcc.getStringKey("origin1"); break;
		case "SOS" : converteOcc = localeOcc.getStringKey("origin2"); break;
		case "Teléfono" : converteOcc = localeOcc.getStringKey("origin3"); break;
		case "0800" : converteOcc = localeOcc.getStringKey("origin4"); break;
		case "Correo" : converteOcc = localeOcc.getStringKey("origin5"); break;
		case "Outro" : converteOcc = localeOcc.getStringKey("origin6"); break;
		
		case "Abierto" : converteOcc = localeOcc.getStringKey("state1"); break;
		case "Cancelado" : converteOcc = localeOcc.getStringKey("state2"); break;
		case "Finalizado" : converteOcc = localeOcc.getStringKey("state3"); break;
		
		case "Desgaste natural" : converteOcc = localeOcc.getStringKey("probable_cause1"); break;
		case "Colisión" : converteOcc = localeOcc.getStringKey("probable_cause2"); break;
		case "Vandalismo" : converteOcc = localeOcc.getStringKey("probable_cause3"); break;
		case "Lluvia" : converteOcc = localeOcc.getStringKey("probable_cause4"); break;
		case "Temperatura" : converteOcc = localeOcc.getStringKey("probable_cause5"); break;
		case "Viento" : converteOcc = localeOcc.getStringKey("probable_cause6"); break;
		case "Rayo" : converteOcc = localeOcc.getStringKey("probable_cause7"); break;
		case "Others" : converteOcc = localeOcc.getStringKey("probable_cause8"); break;
		case "------------" : converteOcc = localeOcc.getStringKey("probable_cause9"); break;
		
		case "Día" : converteOcc = localeOcc.getStringKey("condition1"); break;
		case "Día lluvioso" : converteOcc = localeOcc.getStringKey("condition2"); break;
		case "Dia de nevoeiro" : converteOcc = localeOcc.getStringKey("condition3"); break;
		case "Noche" : converteOcc = localeOcc.getStringKey("condition4"); break;
		case "Noche con lluvia" : converteOcc = localeOcc.getStringKey("condition5"); break;
		case "Noche con niebla" : converteOcc = localeOcc.getStringKey("condition6"); break;
		
		case "Normal" : converteOcc = localeOcc.getStringKey("conditionTrack1"); break;
		case "Moderado" : converteOcc = localeOcc.getStringKey("conditionTrack2"); break;
		case "Denso" : converteOcc = localeOcc.getStringKey("conditionTrack3"); break;
		case "Detenido" : converteOcc = localeOcc.getStringKey("conditionTrack4"); break;
		case "Nulo" : converteOcc = localeOcc.getStringKey("conditionTrack5"); break;
		
		case "Línea recta" : converteOcc = localeOcc.getStringKey("characteristic1"); break;
		case "Subida recta" : converteOcc = localeOcc.getStringKey("characteristic2"); break;
		case "Descenso recto" : converteOcc = localeOcc.getStringKey("characteristic3"); break;
		case "Curva plana" : converteOcc = localeOcc.getStringKey("characteristic4"); break;
		case "Curva hacia arriba" : converteOcc = localeOcc.getStringKey("characteristic5"); break;
		case "Curva Descida" : converteOcc = localeOcc.getStringKey("characteristic6"); break;
		
		case "Vehículo" : converteOcc = localeOcc.getStringKey("interference1"); break;
		case "Objeto" : converteOcc = localeOcc.getStringKey("interference2"); break;
		case "Animal" : converteOcc = localeOcc.getStringKey("interference3"); break;
		case "Persona" : converteOcc = localeOcc.getStringKey("interference4"); break;
		
		case "Precaución" : converteOcc = localeOcc.getStringKey("signaling1"); break;
		case "Hombre de la bandera" : converteOcc = localeOcc.getStringKey("signaling2"); break;
		case "Vehículos" : converteOcc = localeOcc.getStringKey("signaling3"); break;
		case "Usuario" : converteOcc = localeOcc.getStringKey("signaling4"); break;
		
		case "Enfermo" : converteOcc = localeOcc.getStringKey("stateConductor1"); break;
		case "Intoxicado" : converteOcc = localeOcc.getStringKey("stateConductor2"); break;
		case "Somnoliento" : converteOcc = localeOcc.getStringKey("stateConductor3"); break;
		case "Lesión leve" : converteOcc = localeOcc.getStringKey("stateConductor4"); break;
		case "Lesion grave" : converteOcc = localeOcc.getStringKey("stateConductor5"); break;
		
		case "Asistencia en carretera" : converteOcc = localeOcc.getStringKey("action1"); break;
		case "Ambulancia" : converteOcc = localeOcc.getStringKey("action2"); break;
		case "Policía" : converteOcc = localeOcc.getStringKey("action3"); break;
		case "Bomberos" : converteOcc = localeOcc.getStringKey("action4"); break;
		case "Cabrestante" : converteOcc = localeOcc.getStringKey("action5"); break;
		
		case "Iniciado" : converteOcc = localeOcc.getStringKey("actionState1"); break;
		case "Pendiente" : converteOcc = localeOcc.getStringKey("actionState2"); break;
		case "Finalizados" : converteOcc = localeOcc.getStringKey("actionState3"); break;
		
		case "Acotamiento A" : converteOcc = localeOcc.getStringKey("trackInterrupted1"); break;
		case "Acotamiento B" : converteOcc = localeOcc.getStringKey("trackInterrupted2"); break;
		case "Pista A" : converteOcc = localeOcc.getStringKey("trackInterrupted3"); break;
		case "Pista B" : converteOcc = localeOcc.getStringKey("trackInterrupted4"); break;
		
		case "Vehículo " : converteOcc = localeOcc.getStringKey("damageType1"); break;
		case "Barrera" : converteOcc = localeOcc.getStringKey("damageType2"); break;
		case "Señalización" : converteOcc = localeOcc.getStringKey("damageType3"); break;
		case "SOS " : converteOcc = localeOcc.getStringKey("damageType4"); break;
		case "CFTV" : converteOcc = localeOcc.getStringKey("damageType5"); break;
		
		case "KM" : converteOcc = localeOcc.getStringKey("damageUnity1"); break;
		case "Metros" : converteOcc = localeOcc.getStringKey("damageUnity2"); break;
		case "Partes" : converteOcc = localeOcc.getStringKey("damageUnity3"); break;
		
		case "Animais" : converteOcc = localeOcc.getStringKey("involvedType1"); break;
		case "Peatones" : converteOcc = localeOcc.getStringKey("involvedType2"); break;
		case "Objetos" : converteOcc = localeOcc.getStringKey("involvedType3"); break;
		case "Vehículos   " : converteOcc = localeOcc.getStringKey("involvedType4"); break;
		
		case "Alto" : converteOcc = localeOcc.getStringKey("damageSeverity1"); break;
		case "Medio" : converteOcc = localeOcc.getStringKey("damageSeverity2"); break;
		case "Bajo" : converteOcc = localeOcc.getStringKey("damageSeverity3"); break;
		}
		
		return converteOcc;
	}
	public String convertPermission(String role) {
		
		String convertedRole = "";
		
		if(role.equals("ROLE_ADMIN"))
		   convertedRole = locale.getStringKey("users_access_level_label_admin");
		
		if(role.equals("ROLE_OCC"))
			   convertedRole = locale.getStringKey("users_access_level_label_occ");
		
		if(role.equals("ROLE_MAINTENANCE"))
			   convertedRole = locale.getStringKey("users_access_level_label_maintenance");
		
		if(role.equals("ROLE_OPERATIONAL"))
			   convertedRole = locale.getStringKey("users_access_level_label_operational");
		
		if(role.equals("ROLE_SUPERVISOR"))
			   convertedRole = locale.getStringKey("users_access_level_label_supervisor");
		
		if(role.equals("ROLE_SUPER_USER"))
			   convertedRole = locale.getStringKey("users_access_level_label_super_user");
		
		if(role.equals("ROLE_USER"))
			   convertedRole = locale.getStringKey("users_access_level_label_user");
						
		return convertedRole;
				
	}
	
	//USERS PANEL TRANSLATION USE
	
	public String isActive(boolean bool) {
		
		String status = "";
	
		if(bool == true)
			 status = locale.getStringKey("user_bool_is_active");
		
		else status = locale.getStringKey("user_bool_is_inactive");		
		
		return status;
	}
	
	
	
public String periodName(String period) {
		
		String periodText = "";
				
		if(period.equals("05 minutes"))
			periodText = localePeriod.getStringKey("periods_five_minutes");

		if(period.equals("06 minutes"))
			periodText = localePeriod.getStringKey("periods_six_minutes");

		if(period.equals("10 minutes"))
			periodText = localePeriod.getStringKey("periods_teen_minutes");

		if(period.equals("15 minutes"))
			periodText = localePeriod.getStringKey("periods_fifteen_minutes");

		if(period.equals("30 minutes"))
			periodText = localePeriod.getStringKey("periods_thirty_minutes");

		if(period.equals("01 hour"))
			periodText = localePeriod.getStringKey("periods_one_hour");
		
		if(period.equals("06 hours"))
			periodText = localePeriod.getStringKey("periods_six_hours");
		
		if(period.equals("24 hours"))
			periodText = localePeriod.getStringKey("periods_twenty_four_hours");
		
		if(period.equals("month"))
			periodText = localePeriod.getStringKey("periods_month");
		
		if(period.equals("year"))
			periodText = localePeriod.getStringKey("periods_year");
				
				
		return periodText;			
		
	}

   
   public String CheckDirection1(String sentido) throws Exception {

	   String direction = "";
	   
		if (sentido.equals("N")) 			
			direction = localeDirections.getStringKey("directions_north");
				
		if(sentido.equals("S"))		  
			direction = localeDirections.getStringKey("directions_south");
				
		if (sentido.equals("L")) 		
			direction = localeDirections.getStringKey("directions_east");		
		
		if (sentido.equals("O"))			
			direction = localeDirections.getStringKey("directions_west");		
		
		return direction;
	  }
   
   public String CheckDirection2(String sentido) throws Exception {

	   String direction = "";
	   
		if (sentido.equals("N")) 			
			direction = localeDirections.getStringKey("directions_south");
				
		if(sentido.equals("S"))		  
			direction = localeDirections.getStringKey("directions_north");
				
		if (sentido.equals("L")) 		
			direction = localeDirections.getStringKey("directions_west");		
		
		if (sentido.equals("O"))			
			direction = localeDirections.getStringKey("directions_east");		
		
		return direction;
	  }
   
   public String CheckHeaderDirection1(String sentido) throws Exception {

	   String direction = "";
	   
		if (sentido.equals("N")) 			
			direction = localeDirections.getStringKey("directions_tab_north");
				
		if(sentido.equals("S"))		  
			direction = localeDirections.getStringKey("directions_tab_south");
				
		if (sentido.equals("L")) 		
			direction = localeDirections.getStringKey("directions_tab_east");		
		
		if (sentido.equals("O"))			
			direction = localeDirections.getStringKey("directions_tab_west");		
		
		return direction;
	  }
   
   public String CheckHeaderDirection2(String sentido) throws Exception {

	   String direction = "";
	   
		if (sentido.equals("N")) 			
			direction = localeDirections.getStringKey("directions_tab_south");
				
		if(sentido.equals("S"))		  
			direction = localeDirections.getStringKey("directions_tab_north");
				
		if (sentido.equals("L")) 		
			direction = localeDirections.getStringKey("directions_tab_west");		
		
		if (sentido.equals("O"))			
			direction = localeDirections.getStringKey("directions_tab_east");		
		
		return direction;
	  }

	//Abreviação do Mês
	public String MonthAbbreviation(String selectedMes) {

		String selectMonth = "";				
								
		if (selectedMes.equals("1"))
			selectMonth = localeCalendar.getStringKey("january_abbr");
		if (selectedMes.equals("2"))
			selectMonth = localeCalendar.getStringKey("february_abbr");
		if (selectedMes.equals("3"))
			selectMonth = localeCalendar.getStringKey("march_abbr");
		if (selectedMes.equals("4"))
			selectMonth = localeCalendar.getStringKey("april_abbr");
		if (selectedMes.equals("5"))
			selectMonth = localeCalendar.getStringKey("may_abbr");
		if (selectedMes.equals("6"))
			selectMonth = localeCalendar.getStringKey("june_abbr");
		if (selectedMes.equals("7"))
			selectMonth = localeCalendar.getStringKey("july_abbr");
		if (selectedMes.equals("8"))
			selectMonth = localeCalendar.getStringKey("august_abbr");
		if (selectedMes.equals("9"))
			selectMonth = localeCalendar.getStringKey("september_abbr");
		if (selectedMes.equals("10"))
			selectMonth = localeCalendar.getStringKey("october_abbr");
		if (selectedMes.equals("11"))
			selectMonth = localeCalendar.getStringKey("november_abbr");
		if (selectedMes.equals("12"))
			selectMonth = localeCalendar.getStringKey("december_abbr");						
		
		return selectMonth;
	}
	
	//Abreviação do Ano 
	public String YearAbbreviation(String ano) {		
		return ano.substring(2,4);	
	}
	
	
	 public void sentidoExcelHeader(int sheetIndex, String faixa1, String[] dir1, String[] dir2) {
		    	 
    	 if(faixa1.equals("N")) {
    		 dir1[sheetIndex] = localeDirections.getStringKey("directions_tab_north")+"/"+localeDirections.getStringKey("directions_tab_west");
    		 dir2[sheetIndex] = localeDirections.getStringKey("directions_tab_south")+"/"+localeDirections.getStringKey("directions_tab_east");	    		
    	 }
    	 
    	 if(faixa1.equals("S")) {
    		 dir1[sheetIndex] = localeDirections.getStringKey("directions_tab_south")+"/"+localeDirections.getStringKey("directions_tab_east");	    		
        	 dir2[sheetIndex] = localeDirections.getStringKey("directions_tab_north")+"/"+localeDirections.getStringKey("directions_tab_west");
    		    		
    	 }
    	 
    	 if(faixa1.equals("L")) {
    		 dir1[sheetIndex] = localeDirections.getStringKey("directions_tab_east")+"/"+localeDirections.getStringKey("directions_tab_south");
    		 dir2[sheetIndex] = localeDirections.getStringKey("directions_tab_west")+"/"+localeDirections.getStringKey("directions_tab_north");    		
    	 }
    	 
    	 if(faixa1.equals("O")) {
    		 dir1[sheetIndex] = localeDirections.getStringKey("directions_tab_west")+"/"+localeDirections.getStringKey("directions_tab_north"); 
    		 dir2[sheetIndex] = localeDirections.getStringKey("directions_tab_east")+"/"+localeDirections.getStringKey("directions_tab_south");   		 
    	 }	    	 
      }  
	 
	 
	 //USED FOR METEO
		public String monthComparison(int selectedMes) {

			String selectMonth = "";				
									
			if (selectedMes == 1)
				selectMonth = localeCalendar.getStringKey("january");
			if (selectedMes == 2)
				selectMonth = localeCalendar.getStringKey("february");
			if (selectedMes == 3)
				selectMonth = localeCalendar.getStringKey("march");
			if (selectedMes == 4)
				selectMonth = localeCalendar.getStringKey("april");
			if (selectedMes == 5)
				selectMonth = localeCalendar.getStringKey("may");
			if (selectedMes == 6)
				selectMonth = localeCalendar.getStringKey("june");
			if (selectedMes == 7)
				selectMonth = localeCalendar.getStringKey("july");
			if (selectedMes == 8)
				selectMonth = localeCalendar.getStringKey("august");
			if (selectedMes == 9)
				selectMonth = localeCalendar.getStringKey("september");
			if (selectedMes == 10)
				selectMonth = localeCalendar.getStringKey("october");
			if (selectedMes == 11)
				selectMonth = localeCalendar.getStringKey("november");
			if (selectedMes == 12)
				selectMonth = localeCalendar.getStringKey("december");						
			
			return selectMonth;
		}
	
	 	
}
