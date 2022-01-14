package br.com.tracevia.webapp.methods;

import br.com.tracevia.webapp.util.LocaleUtil;

public class TranslationMethods {

	LocaleUtil locale, localePeriod, localeSat, localeDirections, localeCalendar, localeOcc, localeEmail, localeWim, localeDai,
	localeReports;

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

		localeWim = new LocaleUtil();
		localeWim.getResourceBundle(LocaleUtil.LABELS_WIM);

		localeDai = new LocaleUtil();
		localeDai.getResourceBundle(LocaleUtil.LABELS_DAI);
		
		localeReports = new LocaleUtil();
		localeReports.getResourceBundle(LocaleUtil.LABELS_REPORTS);

	}
	
	public String daiLabels(String dai) {
		String converteDai= "";
		switch (dai) {
		case "Unknown(Event)": converteDai = localeDai.getStringKey("unknown"); break;
		case "Congestion(Event)": converteDai = localeDai.getStringKey("congestion"); break;
		case "Illegal Parking(Event)": converteDai = localeDai.getStringKey("parking"); break;
		case "Wrong-Way Driving(Event)": converteDai = localeDai.getStringKey("driver_opposite"); break;
		case "Pedestrain(Event)": converteDai = localeDai.getStringKey("pedestrian"); break;
		case "Thrown Object(Event)": converteDai = localeDai.getStringKey("thrown_object"); break;
		case "Smoke object(Event)": converteDai = localeDai.getStringKey("Smoke"); break;
		case "Driving on the Lane Line(Event)": converteDai = localeDai.getStringKey("driver_lane_yellow"); break;
		case "Blacklist data(Event)": converteDai = localeDai.getStringKey("blacklist"); break;
		case "Over speed(Event)": converteDai = localeDai.getStringKey("over_speed"); break;
		case "Illegal Lane Change(Event)": converteDai = localeDai.getStringKey("lane_change"); break;
		case "Jumping in line(Event)": converteDai = localeDai.getStringKey("jumping"); break;
		case "Braking the road(Event)": converteDai = localeDai.getStringKey("braking"); break;
		case "Construction(Event)": converteDai = localeDai.getStringKey("construction"); break;
		case "Road accident(Event)": converteDai = localeDai.getStringKey("road_accident"); break;
		case "Illegal parking in parallel(Event)": converteDai = localeDai.getStringKey("parking_parallel"); break;
		case "Mist(Event)": converteDai = localeDai.getStringKey("mist"); break;
		case "Occupying the emergency rail(Event)": converteDai = localeDai.getStringKey("emergency_rail"); break;
		case "Fire": converteDai = localeDai.getStringKey("fire"); break;
		case "Manual fortification Barrage(Event)": converteDai = localeDai.getStringKey("fortification_dam"); break;
		case "Occupying the passing lane(Event)": converteDai = localeDai.getStringKey("passing_lane"); break;
		case "Violating the prohibition sign(Event)": converteDai = localeDai.getStringKey("violating_prohibition"); break;
		case "Sudden stop of speed(Event)": converteDai = localeDai.getStringKey("sudden_stop"); break;
		case "Moving slowly(Event)": converteDai = localeDai.getStringKey("moving_slowly"); break;
		case "Up": converteDai = localeDai.getStringKey("up"); break;
		case "Two-way": converteDai = localeDai.getStringKey("two_way"); break;
		case "Down": converteDai = localeDai.getStringKey("down"); break;
		default: converteDai = ""; break;
		
		}
		return converteDai;
	}
	
	public String colasLabels(String colas) {
		String converteColas= "";
		switch (colas) {
		case "Unknown(Event)": converteColas = localeDai.getStringKey("unknown"); break;
		case "Congestion(Event)": converteColas = localeDai.getStringKey("congestion"); break;
		case "Illegal Parking(Event)": converteColas = localeDai.getStringKey("parking"); break;
		case "Wrong-Way Driving(Event)": converteColas = localeDai.getStringKey("driver_opposite"); break;
		case "Pedestrain(Event)": converteColas = localeDai.getStringKey("pedestrian"); break;
		case "Thrown Object(Event)": converteColas = localeDai.getStringKey("thrown_object"); break;
		case "Smoke object(Event)": converteColas = localeDai.getStringKey("Smoke"); break;
		case "Driving on the Lane Line(Event)": converteColas = localeDai.getStringKey("driver_lane_yellow"); break;
		case "Blacklist data(Event)": converteColas = localeDai.getStringKey("blacklist"); break;
		case "Over speed(Event)": converteColas = localeDai.getStringKey("over_speed"); break;
		case "Illegal Lane Change(Event)": converteColas = localeDai.getStringKey("lane_change"); break;
		case "Jumping in line(Event)": converteColas = localeDai.getStringKey("jumping"); break;
		case "Braking the road(Event)": converteColas = localeDai.getStringKey("braking"); break;
		case "Construction(Event)": converteColas = localeDai.getStringKey("construction"); break;
		case "Road accident(Event)": converteColas = localeDai.getStringKey("road_accident"); break;
		case "Illegal parking in parallel(Event)": converteColas = localeDai.getStringKey("parking_parallel"); break;
		case "Mist(Event)": converteColas = localeDai.getStringKey("mist"); break;
		case "Occupying the emergency rail(Event)": converteColas = localeDai.getStringKey("emergency_rail"); break;
		case "Fire": converteColas = localeDai.getStringKey("fire"); break;
		case "Manual fortification Barrage(Event)": converteColas = localeDai.getStringKey("fortification_dam"); break;
		case "Occupying the passing lane(Event)": converteColas = localeDai.getStringKey("passing_lane"); break;
		case "Violating the prohibition sign(Event)": converteColas = localeDai.getStringKey("violating_prohibition"); break;
		case "Sudden stop of speed(Event)": converteColas = localeDai.getStringKey("sudden_stop"); break;
		case "Moving slowly(Event)": converteColas = localeDai.getStringKey("moving_slowly"); break;
		case "Up": converteColas = localeDai.getStringKey("up"); break;
		case "Two-way": converteColas = localeDai.getStringKey("two_way"); break;
		case "Down": converteColas = localeDai.getStringKey("down"); break;
		default: converteColas = ""; break;
		
		}
		return converteColas;
	}
	
	public String wimLabels(String wim) {
		String converteWim = "";
		switch(wim){
		case "WIM REPORT": converteWim = localeWim.getStringKey("wimReport");break;
		case "N SERIAL": converteWim = localeWim.getStringKey("serial_number");break;
		case "CLASSE": converteWim = localeWim.getStringKey("classe");break;
		case "DATAHOUR": converteWim = localeWim.getStringKey("date_hour");break;
		case "INFORMATION1": converteWim = localeWim.getStringKey("titleReport");break;
		case "INFORMATION2": converteWim = localeWim.getStringKey("weight_distance");break;
		case "AXES": converteWim = localeWim.getStringKey("axes"); break;
		case "SPEED" : converteWim = localeWim.getStringKey("speed");break;
		case "PBT" : converteWim = localeWim.getStringKey("pbtTotal");break;
		case "TYPE" : converteWim = localeWim.getStringKey("type");break;
		case "WEIGHT" : converteWim = localeWim.getStringKey("weight");break;
		case "DSTAXES" : converteWim = localeWim.getStringKey("dstAxes");break;
		case "indicator1" : converteWim = localeWim.getStringKey("indicator01");break;
		case "indicator2" : converteWim = localeWim.getStringKey("indicator02");break;
		case "indicator3" : converteWim = localeWim.getStringKey("indicator03");break;
		case "indicator" : converteWim = localeWim.getStringKey("weight_indicator");break;

		}

		return converteWim;
	}
	public String occLabels(String occ) {
		//var
		String converteOcc = "";

		switch(occ){

		case "Eventos" : converteOcc = localeOcc.getStringKey("occ_titulo_evento"); break;
		case "dateOcc" : converteOcc = localeOcc.getStringKey("occ_titulo_data_inicio_fim"); break;
		case "CausaPro" : converteOcc = localeOcc.getStringKey("occ_titulo_causa_provavel"); break;
		case "Evento Local" : converteOcc = localeOcc.getStringKey("occ_titulo_evento_local"); break;
		case "Detalhes" : converteOcc = localeOcc.getStringKey("occ_titulo_detalhes"); break;
		case "description" : converteOcc = localeOcc.getStringKey("occ_register_descricao"); break;
		case "Envolvidos" : converteOcc = localeOcc.getStringKey("occ_titulo_envolvidos"); break;
		case "Traffic" : converteOcc = localeOcc.getStringKey("occ_titulo_evento_transito"); break;
		case "Danos" : converteOcc = localeOcc.getStringKey("occ_titulo_danos"); break;
		case "action" : converteOcc = localeOcc.getStringKey("occ_acoes_acoes"); break;
		case "Tipo" : converteOcc = localeOcc.getStringKey("occ_list_table_type"); break;
		case "Origem" : converteOcc = localeOcc.getStringKey("occ_register_table_source"); break;
		case "Situacao" : converteOcc = localeOcc.getStringKey("occ_register_table_status"); break;
		case "Inicial" : converteOcc = localeOcc.getStringKey("occ_inicial"); break;
		case "Final" : converteOcc = localeOcc.getStringKey("occ_final"); break;
		case "Causa" : converteOcc = localeOcc.getStringKey("occ_list_table_cause"); break;
		case "Rodovia" : converteOcc = localeOcc.getStringKey("occ_register_rodovia"); break;
		case "Estado" : converteOcc = localeOcc.getStringKey("occ_register_Estado"); break;
		case "Sentido" : converteOcc = localeOcc.getStringKey("occ_register_sentido"); break;
		case "Faixa" : converteOcc = localeOcc.getStringKey("occ_register_faixa"); break;
		case "obs" : converteOcc = localeOcc.getStringKey("occ_register_outros_eventos"); break;
		case "condition" : converteOcc = localeOcc.getStringKey("occ_register_cond_local"); break;
		case "char" : converteOcc = localeOcc.getStringKey("occ_register_caracteristica"); break;
		case "Interferencia Faixa" : converteOcc = localeOcc.getStringKey("occ_register_interf_faixa"); break;
		case "sinalizacao" : converteOcc = localeOcc.getStringKey("occ_register_sinalizacao"); break;
		case "Situacao Condutor" : converteOcc = localeOcc.getStringKey("occ_register_situacao_condutor"); break;
		case "Titulo Descricao" : converteOcc = localeOcc.getStringKey("occ_titulo_descricao"); break;
		case "Extensao(KM)" : converteOcc = localeOcc.getStringKey("occ_register_extesao"); break;
		case "Pista Interrompida" : converteOcc = localeOcc.getStringKey("occ_register_pista_interrp"); break;
		case "Quantidade" : converteOcc = localeOcc.getStringKey("occ_damage_amount"); break;
		case "Assinatura" : converteOcc = localeOcc.getStringKey("occ_assinatura"); break;
		case "Data do relatorio" : converteOcc = localeOcc.getStringKey("occ_relatorio"); break;
		case "report" : converteOcc = localeOcc.getStringKey("occ_title_pdf"); break;
		case "Gravidade" : converteOcc = localeOcc.getStringKey("occ_register_gravidade"); break;
		case "Unidade" : converteOcc = localeOcc.getStringKey("occ_damage_unity"); break;
		case "Condition track" : converteOcc = localeOcc.getStringKey("occ_register_cond_trafego"); break;
		case "operador" : converteOcc = localeOcc.getStringKey("nome_operador"); break;
		case "occ" : converteOcc = localeOcc.getStringKey("occ_list_table_id"); break;
		}

		return converteOcc;
	}

	public String listOcc(String occ) {

		String converteOcc = "";

		switch(occ) {
		//type
		case "Accident with victim" : converteOcc = localeOcc.getStringKey("type1"); break;
		case "Accident with serious victim" : converteOcc = localeOcc.getStringKey("type2"); break;
		case "Animal en la carretera" : converteOcc = localeOcc.getStringKey("type3"); break;
		case "Objeto en la carretera" : converteOcc = localeOcc.getStringKey("type4"); break;
		case "Accidente leve" : converteOcc = localeOcc.getStringKey("type5"); break;
		case "Accidente moderado" : converteOcc = localeOcc.getStringKey("type6"); break;
		case "Accidente grave" : converteOcc = localeOcc.getStringKey("type7"); break;
		case "Iluminación" : converteOcc = localeOcc.getStringKey("type8"); break;
		case "Otros" : converteOcc = localeOcc.getStringKey("type9"); break;

		//causa provavel
		case "Desgaste natural" : converteOcc = localeOcc.getStringKey("probable_cause1"); break;
		case "Collision" : converteOcc = localeOcc.getStringKey("probable_cause2"); break;
		case "Vandalismo" : converteOcc = localeOcc.getStringKey("probable_cause3"); break;
		case "Lluvia" : converteOcc = localeOcc.getStringKey("probable_cause4"); break;
		case "Temperatura" : converteOcc = localeOcc.getStringKey("probable_cause5"); break;
		case "Viento" : converteOcc = localeOcc.getStringKey("probable_cause6"); break;
		case "Rayo" : converteOcc = localeOcc.getStringKey("probable_cause7"); break;
		case "Others" : converteOcc = localeOcc.getStringKey("probable_cause8"); break;
		case "------------" : converteOcc = localeOcc.getStringKey("probable_cause9"); break;
		//status
		case "Abierto" : converteOcc = localeOcc.getStringKey("state1"); break;
		case "Cancelado" : converteOcc = localeOcc.getStringKey("state2"); break;
		case "Finalizado" : converteOcc = localeOcc.getStringKey("state3"); break;

		}

		return converteOcc;
	}
	//
	//USERS PANEL TRANSLATION OCCURRENCES
	public String occurrencesTranslator(String occ) {

		//var
		String converteOcc = "";
		switch(occ){

		//Rodovia
		case "Autopista Tuxpam - Tampico" : converteOcc = localeOcc.getStringKey("highway1"); break;
		case "Autopista Cardel - Poza Rica" : converteOcc = localeOcc.getStringKey("highway2"); break;
		case "Autopista Siervo de la Nacion" : converteOcc = localeOcc.getStringKey("highway3"); break;

		//STATE
		case "Veracruz" : converteOcc = localeOcc.getStringKey("estado_local1"); break;
		case "CDMX" : converteOcc = localeOcc.getStringKey("estado_local2"); break;

		//DIRECTION
		case "Norte" : converteOcc = localeOcc.getStringKey("direction1"); break;
		case "Sur" : converteOcc = localeOcc.getStringKey("direction2"); break;
		case "Este" : converteOcc = localeOcc.getStringKey("direction3"); break;
		case "Oeste" : converteOcc = localeOcc.getStringKey("direction4"); break;

		//FAIXA
		case "1A" : converteOcc = localeOcc.getStringKey("lane1"); break;
		case "2A" : converteOcc = localeOcc.getStringKey("lane2"); break;
		case "1B" : converteOcc = localeOcc.getStringKey("lane3"); break;
		case "2B" : converteOcc = localeOcc.getStringKey("lane4"); break;

		//TYPO
		case "Accident with victim" : converteOcc = localeOcc.getStringKey("type1"); break;
		case "Accident with serious victim" : converteOcc = localeOcc.getStringKey("type2"); break;
		case "Animal en la carretera" : converteOcc = localeOcc.getStringKey("type3"); break;
		case "Objeto en la carretera" : converteOcc = localeOcc.getStringKey("type4"); break;
		case "Accidente leve" : converteOcc = localeOcc.getStringKey("type5"); break;
		case "Accidente moderado" : converteOcc = localeOcc.getStringKey("type6"); break;
		case "Accidente grave" : converteOcc = localeOcc.getStringKey("type7"); break;
		case "illumination" : converteOcc = localeOcc.getStringKey("type8"); break;
		case "Otros" : converteOcc = localeOcc.getStringKey("type9"); break;

		//ORIGIN
		case "Cameras" : converteOcc = localeOcc.getStringKey("origin1"); break;
		case "SOS" : converteOcc = localeOcc.getStringKey("origin2"); break;
		case "tel" : converteOcc = localeOcc.getStringKey("origin3"); break;
		case "0800" : converteOcc = localeOcc.getStringKey("origin4"); break;
		case "Correo" : converteOcc = localeOcc.getStringKey("origin5"); break;
		case "Outro" : converteOcc = localeOcc.getStringKey("origin6"); break;

		//STATUS
		case "Abierto" : converteOcc = localeOcc.getStringKey("state1"); break;
		case "Cancelado" : converteOcc = localeOcc.getStringKey("state2"); break;
		case "Finalizado" : converteOcc = localeOcc.getStringKey("state3"); break;

		//CAUSE PROBABLE
		case "Desgaste natural" : converteOcc = localeOcc.getStringKey("probable_cause1"); break;
		case "Collision" : converteOcc = localeOcc.getStringKey("probable_cause2"); break;
		case "Vandalismo" : converteOcc = localeOcc.getStringKey("probable_cause3"); break;
		case "Lluvia" : converteOcc = localeOcc.getStringKey("probable_cause4"); break;
		case "Temperatura" : converteOcc = localeOcc.getStringKey("probable_cause5"); break;
		case "Viento" : converteOcc = localeOcc.getStringKey("probable_cause6"); break;
		case "Rayo" : converteOcc = localeOcc.getStringKey("probable_cause7"); break;
		case "Others" : converteOcc = localeOcc.getStringKey("probable_cause8"); break;
		case "------------" : converteOcc = localeOcc.getStringKey("probable_cause9"); break;

		//CONDITION
		case "day" : converteOcc = localeOcc.getStringKey("condition1"); break;
		case "Rainy day": converteOcc = localeOcc.getStringKey("condition2"); break;
		case "Cloudy day" : converteOcc = localeOcc.getStringKey("condition3"); break;
		case "Noche" : converteOcc = localeOcc.getStringKey("condition4"); break;
		case "Noche con lluvia" : converteOcc = localeOcc.getStringKey("condition5"); break;
		case "Noche con niebla" : converteOcc = localeOcc.getStringKey("condition6"); break;

		//TRACK
		case "Normal" : converteOcc = localeOcc.getStringKey("conditionTrack1"); break;
		case "Moderado" : converteOcc = localeOcc.getStringKey("conditionTrack2"); break;
		case "Denso" : converteOcc = localeOcc.getStringKey("conditionTrack3"); break;
		case "Detenido" : converteOcc = localeOcc.getStringKey("conditionTrack4"); break;
		case "Nulo" : converteOcc = localeOcc.getStringKey("conditionTrack5"); break;

		//CHARACTERISTIC
		case "Straight line" : converteOcc = localeOcc.getStringKey("characteristic1"); break;
		case "Subida recta" : converteOcc = localeOcc.getStringKey("characteristic2"); break;
		case "Descenso recto" : converteOcc = localeOcc.getStringKey("characteristic3"); break;
		case "Curva plana" : converteOcc = localeOcc.getStringKey("characteristic4"); break;
		case "Curva hacia arriba" : converteOcc = localeOcc.getStringKey("characteristic5"); break;
		case "Curva Descida" : converteOcc = localeOcc.getStringKey("characteristic6"); break;

		//INTERFERENCE
		case "Vehicle" : converteOcc = localeOcc.getStringKey("interference1"); break;
		case "Objeto" : converteOcc = localeOcc.getStringKey("interference2"); break;
		case "Animal" : converteOcc = localeOcc.getStringKey("interference3"); break;
		case "Persona" : converteOcc = localeOcc.getStringKey("interference4"); break;

		//SIGNALING
		case "Caution" : converteOcc = localeOcc.getStringKey("signaling1"); break;
		case "Hombre de la bandera" : converteOcc = localeOcc.getStringKey("signaling2"); break;
		case "Vehicles" : converteOcc = localeOcc.getStringKey("signaling3"); break;
		case "Usuario" : converteOcc = localeOcc.getStringKey("signaling4"); break;

		//STATE
		case "Enfermo" : converteOcc = localeOcc.getStringKey("stateConductor1"); break;
		case "Intoxicado" : converteOcc = localeOcc.getStringKey("stateConductor2"); break;
		case "Somnoliento" : converteOcc = localeOcc.getStringKey("stateConductor3"); break;
		case "Mild injury" : converteOcc = localeOcc.getStringKey("stateConductor4"); break;
		case "Serious injury" : converteOcc = localeOcc.getStringKey("stateConductor5"); break;

		//ACTION
		case "Asistencia en carretera" : converteOcc = localeOcc.getStringKey("action1"); break;
		case "Ambulancia" : converteOcc = localeOcc.getStringKey("action2"); break;
		case "Policeman" : converteOcc = localeOcc.getStringKey("action3"); break;
		case "Bomberos" : converteOcc = localeOcc.getStringKey("action4"); break;
		case "Cabrestante" : converteOcc = localeOcc.getStringKey("action5"); break;

		//ACTION STATUS
		case "Iniciado" : converteOcc = localeOcc.getStringKey("actionState1"); break;
		case "Pendiente" : converteOcc = localeOcc.getStringKey("actionState2"); break;
		case "Finalizados" : converteOcc = localeOcc.getStringKey("actionState3"); break;

		//track Interrupted
		case "Acotamiento A" : converteOcc = localeOcc.getStringKey("trackInterrupted1"); break;
		case "Acotamiento B" : converteOcc = localeOcc.getStringKey("trackInterrupted2"); break;
		case "Pista A" : converteOcc = localeOcc.getStringKey("trackInterrupted3"); break;
		case "Pista B" : converteOcc = localeOcc.getStringKey("trackInterrupted4"); break;

		//DAMAGE TYPE
		case "VehiclesDamage" : converteOcc = localeOcc.getStringKey("damageType1"); break;
		case "Barrera" : converteOcc = localeOcc.getStringKey("damageType2"); break;
		case "Signaling" : converteOcc = localeOcc.getStringKey("damageType3"); break;
		case "SOS " : converteOcc = localeOcc.getStringKey("damageType4"); break;
		case "CFTV" : converteOcc = localeOcc.getStringKey("damageType5"); break;

		//DAMAGE UNITY
		case "KM" : converteOcc = localeOcc.getStringKey("damageUnity1"); break;
		case "Metros" : converteOcc = localeOcc.getStringKey("damageUnity2"); break;
		case "Partes" : converteOcc = localeOcc.getStringKey("damageUnity3"); break;

		//TYPE INVOLVED
		case "Animais" : converteOcc = localeOcc.getStringKey("involvedType1"); break;
		case "Peatones" : converteOcc = localeOcc.getStringKey("involvedType2"); break;
		case "Objetos" : converteOcc = localeOcc.getStringKey("involvedType3"); break;
		case "VehiclesInvolved" : converteOcc = localeOcc.getStringKey("involvedType4"); break;

		//DAMAGE SEVERITY
		case "Alto" : converteOcc = localeOcc.getStringKey("damageSeverity1"); break;
		case "Medio" : converteOcc = localeOcc.getStringKey("damageSeverity2"); break;
		case "Bajo" : converteOcc = localeOcc.getStringKey("damageSeverity3"); break;

		default: converteOcc = ""; break;

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


	public String CheckDirection(String sentido) throws Exception {

		String direction = "";

		try {

			if (sentido.equals("N")) 			
				direction = localeDirections.getStringKey("directions_north");

			if(sentido.equals("S"))		  
				direction = localeDirections.getStringKey("directions_south");

			if (sentido.equals("L")) 		
				direction = localeDirections.getStringKey("directions_east");		

			if (sentido.equals("O"))			
				direction = localeDirections.getStringKey("directions_west");	

		}catch(NullPointerException ex) {

			//NULL POINTER EXCEPTION QUANDO VALOR FOR NULO
			//NÃƒO MOSTRAR ERRO
		}


		return direction;
	}

	public String Check2ndDirection(String sentido) throws Exception {

		String direction = "";

		try {

			if (sentido.equals("N")) 			
				direction = localeDirections.getStringKey("directions_south");

			if(sentido.equals("S"))		  
				direction = localeDirections.getStringKey("directions_north");

			if (sentido.equals("L")) 		
				direction = localeDirections.getStringKey("directions_west");		

			if (sentido.equals("O"))			
				direction = localeDirections.getStringKey("directions_east");	

		}catch(NullPointerException ex) {

			//NULL POINTER EXCEPTION QUANDO VALOR FOR NULO
			//NÃƒO MOSTRAR ERRO
		}


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


	//AbreviaÃ§Ã£o do MÃªs
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

	//AbreviaÃ§Ã£o do Ano 
	public String yearAbbreviation(String ano) {		
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


	//USED FOR Meteo
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
	
	// -------------------------------------------------------------------------------------------------------

	/**
	 * Método que retorna o nome de uma direção de acordo com sua sigla
	 * @author Wellington 21/09/2021	
	 * @version 1.0
	 * @since 1.0
	 * @param direction - Sigla da direção a ser apresentada
	 * @return O nome da direção
	 */
	public String translateDirections(String direction) {
		
		String dir = "";		
		
		switch(direction) {
		
		case "N": dir = localeDirections.getStringKey("directions_north"); break;
		case "S": dir = localeDirections.getStringKey("directions_south"); break;
		case "L": dir = localeDirections.getStringKey("directions_east"); break;
		case "O": dir = localeDirections.getStringKey("directions_west"); break;		
		
		}		
		
		return dir;	
		
	}
	
	// -------------------------------------------------------------------------------------------------------
	
	/** 
	 * Método para traduzir valor do texto de um período selecionado
	 * @author wellington 13/11/2021
	 * @version 1.0
	 * @since 1.0
	 * @param period texto do período a ser traduzido
	 * @return texto do período traduzido para um idioma
	 */
	public String periodTranslator(String period) {

		String periodLabel = "";
		
		switch(period) {
		
		case "5 minutes" : periodLabel = localePeriod.getStringKey("periods_five_minutes"); break;
		case "6 minutes" : periodLabel = localePeriod.getStringKey("periods_six_minutes"); break;
		case "10 minutes" : periodLabel = localePeriod.getStringKey("periods_teen_minutes"); break;
		case "15 minutes" : periodLabel = localePeriod.getStringKey("periods_fifteen_minutes"); break;
		case "30 minutes" : periodLabel = localePeriod.getStringKey("periods_thirty_minutes"); break;
		case "1 hour" : periodLabel = localePeriod.getStringKey("periods_one_hour"); break;
		case "6 hours" : periodLabel = localePeriod.getStringKey("periods_six_hours"); break;
		case "day" : periodLabel = localePeriod.getStringKey("periods_twenty_four_hours"); break;
		case "month" : periodLabel = localePeriod.getStringKey("periods_month"); break;
		case "year" : periodLabel = localePeriod.getStringKey("periods_year"); break;
		default: periodLabel = " ---- "; break;
						
		}

		
		return periodLabel;			

	}
	
	// -------------------------------------------------------------------------------------------------------
	
     public String directions(String direction) {
		
		String dir = "";		
		
		switch(direction) {
		
		case "NORTH / SOUTH": dir = localeDirections.getStringKey("directions_north") + " / " + localeDirections.getStringKey("directions_south"); break;
		case "SOUTH / NORTH": dir = localeDirections.getStringKey("directions_south") + " / " + localeDirections.getStringKey("directions_north"); break;
		case "EAST / WEST": dir = localeDirections.getStringKey("directions_east") + " / " + localeDirections.getStringKey("directions_west"); break;
		case "WEST / EAST": dir = localeDirections.getStringKey("directions_west") + " / " + localeDirections.getStringKey("directions_east") ; break;		
		
		}		
		
		return dir;	
		
	}
     
  // -------------------------------------------------------------------------------------------------------
     
     public String direction(String direction) {
 		
 		String dir = "";		
 		
 		switch(direction) {
 		
 		case "N": dir = localeDirections.getStringKey("directions_north"); break;
 		case "S": dir = localeDirections.getStringKey("directions_south"); break;
 		case "L": dir = localeDirections.getStringKey("directions_east"); break;
 		case "O": dir = localeDirections.getStringKey("directions_west"); break;		
 		
 		}		
 		
 		return dir;	
 		
 	}
      
   // -------------------------------------------------------------------------------------------------------
     
     
     public String directionTab(String direction) {
 		
 		String dir = "";		
 		
 		switch(direction) {
 		
 		case "N": dir = localeDirections.getStringKey("directions_tab_north"); break;
 		case "S": dir = localeDirections.getStringKey("directions_tab_south"); break;
 		case "L": dir = localeDirections.getStringKey("directions_tab_east"); break;
 		case "O": dir = localeDirections.getStringKey("directions_tab_west"); break;		
 		
 		}		
 		
 		return dir;	
 		
 	}
      
   // -------------------------------------------------------------------------------------------------------
     
     
     public String oppositeDirectionTab(String direction) {
  		
  		String dir = "";		
  		
  		switch(direction) {
  		
  		case "N": dir = localeDirections.getStringKey("directions_tab_south"); break;
  		case "S": dir = localeDirections.getStringKey("directions_tab_north"); break;
  		case "L": dir = localeDirections.getStringKey("directions_tab_west"); break;
  		case "O": dir = localeDirections.getStringKey("directions_tab_east"); break;		
  		
  		}		
  		
  		return dir;	
  		
  	}
       
    // -------------------------------------------------------------------------------------------------------
     
     public String directionAbbreviation(String direction) {
  		
  		String dir = "";		
  		
  		switch(direction) {
  		
  		case "N": dir = localeDirections.getStringKey("directions_north_abbr"); break;
  		case "S": dir = localeDirections.getStringKey("directions_south_abbr"); break;
  		case "L": dir = localeDirections.getStringKey("directions_east_abbr"); break;
  		case "O": dir = localeDirections.getStringKey("directions_west_abbr"); break;		
  		
  		}		
  		
  		return dir;	
  		
  	}
       
    // -------------------------------------------------------------------------------------------------------
      
      public String oppositeDirectionAbbreviation(String direction) {
   		
   		String dir = "";		
   		
   		switch(direction) {
   		
   		case "N": dir = localeDirections.getStringKey("directions_south_abbr"); break;
   		case "S": dir = localeDirections.getStringKey("directions_north_abbr"); break;
   		case "L": dir = localeDirections.getStringKey("directions_west_abbr"); break;
   		case "O": dir = localeDirections.getStringKey("directions_east_abbr"); break;		
   		
   		}		
   		
   		return dir;	
   		
   	}
        
     // -------------------------------------------------------------------------------------------------------


     public String verticalAxisTranslate(String vAxis) {
	    	
	    	LocaleUtil locale = new LocaleUtil();
	    	locale.getResourceBundle(LocaleUtil.LABELS_CHARTS);
	    	
	    	String vertical = "";
	    	
	    	switch(vAxis) {
	    	
	    	case "vehicle": vertical = locale.getStringKey("chart_h_axis_vehicles"); break;
		    	
	    	}
	    	   	    		    	
	    	return vertical;
	    	
	    }
     
     // -------------------------------------------------------------------------------------------------------
     
 }