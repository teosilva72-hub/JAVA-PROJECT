package br.com.tracevia.webapp.controller.hit;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import br.com.tracevia.webapp.controller.global.EquipmentsBean;
import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.occ.TuxpanDAO;
import br.com.tracevia.webapp.model.global.EquipmentDataSource;
import br.com.tracevia.webapp.model.hit.HIT;
import br.com.tracevia.webapp.model.occ.TuxpanOccModel;

@ManagedBean(name="HitController")
public class ControllerHIT {
	private HIT hit;
	private EquipmentsDAO equipDAO;
	private EquipmentDataSource listar;
	private String msgOne, msgTwo, msgThree;
	private int equipId;
		@PostConstruct
		public void init() {
			hit = new HIT();
			equipDAO = new EquipmentsDAO();
		}
		public boolean listHit(int equipId, String equipTable, String interfaceView, int id) {
			boolean x = false;

			EquipmentsBean bean = new EquipmentsBean();
			
			return x;
		}
		
		
		public EquipmentDataSource getListar() {
			return listar;
		}
		
		public int getEquipId() {
			return equipId;
		}
		public void setEquipId(int equipId) {
			this.equipId = equipId;
		}
		public void setListar(EquipmentDataSource listar) {
			this.listar = listar;
		}
		public String getMsgOne() {
			return msgOne;
		}
		public void setMsgOne(String msgOne) {
			this.msgOne = msgOne;
		}
		public String getMsgTwo() {
			return msgTwo;
		}
		public void setMsgTwo(String msgTwo) {
			this.msgTwo = msgTwo;
		}
		public String getMsgThree() {
			return msgThree;
		}
		public void setMsgThree(String msgThree) {
			this.msgThree = msgThree;
		}
		
}
