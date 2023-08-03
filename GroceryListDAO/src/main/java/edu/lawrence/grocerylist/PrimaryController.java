package edu.lawrence.grocerylist;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable {
    private GroceryDAO dao;
    private ObservableList<GroceryItem> purchase = FXCollections.observableArrayList();

    @FXML private TextField upcCode;
    @FXML private ListView items;
    @FXML private Label grandTotal;
    @FXML private Label message;
    
    @FXML
    private void enterItem(ActionEvent event) {
        String code = upcCode.getText();
        if(code.isBlank())
            return;
        GroceryItem toBuy = dao.lookupItem(code);
        if(toBuy != null) {
            purchase.add(toBuy);
            message.setText("Enter code:");
        } else {
            message.setText("Invalid code");
        }
        grandTotal.setText("");
        upcCode.setText("");
    }
    
    @FXML
    private void removeItem(ActionEvent event) {
        if(purchase.size() > 0) {
            int selected = items.getSelectionModel().getSelectedIndex();
            purchase.remove(selected);
        }
    }
    
    @FXML
    private void concludePurchase(ActionEvent event) {
        if(purchase.isEmpty()) return;

        if(dao.recordPurchase(purchase) == false) {
            grandTotal.setText("Error recording purchase.");
            return;
        }

        double total = 0.0;
        for(int n = 0;n < purchase.size();n++) {
            total = total + purchase.get(n).getPrice();
        }
        String result = String.format("Your total is $%.2f", total);
        grandTotal.setText(result);
        message.setText("Enter code:");  
        purchase.clear();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new GroceryDAO();
        items.setItems(purchase);
    } 
    
    
}
