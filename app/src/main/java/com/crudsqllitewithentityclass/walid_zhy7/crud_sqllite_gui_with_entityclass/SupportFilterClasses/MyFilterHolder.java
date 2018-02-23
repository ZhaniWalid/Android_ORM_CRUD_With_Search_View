package com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.SupportFilterClasses;

/**
 * Created by HP on 22/02/2018.
 */
// this class used for search view : filter

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.R;

public class MyFilterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   public TextView nameTxt,quanTxt;
   ItemClickListener itemClickListener;
   public ImageView deleteProduct;
   public ImageView editProduct;


    public MyFilterHolder(View itemView) {
        super(itemView);
        nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
        quanTxt= (TextView) itemView.findViewById(R.id.quanTxt);
        deleteProduct = (ImageView)itemView.findViewById(R.id.btn_del_prod);
        editProduct  = (ImageView)itemView.findViewById(R.id.btn_edit_prod);
        itemView.setOnClickListener(this);
    }

    // for search view : filter
    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(view,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener=ic;
    }

    // End -> for search view : filter
}
