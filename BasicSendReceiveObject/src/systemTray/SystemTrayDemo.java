package systemTray;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SystemTrayDemo {


    public static void constructGUI(){
        SystemTray tray = SystemTray.getSystemTray();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.beep();
        toolkit.beep();
        toolkit.beep();
        toolkit.beep();
        Image image = toolkit.getImage("C:\\Users\\gldng\\Desktop\\smile.jpg");

        PopupMenu menu = new PopupMenu();
        MenuItem m1 = new MenuItem("Say Hello");
        m1.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null,"Helloooooo");
                    }
                }
        );

        menu.add(m1);

        MenuItem m2 = new MenuItem("Open pdf");
        m2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Desktop.isDesktopSupported()){
                    File myFile = new File("C:\\Users\\gldng\\Desktop\\KrediTransferTablosu.pdf");
                    try {
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        System.out.println("Yine hata oldu");
                    }
                }
            }
        });
        menu.add(m2);

        TrayIcon icon = new TrayIcon(image, "SystemTray Demo", menu);
        icon.setImageAutoSize(true);

        try {
            tray.add(icon);
        } catch (AWTException e) {
            System.out.println("hataa");
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                constructGUI();
            }
        });
    }
}
