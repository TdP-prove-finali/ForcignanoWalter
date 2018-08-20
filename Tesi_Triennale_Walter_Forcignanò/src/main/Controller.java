package main;

/**
 * Sample Skeleton for 'tesi.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import model.Model;
import model.Step;

public class Controller {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxPartenza"
    private ComboBox<Step> boxPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="boxDestinazione"
    private ComboBox<Step> boxDestinazione; // Value injected by FXMLLoader

    @FXML // fx:id="PesoTempo"
    private RadioButton PesoTempo; // Value injected by FXMLLoader

    @FXML // fx:id="txtArea"
    private TextArea txtArea; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxPartenza != null : "fx:id=\"boxPartenza\" was not injected: check your FXML file 'tesi.fxml'.";
        assert boxDestinazione != null : "fx:id=\"boxDestinazione\" was not injected: check your FXML file 'tesi.fxml'.";
        assert PesoTempo != null : "fx:id=\"PesoTempo\" was not injected: check your FXML file 'tesi.fxml'.";
        assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'tesi.fxml'.";

    }

	public void setModel(Model model) {
		this.model=model;
		// non va bene perchè devo avere una lista di posizioni che corrispondono alla via.
		//l'utente sceglie in base alle vie non in base alle coordinate.
		//posso creare degli oggetti che hanno via e coordinate insieme.
		//anche se in teoria questo puo' essere risolto direttamente con lo step.
		//poi si vede.
		
		//boxPartenza.getItems().addAll(model.getStepIdMap().values());
		//boxDestinazione.getItems().addAll(model.getStepIdMap().values());
	}
}
