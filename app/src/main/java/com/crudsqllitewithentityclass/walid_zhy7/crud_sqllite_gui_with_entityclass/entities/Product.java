package com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.entities;

/**
 * Created by Walid Zhani on 21/02/2018.
 */

public class Product {

    private  int    id;
    private  String name;
    //private  String type;
    private  int  quantity;

    public Product() {
    }

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Product(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
