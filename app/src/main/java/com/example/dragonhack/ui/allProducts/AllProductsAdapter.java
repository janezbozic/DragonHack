package com.example.dragonhack.ui.allProducts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dragonhack.R;
import com.example.dragonhack.database.entity.ProductDetails;

import java.util.ArrayList;

public class AllProductsAdapter extends ArrayAdapter<ProductDetails>{

    private ArrayList<ProductDetails> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
    }

    public AllProductsAdapter(ArrayList<ProductDetails> data, Context context) {
        super(context, R.layout.product_in_list, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ProductDetails dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.product_in_list, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.itemInList);

            viewHolder.txtName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), dataModel.getProduct_name(), Toast.LENGTH_SHORT).show();
                }
            });

            String [] arr = dataModel.getExpDate().split("[-]");
            String date = arr[2] + "." + arr[1] + "." + arr[0];

            viewHolder.txtName.setText(dataModel.getProduct_name() + ": " + date);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        // Return the completed view to render on screen
        return convertView;
    }
}