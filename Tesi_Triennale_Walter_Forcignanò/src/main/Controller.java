package main;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import model.Model;
import model.Posizione;
import model.PosizionePiuPeso;

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

	@FXML // fx:id="txtArea"
	private TextArea txtArea; // Value injected by FXMLLoader

	@FXML
	void handleCalcolaPercorso(ActionEvent event) {

		if ((this.boxPartenza.getValue() == null) || this.boxDestinazione.getValue() == null) {
			this.txtArea.setText(
					"Errore è necessario selezionare una posizione di partenza e una di destinazione distinte.");
		} else if (this.boxDestinazione.getValue().equals(this.boxPartenza.getValue())) {
			this.txtArea.setText(
					"Errore è necessario selezionare una posizione di partenza e una di destinazione distinte.");
		} else {
			// calcolare il percorso ottimale tra partenza e destinazione.

			List<PosizionePiuPeso> percorsoOttimale = model.calcolaPercorsoOttimale(this.boxPartenza.getValue(),
					this.boxDestinazione.getValue());

			if (percorsoOttimale.size() == 0)
				this.txtArea.setText("Destinazione non raggiungibile.");
			else
				this.txtArea.setText("Il percorso ottimale è: \n" + percorsoOttimale.toString());

		}
	}
	
	  @FXML
	    void handleChangePeso(ActionEvent event) {
		  
		  model.changeWeight();
		  
		  model.creaArchiGrafo();
		  if(model.isPesoTempo()) {
			  this.txtArea.appendText("\n Modifiche apportate al grafo.\n Ora i pesi del grafo sono secondi.");
		  } else
			  this.txtArea.appendText("\n Modifiche apportate al grafo.\n Ora i pesi del grafo sono km percorsi.");
		  //il problema è che dovrei creare nuovamente il grafo ogni volta che l'utente decide di cambiare il peso.
		  //possibilità:
		  //***potrei aggiungere direttamente tutti e due i pesi tramite un oggetto che al tempo stesso ha sia distanza che tempo di percorrenza
		  //poi in base al valore del boolean pesoTempo decido come calcolare il peso.
//		  *** problema: non posso inserire due pesi con defaultweightedEdge ma solo un double.
		  //**creare il grafo qua.
		  //*creare due grafi.

	    }

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert boxPartenza != null : "fx:id=\"boxPartenza\" was not injected: check your FXML file 'tesi.fxml'.";
		assert boxDestinazione != null : "fx:id=\"boxDestinazione\" was not injected: check your FXML file 'tesi.fxml'.";
		assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'tesi.fxml'.";
		assert PesoTempo != null : "fx:id=\"PesoTempo\" was not injected: check your FXML file 'tesi.fxml'.";
		assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'tesi.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		model.creaVerticiGrafo();
		model.creaArchiGrafo();
		boxPartenza.getItems().addAll(model.getPositionsList());
		boxDestinazione.getItems().addAll(model.getPositionsList());
		//questo box potrei caricarlo una volta scelta una destinazione.

	}
}
