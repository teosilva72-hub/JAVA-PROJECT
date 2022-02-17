package br.com.tracevia.webapp.controller.occ;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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

import org.primefaces.context.RequestContext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.tracevia.webapp.controller.global.UserAccountBean;
import br.com.tracevia.webapp.dao.occ.OccurencesDao2;
import br.com.tracevia.webapp.dao.occ.OccurrencesDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.occ.OccurenceData2;
import br.com.tracevia.webapp.model.occ.OccurrencesData;
import br.com.tracevia.webapp.model.occ.OccurrencesDetails;
import br.com.tracevia.webapp.util.LocaleUtil;



@ManagedBean(name="Occurence2")
@ViewScoped
public class Occurence2 {

private OccurenceData2  data;
private OccurenceData2 getPdf;
private OccurrencesDetails details;
private UserAccountBean userId;

OccurencesDao2 dao;
LocaleUtil occLabel, occMessages;

private boolean save, edit, new_, reset, fields, enableBtn,
table, alterar, pdf;
private String logo, userPdf;


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

	private String mainPath, localPath, occNumber,path, way, downloadPath, pathDownload, pathSQL, monthPdf,
	 dayPdf,  nameUser, userName; 
	private String getFile, fileDelete, fileUpdate, pathImage, absoluteImage, imagePath;
	private String[] listarFile, listUpdate, tableFile, imagem, FileName;
	private File arquivos[], directory, fileWay;
	private int bytes, total, situation, idUpload;
	private int value, content, nivelUser;
	private Part file = null;
	private Part file2 = null;
	private ImageIcon image;
	private int rowkey;
	private boolean selectedRow;
	private List<OccurenceData2> occurrences;

	private String dataTeste;
	private String sinistro;



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
	public OccurenceData2 getData() {
		return data;
	}
	public void setData(OccurenceData2 data) {
		this.data = data;
	}
	public OccurrencesDetails getDetails() {
		return details;
	}
	public void setDetails(OccurrencesDetails details) {
		this.details = details;
	}
	public List<OccurenceData2> getOccurrences() {
		return occurrences;
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
public void teste() {
		System.out.println("Estamos aqui");
		System.out.println(getData());
	}

	@PostConstruct
	public void initialize() {	
	
		//input select options
		occurrences = new ArrayList<OccurenceData2>();

		try {

			OccurencesDao2 dao = new OccurencesDao2();
			data = new  OccurenceData2();
		
			
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
	
	
	
	//taking the last id from the database and passing 1 more
	public int pegarId() throws Exception{

		OccurencesDao2 x = new OccurencesDao2();
		value =  x.GetId();
		//String e = userId.getUser_id();
		value += 1;
		data = new OccurenceData2();
		data.setOcc_number(String.valueOf(value));

		return value;
	}
 public void Teste() {
	 
	 System.out.println("testee");
	 
	 
	 
 }
	//cadastar occurrences
	public void cadastroOcorrencia() throws Exception {
		boolean sucess = false;

		System.out.println("teste");

		OccurencesDao2 dao = new OccurencesDao2();
		occNumber= dao.cadastroOcorrencia(data);
		localPath = localPath(occNumber);

		//if id is non-null
		//if(occ_number != null) {
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

	

	

	//update occurences
	public void atualizarOcorrencia() throws Exception {

		OccurencesDao2 dao = new OccurencesDao2();

		//ao atualizar a ocorrencia passamos um valor null para a variavel (nameUser),
		//passos 0 para a variavel (accessLevel) e para a variavel (updateTable) passamos false
		// ambos os valores serão armazenados no BD a fazer a requisição
		String nameUser = ""; int accessLevel = 0; boolean updateTable = false; 
		System.out.println(data.getData_number());
		idUpdate = data.getData_number();
		//Passando dados definidos para o banco de dados
		dao.editTable(updateTable, nameUser, accessLevel, data.getSinistro());

		//passando falso para acessar a condição IF
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
			//executando função javascript
			RequestContext.getCurrentInstance().execute("resetForm()");
			RequestContext.getCurrentInstance().execute("fileTotalHidden()");
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");


			//listar ocorrencia
			//occurrences = dao.listarOcorrencias();

			//listar arquivos sem direito a modificação
			//TableFile();

		}
		resetUpdate();

	}

	//metodo anular ocorrencia, esse metodo é chamado quando estamos na sessão novo
	public void resetOccurrencesData(){       

		data = new OccurenceData2();
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
	//metodo anular ocorrencia, esse metodo é chamado quando estamos na sessão editar
	public void resetUpdate() throws Exception{
		OccurencesDao2 dao = new OccurencesDao2();

		//ao anular a ocorrencia passamos os valores predeterminados para a seguintes variaveis:
		//(nameUser = null, accessLevel = 0 e updateTable = False) ambos os valores são armazenados no BD.
		String nameUser = "";
		int accessLevel = 0;
		boolean updateTable = false;
		pdf = false;

		//passando dados para o banco de dados
		dao.editTable(updateTable, nameUser, accessLevel, data.getSinistro());

		//atualizar a ocorrencia depois que o metodo for chamado
		//occurrences = dao.listarOcorrencias();
		//chamando função javascript
		RequestContext.getCurrentInstance().execute("eventValidator()");

		data = new OccurenceData2();

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

		try {

			//chamando valores do usuário de outro controller
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();	

			//pegando os valores de outro controller e armazenado dentro das variaveis: (userName e nivelUser)
			userName = (String) facesContext.getExternalContext().getSessionMap().get("user");
			nivelUser = (int) facesContext.getExternalContext().getSessionMap().get("nivel");

			//executando função javascript
			RequestContext.getCurrentInstance().execute("displayPdf()");
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");

			OccurrencesDAO dao = new OccurrencesDAO();

			//buscar dados por id
			//dataa = dao.buscarOcorrenciaPorId(rowkey);

			//buscar dados pdf
			//getPdf = dao.submitPdf(rowkey);

			//buscar caminho dos arquivos 
			pathSQL = data.getLocalFiles();

			//status da occorencia
			String x = data.getState_occurrences();

			//transformando em int
			situation = Integer.parseInt(x);
			System.out.println(data.getLastDateHour());

			Date parsedDate = dateFormat.parse(data.getLastDateHour());
			timestamp = new java.sql.Timestamp(parsedDate.getTime());
			timestamp2 = new java.sql.Timestamp(b.getTime());
			System.out.println(timestamp +" <");
			System.out.println(timestamp2 + "<<");
			System.out.println((timestamp.after(timestamp2)+" antes"));
			System.out.println((timestamp.before(timestamp2)+" depois"));
			System.out.println(data.getEditTable()+" editTable");
		}catch(Exception ex){

			ex.printStackTrace();

		}
		//listando arquivos quando clica na linha da tabela
		TableFile();
		pdf = false;
		//Se a linha da table estiver selecionada:
		if(selectedRow) {

			//se a situação for igual 30 ou 31
			//não é possivel fazer alteração
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

				//senão se for igual a false acesso liberado para realizar edição
			}else if((data.getEditTable() == false)|| timestamp.before(timestamp2)) {

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

				//senão se for igual a true acesso bloqueado para realizar edição
			}else if((data.getEditTable() == true && timestamp.after(timestamp2))) {

				//executando função javascript
				RequestContext.getCurrentInstance().execute("msgUser()");

				save = true;
				alterar = true;
				reset = true;
				new_ = false;
				fields = true;
				edit = true;
				table = true;

				//se o nome do usuario local, for igual o nome da pessoa que esta editando
				//a ocorrencia acessa pode acessar a essa condição
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

					//senão se o nivel de acesso do usuário for igual a 1 ou igual a 6
					//tem permissão para acessar a condição
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

			//método não está sendo usado
			//senão estiver selecionada a linha da tabela
		}else {

			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//executando as funções javascript
			RequestContext.getCurrentInstance().execute("hiddenPdf()");
			RequestContext.getCurrentInstance().execute("msgFinishedHidden()");
			RequestContext.getCurrentInstance().execute("listingFileBtn()");
			RequestContext.getCurrentInstance().execute("listingFile()");

		}
		//zerando as variaveis
		listarFile = null;
	}
	//método novo
	public void btnEnable() throws Exception {
		//executando as funções javascript
		RequestContext.getCurrentInstance().execute("inputs()");
		RequestContext.getCurrentInstance().execute("disableEdit()");
		RequestContext.getCurrentInstance().execute("listUpdateFile1()");
		RequestContext.getCurrentInstance().execute("uploadFile()");
		RequestContext.getCurrentInstance().execute("hiddenListFile()");
		RequestContext.getCurrentInstance().execute("hiddenPdf()");
		//pegando o valor da ultima variavel do vanco de dados
		value = pegarId(); // pegarId() + 1
		//se o valor for maior do que 0 acessamos a condição
		if(value > 0) {

			//executando o método cadastrar, ou seja, quando acessamos esse método
			//estamos reservando o id no banco de dados.
			cadastroOcorrencia();

			//transformando a variavel (value) em String
			String sinistro = (String.valueOf(value));

			//CREATE LOCAL PATH
			localPath = localPath(sinistro);
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
	//método editar ocorrencia
	public void btnEdit() throws Exception {

		OccurencesDao2 dao = new OccurencesDao2();

		//pegando valores dos usuários de outro controller
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		//passando os valores predeterminados para as variveis
		String nameUser = (String) facesContext.getExternalContext().getSessionMap().get("user");
		int nivelUser = (int) facesContext.getExternalContext().getSessionMap().get("nivel");
		boolean updateTable = true;
		//passando valores das varaveis para o banco de dados
		dao.editTable(updateTable, nameUser, nivelUser, data.getSinistro());			
		enableBtn = true;
		//btn
		fields = false;
		reset = false;
		save = true;
		edit = true;
		alterar = false;
		pdf = true;

		//executando funções javascript
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("btnEnable();");
		request.execute("inputs()");
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
	public String localPath(String sinistro) {

		DateTimeApplication dta = new DateTimeApplication();
		LocalDate local = dta.localeDate();
		path = local.getYear()+"//"+local.getMonthValue()+"//OCC_"+sinistro;

		return path;
	}

	//método para enviar arquivos na tela de atualização de cadastro
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

		//executando funções javascript
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("btnEnable();");
		RequestContext.getCurrentInstance().execute("mostrarTab2()");
		RequestContext.getCurrentInstance().execute("bloquerTable()");
		RequestContext.getCurrentInstance().execute("msgSaveFile()");
		RequestContext.getCurrentInstance().execute("fileTotal1()");
		RequestContext.getCurrentInstance().execute("alterarBtn()");



	}

	//método enviando
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

	//método listar arqivos ao atualizar
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

		//criando caminho da seleÃ§Ã£o da pasta.
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

			//pegando os nomes do arquivos na posição
			listUpdate[x] = arquivos[x].getName();

			System.out.println("Position: "+x+" < Arquivo na pasta > "+ listUpdate[x]);

			x++;

		}

		//retornando o valor do método listUpdate == nome do arquivo
		return listUpdate;
	}
	//método listar arquivos quando clicamos na tablea
	public String[] TableFile() {
		System.out.println(timestamp+" <a");
		System.out.println(timestamp2+" <b");
		//pegando o status da ocorrência		
		String b = data.getState_occurrences();
		//tranformando o valor do status da ocorrência  em inteiro
		situation = Integer.parseInt(b);
		pdf = false;
		//se o status da occorencia for igual a 30 ou 31, acessamos esse função
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

			//se o nivel de acesso dop usuário for igual a 1 ou igual a 6, acessamos a função
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

			//senão se for igual a true acesso bloqueado para realizar edição
		}else if(data.getEditTable() == true && timestamp.after(timestamp2)) {

			RequestContext.getCurrentInstance().execute("msgUser()");

			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//se o nome do usuário for igual o nome armazenado no atributo nameUser, acessamos a condição
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

				//senão se o nivel de acesso do usuário for igual a 1 ou igual a 6, acessamos a condição
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
		//passando valor final do método para a variavel tableFile
		return tableFile;

	}

	//método listar arquivos
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
		//passando valor do método para a variável
		return listarFile;
	}

	//esse método não esta sendo aplicado, está aqui para futuros testes
	//Buscando imagem

	public String getImageUpload(String myImg) throws Exception{
		//gerando o caminho onde se encontra a imagem
		OccurrencesBean pegar= new OccurrencesBean();
		int id = pegar.pegarId() - 1;
		DateTimeApplication dddd = new DateTimeApplication();
		LocalDate data = dddd.localeDate();
		String imagePath = data.getYear()+"/"+data.getMonthValue()+"/"+"OCC_"+id+"/";

		absoluteImage = pathImage+imagePath+myImg;

		return absoluteImage;

	}
	//esse método não esta sendo usado, está aqui para testes
	public String getImageUpdate(String myImg) throws Exception{

		//gerando o caminho onde se encontra a imagem
		int id = getValue();

		String imagePath = pathSQL+"/";

		absoluteImage = pathImage+imagePath+myImg;

		return absoluteImage;

	}

	//método deletar arquivos na atualização do cadastro
	public void deleteFileUpdate(String file) throws Exception {
		//tentar
		try {
			//criando o camingo onde os arquivos são armazenados
			fileWay = new File(mainPath+way+"\\");

			//buscando arquivos para ver se existem dentro do diretório
			boolean check = new File(fileWay, file).exists();

			//se existir arquivos dentro da pasta, acessamos a condição
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
			//executando função javascript
			RequestContext.getCurrentInstance().execute("mostrarTab2()");
			//msg de erro ao deletar file
			System.out.println("Erro ao excluir arquivo");
		}

	}
	//método deletar arquivos quando estamos na tela de novo cadastri
	public void deleteFile(String file){
		//tentar
		try {

			//caminho do diretorio e o arquivo que estou apagando
			directory = new File(mainPath+path+"\\");

			//buscando arquivos para ver se existem dentro do diretório
			boolean check = new File(directory, file).exists();
			//se existir arquivos dentro da pasta, acessamos a condição
			if(check) {
				//pegando o arquivo
				File currentFile = new File(directory, file);
				//execute o delete
				currentFile.delete();
				//executando javascript
				RequestContext.getCurrentInstance().execute("bloquerTable()");
				//quando deletamos um arquivo, passamos o valor -1 para o total
				total -=  1;
				//executando método listar arquivos
				listFiles();
				//executando funções javascript
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
			System.out.println("Erro ao excluir arquivo");
		}

	}
	//exclui pasta gerada do occ quando é clicado em anular
	public File deleteDirectory() {

		try {
			//escolhendo o caminho do arquivo
			File folder = new File(mainPath+path+"\\");

			//se o diretorio for encontrado
			if (folder.isDirectory()) {
				//pegamos todos os arquivos que estão dentro da pasta
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
			//executando função js
			RequestContext.getCurrentInstance().execute("mostrarTab2()");

		}
		//retornando um valor nulo para esse método
		return null;
	}
	//método baixar arquivos
	public void download(String fileName) throws Exception {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); //SOLUTION

		OccurencesDao2 dao = new OccurencesDao2();
		data = new OccurenceData2();
		//pégando o id da ocorrência e atribuindo +1
		int id = dao.GetId();
		id += 1;
		//criando variavel global

		data.setSinistro(String.valueOf(id));

		//chamando mÃªs e ano, para passar para o caminho.
		DateTimeApplication dddd = new DateTimeApplication();
		LocalDate data = dddd.localeDate();

		//criando caminho da seleÃ§Ã£o da pasta.
		String absoluteFile = data.getYear()+"/"+data.getMonthValue()+"/OCC_"+id+"/"+fileName;

		//caminho onde pegamos o arquivo
		URL url = new URL(downloadPath+absoluteFile);
		String arquivos = url.getQuery();

		//caminho onde o arquivo serÃ¡ guardado
		//File file = new File(pathDownload+fileName);

		InputStream is = url.openStream();
		//FileOutputStream fos = new FileOutputStream(file);

		int bytes = 0;

		//enquanto o byte faz a leitura do arquivo o qual é diferente de -1
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


		//executando funções js
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

		//criando caminho da seleÃ§Ã£o da pasta.
		String absoluteFile = pathSQL+"/"+fileName;

		//caminho onde pegamos o arquivo
		URL url = new URL(downloadPath+absoluteFile);
		String arquivos = url.getQuery();

		//caminho onde o arquivo serÃ¡ guardado
		//File file = new File(pathDownload+fileName);

		InputStream is = url.openStream();
		//FileOutputStream fos = new FileOutputStream(file);

		int bytes = 0;
		//enquanto o byte faz a leitura do arquivo o qual é diferente de -1
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

		//criando caminho da seleÃ§Ã£o da pasta.
		String absoluteFile = pathSQL+"/"+fileName;

		//caminho onde pegamos o arquivo
		URL url = new URL(downloadPath+absoluteFile);
		String arquivos = url.getQuery();

		//caminho onde o arquivo serÃ¡ guardado
		//File file = new File(pathDownload+fileName);

		InputStream is = url.openStream();
		//FileOutputStream fos = new FileOutputStream(file);

		int bytes = 0;
		//enquanto o byte faz a leitura do arquivo o qual é diferente de -1
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

		//se a variavel situação for igual a 30 ou 31 acessamos a condição
		if(situation == 31 || situation == 30) {
			//btns
			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//senão acessamos a essa condição
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
	//método download PDF
	public String[] downloadPdf() throws Exception {

		// cria��o do documento
		Document document = new Document();
		TranslationMethods trad = new TranslationMethods();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); //SOLUTION

		try {			 	  

			//caminho onde � gerado o pdf
			PdfWriter writer = PdfWriter.getInstance(document, baos);

			//gera o arquivo		
			document.open();			

			//formato da folha
			document.setPageSize(PageSize.A4);

			RequestContext.getCurrentInstance().execute("uploadFile()");
			RequestContext.getCurrentInstance().execute("displayPdf()");
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");
			RequestContext.getCurrentInstance().execute("msgDownload()");

			//Editando o tipo de fonte do titulo
			Paragraph pTitulo = new Paragraph(new Phrase(20F , trad.occLabels("report"), FontFactory.getFont(FontFactory.HELVETICA, 17F)));
			Paragraph evento = new Paragraph(new Phrase(20F , trad.occLabels("Eventos"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph dateHour = new Paragraph(new Phrase(20F , trad.occLabels("dateOcc"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph causeProbable = new Paragraph(new Phrase(20F , trad.occLabels("CausaPro"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph eventoLocal = new Paragraph(new Phrase(20F , trad.occLabels("Evento Local"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph detalhes = new Paragraph(new Phrase(20F , trad.occLabels("Detalhes"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph description = new Paragraph(new Phrase(20F , trad.occLabels("description"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph envolvidos = new Paragraph(new Phrase(20F , trad.occLabels("Envolvidos"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph track = new Paragraph(new Phrase(20F , trad.occLabels("Traffic"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph danos = new Paragraph(new Phrase(20F , trad.occLabels("Danos"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));
			Paragraph action = new Paragraph(new Phrase(20F , trad.occLabels("action"), FontFactory.getFont(FontFactory.HELVETICA, 15F)));

			//chamando a imagem
			logo = "C:\\Tracevia\\Software\\External\\Logo\\tuxpan.png";
			//Image image1 = Image.getInstance(RoadConcessionaire.externalDefaultLogo);
			Image image2 = Image.getInstance(logo);

			//edi��o das imagens
			//image1.setAbsolutePosition(50, 790);
			//image1.scaleAbsolute (100, 50);
			image2.setAbsolutePosition(420, 800);
			image2.scaleAbsolute (120, 30);
			//passando a imagem
			//document.add(image1);
			document.add(image2);

			//add titulo
			ColumnText tl = new ColumnText(writer.getDirectContent());
			Paragraph tx = new Paragraph();
			tl.setSimpleColumn(400,780,200,50);
			tx.add(pTitulo);
			tl.addElement(tx);
			tl.go();
			document.add(new Paragraph("\n\n\n\n"));


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
			document.add(new Paragraph(trad.occLabels("occ")+": "+data.getSinistro()+" "
					+ trad.occLabels("Tipo")+(": ")+ trad.occurrencesTranslator(getPdf.getMotivo())+"         "
					+ trad.occLabels("Situacao")+(": ")+ trad.occurrencesTranslator(getPdf.getState_occurrences())+"\n"
					+"_____________________________________________________________________________"
					+"\n\n "
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
		
			//causa prov�vel e descri��o principal e interna.
			/*Rectangle causePr= new Rectangle(577, 310, 10, 610); // you can resize rectangle 
			causePr.enableBorderSide(1);
			causePr.enableBorderSide(2);
			causePr.enableBorderSide(4);
			causePr.enableBorderSide(8);
			causePr.setBorderColor(BaseColor.BLACK);
			causePr.setBorderWidth(1);
			document.add(causePr);*/
			document.add(new Paragraph(causeProbable+"\n"+"\n"));
			document.add(new Paragraph(trad.occLabels("Causa")+": "+trad.occurrencesTranslator(getPdf.getMotivo())+"\n\n"));
			document.add(new Paragraph(trad.occLabels("description")+": "+data.getObservacao()+"\n"
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

			document.add(new Paragraph(trad.occLabels("Sentido")+": "+getPdf.getDirecao()+"                         "
		
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
		

			//final da primeira p�gina

			document.newPage();//inicio da segunda p�gina
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
			document.add(new Paragraph(trad.occLabels("Tipo")+": "+getPdf.getNome()+"\n\n"));
			document.add(new Paragraph(trad.occLabels("description")+": "+ data.getSaude()+"\n"
					+"_____________________________________________________________________________"
					+"\n\n"));

			//Tr�nsito
			/*Rectangle track1 = new Rectangle(577, 560, 10, 665); // you can resize rectangle 
			track1.enableBorderSide(1);
			track1.enableBorderSide(2);
			track1.enableBorderSide(4);
			track1.enableBorderSide(8);
			track1.setBorderColor(BaseColor.BLACK);
			track1.setBorderWidth(1);
			document.add(track1);*/

			document.newPage();//inicio da terceira p�gina

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

			//a�tion
			/*Rectangle action1 = new Rectangle(577, 225, 10, 415); // you can resize rectangle 
			action1.enableBorderSide(1);
			action1.enableBorderSide(2);
			action1.enableBorderSide(4);
			action1.enableBorderSide(8);
			action1.setBorderColor(BaseColor.BLACK);
			action1.setBorderWidth(1);
			document.add(action1);*/
			document.add(new Paragraph(action+"\n"+"\n"));



			//causa prov�vel e descri��o principal e interna.

			document.add(new Paragraph(causeProbable+"\n"+"\n"));
			document.add(new Paragraph(trad.occLabels("Causa")+": "+trad.occurrencesTranslator(getPdf.getMotivo())+"\n\n"));

		
	
			document.add(new Paragraph(eventoLocal+"\n"+"\n"));
			document.add(new Paragraph("KM: "+data.getKmregistro()+"    "
					+ trad.occLabels("Rodovia")+": "+getPdf.getPedagio()+"\n\n"));
			
			document.add(new Paragraph(trad.occLabels("Sentido")+": "+getPdf.getDirecao()+"  "
					+ trad.occLabels("obs")+": "+data.getObservacao()+"\n"
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
		
			/*	Rectangle details= new Rectangle(577, 255, 10, 355); // you can resize rectangle 
			details.enableBorderSide(1);
			details.enableBorderSide(2);
			details.enableBorderSide(4);
			details.enableBorderSide(8);
			details.setBorderColor(BaseColor.BLACK);
			details.setBorderWidth(1);
			document.add(details);*/
			document.add(new Paragraph(detalhes+"\n"+"\n"));


			//final da primeira p�gina

			document.newPage();//inicio da segunda p�gina
			Rectangle rowPage11 = new Rectangle(577, 40, 10, 820); //linha da pagina 
			rowPage11.setBorderColor(BaseColor.BLACK);
			rowPage11.setBorderWidth(2);
			rowPage11.setBorder(Rectangle.BOX);
			document.add(rowPage11);
			ColumnText ct11 = new ColumnText(writer.getDirectContent());
			ct11.setSimpleColumn(700,0,200,30);
			Paragraph p11 = new Paragraph();
			p11.add("                              "+"Pag 2");//paragrafo Evento
			ct11.addElement(p11);
			ct11.go();
			/*Rectangle descriptions= new Rectangle(577, 110, 10, 250); // you can resize rectangle 
			descriptions.enableBorderSide(1);
			descriptions.enableBorderSide(2);
			descriptions.enableBorderSide(4);
			descriptions.enableBorderSide(8);
			descriptions.setBorderColor(BaseColor.BLACK);
			descriptions.setBorderWidth(1);
			document.add(descriptions);*/
		

			//Envolvidos
			/*Rectangle envolvido = new Rectangle(577, 670, 10, 810); // you can resize rectangle 
			envolvido.enableBorderSide(1);
			envolvido.enableBorderSide(2);
			envolvido.enableBorderSide(4);
			envolvido.enableBorderSide(8);
			envolvido.setBorderColor(BaseColor.BLACK);
			envolvido.setBorderWidth(1);
			document.add(envolvido);*/
		

			//Tr�nsito
			/*Rectangle track1 = new Rectangle(577, 560, 10, 665); // you can resize rectangle 
			track1.enableBorderSide(1);
			track1.enableBorderSide(2);
			track1.enableBorderSide(4);
			track1.enableBorderSide(8);
			track1.setBorderColor(BaseColor.BLACK);
			track1.setBorderWidth(1);
			document.add(track1);*/
			document.add(new Paragraph(track+"\n"+"\n"));

			document.add(new Paragraph());


			Rectangle rowPage21 = new Rectangle(577, 40, 10, 820); //linha da pagina 
			rowPage21.setBorderColor(BaseColor.BLACK);
			rowPage21.setBorderWidth(2);
			rowPage21.setBorder(Rectangle.BOX);
			document.add(rowPage21);

			ColumnText ct21 = new ColumnText(writer.getDirectContent());
			ct21.setSimpleColumn(700,0,200,30);
			Paragraph p21 = new Paragraph();
			p21.add("                              "+"Pag 3");//paragrafo Evento
			ct21.addElement(p21);
			ct21.go();

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


			/*Rectangle action1 = new Rectangle(577, 225, 10, 415); // you can resize rectangle 
			action1.enableBorderSide(1);
			action1.enableBorderSide(2);
			action1.enableBorderSide(4);
			action1.enableBorderSide(8);
			action1.setBorderColor(BaseColor.BLACK);
			action1.setBorderWidth(1);
			document.add(action1);*/
//darken date and time
		
			int day1 = LocalDateTime.now().getDayOfMonth();
			int year1 = LocalDateTime.now().getYear();
			int month1 = LocalDateTime.now().getMonthValue();
			int hour1 = LocalDateTime.now().getHour();
			int minute1 = LocalDateTime.now().getMinute();
			int second1 = LocalDateTime.now().getSecond();



			//System.out.println("testando aqui agora: "+ day+"/"+month+"/"+year);
			userName = (String) facesContext.getExternalContext().getSessionMap().get("user");
			document.add(new Paragraph("\n\n                "+trad.occLabels("operador")+": "+userPdf));
			//assinatura
			document.add(new Paragraph("\n                "+trad.occLabels("Assinatura")+":"+ "______________________________________________."+"\n\n"
					+ "                                    "+trad.occLabels("Data do relatorio")+":  "+dayPdf+"/"+monthPdf+"/"+year1));


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
		externalContext.setResponseHeader("Content-Disposition","attachment; filename=\""+"OCC_"+data.getSinistro()+".pdf\"");

		externalContext.setResponseContentLength(baos.size());

		OutputStream responseOutputStream = externalContext.getResponseOutputStream();  
		baos.writeTo(responseOutputStream);
		responseOutputStream.flush();
		responseOutputStream.close();


		facesContext.responseComplete();  

		// DOWNLOAD


		//getRowValue();
		System.out.println(timestamp+" <a");
		System.out.println(timestamp2+" <b");
		String x = data.getState_occurrences();
		situation = Integer.parseInt(x);

		//pegando os valores de outro controller e armazenado dentro das variaveis: (userName e nivelUser)
		userName = (String) facesContext.getExternalContext().getSessionMap().get("user");
		nivelUser = (int) facesContext.getExternalContext().getSessionMap().get("nivel");

		//se a variavel situa��o for igual a 30 ou 31 acessamos a essa condi��o
		if(situation == 31 || situation == 30) {
			//btns
			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//executando fun��es js
			RequestContext.getCurrentInstance().execute("msgDownload()");
			RequestContext.getCurrentInstance().execute("listUpdateFile2()");

			//sen�o acessa a essa condi��o
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

			//sen�o se o nivel de acesso do usu�rio for igual a 1 ou igual a 6
			//tem permiss�o para acessar a condi��o
		}else if((data.getEditTable() == false)||timestamp.before(timestamp2)) {

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

			//sen�o se for igual a true acesso bloqueado para realizar edi��o
		}else if((data.getEditTable() == true && timestamp.after(timestamp2))) {

			//executando fun��o javascript

			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//se o nome do usuario local, for igual o nome da pessoa que esta editando
			//a ocorrencia acessa pode acessar a essa condi��o
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

				//sen�o se o nivel de acesso do usu�rio for igual a 1 ou igual a 6
				//tem permiss�o para acessar a condi��o
			}else {
				//btn menu
				save = true;
				alterar = true;
				reset = true;
				new_ = false;
				fields = true;
				edit = true;
				table = true;
				//executando fun��es js
				RequestContext.getCurrentInstance().execute("msgDownload()");
				RequestContext.getCurrentInstance().execute("listUpdateFile2()");
			}

			int id = getValue();

			//criando caminho da sele��o da pasta.
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

		OccurencesDao2 occ = new OccurencesDao2();
		String id = data.getSinistro();
		String lastData = date;
		String user = nameUser;
		

		TimeUnit.MINUTES.sleep(1);
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("btnEnable();");
		request.execute("inputs()");
		System.out.println("pronto");

	}

	public String getImagePath(String image) {

		try {

			Path path = Paths.get(image);								
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);

		} catch (IOException e) {											
			return "";
		}}}






