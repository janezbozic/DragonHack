package com.example.dragonhack.ui.allProducts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dragonhack.R;
import com.example.dragonhack.database.entity.ProductDetails;

import java.util.ArrayList;

public class AllProductsFragment extends Fragment {

    private AllProductsViewModel allProductsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        allProductsViewModel =
                new ViewModelProvider(getActivity()).get(AllProductsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_all_products, container, false);

        ListView listView = root.findViewById(R.id.productsListView);

        AllProductsAdapter adapter = new AllProductsAdapter(new ArrayList<ProductDetails>(allProductsViewModel.getAllProducts()), getContext());

        listView.setAdapter(adapter);

        return root;
    }
}