package self.liang.springboot.example.listenter;

import org.springframework.boot.CommandLineRunner;

public class HelloCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("HelloCommandLineRunner......");
    }
}
