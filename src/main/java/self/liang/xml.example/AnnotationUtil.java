package self.liang.xml.example;
import java.lang.reflect.Field;

public class AnnotationUtil {

    public static String getXmlAnnotationValue(Field field, Class<? extends XmlName> clazz) {
        String result = null;
        XmlName xmlName = field.getAnnotation(clazz);
        if (xmlName != null) {
            result = xmlName.value();
        }
        return result;
    }

}
