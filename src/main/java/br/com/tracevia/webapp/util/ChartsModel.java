package br.com.tracevia.webapp.util;

import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LegendPlacement;

public class ChartsModel {
			
	private LineChartModel lineModel;
		
	public LineChartModel getLineModel() {
		return lineModel;
	}		
	
	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}
	
	public void createChartSeries(LineChartSeries serie) {		
	    serie = new LineChartSeries();			
	}
	
	public void createChartLabel(LineChartSeries serie, String label) {		
		serie.setLabel(label);
	}
	
	public void setChartValues(LineChartSeries serie, String object, int value) {		
		serie.set(object, value);		
	}
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
	
	public void createLinearModel(LineChartModel model, String titulo, String position, String colors, String xAxisLabel, String yAxisLabel,
			String yThickFormat, boolean shadow, boolean pointLabels, boolean animated, boolean zoom, 
			int yThickAngle, int xThickAngle, int min, int max ) {
		
		model = new LineChartModel();	
		model.setTitle(titulo);	
		model.setLegendPosition(position); 
		model.setShadow(shadow);
		model.setShowPointLabels(pointLabels);
		model.setAnimate(animated);
		model.setZoom(zoom);
		//model.setSeriesColors(colors);		
		
		Axis yAxis = model.getAxis(AxisType.Y); 
		Axis xAxis = model.getAxis(AxisType.X);
		
		yAxis.setTickAngle(yThickAngle); // gira o texto do eixo Y, ou seja, gira o texto que fica na esquerda do gráfico
        yAxis = model.getAxis(AxisType.Y);
        yAxis.setTickFormat(yThickFormat);
        yAxis.setMin(min);
		yAxis.setMax(max);
        
        yAxis.setLabel(yAxisLabel);
		xAxis.setLabel(xAxisLabel);		
		
		xAxis.setTickAngle(xThickAngle); // gira o texto do eixo X, de baixo, aumentar tamanho por javascript
		
	}
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
			
	public void createChartSeries(LineChartSeries[] series, List<SelectItem> label, int equipamentos) {
						
		for(int e = 0; e < equipamentos; e++) { 
			 series[e] = new LineChartSeries();						 
			 series[e].setLabel(label.get(e).getLabel());		 
		}		
	}
		
	public void createChartSeries(LineChartSeries[] series, String[] label, int equipamentos) {
		
		  for(int e = 0; e < equipamentos; e++) { 
			 series[e] = new LineChartSeries();						 
			 series[e].setLabel(label[e]);				
	      }			
	}
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
		
   	public void setChartTitle(LineChartModel model, String titulo) {		
		model.setTitle(titulo);	
	}
   	
   	/* ------------------------------------------------------------------------------------------------------------------------ */
	
	public void setChartLegendPosition(LineChartModel model, String position) {		
		model.setLegendPosition(position); 		
	}
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
	
	public void setChartLegendCols(LineChartModel model, int cols) {		
		model.setLegendCols(cols); 		
	}
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
	
	public void setChartLegendRows(LineChartModel model, int rows) {		
		model.setLegendRows(rows); 		
	}
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
	
	public void setChartLegendPlacement(LineChartModel model, LegendPlacement place) {		
		model.setLegendPlacement(place); 		
	}
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
		
	public void setChartShadow(LineChartModel model, boolean shadow) {		
		model.setShadow(shadow);		
	}
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
	
	public void setChartShowPointLabels(LineChartModel model, boolean points) {		
		model.setShadow(points);		
	}
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
			
    public void setChartAnimate(LineChartModel model, boolean animate) {
    	model.setAnimate(animate);    	
    }
    
    /* ------------------------------------------------------------------------------------------------------------------------ */
    
    public void setChartZoom(LineChartModel model, boolean zoom) {
    	model.setZoom(zoom);    	
    }
    
    /* ------------------------------------------------------------------------------------------------------------------------ */
    
    public Axis createChartXAxis(LineChartModel model) {
    	 Axis xAxis = model.getAxis(AxisType.X);
    	 
    	 return xAxis;
    }
    
    /* ------------------------------------------------------------------------------------------------------------------------ */
    
    public Axis createChartYAxis(LineChartModel model) {
 	       Axis yAxis = model.getAxis(AxisType.Y);  
 	       
 	       return yAxis;
    }
    
    /* ------------------------------------------------------------------------------------------------------------------------ */
    
    public void setChartAxisLabel(LineChartModel model, Axis axis, String label) {
 	   axis.setLabel(label);	   
    }
    
    /* ------------------------------------------------------------------------------------------------------------------------ */
          
    public void setChartAxisXCategoryLabel(LineChartModel model, String label) {    	
    	model.getAxes().put(AxisType.X, new CategoryAxis(label));    	
    }
      
    /* ------------------------------------------------------------------------------------------------------------------------ */
    
    public void setChartAxisYCategoryLabel(LineChartModel model, String label) {    	
    	model.getAxes().put(AxisType.Y, new CategoryAxis(label));
    }
    
    /* ------------------------------------------------------------------------------------------------------------------------ */
    
    public void setChartSerieColors(LineChartModel model, String colors) {    	
    	    model.setSeriesColors(colors);	
    }
    
    /* ------------------------------------------------------------------------------------------------------------------------ */
    
    public void setChartTickFormat(Axis axis, String format) {    	
	    axis.setTickFormat(format);	
    }
    
    /* ------------------------------------------------------------------------------------------------------------------------ */
    
    public void setChartAxisAngle(Axis axis, int thickAngle) {    	
	    axis.setTickAngle(thickAngle);	
    }
    
    /* ------------------------------------------------------------------------------------------------------------------------ */
    
    public void setChartAxisMaxAndMin(Axis axis, int min, int max) {    	
	    axis.setMin(min);
	    axis.setMax(max);
    }    
    
    /* ------------------------------------------------------------------------------------------------------------------------ */
	
	public void addSeries(LineChartModel model, LineChartSeries[] series, int equipamentos) {
		
		for(int e=0; e < equipamentos; e++)			
			model.addSeries(series[e]);		
	}
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
	
    public void addSeries(LineChartModel model, LineChartSeries[][] series, int equipamentos) {
		    	  
    	   int d = 0; // Sempre começa pela primera data a ser apresentada 
    	   
		   for(int e=0; e < equipamentos; e++) {			
			model.addSeries(series[d][e]);		
	   }
    }
    
    /* ------------------------------------------------------------------------------------------------------------------------ */
  
	
  public void Series(LineChartSeries [] series, String yAxisValue, int xAxisValue, int equip) {		
	      series[equip].set(yAxisValue, xAxisValue);	
	}
  /* ------------------------------------------------------------------------------------------------------------------------ */
	
  /** Initialize Line ChartBean
   *  
   * @author Wellington da Silva : 2021-05-21
   * @summary Function used to initiate line chart
   * @since version 1.0
   * @version 1.0 
   * @description Initiate line chart
   * @copyright Tracevia S/A 2021   
   * @see <a href="https://www.primefaces.org/docs/api/6.1/org/primefaces/model/chart/LineChartModel.html"></a> 
   * @returns retorna 
   * 
  **/
  
	public LineChartModel createLineModels() {
		
		LineChartModel lineModel = initLinearModel();
		lineModel.setTitle("Linear ChartBean");
		lineModel.setLegendPosition("e");
		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(10);
		
		yAxis.setLabel("Teste");
				
		return lineModel;

	}

	public LineChartModel initLinearModel() {
		
		LineChartModel model = new LineChartModel();

		ChartSeries series1 = new ChartSeries();
		series1.setLabel("Series 1");

		series1.set(1, 2);
		series1.set(2, 1);
		series1.set(3, 3);
		series1.set(4, 6);
		series1.set(5, 8);

		ChartSeries series2 = new ChartSeries();
		series2.setLabel("Series 2");

		series2.set(1, 6);
		series2.set(2, 3);
		series2.set(3, 2);
		series2.set(4, 7);
		series2.set(5, 9);

		model.addSeries(series1);
		model.addSeries(series2);

		return model;
	}
	
	
	/* ------------------------------------------------------------------------------------------------------------------------ */

}
