 package edu.lawrence.grocerylist;

import java.sql.*;
import java.util.Collection;

public class GroceryDAO {
    private Statement statement;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    
    public GroceryDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection
              ("jdbc:mysql://localhost:3306/grocery?user=student&password=Cmsc250!");
            statement = connection.createStatement();
            String insertSQL = "insert into line_item(item,purchase) values (?,?)";
            insertStatement = connection.prepareStatement(insertSQL);
            
            //update inventory amount using prepared statement
            String updateSQL = "update inventory set amount = amount-1 where upc=?";
            updateStatement = connection.prepareStatement(updateSQL);
            
            
        } catch(Exception ex) {
            System.out.println("Problem opening database connection.");
            ex.printStackTrace();
        }
    }
    
    public GroceryItem lookupItem(String code) {
        GroceryItem result = null;
        String sql = "select upc,item,price from products where upc=" + code;
        try {
            ResultSet resultSet = statement.executeQuery(sql);

            // Iterate through the result and print the student names
            if (resultSet.next()) {
                result = new GroceryItem(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));
            }
        } catch (Exception ex) {
            System.out.println("Error in SQL select");
            ex.printStackTrace();
        }
        return result;
    }
    
    public boolean recordPurchase(Collection<GroceryItem> purchase) {
        String transactionSQL = "insert into purchase(time) values (CURRENT_TIMESTAMP())";
        String transIDSQL = "select LAST_INSERT_ID()";
        int purchaseID = -1;
        boolean success = true;
        try {
            statement.execute(transactionSQL);
            ResultSet resultSet = statement.executeQuery(transIDSQL);

          
            if (resultSet.next()) {
                purchaseID = resultSet.getInt(1);
            }
        } catch (Exception ex) {
            System.out.println("Error setting up purchase");
            ex.printStackTrace();
            success = false;
        }
        if(purchaseID != -1) {
            try {
                for(GroceryItem item : purchase) {
                    insertStatement.setString(1, item.getUpc());
                    insertStatement.setInt(2, purchaseID);
                    insertStatement.execute();
                    
                    //not sure where to put it but,,
                    updateStatement.setString(1, item.getUpc());
                    updateStatement.execute();
                }
                
            } catch (Exception ex) {
                System.out.println("Error recording line items");
                ex.printStackTrace();
                success = false;
            }
        }
        return success;
    }

}
