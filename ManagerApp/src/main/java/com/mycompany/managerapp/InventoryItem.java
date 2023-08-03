
package com.mycompany.managerapp;

public class InventoryItem {
    private String upc;
    private String title;
    private int amount;
    
    public InventoryItem(String upc,String title,int amount) {
        this.upc = upc;
        this.title = title;
        this.amount = amount;
    }

    public String getUpc() {
        return upc;
    }

    public String getTitle() {
        return title;
    }

    public int getAmount() {
        return amount;
    }
    public String toString() {
        return upc + "\t" + title + "\t" + amount;
    }
}
