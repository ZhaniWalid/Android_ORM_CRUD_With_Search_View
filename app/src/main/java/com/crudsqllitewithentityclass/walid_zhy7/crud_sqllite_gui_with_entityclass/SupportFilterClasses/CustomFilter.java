package com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.SupportFilterClasses;


/**
 * Created by HP on 22/02/2018.
 */
// this class used for search view : filter

import android.widget.Filter;

import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.entities.Product;
import com.crudsqllitewithentityclass.walid_zhy7.crud_sqllite_gui_with_entityclass.supportClasses.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomFilter extends Filter {

    MyFilterProductAdapter adapter;
    List<Product> filterList;

    public CustomFilter(List<Product> filterList,MyFilterProductAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            List<Product> filteredProducts=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredProducts.add(filterList.get(i));
                }
            }

            results.count=filteredProducts.size();
            results.values=filteredProducts;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.listProducts = (List<Product>) filterResults.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
