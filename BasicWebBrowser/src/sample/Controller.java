package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button goButton;

    @FXML
    private TextField addressBar;

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    @FXML
    private void getWebsite(ActionEvent actionEvent){
        String address = "";
        if(!addressBar.getText().startsWith("https://")) {
            address = "http://" + addressBar.getText();
        }
        webEngine.load(address);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        webEngine.load("http://");
    }
}
