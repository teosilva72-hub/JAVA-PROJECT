package br.com.tracevia.webapp.model.sat;

public class SatReports {
	
	//Builder Pattern
	public static class Builder{
		
		private String date;
		private String dateTime;
		private int total;
		private int eqp1, eqp2, eqp3, eqp4, eqp5, eqp6, eqp7, eqp8, eqp9, 	
		eqp10, eqp11, eqp12, eqp13, eqp14, eqp15, eqp16, eqp17, eqp18, eqp19, 
		eqp20, eqp21, eqp22, eqp23, eqp24, eqp25, eqp26, eqp27, eqp28, eqp29,
		eqp30, eqp31, eqp32, eqp33, eqp34, eqp35, eqp36, eqp37, eqp38, eqp39,
		eqp40, eqp41, eqp42, eqp43, eqp45,  eqp44, eqp46, eqp47, eqp48, eqp49, eqp50;
		
		private int motoDir1, lightDir1, heavyDir1, longDir1;
		private int motoDir2, lightDir2, heavyDir2, longDir2;
						
		private String equipment, direction1, direction2;
		
		private int axles_1,  axles_2,
		axles_3, axles_4, axles_5, axles_6, axles_7, axles_8, axles_9, axles_10;
		
		private int class1, class2, class3, class4, class5, class6, class7,class8, class9, class10,
		class11, class12, class13, class14, class15, class16, class17, class18;
				
		private int speed50km, speed70km, speed90km, speed120km, speed150km, speed150km_bigger;
		
		private int speedValue1, speedValue2, total1, total2;
		
		//Constructors		
		public Builder date(String date) {
			this.date = date; 
		    return this;			
		}
		
		public Builder dateTime(String dateTime) {
			this.dateTime = dateTime;			
			return this;			
		}
		
		public Builder total(int total) {
			this.total = total;			
			return this;			
		}
		
		  /* *** COUNT VEHICLES *** */
		
		public Builder equip1(int eqp1) {
			this.eqp1 = eqp1;			
			return this;			
		}
		
		public Builder equip2(int eqp2) {
			this.eqp2 = eqp2;			
			return this;			
		}
		
		public Builder equip3(int eqp3) {
			this.eqp3 = eqp3;			
			return this;			
		}
		
		public Builder equip4(int eqp4) {
			this.eqp4 = eqp4;			
			return this;			
		}
		
		public Builder equip5(int eqp5) {
			this.eqp5 = eqp5;			
			return this;			
		}
		
		public Builder equip6(int eqp6) {
			this.eqp6 = eqp6;			
			return this;			
		}
		
		public Builder equip7(int eqp7) {
			this.eqp7 = eqp7;			
			return this;			
		}
		
		public Builder equip8(int eqp8) {
			this.eqp8 = eqp8;			
			return this;			
		}
		
		public Builder equip9(int eqp9) {
			this.eqp9 = eqp9;			
			return this;			
		}
		
		public Builder equip10(int eqp10) {
			this.eqp10 = eqp10;			
			return this;			
		}
		
		public Builder equip11(int eqp11) {
			this.eqp11 = eqp11;			
			return this;			
		}
		
		public Builder equip12(int eqp12) {
			this.eqp12 = eqp12;			
			return this;			
		}
		
		public Builder equip13(int eqp13) {
			this.eqp13 = eqp13;			
			return this;			
		}
		
		public Builder equip14(int eqp14) {
			this.eqp14 = eqp14;			
			return this;			
		}
		
		public Builder equip15(int eqp15) {
			this.eqp15 = eqp15;			
			return this;			
		}
		
		public Builder equip16(int eqp16) {
			this.eqp16 = eqp16;			
			return this;			
		}
		
		public Builder equip17(int eqp17) {
			this.eqp17 = eqp17;			
			return this;			
		}
		
		public Builder equip18(int eqp18) {
			this.eqp18 = eqp18;			
			return this;			
		}
		
		public Builder equip19(int eqp19) {
			this.eqp19 = eqp19;			
			return this;			
		}
		
		public Builder equip20(int eqp20) {
			this.eqp20 = eqp20;			
			return this;			
		}
		
		public Builder equip21(int eqp21) {
			this.eqp21 = eqp21;			
			return this;			
		}
		
		public Builder equip22(int eqp22) {
			this.eqp22 = eqp22;			
			return this;			
		}
		
		public Builder equip23(int eqp23) {
			this.eqp23 = eqp23;			
			return this;			
		}
		
		public Builder equip24(int eqp24) {
			this.eqp24 = eqp24;			
			return this;			
		}
		
		public Builder equip25(int eqp25) {
			this.eqp25 = eqp25;			
			return this;			
		}
		
		public Builder equip26(int eqp26) {
			this.eqp26 = eqp26;			
			return this;			
		}
		
		public Builder equip27(int eqp27) {
			this.eqp27 = eqp27;			
			return this;			
		}
		
		public Builder equip28(int eqp28) {
			this.eqp28 = eqp28;			
			return this;			
		}
		
		public Builder equip29(int eqp29) {
			this.eqp29 = eqp29;			
			return this;			
		}
		
		public Builder equip30(int eqp30) {
			this.eqp30 = eqp30;			
			return this;			
		}
		
		public Builder equip31(int eqp31) {
			this.eqp31 = eqp31;			
			return this;			
		}
		
		public Builder equip32(int eqp32) {
			this.eqp32 = eqp32;			
			return this;			
		}
						
		public Builder equip33(int eqp33) {
			this.eqp33 = eqp33;			
			return this;			
		}
		
		public Builder equip34(int eqp34) {
			this.eqp34 = eqp34;			
			return this;			
		}
		
		public Builder equip35(int eqp35) {
			this.eqp35 = eqp35;			
			return this;			
		}
		
		public Builder equip36(int eqp36) {
			this.eqp36 = eqp36;			
			return this;			
		}
		
		public Builder equip37(int eqp37) {
			this.eqp37 = eqp37;			
			return this;			
		}
		
		public Builder equip38(int eqp38) {
			this.eqp38 = eqp38;			
			return this;			
		}
		
		public Builder equip39(int eqp39) {
			this.eqp39 = eqp39;			
			return this;			
		}
		
		public Builder equip40(int eqp40) {
			this.eqp40 = eqp40;			
			return this;			
		}
		
		public Builder equip41(int eqp41) {
			this.eqp41 = eqp41;			
			return this;			
		}
		
		public Builder equip42(int eqp42) {
			this.eqp42 = eqp42;			
			return this;			
		}
		
		public Builder equip43(int eqp43) {
			this.eqp43 = eqp43;			
			return this;			
		}
		
		public Builder equip44(int eqp44) {
			this.eqp44 = eqp44;			
			return this;			
		}
		
		public Builder equip45(int eqp45) {
			this.eqp45 = eqp45;			
			return this;			
		}
		
		public Builder equip46(int eqp46) {
			this.eqp46 = eqp46;			
			return this;			
		}
		
		public Builder equip47(int eqp47) {
			this.eqp47 = eqp47;			
			return this;			
		}
		
		public Builder equip48(int eqp48) {
			this.eqp48 = eqp48;			
			return this;			
		}
		
		public Builder equip49(int eqp49) {
			this.eqp49 = eqp49;			
			return this;			
		}
		
		public Builder equip50(int eqp50) {
			this.eqp50 = eqp50;			
			return this;			
		}
		
		   /* *** COUNT VEHICLES *** */
		
		/* *** COUNT VEHICLES FLOW *** */
		
		public Builder motosDir1(int motoVeh) {
			this.motoDir1 = motoVeh;			
			return this;			
		}
		
		public Builder lightDir1(int lightVeh) {
			this.lightDir1 = lightVeh;			
			return this;			
		}
		
		public Builder heavyDir1(int heavyVeh) {
			this.heavyDir1 = heavyVeh;			
			return this;			
		}
		
		public Builder longDir1(int longVeh) {
			this.longDir1 = longVeh;			
			return this;			
		}
		
		//Direction 2
		public Builder motosDir2(int motoVeh) {
			this.motoDir2 = motoVeh;			
			return this;			
		}
		
		public Builder lightDir2(int lightVeh) {
			this.lightDir2 = lightVeh;			
			return this;			
		}
		
		public Builder heavyDir2(int heavyVeh) {
			this.heavyDir2 = heavyVeh;			
			return this;			
		}
		
		public Builder longDir2(int longVeh) {
			this.longDir2 = longVeh;			
			return this;			
		}
		
		
		/* *** COUNT VEHICLES FLOW *** */
			
		/* PERIOD FLOW */
		
		public Builder equipment(String equipment) {
			this.equipment = equipment;			
			return this;			
		}
		
		public Builder direction1(String dir1) {
			this.direction1 = dir1;			
			return this;			
		}
		
		public Builder direction2(String dir2) {
			this.direction2 = dir2;			
			return this;			
		}
		
		/* PERIOD FLOW */
		
		/* TYPE AXLES */
				
		public Builder axles1(int axles1) {
			this.axles_1 = axles1;
			return this;			
		}
		
		public Builder axles2(int axles2) {
			this.axles_2 = axles2;
			return this;			
		}
		
		public Builder axles3(int axles3) {
			this.axles_3 = axles3;
			return this;			
		}
		
		public Builder axles4(int axles4) {
			this.axles_4 = axles4;
			return this;			
		}
		
		public Builder axles5(int axles5) {
			this.axles_5 = axles5;
			return this;			
		}
		
		public Builder axles6(int axles6) {
			this.axles_6 = axles6;
			return this;			
		}
		
		public Builder axles7(int axles7) {
			this.axles_7 = axles7;
			return this;			
		}
		
		public Builder axles8(int axles8) {
			this.axles_8 = axles8;
			return this;			
		}
		
		public Builder axles9(int axles9) {
			this.axles_9 = axles9;
			return this;			
		}	
		
		public Builder axles10(int axles10) {
			this.axles_10 = axles10;
			return this;			
		}	
		
		/* TYPE AXLES */
		
		/* TYPE CLASS AND WEIGHING */
		
		public Builder class1(int class1) {
			this.class1 = class1;
			return this;			
		}
		
		public Builder class2(int class2) {
			this.class2 = class2;
			return this;			
		}
		
		public Builder class3(int class3) {
			this.class3 = class3;
			return this;			
		}
		
		public Builder class4(int class4) {
			this.class4 = class4;
			return this;			
		}
		
		public Builder class5(int class5) {
			this.class5 = class5;
			return this;			
		}
		
		public Builder class6(int class6) {
			this.class6 = class6;
			return this;			
		}
		
		public Builder class7(int class7) {
			this.class7 = class7;
			return this;			
		}
		
		public Builder class8(int class8) {
			this.class8 = class8;
			return this;			
		}
		
		public Builder class9(int class9) {
			this.class9 = class9;
			return this;			
		}
		
		public Builder class10(int class10) {
			this.class10 = class10;
			return this;			
		}
		
		public Builder class11(int class11) {
			this.class11 = class11;
			return this;			
		}
		
		public Builder class12(int class12) {
			this.class12 = class12;
			return this;			
		}
		
		public Builder class13(int class13) {
			this.class13 = class13;
			return this;			
		}
		
		public Builder class14(int class14) {
			this.class14 = class14;
			return this;			
		}
		
		public Builder class15(int class15) {
			this.class15 = class15;
			return this;			
		}
		
		public Builder class16(int class16) {
			this.class16 = class16;
			return this;			
		}
		
		public Builder class17(int class17) {
			this.class17 = class17;
			return this;			
		}
		
		public Builder class18(int class18) {
			this.class18 = class18;
			return this;			
		}
										
		/* TYPE CLASS AND WEIGHING */
				
		
		/* SPEED */
		
		public Builder speed50km(int km50) {
			this.speed50km = km50;
			return this;			
		}	
		
		public Builder speed70km(int km70) {
			this.speed70km = km70;
			return this;			
		}	
		
		public Builder speed90km(int km90) {
			this.speed90km = km90;
			return this;			
		}	
		
		public Builder speed120km(int km120) {
			this.speed120km = km120;
			return this;			
		}	
		
		
		public Builder speed150km(int km150) {
			this.speed150km = km150;
			return this;			
		}	
		
		public Builder speed150Bigger(int km150Bigger) {
			this.speed150km_bigger = km150Bigger;
			return this;			
		}	
		
		/* SPEED */
		
		/*PERIOD AND MONTH FLOW*/
			
		public Builder speed1(int speed1) {
			this.speedValue1 = speed1;
			return this;			
		}
		
		public Builder speed2(int speed2) {
			this.speedValue2 = speed2;
			return this;			
		}
		
		public Builder total1(int total1) {
			this.total1 = total1;
			return this;			
		}
		
		public Builder total2(int total2) {
			this.total2 = total2;
			return this;			
		}
				
		/*PERIOD AND MONTH FLOW*/
		
		//Constructors
		
        //GETTERS
		
		public String getDate() {
			return date;
		}

		public String getDateTime() {
			return dateTime;
		}

		public int getTotal() {
			return total;
		}

		//COUNT VEHICLES
		
		public int getEqp1() {
			return eqp1;
		}

		public int getEqp2() {
			return eqp2;
		}

		public int getEqp3() {
			return eqp3;
		}

		public int getEqp4() {
			return eqp4;
		}

		public int getEqp5() {
			return eqp5;
		}

		public int getEqp6() {
			return eqp6;
		}

		public int getEqp7() {
			return eqp7;
		}

		public int getEqp8() {
			return eqp8;
		}

		public int getEqp9() {
			return eqp9;
		}

		public int getEqp10() {
			return eqp10;
		}

		public int getEqp11() {
			return eqp11;
		}

		public int getEqp12() {
			return eqp12;
		}

		public int getEqp13() {
			return eqp13;
		}

		public int getEqp14() {
			return eqp14;
		}

		public int getEqp15() {
			return eqp15;
		}

		public int getEqp16() {
			return eqp16;
		}

		public int getEqp17() {
			return eqp17;
		}

		public int getEqp18() {
			return eqp18;
		}

		public int getEqp19() {
			return eqp19;
		}

		public int getEqp20() {
			return eqp20;
		}

		public int getEqp21() {
			return eqp21;
		}

		public int getEqp22() {
			return eqp22;
		}

		public int getEqp23() {
			return eqp23;
		}

		public int getEqp24() {
			return eqp24;
		}

		public int getEqp25() {
			return eqp25;
		}

		public int getEqp26() {
			return eqp26;
		}

		public int getEqp27() {
			return eqp27;
		}

		public int getEqp28() {
			return eqp28;
		}

		public int getEqp29() {
			return eqp29;
		}

		public int getEqp30() {
			return eqp30;
		}

		public int getEqp31() {
			return eqp31;
		}

		public int getEqp32() {
			return eqp32;
		}

		public int getEqp33() {
			return eqp33;
		}

		public int getEqp34() {
			return eqp34;
		}

		public int getEqp35() {
			return eqp35;
		}

		public int getEqp36() {
			return eqp36;
		}

		public int getEqp37() {
			return eqp37;
		}

		public int getEqp38() {
			return eqp38;
		}

		public int getEqp39() {
			return eqp39;
		}

		public int getEqp40() {
			return eqp40;
		}

		public int getEqp41() {
			return eqp41;
		}

		public int getEqp42() {
			return eqp42;
		}

		public int getEqp43() {
			return eqp43;
		}

		public int getEqp45() {
			return eqp45;
		}

		public int getEqp44() {
			return eqp44;
		}

		public int getEqp46() {
			return eqp46;
		}

		public int getEqp47() {
			return eqp47;
		}

		public int getEqp48() {
			return eqp48;
		}

		public int getEqp49() {
			return eqp49;
		}

		public int getEqp50() {
			return eqp50;
		}
		
		// COUNT VEHICLES
		
		// COUNT VEHICLES FLOW
		
		public int getMotoDir1() {
			return motoDir1;
		}

		public int getLightDir1() {
			return lightDir1;
		}

		public int getHeavyDir1() {
			return heavyDir1;
		}

		public int getLongDir1() {
			return longDir1;
		}

		public int getMotoDir2() {
			return motoDir2;
		}

		public int getLightDir2() {
			return lightDir2;
		}

		public int getHeavyDir2() {
			return heavyDir2;
		}

		public int getLongDir2() {
			return longDir2;
		}
	
		//COUNT VEHICLES FLOW
						
		//PERIOD FLOW
		
		public String getEquipment() {
			return equipment;
		}

		public String getDirection1() {
			return direction1;
		}

		public String getDirection2() {
			return direction2;
		}
				
		//TYPE AXLES
		
		public int getAxles_1() {
			return axles_1;
		}
				
		public int getAxles_2() {
			return axles_2;
		}

		public int getAxles_3() {
			return axles_3;
		}

		public int getAxles_4() {
			return axles_4;
		}

		public int getAxles_5() {
			return axles_5;
		}

		public int getAxles_6() {
			return axles_6;
		}

		public int getAxles_7() {
			return axles_7;
		}

		public int getAxles_8() {
			return axles_8;
		}

		public int getAxles_9() {
			return axles_9;
		}
		
		public int getAxles_10() {
			return axles_10;
		}

		//TYPE AXLES
		
		//TYPE CLASS AND WEIGHING
		
		public int getClass1() {
			return class1;
		}

		public int getClass2() {
			return class2;
		}

		public int getClass3() {
			return class3;
		}

		public int getClass4() {
			return class4;
		}

		public int getClass5() {
			return class5;
		}

		public int getClass6() {
			return class6;
		}

		public int getClass7() {
			return class7;
		}

		public int getClass8() {
			return class8;
		}

		public int getClass9() {
			return class9;
		}

		public int getClass10() {
			return class10;
		}

		public int getClass11() {
			return class11;
		}

		public int getClass12() {
			return class12;
		}

		public int getClass13() {
			return class13;
		}	
		
		public int getClass14() {
			return class14;
		}
		
		public int getClass15() {
			return class15;
		}
		
		public int getClass16() {
			return class16;
		}
		
		public int getClass17() {
			return class17;
		}
		
		public int getClass18() {
			return class18;
		}
		
		//TYPE CLASS AND WEIGHING
		
		/* SPEED */

		public int getSpeed50km() {
			return speed50km;
		}

		public int getSpeed70km() {
			return speed70km;
		}

		public int getSpeed90km() {
			return speed90km;
		}

		public int getSpeed120km() {
			return speed120km;
		}

		public int getSpeed150km() {
			return speed150km;
		}

		public int getSpeed150km_bigger() {
			return speed150km_bigger;
		}

		/* SPEED */
		
		/* PERIOD AND MONTHLY FLOW */
		
		public int getSpeedValue1() {
			return speedValue1;
		}

		public int getSpeedValue2() {
			return speedValue2;
		}

		public int getTotal1() {
			return total1;
		}

		public int getTotal2() {
			return total2;
		}

		//Getters
							
	}
		
	private String month; 
	private String year;	
	public String[] vehicles;	
	public String[][] satCount;	
	private String equipment;
	private String period;
	private String startDate;
	private String endDate;
	private String[][] satsCountDates;
	private String[][] satsCountPeriods;	
	private String[] directions;
	public String[] axles;
	public String[] classes;
	private int[] equipNames;
	public String[] equipments;	
	private String sentido1;
	private String sentido2;
	private String type;
		
	//Results
	private String date;
	private String dateTime;
		
	public SatReports() {}
			
	public SatReports(String date, String dateTime) {
		this.date = date;
		this.dateTime = dateTime;  
	}
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String string) {
		this.month = string;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String[] getVehicles() {
		return vehicles;
	}

	public void setVehicles(String[] vehicles) {
		this.vehicles = vehicles;
	}

	public String[][] getSatCount() {
		return satCount;
	}

	public void setSatCount(String[][] satCount) {
		this.satCount = satCount;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String[][] getSatsCountDates() {
		return satsCountDates;
	}

	public void setSatsCountDates(String[][] satsCountDates) {
		this.satsCountDates = satsCountDates;
	}

	public String[][] getSatsCountPeriods() {
		return satsCountPeriods;
	}

	public void setSatsCountPeriods(String[][] satsCountPeriods) {
		this.satsCountPeriods = satsCountPeriods;
	}

	public String[] getDirections() {
		return directions;
	}

	public void setDirections(String[] directions) {
		this.directions = directions;
	}

	public String[] getAxles() {
		return axles;
	}

	public void setAxles(String[] axles) {
		this.axles = axles;
	}

	public String[] getClasses() {
		return classes;
	}

	public void setClasses(String[] classes) {
		this.classes = classes;
	}

	public int[] getEquipNames() {
		return equipNames;
	}

	public void setEquipNames(int[] equipNames) {
		this.equipNames = equipNames;
	}

	public String[] getEquipments() {
		return equipments;
	}

	public void setEquipments(String[] equipments) {
		this.equipments = equipments;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
		
	public String getSentido1() {
		return sentido1;
	}

	public void setSentido1(String sentido1) {
		this.sentido1 = sentido1;
	}

	public String getSentido2() {
		return sentido2;
	}

	public void setSentido2(String sentido2) {
		this.sentido2 = sentido2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	
}
