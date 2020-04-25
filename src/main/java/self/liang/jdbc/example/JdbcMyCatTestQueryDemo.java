package self.liang.jdbc.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class JdbcMyCatTestQueryDemo {
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:4007/orders?useUnicode=true&amp;characterEncoding=UTF-8";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "select id,detail,user_id,operator_type,time from log_info where id = ?";
            stmt = conn.prepareStatement(sql);
            Long time = System.currentTimeMillis();
            Random random = new Random();
//            `id` int(11) NOT NULL AUTO_INCREMENT,
//             `detail` varchar(255) DEFAULT NULL,
//             `user_id` int(11) DEFAULT NULL,
//             `operator_type` int(11) DEFAULT NULL,
//            `time` varchar(50) DEFAULT NULL,
//            PRIMARY KEY (`id`)
            for (int i = 10000; i < 20000; i++) {
                stmt.setInt(1,i);
                stmt.executeQuery();
            }
            System.out.println(System.currentTimeMillis() - time);
            System.out.println("准备close");
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }


}
