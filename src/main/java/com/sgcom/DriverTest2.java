package com.sgcom;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DriverTest2 {
    public static void main(String[] args) {
        DriverTest2 t = new DriverTest2();
        config();
        t.test();
    }

    public static void config() {
        try {
            Class<java.sql.Driver> driverClass = (Class<Driver>) Class.forName("com.mysql.jdbc.Driver");
            DriverManager.registerDriver((java.sql.Driver) driverClass.newInstance());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void test() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.124:3306/mydb", "sys", "!jindata123");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM UNSTRUCTURED_DAT");
            rs.next();
            System.out.println(rs.getString(1));

            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}