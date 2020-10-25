package self.liang.windows;

import java.io.File;

//统一命名zip
public class RenameFileUtil {

    public static void main(String[] args) {

        renameUnreadableChar();

    }
    public static void reNameZip(){
        String dir = "E:/BaiduNetdiskDownload/100205";
        File file = new File(dir);
        boolean isDirectory = file.isDirectory();
        if (isDirectory) {
            File[] files = file.listFiles();
            for (File f : files) {
                boolean b = f.renameTo(new File(dir + "/" + f.getName() + ".zip"));
            }
        }
    }

    public static void renameUnreadableChar(){
        String dir = "E:\\BaiduNetdiskDownload\\093007\\ANDY0320-6";
        File file = new File(dir);
        boolean isDirectory = file.isDirectory();
        if (isDirectory) {
            File[] files = file.listFiles();
            for (File f : files) {
                System.out.println(f.getName());
                String fileName = f.getName();
                String replace = fileName.replace("・", "");
                replace = replace.replace("♥", "");
                boolean b = f.renameTo(new File(dir + "/" + replace));
            }
        }
    }

}
