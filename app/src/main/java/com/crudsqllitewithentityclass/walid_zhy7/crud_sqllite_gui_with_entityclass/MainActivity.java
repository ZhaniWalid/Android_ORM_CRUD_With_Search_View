package com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.R;

import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.SupportFilterClasses.MyFilterProductAdapter;
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.entities.Product;
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.supportClasses.ProductAdapter;
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.supportClasses.SqliteDatabase;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private  static final String TAG = MainActivity.class.getSimpleName();
    private SqliteDatabase mDatabase;

    SearchView sv; // for search view : filter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout fLayout = (FrameLayout)findViewById(R.id.activity_to_do);
        RecyclerView productView = (RecyclerView)findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(this);

        List<Product> allProducts = mDatabase.listProducts();

        if ( allProducts.size() > 0 ){
            productView.setVisibility(View.VISIBLE);
            ProductAdapter mAdapter = new ProductAdapter(this,allProducts);
            productView.setAdapter(mAdapter);
        }else{
            productView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no product in the database. Start adding now", Toast.LENGTH_LONG).show();
        }



        // for search view : filter

       sv = (SearchView) findViewById(R.id.mSearch);
       //RecyclerView rv = (RecyclerView) findViewById(R.id.product_list);
        //SET ITS PROPETRIES
        productView.setLayoutManager(new LinearLayoutManager(this));
        productView.setItemAnimator(new DefaultItemAnimator());
        //ADAPTER
        List<Product> allProducts_2 = mDatabase.listProducts();
        final MyFilterProductAdapter adapter = new MyFilterProductAdapter(this,allProducts_2);
        productView.setAdapter(adapter);

        //SEARCH
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
               adapter.getFilter().filter(query);
               return false;
            }
        });

        // End -> for search view : filter

        // add portion
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new quick task
                addTaskDialog();
            }
        });

        // fin add portion
    }

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.activity_add__product__layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        //final Spinner typeField = (Spinner)subView.findViewById(R.id.enter_type);
        final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD PRODUCT", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String name  = nameField.getText().toString();
                //final String type  = typeField.getText().toString();
                final int quantity = Integer.parseInt(quantityField.getText().toString());

                if(TextUtils.isEmpty(name) || quantity <= 0){
                    Toast.makeText(MainActivity.this, "Adding Product Failed,Please Check your input values", Toast.LENGTH_LONG).show();
                }else{
                    Product product = new Product(name,quantity);
                    mDatabase.addProduct(product);
                    Toast.makeText(MainActivity.this, "Adding Product Succeeded", Toast.LENGTH_LONG).show();
                    //refresh the activity
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Task Add : cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }
}
