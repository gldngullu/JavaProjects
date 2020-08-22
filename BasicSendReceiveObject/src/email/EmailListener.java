package email;

import com.sun.mail.imap.IMAPFolder;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.MessageChangedEvent;
import javax.mail.event.MessageChangedListener;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import javax.swing.*;
import java.util.Properties;

public class EmailListener {

    public static void main(String[] args) {
        new EmailListener();

    }

    public EmailListener() {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getInstance(props, null);

        try {
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", "advanced.java.denemeler@gmail.com", "Kaleme34.");

            final IMAPFolder inbox = (IMAPFolder) store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            inbox.addMessageCountListener(new MessageCountListener() {
                @Override
                public void messagesAdded(MessageCountEvent e) {
                    System.out.println("Message Count Event Fired");
                    Thread t = new Thread(new Runnable() {
                        public void run() {
                            JOptionPane.showMessageDialog(null, "Hellooooo");
                        }

                    });
                    t.start();
                }

                @Override
                public void messagesRemoved(MessageCountEvent e) {
                    System.out.println("Message Removed Event fired");
                }
            });
            inbox.addMessageChangedListener(new MessageChangedListener() {
                @Override
                public void messageChanged(MessageChangedEvent e) {
                    System.out.println("Message Changed Event fired");
                }
            });

            Thread t = new Thread(new Runnable() {

                public void run() {
                    try {
                        while (true) {
                            inbox.idle();
                        }
                    } catch (MessagingException ex) {
                        //Handling exception goes here
                    }
                }
            });

            t.start();

        } catch (Exception mex) {
            mex.printStackTrace();
        }

    }
}
