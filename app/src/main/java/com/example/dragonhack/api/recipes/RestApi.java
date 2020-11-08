package com.example.dragonhack.api.recipes;

import com.example.dragonhack.models.dto.IngredientsDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApi {

    @GET("list.php?i=list")
    Call<IngredientsDTO> getAllIngredients();
}
