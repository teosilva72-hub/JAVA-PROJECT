package br.com.tracevia.webapp.controller.dms;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import com.google.gson.Gson;

import br.com.tracevia.webapp.controller.global.LanguageBean;
import br.com.tracevia.webapp.dao.dms.DMSDAO;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.dms.Messages;
import br.com.tracevia.webapp.model.global.Estrada;
import br.com.tracevia.webapp.util.LocaleUtil;

@ManagedBean(name = "interfaces")
@ViewScoped
public class InterfacesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int amountDMS;
	List<DMS> dmsList;

	int[] equips, equipUpd, msgEquip;
	String[] image, image1, imageAux;
	char[][][] letter, letter1, letterAux;
	boolean[] state, state_prev;
	private String imagem;
	private String texto1;
	private String texto2;
	private String texto3;
	private Estrada str, str1;
	private String typeSelection;

	boolean checkbox, send;
	boolean checkAllBoxes;

	private List<Messages> messages;

	private Messages message;
	int messageID;

	LocaleUtil locale;

	String standard_image = "000_6464.bmp";

	@ManagedProperty(value = "#{language}")
	private LanguageBean lang;

	@ManagedProperty("#{msgs}")
	private ResourceBundle msgs;

	public int getAmountDMS() {
		return amountDMS;
	}

	public void setAmountDMS(int amountDMS) {
		this.amountDMS = amountDMS;
	}

	public List<DMS> getDmsList() {
		return dmsList;
	}

	public void setDmsList(List<DMS> dmsList) {
		this.dmsList = dmsList;
	}

	public int[] getEquips() {
		return equips;
	}

	public void setEquips(int[] equips) {
		this.equips = equips;
	}

	public String[] getImage() {
		return image;
	}

	public void setImage(String[] image) {
		this.image = image;
	}

	public String[] getImage1() {
		return image1;
	}

	public void setImage1(String[] image1) {
		this.image1 = image1;
	}

	public char[][][] getLetter() {
		return letter;
	}

	public void setLetter(char[][][] letter) {
		this.letter = letter;
	}

	public char[][][] getLetter1() {
		return letter1;
	}

	public void setLetter1(char[][][] letter1) {
		this.letter1 = letter1;
	}

	public LanguageBean getLang() {
		return lang;
	}

	public void setLang(LanguageBean lang) {
		this.lang = lang;
	}

	public ResourceBundle getMsgs() {
		return msgs;
	}

	public void setMsgs(ResourceBundle msgs) {
		this.msgs = msgs;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getTexto1() {
		return texto1;
	}

	public void setTexto1(String texto1) {
		this.texto1 = texto1;
	}

	public String getTexto2() {
		return texto2;
	}

	public void setTexto2(String texto2) {
		this.texto2 = texto2;
	}

	public String getTexto3() {
		return texto3;
	}

	public void setTexto3(String texto3) {
		this.texto3 = texto3;
	}

	public List<Messages> getMessages() {
		return messages;
	}

	public void setMessages(List<Messages> messages) {
		this.messages = messages;
	}

	public Messages getMessage() {
		return message;
	}

	public void setMessage(Messages message) {
		this.message = message;
	}

	public boolean[] getState() {
		return state;
	}

	public void setState(boolean[] state) {
		this.state = state;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

	public boolean isCheckAllBoxes() {
		return checkAllBoxes;
	}

	public void setCheckAllBoxes(boolean checkAllBoxes) {
		this.checkAllBoxes = checkAllBoxes;
	}

	public Estrada getStr() {
		return str;
	}

	public void setStr(Estrada str) {
		this.str = str;
	}

	public Estrada getStr1() {
		return str1;
	}

	public void setStr1(Estrada str1) {
		this.str1 = str1;
	}

	public String getTypeSelection() {
		return typeSelection;
	}

	public void setTypeSelection(String typeSelection) {
		this.typeSelection = typeSelection;
	}

	@PostConstruct
	public void initialize() {

		try {			
			imagem = "000_6464.bmp";
			
			checkbox = true;
			send = true;
			
			locale = new LocaleUtil();
			locale.getResourceBundle(LocaleUtil.MESSAGES_PMV);
			
			DMSDAO dmsDAO = new DMSDAO();
			
			dmsList = dmsDAO.idsDMS();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activateMessage() throws Exception {

		FacesContext context = FacesContext.getCurrentInstance();
		DMSDAO dmsDAO = new DMSDAO();
		Gson gson = new Gson();

		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		Map<String, String> dms = gson.fromJson(params.get("dmsChanges"), Map.class);

		for (String idDMS : dms.keySet()) {
			int idMSG = Integer.parseInt(dms.get(idDMS));

			dmsDAO.changeActivateMessage(Integer.parseInt(idDMS), idMSG);
		}
	}
}