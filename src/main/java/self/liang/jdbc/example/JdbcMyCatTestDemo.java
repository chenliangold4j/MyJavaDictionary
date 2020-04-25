package self.liang.jdbc.example;

import java.sql.*;
import java.util.Random;

public class JdbcMyCatTestDemo {
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:8067/TESTDB?useUnicode=true&amp;characterEncoding=UTF-8";

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
            String sql = "INSERT INTO log_info(id,detail,user_id,operator_type,time) values(?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            Long time = System.currentTimeMillis();
            stmt.clearParameters();
            for (int k = 40001; k <= 50000; k++) {
                stmt.setInt(1,k);
                stmt.setString(2,"detial"+k);
                stmt.setInt(3,k);
                stmt.setInt(4,k);
                stmt.setString(5,"time"+k);
                stmt.executeUpdate();
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
