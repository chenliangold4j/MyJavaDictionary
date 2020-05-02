package self.liang.jdbc.example;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.Random;
import java.util.UUID;

public class JdbcMyCatTestDemo {
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3307/testdata?useUnicode=true&amp;characterEncoding=UTF-8";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) throws InterruptedException {
        JdbcMyCatTestDemo jdbcMyCatTestDemo = new JdbcMyCatTestDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                jdbcMyCatTestDemo.addUser(200 * 10000, 100 * 10000 + 1);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                jdbcMyCatTestDemo.addProduct(20000, 10001);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                jdbcMyCatTestDemo.addshipping(200 * 10000, 100 * 10000 + 1);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                jdbcMyCatTestDemo.addCart(200 * 10000, 100 * 10000 + 1);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                jdbcMyCatTestDemo.addOrderCommon(200 * 10000, 100 * 10000 + 1);
            }
        }).start();

        Thread.sleep(100000000);
    }

    public void addOrderCommon(int count, int start) {
        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);
            String sql = "insert into mmall_order_item ( user_id, order_no, product_id, product_name, product_image, current_unit_price, quantity, total_price, create_time, update_time) " +
                    "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            String sql2 = "insert into mmall_order ( order_no, user_id, shipping_id, payment, payment_type, postage, status, payment_time, send_time, end_time, close_time, create_time, update_time) " +
                    "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            String sql3 = "insert into mmall_pay_info (user_id, order_no, pay_platform, platform_number, platform_status, create_time, update_time) " +
                    "values ( ?, ?, ?, ?, ?, ?, ?) ";
            stmt = conn.prepareStatement(sql);
            stmt2 = conn.prepareStatement(sql2);
            stmt3 = conn.prepareStatement(sql3);

            Long time = System.currentTimeMillis();
            stmt.clearParameters();
            stmt2.clearParameters();
            stmt3.clearParameters();
            for (int k = start; k < count + start; k++) {
                int user_id = k;
                int product_id = k % 1000054;
                if (product_id < 26) product_id = 26;
                long orderNo = k + 10000000;
                int shippingId = user_id + 9;
                BigDecimal price = BigDecimal.valueOf(1000);
                String productName = "商品" + user_id;
                int quantity = k % 3;
                BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));

                stmt.setInt(1, user_id);
                stmt.setLong(2, orderNo);
                stmt.setInt(3, product_id);
                stmt.setBytes(4, productName.getBytes(Charset.forName("UTF-8")));
                stmt.setBytes(5, "test.png".getBytes(Charset.forName("UTF-8")));
                stmt.setBigDecimal(6, price);
                stmt.setInt(7, quantity);
                stmt.setBigDecimal(8, total);
                stmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
                stmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
                stmt.addBatch();

//                order_no, user_id, shipping_id, payment, payment_type, postage, status, payment_time, send_time, end_time, close_time, create_time, update_time
                stmt2.setLong(1, orderNo);
                stmt2.setInt(2, user_id);
                stmt2.setInt(3, shippingId);
                stmt2.setBigDecimal(4, BigDecimal.valueOf(20));
                stmt2.setInt(5, 1);
                stmt2.setInt(6, 10);
                stmt2.setInt(7, 20);
                stmt2.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                stmt2.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
                stmt2.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
                stmt2.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
                stmt2.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
                stmt2.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
                stmt2.addBatch();
//                user_id, order_no, pay_platform, platform_number, platform_status, create_time, update_time
                stmt3.setInt(1, user_id);
                stmt3.setLong(2, orderNo);
                stmt3.setInt(3, 1);
                stmt3.setBytes(4, UUID.randomUUID().toString().getBytes("UTF-8"));
                stmt3.setBytes(5, "OK".getBytes());
                stmt3.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                stmt3.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                stmt3.addBatch();
                if (k % 5000 == 0) {
                    stmt.executeBatch();
                    stmt2.executeBatch();
                    stmt3.executeBatch();
                    conn.commit();
                    System.out.println(System.currentTimeMillis() - time);
                }
            }
            conn.commit();
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

    //start 为userId
    public void addCart(int count, int start) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);
            String sql = "insert into mmall_cart ( user_id, product_id, quantity, checked, create_time, update_time) values ( ?, ?, ?, ?, ?, ?) ";
            stmt = conn.prepareStatement(sql);
            Long time = System.currentTimeMillis();
            stmt.clearParameters();

            for (int k = start; k < count + start; k++) {
                stmt.setInt(1, k);
                int product_id = k % 1000000;
                if (product_id < 26) product_id = 26;
                stmt.setInt(2, product_id);
                stmt.setInt(3, (k % 3) + 1);
                stmt.setInt(4, 1);
                stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                stmt.addBatch();
                if (k % 10000 == 0) {
                    stmt.executeBatch();
                    conn.commit();
                    System.out.println(System.currentTimeMillis() - time);
                }
            }
            conn.commit();
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


    //start 为user
    public void addshipping(int shippingCount, int startNum) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);
            String sql = "insert into mmall_shipping (user_id, receiver_name, receiver_phone, receiver_mobile, receiver_province, " +
                    "receiver_city, receiver_district, receiver_address, receiver_zip, create_time, update_time ) " +
                    "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
            stmt = conn.prepareStatement(sql);
            Long time = System.currentTimeMillis();
            stmt.clearParameters();

            for (int k = startNum; k < shippingCount + startNum; k++) {
                stmt.setInt(1, k);
                stmt.setBytes(2, ("接收者" + k).getBytes(Charset.forName("UTF-8")));
                stmt.setBytes(3, ("手机" + k).getBytes(Charset.forName("UTF-8")));
                stmt.setBytes(4, ("电话" + k).getBytes(Charset.forName("UTF-8")));
                stmt.setBytes(5, ("省份" + k % 24).getBytes(Charset.forName("UTF-8")));
                stmt.setBytes(6, ("城市" + k % 1024).getBytes(Charset.forName("UTF-8")));
                stmt.setBytes(7, ("区" + k).getBytes(Charset.forName("UTF-8")));
                stmt.setBytes(8, ("地址" + k).getBytes(Charset.forName("UTF-8")));
                stmt.setBytes(9, ("" + (k % 100000)).getBytes(Charset.forName("UTF-8")));
                stmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
                stmt.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
                stmt.addBatch();
                if (k % 10000 == 0) {
                    stmt.executeBatch();
                    conn.commit();
                    System.out.println(System.currentTimeMillis() - time);
                }
            }
            conn.commit();
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

    public void addUser(int userCont, int startNum) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);
            String sql = "insert into mmall_user ( username, password,email, phone, question, answer, role, create_time,update_time)     values ( ?, ?,?, ?, ?, ?, ?, ?,?)";
            stmt = conn.prepareStatement(sql);
            Long time = System.currentTimeMillis();
            stmt.clearParameters();
            for (int k = startNum; k <= startNum + userCont; k++) {
                stmt.setBytes(1, ("用户" + k).getBytes(Charset.forName("UTF-8")));
                stmt.setBytes(2, ("密码" + k).getBytes(Charset.forName("UTF-8")));
                stmt.setBytes(3, ("email" + k).getBytes(Charset.forName("UTF-8")));
                stmt.setBytes(4, ("phone" + k).getBytes(Charset.forName("UTF-8")));
                stmt.setBytes(5, ("问题" + k).getBytes(Charset.forName("UTF-8")));
                stmt.setBytes(6, ("答案" + k).getBytes(Charset.forName("UTF-8")));
                stmt.setInt(7, 0);
                stmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                stmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
                stmt.addBatch();
                if (k % 5000 == 0) {
                    stmt.executeBatch();
                    conn.commit();
                    System.out.println(System.currentTimeMillis() - time);
                }
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

    public void addProduct(int proCount, int startNum) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);
            String sql = "insert into mmall_product ( category_id, name, subtitle, main_image, price, stock, status, create_time, update_time, sub_images, detail ) " +
                    "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
            stmt = conn.prepareStatement(sql);
            Long time = System.currentTimeMillis();
            stmt.clearParameters();

            for (int j = 100006; j < 100031; j++) {
                for (int k = startNum; k < startNum + proCount; k++) {
                    stmt.setInt(1, j);
                    stmt.setBytes(2, ("商品" + j + k).getBytes(Charset.forName("UTF-8")));
                    stmt.setBytes(3, ("subtitle" + j + k).getBytes(Charset.forName("UTF-8")));
                    stmt.setBytes(4, ("241997c4-9e62-4824-b7f0-7425c3c28917.jpeg" + k).getBytes(Charset.forName("UTF-8")));
                    stmt.setBigDecimal(5, BigDecimal.valueOf(k));
                    stmt.setInt(6, 10000);
                    stmt.setInt(7, 1);
                    stmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                    stmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
                    stmt.setBytes(10, "241997c4-9e62-4824-b7f0-7425c3c28917.jpeg,b6c56eb0-1748-49a9-98dc-bcc4b9788a54.jpeg,92f17532-1527-4563-aa1d-ed01baa0f7b2.jpeg,3adbe4f7-e374-4533-aa79-cc4a98c529bf.jpeg".getBytes("UTF-8"));
                    stmt.setBytes(11, ("<p><img alt=\"10000.jpg\" src=\"http://img.happymmall.com/00bce8d4-e9af-4c8d-b205-e6c75c7e252b.jpg\" width=\"790\" height=\"553\"><br></p><p><img alt=\"20000.jpg\" src=\"http://img.happymmall.com/4a70b4b4-01ee-46af-9468-31e67d0995b8.jpg\" width=\"790\" height=\"525\"><br></p><p><img alt=\"30000.jpg\" src=\"http://img.happymmall.com/0570e033-12d7-49b2-88f3-7a5d84157223.jpg\" width=\"790\" height=\"365\"><br></p><p><img alt=\"40000.jpg\" src=\"http://img.happymmall.com/50515c02-3255-44b9-a829-9e141a28c08a.jpg\" width=\"790\" height=\"525\"><br></p><p><img alt=\"50000.jpg\" src=\"http://img.happymmall.com/c138fc56-5843-4287-a029-91cf3732d034.jpg\" width=\"790\" height=\"525\"><br></p><p><img alt=\"60000.jpg\" src=\"http://img.happymmall.com/c92d1f8a-9827-453f-9d37-b10a3287e894.jpg\" width=\"790\" height=\"525\"><br></p><p><br></p><p><img alt=\"TB24p51hgFkpuFjSspnXXb4qFXa-1776456424.jpg\" src=\"http://img.happymmall.com/bb1511fc-3483-471f-80e5-c7c81fa5e1dd.jpg\" width=\"790\" height=\"375\"><br></p><p><br></p><p><img alt=\"shouhou.jpg\" src=\"http://img.happymmall.com/698e6fbe-97ea-478b-8170-008ad24030f7.jpg\" width=\"750\" height=\"150\"><br></p><p><img alt=\"999.jpg\" src=\"http://img.happymmall.com/ee276fe6-5d79-45aa-8393-ba1d210f9c89.jpg\" width=\"790\" height=\"351\"><br></p>").getBytes(Charset.forName("UTF-8")));
                    stmt.addBatch();
                    if (k % 5000 == 0) {
                        stmt.executeBatch();
                        conn.commit();
                        System.out.println(System.currentTimeMillis() - time);
                    }
                }
            }
            conn.commit();
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
