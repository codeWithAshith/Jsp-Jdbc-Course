package com.codewithashith.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnection {

    private static Connection connection = null;

    //    private static String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
    private static String jdbcURL = "jdbc:h2:mem:todo";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "root";

    public static Connection getConnection() {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
//            System.out.println("connected -> " + !connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void createData() {
        try {
            Connection con = getConnection();
            con.createStatement().executeUpdate("" +
                    "CREATE TABLE IF NOT EXISTS auth (" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "username VARCHAR(255)," +
                    "password VARCHAR(255)," +
                    "PRIMARY KEY(id)" +
                    ")");
            con.createStatement().executeUpdate("" +
                    "INSERT INTO auth (username, password) VALUES" +
                    "('a','a');" +
                    "");
            con.createStatement().executeUpdate("" +
                    "INSERT INTO auth (username, password) VALUES" +
                    "('b','b');" +
                    "");
            con.createStatement().executeUpdate("" +
                    "INSERT INTO auth (username, password) VALUES" +
                    "('ab','ab');" +
                    "");
            con.createStatement().executeUpdate("" +
                    "CREATE TABLE IF NOT EXISTS todo (" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "todo VARCHAR(255)," +
                    "image blob,"+
                    "userId INT," +
                    "PRIMARY KEY(id)," +
                    "FOREIGN KEY (userId) REFERENCES auth(id)"+
                    ")");
//            con.createStatement().executeUpdate("" +
//                    "INSERT INTO todo (todo, userId) VALUES" +
//                    "('ab',1);" +
//                    "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

