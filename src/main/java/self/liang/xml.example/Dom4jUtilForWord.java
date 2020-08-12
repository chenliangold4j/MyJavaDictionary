package self.liang.xml.example;


import org.dom4j.Document;
import org.dom4j.Element;

import java.util.*;


public class Dom4jUtilForWord {

    public static List<Map<String, String>> getAllItemFromXmlString(String xml) {
        Document document = Dom4jUtil.parse(xml);
        Element root = document.getRootElement();
        List<Map<String, String>> result = new ArrayList<>();
        if (root != null) {
            Iterator<Element> eleIterator = root.elementIterator();
            while (eleIterator.hasNext()) {
                Element son = eleIterator.next();
                if (son != null) {
                    Map<String, String> item = getMapFromELement(son);
                    result.add(item);
                }
            }
        }

        return result;
    }

    private static Map<String, String> getMapFromELement(Element elements) {
        Map<String, String> map = new HashMap<>();
        for (Iterator<Element> it = elements.elementIterator(); it.hasNext(); ) {
            Element element = it.next();
            String key = element.getName();
            String value = element.getText();
            map.put(key, value);
        } // --------End For--------
        return map;
    }

}
