package com.example.dragonhack.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dragonhack.R;
import com.example.dragonhack.database.entity.ProductDetails;
import com.example.dragonhack.models.dto.Hit;

import java.util.ArrayList;

public class SlideShowAdapter extends ArrayAdapter<Hit>{

    private ArrayList<Hit> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
    }

    public SlideShowAdapter(ArrayList<Hit> data, Context context) {
        super(context, R.layout.product_in_list, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Hit dataModel = getItem(position);
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
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.getRecipe().getRecipeUrl()));
                    getContext().startActivity(browserIntent);
                }
            });

            viewHolder.txtName.setText(dataModel.getRecipe().getRecipeLabel());

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