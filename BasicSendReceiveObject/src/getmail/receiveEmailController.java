package getmail;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.mail.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class receiveEmailController implements Initializable {

    @FXML
    private TextArea emailContent;
    private Session session;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imaps");
        emailContent.setWrapText(true);
        emailContent.setEditable(false);
        session = Session.getInstance(properties, null);
    }

    @FXML
    private void showMailButton(ActionEvent actionEvent){
        String buttonString = ((Button) actionEvent.getSource()).getText();
        if (buttonString.equals("Show Email"))
            printMail();
    }

    private void printMail(){
        //StringBuilder sb = new StringBuilder();
        getMail();
        //String content = getMail(sb).toString();
        //mailContent.setText(content);
    }

    private void getMail(){
        try {
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", "advanced.java.denemeler", "Kaleme34.");
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            System.out.println();
            String a = "asfaf";
            a.toUpperCase();
            Message message = inbox.getMessage(inbox.getMessageCount());
            Address sender = message.getFrom()[0];
            //sb.append("FROM: "+ sender.toString() + "\n" +
                emailContent.setText("FROM: "+ sender.toString() + "\n" +
                    "SENT DATE: "+ message.getSentDate() + "\n" +
                    "SUBJECT: "+ message.getSubject() + "\n" );

                String lala = message.getContent().toString();
            //Multipart multipart = (Multipart) message.getContent();
            //BodyPart bodyPart = multipart.getBodyPart(0);
            emailContent.setText(emailContent.getText() + "CONTENT: " + "\n" + lala);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
