package br.com.tracevia.webapp.controller.occ;

import java.awt.Image;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import br.com.tracevia.webapp.dao.occ.OccurrencesDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.occ.OccurrencesData;
import br.com.tracevia.webapp.model.occ.OccurrencesDetails;
import br.com.tracevia.webapp.util.LocaleUtil;
@ManagedBean(name="occurrencesBean")
@ViewScoped
public class OccurrencesBean {

	private OccurrencesData data;
	private OccurrencesDetails details;

	OccurrencesDAO dao;
	LocaleUtil occLabel, occMessages;

	private boolean save, edit, new_, reset, fields, table, alterar;

	private String action, title_modal, message_modal;

	private String mainPath, localPath, occNumber, path, way, downloadPath, pathDownload; 
	private String getFile, fileDelete, fileUpdate, pathImage, absoluteImage;
	private String[] listarFile, listUpdate, imagem, FileName;
	private File arquivos[], directory, fileWay;
	private int bytes, total;
	private int value, content;
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

		OccurrencesDAO dao = new OccurrencesDAO();
		value = dao.GetId();
		value += 1;
		data = new OccurrencesData();
		data.setData_number(String.valueOf(value));

		return value;

	}

	public void cadastroOcorrencia() throws Exception {

		boolean sucess = false;

		OccurrencesDAO dao = new OccurrencesDAO();
		occNumber = dao.cadastroOcorrencia(data);

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
			//resetOccurrencesData(); // Clean form

			occurrences = dao.listarOcorrencias(); // List occurrences    

			sucess = dao.updateFilePath(localPath, occNumber); // Update path on Data Base

			System.out.println("PATH UPDATED: "+sucess);
		}

	}


	public void atualizarOcorrencia() throws Exception {

		boolean status = false;

		OccurrencesDAO dao = new OccurrencesDAO();

		status = dao.atualizarOcorrencia(data);

		//org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");

		if(status) {

			//btn
			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;

			//listarFile = null;
			listUpdate = null;
			total = 0;

			occurrences = dao.listarOcorrencias();
			listUpdate = null;
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

		//método
		deleteDirectory();

	}
	public void getRowValue() {

		//System.out.println(rowkey);
		//System.out.println(selectedRow);

		try {

			OccurrencesDAO dao = new OccurrencesDAO();
			data = dao.buscarOcorrenciaPorId(rowkey);

		}catch(Exception ex){
			ex.printStackTrace();
		}

		System.out.println("SelectRow: "+selectedRow);

		//Se a linha da table estiver selecionada:
		if(selectedRow) {

			save = true;
			alterar = true;
			edit = false;
			new_ = true;
			reset = true;
			fields = true; 

		}else {

			save = true;
			alterar = true;
			reset = true;
			new_ = false;
			fields = true;
			edit = true;
			table = true;
			//function js
			//org.primefaces.context.RequestContext.getCurrentInstance().execute("reload()");
		}
	}

	//Habilitar bot�es (Default)
	//Action NOVO
	public void btnEnable() throws Exception {

		edit = false;
		save = false;
		alterar = false;
		reset = false;
		new_ = true;
		fields = false;
		action = "save";

		//Global
		value = pegarId();

		if(value > 0) {

			String occNumber = (String.valueOf(value));

			//CREATE LOCAL PATH
			localPath = localPath(occNumber);
			createFileFolder(mainPath, localPath);	

			//Criou a pasta ok

		}

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
		path = local.getYear()+"\\"+local.getMonthValue()+"\\OCC_"+occ_number;

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
	}

	public String[] listingUpdate() {

		org.primefaces.context.RequestContext.getCurrentInstance().execute("alterarBtn()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("bloquerTable()");
		
		//pega o id da tabela.
		int id = getValue();

		//chamando mês e ano, para passar para o caminho.
		DateTimeApplication dddd = new DateTimeApplication();
		LocalDate data = dddd.localeDate();

		//criando caminho da seleção da pasta.
		way = data.getYear()+"\\"+data.getMonthValue()+"\\OCC_"+id;

		//caminho criado
		fileWay = new File(mainPath+way);
		System.out.println("Estamos aqui: > "+ fileWay);

		int x = 0;

		arquivos = fileWay.listFiles();

		listUpdate = new String[arquivos.length];
		total = arquivos.length;
		while (x != arquivos.length){

			listUpdate[x] = arquivos[x].getName();

			System.out.println("Position: "+x+" < Arquivo na pasta > "+ listUpdate[x]);

			x++;

		}

		fields = false;
		reset = false;
		save = true;
		edit = true;
		alterar = false;
		action = "update";

		return listUpdate;
	}

	public String[] listFiles() throws Exception{

		//variável do tipo int
		content = 0;

		//local do armazenamento do arquivo
		directory = new File(mainPath+path+"\\");
		
		//listar arquivos
		arquivos = directory.listFiles();

		//pega o total de arquivos no diretório
		total = arquivos.length;
		System.out.println("Total Files: "+ total);

		listarFile = new String[arquivos.length];
		
		//tratando imagem
			/*OccurrencesBean pegar= new OccurrencesBean();
			int id = pegar.pegarId();
			DateTimeApplication dddd = new DateTimeApplication();
			LocalDate data = dddd.localeDate();
			String imagePath = data.getYear()+"/"+data.getMonthValue()+"/OCC_"+id+"/";
				*/
		
		//lopping para pegar arquivos dentro do diretório
		while (content != arquivos.length){
			//pega arquivo pelo nome
			listarFile[content] = arquivos[content].getName();
			//passando a imagem
			//absoluteImage = pathImage+imagePath+listarFile[content];
			
			System.out.println(content+": Arquivo recebido "+ listarFile[content]);
			//System.out.println("Testando aqui: "+absoluteImage);

			content++;
			
		} 
		
		return listarFile;
	}
	public String caminhoImage(String myImg) throws Exception{
		
		//tratando imagem
		OccurrencesBean pegar= new OccurrencesBean();
		int id = pegar.pegarId();
		DateTimeApplication dddd = new DateTimeApplication();
		LocalDate data = dddd.localeDate();
		String imagePath = data.getYear()+"/"+data.getMonthValue()+"/OCC_"+id+"/";
			
		absoluteImage = pathImage+imagePath+myImg;
		
		System.out.println("Testando aqui: "+ absoluteImage + ">>>"+ myImg);
		
		return absoluteImage;
		
	}
	//Buscando imagem
	public String getImageUpload(String myImg) throws Exception{
		
		//gerando o caminho onde se encontra a imagem
		OccurrencesBean pegar= new OccurrencesBean();
		int id = pegar.pegarId();
		DateTimeApplication dddd = new DateTimeApplication();
		LocalDate data = dddd.localeDate();
		String imagePath = data.getYear()+"/"+data.getMonthValue()+"/OCC_"+id+"/";
			
		absoluteImage = pathImage+imagePath+myImg;
		
		System.out.println("Testando aqui: "+ absoluteImage);
		
		return absoluteImage;
		
	}
public String caminhoImageUpdate(String myImg) throws Exception{
		
		//gerando o caminho onde se encontra a imagem
		int id = getValue();
		DateTimeApplication dddd = new DateTimeApplication();
		LocalDate data = dddd.localeDate();
		String imagePath = data.getYear()+"/"+data.getMonthValue()+"/OCC_"+id+"/";
			
		absoluteImage = pathImage+imagePath+myImg;
		
		System.out.println("Testando aqui: "+ id);
		
		return absoluteImage;
		
	}
	
	public void deleteFileUpdate(String file) {
		try {
			fileWay = new File(mainPath+way+"\\");
			System.out.println(fileWay+file);

			boolean check = new File(fileWay, file).exists();

			if(check) {

				File currentFile = new File(fileWay, file);

				currentFile.delete();

				System.out.println("Arquivo excluido com sucesso! ");

				total = total - 1;
				listingUpdate();

				org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
				org.primefaces.context.RequestContext.getCurrentInstance().execute("msgDelete()");
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

				total -=  1;
				listFiles();
				
				org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
				org.primefaces.context.RequestContext.getCurrentInstance().execute("msgDelete()");
				
			}

		}catch(Exception ex) {
			org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
			System.out.println("Erro ao excluir arquivo");
		}

	}

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
			
			//chamando mês e ano, para passar para o caminho.
			DateTimeApplication dddd = new DateTimeApplication();
			LocalDate data = dddd.localeDate();

			//criando caminho da seleção da pasta.
			String absoluteFile = data.getYear()+"/"+data.getMonthValue()+"/OCC_"+id+"/"+fileName;
			
			//caminho onde pegamos o arquivo
			URL url = new URL(downloadPath+absoluteFile);
			String arquivos = url.getQuery();
			
		  	//caminho onde o arquivo será guardado
	        File file = new File(pathDownload+fileName);

	        InputStream is = url.openStream();
	        FileOutputStream fos = new FileOutputStream(file);

	        int bytes = 0;
	        
	        while ((bytes = is.read()) != -1) {
	            fos.write(bytes);
	        }

	        is.close();

	        fos.close();
	    
		org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("msgDownload()");
		System.out.println("Download realizado: "+fileName);
	}
	public void downloadUpdate(String fileName) throws Exception {
		
			//pegando o id
			int id = getValue();
			
			//chamando mês e ano, para passar para o caminho.
			DateTimeApplication dddd = new DateTimeApplication();
			LocalDate data = dddd.localeDate();

			//criando caminho da seleção da pasta.
			String absoluteFile = data.getYear()+"/"+data.getMonthValue()+"/OCC_"+id+"/"+fileName;
			
			//caminho onde pegamos o arquivo
			URL url = new URL(downloadPath+absoluteFile);
			String arquivos = url.getQuery();
			
		  	//caminho onde o arquivo será guardado
	        File file = new File(pathDownload+fileName);

	        InputStream is = url.openStream();
	        FileOutputStream fos = new FileOutputStream(file);

	        int bytes = 0;
	        
	        while ((bytes = is.read()) != -1) {
	            fos.write(bytes);
	        }

	        is.close();

	        fos.close();
	        
	    	fields = false;
			reset = false;
			save = true;
			edit = true;
			alterar = false;
			action = "update";
			
		org.primefaces.context.RequestContext.getCurrentInstance().execute("alterarBtn()");   
		org.primefaces.context.RequestContext.getCurrentInstance().execute("mostrarTab2()");
		org.primefaces.context.RequestContext.getCurrentInstance().execute("msgDownload()");
		System.out.println("Download realizado: "+fileName);
	}
	public void pegarImagem() {
		
	}
	
}