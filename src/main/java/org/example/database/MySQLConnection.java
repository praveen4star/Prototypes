package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnection {
    private final String url;
    private final String user;
    private final String password;
    public MySQLConnection(){
        this.url = "jdbc:mysql://localhost:3306/test";
        this.user = "root";
        this.password = "Test@123";
    }
    public Connection getConnection(){
        try {
            return DriverManager.getConnection(this.url, this.user, this.password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
