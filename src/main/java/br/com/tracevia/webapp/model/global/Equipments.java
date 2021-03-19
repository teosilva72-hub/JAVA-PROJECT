package br.com.tracevia.webapp.model.global;

import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.model.sat.SAT;

public class Equipments {
	
	private int equip_id;
	private String table_id;
	private String creation_date;
	private String creation_username;
	private int number_lanes;
	private String nome;
	private String estrada;	
	private String cidade;	
	private String km;
	private String dir_lane1;
    private String dir_lane2;
	private String dir_lane3;
	private String dir_lane4;
	private String dir_lane5;
	private String dir_lane6;
	private String dir_lane7;
	private String dir_lane8;
	private String posicao;
	private int linearPosX;
	private int linearPosY;
	private int mapPosX;
	private int mapPosY;
	private int mapWidth;
	private int height;	
	private int linearWidth;
	private int dlgPosX;
	private int dlgPosY;
	private int status;
	private boolean notificacao;
	private boolean visible;
		
	public Equipments(int equip_id, String table_id, String creation_date, String creation_username, int number_lanes, String nome, String estrada, String cidade, String km,
	String dir_lane1, String dir_lane2, String dir_lane3, String dir_lane4, String dir_lane5, String dir_lane6, String dir_lane7, String dir_lane8, String posicao,
    int linearPosX, int linearPosY, int linearWidth, int mapPosX, int mapPosY, int mapWidth, int height, int dlgPosX, int dlgPosY,
	  int status, boolean notificacao, boolean visible) {
		
		this.equip_id = equip_id;
		this.table_id = table_id;
		this.creation_date = creation_date;
		this.creation_username = creation_username;
		this.number_lanes = number_lanes;
		this.nome = nome;
		this.estrada = estrada;
		this.cidade = cidade;
		this.km = km;
		this.dir_lane1 = dir_lane1;
		this.dir_lane1 = dir_lane2;
		this.dir_lane1 = dir_lane3;
		this.dir_lane1 = dir_lane4;
		this.dir_lane1 = dir_lane5;
		this.dir_lane1 = dir_lane6;
		this.dir_lane1 = dir_lane7;
		this.dir_lane1 = dir_lane8;
		this.posicao = posicao;
		this.linearPosX = linearPosX;
		this.linearPosY = linearPosY;
		this.linearWidth = linearWidth;
		this.mapPosX = mapPosX;
		this.mapPosY = mapPosY;
		this.mapWidth = mapWidth;		
		this.height = height;
		this.dlgPosX = dlgPosX;
		this.dlgPosY = dlgPosY;
		this.status = status;
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
		
		public int getNumber_lanes () {
			return number_lanes;
		}
		
		public void setNumber_lanes(int number_lanes) {
			this.number_lanes = number_lanes;
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
		
		public String getDir_lane1() {
			return dir_lane1;
		}

		public void setDir_lane1(String dir_lane1) {
			this.dir_lane1 = dir_lane1;
		}

		public String getDir_lane2() {
			return dir_lane2;
		}

		public void setDir_lane2(String dir_lane2) {
			this.dir_lane2 = dir_lane2;
		}

		public String getDir_lane3() {
			return dir_lane3;
		}

		public void setDir_lane3(String dir_lane3) {
			this.dir_lane3 = dir_lane3;
		}

		public String getDir_lane4() {
			return dir_lane4;
		}

		public void setDir_lane4(String dir_lane4) {
			this.dir_lane4 = dir_lane4;
		}

		public String getDir_lane5() {
			return dir_lane5;
		}

		public void setDir_lane5(String dir_lane5) {
			this.dir_lane5 = dir_lane5;
		}

		public String getDir_lane6() {
			return dir_lane6;
		}

		public void setDir_lane6(String dir_lane6) {
			this.dir_lane6 = dir_lane6;
		}

		public String getDir_lane7() {
			return dir_lane7;
		}

		public void setDir_lane7(String dir_lane7) {
			this.dir_lane7 = dir_lane7;
		}

		public String getDir_lane8() {
			return dir_lane8;
		}

		public void setDir_lane8(String dir_lane8) {
			this.dir_lane8 = dir_lane8;
		}

		public String getPosicao() {
			return posicao;
		}

		public void setPosicao(String posicao) {
			this.posicao = posicao;
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

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
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

		public int getLinearWidth() {
			return linearWidth;
		}

		public void setLinearWidth(int linearWidth) {
			this.linearWidth = linearWidth;
		}

		//Linear Generic equipments
		public List<Equipments> listEquipments(String modulo) throws Exception {
			
			List<Equipments> lista = new ArrayList<Equipments>();	
			EquipmentsDAO dao = new EquipmentsDAO();			
			lista.addAll(dao.buildEquipmentsInterface(modulo));	
			
			return lista;
		}
		
		//Linear SAT equipments
		public List<? extends Equipments> listSatEquipments() throws Exception {
			
			List<SAT> lista = new ArrayList<SAT>();	
			EquipmentsDAO dao = new EquipmentsDAO();			
			lista.addAll(dao.buildSatEquipmentsInterface());						
			
			return lista;
		}
			
		
}