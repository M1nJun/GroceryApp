package com.mycompany.inventoryapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class InventoryApp {
    public static void main(String[] args) {
        Statement statement = null;
        PreparedStatement insertStatement = null;
        int initialAmount = 10;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection
              ("jdbc:mysql://localhost:3306/grocery?user=student&password=Cmsc250!");
            statement = connection.createStatement();
            String insertSQL = "insert into inventory(upc, amount) values (?,?)";
            insertStatement = connection.prepareStatement(insertSQL);
        } catch(Exception ex) {
            System.out.println("Problem opening database connection.");
            ex.printStackTrace();
        }
        
        String sql = "select upc from products";
        try {
            ResultSet results = statement.executeQuery(sql);
            while(results.next()) {
                insertStatement.setString(1, results.getString(1));
                insertStatement.setInt(2, initialAmount);
                insertStatement.execute();
            }
        }   catch (Exception ex) {
        System.out.println("Error in SQL select");
        ex.printStackTrace();
    }
        
    }
    
    
}
