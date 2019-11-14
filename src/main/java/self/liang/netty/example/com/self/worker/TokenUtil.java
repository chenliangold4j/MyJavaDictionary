package self.liang.netty.example.com.self.worker;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class TokenUtil {

    static Queue<String> queue = new LinkedList<>();

    static{
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init() throws IOException {
        String path = "D:/1.txt";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
        String value = null;
        do {
            value = bufferedReader.readLine();
            queue.add(value);
        } while (value != null);
    }

    public static String getValue() {
        String result = null;
        synchronized (TokenUtil.class) {
            result = queue.poll();
        }
        return  result;
    }

}
