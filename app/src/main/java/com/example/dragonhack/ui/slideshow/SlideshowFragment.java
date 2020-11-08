package com.example.dragonhack.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dragonhack.R;
import com.example.dragonhack.api.recipes.RestApi;
import com.example.dragonhack.api.recipes.ServiceGenerator;
import com.example.dragonhack.database.entity.ProductDetails;
import com.example.dragonhack.models.dto.Hit;
import com.example.dragonhack.models.dto.IngredientDTO;
import com.example.dragonhack.models.dto.IngredientsDTO;
import com.example.dragonhack.models.dto.ProductDTO;
import com.example.dragonhack.ui.allProducts.AllProductsAdapter;
import com.example.dragonhack.ui.allProducts.AllProductsViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private RestApi mRestClient=new ServiceGenerator().createService(com.example.dragonhack.api.recipes.RestApi.class);
    private AllProductsViewModel allProductsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slideshowViewModel = new ViewModelProvider(getActivity()).get(SlideshowViewModel.class);
        allProductsViewModel = new ViewModelProvider(getActivity()).get(AllProductsViewModel.class);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        ListView listView = root.findViewById(R.id.slideshowListView);

        String [] keyW = allProductsViewModel.getAllProducts().get(0).getKeywords().split("[,]");

        String f = keyW[0];

        for (int i = 1; i<keyW.length; i++){
            if (keyW[i].length() > f.length())
                f = keyW[i];
        }
        mRestClient.getAllIngredients(f).enqueue(new Callback<IngredientDTO>() {
            @Override
            public void onResponse(Call<IngredientDTO> call, Response<IngredientDTO> response) {
                ArrayList<Hit> hits = response.body().getHits();
                SlideShowAdapter adapter = new SlideShowAdapter(new ArrayList<Hit>(hits), getContext());
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<IngredientDTO> call, Throwable t){
                Toast.makeText(getActivity(), "Something went wrong while fetching the recipes. Problem connecting to server.", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}