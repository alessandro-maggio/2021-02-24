
/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
	boolean grafoCreato= false;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnGiocatoreMigliore"
    private Button btnGiocatoreMigliore; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMatch"
    private ComboBox<Match> cmbMatch; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	try {
    		int combo= cmbMatch.getValue().getMatchID();
    		String result= this.model.creaGrafo(combo);
    		txtResult.clear();
    		txtResult.appendText(result);
    		grafoCreato= true;
    		
    	}catch(NullPointerException e) {
    		
    		txtResult.clear();
    		txtResult.appendText("Selezionare un match!\n");
    		
    	}
    	
    }

    @FXML
    void doGiocatoreMigliore(ActionEvent event) {
    	
    	try {
    		String result= this.model.giocatoreMigliore();
        	txtResult.setText(result+"\n");
    		
    	}catch(NullPointerException e) {
    		txtResult.setText("Devi prima creare il grafo!\n");
    	}
    	
    	
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	
    	if(grafoCreato==true) {
    	
    	try {
    		doGiocatoreMigliore(event);
    		txtResult.clear();
        	int azioni= Integer.parseInt(txtN.getText());
        	txtResult.setText(this.model.finale(azioni));
    		
    	}catch(NumberFormatException e){
    		txtResult.setText("Inserire un numero di azioni valido!\n");
    	}
    	}
    	else {
    		txtResult.appendText("E' prima necessario creare il grafo!\n");
    	}
    	
    	
    	
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGiocatoreMigliore != null : "fx:id=\"btnGiocatoreMigliore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMatch != null : "fx:id=\"cmbMatch\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbMatch.getItems().clear();
    	cmbMatch.getItems().addAll(this.model.getAllMatches());
    }
}
