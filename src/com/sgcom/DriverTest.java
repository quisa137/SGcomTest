package com.sgcom;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DriverTest {
    public static void config() {
        try {
            Class<Driver> driverClass = (Class<Driver>) Class.forName("Altibase.jdbc.driver.AltibaseDriver");
            DriverManager.registerDriver((Driver) driverClass.newInstance());
            DriverManager.registerDriver((Driver) driverClass.newInstance());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void test() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:Altibase://192.168.0.112:20300/mydb", "sys", "manager");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM UNSTRUCTURED_DATA");
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
