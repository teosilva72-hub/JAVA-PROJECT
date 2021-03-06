package br.com.tracevia.webapp.controller.dms;

import java.io.Serializable;
import java.lang.reflect.Type;
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
import com.google.gson.reflect.TypeToken;

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
import br.com.tracevia.webapp.util.MessagesUtil;

@ManagedBean(name = "pmvMessage")
@ViewScoped
public class PMVmessageBean implements Serializable {

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
	private List<Messages> messagesOnly;
	private List<Enumeration> messages_enumeration;
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

	public List<Messages> getMessages() {
		return messages;
	}

	public void setMessages(List<Messages> messages) {
		this.messages = messages;
	}

	public List<Enumeration> getMessages_enumeration() {
		return messages_enumeration;
	}

	public void setMessages_enumeration(List<Enumeration> messages_enumeration) {
		this.messages_enumeration = messages_enumeration;
	}

	public Messages getMessage() {
		return message;
	}

	public void setMessage(Messages message) {
		this.message = message;
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

			messages = dao.mensagensDisponiveis();
			messages_enumeration = enumeration.create(messages);

			messagesOnly = dao.mensagensOnly();

			ArrayList<Equipments> list = new ArrayList<Equipments>();
			list = equipDAO.listPMVSites();

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
		MessagesDAO dao = new MessagesDAO();
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();

		// Pegar Usu�rio na sess�o
		String user = (String) context.getExternalContext().getSessionMap().get("user");

		Map<String, String> params = context.getExternalContext().getRequestParameterMap();

		int msgID = Integer.parseInt(params.get("requestParamID"));

		List<Map<String, String>> ListaPages = new ArrayList<Map<String, String>>();

		Gson gson = new Gson();
		for (int i = 1; i <= 5; i++) {
			Map<String, String> page = gson.fromJson(params.get("requestParamPAGE" + i), Map.class);
			if (!page.get("timer").equals("0") || i == 1)
				ListaPages.add(page);
		}

		if (msgID == 0)
			success = dao.createMessage(msgID, user, ListaPages);
		else
			success = dao.editMessage(msgID, user, ListaPages);

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

	public void updateMessages(AjaxBehaviorEvent event) throws AbortProcessingException {

		try {

			MessagesDAO dao = new MessagesDAO();

			messages = dao.mensagensDisponiveis();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createAvailableMessage() throws Exception { // TODO: Nao esta sendo usado na pagina mensagem available,
															// possivelmente possa ser removido esse metodo

		boolean result;
		MessagesDAO dao = new MessagesDAO();
		FacesContext context = FacesContext.getCurrentInstance();

		MessagesUtil message = new MessagesUtil();

		for (int i = 0; i < message1.length(); i++) {
			if (message1.charAt(i) != Character.MIN_VALUE)
				chars1[i] = message1.charAt(i);
		}

		for (int i = 0; i < message2.length(); i++) {
			if (message2.charAt(i) != Character.MIN_VALUE)
				chars2[i] = message2.charAt(i);
		}

		for (int i = 0; i < message3.length(); i++) {
			if (message3.charAt(i) != Character.MIN_VALUE)
				chars3[i] = message3.charAt(i);
		}

		// Sem selecionar imagem
		if (picture == null)
			picture = new Picture(0);

		// Pegar Usu�rio na sess�o
		String user = (String) context.getExternalContext().getSessionMap().get("user");

		result = dao.insertMessage(picture.getId(), user, tipo, nome, message1, message2, message3);

		if (result) {
			message.InfoMessage(localeMessage.getStringKey("dms_available_message_register"), " ");
			messages = dao.mensagensDisponiveis(); // Atualiza lista de mensagens
			cleanSelection();
		}

		else
			message.ErrorMessage(localeMessage.getStringKey("dms_available_message_register_error"), " ");
	}

	public void updateAvailableMessage() throws Exception {

		boolean result;
		MessagesDAO dao = new MessagesDAO();
		FacesContext context = FacesContext.getCurrentInstance();

		MessagesUtil message = new MessagesUtil();

		if (picture == null)
			picture = new Picture(idPicture); // Caso n�o seja selecionado nenhum novo id

		// Pegar Usu�rio na sess�o
		String user = (String) context.getExternalContext().getSessionMap().get("user");

		result = dao.updateMessage(picture.getId(), user, tipo, nome, message1, message2, message3, idMessage);

		if (result) {
			message.InfoMessage(localeMessage.getStringKey("dms_available_message_update"), " ");
			messages = dao.mensagensDisponiveis(); // Atualiza lista de mensagens
		}

		else
			message.InfoMessage(localeMessage.getStringKey("dms_available_message_update_error"), " ");

		cleanSelection();
	}

	// Deletar Registro
	public void deleteRegister() throws Exception {

		boolean response;
		int active;

		MessagesDAO dao = new MessagesDAO();

		MessagesUtil message = new MessagesUtil();

		if (rowKey != 0) {

			// Verifica se a mensagem est� ativa
			active = dao.verifyRegisterIsActive(rowKey);

			if (active == 0) { // Caso n�o esteja ativa segue para exclus�o
				response = dao.removeRegister(rowKey);

				if (response) {
					messages = dao.mensagensDisponiveis(); // Atualiza lista de mensagens
					message.InfoMessage(localeMessage.getStringKey("dms_available_message_delete_sucess"), " ");
				}

				else
					message.InfoMessage(localeMessage.getStringKey("dms_available_message_delete_error"), " ");

			} else
				message.ErrorMessage(localeMessage.getStringKey("dms_available_message_delete_error_in_use_header"),
						localeMessage.getStringKey("dms_available_message_delete_error_in_use_body"));

		} else
			message.WarningMessage(localeMessage.getStringKey("dms_activation_message_activation_not_found"), " ");

	}

	public void messageActions() throws Exception {

		// cleanSelection(); // Limpar antes

		if (actionType.equals("edit"))
			updateAvailableMessage();

		else
			createAvailableMessage();
	}

	/*
	 * public void completarArray(int ini, int tam, char[] array) {
	 * 
	 * for(int c = ini; c < tam; c++) array[c] = ' '; }
	 */

	/* Mensagens Disponiveis Box */

	public void messageRowOne() {

		int tam = message1.length();

		if (tam > i && tam < 13) {

			chars1[i] = message1.charAt(i);
			i++;
		}

		else if (tam < i && tam > (-1)) {
			chars1[i - 1] = Character.MIN_VALUE;
			i--;
		}
	}

	public void messageRowTwo() {

		int tam = message2.length();

		if (tam > j && tam < 13) {

			chars2[j] = message2.charAt(j);
			j++;
		}

		else if (tam < j && tam > (-1)) {
			chars2[j - 1] = Character.MIN_VALUE;
			j--;
		}

	}

	public void messageRowThree() {

		int tam = message3.length();

		if (tam > k && tam < 13) {

			chars3[k] = message3.charAt(k);
			k++;
		}

		else if (tam < k && tam > (-1)) {
			chars3[k - 1] = Character.MIN_VALUE;
			k--;
		}
	}

	/* Mensagens Disponiveis Box */

	public void editMessage() throws Exception {

		StringBuilder sb = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();

		MessagesDAO dao = new MessagesDAO();
		Messages message = new Messages();
		MessagesUtil messageUtil = new MessagesUtil();

		if (rowKey != 0) {

			create = true;
			editable = true;
			delete = true;
			select = true;
			submit = false;
			inputs = false;

			actionType = "edit";

			RequestContext.getCurrentInstance().execute("imageEnabled();"); // Executar Javascript

			message = dao.mensagensDisponivelById(rowKey);

			this.image = String.valueOf(message.getImage());

			picture = new Picture(message.getId_image());
			idPicture = picture.getId();

			// Image
			this.tipo = message.getTipo();
			this.nome = message.getNome();
			this.idMessage = String.valueOf(message.getId_message()); // TODO: corrigir

			for (int i = 0; i < message.getPages().get(0).getText1().length(); i++) {

				if (message.getPages().get(0).getText1().charAt(i) != Character.MIN_VALUE) {
					chars1[i] = message.getPages().get(0).getText1().charAt(i);
					lmt1++;

				}
			}

			for (int i = 0; i < message.getPages().get(0).getText2().length(); i++) {
				if (message.getPages().get(0).getText2().charAt(i) != Character.MIN_VALUE) {
					chars2[i] = message.getPages().get(0).getText2().charAt(i);
					lmt2++;

				}
			}

			for (int i = 0; i < message.getPages().get(0).getText3().length(); i++) {
				if (message.getPages().get(0).getText3().charAt(i) != Character.MIN_VALUE) {
					chars3[i] = message.getPages().get(0).getText3().charAt(i);
					lmt3++;

				}
			}

			message1 = sb.append(chars1).toString();
			message2 = sb1.append(chars2).toString();
			message3 = sb2.append(chars3).toString();

			i = lmt1; // ponteiro primeira linha
			j = lmt2; // ponteiro segunda linha
			k = lmt3; // ponteiro terceira linha

			// System.out.println("I: "+i);
			// System.out.println("J: "+j);
			// System.out.println("K: "+k);

		} else {

			messageUtil.WarningMessage(localeMessage.getStringKey("dms_activation_message_activation_not_found"), " ");

			RequestContext.getCurrentInstance().execute("PF('bui').hide()"); // Executar Javascript
		}
	}

	public void createMessage() { // Todo: Provalvemente ele já esta obsoleto

		actionType = "create";
		create = true; // Unlock items
		editable = true;
		delete = true;
		select = true;
		submit = false;
		inputs = false;

		RequestContext.getCurrentInstance().execute("imageEnabled();"); // Executar Javascript

	}

	public int initCharsFromEdit() {

		int value = 1;

		// while(selectedMessage.getTexto().length() < 12)
		value++;

		return value;
	}

	public void cleanSelection() {

		image = "000_6464.bmp";
		chars1 = new char[12];
		chars2 = new char[12];
		chars3 = new char[12];
		idMessage = null;
		tipo = null;
		nome = null;
		picture = new Picture();
		picture.setId(0);
		create = false; // enabled
		select = false; // enabled
		editable = true; // disabled
		delete = true; // disabled
		submit = true; // disabled
		inputs = true; // disabled
		message1 = null;
		message2 = null;
		message3 = null;
		i = 0;
		j = 0;
		k = 0;
		lmt1 = 0;
		lmt2 = 0;
		lmt3 = 0;
	}

	/* Message Creation Available */

	/* Messages Activate */

	public int initMessage1Edit(String message1) {
		return message1.length();
	}

	public int initMessage2Edit(String message2) {
		return message2.length();
	}

	public int initMessage3Edit(String message3) {
		return message3.length();
	}

	// EXAMINE
	public List<Messages> getActivesMessages() throws Exception {

		MessagesDAO dao = new MessagesDAO();

		List<Messages> lista = new ArrayList<Messages>();
		List<Integer> lista1 = new ArrayList<Integer>();

		lista1 = equipDAO.listPMVSitesById();
		int[] equip = new int[lista1.size()];

		int n = 0;

		for (Integer i : lista1) {
			equip[n] = i;
			n++;
		}

		lista = dao.selectActivesMessages();

		// image = msg.getImagem();
		// messageActive = msg.getTexto();

		// int value = initCharsFromEdit(); //retornar o tamanho de caracteres que
		// contem em characters[?]
		// j = (value - 1);

		// activeCharacters(); // preencher Caracteres no equipamento
		// modify = false;

		return lista;

	}

	public void selectMessageId() {

		MessagesUtil messageUtil = new MessagesUtil();

		if (message != null) {
			messageID = message.getId_message();
			messageUtil.InfoMessage(localeMessage.getStringKey("dms_activation_message_activation_selected"), " ");
			editable = false;
		}
	}

	/* Atualizar mensagem */
	public void updateActiveMessage(int equip) throws Exception {

		boolean state = false;

		MessagesDAO dao = new MessagesDAO();
		MessagesUtil messageUtil = new MessagesUtil();
		// Messages mes = new Messages();

		selectedEquip = equip;

		if (messageID != 0) {

			// mes = dao.mensagensDisponivelById(messageID);

			state = dao.updateModifyMessage(messageID, selectedEquip);

			if (state)
				messageUtil.InfoMessage(localeMessage.getStringKey("dms_activation_message_send_activation"), " ");

			else
				messageUtil.ErrorMessage(localeMessage.getStringKey("dms_activation_message_send_activation_error"),
						" ");

		} else
			messageUtil.WarningMessage(localeMessage.getStringKey("dms_activation_message_activation_not_found"), " ");

	}

	public void resetActiveForm() {

		image = "000_6464.bmp";
		messageActive = null;
		editable = true;
		message = null;
		selectedEquip = 0;
	}

	/* Messages Activate */

	public void resetActivationPanel() {
		editable = true;
		message = null;

	}

	public void imageState() {

		if (!inputs)
			RequestContext.getCurrentInstance().execute("changeDIV();"); // Executar Javascript
	}

	public void getRowValue() {

		System.out.println(rowKey);
		// System.out.println(selectedRow);

		// Caso a linha da tabela esteja selecionada
		if (selectedRow) {

			create = true; // disabled
			submit = true; // disabled
			delete = false; // disabled
			select = false; // enabled
			editable = false; // enabled

		} else {

			rowKey = 0; // Reset State
			create = false; // disabled
			submit = false; // disabled
			delete = true; // disabled
			select = true; // enabled
			editable = true; // enabled
		}
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

	public List<Messages> getMessagesOnly() {
		return messagesOnly;
	}

	public void setMessagesOnly(List<Messages> messagesOnly) {
		this.messagesOnly = messagesOnly;
	}

}
