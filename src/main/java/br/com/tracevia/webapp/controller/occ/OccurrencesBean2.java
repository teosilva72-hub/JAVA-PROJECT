package br.com.tracevia.webapp.controller.occ;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import javax.swing.ImageIcon;

import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.primefaces.component.tabview.Tab;
import org.primefaces.context.RequestContext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.tracevia.webapp.controller.dai.DaiBean;
import br.com.tracevia.webapp.controller.dai.DaiBean.Traffic;
import br.com.tracevia.webapp.controller.global.UserAccountBean;
import br.com.tracevia.webapp.dao.occ.OccurencesDao2;
import br.com.tracevia.webapp.dao.occ.OccurrencesDAO;
import br.com.tracevia.webapp.dao.occ.OccurrencesDAO2;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.occ.OccurrencesData;
import br.com.tracevia.webapp.model.occ.OccurrencesData2;
import br.com.tracevia.webapp.model.occ.OccurrencesDetails;
import br.com.tracevia.webapp.model.sos.SOS;
import br.com.tracevia.webapp.model.sos.SosData;
import br.com.tracevia.webapp.util.LocaleUtil;

@ManagedBean(name="occurrencesBean2")
@ViewScoped
public class OccurrencesBean2 {

	private OccurrencesData2 data;
	private OccurrencesData2 getPdf;
	private UserAccountBean userId;
	private OccurrencesDetails details;

	OccurrencesDAO2 dao;
	LocaleUtil occLabel, occMessages;



	private boolean save, edit, new_, reset, fields, enableBtn,
	table, alterar, pdf;
	String img_dai;
	private String logo, userPdf, externalId, externalType, dataDai, equip_id, id_get_rodov;


	public String getImg_dai() {
		return img_dai;
	}
	public void setImg_dai(String img_dai) {
		this.img_dai = img_dai;
	}
	public String getId_get_rodov() {
		return id_get_rodov;
	}
	public void setId_get_rodov(String id_get_rodov) {
		this.id_get_rodov = id_get_rodov;
	}
	public String getEquip_id() {
		return equip_id;
	}
	public void setEquip_id(String equip_id) {
		this.equip_id = equip_id;
	}
	public String getDataDai() {
		return dataDai;
	}
	public void setDataDai(String dataDai) {
		this.dataDai = dataDai;
	}
	public String getExternalType() {
		return externalType;
	}
	public void setExternalType(String externalType) {
		this.externalType = externalType;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getUserPdf() {
		return userPdf;
	}
	public void setUserPdf(String userPdf) {
		this.userPdf = userPdf;
	}
	public String getLogo() {
		try {

			Path path = Paths.get(logo);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		} catch (IOException e) {
			return "";
		}
	}

	private String action, title_modal, message_modal, idUpdate;

	private String mainPath, localPath, occNumber, path, way, downloadPath, pathDownload, pathSQL, monthPdf,
	minutePdf, secondPdf, dayPdf, hourPdf, numveiculo, marca, tipo_veic , modelo, cor, placa, telefone, nameUser, userName; 
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
	private List<OccurrencesData2> occurrences;



	public String getNumveiculo() {
		return numveiculo;
	}
	public void setNumveiculo(String numveiculo) {
		this.numveiculo = numveiculo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getTipo_veic() {
		return tipo_veic;
	}
	public void setTipo_veic(String tipo_veic) {
		this.tipo_veic = tipo_veic;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

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

	public boolean isEnableBtn() {
		return enableBtn;
	}
	public void setEnableBtn(boolean enableBtn) {
		this.enableBtn = enableBtn;
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
	public OccurrencesData2 getData() {
		return data;
	}
	public void setData(OccurrencesData2 data) {
		this.data = data;
	}
	public OccurrencesDetails getDetails() {
		return details;
	}
	public void setDetails(OccurrencesDetails details) {
		this.details = details;
	}
	public List<OccurrencesData2> getOccurrences() {
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
		occurrences = new ArrayList<OccurrencesData2>();
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

			OccurrencesDAO2 dao = new OccurrencesDAO2();
			data = new OccurrencesData2();

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

			for(int h = 0; h < 12; h++) {				
				if(h < 10)
					horas.add(new SelectItem("0"+String.valueOf(h), "0"+String.valueOf(h)));
				else
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

			data = new  OccurrencesData2();


			//occurrences = dao.listarOcorrencias();


			//initialize btns
			edit = true;
			save = true;
			reset = true;
			new_ = false;
			fields = true;
			table = true;
			pdf = true;

			//paths
			mainPath = "C:\\Occurrences\\";			
			downloadPath = "file:///C:/Occurrences/";
			pathDownload = System.getenv("USERPROFILE") + "\\Downloads\\";

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}	
	public String equipDirection() throws Exception {
		OccurrencesDAO dir = new OccurrencesDAO();
		OccurrencesData x = dir.direction(getEquip_id());
		RequestContext.getCurrentInstance().execute(String.format("getDirection('%s')", x.getDirection()));
		return "";
	}
	public String getRodovia() {
		OccurrencesDAO rod = new OccurrencesDAO();
		OccurrencesData x = rod.rodovia(getId_get_rodov());
		RequestContext.getCurrentInstance().execute(String.format("getRodivia('%s')", x.getHighway()));
		return "";
	}
	public String externalID() throws Exception {
		OccurrencesDAO sos = new OccurrencesDAO();
		SOS x = sos.getExternal(getExternalId(), getExternalType());
		if(externalType.equals("sos")) {
			RequestContext.getCurrentInstance().execute(String.format("setType('24', '%s', '%s', '%s', '%s', '%s')",
					x.getStart_data(), x.getKm(), x.getDirection(), x.getEstrada(), x.getNome()));
		}else if(externalType.equals("dai")) {
			//object
			DaiBean dai = new DaiBean();
			//passar data metodo
			SimpleDateFormat date_parse = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			Date date = date_parse.parse(getDataDai());
			dai.getSpecificFile(date);
			//objetos traffic
			Traffic traffic = dai.traffic;

			//js
			RequestContext.getCurrentInstance().execute(String.format("setTypeDai('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
					traffic.incident, traffic.date, traffic.hour, traffic.direction,
					traffic.km, traffic.channel, traffic.getName(), traffic.getEquipId(), traffic.getPath()));

			String getFile = traffic.getFile().toAbsolutePath().toString();
			//System.out.println(getFile);
			File list = new File(getFile);
			img_dai = list.getAbsolutePath();
			//RequestContext.getCurrentInstance().execute(String.format("saveImgDaiOcc('%s')", img_dai));
		}


		return img_dai;
	}

	public void copyImgDai() throws Exception {
		String img = getImg_dai();
		String occ_path = "C:\\Occurrences\\";

		OccurrencesDAO dao = new OccurrencesDAO();
		int id = dao.GetId();

		DateTimeApplication dta = new DateTimeApplication();
		LocalDate local = dta.localeDate();

		String absolutePath = occ_path+local.getYear()+"\\"+local.getMonthValue()+"\\"+"OCC_"+id+"\\";

		String  sourcePath = img;   // source file path
		File sourceFile = new File(sourcePath);        // Creating A Source File
		File destinationFile = new File(absolutePath+sourceFile.getName());   //Creating A Destination File. Name stays the same this way, referring to getName()
		try 
		{
			//Thread.sleep(2000);
			Files.copy(sourceFile.toPath(), destinationFile.toPath());
			// Static Methods To Copy Copy source path to destination path
		} catch(Exception e)
		{
			System.out.println(e + " < error");  // printing in case of error.
		}

		//File destinationFile = new File(path+sourceFile.getName());
	}
	//taking the last id from the database and passing 1 more
	public int pegarId() throws Exception{

		OccurrencesDAO2 x = new OccurrencesDAO2();
		value = x.GetId();
		//String e = userId.getUser_id();
		value += 1;
		data = new OccurrencesData2();
		data.setData_number(String.valueOf(value));

		return value;
	}

	//register occurrences
	public void cadastroOcorrencia() throws Exception {
		boolean sucess = false;

		OccurrencesDAO2 dao = new OccurrencesDAO2();
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
			//occurrences = dao.listarOcorrencias(); // List occurrences    
			sucess = dao.updateFilePath(localPath, occNumber); // Update path on Data Base

		}

	}

	//update occurences
	public void atualizarOcorrencia() throws Exception {

		OccurrencesDAO2 dao = new OccurrencesDAO2();

		//ao atualizar a ocorrencia passamos um valor null para a variavel (nameUser),
		//passos 0 para a variavel (accessLevel) e para a variavel (updateTable) passamos false
		// ambos os valores serÃ£o armazenados no BD a fazer a requisiÃ§Ã£o
		String nameUser = ""; int accessLevel = 0; boolean updateTable = false; 
		System.out.println(data.getData_number()+"DataNumber123");
		System.out.println(data.getStart_hour()+" hora" );
		idUpdate = data.getData_number();
		//Passando dados definidos para o banco de dados
		dao.editTable(updateTable, nameUser, accessLevel, data.getData_number());

		//passando falso para acessar a condiÃ§Ã£o IF
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
			//executando funÃ§Ã£o javascript
			RequestContext.getCurrentInstance().execute("resetForm()");
			RequestContext.getCurrentInstance().execute("fileTotalHidden()");
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");


			//listar ocorrencia
			//occurrences = dao.listarOcorrencias();

			//listar arquivos sem direito a modificaÃ§Ã£o
			//TableFile();

		}
		resetUpdate();

	}

	//metodo anular ocorrencia, esse metodo Ã© chamado quando estamos na sessÃ£o novo
	public void resetOccurrencesData(){       

		data = new OccurrencesData2();
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
	//metodo anular ocorrencia, esse metodo Ã© chamado quando estamos na sessÃ£o editar
	public void resetUpdate() throws Exception{
		OccurrencesDAO2 dao = new OccurrencesDAO2();

		//ao anular a ocorrencia passamos os valores predeterminados para a seguintes variaveis:
		//(nameUser = null, accessLevel = 0 e updateTable = False) ambos os valores sÃ£o armazenados no BD.
		String nameUser = "";
		int accessLevel = 0;
		boolean updateTable = false;
		pdf = false;

		//passando dados para o banco de dados
		dao.editTable(updateTable, nameUser, accessLevel, data.getData_number());

		//atualizar a ocorrencia depois que o metodo for chamado
		//occurrences = dao.listarOcorrencias();
		//chamando função javascript
		RequestContext.getCurrentInstance().execute("eventValidator()");

		data = new OccurrencesData2();

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

	Timestamp timestamp = null;
	Timestamp timestamp2 = null;

	public void getRowValue() throws Exception {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.MINUTE, -5);
		Date b = calendar.getTime();
		System.out.println(rowkey);
		System.out.println("rowkey pdf" + rowkey);


		try {

			//chamando valores do usuÃ¡rio de outro controller
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();	

			//pegando os valores de outro controller e armazenado dentro das variaveis: (userName e nivelUser)
			userName = (String) facesContext.getExternalContext().getSessionMap().get("user");
			nivelUser = (int) facesContext.getExternalContext().getSessionMap().get("nivel");

			//executando funÃ§Ã£o javascript
			RequestContext.getCurrentInstance().execute("displayPdf()");
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");

			OccurrencesDAO2 dao = new OccurrencesDAO2();

			//buscar dados por id
			data = dao.buscarOcorrenciaPorId(rowkey);

			//buscar dados pdf
			//getPdf = dao.submitPdf(rowkey);

			//buscar caminho dos arquivos 
			pathSQL = data.getLocalFiles();

			//status da occorencia
			String x = data.getState_occurrences();

			//transformando em int
			situation = Integer.parseInt(x);
			System.out.println(data.getType()+ "getRowValue");

			//Date parsedDate = dateFormat.parse(data.getLastDateHour());
			//timestamp = new java.sql.Timestamp(parsedDate.getTime());
			//timestamp2 = new java.sql.Timestamp(b.getTime());
			//			System.out.println(timestamp +" <");
			//			System.out.println(timestamp2 + "<<");
			//			System.out.println((timestamp.after(timestamp2)+" antes"));
			//			System.out.println((timestamp.before(timestamp2)+" depois"));
			//			System.out.println(data.getEditTable()+" editTable");
		}catch(Exception ex){

			ex.printStackTrace();

		}
		//listando arquivos quando clica na linha da tabela
		//TableFile();
		pdf = false;
		//Se a linha da table estiver selecionada:
		if(selectedRow) {

			//se a situaÃ§Ã£o for igual 30 ou 31
			//nÃ£o Ã© possivel fazer alteraÃ§Ã£o
			if(situation == 31 || situation == 30) {

				//btn
				save = true;
				alterar = false;
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
				}

				//senÃ£o se for igual a false acesso liberado para realizar ediÃ§Ã£o
			}else if((data.getEditTable() == false)|| timestamp.before(timestamp2)) {

				//btn
				save = true;
				alterar = true;
				edit = false;
				new_ = false;
				reset = true;
				fields = true; 
				System.out.println("aqui3");
				//execute js
				RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
				RequestContext.getCurrentInstance().execute("fileTotal()");

				//senÃ£o se for igual a true acesso bloqueado para realizar ediÃ§Ã£o
			}else if((data.getEditTable() == true && timestamp.after(timestamp2))) {

				//executando funÃ§Ã£o javascript
				RequestContext.getCurrentInstance().execute("msgUser()");

				save = false;
				alterar = false;
				reset = true;
				new_ = true;
				fields = true;
				edit = false;
				table = true;
				System.out.println("aqui4");
				//se o nome do usuario local, for igual o nome da pessoa que esta editando
				//a ocorrencia acessa pode acessar a essa condiÃ§Ã£o
				if(userName.equals(data.getNameUser())){

					//btn
					save = true;
					alterar = true;
					edit = false;
					new_ = false;
					reset = true;
					fields = true; 
					System.out.println("aqui5");
					//execute js
					RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
					RequestContext.getCurrentInstance().execute("fileTotal()");

					//senÃ£o se o nivel de acesso do usuÃ¡rio for igual a 1 ou igual a 6
					//tem permissÃ£o para acessar a condiÃ§Ã£o
				}else if(nivelUser == 1 || nivelUser == 6) {

					//btn
					save = true;
					alterar = true;
					edit = false;
					new_ = false;
					reset = true;
					fields = true; 
					System.out.println("aqui5");
					//execute js
					RequestContext.getCurrentInstance().execute("hiddenBtnIcon()");
					RequestContext.getCurrentInstance().execute("fileTotal()");
				}

			}

			//mÃ©todo nÃ£o estÃ¡ sendo usado
			//senÃ£o estiver selecionada a linha da tabela
		}else {

			save = true;
			alterar =true;
			reset = true;
			new_ = true;
			fields = true;
			edit = false;
			table = true;
			System.out.println("aqui6");
			//executando as funções javascript
			RequestContext.getCurrentInstance().execute("hiddenPdf()");
			RequestContext.getCurrentInstance().execute("msgFinishedHidden()");
			RequestContext.getCurrentInstance().execute("listingFileBtn()");
			RequestContext.getCurrentInstance().execute("listingFile()");

		}
		//zerando as variaveis
		listarFile = null;
	}
	//mÃ©todo novo
	public void btnEnable() throws Exception {

		//executando as funÃ§Ãµes javascript
		RequestContext.getCurrentInstance().execute("inputs()");
		RequestContext.getCurrentInstance().execute("disableEdit()");
		RequestContext.getCurrentInstance().execute("listUpdateFile1()");
		RequestContext.getCurrentInstance().execute("uploadFile()");
		RequestContext.getCurrentInstance().execute("hiddenListFile()");
		RequestContext.getCurrentInstance().execute("hiddenPdf()");
		//pegando o valor da ultima variavel do vanco de dados
		value = pegarId(); // pegarId() + 1
		//se o valor for maior do que 0 acessamos a condiÃ§Ã£o
		if(value > 0) {

			//executando o mÃ©todo cadastrar, ou seja, quando acessamos esse mÃ©todo
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
			edit = false;
			save = false;
			alterar = true;
			reset = false;
			new_ = true;
			fields = false;
			action = "save";
			pdf = true;
		}

	}
	//mÃ©todo editar ocorrencia
	public void btnEdit() throws Exception {

		OccurrencesDAO2 dao = new OccurrencesDAO2();
		System.out.println("btnedit");
		//pegando valores dos usuários de outro controller
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		//passando os valores predeterminados para as variveis
		String nameUser = (String) facesContext.getExternalContext().getSessionMap().get("user");
		int nivelUser = (int) facesContext.getExternalContext().getSessionMap().get("nivel");
		boolean updateTable = true;
		//passando valores das varaveis para o banco de dados
		dao.editTable(updateTable, nameUser, nivelUser, data.getData_number());			
		enableBtn = true;
		//btn
		fields = false;
		reset = false;
		save = false		;
		edit = true;
		alterar = false;
		pdf = true;

		//executando funÃ§Ãµes javascript
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("btnEnable();");
		request.execute("inputs()");
		RequestContext.getCurrentInstance().execute("alterBtnReset()");
		RequestContext.getCurrentInstance().execute("bloquerTable()");
		RequestContext.getCurrentInstance().execute("listUpdateFile1()");
		RequestContext.getCurrentInstance().execute("alterarBtn()");
		RequestContext.getCurrentInstance().execute("atualizarTela()");

		//listando arquivos
		//	listingUpdate();

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

	//mÃ©todo para enviar arquivos na tela de atualizaÃ§Ã£o de cadastro
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

		//executando funÃ§Ãµes javascript
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("btnEnable();");
		RequestContext.getCurrentInstance().execute("mostrarTab2()");
		RequestContext.getCurrentInstance().execute("bloquerTable()");
		RequestContext.getCurrentInstance().execute("msgSaveFile()");
		RequestContext.getCurrentInstance().execute("fileTotal1()");
		RequestContext.getCurrentInstance().execute("alterarBtn()");



	}

	//mÃ©todo enviando
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

	//mÃ©todo listar arqivos ao atualizar
	public String[] listingUpdate() {

		//executar javascript
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("btnEnable();");
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

		//criando caminho da seleÃƒÂ§ÃƒÂ£o da pasta.
		way = pathSQL;

		//caminho criado
		fileWay = new File(mainPath+way);
		//iniciando variavel com valor 0
		int x = 0;

		//listando os arquivos
		//arquivos = fileWay.listFiles();
		//listUpdate = new String[arquivos.length];
		//pegando o total de aruivos dentro do diretorio
		total = arquivos.length;
		//enquantos os arquivos forem diferentes do valor 0 executa
		while (x != arquivos.length){

			//pegando os nomes do arquivos na posiÃ§Ã£o
			listUpdate[x] = arquivos[x].getName();

			System.out.println("Position: "+x+" < Arquivo na pasta > "+ listUpdate[x]+ "pdf");

			x++;

		}

		//retornando o valor do mÃ©todo listUpdate == nome do arquivo
		return listUpdate;
	}
	//mÃ©todo listar arquivos quando clicamos na tablea
	public String[] TableFile() {

		//pegando o status da ocorrÃªncia		
		String b = data.getState_occurrences();
		//tranformando o valor do status da ocorrÃªncia  em inteiro
		situation = Integer.parseInt(b);
		pdf = false;
		//se o status da occorencia for igual a 30 ou 31, acessamos esse funÃ§Ã£o
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

			//se o nivel de acesso dop usuÃ¡rio for igual a 1 ou igual a 6, acessamos a funÃ§Ã£o
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
			//senão se o valor do atributo editTable for igual 0 (false), acessos a condição
		}else if(data.getEditTable() == false || timestamp.before(timestamp2)) {

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

			//senÃ£o se for igual a true acesso bloqueado para realizar ediÃ§Ã£o
		}else if(data.getEditTable() == true && timestamp.after(timestamp2)) {

			RequestContext.getCurrentInstance().execute("msgUser()");
			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//se o nome do usuÃ¡rio for igual o nome armazenado no atributo nameUser, acessamos a condiÃ§Ã£o
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

				//senÃ£o se o nivel de acesso do usuÃ¡rio for igual a 1 ou igual a 6, acessamos a condiÃ§Ã£o
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

		//criando caminho da seleÃ§Ã£o da pasta.
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

			x++;

		}
		//passando valor final do mÃ©todo para a variavel tableFile
		return tableFile;

	}

	//mÃ©todo listar arquivos
	public String[] listFiles() throws Exception{

		//variÃƒÂ¡vel do tipo int
		content = 0;

		//local do armazenamento do arquivo
		directory = new File(mainPath+path+"\\");

		//listar arquivos
		arquivos = directory.listFiles();

		//pega o total de arquivos no diretÃƒÂ³rio
		total = arquivos.length;
		System.out.println("Total Files: "+ total );

		listarFile = new String[arquivos.length];

		//lopping para pegar arquivos dentro do diretÃƒÂ³rio
		while (content != arquivos.length){
			//pega arquivo pelo nome
			listarFile[content] = arquivos[content].getName();		
			content++;

		} 
		//passando valor do mÃ©todo para a variÃ¡vel
		return listarFile;
	}

	//esse mÃ©todo nÃ£o esta sendo aplicado, estÃ¡ aqui para futuros testes
	//Buscando imagem

	public String getImageUpload(String myImg) throws Exception{
		//gerando o caminho onde se encontra a imagem
		OccurrencesBean2 pegar= new OccurrencesBean2();
		int id = pegar.pegarId() - 1;
		DateTimeApplication dddd = new DateTimeApplication();
		LocalDate data = dddd.localeDate();
		String imagePath = data.getYear()+"/"+data.getMonthValue()+"/"+"OCC_"+id+"/";

		absoluteImage = pathImage+imagePath+myImg;

		return absoluteImage;

	}
	//esse mÃ©todo nÃ£o esta sendo usado, estÃ¡ aqui para testes
	public String getImageUpdate(String myImg) throws Exception{

		//gerando o caminho onde se encontra a imagem
		int id = getValue();

		String imagePath = pathSQL+"/";

		absoluteImage = pathImage+imagePath+myImg;

		return absoluteImage;

	}

	//mÃ©todo deletar arquivos na atualizaÃ§Ã£o do cadastro
	public void deleteFileUpdate(String file) throws Exception {
		//tentar
		try {
			//criando o camingo onde os arquivos sÃ£o armazenados
			fileWay = new File(mainPath+way+"\\");

			//buscando arquivos para ver se existem dentro do diretÃ³rio
			boolean check = new File(fileWay, file).exists();

			//se existir arquivos dentro da pasta, acessamos a condiÃ§Ã£o
			if(check) {
				RequestContext request = RequestContext.getCurrentInstance();
				request.execute("btnEnable();");
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
				listingUpdate();
				//listando arquivos
			}

			//capturar 
		}catch(Exception ex) {
			//executando funÃ§Ã£o javascript
			RequestContext.getCurrentInstance().execute("mostrarTab2()");
			//msg de erro ao deletar file
		}

	}
	//mÃ©todo deletar arquivos quando estamos na tela de novo cadastri
	public void deleteFile(String file){
		//tentar
		try {

			//caminho do diretorio e o arquivo que estou apagando
			directory = new File(mainPath+path+"\\");

			//buscando arquivos para ver se existem dentro do diretÃ³rio
			boolean check = new File(directory, file).exists();
			//se existir arquivos dentro da pasta, acessamos a condiÃ§Ã£o
			if(check) {
				//pegando o arquivo
				File currentFile = new File(directory, file);
				//execute o delete
				currentFile.delete();
				//executando javascript
				RequestContext.getCurrentInstance().execute("bloquerTable()");
				//quando deletamos um arquivo, passamos o valor -1 para o total
				total -=  1;
				//executando mÃ©todo listar arquivos
				listFiles();
				//executando funÃ§Ãµes javascript
				RequestContext.getCurrentInstance().execute("mostrarTab2()");
				RequestContext.getCurrentInstance().execute("msgDelete()");
				RequestContext.getCurrentInstance().execute("bloquerTable()");
				RequestContext.getCurrentInstance().execute("uploadFile1()");
			}

			//capturar
		}catch(Exception ex) {
			//executando js
			RequestContext.getCurrentInstance().execute("mostrarTab2()");
			//msg de erro
		}

	}
	//exclui pasta gerada do occ quando Ã© clicado em anular
	public File deleteDirectory() {

		try {
			//escolhendo o caminho do arquivo
			File folder = new File(mainPath+path+"\\");

			//se o diretorio for encontrado
			if (folder.isDirectory()) {
				//pegamos todos os arquivos que estÃ£o dentro da pasta
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
			//executando funÃ§Ã£o js
			RequestContext.getCurrentInstance().execute("mostrarTab2()");

		}
		//retornando um valor nulo para esse mÃ©todo
		return null;
	}
	//mÃ©todo baixar arquivos
	public void download(String fileName) throws Exception {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); //SOLUTION

		OccurrencesDAO2 dao = new OccurrencesDAO2();
		data = new OccurrencesData2();
		//pégando o id da ocorrência e atribuindo +1
		int id = dao.GetId();
		id += 1;
		//criando variavel global

		data.setData_number(String.valueOf(id));

		//chamando mÃƒÂªs e ano, para passar para o caminho.
		DateTimeApplication dddd = new DateTimeApplication();
		LocalDate data = dddd.localeDate();

		//criando caminho da seleÃƒÂ§ÃƒÂ£o da pasta.
		String absoluteFile = data.getYear()+"/"+data.getMonthValue()+"/OCC_"+id+"/"+fileName;

		//caminho onde pegamos o arquivo
		URL url = new URL(downloadPath+absoluteFile);
		String arquivos = url.getQuery();

		//caminho onde o arquivo serÃƒÂ¡ guardado
		//File file = new File(pathDownload+fileName);

		InputStream is = url.openStream();
		//FileOutputStream fos = new FileOutputStream(file);

		int bytes = 0;

		//enquanto o byte faz a leitura do arquivo o qual Ã© diferente de -1
		while ((bytes = is.read()) != -1) {
			//subscreve/ copia o arquivo atual
			baos.write(bytes);
		}

		//fechando o comando downlod
		is.close();
		//fos.close();

		externalContext.setResponseHeader("Content-Disposition","attachment; filename=\""+fileName);

		externalContext.setResponseContentLength(baos.size());

		OutputStream responseOutputStream = externalContext.getResponseOutputStream();  
		baos.writeTo(responseOutputStream);
		responseOutputStream.flush();
		responseOutputStream.close();


		facesContext.responseComplete();  

		// DOWNLOAD


		//executando funÃ§Ãµes js
		RequestContext.getCurrentInstance().execute("bloquerTable()");
		RequestContext.getCurrentInstance().execute("mostrarTab2()");
		RequestContext.getCurrentInstance().execute("msgDownload()");
	}
	public void downloadUpdate(String fileName) throws Exception {
		//js
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("btnEnable();");
		RequestContext.getCurrentInstance().execute("bloquerTable()");	
		RequestContext.getCurrentInstance().execute("mostrarTab2()");
		RequestContext.getCurrentInstance().execute("msgDownload()");
		RequestContext.getCurrentInstance().execute("alterarBtn()");

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); //SOLUTION

		//pegando o id
		int id = getValue();

		//criando caminho da seleÃƒÂ§ÃƒÂ£o da pasta.
		String absoluteFile = pathSQL+"/"+fileName;

		//caminho onde pegamos o arquivo
		URL url = new URL(downloadPath+absoluteFile);
		String arquivos = url.getQuery();

		//caminho onde o arquivo serÃƒÂ¡ guardado
		//File file = new File(pathDownload+fileName);

		InputStream is = url.openStream();
		//FileOutputStream fos = new FileOutputStream(file);

		int bytes = 0;
		//enquanto o byte faz a leitura do arquivo o qual Ã© diferente de -1
		while ((bytes = is.read()) != -1) {
			baos.write(bytes);
		}
		//parando o download
		is.close();
		// DOWNLOAD

		externalContext.setResponseHeader("Content-Disposition","attachment; filename=\""+fileName);

		externalContext.setResponseContentLength(baos.size());

		OutputStream responseOutputStream = externalContext.getResponseOutputStream();  
		baos.writeTo(responseOutputStream);
		responseOutputStream.flush();
		responseOutputStream.close();


		facesContext.responseComplete();  

		// DOWNLOAD

	}
	//download quando fizer o clique em alguma linha da tabela
	public void downloadUpdateTable(String fileName) throws Exception {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); //SOLUTION

		//pegando o id
		int id = getValue();

		//criando caminho da seleÃƒÂ§ÃƒÂ£o da pasta.
		String absoluteFile = pathSQL+"/"+fileName;

		//caminho onde pegamos o arquivo
		URL url = new URL(downloadPath+absoluteFile);
		String arquivos = url.getQuery();

		//caminho onde o arquivo serÃƒÂ¡ guardado
		//File file = new File(pathDownload+fileName);

		InputStream is = url.openStream();
		//FileOutputStream fos = new FileOutputStream(file);

		int bytes = 0;
		//enquanto o byte faz a leitura do arquivo o qual Ã© diferente de -1
		while ((bytes = is.read()) != -1) {
			baos.write(bytes);
		}
		//fechando o download
		is.close();
		//fos.close();
		// DOWNLOAD

		externalContext.setResponseHeader("Content-Disposition","attachment; filename=\""+fileName);

		externalContext.setResponseContentLength(baos.size());

		OutputStream responseOutputStream = externalContext.getResponseOutputStream();  
		baos.writeTo(responseOutputStream);
		responseOutputStream.flush();
		responseOutputStream.close();


		facesContext.responseComplete();  

		// DOWNLOAD

		//se a variavel situaÃ§Ã£o for igual a 30 ou 31 acessamos a condiÃ§Ã£o
		if(situation == 31 || situation == 30) {
			//btns
			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//senÃ£o acessamos a essa condiÃ§Ã£o
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
	}
	//mÃ©todo download PDF
	public String[] downloadPdf() throws Exception {
		// cria��o do documento
		Document document = new Document();
		TranslationMethods trad = new TranslationMethods();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); //SOLUTION

		try {			 	  

			//caminho onde ï¿½ gerado o pdf
			PdfWriter writer = PdfWriter.getInstance(document, baos);

			//gera o arquivo		
			document.open();			

			//formato da folha
			document.setPageSize(PageSize.A4);

			RequestContext.getCurrentInstance().execute("uploadFile()");
			RequestContext.getCurrentInstance().execute("displayPdf()");
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");
			RequestContext.getCurrentInstance().execute("msgDownload()");

			//chamando a imagem
			logo = "C:\\Tracevia\\Software\\External\\Logo\\tuxpan.png";
			Image image1 = Image.getInstance(RoadConcessionaire.externalImagePath);
			Image image2 = Image.getInstance(logo);

			//ediï¿½ï¿½o das imagens
			image2.setAbsolutePosition(70, 770);
			image2.scaleAbsolute (80, 30);

			//passando a imagem
			//document.add(image1);

			document.add(image2);

			PdfContentByte canvas = writer.getDirectContent();
			Paragraph conteudo = new Paragraph();

			// Assim criaremos uma linha em branco
			conteudo.add(new Paragraph(" "));

			PdfPTable table = new PdfPTable(2);
			Paragraph title1 = new Paragraph("REGISTRO DE ACCIDENTE \n AUTOPISTA TUXPAN-TAMPICO\nSEGUROS SURA, S.A de C.V.", FontFactory.getFont(FontFactory.TIMES_ROMAN,9, Font.BOLD, BaseColor.BLACK));

			title1.setIndentationLeft(170);
			table.setTotalWidth(new float[]{ 250, 200 });
			table.setLockedWidth(true);


			Font formatText1 = new Font(Font.FontFamily.TIMES_ROMAN, 9f, Font.BOLD);
			document.add(new Paragraph(title1));
			//table.writeSelectedRows(0, 2, 0, -1, canvas);

			table.addCell(new PdfPCell(new Phrase("Plaza de Cobro", formatText1)));
			table.addCell("");
			table.addCell(new PdfPCell(new Phrase("Folio Secuencial", formatText1)));
			table.addCell("");
			table.addCell(new PdfPCell(new Phrase("Reporte", formatText1)));
			table.addCell("");
			table.addCell(new PdfPCell(new Phrase("Reporte", formatText1)));
			table.addCell("");
			table.addCell(new PdfPCell(new Phrase("Fecha", formatText1)));
			table.addCell("");
			table.addCell(new PdfPCell(new Phrase("Hora", formatText1)));
			table.addCell("");
			table.addCell(new PdfPCell(new Phrase("Dirección Y/O Trayecto", formatText1)));
			table.addCell("");
			table.addCell(new PdfPCell(new Phrase("Kilómetro de Registro", formatText1)));
			table.addCell("");
			table.addCell(new PdfPCell(new Phrase("Kilómetro Inicial", formatText1)));
			table.addCell("");
			table.addCell(new PdfPCell(new Phrase("Kilómetro Final", formatText1)));
			table.addCell("");
			table.addCell(new PdfPCell(new Phrase("Póliza Por Afectar", formatText1)));
			table.addCell("");
			table.addCell(new PdfPCell(new Phrase("Hora de Registro a Cabina", formatText1)));
			table.addCell("");
			table.addCell(new PdfPCell(new Phrase("Hora de Arribo de Ajustador", formatText1)));
			table.addCell("");

			document.add(conteudo);
			document.add(table);
			document.add(conteudo);
			//

			Paragraph title2 = new Paragraph("Vehículos Involucrados");
			PdfPTable table1 = new PdfPTable(4);
			table1.setTotalWidth(500);
			table1.setLockedWidth(true);
			document.add(title2);

			table1.addCell(new PdfPCell(new Phrase("Tipo de Vehículo", formatText1)));
			table1.addCell("");
			table1.addCell(new PdfPCell(new Phrase("Número de ejes de la unidad", formatText1)));
			table1.addCell("");
			table1.addCell(new PdfPCell(new Phrase("Tipo de Vehículo", formatText1)));
			table1.addCell("");
			table1.addCell(new PdfPCell(new Phrase("Número de ejes de la unidad", formatText1)));
			table1.addCell("");
			table1.addCell(new PdfPCell(new Phrase("Tipo de Vehículo", formatText1)));
			table1.addCell("");
			table1.addCell(new PdfPCell(new Phrase("Número de ejes de la unidad", formatText1)));
			table1.addCell("");

			document.add(conteudo);
			document.add(table1);

			PdfPTable table2 = new PdfPTable(7);
			table2.setTotalWidth(500);
			table2.setLockedWidth(true);
			table2.setHeaderRows(1);
			table2.addCell(new PdfPCell(new Phrase("No.", formatText1)));
			table2.addCell(new PdfPCell(new Phrase("Marca", formatText1)));
			table2.addCell(new PdfPCell(new Phrase("Tipo", formatText1)));
			table2.addCell(new PdfPCell(new Phrase("Modelo", formatText1)));
			table2.addCell(new PdfPCell(new Phrase("Color", formatText1)));
			table2.addCell(new PdfPCell(new Phrase("Placas/Estado", formatText1)));
			table2.addCell(new PdfPCell(new Phrase("Telefone", formatText1)));
			for(int i = 0; i< 32; i++)
				table2.addCell(new PdfPCell(new Phrase("Column", formatText1)));

			document.add(conteudo);
			document.add(table2);
			document.add(conteudo);

			Paragraph title4 = new Paragraph("Datos de Personas");
			PdfPTable table3= new PdfPTable(4);
			table3.setTotalWidth(500);
			table3.setTotalWidth(new float[]{75, 150, 75, 200 });
			table3.setLockedWidth(true);
			document.add(title4);
			table3.setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.setHeaderRows(1);
			table3.addCell(new PdfPCell(new Phrase("No.", formatText1)));
			table3.addCell(new PdfPCell(new Phrase("NOMBRE DEL CONTUCTOR", formatText1)));
			table3.addCell(new PdfPCell(new Phrase("EDAD", formatText1)));
			table3.addCell(new PdfPCell(new Phrase("CONDICIONES DE SALUD", formatText1)));
			for(int i = 0; i< 9; i++)
				table3.addCell(new PdfPCell(new Phrase("Column", formatText1)));

			document.add(conteudo);
			document.add(table3);

			Paragraph title5 = new Paragraph("");
			PdfPTable table4 = new PdfPTable(2);
			table4.setTotalWidth(500);
			table4.setTotalWidth(new float[]{ 200, 300 });
			table4.setLockedWidth(true);
			table4.addCell(new PdfPCell(new Phrase("MOTIVO DEL ACCIDENTE:", formatText1)));
			table4.addCell("");

			document.add(conteudo);
			document.add(table4);

			PdfPTable table5 = new PdfPTable(3);
			table5.setTotalWidth(500);
			table5.setTotalWidth(new float[]{ 50, 150, 300 });
			table5.setLockedWidth(true);
			int[] test = new int[19];
			for(int i=0;i<19;i++) {
				if(i == 0) {
					table5.addCell(new PdfPCell(new Phrase("A", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("SEMOVIENTE", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("COLUMN", formatText1)));
				}
				if(i == 1) {
					table5.addCell("B");
					table5.addCell(new PdfPCell(new Phrase("TRABAJOS DE\nCONSERVACIÓN", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("COLUMN", formatText1)));
				}
				if(i == 2) {
					table5.addCell(new PdfPCell(new Phrase("C", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("LLUVIA, GRANIZO", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("COLUMN", formatText1)));
				}
				if(i == 3) {
					table5.addCell(new PdfPCell(new Phrase("D", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("NEBLINA", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("COLUMN", formatText1)));
				}
				if(i == 4) {
					table5.addCell(new PdfPCell(new Phrase("E", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("VANDALISMO", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("COLUMN", formatText1)));
				}
				if(i == 5) {
					table5.addCell(new PdfPCell(new Phrase("F", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("OTRO", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("COLUMN", formatText1)));
				}
				//table5.addCell("column "+ i);
			}

			document.add(table5);
			
			Rectangle obs = new Rectangle(48, 140, 548, 180);
			obs.setBorder(Rectangle.BOX);
			obs.setBorderWidth(2);
			canvas.rectangle(obs);
			Paragraph obs_ = new Paragraph();
			document.add(conteudo);
			obs_.add(new Paragraph(new Phrase(10F, "Daños pendientes por cuantificar: Usuario no cuenta con seguro, la autopista no reclama daño alguno.Daños pendientes por cuantificar: Usuario no cuenta con seguro, la autopista no reclama daño alguno", FontFactory.getFont(FontFactory.HELVETICA, 10F))));
			obs_.setIndentationLeft(40f);
			document.add(obs_);
			
			
			Rectangle ass = new Rectangle(48, 130, 548, 60);
			ass.setBorder(Rectangle.BOX);
			document.add(conteudo);
			
			PdfPTable table_ass = new PdfPTable(2);
			table_ass.setTotalWidth(500);
			table_ass.setTotalWidth(new float[]{ 250, 250 });
			table_ass.setLockedWidth(true);
			
			Paragraph title__ = new Paragraph(new Phrase(10F, "OPERADOR DEL C. CONTROL", FontFactory.getFont(FontFactory.COURIER_BOLD, 10F)));
			Paragraph title_ = new Paragraph(new Phrase(10F, "AJUSTADOR", FontFactory.getFont(FontFactory.COURIER_BOLD, 10F)));
			table_ass.addCell(title__);
			table_ass.addCell(title_);
			table_ass.addCell(new PdfPCell(new Phrase("\nNOMBRE: Juan Baptista Mendez", formatText1)));
			table_ass.addCell(new PdfPCell(new Phrase("\nNOMBRE: Alvaro Angel Millano", formatText1)));
			table_ass.addCell(new PdfPCell(new Phrase("\nFIRMA: ______________________", formatText1)));
			table_ass.addCell(new PdfPCell(new Phrase("\nFIRMA: ______________________", formatText1)));
			
			
			
			
			document.add(table_ass);			
			/*Paragraph ass__ = new Paragraph();
			Paragraph name__ = new Paragraph();
			Paragraph title__ = new Paragraph();
			title__.add(new Paragraph(new Phrase(10F, "OPERADOR DEL C. CONTROL", FontFactory.getFont(FontFactory.HELVETICA, 10F))));
			title__.setIndentationLeft(30f);
			document.add(conteudo);
			document.add(title__);
			name__.add("Nombre: Tester");
			name__.setIndentationLeft(30f);
			document.add(name__);
			ass__.add("Firma:  _______________");
			ass__.setIndentationLeft(30f);
			document.add(ass__);
			
			Paragraph ass_ = new Paragraph();
			Paragraph name_ = new Paragraph();
			Paragraph title_ = new Paragraph();
			title_.add(new Paragraph(new Phrase(10F, "AJUSTADOR", FontFactory.getFont(FontFactory.HELVETICA, 10F))));
			title_.setIndentationLeft(300f);
			document.add(conteudo);
			document.add(title_);
			name_.add("Nombre: Tester");
			name_.setIndentationLeft(300f);
			document.add(name_);
			ass_.add("Firma:  _______________");
			ass_.setIndentationLeft(300f);
			document.add(ass_);*/
			
			ass.setBorderWidth(2);
			canvas.rectangle(ass);
		}
		catch(DocumentException de) {
			System.err.println(de.getMessage());
		}
		catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		document.close();


		// DOWNLOAD

		externalContext.setResponseContentType("application/pdf");
		externalContext.setResponseHeader("Content-Disposition","attachment; filename=\""+"informe_ocurrencias_"+data.getData_number()+".pdf\"");

		externalContext.setResponseContentLength(baos.size());

		OutputStream responseOutputStream = externalContext.getResponseOutputStream();  
		baos.writeTo(responseOutputStream);
		responseOutputStream.flush();
		responseOutputStream.close();


		facesContext.responseComplete();  

		// DOWNLOAD

		//passando valor final do m�todo para a variavel tableFile
		return tableFile;

	}
	public void enableUser() throws Exception {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		//passando os valores predeterminados para as variveis
		String nameUser = (String) facesContext.getExternalContext().getSessionMap().get("user");
		int day1 = LocalDateTime.now().getDayOfMonth();
		String dia = String.valueOf(day1);
		int year1 = LocalDateTime.now().getYear();
		String ano = String.valueOf(year1);
		int month1 = LocalDateTime.now().getMonthValue();
		String mes = String.valueOf(month1);
		int hora = LocalDateTime.now().getHour();
		String hour = String.valueOf(hora);
		int min = LocalDateTime.now().getMinute();
		String minute = String.valueOf(min);
		int sec = LocalDateTime.now().getSecond();
		String second = String.valueOf(sec);
		String date = dia+"/"+mes+"/"+ano+" "+hora+":"+minute+":"+second;

		OccurrencesDAO2 occ = new OccurrencesDAO2();
		String id = data.getData_number();
		String lastData = date;
		String user = nameUser;
		occ.lastUser(id, lastData, user);

		TimeUnit.MINUTES.sleep(1);
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("btnEnable();");
		request.execute("inputs()");

	}

	public String getImagePath(String image) {

		try {

			Path path = Paths.get(image);								
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);

		} catch (IOException e) {											
			return "";
		}

	}	


}

