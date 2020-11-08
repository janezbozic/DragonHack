package com.example.dragonhack.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.dragonhack.models.dto.IngredientDTO;
import com.example.dragonhack.models.dto.IngredientsDTO;
import com.example.dragonhack.models.dto.ProductDTO;

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
    private Set<String> ingredients;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slideshowViewModel = new ViewModelProvider(getActivity()).get(SlideshowViewModel.class);
        ingredients=slideshowViewModel.getIngredients();
        if(slideshowViewModel.getIngredients()==null){
            ingredients= new HashSet<>();
            mRestClient.getAllIngredients().enqueue(new Callback<IngredientsDTO>() {
                @Override
                public void onResponse(Call<IngredientsDTO> call, Response<IngredientsDTO> response) {
                    ArrayList<IngredientDTO> ingredientsDto=response.body().getIngredients();
                    for(IngredientDTO ingredientDTO : ingredientsDto){
                        ingredients.add(ingredientDTO.getStrIngredient());
                    }
                    slideshowViewModel.setIngredients(ingredients);
                }

                @Override
                public void onFailure(Call<IngredientsDTO> call, Throwable t){
                    Toast.makeText(getActivity(), "Something went wrong while fetching the recipes. Problem connecting to server.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        List<String> keywords=slideshowViewModel.getKeywords();
        for(int i=0;i<keywords.size();i++){
            String[] splitString=keywords.get(i).split("[,]");
            for(int j=0;j<splitString.length;j++){
                if(ingredients.contains(splitString[j])){
                    System.out.println(splitString[j]);
                }
            }
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}