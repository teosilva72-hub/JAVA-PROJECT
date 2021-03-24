package br.com.tracevia.webapp.controller.occ;
import com.itextpdf.text.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import org.jboss.logging.Cause;
import org.primefaces.context.RequestContext;
import org.snmp4j.util.TableListener;

import br.com.tracevia.webapp.controller.global.UserAccountBean;
import br.com.tracevia.webapp.dao.occ.OccurrencesDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.UserAccount;
import br.com.tracevia.webapp.model.occ.OccurrencesData;
import br.com.tracevia.webapp.model.occ.OccurrencesDetails;
import br.com.tracevia.webapp.util.LocaleUtil;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;
@ManagedBean(name="occurrencesBean")
@ViewScoped
public class OccurrencesBean {

	private OccurrencesData data;
	private OccurrencesData getPdf;
	private UserAccountBean userId;
	private OccurrencesDetails details;

	OccurrencesDAO dao;
	LocaleUtil occLabel, occMessages;
	//
	private boolean save, edit, new_, reset, fields,
	table, alterar, pdf;

	private String action, title_modal, message_modal, idUpdate;

	private String mainPath, localPath, occNumber, path, way, downloadPath, pathDownload, pathSQL, monthPdf,
	minutePdf, secondPdf, dayPdf, hourPdf, nameUser, userName; 
	private String getFile, fileDelete, fileUpdate, pathImage, absoluteImage, imagePath;
	private String[] listarFile, listUpdate, tableFile, imagem, FileName;
	private File arquivos[], directory, fileWay;
	private int bytes, total, situation, idUpload;
	private int value, value1, value2, content, nivelUser;
	private Part file = null;
	private Part file2 = null;
	private ImageIcon image;
	private int rowkey;
	private boolean selectedRow;
	private List<OccurrencesData> occurrences;

	private List<SelectItem> rodovias, estado_locais, sentidos, faixas, tipos, origens, estados, causa_provaveis, condicao_locais, 
	condicao_de_trafegos, caracteristica_da_secoes, interferencia_da_faixas, sinalizacoes, estado_condutores, horas, minutos, actions,
	actionState, trackInterrupted, damageType, damageUnity, involvedType, damage_gravity, typeHour1;
	
	public List<SelectItem> getTypeHour1() {
		return typeHour1;
	}
	public void setTypeHour1(List<SelectItem> typeHour1) {
		this.typeHour1 = typeHour1;
	}
	public List<SelectItem> getEstado_locais() {
		return estado_locais;
	}
	public List<SelectItem> getSentidos() {
		return sentidos;
	}
	public List<SelectItem> getFaixas() {
		return faixas;
	}
	public List<SelectItem> getTipos() {
		return tipos;
	}
	public List<SelectItem> getOrigens() {
		return origens;
	}
	public List<SelectItem> getEstados() {
		return estados;
	}
	public List<SelectItem> getCausa_provaveis() {
		return causa_provaveis;
	}
	public List<SelectItem> getCondicao_locais() {
		return condicao_locais;
	}
	public List<SelectItem> getCondicao_de_trafegos() {
		return condicao_de_trafegos;
	}
	public List<SelectItem> getCaracteristica_da_secoes() {
		return caracteristica_da_secoes;
	}
	public List<SelectItem> getInterferencia_da_faixas() {
		return interferencia_da_faixas;
	}
	public List<SelectItem> getSinalizacoes() {
		return sinalizacoes;
	}
	public List<SelectItem> getEstado_condutores() {
		return estado_condutores;
	}

	public List<SelectItem> getRodovias() {
		return rodovias;
	}
	public List<SelectItem> getActions() {
		return actions;
	}
	public List<SelectItem> getActionState() {
		return actionState;
	}
	public List<SelectItem> getTrackInterrupted() {
		return trackInterrupted;
	}
	public List<SelectItem> getDamageType() {
		return damageType;
	}
	public List<SelectItem> getDamageUnity() {
		return damageUnity;
	}
	public boolean isPdf() {
		return pdf;
	}
	public void setPdf(boolean pdf) {
		this.pdf = pdf;
	}
	public String getIdUpdate() {
		return idUpdate;
	}
	public void setIdUpdate(String idUpdate) {
		this.idUpdate = idUpdate;
	}
	public int getIdUpload() {
		return idUpload;
	}
	public void setIdUpload(int idUpload) {
		this.idUpload = idUpload;
	}
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public String[] getTableFile() {
		return tableFile;
	}
	public void setTableFile(String[] tableFile) {
		this.tableFile = tableFile;
	}
	public String[] getFileName() {
		return FileName;
	}
	public void setFileName(String[] fileName) {
		FileName = fileName;
	}
	public String[] getImagem() {
		return imagem;
	}
	public void setImagem(String[] imagem) {
		this.imagem = imagem;
	}
	public ImageIcon getImage() {
		return image;
	}
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	public String getFileUpdate() {
		return fileUpdate;
	}
	public void setFileUpdate(String fileUpdate) {
		this.fileUpdate = fileUpdate;
	}
	public File getFileWay() {
		return fileWay;
	}
	public void setFileWay(File fileWay) {
		this.fileWay = fileWay;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public String[] getListUpdate() {
		return listUpdate;
	}
	public void setListUpdate(String[] listUpdate) {
		this.listUpdate = listUpdate;
	}
	public String getFileDelete() {
		return fileDelete;
	}
	public void setFileDelete(String fileDelete) {
		this.fileDelete = fileDelete;
	}
	public int getContent() {
		return content;
	}
	public void setContent(int content) {
		this.content = content;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getBytes() {
		return bytes;
	}
	public void setBytes(int bytes) {
		this.bytes = bytes;
	}
	public String getGetFile() {
		return getFile;
	}
	public void setGetFile(String getFile) {
		this.getFile = getFile;
	}
	public OccurrencesData getData() {
		return data;
	}
	public void setData(OccurrencesData data) {
		this.data = data;
	}
	public OccurrencesDetails getDetails() {
		return details;
	}
	public void setDetails(OccurrencesDetails details) {
		this.details = details;
	}
	public List<OccurrencesData> getOccurrences() {
		return occurrences;
	}
	public List<SelectItem> getHoras() {
		return horas;
	}
	public List<SelectItem> getMinutos() {
		return minutos;
	}

	public String getAbsoluteImage() {
		return absoluteImage;
	}
	public void setAbsoluteImage(String absoluteImage) {
		this.absoluteImage = absoluteImage;
	}
	public String getPathImage() {
		return pathImage;
	}

	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getPathDownload() {
		return pathDownload;
	}
	public void setPathDownload(String pathDownload) {
		this.pathDownload = pathDownload;
	}
	public int getRowkey() {
		return rowkey;
	}
	public void setRowkey(int rowkey) {
		this.rowkey = rowkey;
	}

	public boolean isSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(boolean selectedRow) {
		this.selectedRow = selectedRow;
	}

	public boolean isSave() {
		return save;
	}
	public boolean isAlterar() {
		return alterar;
	}
	public boolean isEdit() {
		return edit;
	}
	public boolean isReset() {
		return reset;
	}
	public boolean isNew_() {
		return new_;
	}
	public boolean isFields() {
		return fields;
	}
	public boolean isTable() {
		return table;
	}

	public void setSave(boolean save) {
		this.save = save;
	}
	public void setAlterar(boolean alterar) {
		this.alterar = alterar;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public void setNew_(boolean new_) {
		this.new_ = new_;
	}	
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	public void setFields(boolean fields) {
		this.fields = fields;
	}
	public void setTable(boolean table) {
		this.table = table;
	}

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	public String getTitle_modal() {
		return title_modal;
	}
	public void setTitle_modal(String title_modal) {
		this.title_modal = title_modal;
	}
	public String getMessage_modal() {
		return message_modal;
	}
	public void setMessage_modal(String message_modal) {
		this.message_modal = message_modal;
	}
	public List<SelectItem> getInvolvedType() {
		return involvedType;
	}
	public void setInvolvedType(List<SelectItem> involvedType) {
		this.involvedType = involvedType;
	}
	public List<SelectItem> getDamage_gravity() {
		return damage_gravity;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	public Part getFile() {
		return file;
	}
	public void setFile(Part file) {
		this.file = file;
	}

	public Part getFile2() {
		return file2;
	}
	public void setFile2(Part file2) {
		this.file2 = file2;
	}

	public File[] getArquivos() {
		return arquivos;
	}
	public void setArquivo(File arquivos[]) {
		this.arquivos = arquivos;
	}
	public File getDirectory() {
		return directory;
	}
	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public String[] getListarFile() {
		return listarFile;
	}
	public void setListarFile(String[] listarFile) {
		this.listarFile = listarFile;
	}

	@PostConstruct
	public void initialize() {	
		//input select options
		occurrences = new ArrayList<OccurrencesData>();
		rodovias = new ArrayList<SelectItem>();
		estado_locais = new  ArrayList<SelectItem>();
		sentidos = new  ArrayList<SelectItem>();
		faixas = new  ArrayList<SelectItem>();
		tipos = new  ArrayList<SelectItem>();
		origens = new  ArrayList<SelectItem>();
		estados = new  ArrayList<SelectItem>();
		causa_provaveis = new  ArrayList<SelectItem>();
		condicao_locais = new  ArrayList<SelectItem>();
		condicao_de_trafegos = new  ArrayList<SelectItem>();
		caracteristica_da_secoes = new  ArrayList<SelectItem>();
		interferencia_da_faixas = new  ArrayList<SelectItem>();
		sinalizacoes = new  ArrayList<SelectItem>();
		estado_condutores = new  ArrayList<SelectItem>();
		actions = new ArrayList<SelectItem>();
		actionState = new ArrayList<SelectItem>();
		trackInterrupted = new ArrayList<SelectItem>();
		damageType = new ArrayList<SelectItem>();
		damageUnity = new ArrayList<SelectItem>();
		involvedType = new ArrayList<SelectItem>();
		damage_gravity = new ArrayList<SelectItem>();
		try {

			OccurrencesDAO dao = new OccurrencesDAO();
			data = new OccurrencesData();

			rodovias = dao.dropDownFieldValues("rodovia");
			estado_locais = dao.dropDownFieldValues("estado_local");
			sentidos = dao.dropDownFieldValues("sentido");
			faixas = dao.dropDownFieldValues("faixa");
			tipos = dao.dropDownFieldValues("tipo");
			origens = dao.dropDownFieldValues("origem");
			estados = dao.dropDownFieldValues("estado");
			causa_provaveis = dao.dropDownFieldValues("causa_provavel");
			condicao_locais = dao.dropDownFieldValues("condicao_local");
			condicao_de_trafegos = dao.dropDownFieldValues("condicao_de_trafego");
			caracteristica_da_secoes = dao.dropDownFieldValues("caracteristica_da_secao");
			interferencia_da_faixas = dao.dropDownFieldValues("interferencia_da_faixa");
			sinalizacoes = dao.dropDownFieldValues("sinalizacao");
			estado_condutores = dao.dropDownFieldValues("estado_condutor");
			actions = dao.dropDownFieldValues("actions");
			actionState = dao.dropDownFieldValues("actionState");
			trackInterrupted = dao.dropDownFieldValues("trackinterrupted");
			damageType = dao.dropDownFieldValues("damageType");
			damageUnity = dao.dropDownFieldValues("damageUnity");
			involvedType = dao.dropDownFieldValues("involvedType");
			damage_gravity = dao.dropDownFieldValues("damageSeverity");

			//AM/PM
			typeHour1 = new ArrayList<SelectItem>();
			typeHour1.add(new SelectItem("am", "am"));
			typeHour1.add(new SelectItem("pm", "pm"));
			//hours
			horas = new  ArrayList<SelectItem>();

			for(int h = 1; h < 13; h++) {				

					horas.add(new SelectItem(String.valueOf(h), String.valueOf(h)));

			}
			//minutes
			minutos = new  ArrayList<SelectItem>();

			for(int m = 0; m < 60; m++){				
				if (m < 10)
					minutos.add(new SelectItem("0"+String.valueOf(m), "0"+String.valueOf(m)));
				else 
					minutos.add(new SelectItem(String.valueOf(m), String.valueOf(m)));

			}

			occLabel = new LocaleUtil();
			occLabel.getResourceBundle(LocaleUtil.LABELS_OCC);

			occMessages = new LocaleUtil();
			occMessages.getResourceBundle(LocaleUtil.MESSAGES_OCC);

			title_modal = occLabel.getStringKey("btn_salvar");
			message_modal = occLabel.getStringKey("msg_salvar");

			//Preencher Lista
			occurrences = dao.listarOcorrencias();

			//initialize btns
			edit = true;
			save = true;
			reset = true;
			new_ = false;
			fields = true;
			table = true;
			pdf = true;

			//paths
			mainPath = "C:\\Tracevia\\";
			pathImage = "http://localhost:8081/occ/";
			downloadPath = "file:///C:/Tracevia/";
			pathDownload = "C:\\Users\\mateu\\Downloads\\";

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}	

	//taking the last id from the database and passing 1 more
	public int pegarId() throws Exception{

		OccurrencesDAO x = new OccurrencesDAO();
		value = x.GetId();
		//String e = userId.getUser_id();
		value += 1;
		data = new OccurrencesData();
		data.setData_number(String.valueOf(value));

		return value;
	}

	//register occurrences
	public void cadastroOcorrencia() throws Exception {
		boolean sucess = false;

		OccurrencesDAO dao = new OccurrencesDAO();
		occNumber = dao.cadastroOcorrencia(data);
		//CREATE LOCAL PATH
		localPath = localPath(occNumber);

		//if id is non-null
		if(occNumber != null) {

			//btn
			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;
			pdf = true;
			listarFile = null;
			total = 0;
			
			//list occurences
			occurrences = dao.listarOcorrencias(); // List occurrences    
			sucess = dao.updateFilePath(localPath, occNumber); // Update path on Data Base

		}

	}

	//update occurences
	public void atualizarOcorrencia() throws Exception {

		OccurrencesDAO dao = new OccurrencesDAO();

		//ao atualizar a ocorrencia passamos um valor null para a variavel (nameUser),
		//passos 0 para a variavel (accessLevel) e para a variavel (updateTable) passamos false
		// ambos os valores ser√£o armazenados no BD a fazer a requisi√ß√£o
		String nameUser = ""; int accessLevel = 0; boolean updateTable = false; 
		System.out.println(data.getData_number());
		idUpdate = data.getData_number();
		//Passando dados definidos para o banco de dados
		dao.editTable(updateTable, nameUser, accessLevel, data.getData_number());

		//passando falso para acessar a condi√ß√£o IF
		boolean status = false;
		status = dao.atualizarOcorrencia(data);
		if(status) {

			//btn
			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;
			pdf = false;
			//resetando a variavel (total e listUpdate)
			listUpdate = null;
			listarFile = null;
			tableFile = null;
			//executando fun√ß√£o javascript
			RequestContext.getCurrentInstance().execute("resetForm()");
			RequestContext.getCurrentInstance().execute("fileTotalHidden()");
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");
			

			//listar ocorrencia
			occurrences = dao.listarOcorrencias();

			//listar arquivos sem direito a modifica√ß√£o
			//TableFile();

		}
		resetUpdate();

	}

	//metodo anular ocorrencia, esse metodo √© chamado quando estamos na sess√£o novo
	public void resetOccurrencesData(){       

		data = new OccurrencesData();
		RequestContext.getCurrentInstance().execute("cancelOcc()");
		//btn
		save = true;
		alterar = true;
		reset = true;
		new_ = false;
		fields = true;
		edit = true;
		table = true;
		pdf = true;

		//var 
		listarFile = null;
		listUpdate = null;
		total = 0;

		//deletar pasta
		deleteDirectory();
	}
	//metodo anular ocorrencia, esse metodo √© chamado quando estamos na sess√£o editar
	public void resetUpdate() throws Exception{
		OccurrencesDAO dao = new OccurrencesDAO();

		//ao anular a ocorrencia passamos os valores predeterminados para a seguintes variaveis:
		//(nameUser = null, accessLevel = 0 e updateTable = False) ambos os valores s√£o armazenados no BD.
		String nameUser = "";
		int accessLevel = 0;
		boolean updateTable = false;
		pdf = false;

		//passando dados para o banco de dados
		dao.editTable(updateTable, nameUser, accessLevel, data.getData_number());

		//atualizar a ocorrencia depois que o metodo for chamado
		occurrences = dao.listarOcorrencias();
		//chamando fun√ß√£o javascript
		RequestContext.getCurrentInstance().execute("eventValidator()");

		data = new OccurrencesData();

		//btn
		save = true;
		alterar = true;
		reset = true;
		new_ = false;
		fields = true;
		edit = true;
		table = true;
		pdf = true;

		//zerando os valores das variaves (listUpdate e total)
		listUpdate = null;
		total = 0;

	}
	public void getRowValue() throws Exception {

		try {

			//chamando valores do usu√°rio de outro controller
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();	

			//pegando os valores de outro controller e armazenado dentro das variaveis: (userName e nivelUser)
			userName = (String) facesContext.getExternalContext().getSessionMap().get("user");
			nivelUser = (int) facesContext.getExternalContext().getSessionMap().get("nivel");

			//executando fun√ß√£o javascript
			RequestContext.getCurrentInstance().execute("displayPdf()");
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");

			OccurrencesDAO dao = new OccurrencesDAO();

			//buscar dados por id
			data = dao.buscarOcorrenciaPorId(rowkey);

			//buscar dados pdf
			getPdf = dao.submitPdf(rowkey);

			//buscar caminho dos arquivos 
			pathSQL = data.getLocalFiles();

			//status da occorencia
			String x = data.getState_occurrences();

			//transformando em int
			situation = Integer.parseInt(x);

		}catch(Exception ex){

			ex.printStackTrace();

		}
		//listando arquivos quando clica na linha da tabela
		TableFile();
		pdf = false;
		//Se a linha da table estiver selecionada:
		if(selectedRow) {

			//se a situa√ß√£o for igual 30 ou 31
			//n√£o √© possivel fazer altera√ß√£o
			if(situation == 31 || situation == 30) {

				//btn
				save = true;
				alterar = true;
				reset = true;
				new_ = false;
				fields = true;
				edit = true;
				table = true;

				if(nivelUser == 1 || nivelUser == 6) {
					//btn
					save = true;
					alterar = true;
					edit = false;
					new_ = false;
					reset = true;
					fields = true; 

					//execute js
					RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
					RequestContext.getCurrentInstance().execute("fileTotal()");
				}

				//listar arquivos

				//execute js
				RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
				RequestContext.getCurrentInstance().execute("fileTotal()");

				//sen√£o se for igual a false acesso liberado para realizar edi√ß√£o
			}else if((data.getEditTable() == false)) {

				//btn
				save = true;
				alterar = true;
				edit = false;
				new_ = false;
				reset = true;
				fields = true; 

				//execute js
				RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
				RequestContext.getCurrentInstance().execute("fileTotal()");

				//sen√£o se for igual a true acesso bloqueado para realizar edi√ß√£o
			}else if((data.getEditTable() == true)) {

				//executando fun√ß√£o javascript
				RequestContext.getCurrentInstance().execute("msgUser()");

				save = true;
				alterar = true;
				reset = true;
				new_ = false;
				fields = true;
				edit = true;
				table = true;

				//se o nome do usuario local, for igual o nome da pessoa que esta editando
				//a ocorrencia acessa pode acessar a essa condi√ß√£o
				if(userName.equals(data.getNameUser())){

					//btn
					save = true;
					alterar = true;
					edit = false;
					new_ = false;
					reset = true;
					fields = true; 

					//execute js
					RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
					RequestContext.getCurrentInstance().execute("fileTotal()");

					//sen√£o se o nivel de acesso do usu√°rio for igual a 1 ou igual a 6
					//tem permiss√£o para acessar a condi√ß√£o
				}else if(nivelUser == 1 || nivelUser == 6) {

					//btn
					save = true;
					alterar = true;
					edit = false;
					new_ = false;
					reset = true;
					fields = true; 

					//execute js
					RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
					RequestContext.getCurrentInstance().execute("fileTotal()");
				}

			}

			//m√©todo n√£o est√° sendo usado
			//sen√£o estiver selecionada a linha da tabela
		}else {

			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//executando as fun√ß√µes javascript
			RequestContext.getCurrentInstance().execute("hiddenPdf()");
			RequestContext.getCurrentInstance().execute("msgFinishedHidden()");
			RequestContext.getCurrentInstance().execute("listingFileBtn()");
			RequestContext.getCurrentInstance().execute("listingFile()");

		}
		//zerando as variaveis
		listarFile = null;
	}
	//m√©todo novo
	public void btnEnable() throws Exception {

		//executando as fun√ß√µes javascript
		RequestContext.getCurrentInstance().execute("inputs()");
		RequestContext.getCurrentInstance().execute("disableEdit()");
		RequestContext.getCurrentInstance().execute("listUpdateFile1()");
		RequestContext.getCurrentInstance().execute("uploadFile()");
		RequestContext.getCurrentInstance().execute("hiddenListFile()");
		RequestContext.getCurrentInstance().execute("hiddenPdf()");
		//pegando o valor da ultima variavel do vanco de dados
		value = pegarId(); // pegarId() + 1
		//se o valor for maior do que 0 acessamos a condi√ß√£o
		if(value > 0) {

			//executando o m√©todo cadastrar, ou seja, quando acessamos esse m√©todo
			//estamos reservando o id no banco de dados.
			cadastroOcorrencia();

			//transformando a variavel (value) em String
			String occNumber = (String.valueOf(value));

			//CREATE LOCAL PATH
			localPath = localPath(occNumber);
			createFileFolder(mainPath, localPath);	
			//executando javascript
			RequestContext.getCurrentInstance().execute("inputs()");
			RequestContext.getCurrentInstance().execute("listingFile()");
			RequestContext.getCurrentInstance().execute("disableEdit()");
			RequestContext.getCurrentInstance().execute("resetForm()");
			RequestContext.getCurrentInstance().execute("fileTotalHidden()");

			//btn
			edit = true;
			save = false;
			alterar = true;
			reset = false;
			new_ = true;
			fields = false;
			action = "save";
			pdf = true;
		}

	}
	//m√©todo editar ocorrencia
	public void btnEdit() throws Exception {

		OccurrencesDAO dao = new OccurrencesDAO();

		//pegando valores dos usu√°rios de outro controller
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		//passando os valores predeterminados para as variveis
		String nameUser = (String) facesContext.getExternalContext().getSessionMap().get("user");
		int nivelUser = (int) facesContext.getExternalContext().getSessionMap().get("nivel");
		boolean updateTable = true;
		//passando valores das varaveis para o banco de dados
		dao.editTable(updateTable, nameUser, nivelUser, data.getData_number());			
//
		//btn
		fields = false;
		reset = false;
		save = true;
		edit = true;
		alterar = false;
		pdf = true;

		//executando fun√ß√µes javascript
		RequestContext.getCurrentInstance().execute("inputs()");
		RequestContext.getCurrentInstance().execute("alterBtnReset()");
		RequestContext.getCurrentInstance().execute("bloquerTable()");
		RequestContext.getCurrentInstance().execute("listUpdateFile1()");
		RequestContext.getCurrentInstance().execute("alterarBtn()");
		RequestContext.getCurrentInstance().execute("atualizarTela()");

		//listando arquivos
		listingUpdate();

	}
	//CREATE DIRECTORY FOR FILES
	public void createFileFolder(String mainPath, String localPath) {

		String directoryName = mainPath.concat(localPath);

		File directory = new File(directoryName);

		if (!directory.exists()){
			directory.mkdirs();	      
		}

	}

	//CREATE PATH FOR OCCURRENCE
	//ACCORDING YEAR AND MONTH
	public String localPath(String occ_number) {

		DateTimeApplication dta = new DateTimeApplication();
		LocalDate local = dta.localeDate();
		path = local.getYear()+"//"+local.getMonthValue()+"//OCC_"+occ_number;

		return path;
	}

	//m√©todo para enviar arquivos na tela de atualiza√ß√£o de cadastro
	public void updateFile() throws IOException, ServletException{
		RequestContext.getCurrentInstance().execute("msgUploads()");
		uploadBean up = new uploadBean();
		up.update(mainPath, way, file2);

		//btns
		edit = true;
		save = false;
		alterar = true;
		reset = false;
		new_ = true;
		fields = false;

		//listando arquivos
		listingUpdate();

		//executando fun√ß√µes javascript
		RequestContext.getCurrentInstance().execute("mostrarTab2()");
		RequestContext.getCurrentInstance().execute("bloquerTable()");
		RequestContext.getCurrentInstance().execute("msgSaveFile()");
		RequestContext.getCurrentInstance().execute("fileTotal1()");
		RequestContext.getCurrentInstance().execute("alterarBtn()");



	}

	//m√©todo enviando
	public void uploadFile() throws Exception {

		uploadBean up = new uploadBean();	

		//passando variavel para uploadFile
		up.upload(file, mainPath, localPath);			

		//btn
		edit = true;
		save = false;
		alterar = true;
		reset = false;
		new_ = true;
		fields = false;

		//listando arquivosd
		listFiles();

		//executando o javascript
		RequestContext.getCurrentInstance().execute("mostrarTab2()");
		RequestContext.getCurrentInstance().execute("bloquerTable()");
		RequestContext.getCurrentInstance().execute("fileTotal()");
		RequestContext.getCurrentInstance().execute("listUpdateFile1()");
		RequestContext.getCurrentInstance().execute("uploadFile()");
		RequestContext.getCurrentInstance().execute("blockListFile()");
		RequestContext.getCurrentInstance().execute("msgUploads()");

	}

	//m√©todo listar arqivos ao atualizar
	public String[] listingUpdate() {

		//executar javascript
		RequestContext.getCurrentInstance().execute("bloquerTable()");
		RequestContext.getCurrentInstance().execute("listUpdateFile1()");
		RequestContext.getCurrentInstance().execute("mostrarTab2()");
		RequestContext.getCurrentInstance().execute("alterarBtn()");
		RequestContext.getCurrentInstance().execute("msgUploads()");

		//btns
		save = true;
		alterar = false;
		edit = true;
		new_ = true;
		reset = false;
		fields = false;
		pdf = true;

		//pega o id da tabela.
		int id = getValue();

		//criando caminho da sele√É¬ß√É¬£o da pasta.
		way = pathSQL;

		//caminho criado
		fileWay = new File(mainPath+way);
		//System.out.println("Estamos aqui: > "+ fileWay);
		//iniciando variavel com valor 0
		int x = 0;

		//listando os arquivos
		arquivos = fileWay.listFiles();
		listUpdate = new String[arquivos.length];
		//pegando o total de aruivos dentro do diretorio
		total = arquivos.length;
		//enquantos os arquivos forem diferentes do valor 0 executa
		while (x != arquivos.length){

			//pegando os nomes do arquivos na posi√ß√£o
			listUpdate[x] = arquivos[x].getName();

			System.out.println("Position: "+x+" < Arquivo na pasta > "+ listUpdate[x]);

			x++;

		}

		//retornando o valor do m√©todo listUpdate == nome do arquivo
		return listUpdate;
	}
	//m√©todo listar arquivos quando clicamos na tablea
	public String[] TableFile() {

		//pegando o status da ocorr√™ncia		
		String b = data.getState_occurrences();
		//tranformando o valor do status da ocorr√™ncia  em inteiro
		situation = Integer.parseInt(b);
		pdf = false;
		//se o status da occorencia for igual a 30 ou 31, acessamos esse fun√ß√£o
		if(situation == 31 || situation == 30) {
			//btns
			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//executando javascript
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");
			RequestContext.getCurrentInstance().execute("msgFinished()");

			//se o nivel de acesso dop usu√°rio for igual a 1 ou igual a 6, acessamos a fun√ß√£o
			if(nivelUser == 1 || nivelUser == 6) {
				//btn
				save = true;
				alterar = true;
				edit = false;
				new_ = false;
				reset = true;
				fields = true; 

				//execute js
				RequestContext.getCurrentInstance().execute("listUpdateFile2()");
				RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
				RequestContext.getCurrentInstance().execute("fileTotal()");
			}
			//sen√£o se o valor do atributo editTable for igual 0 (false), acessos a condi√ß√£o
		}else if(data.getEditTable() == false) {

			//btn
			save = true;
			alterar = true;
			edit = false;
			new_ = false;
			reset = true;
			fields = true; 

			//execute js
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");
			RequestContext.getCurrentInstance().execute("unLock()");
			RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
			RequestContext.getCurrentInstance().execute("fileTotal()");

			//sen√£o se for igual a true acesso bloqueado para realizar edi√ß√£o
		}else if(data.getEditTable() == true ) {

			RequestContext.getCurrentInstance().execute("msgUser()");

			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//se o nome do usu√°rio for igual o nome armazenado no atributo nameUser, acessamos a condi√ß√£o
			if(userName.equals(data.getNameUser())){

				//btn
				save = true;
				alterar = true;
				edit = false;
				new_ = false;
				reset = true;
				fields = true; 

				//execute js
				RequestContext.getCurrentInstance().execute("listUpdateFile2()");
				RequestContext.getCurrentInstance().execute("unLock()");
				RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
				RequestContext.getCurrentInstance().execute("fileTotal()");

				//sen√£o se o nivel de acesso do usu√°rio for igual a 1 ou igual a 6, acessamos a condi√ß√£o
			}else if(nivelUser == 1 || nivelUser == 6) {
				//btn
				save = true;
				alterar = true;
				edit = false;
				new_ = false;
				reset = true;
				fields = true; 

				//execute js
				RequestContext.getCurrentInstance().execute("listUpdateFile2()");
				RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
				RequestContext.getCurrentInstance().execute("fileTotal()");
			}

		}

		//executando javascript
		RequestContext.getCurrentInstance().execute("fileTotal1()");

		//pega o id da tabela.
		int id = getValue();

		//criando caminho da sele√ß√£o da pasta.
		way = pathSQL;

		//caminho criado
		fileWay = new File(mainPath+way);
		//System.out.println("Estamos aqui: > "+ fileWay);

		int x = 0;

		arquivos = fileWay.listFiles();

		tableFile = new String[arquivos.length];
		total = arquivos.length;
		while (x != arquivos.length){

			tableFile[x] = arquivos[x].getName();

			System.out.println("Position: "+x+" < Arquivo na pasta > "+ tableFile[x]);

			x++;

		}
		//passando valor final do m√©todo para a variavel tableFile
		return tableFile;

	}

	//m√©todo listar arquivos
	public String[] listFiles() throws Exception{

		//vari√É¬°vel do tipo int
		content = 0;

		//local do armazenamento do arquivo
		directory = new File(mainPath+path+"\\");

		//listar arquivos
		arquivos = directory.listFiles();

		//pega o total de arquivos no diret√É¬≥rio
		total = arquivos.length;
		System.out.println("Total Files: "+ total);

		listarFile = new String[arquivos.length];

		//lopping para pegar arquivos dentro do diret√É¬≥rio
		while (content != arquivos.length){
			//pega arquivo pelo nome
			listarFile[content] = arquivos[content].getName();		
			System.out.println(content+": Arquivo recebido "+ listarFile[content]);
			content++;

		} 
		//passando valor do m√©todo para a vari√°vel
		return listarFile;
	}

	//esse m√©todo n√£o esta sendo aplicado, est√° aqui para futuros testes
	//Buscando imagem
	public String getImageUpload(String myImg) throws Exception{

		//gerando o caminho onde se encontra a imagem
		OccurrencesBean pegar= new OccurrencesBean();
		int id = pegar.pegarId();
		DateTimeApplication dddd = new DateTimeApplication();
		LocalDate data = dddd.localeDate();
		String imagePath = data.getYear()+"/"+data.getMonthValue()+"/"+"OCC_"+id+"/";

		absoluteImage = pathImage+imagePath+myImg;

		return absoluteImage;

	}
	//esse m√©todo n√£o esta sendo usado, est√° aqui para testes
	public String getImageUpdate(String myImg) throws Exception{

		//gerando o caminho onde se encontra a imagem
		int id = getValue();

		String imagePath = pathSQL+"/";

		absoluteImage = pathImage+imagePath+myImg;

		return absoluteImage;

	}

	//m√©todo deletar arquivos na atualiza√ß√£o do cadastro
	public void deleteFileUpdate(String file) throws Exception {
		//tentar
		try {
			//criando o camingo onde os arquivos s√£o armazenados
			fileWay = new File(mainPath+way+"\\");

			//buscando arquivos para ver se existem dentro do diret√≥rio
			boolean check = new File(fileWay, file).exists();

			//se existir arquivos dentro da pasta, acessamos a condi√ß√£o
			if(check) {

				//pegando o arquivo
				File currentFile = new File(fileWay, file);
				//execute o delete
				currentFile.delete();
				//btns
				save = true;
				alterar = false;
				edit = true;
				new_ = true;
				reset = false;
				fields = false;
				//quando deletamos um arquivo, passamos o valor -1 para o total
				total = total - 1;
				//executando javascript
				RequestContext.getCurrentInstance().execute("mostrarTab2()");
				RequestContext.getCurrentInstance().execute("msgDelete()");
				RequestContext.getCurrentInstance().execute("bloquerTable()");
				RequestContext.getCurrentInstance().execute("alterarBtn()");
				//listando arquivos
				listingUpdate();
			}

			//capturar 
		}catch(Exception ex) {
			//executando fun√ß√£o javascript
			RequestContext.getCurrentInstance().execute("mostrarTab2()");
			//msg de erro ao deletar file
			System.out.println("Erro ao excluir arquivo");
		}

	}
	//m√©todo deletar arquivos quando estamos na tela de novo cadastri
	public void deleteFile(String file){
		//tentar
		try {

			//caminho do diretorio e o arquivo que estou apagando
			directory = new File(mainPath+path+"\\");

			//buscando arquivos para ver se existem dentro do diret√≥rio
			boolean check = new File(directory, file).exists();
			//se existir arquivos dentro da pasta, acessamos a condi√ß√£o
			if(check) {
				//pegando o arquivo
				File currentFile = new File(directory, file);
				//execute o delete
				currentFile.delete();
				//executando javascript
				RequestContext.getCurrentInstance().execute("bloquerTable()");
				//quando deletamos um arquivo, passamos o valor -1 para o total
				total -=  1;
				//executando m√©todo listar arquivos
				listFiles();
				//executando fun√ß√µes javascript
				RequestContext.getCurrentInstance().execute("mostrarTab2()");
				RequestContext.getCurrentInstance().execute("msgDelete()");
				RequestContext.getCurrentInstance().execute("bloquerTable()");

			}

			//capturar
		}catch(Exception ex) {
			//executando js
			RequestContext.getCurrentInstance().execute("mostrarTab2()");
			//msg de erro
			System.out.println("Erro ao excluir arquivo");
		}

	}
	//exclui pasta gerada do occ quando √© clicado em anular
	public File deleteDirectory() {

		try {
			//escolhendo o caminho do arquivo
			File folder = new File(mainPath+path+"\\");

			//se o diretorio for encontrado
			if (folder.isDirectory()) {
				//pegamos todos os arquivos que est√£o dentro da pasta
				File[] files = folder.listFiles();

				//para deletar os objetos, percorremos a lista e fazemos de delete
				for (File filesToDelete : files) {

					//deletando arquivos
					filesToDelete.delete();

				}

				//deletando a pasta
				folder.delete();

			}

			//capturando
		}catch(Exception ex) {
			//msg de erro
			System.out.println("Erro ao excluir diretorio");
			//executando fun√ß√£o js
			RequestContext.getCurrentInstance().execute("mostrarTab2()");

		}
		//retornando um valor nulo para esse m√©todo
		return null;
	}
	//m√©todo baixar arquivos
	public void download(String fileName) throws Exception {

		OccurrencesDAO dao = new OccurrencesDAO();
		data = new OccurrencesData();
		//p√©gando o id da ocorr√™ncia e atribuindo +1
		int id = dao.GetId();
		id += 1;
		//criando variavel global

		data.setData_number(String.valueOf(id));

		//chamando m√É¬™s e ano, para passar para o caminho.
		DateTimeApplication dddd = new DateTimeApplication();
		LocalDate data = dddd.localeDate();

		//criando caminho da sele√É¬ß√É¬£o da pasta.
		String absoluteFile = data.getYear()+"/"+data.getMonthValue()+"/OCC_"+id+"/"+fileName;

		//caminho onde pegamos o arquivo
		URL url = new URL(downloadPath+absoluteFile);
		String arquivos = url.getQuery();

		//caminho onde o arquivo ser√É¬° guardado
		File file = new File(pathDownload+fileName);

		InputStream is = url.openStream();
		FileOutputStream fos = new FileOutputStream(file);

		int bytes = 0;

		//enquanto o byte faz a leitura do arquivo o qual √© diferente de -1
		while ((bytes = is.read()) != -1) {
			//subscreve/ copia o arquivo atual
			fos.write(bytes);
		}

		//fechando o comando downlod
		is.close();
		fos.close();

		//executando fun√ß√µes js
		RequestContext.getCurrentInstance().execute("bloquerTable()");
		RequestContext.getCurrentInstance().execute("mostrarTab2()");
		RequestContext.getCurrentInstance().execute("msgDownload()");
	}
	public void downloadUpdate(String fileName) throws Exception {
		//js
		RequestContext.getCurrentInstance().execute("bloquerTable()");	
		RequestContext.getCurrentInstance().execute("mostrarTab2()");
		RequestContext.getCurrentInstance().execute("msgDownload()");
		RequestContext.getCurrentInstance().execute("alterarBtn()");
		//pegando o id
		int id = getValue();

		//criando caminho da sele√É¬ß√É¬£o da pasta.
		String absoluteFile = pathSQL+"/"+fileName;

		//caminho onde pegamos o arquivo
		URL url = new URL(downloadPath+absoluteFile);
		String arquivos = url.getQuery();

		//caminho onde o arquivo ser√É¬° guardado
		File file = new File(pathDownload+fileName);

		InputStream is = url.openStream();
		FileOutputStream fos = new FileOutputStream(file);

		int bytes = 0;
		//enquanto o byte faz a leitura do arquivo o qual √© diferente de -1
		while ((bytes = is.read()) != -1) {
			fos.write(bytes);
		}
		//parando o download
		is.close();
		fos.close();

	}
	//download quando fizer o clique em alguma linha da tabela
	public void downloadUpdateTable(String fileName) throws Exception {

		//pegando o id
		int id = getValue();

		//criando caminho da sele√É¬ß√É¬£o da pasta.
		String absoluteFile = pathSQL+"/"+fileName;

		//caminho onde pegamos o arquivo
		URL url = new URL(downloadPath+absoluteFile);
		String arquivos = url.getQuery();

		//caminho onde o arquivo ser√É¬° guardado
		File file = new File(pathDownload+fileName);

		InputStream is = url.openStream();
		FileOutputStream fos = new FileOutputStream(file);

		int bytes = 0;
		//enquanto o byte faz a leitura do arquivo o qual √© diferente de -1
		while ((bytes = is.read()) != -1) {
			fos.write(bytes);
		}
		//fechando o download
		is.close();
		fos.close();

		//se a variavel situa√ß√£o for igual a 30 ou 31 acessamos a condi√ß√£o
		if(situation == 31 || situation == 30) {
			//btns
			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//sen√£o acessamos a essa condi√ß√£o
		}else {
			//btns
			save = true;
			alterar = true;
			edit = false;
			new_ = false;
			reset = true;
			fields = true; 

		}
		//executando js
		RequestContext.getCurrentInstance().execute("listUpdateFile2()");
		RequestContext.getCurrentInstance().execute("mostrarTab2()");
		RequestContext.getCurrentInstance().execute("msgDownload()");
		System.out.println("Download realizado: "+fileName);
	}
	//m√©todo download PDF
	public String[] downloadPdf() throws Exception {
		// criaÁ„o do documento
		Document document = new Document();
		TranslationMethods trad = new TranslationMethods();


		try {
			//caminho onde È gerado o pdf
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\mateu\\Downloads\\"+"OCC_"+data.getData_number()+".pdf"));
			//gera o arquivo
			document.open();

			//formato da folha
			document.setPageSize(PageSize.A4);

			RequestContext.getCurrentInstance().execute("uploadFile()");
			RequestContext.getCurrentInstance().execute("displayPdf()");
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");
			RequestContext.getCurrentInstance().execute("msgDownload()");

			//Editando o tipo de fonte do titulo
			Paragraph pTitulo = new Paragraph(new Phrase(20F , trad.occLabels("RelatÛrio da OcorrÍncia"), FontFactory.getFont(FontFactory.HELVETICA, 17F)));
			Paragraph evento = new Paragraph(new Phrase(20F , trad.occLabels("Eventos"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph dateHour = new Paragraph(new Phrase(20F , trad.occLabels("Data, InÌcio, Fim"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph causeProbable = new Paragraph(new Phrase(20F , trad.occLabels("Causa Prov·vel"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph eventoLocal = new Paragraph(new Phrase(20F , trad.occLabels("Evento Local"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph detalhes = new Paragraph(new Phrase(20F , trad.occLabels("Detalhes"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph description = new Paragraph(new Phrase(20F , trad.occLabels("DescriÁ„o"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph envolvidos = new Paragraph(new Phrase(20F , trad.occLabels("Envolvidos"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph track = new Paragraph(new Phrase(20F , trad.occLabels("Tr‚nsito"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph danos = new Paragraph(new Phrase(20F , trad.occLabels("Danos"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph action = new Paragraph(new Phrase(20F , trad.occLabels("AÁ„o"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));

			//chamando a imagem

			Image image1 = Image.getInstance("C:\\Users\\mateu\\eclipse-workspace\\tracevia-application\\src\\main\\webapp\\resources\\images\\home\\traceviaLayout.png");
			Image image2 = Image.getInstance("C:\\Users\\mateu\\eclipse-workspace\\tracevia-application\\src\\main\\webapp\\resources\\images\\home\\tuxpan.png");

			System.out.println(RoadConcessionaire.externalImagePath);
			//ediÁ„o das imagens
			image1.setAbsolutePosition(50, 790);
			image1.scaleAbsolute (100, 50);
			image2.setAbsolutePosition(420, 800);
			image2.scaleAbsolute (120, 30);
			//passando a imagem
			document.add(image1);
			document.add(image2);

			//add titulo
			ColumnText tl = new ColumnText(writer.getDirectContent());
			Paragraph tx = new Paragraph();
			tl.setSimpleColumn(400,820,200,50);
			tx.add(pTitulo);
			tl.addElement(tx);
			tl.go();
			document.add(new Paragraph("\n"));


			Rectangle rowPage = new Rectangle(577, 40, 10, 790); //linha da pagina 

			rowPage.setBorderColor(BaseColor.BLACK);
			rowPage.setBorderWidth(2);
			rowPage.setBorder(Rectangle.BOX);
			document.add(rowPage);
			//final da linda da pagina
			ColumnText ct = new ColumnText(writer.getDirectContent());
			ct.setSimpleColumn(700,0,200,30);
			Paragraph p = new Paragraph();
			p.add("                              "+"Pag 1");//paragrafo Evento
			ct.addElement(p);
			ct.go();
			document.add(new Paragraph(evento+"\n"+"\n"));
			document.add(new Paragraph("Occ N∫: "+data.getData_number()+"        "
					+ trad.occLabels("Tipo")+(": ")+ getPdf.getType()+"         "
					+ trad.occLabels("Origem")+(": ")+getPdf.getOrigin()+"          "
					+ trad.occLabels("SituaÁ„o")+(": ")+getPdf.getState_occurrences()+"\n"
					+"_____________________________________________________________________________"
					+"\n\n"
					));

			//data e hora inicial
			/*Rectangle dateHourStart = new Rectangle(577, 615, 10, 680); // you can resize rectangle 
			dateHourStart.enableBorderSide(1);
			dateHourStart.enableBorderSide(2);
			dateHourStart.enableBorderSide(4);
			dateHourStart.enableBorderSide(8);
			dateHourStart.setBorderColor(BaseColor.BLACK);
			dateHourStart.setBorderWidth(1);
			document.add(dateHourStart);*/
			document.add(new Paragraph(dateHour+"\n"+"\n"));
			//data e hora inicial
			document.add(new Paragraph(trad.occLabels("Inicial")+": "+data.getStart_date()
			+"             "+("Inicial")+(": ")+data.getStart_hour()+":"+data.getStart_minute()+"  "+data.getTypeHour1()
			+"             "+ ("Final")+": "+data.getEnd_date()+"             "+
			("Final")+": "+data.getEnd_hour()+":"+data.getEnd_minute()+" "+data.getTypeHour2()+"\n"
			+"_____________________________________________________________________________"
			+"\n\n"));


			//causa prov·vel e descriÁ„o principal e interna.
			/*Rectangle causePr= new Rectangle(577, 310, 10, 610); // you can resize rectangle 
			causePr.enableBorderSide(1);
			causePr.enableBorderSide(2);
			causePr.enableBorderSide(4);
			causePr.enableBorderSide(8);
			causePr.setBorderColor(BaseColor.BLACK);
			causePr.setBorderWidth(1);
			document.add(causePr);*/
			document.add(new Paragraph(causeProbable+"\n"+"\n"));
			document.add(new Paragraph(trad.occLabels("Causa")+": "+getPdf.getCause()+"\n\n"));
			document.add(new Paragraph(trad.occLabels("DescriÁ„o")+": "+data.getCause_description()+"\n"
					+"_____________________________________________________________________________"
					+"\n\n"));

			//Evento Local
			/*Rectangle eventL= new Rectangle(577, 90, 10, 300); // you can resize rectangle 
			eventL.enableBorderSide(1);
			eventL.enableBorderSide(2);
			eventL.enableBorderSide(4);
			eventL.enableBorderSide(8);
			eventL.setBorderColor(BaseColor.BLACK);
			eventL.setBorderWidth(1);
			document.add(eventL);*/
			document.add(new Paragraph(eventoLocal+"\n"+"\n"));
			document.add(new Paragraph("KM: "+data.getKilometer()+"            "
					+ trad.occLabels("Rodovia")+": "+getPdf.getHighway()+"            "
					+ trad.occLabels("Estado")+": "+data.getLocal_state()+"\n\n"));
			document.add(new Paragraph(trad.occLabels("Sentido")+": "+getPdf.getDirection()+"                         "
					+ trad.occLabels("Faixa")+": "+getPdf.getLane()+"                 "
					+ trad.occLabels("ObservaÁ„o")+": "+data.getOthers()+"\n"
					+"_____________________________________________________________________________"
					+"\n\n"));

			//Detalhes
			/*	Rectangle details= new Rectangle(577, 255, 10, 355); // you can resize rectangle 
			details.enableBorderSide(1);
			details.enableBorderSide(2);
			details.enableBorderSide(4);
			details.enableBorderSide(8);
			details.setBorderColor(BaseColor.BLACK);
			details.setBorderWidth(1);
			document.add(details);*/
			document.add(new Paragraph(detalhes+"\n"+"\n"));
			document.add(new Paragraph("CondiciÛn local: "+getPdf.getLocal_condition()+"  "
					+ trad.occLabels("CondiciÛn del tr·fico")+": "+getPdf.getTraffic()+"   "
					+ trad.occLabels("CaracterÌstica")+": "+getPdf.getCharacteristic()+"\n\n"));
			document.add(new Paragraph(trad.occLabels("InterferÍncia Faixa")+": "+getPdf.getInterference()+"     "
					+trad.occLabels("SinalizaÁ„o")+": "+getPdf.getSignaling()+"     "
					+trad.occLabels("SituaÁ„o Condutor")+": "+ getPdf.getConductor_condition()));

			//final da primeira p·gina

			document.newPage();//inicio da segunda p·gina
			Rectangle rowPage1 = new Rectangle(577, 40, 10, 820); //linha da pagina 
			rowPage1.setBorderColor(BaseColor.BLACK);
			rowPage1.setBorderWidth(2);
			rowPage1.setBorder(Rectangle.BOX);
			document.add(rowPage1);
			ColumnText ct1 = new ColumnText(writer.getDirectContent());
			ct1.setSimpleColumn(700,0,200,30);
			Paragraph p1 = new Paragraph();
			p1.add("                              "+"Pag 2");//paragrafo Evento
			ct1.addElement(p1);
			ct1.go();
			/*Rectangle descriptions= new Rectangle(577, 110, 10, 250); // you can resize rectangle 
			descriptions.enableBorderSide(1);
			descriptions.enableBorderSide(2);
			descriptions.enableBorderSide(4);
			descriptions.enableBorderSide(8);
			descriptions.setBorderColor(BaseColor.BLACK);
			descriptions.setBorderWidth(1);
			document.add(descriptions);*/
			document.add(new Paragraph(description+"\n"+"\n"));
			document.add(new Paragraph(trad.occLabels("Titulo DescriÁ„o")+": "+ data.getDescription_title()+"\n\n"));
			document.add(new Paragraph(trad.occLabels("DescriÁ„o")+": "+data.getDescription_text()+"\n"
					+"_____________________________________________________________________________"
					+"\n\n"));

			//Envolvidos
			/*Rectangle envolvido = new Rectangle(577, 670, 10, 810); // you can resize rectangle 
			envolvido.enableBorderSide(1);
			envolvido.enableBorderSide(2);
			envolvido.enableBorderSide(4);
			envolvido.enableBorderSide(8);
			envolvido.setBorderColor(BaseColor.BLACK);
			envolvido.setBorderWidth(1);
			document.add(envolvido);*/
			document.add(new Paragraph(envolvidos+"\n"+"\n"));
			document.add(new Paragraph(trad.occLabels("Tipo")+": "+getPdf.getInvolved_type()+"\n\n"));
			document.add(new Paragraph(trad.occLabels("DescriÁ„o")+": "+ data.getInvolved_description()+"\n"
					+"_____________________________________________________________________________"
					+"\n\n"));
			
			//Tr‚nsito
			/*Rectangle track1 = new Rectangle(577, 560, 10, 665); // you can resize rectangle 
			track1.enableBorderSide(1);
			track1.enableBorderSide(2);
			track1.enableBorderSide(4);
			track1.enableBorderSide(8);
			track1.setBorderColor(BaseColor.BLACK);
			track1.setBorderWidth(1);
			document.add(track1);*/
			document.add(new Paragraph(track+"\n"+"\n"));
			document.add(new Paragraph(trad.occLabels("Inicial")+": " + data.getTrackStartDate()+ "             "+trad.occLabels("Inicial")+": " + data.getTrackStartHour() + ":" + data.getTrackStartMinute()+"  "
			+data.getTypeHour3()+ "             "+ trad.occLabels("Final")+": " + data.getTrackEndDate() + "             "+ trad.occLabels("Final")+": " + data.getTrackEndHour() + ":"+data.getTrackEndMinute()+"  "+data.getTypeHour4() + "\n\n"));
			document.add(new Paragraph());
			document.add(new Paragraph(trad.occLabels("Extens„o(KM)")+": "+data.getTraffic_extension()+"            "
					+trad.occLabels("Pista Interrompida")+": "+ getPdf.getTraffic_stopped()+"\n\n"));
			document.newPage();//inicio da terceira p·gina
			
			Rectangle rowPage2 = new Rectangle(577, 40, 10, 820); //linha da pagina 
			rowPage2.setBorderColor(BaseColor.BLACK);
			rowPage2.setBorderWidth(2);
			rowPage2.setBorder(Rectangle.BOX);
			document.add(rowPage2);
			
			ColumnText ct2 = new ColumnText(writer.getDirectContent());
			ct2.setSimpleColumn(700,0,200,30);
			Paragraph p2 = new Paragraph();
			p2.add("                              "+"Pag 3");//paragrafo Evento
			ct2.addElement(p2);
			ct2.go();
			
			//Danos
			/*Rectangle damage1 = new Rectangle(577, 420, 10, 555); // you can resize rectangle 
			damage1.enableBorderSide(1);
			damage1.enableBorderSide(2);
			damage1.enableBorderSide(4);
			damage1.enableBorderSide(8);
			damage1.setBorderColor(BaseColor.BLACK);
			damage1.setBorderWidth(1);
			document.add(damage1);*/
			document.add(new Paragraph(danos+"\n"+"\n"));
			document.add(new Paragraph(""));
			document.add(new Paragraph(trad.occLabels("Tipo")+": "+getPdf.getDamage_type_damage()+"     "+trad.occLabels("Gravidade")+": "+getPdf.getDamage_gravity()+"     "+trad.occLabels("Unidade")+": "+getPdf.getDamageUnity()
			+"     "+trad.occLabels("Quantidade")+": "+data.getDamage_amount()+ "\n\n"));
			document.add(new Paragraph(trad.occLabels("DescriÁ„o")+": "+data.getDemage_description()+"\n"
					+"_____________________________________________________________________________"
					+"\n\n"));
			//aÁtion
			/*Rectangle action1 = new Rectangle(577, 225, 10, 415); // you can resize rectangle 
			action1.enableBorderSide(1);
			action1.enableBorderSide(2);
			action1.enableBorderSide(4);
			action1.enableBorderSide(8);
			action1.setBorderColor(BaseColor.BLACK);
			action1.setBorderWidth(1);
			document.add(action1);*/
			document.add(new Paragraph(action+"\n"+"\n"));

			document.add(new Paragraph(trad.occLabels("Tipo")+": "+getPdf.getAction_type()+"             "+trad.occLabels("SituaÁ„o")+": "+getPdf.getStatusAction()+"\n\n"));
			document.add(new Paragraph(trad.occLabels("Inicial")+": "+data.getActionStartData()+"     "+trad.occLabels("Inicial")+": "+data.getActionStartHour()+":"+data.getActionStartMinute()+"  "+data.getTypeHour5()
			+"             "+trad.occLabels("Final")+": "+data.getActionEndData()+"             "+trad.occLabels("Final")+": "+data.getActionEndHour()+":"+data.getActionEndMinute()+"  "+data.getTypeHour6()+"\n\n"));
			document.add(new Paragraph(trad.occLabels("DescriÁ„o")+": "+data.getAction_description()+"\n"
					+"_____________________________________________________________________________\n"));
			//darken date and time
			int day1 = LocalDateTime.now().getDayOfMonth();
			int year1 = LocalDateTime.now().getYear();
			int month1 = LocalDateTime.now().getMonthValue();
			int hour1 = LocalDateTime.now().getHour();
			int minute1 = LocalDateTime.now().getMinute();
			int second1 = LocalDateTime.now().getSecond();

			//if the variable is less than ten, I access the condition and add a zero before the minute.
			if(day1 < 10) {dayPdf = "0"+String.valueOf(day1);}else {dayPdf = String.valueOf(day1);}
			if(hour1 < 10) {hourPdf = "0"+String.valueOf(hour1);}else {hourPdf = String.valueOf(hour1);}
			if(month1 < 10) {monthPdf = "0"+String.valueOf(month1);}else {monthPdf = String.valueOf(month1);}
			if(minute1 < 10) {minutePdf = "0"+String.valueOf(minute1);}else {minutePdf = String.valueOf(minute1);}
			if(second1 < 10) {secondPdf = "0"+String.valueOf(second1);}else {secondPdf = String.valueOf(second1);}	

			//System.out.println("testando aqui agora: "+ day+"/"+month+"/"+year);

			//assinatura
			document.add(new Paragraph("\n\n                "+trad.occLabels("Assinatura")+":"+ "______________________________________________."+"\n\n"
					+ "                                    "+trad.occLabels("Data do relatÛrio")+":  "+dayPdf+"/"+monthPdf+"/"+year1));

		}
		catch(DocumentException de) {
			System.err.println(de.getMessage());
		}
		catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		document.close();

		//getRowValue();

		String x = data.getState_occurrences();
		situation = Integer.parseInt(x);

		//chamando valores do usu·rio de outro controller
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	

		//pegando os valores de outro controller e armazenado dentro das variaveis: (userName e nivelUser)
		userName = (String) facesContext.getExternalContext().getSessionMap().get("user");
		nivelUser = (int) facesContext.getExternalContext().getSessionMap().get("nivel");

		//se a variavel situaÁ„o for igual a 30 ou 31 acessamos a essa condiÁ„o
		if(situation == 31 || situation == 30) {
			//btns
			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//executando funÁıes js
			RequestContext.getCurrentInstance().execute("msgDownload()");
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");

			//sen„o acessa a essa condiÁ„o
		}else if(userName.equals(data.getNameUser())){

			//btn
			save = true;
			alterar = true;
			edit = false;
			new_ = false;
			reset = true;
			fields = true; 

			//execute js
			RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
			RequestContext.getCurrentInstance().execute("fileTotal()");
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");
			RequestContext.getCurrentInstance().execute("msgDownload()");

			//sen„o se o nivel de acesso do usu·rio for igual a 1 ou igual a 6
			//tem permiss„o para acessar a condiÁ„o
		}else if((data.getEditTable() == false)) {

			//btn
			save = true;
			alterar = true;
			edit = false;
			new_ = false;
			reset = true;
			fields = true; 

			//execute js
			RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
			RequestContext.getCurrentInstance().execute("fileTotal()");
			RequestContext.getCurrentInstance().execute("msgDownload()");

			//sen„o se for igual a true acesso bloqueado para realizar ediÁ„o
		}else if((data.getEditTable() == true)) {

			//executando funÁ„o javascript

			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//se o nome do usuario local, for igual o nome da pessoa que esta editando
			//a ocorrencia acessa pode acessar a essa condiÁ„o
			if(userName.equals(data.getNameUser())){

				//btn
				save = true;
				alterar = true;
				edit = false;
				new_ = false;
				reset = true;
				fields = true; 

				//execute js
				RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
				RequestContext.getCurrentInstance().execute("fileTotal()");
				RequestContext.getCurrentInstance().execute("msgDownload()");

				//sen„o se o nivel de acesso do usu·rio for igual a 1 ou igual a 6
				//tem permiss„o para acessar a condiÁ„o
			}else {
				//btn menu
				save = true;
				alterar = true;
				reset = true;
				new_ = false;
				fields = true;
				edit = true;
				table = true;
				//executando funÁıes js
				RequestContext.getCurrentInstance().execute("msgDownload()");
				RequestContext.getCurrentInstance().execute("listUpdateFile2()");
			}
			int id = getValue();

			//criando caminho da seleÁ„o da pasta.
			way = pathSQL;

			//caminho criado
			fileWay = new File(mainPath+way);
			//System.out.println("Estamos aqui: > "+ fileWay);

			int y = 0;

			arquivos = fileWay.listFiles();

			tableFile = new String[arquivos.length];
			total = arquivos.length;
			while (y != arquivos.length){

				tableFile[y] = arquivos[y].getName();

				System.out.println("Position: "+y+" < Arquivo na pasta > "+ tableFile[y]);

				y++;

			}
		}
		//passando valor final do mÈtodo para a variavel tableFile
		return tableFile;

	}

}