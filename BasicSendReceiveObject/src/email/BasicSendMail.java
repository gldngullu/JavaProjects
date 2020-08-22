package email;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
//cse482atisik@gmail.com", "EmineEkin"

public class BasicSendMail {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //properties.put("mail.from", "advanced.java.denemeler@gmail.com");
        properties.put("mail.smtp.auth", "true");
        Authenticator a = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("advanced.java.denemeler@gmail.com", "Kaleme34.");
                //return new PasswordAuthentication("cse482atisik@gmail.com", "EmineEkin");
            }
        } ;

        Session session = Session.getDefaultInstance(properties, a);
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom();
            msg.setRecipients(Message.RecipientType.TO, "mertali.koprulu@isik.edu.tr");
            msg.setSubject(".s");
            msg.setSentDate(new Date());
            msg.setText("Nooldu foton gitti");
            Transport.send(msg);
            System.out.println("Message sent");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
