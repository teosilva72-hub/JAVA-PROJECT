package br.com.tracevia.webapp.controller.dms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import com.google.gson.Gson;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.converter.PictureConverter;

import br.com.tracevia.webapp.dao.dms.DMSDAO;
import br.com.tracevia.webapp.dao.dms.MessagesDAO;
import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.dms.Messages;
import br.com.tracevia.webapp.model.dms.Picture;
import br.com.tracevia.webapp.util.LocaleUtil;

@ManagedBean(name = "dmsMessage")
@ViewScoped
public class DMSMessagesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private DMS dms;
	private String image;
	private String boardImage;
	private String[] messageUpdate;
	private String[] messageCreation;
	private String message1;
	private String message2;
	private String message3;
	private String messageActive;
	private char[] chars1;
	private char[] chars2;
	private char[] chars3;
	private int selectedEquip;
	private int rowKey;
	private int idPicture;

	private String tipo;
	private String nome;
	private String idMessage;
	private int selectedPicture;

	int i, j, k, lmt1, lmt2, lmt3;

	DMSDAO dao;
	EquipmentsDAO equipDAO;

	LocaleUtil localeMessage, localeLabel;

	private Picture picture;
	private List<Picture> pictures;
	private Messages message;
	// private HtmlDataTable dataTable;
	private List<Messages> messages;
	private List<Messages> messages_d1;
	private List<Messages> messages_d2;
	private List<Messages> messages_d3;
	private List<Enumeration> messages_enumeration_d1;
	private List<Enumeration> messages_enumeration_d2;
	private List<Enumeration> messages_enumeration_d3;
	private ArrayList<SelectItem> items;
	private ArrayList<SelectItem> name;
	private ArrayList<SelectItem> type;
	ArrayList<Equipments> list;

	private boolean submit;
	private boolean create;
	private boolean editable;
	private boolean select;
	private boolean inputs;
	private boolean delete;
	private boolean selectedRow;

	public int messageID;
	String actionType;

	int[] equipID;

	public DMS getDms() {
		return dms;
	}

	public void setDms(DMS dms) {
		this.dms = dms;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getBoardImage() {
		return boardImage;
	}

	public void setBoardImage(String boardImage) {
		this.boardImage = boardImage;
	}

	public String[] getMessageUpdate() {
		return messageUpdate;
	}

	public void setMessageUpdate(String[] messageUpdate) {
		this.messageUpdate = messageUpdate;
	}

	public String getMessageActive() {
		return messageActive;
	}

	public void setMessageActive(String messageActive) {
		this.messageActive = messageActive;
	}

	public char[] getChars1() {
		return chars1;
	}

	public void setChars1(char[] chars1) {
		this.chars1 = chars1;
	}

	public char[] getChars2() {
		return chars2;
	}

	public void setChars2(char[] chars2) {
		this.chars2 = chars2;
	}

	public char[] getChars3() {
		return chars3;
	}

	public void setChars3(char[] chars3) {
		this.chars3 = chars3;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public List<Messages> getMessages_d1() {
		return messages_d1;
	}

	public void setMessages_d1(List<Messages> messages_d1) {
		this.messages_d1 = messages_d1;
	}

	public List<Messages> getMessages_d2() {
		return messages_d2;
	}

	public void setMessages_d2(List<Messages> messages_d2) {
		this.messages_d2 = messages_d2;
	}

	public List<Messages> getMessages_d3() {
		return messages_d3;
	}

	public void setMessages_d3(List<Messages> messages_d3) {
		this.messages_d3 = messages_d3;
	}

	public List<Enumeration> getMessages_enumeration_d1() {
		return messages_enumeration_d1;
	}

	public void setMessages_enumeration_d1(List<Enumeration> messages_enumeration_d1) {
		this.messages_enumeration_d1 = messages_enumeration_d1;
	}

	public List<Enumeration> getMessages_enumeration_d2() {
		return messages_enumeration_d2;
	}

	public void setMessages_enumeration_d2(List<Enumeration> messages_enumeration_d2) {
		this.messages_enumeration_d2 = messages_enumeration_d2;
	}

	public List<Enumeration> getMessages_enumeration_d3() {
		return messages_enumeration_d3;
	}

	public void setMessages_enumeration_d3(List<Enumeration> messages_enumeration_d3) {
		this.messages_enumeration_d3 = messages_enumeration_d3;
	}

	public Messages getMessage() {
		return message;
	}

	public void setMessage(Messages message) {
		this.message = message;
	}

	public List<Messages> getMessages() {
		return messages;
	}

	public void setMessages(List<Messages> messages) {
		this.messages = messages;
	}

	public boolean isSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public boolean isInputs() {
		return inputs;
	}

	public void setInputs(boolean inputs) {
		this.inputs = inputs;
	}

	public List<SelectItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<SelectItem> items) {
		this.items = items;
	}

	public int getSelectedEquip() {
		return selectedEquip;
	}

	public void setSelectedEquip(int selectedEquip) {
		this.selectedEquip = selectedEquip;
	}

	public ArrayList<Equipments> getList() {
		return list;
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public String[] getMessageCreation() {
		return messageCreation;
	}

	public void setMessageCreation(String[] messageCreation) {
		this.messageCreation = messageCreation;
	}

	public String getMessage1() {
		return message1;
	}

	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public String getMessage3() {
		return message3;
	}

	public void setMessage3(String message3) {
		this.message3 = message3;
	}

	public ArrayList<SelectItem> getName() {
		return name;
	}

	public void setName(ArrayList<SelectItem> name) {
		this.name = name;
	}

	public ArrayList<SelectItem> getType() {
		return type;
	}

	public void setType(ArrayList<SelectItem> type) {
		this.type = type;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(String idMessage) {
		this.idMessage = idMessage;
	}

	public int getIdPicture() {
		return idPicture;
	}

	public void setIdPicture(int idPicture) {
		this.idPicture = idPicture;
	}

	public int getSelectedPicture() {
		return selectedPicture;
	}

	public void setSelectedPicture(int selectedPicture) {
		this.selectedPicture = selectedPicture;
	}

	public int getRowKey() {
		return rowKey;
	}

	public void setRowKey(int rowKey) {
		this.rowKey = rowKey;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(boolean selectedRow) {
		this.selectedRow = selectedRow;
	}

	@PostConstruct
	public void init() {

		localeMessage = new LocaleUtil();
		localeLabel = new LocaleUtil();

		localeMessage.getResourceBundle(LocaleUtil.MESSAGES_PMV);
		localeLabel.getResourceBundle(LocaleUtil.LABELS_PMV);

		picture = new Picture();
		message = new Messages();
		pictures = PictureConverter.pictureDB;
		image = "000_6464.bmp";
		messageCreation = new String[3];
		messageUpdate = new String[3];
		chars1 = new char[12];
		chars2 = new char[12];
		chars3 = new char[12];
		create = false; // enabled
		select = false; // enabled
		editable = true; // disabled
		delete = true; // disabled
		submit = true; // disabled
		inputs = true; // disabled
		rowKey = 0;

		i = 0;
		j = 0;
		k = 0;
		lmt3 = 0;

		// Inicializar mensagens

		try {

			MessagesDAO dao = new MessagesDAO();
			equipDAO = new EquipmentsDAO();
			Enumeration enumeration = new Enumeration();

			messages_d1 = dao.mensagensDisponiveis("driver1");
			messages_enumeration_d1 = enumeration.create(messages_d1);
			
			messages_d2 = dao.mensagensDisponiveis("driver2");
			messages_enumeration_d2 = enumeration.create(messages_d2);

			messages_d3 = dao.mensagensDisponiveis("driver3");
			messages_enumeration_d3 = enumeration.create(messages_d3);

			messages = new ArrayList<Messages>();
			messages.addAll(messages_d1);
			messages.addAll(messages_d2);
			messages.addAll(messages_d3);
			
			ArrayList<Equipments> list = new ArrayList<Equipments>();
			list = equipDAO.listDMSSites();

			items = new ArrayList<SelectItem>();

			for (Equipments e : list) {
				SelectItem s = new SelectItem();
				s.setValue(e.getEquip_id());
				s.setLabel(e.getNome());
				items.add(s);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		type = new ArrayList<SelectItem>();
		type.add(new SelectItem("Alert", localeLabel.getStringKey("dms_active_messages_select_label_alert")));
		type.add(new SelectItem("Information",
				localeLabel.getStringKey("dms_active_messages_select_label_informations")));
		type.add(new SelectItem("Signaling", localeLabel.getStringKey("dms_active_messages_select_label_signaling")));

	}

	/**
	 * Enumeration
	 */
	public class Enumeration {

		private Messages value;
		private int key;

		public Messages getValue() {
			return value;
		}

		public void setValue(Messages value) {
			this.value = value;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		private Enumeration() {
		}

		private Enumeration(Messages value, int key) {
			this.value = value;
			this.key = key;
		}

		public List<Enumeration> create(List<Messages> value) {
			List<Enumeration> list = new ArrayList<Enumeration>();
			for (int i = 0; i < value.size(); i++) {
				Enumeration enumeration = new Enumeration(value.get(i), i);
				list.add(enumeration);
			}
			return list;
		}
	}

	public void changeAvailableMessage() throws Exception {
		boolean success;
		Gson gson = new Gson();
		MessagesDAO dao = new MessagesDAO();
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();

		// Pegar Usu�rio na sess�o
		String user = (String) context.getExternalContext().getSessionMap().get("user");

		Map<String, String> params = context.getExternalContext().getRequestParameterMap();

		Map<String, String> msg = gson.fromJson(params.get("requestParam"), Map.class);

		List<Map<String, String>> ListaPages = new ArrayList<Map<String, String>>();

		for (int i = 1; i <= 5; i++) {
			Map<String, String> page = gson.fromJson(params.get("requestParamPAGE" + i), Map.class);
			if (!page.get("timer").equals("0") || i == 1)
				ListaPages.add(page);
		}

		if (msg.get("id").equals("0"))
			success = dao.createMessage(msg, user, ListaPages);
		else
			success = dao.editMessage(msg, user, ListaPages);

		if (success)
			request.execute("returnAlert('Save action success!');");
		else
			request.execute("returnAlert('Save action failed!');");
	}

	public void removeAvailableMessage() throws Exception {
		boolean success;
		MessagesDAO dao = new MessagesDAO();
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();

		// Pegar Usu�rio na sess�o
		String user = (String) context.getExternalContext().getSessionMap().get("user");

		Map<String, String> params = context.getExternalContext().getRequestParameterMap();

		int msgID = Integer.parseInt(params.get("deleteID"));

		success = dao.removeMessage(msgID, user);

		if (success)
			request.execute("returnAlert('Delete action success!');");
		else
			request.execute("returnAlert('Delete action failed!');");
	}

	/* Message Creation Available */

	public void loadPictureImage(AjaxBehaviorEvent event) throws AbortProcessingException {
		this.image = String.valueOf(picture.getImage());
	}

	// Translate from DB
	public String translateType(String inputType) {

		String type = "";

		if (inputType.equals("Alert"))
			type = localeLabel.getStringKey("dms_active_messages_select_label_alert");

		if (inputType.equals("Information"))
			type = localeLabel.getStringKey("dms_active_messages_select_label_informations");

		if (inputType.equals("Signaling"))
			type = localeLabel.getStringKey("dms_active_messages_select_label_signaling");

		return type;

	}

}
