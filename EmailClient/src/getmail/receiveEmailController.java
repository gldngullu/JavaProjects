package getmail;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;


public class receiveEmailController implements Initializable {

    @FXML
    private TextArea emailContent;
    private Session session;
    @FXML
    private ListView<String> emails;

    private ObservableList<String> messages;
    private Folder inbox;

    @FXML
    private TextField newEmailFrom;
    @FXML
    private TextField newEmailTo;
    @FXML
    private TextField newEmailSubject;
    @FXML
    private TextArea newEmailContent;
    private String email;
    private String password;
    @FXML
    private Label feedbackLabel;
    @FXML
    private Button downloadAttachmentButton;
    private MimeBodyPart instantMimeBodyPart;
    private String fileName;
    @FXML
    private Label attachmentInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imaps");
        emailContent.setWrapText(true);
        emailContent.setEditable(false);
        messages = FXCollections.observableArrayList();
        emails.setItems(messages);
        session = Session.getInstance(properties, null);
        login();
        attachmentInfo.setWrapText(true);
        downloadAttachmentButton.setVisible(false);

        emails.setCellFactory(param -> new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item==null) {
                    setGraphic(null);
                    setText(null);
                    setStyle("");
                }else{
                    setMinWidth(param.getWidth());
                    setMaxWidth(param.getWidth());
                    setPrefWidth(param.getWidth());
                    setWrapText(true);
                    setText(item);
                }
            }
        });

    }


    private void login(){
        try {
            Store store = session.getStore("imaps");
            email = "advanced.java.denemeler@gmail.com";
            password = "Kaleme34.";
            store.connect("imap.gmail.com", email, password);
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            listAllMails(inbox);

            newEmailFrom.setText(email);
            newEmailFrom.setEditable(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void listAllMails(Folder inbox){
        try{
            Message unread[] = inbox.search(new FlagTerm(new Flags(
                    Flags.Flag.SEEN), false));
            System.out.println("No. of Unread Messages : " + unread.length);

            String[] allMessages = new String[inbox.getMessageCount()];

            for (int i = 1; i <= inbox.getMessageCount(); i++) {
                allMessages[inbox.getMessageCount()-i] = inbox.getMessage(i).getFrom()[0] + "  |  " + inbox.getMessage(i).getSubject();
            }
            messages.addAll(Arrays.asList(allMessages));
            getAndShowChoosenMail(0);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void clearNewMailFields(){
        newEmailTo.setText("");
        newEmailSubject.setText("");
        newEmailContent.setText("");
    }

    @FXML
    private void handleListViewClick(MouseEvent changeListener){
        int indexOfMail = emails.getSelectionModel().getSelectedIndex();
        getAndShowChoosenMail(indexOfMail);
    }

    private void getAndShowChoosenMail(int indexOfMail){
        try{
            Message message = inbox.getMessage(inbox.getMessageCount()-indexOfMail);
            Address sender = message.getFrom()[0];
                emailContent.setText("FROM: "+ sender.toString() + "\n" +
                    "SENT DATE: "+ message.getSentDate() + "\n" +
                    "SUBJECT: "+ message.getSubject() + "\n" );

                String content = "";
                if(message.getContent() instanceof String){
                    content = message.getContent().toString();
                } else {
                    Multipart multipart = (Multipart) message.getContent();

                    int partCount = multipart.getCount();
                    for(int i = 0 ; i < partCount ; i++){
                        MimeBodyPart mimeBodyPart =(MimeBodyPart) multipart.getBodyPart(i);
                        if(Part.ATTACHMENT.equalsIgnoreCase(mimeBodyPart.getDisposition())){
                            //so we have a file in this part
                            content = content + "\n " + "Attachment found in email";
                            downloadAttachmentButton.setVisible(true);
                            instantMimeBodyPart = mimeBodyPart;
                        } else {
                            // Buradaki multipartları stringe çevirmenin yöntemini bulamadım :(
                            content = content + "\n " + multipart.getBodyPart(i);
                            downloadAttachmentButton.setVisible(false);
                        }

                    }

                    content = content + "\n " + instantMimeBodyPart.getContent().toString();
                }

            emailContent.setText(emailContent.getText() + "CONTENT: " + "\n" +content);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void createNewEmail(){
        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            Authenticator a = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            };
            Session sessionForSendMail = Session.getDefaultInstance(props, a);
            MimeMessage msg = new MimeMessage(sessionForSendMail);
            msg.setFrom();
            msg.setRecipients(Message.RecipientType.TO, newEmailTo.getText());
            msg.setSubject(newEmailSubject.getText());
            msg.setSentDate(new Date());
            msg.setText(newEmailContent.getText());


            if (fileName != null) {

                Multipart multipart = new MimeMultipart();
                BodyPart bodyPart = new MimeBodyPart();
                bodyPart.setText(newEmailContent.getText());
                multipart.addBodyPart(bodyPart);
                BodyPart attachmentBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(fileName);
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                attachmentBodyPart.setFileName(fileName);
                multipart.addBodyPart(attachmentBodyPart);
                msg.setContent(multipart);

            } else {
                msg.setText(newEmailContent.getText());
            }
            sendNewEmail(msg);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void sendNewEmail(MimeMessage message){
        try {
            Transport.send(message);
            System.out.println("Message sent");
            clearNewMailFields();

            feedbackLabel.setText("Email has been sent to " + message.getRecipients(Message.RecipientType.TO)[0].toString());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void downloadAttachment(){
        try{
            instantMimeBodyPart.saveFile(instantMimeBodyPart.getFileName());
            saveAttachmentToFile(instantMimeBodyPart);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void saveAttachmentToFile(MimeBodyPart mimeBodyPart) {
        try {
            InputStream inputStream = mimeBodyPart.getInputStream();
            File fileToSave = new File("C:\\Users\\gldng\\Downloads\\", mimeBodyPart.getFileName());
            FileOutputStream fileOutputStream = new FileOutputStream(fileToSave);
            byte[] buf = new byte[4096];
            int bytesRead;
            while((bytesRead = inputStream.read(buf))!=-1) {
                fileOutputStream.write(buf, 0, bytesRead);
            }
            fileOutputStream.close();
            System.out.println("Attachment saved");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void addAttachment(){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        String fileAdress = selectedFile.getAbsolutePath();
        fileName= fileAdress.replace("\\","\\\\");
        attachmentInfo.setText("Attachment added to email from the path: " + fileName);
    }



}
