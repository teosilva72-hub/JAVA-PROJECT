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
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import org.jboss.logging.Cause;
import org.snmp4j.util.TableListener;

import br.com.tracevia.webapp.controller.global.UserAccountBean;
import br.com.tracevia.webapp.dao.occ.OccurrencesDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
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

	private boolean save, edit, new_, reset, fields, table, alterar;

	private String action, title_modal, message_modal;

	private String mainPath, localPath, occNumber, path, way, downloadPath, pathDownload, pathSQL, monthPdf,
	minutePdf, secondPdf, dayPdf, hourPdf; 
	private String getFile, fileDelete, fileUpdate, pathImage, absoluteImage, imagePath;
	private String[] listarFile, listUpdate, tableFile, imagem, FileName;
	
	private File arquivos[], directory, fileWay;
	private int bytes, total, situation;
	private int value, value1, value2, content;
	private Part file = null;
	private Part file2 = null;
	private ImageIcon image;
	private int rowkey;
	private boolean selectedRow;
	private List<OccurrencesData> occurrences;

	private List<SelectItem> rodovias, estado_locais, sentidos, faixas, tipos, origens, estados, causa_provaveis, condicao_locais, 
	condicao_de_trafegos, caracteristica_da_secoes, interferencia_da_faixas, sinalizacoes, estado_condutores, horas, minutos, actions,
	actionState, trackInterrupted, damageType, damageUnity, involvedType, damage_gravity;

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
		//listingUpdate();
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

			horas = new  ArrayList<SelectItem>();

			for(int h = 0; h < 24; h++) {				

				if (h < 10)
					horas.add(new SelectItem("0"+String.valueOf(h), "0"+String.valueOf(h)));

				else 
					horas.add(new SelectItem(String.valueOf(h), String.valueOf(h)));

			}
			//Minutos
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

			edit = true;
			save = true;
			reset = true;
			new_ = false;
			fields = true;
			table = true;

			mainPath = "C:\\Tracevia\\";
			pathImage = "http://localhost:8081/occ/";
			downloadPath = "file:///C:/Tracevia/";
			pathDownload = "C:\\Users\\mateu\\Downloads\\";
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}	

	public int pegarId() throws Exception{

		int second = LocalDateTime.now().getSecond();
		int date = LocalDateTime.now().getYear();
		OccurrencesDAO x = new OccurrencesDAO();
		value = x.GetId();
		//String e = userId.getUser_id();
		value += (1+second);
		data = new OccurrencesData();
		data.setData_number(String.valueOf(value));
		//System.out.println(e+ "<<< testando aqui ahr");
		return value;

	}

	public void cadastroOcorrencia() throws Exception {

		boolean sucess = false;

		OccurrencesDAO dao = new OccurrencesDAO();
		occNumber = dao.cadastroOcorrencia(data);
		//int teste = Integer.parseInt(occNumber);
		//CREATE LOCAL PATH
		localPath = localPath(occNumber);

		if(occNumber != null) {

			//btn
			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;
			listarFile = null;
			total = 0;

			occurrences = dao.listarOcorrencias(); // List occurrences    
			sucess = dao.updateFilePath(localPath, occNumber); // Update path on Data Base
			//getRowValue();

		}

	}

	public void atualizarOcorrencia() throws Exception {

		boolean status = false;

		OccurrencesDAO dao = new OccurrencesDAO();

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

			org.primefaces.context.RequestContext.getCurrentInstance().execute("fileTotalHidden()");

			occurrences = dao.listarOcorrencias();
		}

	}

	public void resetOccurrencesData(){       

		data = new OccurrencesData();

		//btn
		save = true;
		alterar = true;
		reset = true;
		new_ = false;
		fields = true;
		edit = true;
		table = true;

		//var 
		listarFile = null;
		listUpdate = null;
		total = 0;

		deleteDirectory();
	}
	public void getRowValue() throws Exception {

		try {
			org.primefaces.context.RequestContext.getCurrentInstance().execute("displayPdf()");
			org.primefaces.context.RequestContext.getCurrentInstance().execute("listUpdateFile2()");
			OccurrencesDAO dao = new OccurrencesDAO();
			data = dao.buscarOcorrenciaPorId(rowkey);
			getPdf = dao.submitPdf(rowkey);
			pathSQL = data.getLocalFiles();
			String x = data.getState_occurrences();
			situation = Integer.parseInt(x);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//listando arquivos quando clica na linha da tabela
		TableFile();
		
		//Se a linha da table estiver selecionada:
		if(selectedRow ) {
			
			//se a situação for igual 30 ou 31
			//não é possivel fazer alteração
			if(situation == 31 || situation == 30) {

				save = true;
				alterar = true;
				reset = true;
				new_ = false;
				fields = true;
				edit = true;
				table = true;
				
				//listar arquivos
				
				//execute js
				org.primefaces.context.RequestContext.getCurrentInstance().execute("msgFinished()");
				org.primefaces.context.RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
				org.primefaces.context.RequestContext.getCurrentInstance().execute("fileTotal()");
				
				//senão pode relizar normalmente alterações
			}else {
				//btn
				save = true;
				alterar = true;
				edit = false;
				new_ = false;
				reset = true;
				fields = true; 
				//execute js
				org.primefaces.context.RequestContext.getCurrentInstance().execute("msgFinished()");
				org.primefaces.context.RequestContext.getCurrentInstance().execute("msgFinishedHidden()");
				org.primefaces.context.RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
				org.primefaces.context.RequestContext.getCurrentInstance().execute("fileTotal()");
			}

			//se não estiver selecionada a linha da tabela
		}else {

			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;
			
			org.primefaces.context.RequestContext.getCurrentInstance().execute("hiddenPdf()");
			org.primefaces.context.RequestContext.getCurrentInstance().execute("msgFinishedHidden()");
			org.primefaces.context.RequestContext.getCurrentInstance().execute("listingFileBtn()");
			org.primefaces.context.RequestContext.getCurrentInstance().execute("listingFile()");
			tableFile = null;
			total = 0;
		}

	}

	//Action NOVO
	public void btnEnable() throws Exception {

		org.primefaces.context.RequestContext.getCurrentInstance().execute("listUpdateFile1()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("disableEdit()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("hiddenPdf()");
		//Global
		value = pegarId();

		if(value > 0) {

			String occNumber = (String.valueOf(value));

			//CREATE LOCAL PATH
			localPath = localPath(occNumber);
			createFileFolder(mainPath, localPath);	
			
			org.primefaces.context.RequestContext.getCurrentInstance().execute("listingFile()");
			org.primefaces.context.RequestContext.getCurrentInstance().execute("disableEdit()");
			org.primefaces.context.RequestContext.getCurrentInstance().execute("resetForm()");
			org.primefaces.context.RequestContext.getCurrentInstance().execute("hiddenPdf()");
			org.primefaces.context.RequestContext.getCurrentInstance().execute("fileTotalHidden()");
			
			
			edit = true;
			save = false;
			alterar = true;
			reset = false;
			new_ = true;
			fields = false;
			action = "save";

		}

	}
	public void btnEdit() {
		
		//btn
		fields = false;
		reset = false;
		save = true;
		edit = true;
		alterar = false;
		
		//js
		org.primefaces.context.RequestContext.getCurrentInstance().execute("bloquerTable()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("listUpdateFile1()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("alterarBtn()");

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

	//CREATE METHOD TO ACTION UPLOAD FILE
	//RETURN VOID
	public void updateFile() throws IOException, ServletException{

		uploadBean up = new uploadBean();
		up.update(mainPath, way, file2);

		edit = true;
		save = false;
		alterar = true;
		reset = false;
		new_ = true;
		fields = false;

		listingUpdate();
		org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("bloquerTable()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("msgSaveFile()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("fileTotal1()");
	}

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

		listFiles();
		//volta para o form 2
		org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("bloquerTable()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("msgSaveFile()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("fileTotal()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("listUpdateFile1()");
	}

	public String[] listingUpdate() {
		
		org.primefaces.context.RequestContext.getCurrentInstance().execute("bloquerTable()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("listUpdateFile1()");
		
		//pega o id da tabela.
		int id = getValue();

		//criando caminho da seleÃ§Ã£o da pasta.
		way = pathSQL;

		//caminho criado
		fileWay = new File(mainPath+way);
		//System.out.println("Estamos aqui: > "+ fileWay);

		int x = 0;

		arquivos = fileWay.listFiles();

		listUpdate = new String[arquivos.length];
		total = arquivos.length;
		while (x != arquivos.length){

			listUpdate[x] = arquivos[x].getName();

			System.out.println("Position: "+x+" < Arquivo na pasta > "+ listUpdate[x]);

			x++;

		}
		

		return listUpdate;
	}
	public String[] TableFile() {
		
		String b = data.getState_occurrences();
		situation = Integer.parseInt(b);
		
		if(situation == 31 || situation == 30) {

			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;
			org.primefaces.context.RequestContext.getCurrentInstance().execute("msgFinished()");
		}
		
		org.primefaces.context.RequestContext.getCurrentInstance().execute("fileTotal1()");

		//pega o id da tabela.
		int id = getValue();

		//criando caminho da seleção da pasta.
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

		return tableFile;

	}
	public String[] listFiles() throws Exception{

		//variÃ¡vel do tipo int
		content = 0;

		//local do armazenamento do arquivo
		directory = new File(mainPath+path+"\\");

		//listar arquivos
		arquivos = directory.listFiles();

		//pega o total de arquivos no diretÃ³rio
		total = arquivos.length;
		System.out.println("Total Files: "+ total);

		listarFile = new String[arquivos.length];

		//lopping para pegar arquivos dentro do diretÃ³rio
		while (content != arquivos.length){
			//pega arquivo pelo nome
			listarFile[content] = arquivos[content].getName();		
			System.out.println(content+": Arquivo recebido "+ listarFile[content]);
			content++;

		} 

		return listarFile;
	}

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
	public String getImageUpdate(String myImg) throws Exception{

		//gerando o caminho onde se encontra a imagem
		int id = getValue();

		String imagePath = pathSQL+"/";

		absoluteImage = pathImage+imagePath+myImg;

		return absoluteImage;

	}

	public void deleteFileUpdate(String file) throws Exception {
		try {
			fileWay = new File(mainPath+way+"\\");
			System.out.println(fileWay+file);

			boolean check = new File(fileWay, file).exists();

			if(check) {

				File currentFile = new File(fileWay, file);

				currentFile.delete();

				System.out.println("Arquivo excluido com sucesso! ");
				
				save = true;
				alterar = false;
				edit = true;
				new_ = true;
				reset = false;
				fields = false;
				
				total = total - 1;
				
				org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
				org.primefaces.context.RequestContext.getCurrentInstance().execute("msgDelete()");
				org.primefaces.context.RequestContext.getCurrentInstance().execute("bloquerTable()");
				org.primefaces.context.RequestContext.getCurrentInstance().execute("alterarBtn()");
				
				listingUpdate();
			}

		}catch(Exception ex) {
			org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
			System.out.println("Erro ao excluir arquivo");
		}
		
	}
	public void deleteFile(String file){

		try {

			//caminho do diretorio e o arquivo que estou apagando
			directory = new File(mainPath+path+"\\");

			boolean check = new File(directory, file).exists();

			System.out.println(check +" \n DIR: "+directory+"\n File: "+file);

			if(check) {

				File currentFile = new File(directory, file);

				currentFile.delete();

				System.out.println("Arquivo excluido com sucesso! "+currentFile);
				org.primefaces.context.RequestContext.getCurrentInstance().execute("bloquerTable()");
				total -=  1;
				listFiles();

				org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
				org.primefaces.context.RequestContext.getCurrentInstance().execute("msgDelete()");
				org.primefaces.context.RequestContext.getCurrentInstance().execute("bloquerTable()");

			}

		}catch(Exception ex) {
			org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
			System.out.println("Erro ao excluir arquivo");
		}

	}
	//exclui pasta gerada do occ quando é clicado em anular
	public File deleteDirectory() {

		try {
			File folder = new File(mainPath+path+"\\");

			System.out.println("Pasta excluida: "+folder);

			if (folder.isDirectory()) {

				File[] files = folder.listFiles();

				for (File filesToDelete : files) {

					filesToDelete.delete();

				}

				//delete finally
				folder.delete();

				System.out.println("Diretorio deletado!");

			}

		}catch(Exception ex) {

			System.out.println("Erro ao excluir diretorio");
			org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
			
		}
		return null;
	}
	public void download(String fileName) throws Exception {

		//pegando o id
		OccurrencesDAO dao = new OccurrencesDAO();
		int id = dao.GetId();
		id += 1;
		data = new OccurrencesData();
		data.setData_number(String.valueOf(id));

		//chamando mÃªs e ano, para passar para o caminho.
		DateTimeApplication dddd = new DateTimeApplication();
		LocalDate data = dddd.localeDate();

		//criando caminho da seleÃ§Ã£o da pasta.
		String absoluteFile = data.getYear()+"/"+data.getMonthValue()+"/OCC_"+id+"/"+fileName;

		//caminho onde pegamos o arquivo
		URL url = new URL(downloadPath+absoluteFile);
		String arquivos = url.getQuery();

		//caminho onde o arquivo serÃ¡ guardado
		File file = new File(pathDownload+fileName);

		InputStream is = url.openStream();
		FileOutputStream fos = new FileOutputStream(file);

		int bytes = 0;

		while ((bytes = is.read()) != -1) {
			fos.write(bytes);
		}

		is.close();

		fos.close();
		org.primefaces.context.RequestContext.getCurrentInstance().execute("bloquerTable()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("msgDownload()");
		System.out.println("Download realizado: "+fileName);
	}
	public void downloadUpdate(String fileName) throws Exception {
		//js
		org.primefaces.context.RequestContext.getCurrentInstance().execute("bloquerTable()");	
		org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("msgDownload()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("alterarBtn()");
		//pegando o id
		int id = getValue();

		//criando caminho da seleÃ§Ã£o da pasta.
		String absoluteFile = pathSQL+"/"+fileName;

		//caminho onde pegamos o arquivo
		URL url = new URL(downloadPath+absoluteFile);
		String arquivos = url.getQuery();

		//caminho onde o arquivo serÃ¡ guardado
		File file = new File(pathDownload+fileName);

		InputStream is = url.openStream();
		FileOutputStream fos = new FileOutputStream(file);

		int bytes = 0;

		while ((bytes = is.read()) != -1) {
			fos.write(bytes);
		}

		is.close();

		fos.close();

		

		
		System.out.println("Download realizado: "+fileName);
	}
	public void downloadUpdateTable(String fileName) throws Exception {

		//pegando o id
		int id = getValue();

		//criando caminho da seleÃ§Ã£o da pasta.
		String absoluteFile = pathSQL+"/"+fileName;

		//caminho onde pegamos o arquivo
		URL url = new URL(downloadPath+absoluteFile);
		String arquivos = url.getQuery();

		//caminho onde o arquivo serÃ¡ guardado
		File file = new File(pathDownload+fileName);

		InputStream is = url.openStream();
		FileOutputStream fos = new FileOutputStream(file);

		int bytes = 0;

		while ((bytes = is.read()) != -1) {
			fos.write(bytes);
		}

		is.close();

		fos.close();

		if(situation == 31 || situation == 30) {

			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;
			
		}else {
			//btn
			save = true;
			alterar = true;
			edit = false;
			new_ = false;
			reset = true;
			fields = true; 
			
		}
		org.primefaces.context.RequestContext.getCurrentInstance().execute("listUpdateFile2()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("msgDownload()");
		System.out.println("Download realizado: "+fileName);
	}
	public void downloadPdf() throws Exception {
		// criação do documento
		Document document = new Document();

		try {
			//caminho onde é gerado o pdf
			PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\mateu\\Downloads\\"+"OCC_"+data.getData_number()+".pdf"));
			//gera o arquivo
			document.open();

			//formato da folha
			document.setPageSize(PageSize.A4);

			//Editando o tipo de fonte do titulo
			Paragraph pTitulo = new Paragraph(new Phrase(20F , "    "
					+ "              Informe de ocurrencia", FontFactory.getFont(FontFactory.HELVETICA, 25F)));
			Paragraph evento = new Paragraph(new Phrase(20F , "Eventos", FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph dateHour = new Paragraph(new Phrase(20F , "Fecha y Hora", FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph causeProbable = new Paragraph(new Phrase(20F , "Causa probable", FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph eventoLocal = new Paragraph(new Phrase(20F , "Evento Local", FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph detalhes = new Paragraph(new Phrase(20F , "Detalles", FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph description = new Paragraph(new Phrase(20F , "Descripción", FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph envolvidos = new Paragraph(new Phrase(20F , "Involucrados", FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph track = new Paragraph(new Phrase(20F , "Tráfico", FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph danos = new Paragraph(new Phrase(20F , "Daño", FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph action = new Paragraph(new Phrase(20F , "acción", FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			
			//chamando a imagem
			
			Image image1 = Image.getInstance("C:\\Users\\mateu\\eclipse-workspace\\tracevia-application\\src\\main\\webapp\\resources\\images\\home\\traceviaLayout.png");
			Image image2 = Image.getInstance("C:\\Users\\mateu\\eclipse-workspace\\tracevia-application\\src\\main\\webapp\\resources\\images\\home\\tuxpan.png");
			
			System.out.println(RoadConcessionaire.externalImagePath);
			//edição das imagens
			image1.setAbsolutePosition(50, 790);
			image1.scaleAbsolute (100, 50);
			image2.setAbsolutePosition(420, 800);
			image2.scaleAbsolute (120, 30);
			//passando a imagem
			document.add(image1);
			document.add(image2);
			
			document.add(new Paragraph("\n"));
			//add titulo
			document.add(new Paragraph(pTitulo));
			document.add(new Paragraph("\n"));

			//primeiro paragrafo Evento
			Rectangle event1 = new Rectangle(577, 685, 10, 750); // you can resize rectangle 
			event1.enableBorderSide(1);
			event1.enableBorderSide(2);
			event1.enableBorderSide(4);
			event1.enableBorderSide(8);
			event1.setBorderColor(BaseColor.BLACK);
			event1.setBorderWidth(1);
			document.add(event1);
			document.add(new Paragraph(evento+"\n"+"\n"));
			//document.add(new Paragraph(" "));
			document.add(new Paragraph("Occ Nº: "+data.getData_number()+"        "
					+ "Tipo: "+ getPdf.getType()+"         "
					+ "Fuente: "+getPdf.getOrigin()+"          "
					+ "Situación: "+getPdf.getState_occurrences()+"\n\n"));

			//data e hora inivial
			Rectangle dateHourStart = new Rectangle(577, 615, 10, 680); // you can resize rectangle 
			dateHourStart.enableBorderSide(1);
			dateHourStart.enableBorderSide(2);
			dateHourStart.enableBorderSide(4);
			dateHourStart.enableBorderSide(8);
			dateHourStart.setBorderColor(BaseColor.BLACK);
			dateHourStart.setBorderWidth(1);
			document.add(dateHourStart);
			document.add(new Paragraph(dateHour+"\n"+"\n"));
			//data e hora inicial
			document.add(new Paragraph("Inicial: "+data.getStart_date()+"             Inicial: "+data.getStart_hour()+":"+data.getStart_minute()+"             "
					+ "Final: "+data.getEnd_date()+"             Final: "+data.getEnd_hour()+":"+data.getEnd_minute()+"\n\n"));

			//causa provável e descrição principal e interna.
			Rectangle causePr= new Rectangle(577, 470, 10, 610); // you can resize rectangle 
			causePr.enableBorderSide(1);
			causePr.enableBorderSide(2);
			causePr.enableBorderSide(4);
			causePr.enableBorderSide(8);
			causePr.setBorderColor(BaseColor.BLACK);
			causePr.setBorderWidth(1);
			document.add(causePr);
			document.add(new Paragraph(causeProbable+"\n"+"\n"));
			document.add(new Paragraph("Causa: "+getPdf.getCause()+"\n\n"));
			document.add(new Paragraph("Descripción: "+data.getCause_description()+"\n\n"));
			document.add(new Paragraph("Descripción interna: "+data.getCauseDescrInter()+"\n\n"));

			//Evento Local
			Rectangle eventL= new Rectangle(577, 360, 10, 465); // you can resize rectangle 
			eventL.enableBorderSide(1);
			eventL.enableBorderSide(2);
			eventL.enableBorderSide(4);
			eventL.enableBorderSide(8);
			eventL.setBorderColor(BaseColor.BLACK);
			eventL.setBorderWidth(1);
			document.add(eventL);
			document.add(new Paragraph(eventoLocal+"\n"+"\n"));
			document.add(new Paragraph(""));
			document.add(new Paragraph("KM: "+data.getKilometer()+"            "
					+ "Autopista: "+getPdf.getHighway()+"            "
					+ "Estado: "+getPdf.getLocal_state()+"\n\n"));
			document.add(new Paragraph("Dirección: "+getPdf.getDirection()+"            "
					+ "Carril: "+getPdf.getLane()+"            "
					+ "Observación: "+data.getOthers()+"\n\n"));

			//Detalhes
			Rectangle details= new Rectangle(577, 255, 10, 355); // you can resize rectangle 
			details.enableBorderSide(1);
			details.enableBorderSide(2);
			details.enableBorderSide(4);
			details.enableBorderSide(8);
			details.setBorderColor(BaseColor.BLACK);
			details.setBorderWidth(1);
			document.add(details);
			document.add(new Paragraph(detalhes+"\n"+"\n"));
			document.add(new Paragraph(""));
			document.add(new Paragraph("Condición local: "+getPdf.getLocal_condition()+"     "
					+ "Condición del tráfico: "+getPdf.getTraffic()+"      "
					+ "Característica: "+getPdf.getCharacteristic()+"\n\n"));
			document.add(new Paragraph("Interference: "+getPdf.getInterference()+"     "
					+"Signaling: "+getPdf.getSignaling()+"     "
					+"Condición conductor: "+ getPdf.getConductor_condition()+"\n\n"));

			//descrição
			Rectangle descriptions= new Rectangle(577, 110, 10, 250); // you can resize rectangle 
			descriptions.enableBorderSide(1);
			descriptions.enableBorderSide(2);
			descriptions.enableBorderSide(4);
			descriptions.enableBorderSide(8);
			descriptions.setBorderColor(BaseColor.BLACK);
			descriptions.setBorderWidth(1);
			document.add(descriptions);
			document.add(new Paragraph(description+"\n"+"\n"));
			document.add(new Paragraph("Descripción del Título: "+ data.getDescription_title()+"\n\n"));
			document.add(new Paragraph("Descripción: "+data.getDescription_text()+"\n\n"));
			document.add(new Paragraph("Descripción interna: "+data.getDescriptionInter()+"\n\n"));

			document.add(new Paragraph("\n\n                                                                        Pág. 1"));
			//adicionando nova pagina
			document.newPage();

			//Envolvidos
			Rectangle envolvido = new Rectangle(577, 670, 10, 810); // you can resize rectangle 
			envolvido.enableBorderSide(1);
			envolvido.enableBorderSide(2);
			envolvido.enableBorderSide(4);
			envolvido.enableBorderSide(8);
			envolvido.setBorderColor(BaseColor.BLACK);
			envolvido.setBorderWidth(1);
			document.add(envolvido);
			document.add(new Paragraph(envolvidos+"\n"+"\n"));
			document.add(new Paragraph("Tipo: "+getPdf.getInvolved_type()+"\n\n"));
			document.add(new Paragraph("Descripción: "+ data.getInvolved_description()+"\n\n"));
			document.add(new Paragraph("Descripción interna: "+ data.getInvolvedInter()+"\n\n"));

			//Trânsito
			Rectangle track1 = new Rectangle(577, 560, 10, 665); // you can resize rectangle 
			track1.enableBorderSide(1);
			track1.enableBorderSide(2);
			track1.enableBorderSide(4);
			track1.enableBorderSide(8);
			track1.setBorderColor(BaseColor.BLACK);
			track1.setBorderWidth(1);
			document.add(track1);
			document.add(new Paragraph(track+"\n"+"\n"));
			document.add(new Paragraph("Inicial: " + data.getTrackStartDate()+ "             Inicial: " + data.getTrackStartHour() + ":" + data.getTrackStartMinute() + "             "
					+ "Final: " + data.getTrackEndDate() + "             Final: " + data.getTrackEndHour() + ":"+data.getTrackEndMinute() + "\n\n"));
			document.add(new Paragraph());
			document.add(new Paragraph("Extensión: "+data.getTraffic_extension()+"            "
					+"Pista interrumpida: "+ getPdf.getTraffic_stopped()+"\n\n"));

			//Danos
			Rectangle damage1 = new Rectangle(577, 420, 10, 555); // you can resize rectangle 
			damage1.enableBorderSide(1);
			damage1.enableBorderSide(2);
			damage1.enableBorderSide(4);
			damage1.enableBorderSide(8);
			damage1.setBorderColor(BaseColor.BLACK);
			damage1.setBorderWidth(1);
			document.add(damage1);
			document.add(new Paragraph(danos+"\n"+"\n"));
			document.add(new Paragraph(""));
			document.add(new Paragraph("Tipo: "+getPdf.getDamage_type_damage()+"     Gravedad: "+getPdf.getDamage_gravity()+"     Unidad: "+getPdf.getDamageUnity()
			+"     Amount: "+data.getDamage_amount()+ "\n\n"));
			document.add(new Paragraph("Descripción: "+data.getDemage_description()+ "\n\n"));
			document.add(new Paragraph("Descripción interna: "+data.getDamageDescriptionInternal()+"\n\n"));

			//ação
			Rectangle action1 = new Rectangle(577, 225, 10, 415); // you can resize rectangle 
			action1.enableBorderSide(1);
			action1.enableBorderSide(2);
			action1.enableBorderSide(4);
			action1.enableBorderSide(8);
			action1.setBorderColor(BaseColor.BLACK);
			action1.setBorderWidth(1);
			document.add(action1);
			document.add(new Paragraph(action+"\n"+"\n"));

			document.add(new Paragraph("Tipo: "+getPdf.getAction_type()+"             Situación: "+getPdf.getStatusAction()+"\n\n"));
			document.add(new Paragraph("Inicial: "+data.getActionStartData()+"     Inicial: "+data.getActionStartHour()+":"+data.getActionStartMinute()
			+"             Final: "+data.getActionEndData()+"             Final: "+data.getActionEndHour()+":"+data.getActionEndMinute()+"\n\n"));
			document.add(new Paragraph("Descripción: "+data.getAction_description()+"\n\n"));
			document.add(new Paragraph("Descripción interna: "+data.getActionInter()+"\n\n"));

			//chamando data e hora
			int day1 = LocalDateTime.now().getDayOfMonth();
			int year1 = LocalDateTime.now().getYear();
			int month1 = LocalDateTime.now().getMonthValue();
			int hour1 = LocalDateTime.now().getHour();
			int minute1 = LocalDateTime.now().getMinute();
			int second1 = LocalDateTime.now().getSecond();
			
			if(day1 < 10) {dayPdf = "0"+String.valueOf(day1);}else {dayPdf = String.valueOf(day1);}
			if(hour1 < 10) {hourPdf = "0"+String.valueOf(hour1);}else {hourPdf = String.valueOf(hour1);}
			if(month1 < 10) {monthPdf = "0"+String.valueOf(month1);}else {monthPdf = String.valueOf(month1);}
			if(minute1 < 10) {minutePdf = "0"+String.valueOf(minute1);}else {minutePdf = String.valueOf(minute1);}
			if(second1 < 10) {secondPdf = "0"+String.valueOf(second1);}else {secondPdf = String.valueOf(second1);}	
			
			//System.out.println("testando aqui agora: "+ day+"/"+month+"/"+year);

			//assinatura
			document.add(new Paragraph("\n\n                    Firma: ______________________________________________."+ "\n\n\n\n"
					+ "                              fecha del informe: "+dayPdf+"/"+monthPdf+"/"+year1+"     Hora: "+hourPdf+":"+minutePdf+":"+secondPdf));
			document.add(new Paragraph("\n\n                                                                        Pág. 2"));

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
		
		if(situation == 31 || situation == 30) {

			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;
			org.primefaces.context.RequestContext.getCurrentInstance().execute("msgDownload()");
			org.primefaces.context.RequestContext.getCurrentInstance().execute("hiddenPdf()");
		}else {
			//btn menu
			edit = false;
			save = true;
			reset = true;  
			new_ = false;
			fields = true;
			table = true;

			org.primefaces.context.RequestContext.getCurrentInstance().execute("msgDownload()");
			org.primefaces.context.RequestContext.getCurrentInstance().execute("hiddenPdf()");
		}
		
			
	}

}