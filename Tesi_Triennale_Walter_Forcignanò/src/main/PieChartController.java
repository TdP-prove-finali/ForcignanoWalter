package main;

/**
 * Sample Skeleton for 'piechart.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

public class PieChartController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="pieChart"
    private PieChart pieChart2; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert pieChart2 != null : "fx:id=\"pieChart\" was not injected: check your FXML file 'piechart.fxml'.";

    }

	public void setDataForPieChart(ObservableList<Data> pieChartData) {
	
		this.pieChart2.setData(pieChartData);
		
	}
}
