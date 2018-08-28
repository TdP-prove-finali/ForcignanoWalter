package main;

/**
 * Sample Skeleton for 'piechart.fxml' Controller Class
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.stage.WindowEvent;

public class PieChartController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="pieChart"
	private PieChart pieChart2; // Value injected by FXMLLoader
	private Controller controller;
	private ObservableList<Data> pieChartData2;

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert pieChart2 != null : "fx:id=\"pieChart\" was not injected: check your FXML file 'piechart.fxml'.";

	}

	public void setDataForPieChart(ObservableList<Data> pieChartData2) {
		this.pieChartData2 = pieChartData2;

		this.pieChart2.setData(pieChartData2);

	}
//gestione dell'evento non funziona
	public void closedWindow(WindowEvent we) {
		if (we.getEventType().equals(WindowEvent.WINDOW_CLOSE_REQUEST)) {
			controller.riportaGraficoATorta(pieChartData2);

		}
	}

	public void setFriend(Controller controller) {
		this.controller = controller;
		// TODO Auto-generated method stub

	}
}
