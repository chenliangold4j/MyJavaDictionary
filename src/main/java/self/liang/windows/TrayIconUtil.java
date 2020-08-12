package self.liang.windows;

import lombok.SneakyThrows;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TrayIconUtil {

    public static void startToast(final List<Map<String, String>> words) {
        if (SystemTray.isSupported()) {
            new Thread(() -> {
                Random random = new Random(System.currentTimeMillis());
                while (true) {
                    int i = random.nextInt(words.size());
                    Map<String, String> stringStringMap = words.get(i);
                    try {
                        TrayIconUtil.displayTray(stringStringMap.get("word"), stringStringMap.get("trans"), stringStringMap.get("phonetic"));
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(10* 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            System.err.println("System tray not supported!");
        }
    }

    public static void displayTray(String word, String trans, String phonetic) throws AWTException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage(word, trans+"\n"+phonetic, TrayIcon.MessageType.INFO);

    }

}
