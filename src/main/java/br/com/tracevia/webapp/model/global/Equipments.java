package br.com.tracevia.webapp.model.global;

import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.model.sos.SOS;

public class Equipments {

	private int equip_id;
	private String table_id;
	private String equip_type;
	private String equip_ip;
	private String creation_date;
	private String creation_username;
	private String update_date;
	private String update_username;
	private String nome;
	private String estrada;	
	private String cidade;	
	private String km;
	private int linearPosX;
	private int linearPosY;
	private int mapPosX;
	private int mapPosY;
	private double longitude;
	private double latitude;
	private int mapWidth;
	private int height;	
	private int linearWidth;
	private int dlgPosX;
	private int dlgPosY;
	private String direction;
	private int status;
	private int lastStatus;
	private boolean notificacao;
	private boolean visible;

	public Equipments(int equip_id, String table_id, String equip_type, String equip_ip, String creation_date,
			String creation_username, String update_date, String update_username, String nome, String estrada,
			String cidade, String km, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth,
			int height, int linearWidth, int dlgPosX, int dlgPosY, String direction, int status, int lastStatus, boolean notificacao,
			boolean visible) {
		
		this.equip_id = equip_id;
		this.table_id = table_id;
		this.equip_type = equip_type;
		this.equip_ip = equip_ip;
		this.creation_date = creation_date;
		this.creation_username = creation_username;
		this.update_date = update_date;
		this.update_username = update_username;
		this.nome = nome;
		this.estrada = estrada;
		this.cidade = cidade;
		this.km = km;
		this.linearPosX = linearPosX;
		this.linearPosY = linearPosY;
		this.mapPosX = mapPosX;
		this.mapPosY = mapPosY;
		this.mapWidth = mapWidth;
		this.height = height;
		this.linearWidth = linearWidth;
		this.dlgPosX = dlgPosX;
		this.dlgPosY = dlgPosY;
		this.direction = direction;
		this.status = status;
		this.lastStatus = lastStatus;
		this.notificacao = notificacao;
		this.visible = visible;
	}

	public Equipments() {}

	public int getEquip_id() {
		return equip_id;
	}


	public void setEquip_id(int equip_id) {
		this.equip_id = equip_id;
	}


	public String getTable_id() {
		return table_id;
	}


	public void setTable_id(String table_id) {
		this.table_id = table_id;
	}


	public String getEquip_type() {
		return equip_type;
	}


	public void setEquip_type(String equip_type) {
		this.equip_type = equip_type;
	}


	public String getEquip_ip() {
		return equip_ip;
	}


	public void setEquip_ip(String equip_ip) {
		this.equip_ip = equip_ip;
	}
	
	public String getCreation_date() {
		return creation_date;
	}


	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}


	public String getCreation_username() {
		return creation_username;
	}


	public void setCreation_username(String creation_username) {
		this.creation_username = creation_username;
	}


	public String getUpdate_date() {
		return update_date;
	}


	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}


	public String getUpdate_username() {
		return update_username;
	}


	public void setUpdate_username(String update_username) {
		this.update_username = update_username;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEstrada() {
		return estrada;
	}


	public void setEstrada(String estrada) {
		this.estrada = estrada;
	}


	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


	public String getKm() {
		return km;
	}


	public void setKm(String km) {
		this.km = km;
	}


	public int getLinearPosX() {
		return linearPosX;
	}


	public void setLinearPosX(int linearPosX) {
		this.linearPosX = linearPosX;
	}


	public int getLinearPosY() {
		return linearPosY;
	}


	public void setLinearPosY(int linearPosY) {
		this.linearPosY = linearPosY;
	}


	public int getMapPosX() {
		return mapPosX;
	}


	public void setMapPosX(int mapPosX) {
		this.mapPosX = mapPosX;
	}


	public int getMapPosY() {
		return mapPosY;
	}


	public void setMapPosY(int mapPosY) {
		this.mapPosY = mapPosY;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public int getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public int getMapWidth() {
		return mapWidth;
	}


	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getLinearWidth() {
		return linearWidth;
	}


	public void setLinearWidth(int linearWidth) {
		this.linearWidth = linearWidth;
	}


	public int getDlgPosX() {
		return dlgPosX;
	}


	public void setDlgPosX(int dlgPosX) {
		this.dlgPosX = dlgPosX;
	}


	public int getDlgPosY() {
		return dlgPosY;
	}


	public void setDlgPosY(int dlgPosY) {
		this.dlgPosY = dlgPosY;
	}
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getLastStatus() {
		return lastStatus;
	}


	public void setLastStatus(int lastStatus) {
		this.lastStatus = lastStatus;
	}


	public boolean isNotificacao() {
		return notificacao;
	}


	public void setNotificacao(boolean notificacao) {
		this.notificacao = notificacao;
	}


	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	//BUILD GENERIC EQUIPMENTS
	public List<Equipments> listEquipments(String modulo, int permission) throws Exception {

		List<Equipments> lista = new ArrayList<Equipments>();	
		EquipmentsDAO dao = new EquipmentsDAO();			
		lista.addAll(dao.buildEquipmentsInterface(modulo, permission));	

		return lista;
	}

	//BUILD GENERIC EQUIPMENTS
	public List<SOS> listSosEquipments(int permission) throws Exception {

		List<SOS> lista = new ArrayList<>();	
		EquipmentsDAO dao = new EquipmentsDAO();			
		lista.addAll(dao.buildSosEquipmentsInterface(permission));	

		return lista;
	}

	//BUILD SAT EQUIPMENTS
	public List<? extends Equipments> listSatEquipments(int permission) throws Exception {

		List<SAT> lista = new ArrayList<SAT>();	
		EquipmentsDAO dao = new EquipmentsDAO();			
		lista.addAll(dao.buildSatEquipmentsInterface(permission));						

		return lista;
	}
		
	//BUILD DMS EQUIPMENTS
    public List<? extends Equipments> listDMSEquipments(int permission) throws Exception {

		List<DMS> lista = new ArrayList<DMS>();	
		EquipmentsDAO dao = new EquipmentsDAO();			
		lista.addAll(dao.buildDMSEquipmentsInterface(permission));						

		return lista;
	}


}
	