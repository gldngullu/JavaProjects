package sendReceiveObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentFxmlController implements Initializable {
    @FXML
    private ListView<Student> studentsList;
    @FXML
    private TextField deptTf;
    @FXML
    private Button saveAndSendButton;
    @FXML
    private TextField idTF;
    @FXML
    private TextField nameTF;

    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    private ObservableList<Student> stList;

    public void saveAndSendAction(ActionEvent actionEvent) {
        Student s = new Student(nameTF.getText(), idTF.getText(), deptTf.getText());
        try {
            toServer.writeObject(s);
            toServer.flush();
            Student m = (Student) fromServer.readObject();
            stList.add(m);
            nameTF.setText("");
            idTF.setText("");
            deptTf.setText("");
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("yazamadim gibi bir sey oldu");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stList = FXCollections.observableArrayList();
        studentsList.setItems(stList);
        try{
            Socket s = new Socket("localhost", 8000);
            toServer = new ObjectOutputStream(s.getOutputStream());
            fromServer = new ObjectInputStream(s.getInputStream());
        }
        catch(Exception ex){
            System.out.println("soket yaratirken yanlis bisey oldu");
        }
    }
}
