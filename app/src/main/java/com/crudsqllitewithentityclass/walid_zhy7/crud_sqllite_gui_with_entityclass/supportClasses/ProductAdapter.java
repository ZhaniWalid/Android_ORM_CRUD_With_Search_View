package com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.supportClasses;

/**
 * Created by Walid Zhani on 21/02/2018.
 */
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.Toast;

import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.SupportFilterClasses.CustomFilter;
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.entities.Product;
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.R;
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.supportClasses.SqliteDatabase;
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.supportClasses.ProductViewHolder;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private Context context;
    public  List<Product> listProducts;
    private SqliteDatabase mDatabase;

    public ProductAdapter() {
    }

    public ProductAdapter(Context context, List<Product> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product__list__layout,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product singleProduct = listProducts.get(position);
        holder.name.setText(singleProduct.getName());
        holder.quantity.setText(String.valueOf(singleProduct.getQuantity()+"  Dinars")); // en affichage j'ai utilisé le "Dinars" plus réaliste qua la "quantité"//mais en bd "quantité"

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(singleProduct);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database
                mDatabase.deleteProduct(singleProduct.getId());
                //refresh the activity page.
                ((Activity)context).finish(); // Stop Activity
                context.startActivity(((Activity) context).getIntent()); // Start Activity Again
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

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
                    mDatabase.updateProduct(new Product(product.getId(),name,quantity));
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
