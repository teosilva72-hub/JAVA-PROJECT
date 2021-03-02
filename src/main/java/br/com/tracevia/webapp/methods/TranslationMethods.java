package br.com.tracevia.webapp.methods;

import br.com.tracevia.webapp.util.LocaleUtil;

public class TranslationMethods {
	
	LocaleUtil locale, localePeriod, localeSat, localeDirections, localeCalendar;
	
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
	
     }
	
	//USERS PANEL TRANSLATION USE
	
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
