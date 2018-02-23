package com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.supportClasses;

/**
 * Created by HP on 21/02/2018.
 */
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.R;

// for the search view : filter
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.SupportFilterClasses.ItemClickListener;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public TextView name,quantity;
    public ImageView deleteProduct;
    public ImageView editProduct;


    public ProductViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.prod_name);
        quantity = (TextView)itemView.findViewById(R.id.prod_quant);
        deleteProduct = (ImageView)itemView.findViewById(R.id.delete_product);
        editProduct  = (ImageView)itemView.findViewById(R.id.edit_product);

    }
}
