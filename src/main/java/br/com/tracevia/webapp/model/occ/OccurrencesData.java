package br.com.tracevia.webapp.model.occ;

public class OccurrencesData {
	private int data_id;
	private String data_number;
	private String type;
	private String origin;
	private String state_occurrences;
	private String start_date;
	private String start_hour;
	private String start_minute;
	private String end_date;
	private String end_hour;
	private String end_minute;
	private String cause;
	private String cause_description;
	private String kilometer;
	private String highway;	
	private String local_state;
	private String direction;
	private String lane;
	private String others;
	private String local_condition;
	private String traffic;
	private String characteristic;
	private String interference;
	private String signaling;
	private String conductor_condition;
	private String description_title;
	private String description_text;
	private String involved_type;
	private String involved_When;
	private String involved_description;
	private String file;
	private String procedure_name;
	private String procedure_description;
	private String traffic_hours;
	private String traffic_minutes;
	private String traffic_extension;
	private String traffic_stopped;
	private String damage_date;
	private String damage_type_damage;
	private String damage_gravity;
	private String damage_amount;
	private String demage_description;
	private String action_type;
	private String action_start;
	private String action_duration;
	private String action_end;
	private String action_description;
	private String date_time;
	private String actionStartData;
	private String actionStartHour;
	private String actionStartMinute;
	private String actionEndData;
	private String actionEndHour;
	private String actionEndMinute;
	private String trackStartDate;
	private String trackStartHour;
	private String trackStartMinute;
	private String trackEndDate;
	private String trackEndHour;
	private String trackEndMinute;
	private String damageDescriptionMain;
	private String damageDescriptionInternal;
	private String damageUnity;
	private String causeDescrInter;
	private String descriptionInter;
	private String involvedInter;
	private String actionInter;
	private String statusAction;
	private String localFiles;

	public OccurrencesData(int data_id, String data_number, String type, String origin, String state_occurrences,
			String start_date, String start_hour, String start_minute, String end_date, String end_hour,
			String end_minute, String cause, String cause_description, String kilometer, String highway,
			String local_state, String direction, String lane, String others, String local_condition, String traffic,
			String characteristic, String interference, String signaling, String conductor_condition,
			String description_title, String description_text, String involved_type, String involved_When,
			String involved_description, String file, String procedure_name, String procedure_description,
			String traffic_hours, String traffic_minutes, String traffic_extension, String traffic_stopped,
			String damage_date, String damage_type_damage, String damage_gravity, String damage_amount,
			String demage_description, String action_type, String action_start, String action_duration,
			String action_end, String action_description, String date_time, String actionStartData,
			String actionStartHour, String actionStartMinute, String actionEndData, String actionEndHour,
			String actionEndMinute, String trackStartDate, String trackStartHour, String trackStartMinute,
			String trackEndDate, String trackEndHour, String trackEndMinute, String damageDescriptionMain,
			String damageDescriptionInternal, String damageUnity, String causeDescrInter, String descriptionInter,
			String involvedInter, String actionInter, String statusAction, String localFiles)
	{

		this.data_id = data_id;
		this.data_number = data_number;
		this.type = type;
		this.origin = origin;
		this.state_occurrences = state_occurrences;
		this.start_date = start_date;
		this.start_hour = start_hour;
		this.start_minute = start_minute;
		this.end_date = end_date;
		this.end_hour = end_hour;
		this.end_minute = end_minute;
		this.cause = cause;
		this.cause_description = cause_description;
		this.kilometer = kilometer;
		this.highway = highway;
		this.local_state = local_state;
		this.direction = direction;
		this.lane = lane;
		this.others = others;
		this.local_condition = local_condition;
		this.traffic = traffic;
		this.characteristic = characteristic;
		this.interference = interference;
		this.signaling = signaling;
		this.conductor_condition = conductor_condition;
		this.description_title = description_title;
		this.description_text = description_text;
		this.involved_type = involved_type;
		this.involved_When = involved_When;
		this.involved_description = involved_description;
		this.file = file;
		this.procedure_name = procedure_name;
		this.procedure_description = procedure_description;
		this.traffic_hours = traffic_hours;
		this.traffic_minutes = traffic_minutes;
		this.traffic_extension = traffic_extension;
		this.traffic_stopped = traffic_stopped;
		this.damage_date = damage_date;
		this.damage_type_damage = damage_type_damage;
		this.damage_gravity = damage_gravity;
		this.damage_amount = damage_amount;
		this.demage_description = demage_description;
		this.action_type = action_type;
		this.action_start = action_start;
		this.action_duration = action_duration;
		this.action_end = action_end;
		this.action_description = action_description;
		this.date_time = date_time;
		this.actionStartData = actionStartData;
		this.actionStartHour = actionStartHour;
		this.actionStartMinute = actionStartMinute;
		this.actionEndData = actionEndData;
		this.actionEndHour = actionEndHour;
		this.actionEndMinute = actionEndMinute;
		this.trackStartDate = trackStartDate;
		this.trackStartHour = trackStartHour;
		this.trackStartMinute = trackStartMinute;
		this.trackEndDate = trackEndDate;
		this.trackEndHour = trackEndHour;
		this.trackEndMinute = trackEndMinute;
		this.damageDescriptionMain = damageDescriptionMain;
		this.damageDescriptionInternal = damageDescriptionInternal;
		this.damageUnity = damageUnity;
		this.causeDescrInter = actionInter;
		this.descriptionInter = descriptionInter;
		this.involvedInter = involvedInter;
		this.actionInter = actionInter;
		this.statusAction = statusAction;
		this.localFiles = localFiles;
	}


	public OccurrencesData() {
	}

	
	public int getData_id() {
		return data_id;
	}


	public String getData_number() {
		return data_number;
	}


	public String getType() {
		return type;
	}


	public String getOrigin() {
		return origin;
	}


	public String getState_occurrences() {
		return state_occurrences;
	}


	public String getStart_date() {
		return start_date;
	}


	public String getStart_hour() {
		return start_hour;
	}


	public String getStart_minute() {
		return start_minute;
	}


	public String getEnd_date() {
		return end_date;
	}


	public String getEnd_hour() {
		return end_hour;
	}


	public String getEnd_minute() {
		return end_minute;
	}


	public String getCause() {
		return cause;
	}


	public String getCause_description() {
		return cause_description;
	}


	public String getKilometer() {
		return kilometer;
	}


	public String getHighway() {
		return highway;
	}


	public String getLocal_state() {
		return local_state;
	}


	public String getDirection() {
		return direction;
	}


	public String getLane() {
		return lane;
	}


	public String getOthers() {
		return others;
	}


	public String getLocal_condition() {
		return local_condition;
	}


	public String getTraffic() {
		return traffic;
	}


	public String getCharacteristic() {
		return characteristic;
	}


	public String getInterference() {
		return interference;
	}


	public String getSignaling() {
		return signaling;
	}


	public String getConductor_condition() {
		return conductor_condition;
	}


	public String getDescription_title() {
		return description_title;
	}


	public String getDescription_text() {
		return description_text;
	}


	public String getInvolved_type() {
		return involved_type;
	}


	public String getInvolved_When() {
		return involved_When;
	}


	public String getInvolved_description() {
		return involved_description;
	}


	public String getFile() {
		return file;
	}


	public String getProcedure_name() {
		return procedure_name;
	}


	public String getProcedure_description() {
		return procedure_description;
	}


	public String getTraffic_hours() {
		return traffic_hours;
	}


	public String getTraffic_minutes() {
		return traffic_minutes;
	}


	public String getTraffic_extension() {
		return traffic_extension;
	}


	public String getTraffic_stopped() {
		return traffic_stopped;
	}


	public String getDamage_date() {
		return damage_date;
	}


	public String getDamage_type_damage() {
		return damage_type_damage;
	}


	public String getDamage_gravity() {
		return damage_gravity;
	}


	public String getDamage_amount() {
		return damage_amount;
	}


	public String getDemage_description() {
		return demage_description;
	}


	public String getAction_type() {
		return action_type;
	}


	public String getAction_start() {
		return action_start;
	}


	public String getAction_duration() {
		return action_duration;
	}


	public String getAction_end() {
		return action_end;
	}


	public String getAction_description() {
		return action_description;
	}
	public String getDamageUnity() {
		return damageUnity;
	}
	
	public String getCauseDescrInter() {
		return causeDescrInter;
	}


	public void setCauseDescrInter(String causeDescrInter) {
		this.causeDescrInter = causeDescrInter;
	}


	public String getDescriptionInter() {
		return descriptionInter;
	}


	public void setDescriptionInter(String descriptionInter) {
		this.descriptionInter = descriptionInter;
	}


	public String getInvolvedInter() {
		return involvedInter;
	}


	public void setInvolvedInter(String involvedInter) {
		this.involvedInter = involvedInter;
	}


	public String getActionInter() {
		return actionInter;
	}

	
	public String getStatusAction() {
		return statusAction;
	}


	public void setStatusAction(String statusAction) {
		this.statusAction = statusAction;
	}


	public void setActionInter(String actionInter) {
		this.actionInter = actionInter;
	}


	public void setData_id(int data_id) {
		this.data_id = data_id;
	}

	public void setData_number(String data_number) {
		this.data_number = data_number;
	}


	public void setType(String type) {
		this.type = type;
	}


	public void setOrigin(String origin) {
		this.origin = origin;
	}


	public void setState_occurrences(String state_occurrences) {
		this.state_occurrences = state_occurrences;
	}


	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}


	public void setStart_hour(String start_hour) {
		this.start_hour = start_hour;
	}


	public void setStart_minute(String start_minute) {
		this.start_minute = start_minute;
	}


	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}


	public void setEnd_hour(String end_hour) {
		this.end_hour = end_hour;
	}


	public void setEnd_minute(String end_minute) {
		this.end_minute = end_minute;
	}


	public void setCause(String cause) {
		this.cause = cause;
	}


	public void setCause_description(String cause_description) {
		this.cause_description = cause_description;
	}


	public void setKilometer(String kilometer) {
		this.kilometer = kilometer;
	}


	public void setHighway(String highway) {
		this.highway = highway;
	}


	public void setLocal_state(String local_state) {
		this.local_state = local_state;
	}


	public void setDirection(String direction) {
		this.direction = direction;
	}


	public void setLane(String lane) {
		this.lane = lane;
	}


	public void setOthers(String others) {
		this.others = others;
	}


	public void setLocal_condition(String local_condition) {
		this.local_condition = local_condition;
	}


	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}


	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}


	public void setInterference(String interference) {
		this.interference = interference;
	}


	public void setSignaling(String signaling) {
		this.signaling = signaling;
	}


	public void setConductor_condition(String conductor_condition) {
		this.conductor_condition = conductor_condition;
	}


	public void setDescription_title(String description_title) {
		this.description_title = description_title;
	}


	public void setDescription_text(String description_text) {
		this.description_text = description_text;
	}


	public void setInvolved_type(String involved_type) {
		this.involved_type = involved_type;
	}


	public void setInvolved_When(String involved_When) {
		this.involved_When = involved_When;
	}


	public void setInvolved_description(String involved_description) {
		this.involved_description = involved_description;
	}


	public void setFile(String file) {
		this.file = file;
	}


	public void setProcedure_name(String procedure_name) {
		this.procedure_name = procedure_name;
	}


	public void setProcedure_description(String procedure_description) {
		this.procedure_description = procedure_description;
	}


	public void setTraffic_hours(String traffic_hours) {
		this.traffic_hours = traffic_hours;
	}


	public void setTraffic_minutes(String traffic_minutes) {
		this.traffic_minutes = traffic_minutes;
	}


	public void setTraffic_extension(String traffic_extension) {
		this.traffic_extension = traffic_extension;
	}


	public void setTraffic_stopped(String traffic_stopped) {
		this.traffic_stopped = traffic_stopped;
	}


	public void setDamage_date(String damage_date) {
		this.damage_date = damage_date;
	}


	public void setDamage_type_damage(String damage_type_damage) {
		this.damage_type_damage = damage_type_damage;
	}


	public void setDamage_gravity(String damage_gravity) {
		this.damage_gravity = damage_gravity;
	}


	public void setDamage_amount(String damage_amount) {
		this.damage_amount = damage_amount;
	}


	public void setDemage_description(String demage_description) {
		this.demage_description = demage_description;
	}


	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}


	public void setAction_start(String action_start) {
		this.action_start = action_start;
	}


	public void setAction_duration(String action_duration) {
		this.action_duration = action_duration;
	}


	public void setAction_end(String action_end) {
		this.action_end = action_end;
	}


	public void setAction_description(String action_description) {
		this.action_description = action_description;
	}
	
	public void setDamageUnity(String damageUnity) {
		this.damageUnity = damageUnity;
	}

	public String getDate_time() {
		return date_time;
	}


	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}


	public String getActionStartData() {
		return actionStartData;
	}


	public void setActionStartData(String actionStartData) {
		this.actionStartData = actionStartData;
	}


	public String getActionStartHour() {
		return actionStartHour;
	}


	public void setActionStartHour(String actionStartHour) {
		this.actionStartHour = actionStartHour;
	}


	public String getActionStartMinute() {
		return actionStartMinute;
	}


	public void setActionStartMinute(String actionStartMinute) {
		this.actionStartMinute = actionStartMinute;
	}


	public String getActionEndData() {
		return actionEndData;
	}


	public void setActionEndData(String actionEndData) {
		this.actionEndData = actionEndData;
	}


	public String getActionEndHour() {
		return actionEndHour;
	}


	public void setActionEndHour(String actionEndHour) {
		this.actionEndHour = actionEndHour;
	}


	public String getActionEndMinute() {
		return actionEndMinute;
	}


	public void setActionEndMinute(String actionEndMinute) {
		this.actionEndMinute = actionEndMinute;
	}


	public String getTrackStartDate() {
		return trackStartDate;
	}


	public void setTrackStartDate(String trackStartDate) {
		this.trackStartDate = trackStartDate;
	}


	public String getTrackStartHour() {
		return trackStartHour;
	}


	public void setTrackStartHour(String trackStartHour) {
		this.trackStartHour = trackStartHour;
	}


	public String getTrackStartMinute() {
		return trackStartMinute;
	}


	public void setTrackStartMinute(String trackStartMinute) {
		this.trackStartMinute = trackStartMinute;
	}


	public String getTrackEndDate() {
		return trackEndDate;
	}


	public void setTrackEndDate(String trackEndDate) {
		this.trackEndDate = trackEndDate;
	}


	public String getTrackEndHour() {
		return trackEndHour;
	}


	public void setTrackEndHour(String trackEndHour) {
		this.trackEndHour = trackEndHour;
	}


	public String getTrackEndMinute() {
		return trackEndMinute;
	}


	public void setTrackEndMinute(String trackEndMinute) {
		this.trackEndMinute = trackEndMinute;
	}


	public String getDamageDescriptionMain() {
		return damageDescriptionMain;
	}


	public void setDamageDescriptionMain(String damageDescriptionMain) {
		this.damageDescriptionMain = damageDescriptionMain;
	}


	public String getDamageDescriptionInternal() {
		return damageDescriptionInternal;
	}


	public void setDamageDescriptionInternal(String damageDescriptionInternal) {
		this.damageDescriptionInternal = damageDescriptionInternal;
	}


	public String getLocalFiles() {
		return localFiles;
	}


	public void setLocalFiles(String localFiles) {
		this.localFiles = localFiles;
	}
	


	
	

	
	
}

