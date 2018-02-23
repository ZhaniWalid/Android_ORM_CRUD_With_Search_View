package com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.SupportFilterClasses;



/**
 * Created by HP on 22/02/2018.
 */
// this class used for search view : filter

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.R;
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.entities.Product;
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.supportClasses.ProductAdapter;
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.supportClasses.ProductViewHolder;
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.supportClasses.SqliteDatabase;

import java.util.List;

public class MyFilterProductAdapter extends RecyclerView.Adapter<MyFilterHolder> implements Filterable {

    // for search view : filter
    List<Product>   filterList;
    CustomFilter    filter;
    ProductAdapter  productAdapter;
    SqliteDatabase sqliteDatabase;
    List<Product>   listProducts;
    private Context context;
    private boolean isDeleted = false;


    public MyFilterProductAdapter(Context context, List<Product> listProducts) {
        // for search view : filter
        this.context      = context;
        this.listProducts = listProducts;
        this.filterList   =   listProducts;
        sqliteDatabase = new SqliteDatabase(context);
    }

    // for search view : filter

    @Override
    public MyFilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //CONVERT XML TO VIEW ONBJ
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);

        //HOLDER
        MyFilterHolder holder=new MyFilterHolder(v);

        return holder;
    }

    //DATA BOUND TO VIEWS
    @Override
    public void onBindViewHolder(MyFilterHolder holder, int position) {
        //BIND DATA
        final Product singleProd = listProducts.get(position);
        holder.nameTxt.setText(singleProd.getName());
        holder.quanTxt.setText(String.valueOf(singleProd.getQuantity()+"  Dinars"));// en affichage j'ai utilisé le "Dinars" plus réaliste qua la "quantité"//mais en bd "quantité"

        //IMPLEMENT CLICK LISTENET
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Snackbar.make(v,listProducts.get(pos).getName(), Snackbar.LENGTH_SHORT).show();
            }
        });

        // buttons Edit and Delete

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(singleProd);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database
                sqliteDatabase.deleteProduct(singleProd.getId());
                isDeleted = true;

                if(isDeleted){
                    //refresh the activity page.
                    ((Activity) context).finish(); // Stop Activity
                    context.startActivity(((Activity) context).getIntent()); // Start Activity Again
                    Toast.makeText(context, "Deleting Product Succeeded", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context, "Deleting Product Failed,Please Try Again", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
    //GET TOTAL NUM OF PRODUCTS
    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CustomFilter(filterList,this);
        }
        return filter;
    }

    //
    private void editTaskDialog(final Product product){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.activity_add__product__layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        //final Spinner typeField = (Spinner)subView.findViewById(R.id.enter_type);
        final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);

        if(product != null){
            nameField.setText(product.getName());
            //typeField.setText(product.getType());
            quantityField.setText(String.valueOf(product.getQuantity()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String name  = nameField.getText().toString();
                //final String type  = String.valueOf(typeField.getSelectedItem().toString());
                final int quantity = Integer.parseInt(quantityField.getText().toString());

                if(TextUtils.isEmpty(name) || quantity <= 0) {
                    Toast.makeText(context, "Updating Product Failed,Please Check your input values", Toast.LENGTH_LONG).show();
                }else{
                    sqliteDatabase.updateProduct(new Product(product.getId(),name,quantity));
                    Toast.makeText(context, "Update Succeeded", Toast.LENGTH_LONG).show();
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Task Update : cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

}
