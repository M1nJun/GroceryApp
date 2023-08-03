package com.mycompany.managerapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ManagerAppDAO {
    private Statement statement;
    private PreparedStatement updateStatement;
    
    public ManagerAppDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection
              ("jdbc:mysql://localhost:3306/grocery?user=student&password=Cmsc250!");
            statement = connection.createStatement();
          
            String updateSQL = "update inventory set amount = amount+? where upc=?";
            updateStatement = connection.prepareStatement(updateSQL);
            
        } catch(Exception ex) {
            System.out.println("Problem opening database connection.");
            ex.printStackTrace();
        }
    }
    
    public InventoryItem increaseStock(String upc, int amount){
        
        InventoryItem item = null;
        try {
            updateStatement.setInt(1, amount);
            updateStatement.setString(2, upc);
            updateStatement.execute();
            String sql = "select inventory.upc, inventory.amount, products.item from inventory join products on inventory.upc = products.upc";
            ResultSet result = statement.executeQuery(sql);
            if(result.next()){
                item = new InventoryItem(result.getString(1), result.getString(3), result.getInt(2));
            }
            
        } catch(Exception ex) {
            System.out.println("Problem opening database connection.");
            ex.printStackTrace();
        }
        
        return item;
    }
    
    public Collection<InventoryItem> reportLowStock(){
        ArrayList<InventoryItem> items = new ArrayList();
        try {
            String scanSql = "select inventory.upc, inventory.amount, products.item from inventory join products on inventory.upc = products.upc where amount<6";
            ResultSet results = statement.executeQuery(scanSql);
            while(results.next()){
                InventoryItem item = new InventoryItem(results.getString(1), results.getString(3), results.getInt(2));
                items.add(item);
            }
        } catch(Exception ex){
            System.out.println("Problem opening database connection.");
            ex.printStackTrace();
        }
        
        return items;
    }
}
