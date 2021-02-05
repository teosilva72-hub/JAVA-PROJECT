package br.com.tracevia.webapp.controller.sat;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.dao.sat.SATinformationsDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.sat.SAT;

@ManagedBean(name = "satLinearView")
@ViewScoped
public class SATBuildLinear {

	static List<? extends Equipments> satList;
	List<SAT> satListValues, satStatus;

	public List<? extends Equipments> getSatList() {
		return satList;
	}

	public List<SAT> getSatListValues() {
		return satListValues;
	}

	public List<SAT> getSatStatus() {
		return satStatus;
	}

	@PostConstruct
	public void initalize() {

		CreateLinearEquipment();

	}

	public void CreateLinearEquipment() {
		
		
		//System.out.println("CON: "+RoadConcessionaire.roadConcessionaire);

		try {

			try {

				SATinformationsDAO satDAO = new SATinformationsDAO();

				boolean status15 = true, values15 = true, status15Before = false, values15Before = false;
				boolean pass = true;

				// LISTAS
				satList = new ArrayList<SAT>();
				satListValues = new ArrayList<SAT>();
				satStatus = new ArrayList<SAT>();

				// LISTAR AUXILIARES
				List<SAT> satListValuesAux = new ArrayList<SAT>();
				List<SAT> satListStatusAux = new ArrayList<SAT>();

				// SAT OBJECT
				SAT sat = new SAT();

				///////////////////////////////
				// SAT EQUIPMENTS
				//////////////////////////////
				satList = sat.ListLinearEquipments("sat");

				/////////////////////////////
				// SAT STATUS
				///////////////////////////

				// PREENCHE LISTA COM STATUS DOS ULTIMOS 15 MINUTOS
				satListStatusAux = satDAO.SATstatus15();

				// CASO N�O ENCONTRE NENHUM STATUS DO ULTIMOS 15 MINUTOS
				// PREENCHE LISTA COM STATUS DOS 15 MINUTOS ANTERIORES
				if (satListStatusAux.isEmpty()) {
					satListStatusAux = satDAO.SATstatus30();
					status15 = false;
				}

				// VERIFICA SE A LISTA CONTINUA VAZIA OU SE H� STATUS
				if (!status15 && !satListStatusAux.isEmpty())
					status15Before = true;

				// CASO HAJA DADOS DOS 15 MINUTOS ANTERIORES EXECUTA ESSE BLOCO
				if (status15Before) {

					for (int s = 0; s < satList.size(); s++) { // FOR START

						System.out.println(satList.get(s).getEquip_id());

						SAT satListObj = new SAT();
						pass = true;

						for (int r = 0; r < satListStatusAux.size(); r++) {
							if (satListStatusAux.get(r).getEquip_id() == satList.get(s).getEquip_id()) {

								satListObj.setStatus(satListStatusAux.get(r).getStatus());

								satStatus.add(satListObj);
								satListStatusAux.remove(r);
								pass = false;

								break;

							}
						}

						if (pass) {

							satListObj.setEquip_id(satList.get(s).getEquip_id());
							satListObj.setStatus(0);

							satStatus.add(satListObj);
						}
					} // FOR END

				} else if (status15) {

					for (int s = 0; s < satList.size(); s++) { // FOR START

						System.out.println(satList.get(s).getEquip_id());

						SAT satListObj = new SAT();
						pass = true;

						for (int r = 0; r < satListStatusAux.size(); r++) {
							if (satListStatusAux.get(r).getEquip_id() == satList.get(s).getEquip_id()) {

								satListObj.setStatus(satListStatusAux.get(r).getStatus());

								satStatus.add(satListObj);
								satListStatusAux.remove(r);
								pass = false;

								break;

							}
						}

						if (pass) {

							satListObj = satDAO.SATstatus15Before(satList.get(s).getEquip_id());

							if (satListObj.getEquip_id() != 0)
								satStatus.add(satListObj);
							else {

								SAT satListObj1 = new SAT();

								satListObj1.setEquip_id(satList.get(s).getEquip_id());
								satListObj1.setStatus(0);

								satStatus.add(satListObj1);

							}
						}
					} // FOR END

				} else
					intializeNullStatus(satList); // CASO N�O EXISTA VALORES VAI INICIALIZAR COM ZEROS TODOS
													// EQUIPAMENTOS

				///////////////////////////
				// SAT STATUS
				/////////////////////////

				/////////////////////////
				// SAT VALUES
				////////////////////////

				// PREENCHE LISTA COM DADOS DOS ULTIMOS 15 MINUTOS
				satListValuesAux = satDAO.RealTimeSATinfo();

				// CASO N�O ENCONTRE NENHUM DADO DO ULTIMOS 15 MINUTOS
				// PREENCHE LISTA COM DADOS DOS 15 MINUTOS ANTERIORES
				if (satListValuesAux.isEmpty()) {
					satListValuesAux = satDAO.RealTimeSATinfo15Before();
					values15 = false;
				}
				
				System.out.println(satListValuesAux.size());

				// VERIFICA SE A LISTA CONTINUA VAZIA OU SE H� DADOS
				if (!values15 && !satListValuesAux.isEmpty())
					values15Before = true;

				// CASO HAJA DADOS DOS 15 MINUTOS ANTERIORES EXECUTA ESSE BLOCO
				if (values15Before) {

					for (int s = 0; s < satList.size(); s++) { // FOR START

						SAT satListObj = new SAT();
						pass = true;

						for (int r = 0; r < satListValuesAux.size(); r++) {
							if (satListValuesAux.get(r).getEquip_id() == satList.get(s).getEquip_id()) {

								satListObj.setQuantidadeS1(satListValuesAux.get(r).getQuantidadeS1());
								satListObj.setQuantidadeS2(satListValuesAux.get(r).getQuantidadeS2());
								satListObj.setVelocidadeS1(satListValuesAux.get(r).getVelocidadeS1());
								satListObj.setVelocidadeS2(satListValuesAux.get(r).getVelocidadeS2());

								satListValues.add(satListObj);
								satListValuesAux.remove(r);
								pass = false;

								break;

							}
						}

						if (pass) {

							satListObj.setEquip_id(satList.get(s).getEquip_id());
							satListObj.setQuantidadeS1(0);
							satListObj.setQuantidadeS2(0);
							satListObj.setVelocidadeS1(0);
							satListObj.setVelocidadeS2(0);

							satListValues.add(satListObj);
						}
					} // FOR END

				} else if (values15) {

					for (int s = 0; s < satList.size(); s++) { // FOR START

						SAT satListObj = new SAT();
						pass = true;

						for (int r = 0; r < satListValuesAux.size(); r++) {
							if (satListValuesAux.get(r).getEquip_id() == satList.get(s).getEquip_id()) {


								satListObj.setQuantidadeS1(satListValuesAux.get(r).getQuantidadeS1());
								satListObj.setQuantidadeS2(satListValuesAux.get(r).getQuantidadeS2());
								satListObj.setVelocidadeS1(satListValuesAux.get(r).getVelocidadeS1());
								satListObj.setVelocidadeS2(satListValuesAux.get(r).getVelocidadeS2());

								satListValues.add(satListObj);
								satListValuesAux.remove(r);
								pass = false;

								break;

							}
						}

						if (pass) {

							satListObj = satDAO.RealTimeSATinfo15Data(satList.get(s).getEquip_id());

							if (satListObj.getEquip_id() != 0)
								satListValues.add(satListObj);
							else {
								SAT satListObj1 = new SAT();

								satListObj1.setEquip_id(satList.get(s).getEquip_id());
								satListObj1.setQuantidadeS1(0);
								satListObj1.setQuantidadeS2(0);
								satListObj1.setVelocidadeS1(0);
								satListObj1.setVelocidadeS2(0);

								satListValues.add(satListObj1);
							}
						}
					} // FOR END

				} else
					intializeNullList(satList); // CASO N�O EXISTA VALORES VAI INICIALIZAR COM ZEROS TODOS EQUIPAMENTOS

				// ///////////////////////
				// //SAT VALUES
				// //////////////////////

				// Caso n�o tenha equipamentos faz nada

			} catch (IndexOutOfBoundsException ex) {

				ex.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void intializeNullList(List<? extends Equipments> satList2) {

		for (int i = 0; i < satList2.size(); i++) {

			SAT sat = new SAT();

			sat.setEquip_id(satList2.get(i).getEquip_id());
			sat.setQuantidadeS1(0);
			sat.setVelocidadeS1(0);
			sat.setQuantidadeS2(0);
			sat.setVelocidadeS2(0);

			satListValues.add(sat);

		}
	}

	public void intializeNullStatus(List<? extends Equipments> satList2) {

		for (int i = 0; i < satList2.size(); i++) {

			SAT sat = new SAT();

			sat.setEquip_id(satList2.get(i).getEquip_id());
			sat.setStatus(0);

			satStatus.add(sat);

		}

	}
	
	
	public void populateCCRSAT() {
		
		
		
	}

}
