package com.abc.database;

import java.util.ArrayList;

public class Item{

    String item,price,qty;


    public Item(String item, String price, String qty) {
        this.item = item;
        this.price = price;
        this.qty = qty;
    }




    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
