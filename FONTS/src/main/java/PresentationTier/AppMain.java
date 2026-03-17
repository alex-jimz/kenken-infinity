package PresentationTier;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AppMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CtrlPresentation ctrlPresentation = new CtrlPresentation();
            }
        });

        UIManager.put("TextField.inactiveForeground", Color.BLACK);
        if (Taskbar.isTaskbarSupported()) {
            Taskbar taskbar = Taskbar.getTaskbar();
            try {
                Image img = ImageIO.read(new File("./Data/Icons/kenkenLogoUnited.png"));
                taskbar.setIconImage(img);
            } catch (Exception e) {
            }
        }
    }
}
