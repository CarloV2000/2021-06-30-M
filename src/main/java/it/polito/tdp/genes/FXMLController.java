/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model ;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnContaArchi"
    private Button btnContaArchi; // Value injected by FXMLLoader

    @FXML // fx:id="btnRicerca"
    private Button btnRicerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtSoglia"
    private TextField txtSoglia; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doContaArchi(ActionEvent event) {
    	this.txtResult.setText(model.creaGrafo());
    	double max = model.pesoMassimo();
    	double min = model.pesoMinimo();
    	this.txtResult.appendText("\nPeso minimo = " + min);
    	this.txtResult.appendText("\nPeso massimo = " + max);
    	String soglia = this.txtSoglia.getText();
    	int sogliaNUM;
    	try {
    		sogliaNUM = Integer.parseInt(soglia);
    		if(sogliaNUM < min || sogliaNUM > max) {
    			this.txtResult.setText("\nValore soglia non compreso tra min ("+min+") e max ("+max+")");
    			return;
    		}
    		//contare numero archi con peso maggiore di s e minore di s
    		int nMaggiori = model.getArchiPesoMaggiore(sogliaNUM).size();
    		int nMinori = model.getArchiPesoMinore(sogliaNUM).size();
    		this.txtResult.appendText("\n"+nMinori+" archi hanno peso minore del peso medio, "+nMaggiori+" archi lo hanno maggiore");
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("\nSelezionare una soglia!!!");
    		return;
    	}
    }

    @FXML
    void doRicerca(ActionEvent event) {
    	double max = model.pesoMassimo();
    	double min = model.pesoMinimo();
    	
    	String soglia = this.txtSoglia.getText();
    	int sogliaNUM;
    	String s = "";
    	try {
    		sogliaNUM = Integer.parseInt(soglia);
    		if(sogliaNUM < min || sogliaNUM > max) {
    			this.txtResult.setText("\nValore soglia non compreso tra min ("+min+") e max ("+max+")");
    			return;
    		}
    		model.getCamminoMax(sogliaNUM);
    		List<Integer>migliore = new ArrayList<>(model.getCamminoMigliore());
    		double somma = model.getSommaMigliore();
    		this.txtResult.appendText("Peso del percorso migliore : "+somma);
    		for(Integer i : migliore ) {
    			s += "\n"+i;
    		}
    		this.txtResult.appendText(s);
    		
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("\nSelezionare una soglia!!!");
    		return;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnContaArchi != null : "fx:id=\"btnContaArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSoglia != null : "fx:id=\"txtSoglia\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model ;
		
	}
}
