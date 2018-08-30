package main;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.javadocmd.simplelatlng.LatLng;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Model;
import model.Posizione;
import model.PosizionePiuPeso;
import model.newRow;

public class Controller {
	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxPartenza"
	private ComboBox<Posizione> boxPartenza; // Value injected by FXMLLoader

	@FXML // fx:id="boxDestinazione"
	private ComboBox<Posizione> boxDestinazione; // Value injected by FXMLLoader

	@FXML // fx:id="btnPercorso"
	private Button btnPercorso; // Value injected by FXMLLoader

	@FXML // fx:id="PesoTempo"
	private RadioButton PesoTempo; // Value injected by FXMLLoader

	@FXML // fx:id="txtField"
	private TextField txtField; // Value injected by FXMLLoader

	@FXML // fx:id="numeroManovre"
	private Label numeroManovre; // Value injected by FXMLLoader

	@FXML // fx:id="tableView"
	private TableView<newRow> tableView; // Value injected by FXMLLoader

	@FXML // fx:id="colonnaPercorsoEseguito"
	private TableColumn<newRow, String> colonnaPercorsoEseguito; // Value injected by FXMLLoader

	@FXML // fx:id="colonnaPeso"
	private TableColumn<newRow, Double> colonnaPeso; // Value injected by FXMLLoader

	@FXML // fx:id="colonnaManovre"
	private TableColumn<newRow, String> colonnaNumeroManovre; // Value injected by FXMLLoader

	@FXML // fx:id="colonnaCoordinate"
	private TableColumn<newRow, LatLng> colonnaCoordinate; // Value injected by FXMLLoader

	@FXML // fx:id="pieChart"
	private PieChart pieChart; // Value injected by FXMLLoader

	@FXML // fx:id="labelResult"
	private Label labelResult; // Value injected by FXMLLoader

	private ObservableList<PieChart.Data> pieChartData;
	private Stage secondaryStage;

	@FXML
	void handleCalcolaPercorso(ActionEvent event) {

		this.txtField.setVisible(false);
		this.pieChart.setVisible(true);

		if ((this.boxPartenza.getValue() == null) || this.boxDestinazione.getValue() == null) {
			this.txtField.setVisible(true);

			this.txtField.setText(
					"Errore è necessario selezionare se possibile una posizione di partenza e una di destinazione distinte.");
		} else if (this.boxDestinazione.getValue().equals(this.boxPartenza.getValue())) {
			this.txtField.setVisible(true);

			this.txtField.setText(
					"Errore è necessario selezionare se possibile una posizione di partenza e una di destinazione distinte .");
		} else {
			// calcolare il percorso ottimale tra partenza e destinazione.

			List<PosizionePiuPeso> percorsoOttimale = model.calcolaPercorsoOttimale(this.boxPartenza.getValue(),
					this.boxDestinazione.getValue());

			if (percorsoOttimale.size() == 0) {
				this.txtField.setVisible(true);
				this.txtField.setText("Destinazione non raggiungibile.");
			}

			else {
			
				riportaStatistiche(percorsoOttimale);

				double pesoTotale = 0;
				for (int i = 0; i < percorsoOttimale.size(); i++) {
					pesoTotale += percorsoOttimale.get(i).getPeso();
				}

				if (model.isPesoTempo()) {

					int ore = (int) (pesoTotale / 3600);
					int minuti = (int) (pesoTotale % 3600 / 60);
					int secondi = (int) (pesoTotale % 3600 % 60);

					this.labelResult.setText("Tempo stimato per raggiungere la destinazione in: " + ore + " ore "
							+ minuti + " minuti " + secondi + " secondi.");

				} else
					this.labelResult.setText("Il numero totale di m da percorrere è " + pesoTotale);
				this.numeroManovre.setText("" + percorsoOttimale.size());
			}

		}
	}

	/**
	 * Metodo che ha lo scopo di calcolare e riportare a schermo le statistiche del
	 * percorso ottimo.
	 * 
	 * @param percorsoOttimale
	 */
	private void riportaStatistiche(List<PosizionePiuPeso> percorsoOttimale) {

		ObservableList<newRow> values = FXCollections.observableArrayList();

		pieChartData.removeAll(pieChartData);

		for (int i = 0; i < percorsoOttimale.size(); i++) {

			values.add(new newRow(percorsoOttimale.get(i).getStringPosizione(), percorsoOttimale.get(i).getPeso(),
					percorsoOttimale.get(i).getPosizione().getManovra(),
					percorsoOttimale.get(i).getPosizione().getCoordinate().toString()));

			pieChartData.add(
					new Data(percorsoOttimale.get(i).getPosizione().getNomeLuogo(), percorsoOttimale.get(i).getPeso()));

		}
		values.get(0).setManovra("departure");
		values.get(percorsoOttimale.size() - 1).setManovra("arrive");
		tableView.setItems(values);
	}

	@FXML
	void handleChangePeso(ActionEvent event) {

		model.changeWeight();

		model.creaGrafo();

		this.txtField.setVisible(true);

		if (model.isPesoTempo()) {
			this.txtField.setText("\n Modifiche apportate al grafo.\n Ora i pesi del grafo sono secondi trascorsi.");
			this.colonnaPeso.setText("Tempo Percorrenza (s)");
		} else {
			this.txtField.setText("\n Modifiche apportate al grafo.\n Ora i pesi del grafo sono m percorsi.");
			this.colonnaPeso.setText("Distanza Percorsa (m)");
		}

	}

	@FXML
	void handleEspandiGrafo(MouseEvent event) {
		try {
			secondaryStage = new Stage();
			// Fill stage with content
			FXMLLoader loader = new FXMLLoader(getClass().getResource("piechart.fxml"));
			BorderPane children = (BorderPane) loader.load();
			Scene scene = new Scene(children);

			PieChartController controller = (PieChartController) loader.getController();


			ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList(this.pieChartData);
			controller.setDataForPieChart(pieChartData2);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			secondaryStage.setScene(scene);
			secondaryStage.show();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.pieChart.setVisible(false);
	}

	@FXML
	void handlePartenzaSelezionata(ActionEvent event) {
		this.tableView.getItems().removeAll(this.tableView.getItems());
		this.txtField.clear();
		this.txtField.setVisible(false);
		this.numeroManovre.setText("");
		this.boxDestinazione.setItems(FXCollections.observableArrayList());

		if (this.boxPartenza.getValue() != null) {
			this.boxDestinazione.getItems().addAll(model.getStatiRaggiungibili(boxPartenza.getValue()));
		}

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert boxPartenza != null : "fx:id=\"boxPartenza\" was not injected: check your FXML file 'tesi.fxml'.";
		assert boxDestinazione != null : "fx:id=\"boxDestinazione\" was not injected: check your FXML file 'tesi.fxml'.";
		assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'tesi.fxml'.";
		assert PesoTempo != null : "fx:id=\"PesoTempo\" was not injected: check your FXML file 'tesi.fxml'.";
		assert numeroManovre != null : "fx:id=\"numeroManovre\" was not injected: check your FXML file 'tesi.fxml'.";
		assert txtField != null : "fx:id=\"txtField\" was not injected: check your FXML file 'tesi.fxml'.";
		assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'tesi.fxml'.";
		assert colonnaPercorsoEseguito != null : "fx:id=\"colonnaPercorsoEseguito\" was not injected: check your FXML file 'tesi.fxml'.";
		assert colonnaPeso != null : "fx:id=\"colonnaPeso\" was not injected: check your FXML file 'tesi.fxml'.";
		assert colonnaNumeroManovre != null : "fx:id=\"colonnaNumeroManovre\" was not injected: check your FXML file 'tesi.fxml'.";
		assert colonnaCoordinate != null : "fx:id=\"colonnaCoordinate\" was not injected: check your FXML file 'tesi.fxml'.";
		assert pieChart != null : "fx:id=\"pieChart\" was not injected: check your FXML file 'tesi.fxml'.";
		assert labelResult != null : "fx:id=\"labelResult\" was not injected: check your FXML file 'tesi.fxml'.";

		this.colonnaPercorsoEseguito.setCellValueFactory(new PropertyValueFactory<>("posizionePiuPeso"));
		this.colonnaPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
		this.colonnaCoordinate.setCellValueFactory(new PropertyValueFactory<>("coordinate"));
		this.colonnaNumeroManovre.setCellValueFactory(new PropertyValueFactory<>("manovra"));
		this.txtField.setVisible(false);

		pieChartData = FXCollections.observableArrayList(new PieChart.Data("strada", 100));
		pieChart.setData(pieChartData);

	}

	public void setModel(Model model) {
		this.model = model;
		model.creaGrafo();

		boxPartenza.getItems().addAll(model.getPositionsList());
		// boxDestinazione.getItems().addAll(model.getPositionsList());
		// questo box potrei caricarlo una volta scelta una destinazione.

	}

}
