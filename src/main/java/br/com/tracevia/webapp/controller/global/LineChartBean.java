package br.com.tracevia.webapp.controller.global;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean(name="chartBean")
@ViewScoped
public class LineChartBean {
	

    private String[] countries = { "\"Day\"", "\"Germany\"", "\"USA\"", "\"Brazil\"", "\"Canada\"", "\"France\"", "\"Russia\"" };
    private String[][] numbers = new String[3][7];
    
    @PostConstruct
    public void init() {
     System.out.println("ChartBeean init..");
        
        for(int i = 0; i < 3; i++) {
        	for(int j = 0; j < 7; j++) {  
        		
        		if(j == 0) //Coluna 0
        			numbers[i][j] = String.valueOf(i + 1);
        
        		else numbers[i][j] = String.valueOf((int) (Math.random() * 101));
        
        	}
        }
        
        for(int i = 0; i < 3; i++)
        	for(int j = 0; j < 7; j++) 
        		System.out.println(numbers[i][j]); 
        
    }

    public String getCountriesString() {

        // ['Germany', 'USA', 'Brazil', 'Canada', 'France', 'Russia']
        String deepToString = Arrays.deepToString(countries);
        System.out.println(deepToString);
        return deepToString;
    }

    public String getNumbersString() {
        // [700, 300, 400, 500, 600, 800]
        String deepToString = Arrays.deepToString(numbers);
        System.out.println(deepToString);
        return deepToString;
    }
}