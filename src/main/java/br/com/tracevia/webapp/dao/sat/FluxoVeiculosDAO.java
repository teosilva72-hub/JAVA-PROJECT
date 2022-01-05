package br.com.tracevia.webapp.dao.sat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.sat.controller.LoginMB;
import br.com.sat.methods.ClassName;
import br.com.sat.model.Classes;
import br.com.sat.model.FluxoVeiculos;
import br.com.sat.util.ConnectionFactory;

public class FluxoVeiculosDAO {

	private Connection conn;
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps;
	private ResultSet rs;
	String sentido1, sentido2;
	ClassName name;
	Classes cs;

	public FluxoVeiculosDAO() throws Exception {

		try {
			this.conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro: \n" + e.getMessage());
		}
	}	
	
	/** M�todo para retorno de uma lista de objetos do tipo FluxoVeiculos com dados referentes ao fluxo mensal de ve�culos
	 * @author Wellington da Silva - data de cria��o: 01/03/2019 10:06 - atualizado em 02/09/2019 16:50	 
	 * @param ano - ano
	 * @param mes - mes	
	 * @param daysInMonth - total de dias em um m�s	
	 * @param equipamento - Equipamento selecionado
	 * @param faixas - N�mero de faixas
	 * @param param - Objeto da classe LoginMB
	 * @parama faixa1 - Primeira faixa do equipamento 
	 * @return ArrayList<FluxoVeiculos> lista - lista de objetos do tipo FluxoVeiculos */	

	public ArrayList<FluxoVeiculos> totalVehicles(String dataInicio, String dataFim, String equipamento, int faixas, LoginMB param, String faixa1)
			throws Exception{		

		ArrayList<FluxoVeiculos> lista = new ArrayList<FluxoVeiculos>();
		name = new ClassName();
		cs = new Classes();	

		int equip = Integer.parseInt(equipamento);

		try {

			//cs = name.getClassName(param.getVarName());		

			conn = ConnectionFactory.getConnection();
			cs = name.getClassName(param.getVarName());
			
			 ordenaSentido(faixa1); // Ordenar sentidos das faixas		

			String sql = " "; // inicializar vari�vel							

			sql = "SELECT date_format(data, '%Y-%m-%d') as data, "
					+ "date_format((sec_to_time(time_to_sec(data)- time_to_sec(data)%(15*60))),'%H:%i') as intervals, ";
						
			if(faixas == 2) {
				
                 /*CONTAGEM*/			
				
				//Sentido 1
				sql+= "IFNULL(ROUND(COUNT(IF(((tb_vbv.lane = 1) AND (tb_equipamentos.faixa1 = '"+sentido1+"') AND (tb_vbv.classe = '"+cs.getClassRideCar()+"' OR (tb_vbv.classe='"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10) OR tb_vbv.classe = '"+cs.getClassNotIdentifiedAxl2()+"')), 1, NULL )),0),0) 'AUTOS1', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1) AND (tb_equipamentos.faixa1 = '"+sentido1+"') AND (tb_vbv.classe <> '"+cs.getClassRideCar()+"' AND tb_vbv.classe <> '"+cs.getClassNotIdentifiedAxl2()+"' AND tb_vbv.classe <> '"+cs.getClassMotorcycle()+"' AND (tb_vbv.classe <> '"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10))), 1, NULL )),0),0) 'COM1', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1) AND (tb_equipamentos.faixa1 = '"+sentido1+"') AND (tb_vbv.classe = '"+cs.getClassMotorcycle()+"')), 1, NULL )),0),0) 'MOTOS1', " +
				"IFNULL(ROUND(COUNT(IF((tb_vbv.lane=1) AND (tb_equipamentos.faixa1 = '"+sentido1+"'), 1, NULL)),0),0) 'TOTALS1', " +			
				
				//Sentido 2
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=2) AND (tb_equipamentos.faixa2 = '"+sentido2+"') AND (tb_vbv.classe = '"+cs.getClassRideCar()+"' OR (tb_vbv.lane=2 AND tb_vbv.classe='"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10) OR tb_vbv.classe = '"+cs.getClassNotIdentifiedAxl2()+"')), 1, NULL )),0),0) 'AUTOS2', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=2) AND (tb_equipamentos.faixa2 = '"+sentido2+"') AND (tb_vbv.classe <> '"+cs.getClassRideCar()+"' AND tb_vbv.classe <> '"+cs.getClassNotIdentifiedAxl2()+"' AND tb_vbv.classe <> '"+cs.getClassMotorcycle()+"' AND (tb_vbv.classe <> '"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10))), 1, NULL )),0),0) 'COM2', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=2) AND (tb_equipamentos.faixa2 = '"+sentido2+"') AND (tb_vbv.classe = '"+cs.getClassMotorcycle()+"')), 1, NULL )),0),0) 'MOTOS2', " +
				"IFNULL(ROUND(COUNT(IF((tb_vbv.lane=2) AND (tb_equipamentos.faixa2 = '"+sentido2+"'), 1, NULL)),0),0) 'TOTALS2', ";
				
                /*M�DIA*/
				
				//Sentido 1
				sql+= "IFNULL(ROUND(AVG(IF((tb_vbv.lane=1) AND (tb_equipamentos.faixa1 = '"+sentido1+"'), tb_vbv.speed, NULL)),0),0) 'AVG TOTALS1', " +

				//Sentido 2
				"IFNULL(ROUND(AVG(IF((tb_vbv.lane=2) AND (tb_equipamentos.faixa2 = '"+sentido2+"'), tb_vbv.speed, NULL)),0),0) 'AVG TOTALS2' ";
				 
			  }
			
			if(faixas == 3) {
				
                /*CONTAGEM*/			
				
				//Sentido 1
				sql+= "IFNULL(ROUND(COUNT(IF(((tb_vbv.lane = 1) AND (tb_equipamentos.faixa1 = '"+sentido1+"') AND (tb_vbv.classe = '"+cs.getClassRideCar()+"' OR (tb_vbv.classe='"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10) OR tb_vbv.classe = '"+cs.getClassNotIdentifiedAxl2()+"')), 1, NULL )),0),0) 'AUTOS1', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1) AND (tb_equipamentos.faixa1 = '"+sentido1+"') AND (tb_vbv.classe <> '"+cs.getClassRideCar()+"' AND tb_vbv.classe <> '"+cs.getClassNotIdentifiedAxl2()+"' AND tb_vbv.classe <> '"+cs.getClassMotorcycle()+"' AND (tb_vbv.classe <> '"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10))), 1, NULL )),0),0) 'COM1', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1) AND (tb_equipamentos.faixa1 = '"+sentido1+"') AND (tb_vbv.classe = '"+cs.getClassMotorcycle()+"')), 1, NULL )),0),0) 'MOTOS1', " +
				"IFNULL(ROUND(COUNT(IF((tb_vbv.lane=1) AND (tb_equipamentos.faixa1 = '"+sentido1+"'), 1, NULL)),0),0) 'TOTALS1', " +			
				
				//Sentido 2
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=2 OR tb_vbv.lane=3) AND (tb_equipamentos.faixa2 = '"+sentido2+"' OR tb_equipamentos.faixa3 = '"+sentido2+"') AND (tb_vbv.classe = '"+cs.getClassRideCar()+"' OR (tb_vbv.lane=2 AND tb_vbv.classe='"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10) OR tb_vbv.classe = '"+cs.getClassNotIdentifiedAxl2()+"')), 1, NULL )),0),0) 'AUTOS2', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=2 OR tb_vbv.lane=3) AND (tb_equipamentos.faixa2 = '"+sentido2+"' OR tb_equipamentos.faixa3 = '"+sentido2+"') AND (tb_vbv.classe <> '"+cs.getClassRideCar()+"' AND tb_vbv.classe <> '"+cs.getClassNotIdentifiedAxl2()+"' AND tb_vbv.classe <> '"+cs.getClassMotorcycle()+"' AND (tb_vbv.classe <> '"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10))), 1, NULL )),0),0) 'COM2', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=2 OR tb_vbv.lane=3) AND (tb_equipamentos.faixa2 = '"+sentido2+"' OR tb_equipamentos.faixa3 = '"+sentido2+"') AND (tb_vbv.classe = '"+cs.getClassMotorcycle()+"')), 1, NULL )),0),0) 'MOTOS2', " +
				"IFNULL(ROUND(COUNT(IF((tb_vbv.lane=2 OR tb_vbv.lane=3) AND (tb_equipamentos.faixa2 = '"+sentido2+"' OR tb_equipamentos.faixa3 = '"+sentido2+"'), 1, NULL)),0),0) 'TOTALS2', ";
				
               /*M�DIA*/
				
				//Sentido 1
				sql+= "IFNULL(ROUND(AVG(IF((tb_vbv.lane=1) AND (tb_equipamentos.faixa1 = '"+sentido1+"'), tb_vbv.speed, NULL)),0),0) 'AVG TOTALS1', " +

				//Sentido 2
				"IFNULL(ROUND(AVG(IF((tb_vbv.lane=2 OR tb_vbv.lane=3) AND (tb_equipamentos.faixa2 = '"+sentido2+"' OR tb_equipamentos.faixa3 = '"+sentido2+"'), tb_vbv.speed, NULL)),0),0) 'AVG TOTALS2' ";
				 
			  }

   
			if(faixas == 4) {

                 /*CONTAGEM*/			
				
				//Sentido 1		
				sql+=" IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2) AND (tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"') AND (tb_vbv.classe = '"+cs.getClassRideCar()+"' OR (tb_vbv.classe='"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10) OR tb_vbv.classe = '"+cs.getClassNotIdentifiedAxl2()+"')), 1, NULL )),0),0) 'AUTOS1', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2) AND (tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"') AND (tb_vbv.classe <> '"+cs.getClassRideCar()+"' AND tb_vbv.classe <> '"+cs.getClassNotIdentifiedAxl2()+"' AND tb_vbv.classe <> '"+cs.getClassMotorcycle()+"' AND (tb_vbv.classe <> '"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10))), 1, NULL )),0),0) 'COM1', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2) AND (tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"') AND (tb_vbv.classe = '"+cs.getClassMotorcycle()+"')), 1, NULL )),0),0) 'MOTOS1', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2) AND (tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"')), 1, NULL)),0),0) 'TOTALS1', " +
               
                //Sentido 2								
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=3 OR tb_vbv.lane=4) AND (tb_equipamentos.faixa3 = '"+sentido2+"' OR tb_equipamentos.faixa4 = '"+sentido2+"') AND (tb_vbv.classe = '"+cs.getClassRideCar()+"' OR (tb_vbv.classe='"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10) OR tb_vbv.classe = '"+cs.getClassNotIdentifiedAxl2()+"')), 1, NULL )),0),0) 'AUTOS2', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=3 OR tb_vbv.lane=4) AND (tb_equipamentos.faixa3 = '"+sentido2+"' OR tb_equipamentos.faixa4 = '"+sentido2+"') AND (tb_vbv.lane=3 OR tb_vbv.lane=4) AND (tb_vbv.classe <> '"+cs.getClassRideCar()+"' AND tb_vbv.classe <> '"+cs.getClassNotIdentifiedAxl2()+"' AND tb_vbv.classe <> '"+cs.getClassMotorcycle()+"' AND (tb_vbv.classe <> '"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10))), 1, NULL )),0),0) 'COM2', " + 
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=3 OR tb_vbv.lane=4) AND (tb_equipamentos.faixa3 = '"+sentido2+"' OR tb_equipamentos.faixa4 = '"+sentido2+"') AND (tb_vbv.classe = '"+cs.getClassMotorcycle()+"')), 1, NULL )),0),0) 'MOTOS2', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=3 OR tb_vbv.lane=4) AND (tb_equipamentos.faixa3 = '"+sentido2+"' OR tb_equipamentos.faixa4 = '"+sentido2+"')), 1, NULL)),0),0) 'TOTALS2', ";
				
               /*VELOCIDADE M�DIA*/

				//Sentido 1		
				sql+= "IFNULL(ROUND(AVG(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2) AND (tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"')), tb_vbv.speed , NULL)),0),0) 'AVG TOTALS1', " +

				//Sentido 2			
				"IFNULL(ROUND(AVG(IF(((tb_vbv.lane=3 OR tb_vbv.lane=4) AND (tb_equipamentos.faixa3 = '"+sentido2+"' OR tb_equipamentos.faixa4 = '"+sentido2+"') ), tb_vbv.speed, NULL)),0),0) 'AVG TOTALS2' ";
				
			}
			
			if(faixas == 6) {

                /*CONTAGEM*/			
				
				//Sentido 1		
				sql+=" IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2 OR tb_vbv.lane=3) AND (tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"' OR tb_equipamentos.faixa3 = '"+sentido1+"') AND (tb_vbv.classe = '"+cs.getClassRideCar()+"' OR (tb_vbv.classe='"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10) OR tb_vbv.classe = '"+cs.getClassNotIdentifiedAxl2()+"')), 1, NULL )),0),0) 'AUTOS1', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2 OR tb_vbv.lane=3) AND tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"' OR tb_equipamentos.faixa3 = '"+sentido1+"') AND (tb_vbv.classe <> '"+cs.getClassRideCar()+"' AND tb_vbv.classe <> '"+cs.getClassNotIdentifiedAxl2()+"' AND tb_vbv.classe <> '"+cs.getClassMotorcycle()+"' AND (tb_vbv.classe <> '"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10))), 1, NULL )),0),0) 'COM1', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2 OR tb_vbv.lane=3) AND tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"' OR tb_equipamentos.faixa3 = '"+sentido1+"') AND (tb_vbv.classe = '"+cs.getClassMotorcycle()+"')), 1, NULL )),0),0) 'MOTOS1', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2 OR tb_vbv.lane=3) AND tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"' OR tb_equipamentos.faixa3 = '"+sentido1+"')), 1, NULL)),0),0) 'TOTALS1', " +
              
               //Sentido 2								
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=4 OR tb_vbv.lane=5 OR tb_vbv.lane=6) AND tb_equipamentos.faixa4 = '"+sentido2+"' OR tb_equipamentos.faixa5 = '"+sentido2+"' OR tb_equipamentos.faixa6 = '"+sentido2+"') AND (tb_vbv.classe = '"+cs.getClassRideCar()+"' OR (tb_vbv.classe='"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10) OR tb_vbv.classe = '"+cs.getClassNotIdentifiedAxl2()+"')), 1, NULL )),0),0) 'AUTOS2', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=4 OR tb_vbv.lane=5 OR tb_vbv.lane=6) AND tb_equipamentos.faixa4 = '"+sentido2+"' OR tb_equipamentos.faixa5 = '"+sentido2+"' OR tb_equipamentos.faixa6 = '"+sentido2+"') AND (tb_vbv.lane=3 OR tb_vbv.lane=4) AND (tb_vbv.classe <> '"+cs.getClassRideCar()+"' AND tb_vbv.classe <> '"+cs.getClassNotIdentifiedAxl2()+"' AND tb_vbv.classe <> '"+cs.getClassMotorcycle()+"' AND (tb_vbv.classe <> '"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10))), 1, NULL )),0),0) 'COM2', " + 
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=4 OR tb_vbv.lane=5 OR tb_vbv.lane=6) AND tb_equipamentos.faixa4 = '"+sentido2+"' OR tb_equipamentos.faixa5 = '"+sentido2+"' OR tb_equipamentos.faixa6 = '"+sentido2+"') AND (tb_vbv.classe = '"+cs.getClassMotorcycle()+"')), 1, NULL )),0),0) 'MOTOS2', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=4 OR tb_vbv.lane=5 OR tb_vbv.lane=6) AND tb_equipamentos.faixa4 = '"+sentido2+"' OR tb_equipamentos.faixa5 = '"+sentido2+"' OR tb_equipamentos.faixa6 = '"+sentido2+"')), 1, NULL)),0),0) 'TOTALS2', ";
				
              /*VELOCIDADE M�DIA*/

				//Sentido 1		
				sql+= "IFNULL(ROUND(AVG(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2 OR tb_vbv.lane=3) AND (tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"' OR tb_equipamentos.faixa3 = '"+sentido1+"')), tb_vbv.speed , NULL)),0),0) 'AVG TOTALS1', " +

				//Sentido 2			
				"IFNULL(ROUND(AVG(IF(((tb_vbv.lane=4 OR tb_vbv.lane=5 OR tb_vbv.lane=6) AND (tb_equipamentos.faixa4 = '"+sentido2+"' OR tb_equipamentos.faixa5 = '"+sentido2+"' OR tb_equipamentos.faixa6 = '"+sentido2+"') ), tb_vbv.speed, NULL)),0),0) 'AVG TOTALS2' ";
				
			}	
			
			if(faixas == 8) {

                /*CONTAGEM*/			
				
				//Sentido 1		
				sql+=" IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2 OR tb_vbv.lane=3 OR tb_vbv.lane=4) AND (tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"' OR tb_equipamentos.faixa3 = '"+sentido1+"' OR tb_equipamentos.faixa4 = '"+sentido1+"') AND (tb_vbv.classe = '"+cs.getClassRideCar()+"' OR (tb_vbv.classe='"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10) OR tb_vbv.classe = '"+cs.getClassNotIdentifiedAxl2()+"')), 1, NULL )),0),0) 'AUTOS1', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2 OR tb_vbv.lane=3 OR tb_vbv.lane=4) AND (tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"' OR tb_equipamentos.faixa3 = '"+sentido1+"' OR tb_equipamentos.faixa4 = '"+sentido1+"') AND (tb_vbv.classe <> '"+cs.getClassRideCar()+"' AND tb_vbv.classe <> '"+cs.getClassNotIdentifiedAxl2()+"' AND tb_vbv.classe <> '"+cs.getClassMotorcycle()+"' AND (tb_vbv.classe <> '"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10))), 1, NULL )),0),0) 'COM1', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2 OR tb_vbv.lane=3 OR tb_vbv.lane=4) AND (tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"' OR tb_equipamentos.faixa3 = '"+sentido1+"' OR tb_equipamentos.faixa4 = '"+sentido1+"') AND (tb_vbv.classe = '"+cs.getClassMotorcycle()+"')), 1, NULL )),0),0) 'MOTOS1', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2 OR tb_vbv.lane=3 OR tb_vbv.lane=4) AND (tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"' OR tb_equipamentos.faixa3 = '"+sentido1+"' OR tb_equipamentos.faixa4 = '"+sentido1+"')), 1, NULL)),0),0) 'TOTALS1', " +
              
               //Sentido 2								
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=5 OR tb_vbv.lane=6 OR tb_vbv.lane=7 OR tb_vbv.lane=8) AND (tb_equipamentos.faixa5 = '"+sentido2+"' OR tb_equipamentos.faixa6 = '"+sentido2+"' OR tb_equipamentos.faixa7 = '"+sentido2+"' OR tb_equipamentos.faixa8 = '"+sentido2+"') AND (tb_vbv.classe = '"+cs.getClassRideCar()+"' OR (tb_vbv.classe='"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10) OR tb_vbv.classe = '"+cs.getClassNotIdentifiedAxl2()+"')), 1, NULL )),0),0) 'AUTOS2', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=5 OR tb_vbv.lane=6 OR tb_vbv.lane=7 OR tb_vbv.lane=8) AND (tb_equipamentos.faixa5 = '"+sentido2+"' OR tb_equipamentos.faixa6 = '"+sentido2+"' OR tb_equipamentos.faixa7 = '"+sentido2+"' OR tb_equipamentos.faixa8 = '"+sentido2+"') AND (tb_vbv.lane=3 OR tb_vbv.lane=4) AND (tb_vbv.classe <> '"+cs.getClassRideCar()+"' AND tb_vbv.classe <> '"+cs.getClassNotIdentifiedAxl2()+"' AND tb_vbv.classe <> '"+cs.getClassMotorcycle()+"' AND (tb_vbv.classe <> '"+cs.getUnknowClass()+"' AND tb_vbv.axlNumber < 10))), 1, NULL )),0),0) 'COM2', " + 
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=5 OR tb_vbv.lane=6 OR tb_vbv.lane=7 OR tb_vbv.lane=8) AND (tb_equipamentos.faixa5 = '"+sentido2+"' OR tb_equipamentos.faixa6 = '"+sentido2+"' OR tb_equipamentos.faixa7 = '"+sentido2+"' OR tb_equipamentos.faixa8 = '"+sentido2+"') AND (tb_vbv.classe = '"+cs.getClassMotorcycle()+"')), 1, NULL )),0),0) 'MOTOS2', " +
				"IFNULL(ROUND(COUNT(IF(((tb_vbv.lane=5 OR tb_vbv.lane=6 OR tb_vbv.lane=7 OR tb_vbv.lane=8) AND (tb_equipamentos.faixa5 = '"+sentido2+"' OR tb_equipamentos.faixa6 = '"+sentido2+"' OR tb_equipamentos.faixa7 = '"+sentido2+"' OR tb_equipamentos.faixa8 = '"+sentido2+"')), 1, NULL)),0),0) 'TOTALS2', ";
				
              /*VELOCIDADE M�DIA*/

				//Sentido 1		
				sql+= "IFNULL(ROUND(AVG(IF(((tb_vbv.lane=1 OR tb_vbv.lane=2 OR tb_vbv.lane=3 OR tb_vbv.lane=4) AND (tb_equipamentos.faixa1 = '"+sentido1+"' OR tb_equipamentos.faixa2 = '"+sentido1+"' OR tb_equipamentos.faixa3 = '"+sentido1+"' OR tb_equipamentos.faixa4 = '"+sentido1+"')), tb_vbv.speed , NULL)),0),0) 'AVG TOTALS1', " +

				//Sentido 2			
				"IFNULL(ROUND(AVG(IF(((tb_vbv.lane=5 OR tb_vbv.lane=6 OR tb_vbv.lane=7 OR tb_vbv.lane=8) AND (tb_equipamentos.faixa5 = '"+sentido2+"' OR tb_equipamentos.faixa6 = '"+sentido2+"' OR tb_equipamentos.faixa7 = '"+sentido2+"' OR tb_equipamentos.faixa8 = '"+sentido2+"')), tb_vbv.speed, NULL)),0),0) 'AVG TOTALS2' ";
				
			}	
			
			
			sql += "FROM tb_vbv " + 
					"INNER JOIN tb_equipamentos ON tb_vbv.siteID = tb_equipamentos.siteID " + 
					"WHERE data BETWEEN '"+dataInicio+"' AND '"+dataFim+"' AND tb_vbv.siteID = '"+equip+"' "; 										


			sql += "GROUP BY DATE(data), intervals " +  
					"ORDER BY DATE(data) ASC ";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs != null) {
				while (rs.next()) {

					FluxoVeiculos f = new FluxoVeiculos();														

					f.setData(rs.getString("data"));	
					f.setIntervalos(rs.getString("intervals"));
					f.setMotos1(rs.getInt("MOTOS1"));
					f.setAutos1(rs.getInt("AUTOS1"));
					f.setComs1(rs.getInt("COM1"));
					f.setTotalCS1(rs.getInt("TOTALS1"));
					f.setMotos2(rs.getInt("MOTOS2"));
					f.setAutos2(rs.getInt("AUTOS2"));
					f.setComs2(rs.getInt("COM2"));	
					f.setTotalCS2(rs.getInt("TOTALS2"));
					f.setTotalVMediaS1(rs.getInt("AVG TOTALS1"));				
					f.setTotalVMediaS2(rs.getInt("AVG TOTALS2"));
									
					lista.add(f);							    
				}							
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps);}

		return lista;
	}
			
	public void ordenaSentido(String faixa1) {
		
		if(faixa1.equals("N")) {			
			sentido1 = "N";
			sentido2 = "S";
		}
		
		if(faixa1.equals("S")) {
			sentido1 = "S";
			sentido2 = "N";
		}
		
		if(faixa1.equals("L")) {
			sentido1 = "L";
			sentido2 = "O";
		}
		
		if(faixa1.equals("O")) {
			sentido1 = "O";
			sentido2 = "L";
		}	
	}	

}
