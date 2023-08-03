package com.mycompany.managerapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable {
    private ManagerAppDAO dao;
    private ObservableList<InventoryItem> updates = FXCollections.observableArrayList();
    
    @FXML private TextField upcCode;
    @FXML private TextField amountNum;
    @FXML private ListView items;
    
    @FXML
    private void enterItem(ActionEvent event) {
        updates.clear();
        String upc = upcCode.getText();
        int amount = Integer.parseInt(amountNum.getText());
        System.out.println(upc + amount);
        
        InventoryItem item = dao.increaseStock(upc, amount);
        System.out.println(item.getUpc() + " " + item.getTitle() + " " + item.getAmount());
        updates.add(item);
        
        upcCode.setText("");
        amountNum.setText("");
    }
    
    @FXML
    private void report(ActionEvent event) {
        updates.clear();
        for(InventoryItem item : dao.reportLowStock()){
            updates.add(item);
        }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new ManagerAppDAO();
        items.setItems(updates);
    }
}
