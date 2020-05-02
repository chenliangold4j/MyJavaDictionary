package self.liang;

public class MarkUtil {

    private static final String DEFAULT_PREPOSITION = ">>>>>>>>>liang>>>>>>>>>";

    public static void soutWithMark(String mark, Object object) {
        System.out.println(DEFAULT_PREPOSITION + mark + ":" + object.toString());
    }

    public static void soutWithMark(Object object) {
        soutWithMark(DEFAULT_PREPOSITION, object);
    }
}
