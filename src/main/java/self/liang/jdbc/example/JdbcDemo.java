package self.liang.jdbc.example;

import java.sql.*;
import java.util.Random;

public class JdbcDemo {
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/test";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&serverTimezone=UTC";


    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);

            //这种方式的万条插入在0.3秒
            stmt = conn.createStatement();
            StringBuilder sql;
//            Random rand = new Random(25);
//            int i;
//            i = rand.nextInt(10000);
//            for(int j=10000;j<20000;j++) {
//                sql = new StringBuilder("INSERT INTO sc(sid,cid,score) values");
//                for (int k = 100; k <= 706; k++) {
//                    sql.append("('"+j+k+"',"+"'"+j+"','"+k+"'),");
//                }
////                System.out.println(sql);
//                sql.replace(sql.length() - 1, sql.length(), "");
//                stmt.execute(sql.toString());
//                conn.commit();
//            }
            sql = new StringBuilder("se INTO sc(sid,cid,score) values");

            System.out.println("准备close");
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
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
