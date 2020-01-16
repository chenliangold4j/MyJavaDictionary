package self.liang.jdbc.example;

import java.sql.*;
import java.util.Random;

public class JdbcTestTransaction {
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
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            Connection conn1 = DriverManager.getConnection(DB_URL, USER, PASS);
            Connection conn2 = DriverManager.getConnection(DB_URL, USER, PASS);
            new Thread(new TransferAccount("1", "2", 500, conn1)).start();
            new Thread(new TransferAccount("2", "1", 500, conn2)).start();
            Thread.sleep(10000000);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Goodbye!");
    }
}

class TransferAccount implements Runnable {
    String payId;
    String receiveId;
    int num;
    Connection con;

    int count = 50;

    public TransferAccount(String payId, String receiveId, int num, Connection con) {
        this.payId = payId;
        this.receiveId = receiveId;
        this.num = num;
        this.con = con;
    }

    @Override
    public void run() {
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        try {
            while (count > 0) {
//                if(count % 10 == 0)
                    System.out.println(payId + ":" + receiveId+"-- count:" + count);
                count--;
                Thread.sleep(10);
                con.setAutoCommit(false);
                String sql1 = "UPDATE cuser SET money=money+? WHERE id=?";
                pstmt1 = con.prepareStatement(sql1);
                pstmt1.setInt(1, num);
                pstmt1.setString(2, payId);
                pstmt1.executeUpdate();
                String sql2 = "UPDATE cuser SET money=money-? WHERE id=?";

                pstmt2 = con.prepareStatement(sql2);
                pstmt2.setInt(1, num);
                pstmt2.setString(2, receiveId);
                pstmt2.executeUpdate();
                con.commit();
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                pstmt1.close();
                pstmt2.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
