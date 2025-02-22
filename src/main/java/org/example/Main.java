package org.example;

import org.example.database.ConnectionPool;
import org.example.database.MySQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // staring the thread
        ConnectionPool connectionPool = new ConnectionPool(5, 10);
        for(int i = 0; i<1000; i++){
            int userId = i+1;
            new Thread(()->{
                try {
                    TestConnection(userId, connectionPool);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    public static void TestConnection(int id, ConnectionPool connectionPool) throws SQLException, InterruptedException {
        Connection connection = connectionPool.getConnection();
        Statement statement = connection.createStatement();
        // building the query
        String query = "SELECT * FROM users where id = " + id;
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            // print id, username, and email
            System.out.println(resultSet.getInt("id") + " " + resultSet.getString("username") + " " + resultSet.getString("email"));
        }
        connectionPool.releaseConnection(connection);
    }
}