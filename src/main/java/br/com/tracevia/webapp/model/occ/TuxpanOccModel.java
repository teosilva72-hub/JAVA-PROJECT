package br.com.tracevia.webapp.model.occ;

import java.util.ArrayList;
import java.util.List;

public class TuxpanOccModel{
	
	private String id;
	private String plz_cobro;
	private String folio_sec;
	private String reporte;
	private String siniestro;
	private String fecha;
	private String hora;
	private String direccion;
	private String km_reg;
	private String km_inicial;
	private String km_final;
	private String poliza;
	private String fecha_cab;
	private String hora_ajust;
	private String tipo_veh_inv;
	private String num_eje_veh_inv;
	private String num_tp_veh;
	private String marca_tp_veh;
	private String tipo_tp_veh;
	private String model_tp_veh;
	private String color;
	private String placa_estado;
	private String tel;
	private String id_person;
	private String nombre;
	private String edad;
	private String condiciones;
	private String semoviente;
	private String trab_conserv;
	private String lluvia_granizo;
	private String neblina;
	private String vandalismo;
	private String otro;
	private String obs_occ;
	private String type_report;
	//siniestro
	private String ocupantes_sin;
	private String veh_sin;
	private String causas_sin;
	private String def_metal;
	private String senal;
	private String dano_pav;
	private String danos_cort_trr;
	private String danos_obr_compl;
	private String dano_plz_cobro;
	private String otros_sin;
	private String obs_sin;
	private String lesionados;
	private String mortos;
	
	public TuxpanOccModel(String id, String plz_cobro, String folio_sec, String reporte, String siniestro,
			String fecha, String hora, String direccion, String km_reg, String km_inicial, String km_final,
			String poliza, String fecha_cab, String hora_ajust, String tipo_veh_inv, String num_eje_veh_inv,
			String num_tp_veh, String marca_tp_veh, String tipo_tp_veh, String model_tp_veh, String color,
			String placa_estado, String tel, String id_person, String nombre, String edad, String condiciones,
			String semoviente, String trab_conserv, String lluvia_granizo, String neblina, String vandalismo,
			String otro, String obs_occ, String ocupantes_sin, String veh_sin, String causas_sin, String def_metal,
			String senal, String dano_pav, String danos_cort_trr, String danos_obr_compl, String dano_plz_cobro,
			String otros_sin, String[] obs_sin, String lesionados, String mortos, String type_report) {
		
		this.type_report = type_report;
		this.id = id;
		this.plz_cobro = plz_cobro;
		this.folio_sec = folio_sec;
		this.reporte = reporte;
		this.siniestro = siniestro;
		this.fecha = fecha;
		this.hora = hora;
		this.direccion = direccion;
		this.km_reg = km_reg;
		this.km_inicial = km_inicial;
		this.poliza = poliza;
		this.fecha_cab = fecha_cab;
		this.hora_ajust = hora_ajust;
		this.tipo_veh_inv = tipo_veh_inv;
		this.num_eje_veh_inv = num_eje_veh_inv;
		this.num_tp_veh = num_tp_veh;
		this.marca_tp_veh = marca_tp_veh;
		this.tipo_tp_veh = tipo_tp_veh;
		this.model_tp_veh = model_tp_veh;
		this.color = color;
		this.placa_estado = placa_estado;
		this.tel = tel;
		this.id_person = id_person;
		this.nombre = nombre;
		this.edad = edad;
		this.condiciones = condiciones;
		this.semoviente = semoviente;
		this.trab_conserv = trab_conserv;
		this.lluvia_granizo = lluvia_granizo;
		this.neblina = neblina;
		this.vandalismo = vandalismo;
		this.otro = otro;
		this.obs_occ = obs_occ;
		this.ocupantes_sin = ocupantes_sin;
		this.veh_sin = veh_sin;
		this.causas_sin = causas_sin;
		this.def_metal = def_metal;
		this.senal = senal;
		this.dano_pav = dano_pav;
		this.danos_cort_trr = danos_cort_trr;
		this.danos_obr_compl = danos_obr_compl;
		this.dano_plz_cobro = dano_plz_cobro;
		this.otros_sin = otros_sin;
		this.lesionados = lesionados;
		this.mortos = mortos;
		
	}
	public TuxpanOccModel(){}
	public String getId() {
		return id;
	}
	
	public String getType_report() {
		return type_report;
	}
	public void setType_report(String type_report) {
		this.type_report = type_report;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlz_cobro() {
		return plz_cobro;
	}
	public void setPlz_cobro(String plz_cobro) {
		this.plz_cobro = plz_cobro;
	}
	public String getFolio_sec() {
		return folio_sec;
	}
	public void setFolio_sec(String folio_sec) {
		this.folio_sec = folio_sec;
	}
	public String getReporte() {
		return reporte;
	}
	public void setReporte(String reporte) {
		this.reporte = reporte;
	}
	public String getSiniestro() {
		return siniestro;
	}
	public void setSiniestro(String siniestro) {
		this.siniestro = siniestro;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getKm_reg() {
		return km_reg;
	}
	public void setKm_reg(String km_reg) {
		this.km_reg = km_reg;
	}
	public String getKm_inicial() {
		return km_inicial;
	}
	public void setKm_inicial(String km_inicial) {
		this.km_inicial = km_inicial;
	}
	public String getKm_final() {
		return km_final;
	}
	public void setKm_final(String km_final) {
		this.km_final = km_final;
	}
	public String getPoliza() {
		return poliza;
	}
	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}
	public String getFecha_cab() {
		return fecha_cab;
	}
	public void setFecha_cab(String fecha_cab) {
		this.fecha_cab = fecha_cab;
	}
	public String getHora_ajust() {
		return hora_ajust;
	}
	public void setHora_ajust(String hora_ajust) {
		this.hora_ajust = hora_ajust;
	}
	public String getTipo_veh_inv() {
		return tipo_veh_inv;
	}
	public void setTipo_veh_inv(String tipo_veh_inv) {
		this.tipo_veh_inv = tipo_veh_inv;
	}
	public String getNum_eje_veh_inv() {
		return num_eje_veh_inv;
	}
	public void setNum_eje_veh_inv(String num_eje_veh_inv) {
		this.num_eje_veh_inv = num_eje_veh_inv;
	}
	public String getNum_tp_veh() {
		return num_tp_veh;
	}
	public void setNum_tp_veh(String num_tp_veh) {
		this.num_tp_veh = num_tp_veh;
	}
	public String getMarca_tp_veh() {
		return marca_tp_veh;
	}
	public void setMarca_tp_veh(String marca_tp_veh) {
		this.marca_tp_veh = marca_tp_veh;
	}
	public String getTipo_tp_veh() {
		return tipo_tp_veh;
	}
	public void setTipo_tp_veh(String tipo_tp_veh) {
		this.tipo_tp_veh = tipo_tp_veh;
	}
	public String getModel_tp_veh() {
		return model_tp_veh;
	}
	public void setModel_tp_veh(String model_tp_veh) {
		this.model_tp_veh = model_tp_veh;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getPlaca_estado() {
		return placa_estado;
	}
	public void setPlaca_estado(String placa_estado) {
		this.placa_estado = placa_estado;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getId_person() {
		return id_person;
	}
	public void setId_person(String id_person) {
		this.id_person = id_person;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEdad() {
		return edad;
	}
	public void setEdad(String edad) {
		this.edad = edad;
	}
	public String getCondiciones() {
		return condiciones;
	}
	public void setCondiciones(String condiciones) {
		this.condiciones = condiciones;
	}
	public String getSemoviente() {
		return semoviente;
	}
	public void setSemoviente(String semoviente) {
		this.semoviente = semoviente;
	}
	public String getTrab_conserv() {
		return trab_conserv;
	}
	public void setTrab_conserv(String trab_conserv) {
		this.trab_conserv = trab_conserv;
	}
	public String getLluvia_granizo() {
		return lluvia_granizo;
	}
	public void setLluvia_granizo(String lluvia_granizo) {
		this.lluvia_granizo = lluvia_granizo;
	}
	public String getNeblina() {
		return neblina;
	}
	public void setNeblina(String neblina) {
		this.neblina = neblina;
	}
	public String getVandalismo() {
		return vandalismo;
	}
	public void setVandalismo(String vandalismo) {
		this.vandalismo = vandalismo;
	}
	public String getOtro() {
		return otro;
	}
	public void setOtro(String otro) {
		this.otro = otro;
	}
	public String getObs_occ() {
		return obs_occ;
	}
	public void setObs_occ(String obs_occ) {
		this.obs_occ = obs_occ;
	}
	public String getOcupantes_sin() {
		return ocupantes_sin;
	}
	public void setOcupantes_sin(String ocupantes_sin) {
		this.ocupantes_sin = ocupantes_sin;
	}
	public String getVeh_sin() {
		return veh_sin;
	}
	public void setVeh_sin(String veh_sin) {
		this.veh_sin = veh_sin;
	}
	public String getCausas_sin() {
		return causas_sin;
	}
	public void setCausas_sin(String causas_sin) {
		this.causas_sin = causas_sin;
	}
	public String getDef_metal() {
		return def_metal;
	}
	public void setDef_metal(String def_metal) {
		this.def_metal = def_metal;
	}
	public String getSenal() {
		return senal;
	}
	public void setSenal(String senal) {
		this.senal = senal;
	}
	public String getDano_pav() {
		return dano_pav;
	}
	public void setDano_pav(String dano_pav) {
		this.dano_pav = dano_pav;
	}
	public String getDanos_cort_trr() {
		return danos_cort_trr;
	}
	public void setDanos_cort_trr(String danos_cort_trr) {
		this.danos_cort_trr = danos_cort_trr;
	}
	public String getDanos_obr_compl() {
		return danos_obr_compl;
	}
	public void setDanos_obr_compl(String danos_obr_compl) {
		this.danos_obr_compl = danos_obr_compl;
	}
	public String getDano_plz_cobro() {
		return dano_plz_cobro;
	}
	public void setDano_plz_cobro(String dano_plz_cobro) {
		this.dano_plz_cobro = dano_plz_cobro;
	}
	public String getOtros_sin() {
		return otros_sin;
	}
	public void setOtros_sin(String otros_sin) {
		this.otros_sin = otros_sin;
	}
	public String getObs_sin() {
		return obs_sin;
	}
	public void setObs_sin(String obs_sin) {
		this.obs_sin = obs_sin;
	}
	public String getLesionados() {
		return lesionados;
	}
	public void setLesionados(String lesionados) {
		this.lesionados = lesionados;
	}
	public String getMortos() {
		return mortos;
	}
	public void setMortos(String mortos) {
		this.mortos = mortos;
	}
	

		
}