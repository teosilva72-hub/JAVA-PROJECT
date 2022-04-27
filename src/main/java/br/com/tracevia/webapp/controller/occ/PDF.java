package br.com.tracevia.webapp.controller.occ;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.poi.ss.util.ImageUtils;
import org.apache.poi.util.IOUtils;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.primefaces.context.RequestContext;

import com.groupdocs.conversion.internal.c.a.c.Border;
import com.groupdocs.conversion.internal.c.a.pd.Cell;
import com.groupdocs.conversion.internal.c.a.w.VerticalAlignment;
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

import br.com.tracevia.webapp.dao.occ.TuxpanDAO;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.occ.TuxpanOccModel;
import br.com.tracevia.webapp.util.ImageUtil;
@ManagedBean(name="OccPdf")
public class PDF {

	public String selectPdf() {

		return id;
	}
	public TuxpanOccModel model() {
		dao = new TuxpanDAO();
		date = new TuxpanOccModel();
		try {
			date = dao.select(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	public void whichPdf() throws Exception {
		if(model().getType_report().equals("1"))
			pdfOcc();
		else if(model().getType_report().equals("2"))
			pdfSin();
	}
	public void pdfOcc() throws Exception {
		Document document = new Document();
		TranslationMethods trad = new TranslationMethods();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); //SOLUTION

		try {			 	  
			PdfWriter writer = PdfWriter.getInstance(document, baos);

			//gera o arquivo		
			document.open();			
			//formato da folha
			document.setPageSize(PageSize.A4);

			//chamando a imagem
			logo = ImageUtil.getInternalImagePath("images", "files", RoadConcessionaire.externalImagePath);

			if(!logo.equals("")) {
				Image tuxpanL = Image.getInstance(logo);
				tuxpanL.setAbsolutePosition(70, 770);
				tuxpanL.scaleAbsolute (80, 30);
				document.add(tuxpanL);
			}

			PdfContentByte canvas = writer.getDirectContent();
			Paragraph conteudo = new Paragraph();

			// Assim criaremos uma linha em branco
			conteudo.add(new Paragraph(" "));

			PdfPTable table = new PdfPTable(2);
			Paragraph title1 = new Paragraph("REGISTRO DE ACCIDENTE \n AUTOPISTA "+RoadConcessionaire.roadConcessionaire.toUpperCase()+"\nSEGUROS SURA, S.A de C.V.", FontFactory.getFont(FontFactory.TIMES_ROMAN,9, Font.BOLD, BaseColor.BLACK));

			title1.setIndentationLeft(170);
			table.setTotalWidth(new float[]{ 250, 200 });
			table.setLockedWidth(true);

			Font formatText1 = new Font(Font.FontFamily.TIMES_ROMAN, 9f, Font.BOLD);
			document.add(new Paragraph(title1));
			//table.writeSelectedRows(0, 2, 0, -1, canvas);

			table.addCell(new PdfPCell(new Phrase("Plaza de Cobro", formatText1)));
			table.addCell(new Phrase(model().getPlz_cobro(), formatText1));
			table.addCell(new PdfPCell(new Phrase("Folio Secuencial", formatText1)));
			table.addCell(new Phrase(model().getFolio_sec(), formatText1));
			table.addCell(new PdfPCell(new Phrase("Reporte", formatText1)));
			table.addCell(new Phrase(model().getReporte(), formatText1));
			table.addCell(new PdfPCell(new Phrase("Siniestro", formatText1)));
			table.addCell(new Phrase(model().getSiniestro(), formatText1));
			table.addCell(new PdfPCell(new Phrase("Fecha", formatText1)));
			table.addCell(new PdfPCell(new Phrase(model().getFecha(), formatText1)));
			table.addCell(new PdfPCell(new Phrase("Hora", formatText1)));
			table.addCell(new PdfPCell(new Phrase(model().getHora(), formatText1)));
			table.addCell(new PdfPCell(new Phrase("Dirección Y/O Trayecto", formatText1)));
			table.addCell(new PdfPCell(new Phrase(model().getDireccion(), formatText1)));
			table.addCell(new PdfPCell(new Phrase("Kilómetro de Registro", formatText1)));
			table.addCell(new PdfPCell(new Phrase(model().getKm_reg(), formatText1)));
			table.addCell(new PdfPCell(new Phrase("Kilómetro Inicial", formatText1)));
			table.addCell(new PdfPCell(new Phrase(model().getKm_inicial(), formatText1)));
			table.addCell(new PdfPCell(new Phrase("Kilómetro Final", formatText1)));
			table.addCell(new PdfPCell(new Phrase(model().getKm_final(), formatText1)));
			table.addCell(new PdfPCell(new Phrase("Póliza Por Afectar", formatText1)));
			table.addCell(new PdfPCell(new Phrase(model().getPoliza(), formatText1)));
			table.addCell(new PdfPCell(new Phrase("Hora de Registro a Cabina", formatText1)));
			table.addCell(new PdfPCell(new Phrase(model().getFecha_cab(), formatText1)));
			table.addCell(new PdfPCell(new Phrase("Hora de Arribo de Ajustador", formatText1)));
			table.addCell(new PdfPCell(new Phrase(model().getHora_ajust(), formatText1)));

			document.add(conteudo);
			document.add(table);
			document.add(conteudo);
			//
			Paragraph title2 = new Paragraph("Vehículos Involucrados");
			PdfPTable table1 = new PdfPTable(4);
			table1.setTotalWidth(500);
			table1.setLockedWidth(true);
			document.add(title2);

			String[] tip_veh = model().getTipo_veh_inv().split(",");
			String[] tip_eje = model().getNum_eje_veh_inv().split(",");

			for(int j=0; j < tip_veh.length; j++){
				table1.addCell(new PdfPCell(new Phrase("Tipo de Vehículo", formatText1)));
				table1.addCell(new PdfPCell(new Phrase(tip_veh[j], formatText1)));
				for(int k =0; k < tip_eje.length; k++) {
					if(k == j) {
						table1.addCell(new PdfPCell(new Phrase("Número de ejes de la unidad", formatText1)));
						table1.addCell(new PdfPCell(new Phrase(tip_eje[k], formatText1)));
					}
				}
			}
			document.add(conteudo);
			document.add(table1);

			PdfPTable table2 = new PdfPTable(7);
			table2.setTotalWidth(500);
			table2.setLockedWidth(true);
			table2.setHeaderRows(1);
			String[] num_veh = model().getNum_tp_veh().split(",");
			String[] marca_veh = model().getMarca_tp_veh().split(",");
			String[] tip_tp_veh = model().getTipo_tp_veh().split(",");
			String[] model_veh = model().getModel_tp_veh().split(",");
			String[] cor_veh = model().getColor().split(",");
			String[] placa_veh = model().getPlaca_estado().split(",");
			String[] tel = model().getTel().split(",");

			table2.addCell(new PdfPCell(new Phrase("No.", formatText1)));
			table2.addCell(new PdfPCell(new Phrase("Marca", formatText1)));
			table2.addCell(new PdfPCell(new Phrase("Tipo", formatText1)));
			table2.addCell(new PdfPCell(new Phrase("Modelo", formatText1)));
			table2.addCell(new PdfPCell(new Phrase("Color", formatText1)));
			table2.addCell(new PdfPCell(new Phrase("Placas/Estado", formatText1)));
			table2.addCell(new PdfPCell(new Phrase("Telefone", formatText1)));
			for(int a=0; a<num_veh.length;a++) {
				table2.addCell(new PdfPCell(new Phrase(num_veh[a], formatText1)));
				table2.addCell(new PdfPCell(new Phrase(marca_veh[a], formatText1)));
				table2.addCell(new PdfPCell(new Phrase(tip_tp_veh[a], formatText1)));
				table2.addCell(new PdfPCell(new Phrase(model_veh[a], formatText1)));
				table2.addCell(new PdfPCell(new Phrase(cor_veh[a], formatText1)));
				table2.addCell(new PdfPCell(new Phrase(placa_veh[a], formatText1)));
				table2.addCell(new PdfPCell(new Phrase(tel[a], formatText1)));
			}

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
			String[] num_per = model().getId_person().split(",");
			String[] name_per = model().getNombre().split(",");
			String[] edad_per = model().getEdad().split(",");
			String[] cond_per = model().getCondiciones().split(",");
			for(int i = 0; i< num_per.length; i++) {
				table3.addCell(new PdfPCell(new Phrase(num_per[i], formatText1)));
				table3.addCell(new PdfPCell(new Phrase(name_per[i], formatText1)));
				table3.addCell(new PdfPCell(new Phrase(edad_per[i], formatText1)));
				table3.addCell(new PdfPCell(new Phrase(cond_per[i], formatText1)));

			}

			document.add(conteudo);
			document.add(table3);
			document.add(conteudo);

			Paragraph title5 = new Paragraph("");
			PdfPTable table4 = new PdfPTable(2);
			table4.setTotalWidth(500);
			table4.setTotalWidth(new float[]{ 200, 300 });
			table4.setLockedWidth(true);
			table4.addCell(new PdfPCell(new Phrase("MOTIVO DEL ACCIDENTE:", formatText1)));
			table4.addCell("");

			document.add(table4);

			PdfPTable table5 = new PdfPTable(3);
			table5.setTotalWidth(500);
			table5.setTotalWidth(new float[]{ 50, 150, 300 });
			table5.setLockedWidth(true);
			for(int i=0;i<19;i++) {
				if(i == 0) {
					table5.addCell(new PdfPCell(new Phrase("A", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("SEMOVIENTE", formatText1)));
					table5.addCell(new PdfPCell(new Phrase(model().getSemoviente(), formatText1)));
				}
				if(i == 1) {
					table5.addCell(new Phrase("B", formatText1));
					table5.addCell(new PdfPCell(new Phrase("TRABAJOS DE\nCONSERVACIÓN", formatText1)));
					table5.addCell(new PdfPCell(new Phrase(model().getTrab_conserv(), formatText1)));
				}
				if(i == 2) {
					table5.addCell(new PdfPCell(new Phrase("C", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("LLUVIA, GRANIZO", formatText1)));
					table5.addCell(new PdfPCell(new Phrase(model().getLluvia_granizo(), formatText1)));
				}
				if(i == 3) {
					table5.addCell(new PdfPCell(new Phrase("D", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("NEBLINA", formatText1)));
					table5.addCell(new PdfPCell(new Phrase(model().getNeblina(), formatText1)));
				}
				if(i == 4) {
					table5.addCell(new PdfPCell(new Phrase("E", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("VANDALISMO", formatText1)));
					table5.addCell(new PdfPCell(new Phrase(model().getVandalismo(), formatText1)));
				}
				if(i == 5) {
					table5.addCell(new PdfPCell(new Phrase("F", formatText1)));
					table5.addCell(new PdfPCell(new Phrase("OTRO", formatText1)));
					table5.addCell(new PdfPCell(new Phrase(model().getOtro(), formatText1)));
				}
			}
			document.add(table5);
			document.add(conteudo);

			PdfPTable tableObs = new PdfPTable(1);
			tableObs.setTotalWidth(500);
			tableObs.setTotalWidth(new float[]{ 500 });
			tableObs.setLockedWidth(true);
			tableObs.addCell(new PdfPCell(new Phrase("Observación", formatText1)));
			tableObs.addCell(new PdfPCell(new Phrase(model().getObs_occ(), formatText1)));
			document.add(tableObs);
			document.add(conteudo);

			PdfPTable table_ass = new PdfPTable(2);
			table_ass.setTotalWidth(500);
			table_ass.setTotalWidth(new float[]{ 250, 250 });
			table_ass.setLockedWidth(true);

			Paragraph title__ = new Paragraph(new Phrase(10F, "OPERADOR DEL C. CONTROL", FontFactory.getFont(FontFactory.COURIER_BOLD, 10F)));
			Paragraph title_ = new Paragraph(new Phrase(10F, "AJUSTADOR", FontFactory.getFont(FontFactory.COURIER_BOLD, 10F)));
			table_ass.addCell(title__);
			table_ass.addCell(title_);
			table_ass.addCell(new PdfPCell(new Phrase("\nNOMBRE: ______________________", formatText1)));
			table_ass.addCell(new PdfPCell(new Phrase("\nNOMBRE: ______________________", formatText1)));
			table_ass.addCell(new PdfPCell(new Phrase("\nFIRMA:  ______________________", formatText1)));
			table_ass.addCell(new PdfPCell(new Phrase("\nFIRMA:  ______________________", formatText1)));
			document.add(table_ass);			

		}
		catch(DocumentException de) {
			System.err.println(de.getMessage());
		}catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		document.close();


		// DOWNLOAD

		externalContext.setResponseContentType("application/pdf");
		externalContext.setResponseHeader("Content-Disposition","attachment; filename=\""+"report_occ_"+model().getId()+".pdf\"");

		externalContext.setResponseContentLength(baos.size());

		OutputStream responseOutputStream = externalContext.getResponseOutputStream();  
		baos.writeTo(responseOutputStream);
		responseOutputStream.flush();
		responseOutputStream.close();


		facesContext.responseComplete(); 

	}

	public boolean pdfSin() throws Exception {
		Document document = new Document();
		TranslationMethods trad = new TranslationMethods();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); //SOLUTION

		try {			 	  

			PdfWriter writer = PdfWriter.getInstance(document, baos);

			document.open();			
			document.setPageSize(PageSize.A4);

			//
			logo = ImageUtil.getInternalImagePath("images", "files", RoadConcessionaire.externalImagePath);
			if(!logo.equals("")) {
				Image tuxpanL = Image.getInstance(logo);
				tuxpanL.setAbsolutePosition(30, 770);
				tuxpanL.scaleAbsolute (90, 30);
				document.add(tuxpanL);
			}
			Font formatText = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD);
			PdfPTable title = new PdfPTable(1);
			Paragraph conteudo = new Paragraph();
			conteudo.add(new Paragraph(" "));
			title.setTotalWidth(new float[]{500});
			title.setLockedWidth(true);
			title.addCell(new PdfPCell(new Phrase("REPORTE PRELIMINAR DE SINIESTRO", formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);
			document.add(conteudo);
			document.add(conteudo);
			document.add(conteudo);
			document.add(title);
			document.add(conteudo);
			//

			PdfPTable table0 = new PdfPTable(5);
			table0.setTotalWidth(500);
			table0.setTotalWidth(new float[]{ 50, 120, 50, 130, 150 });
			table0.setLockedWidth(true);
			table0.addCell(new PdfPCell(new Phrase("Siniestro:", formatText))).setBorderColor(BaseColor.WHITE);
			table0.addCell(new PdfPCell(new Phrase(model().getSiniestro(), formatText))).setBorder(Rectangle.BOTTOM);
			table0.addCell(new PdfPCell(new Phrase("", formatText))).setBorderColor(BaseColor.WHITE);
			table0.addCell(new PdfPCell(new Phrase("Póliza aplicada (probable):", formatText))).setBorderColor(BaseColor.WHITE);
			table0.addCell(new PdfPCell(new Phrase(model().getPoliza(), formatText))).setBorder(Rectangle.BOTTOM);
			table0.addCell(new PdfPCell(new Phrase("Hora:", formatText))).setBorderColor(BaseColor.WHITE);
			table0.addCell(new PdfPCell(new Phrase(model().getHora(), formatText))).setBorder(Rectangle.BOTTOM);
			table0.addCell(new PdfPCell(new Phrase("", formatText))).setBorderColor(BaseColor.WHITE);
			table0.addCell(new PdfPCell(new Phrase("                                Fecha:", formatText))).setBorderColor(BaseColor.WHITE);
			table0.addCell(new PdfPCell(new Phrase(model().getFecha(), formatText))).setBorder(Rectangle.BOTTOM);
			document.add(table0);
			document.add(conteudo);
			//
			PdfPTable table1 = new PdfPTable(5);
			table1.setTotalWidth(500);
			table1.setTotalWidth(new float[]{ 50, 150, 50, 100, 150 });
			table1.setLockedWidth(true);
			String[] veh = model().getVeh_sin().split(",");
			String[] ocup = model().getOcupantes_sin().split(",");
			for(int i=0; i< veh.length;i++) {

				table1.addCell(new PdfPCell(new Phrase("Vehículo " + i, formatText))).setBorderColor(BaseColor.WHITE);
				table1.addCell(new PdfPCell(new Phrase(veh[i],formatText))).setBorder(Rectangle.BOTTOM);
				table1.addCell(new PdfPCell(new Phrase("", formatText))).setBorderColor(BaseColor.WHITE);
				table1.addCell(new PdfPCell(new Phrase("          Ocupantes: ", formatText))).setBorderColor(BaseColor.WHITE);
				table1.addCell(new PdfPCell(new Phrase(ocup[i], formatText))).setBorder(Rectangle.BOTTOM);
			}
			document.add(table1);
			document.add(conteudo);

			PdfPTable table2 = new PdfPTable(5);
			table2.setTotalWidth(500);
			table2.setTotalWidth(new float[]{ 70, 130, 50, 100, 150 });
			table2.setLockedWidth(true);
			table2.addCell(new PdfPCell(new Phrase("KM / Sentido ", formatText))).setBorderColor(BaseColor.WHITE);
			table2.addCell(new PdfPCell(new Phrase(model().getKm_reg()+" / "+model().getDireccion(), formatText))).setBorder(Rectangle.BOTTOM);
			table2.addCell(new PdfPCell(new Phrase("", formatText))).setBorderColor(BaseColor.WHITE);

			table2.addCell(new PdfPCell(new Phrase("               Causas:", formatText))).setBorderColor(BaseColor.WHITE);
			table2.addCell(new PdfPCell(new Phrase(model().getCausas_sin(), formatText))).setBorder(Rectangle.BOTTOM);
			table2.addCell(new PdfPCell(new Phrase("Lesionados: ", formatText))).setBorderColor(BaseColor.WHITE);
			table2.addCell(new PdfPCell(new Phrase(model().getLesionados(), formatText))).setBorder(Rectangle.BOTTOM);
			table2.addCell(new PdfPCell(new Phrase("", formatText))).setBorderColor(BaseColor.WHITE);
			table2.addCell(new PdfPCell(new Phrase("            Muertos: ", formatText))).setBorderColor(BaseColor.WHITE);
			table2.addCell(new PdfPCell(new Phrase(model().getMortos(), formatText))).setBorder(Rectangle.BOTTOM);

			document.add(table2);
			document.add(conteudo);

			PdfPTable rsa = new PdfPTable(1);
			rsa.setTotalWidth(500);
			rsa.setTotalWidth(new float[]{ 500 });
			rsa.setLockedWidth(true);
			rsa.addCell(new PdfPCell(new Phrase("Folio RSA: "+model().getFolio_sec(), formatText))).setBorderColor(BaseColor.WHITE);
			document.add(rsa);
			document.add(conteudo);

			PdfPTable table3 = new PdfPTable(1);
			table3.setTotalWidth(500);
			table3.setTotalWidth(new float[]{500});
			table3.setLockedWidth(true);
			title.addCell(new PdfPCell(new Phrase("DAÑOS EN INFRAESTRUCTURA", formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);
			document.add(table3);
			document.add(conteudo);

			PdfPTable table4 = new PdfPTable(3);
			table4.setTotalWidth(500);
			table4.setTotalWidth(new float[]{ 250, 125, 125 });
			table4.setLockedWidth(true);
			String[] obs = model().getObs_sin().split(",");

			table4.addCell(new PdfPCell(new Phrase("DAÑOS EN", formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);
			table4.addCell(new PdfPCell(new Phrase("SI/NO",formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);
			table4.addCell(new PdfPCell(new Phrase("OBSERVACIONES", formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);

			table4.addCell(new PdfPCell(new Phrase("DEFENSA METÁLIZA", formatText)));
			table4.addCell(new PdfPCell(new Phrase(model().getDef_metal(),formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);
			table4.addCell(new PdfPCell(new Phrase(obs[0], formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);

			table4.addCell(new PdfPCell(new Phrase("SEÑALAMIENTO", formatText)));
			table4.addCell(new PdfPCell(new Phrase(model().getSenal(),formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);
			table4.addCell(new PdfPCell(new Phrase(obs[1], formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);

			table4.addCell(new PdfPCell(new Phrase("DAÑOS EN PAVIMENTO", formatText)));
			table4.addCell(new PdfPCell(new Phrase(model().getDano_pav(),formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);
			table4.addCell(new PdfPCell(new Phrase(obs[2], formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);

			table4.addCell(new PdfPCell(new Phrase("DAÑOS EN CORTES O TERRAPLENES", formatText)));
			table4.addCell(new PdfPCell(new Phrase(model().getDanos_cort_trr(),formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);
			table4.addCell(new PdfPCell(new Phrase(obs[3], formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);

			table4.addCell(new PdfPCell(new Phrase("DAÑOS EN OBRA COMPLEMENTARIA", formatText)));
			table4.addCell(new PdfPCell(new Phrase(model().getDanos_obr_compl(),formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);
			table4.addCell(new PdfPCell(new Phrase(obs[4], formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);

			table4.addCell(new PdfPCell(new Phrase("DAÑOS EN PLAZAS DE COBRO", formatText)));
			table4.addCell(new PdfPCell(new Phrase(model().getPlz_cobro(),formatText)));
			table4.addCell(new PdfPCell(new Phrase(obs[5], formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);

			table4.addCell(new PdfPCell(new Phrase("OTROS (AMBULANCIA, ABANDERAMIENTO OPERADORA", formatText)));
			table4.addCell(new PdfPCell(new Phrase(model().getOtros_sin(),formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);
			table4.addCell(new PdfPCell(new Phrase(obs[6], formatText))).setHorizontalAlignment(Element.ALIGN_CENTER);
			document.add(table4);
			document.add(conteudo);


			TuxpanOcc method = new TuxpanOcc();
			String filter = method.filter(model().getReporte(), model().getSiniestro(), model().getFolio_sec());
			String mainPath = method.createFolder(filter);
			String[] imgs = method.listarFiles(mainPath);
			String[] x = new String[imgs.length];
			PdfPTable table5 = new PdfPTable(2);
			table5.setTotalWidth(500);
			table5.setTotalWidth(new float[]{250, 250});

			table5.setLockedWidth(true);
			for(int i=0;i<imgs.length;i++) {
				x[i] = mainPath+imgs[i];
				img = x[i];
				Image imgP = Image.getInstance(img);
				imgP.setAbsolutePosition(30, 770);
				imgP.scaleAbsolute (100,100);

				if(imgs.length == 1) {table5.addCell(imgP);table5.addCell("");}
				else table5.addCell(imgP);
			}

			document.add(table5);

		}
		catch(DocumentException de) {
			System.err.println(de.getMessage());
		}catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		document.close();


		// DOWNLOAD

		externalContext.setResponseContentType("application/pdf");
		externalContext.setResponseHeader("Content-Disposition","attachment; filename=\""+"report_sin_"+model().getId()+".pdf\"");

		externalContext.setResponseContentLength(baos.size());

		OutputStream responseOutputStream = externalContext.getResponseOutputStream();  
		baos.writeTo(responseOutputStream);
		responseOutputStream.flush();
		responseOutputStream.close();


		facesContext.responseComplete(); 
		return check;
	}

	private boolean check = false;
	private String logo, id, img;
	private TuxpanOccModel date;
	private TuxpanDAO dao;
	public String getLogo() {
		try {
			Path path = Paths.get(logo);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		}catch (Exception e) {
			return "";
		}
	}
	public String getImg() {
		try {
			Path path = Paths.get(img);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		}catch (Exception e) {
			return "";
		}
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}