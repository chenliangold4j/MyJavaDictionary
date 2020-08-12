package self.liang.windows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class TrayIconDemo2 {

    public static void main(String[] args) throws IOException, AWTException {
        SystemTray ad=SystemTray.getSystemTray();
        Desktop desktop=Desktop.getDesktop();
//		创建一个图标
        PopupMenu menu=new PopupMenu();
        MenuItem item=new MenuItem("apple");
//	    点击后跳转到指定的网址
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    desktop.browse(new URI("http://www.baidu.com"));
                } catch (IOException | URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        MenuItem item1=new MenuItem("huawei");
        MenuItem item2=new MenuItem("xiaomi");
        menu.add(item);
        menu.add(item1);
//		添加分割线
        menu.addSeparator();
        menu.add(item2);

        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon icon = new TrayIcon(image, "Tray Demo");

//		设置提示信息
        icon.setToolTip("jd");
        icon.setPopupMenu(menu);
        icon.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                int button=e.getButton();
//				如果鼠标点击的是左键，就触发事件
                if(button==e.BUTTON1)
                {
                    try {
                        desktop.browse(new URI("http://www.jd.com"));
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
        ad.add(icon);
//		添加阻塞，让图标一直有
        while(true)
        {

        }
    }

}
