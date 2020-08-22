package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {
    @FXML
    private TextArea textArea;

    @FXML
    private void openFileAction(ActionEvent actionEvent){
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(((Button)actionEvent.getSource()).getScene().getWindow());
        try {
            Scanner scn = new Scanner(f);
            while(scn.hasNextLine()){
                textArea.appendText(scn.nextLine() + "\n");
            }
            scn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
