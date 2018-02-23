package com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.supportClasses;

/**
 * Created by Walid Zhani on 21/02/2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.entities.Product;

public class SqliteDatabase extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "CRUD_ORM_db_Product.db";
    private static final String TABLE_PRODUCTS = "Products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRODUCTNAME = "productName";
    //private static final String COLUMN_PRODUCTTYPE = "productType";
    private static final String COLUMN_QUANTITY = "quantity";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PRODUCTS_TABLE = " CREATE TABLE " + TABLE_PRODUCTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + COLUMN_PRODUCTNAME + " TEXT, " + COLUMN_QUANTITY + " INTEGER " + ")";
        sqLiteDatabase.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(sqLiteDatabase);
    }

    public List<Product> listProducts(){
        String sql = "select * from " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();

        List<Product> storeProducts = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                //String type = cursor.getString(2);
                int quantity = Integer.parseInt(cursor.getString(2));
                storeProducts.add(new Product(id, name,quantity));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }

    public void addProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getName());
        //values.put(COLUMN_PRODUCTTYPE, product.getType());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
    }

    public void updateProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getName());
        //values.put(COLUMN_PRODUCTTYPE, product.getType());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_PRODUCTS,values, COLUMN_ID  + " = ? ", new String[]{String.valueOf(product.getId())});
    }

    public Product findProduct(String name){
        String query = " SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " = " + name;
        SQLiteDatabase db = this.getWritableDatabase();
        Product mProduct = null;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String productName = cursor.getString(1);
            //String productType = cursor.getString(2);
            int productQuantity = Integer.parseInt(cursor.getString(2));
            mProduct = new Product(id, productName, productQuantity);
        }
        cursor.close();
        return mProduct;
    }

    public void deleteProduct(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ? ", new String[]{String.valueOf(id)});
    }

}
