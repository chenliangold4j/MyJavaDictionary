package self.liang.windows;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import self.liang.xml.example.Dom4jUtil;
import self.liang.xml.example.Dom4jUtilForWord;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WordHelper {

    public static void main(String[] args) {
        File file = new File("src/main/resources/xml/word.xml");
        String xml = FileUtil.readString(file, "UTF8");
        List<Map<String, String>> allItemFromXmlString = Dom4jUtilForWord.getAllItemFromXmlString(xml);
        TrayIconUtil.startToast(allItemFromXmlString);

    }

    private void printDir() {
        // 一般推荐用此方法
        // 获取当前ClassPath的绝对URI路径
        System.out.println("Thread.currentThread().getContextClassLoader():");
        System.out.println(Thread.currentThread().getContextClassLoader()
                .getResource(""));
        System.out.println("---------------------------------------");

        System.out.println("ClasspathUtil.class.getResource:");
        // 获取当前类文件的URI目录
        System.out.println(WordHelper.class.getResource(""));

        // 获取当前的ClassPath的绝对URI路径。
        System.out.println(WordHelper.class.getResource("/"));

        System.out.println("---------------------------------------");

        System.out.println("ClasspathUtil.class.getClassLoader().getResource:");

        // 获取当前ClassPath的绝对URI路径
        System.out
                .println(WordHelper.class.getClassLoader().getResource(""));

        System.out.println("---------------------------------------");

        // 获取当前ClassPath的绝对URI路径
        System.out.println("ClassLoader.getSystemResource:");

        System.out.println(ClassLoader.getSystemResource(""));
        System.out.println("---------------------------------------");

        System.out.println("System.getProperty:");

        // 对于一般项目，这是项目的根路径。对于JavaEE服务器，这可能是服务器的某个路径。
        // 这个并没有统一的规范！所以，绝对不要使用“相对于当前用户目录的相对路径”。
        System.out.println(System.getProperty("user.dir"));
        System.out.println("---------------------------------------");
    }

}
