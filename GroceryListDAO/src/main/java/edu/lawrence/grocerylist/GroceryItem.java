package edu.lawrence.grocerylist;

public class GroceryItem {
    private String upc;
    private String title;
    private int price;
    
    public GroceryItem(String code,String name,int cost) {
        upc = code;
        title = name;
        price = cost;
    }

    public String getUpc() {
        return upc;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price/100.0;
    }
    
    public String getPriceStr() {
        if(price%100 < 10)
            return "$" + price/100 + ".0" + price%100;
        return "$" + price/100 + "." + price%100;
    }
    
    public String toString() {
        return title + " " + this.getPriceStr();
    }
}

