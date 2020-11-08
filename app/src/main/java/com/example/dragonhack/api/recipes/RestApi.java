package com.example.dragonhack.api.recipes;

import com.example.dragonhack.models.dto.IngredientDTO;
import com.example.dragonhack.models.dto.IngredientsDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET("search?app_id=de639463&app_key=5a17ad91ff7073547b66f63a8ad0f7f7&")
    Call<IngredientDTO> getAllIngredients(@Query("q") String ingredient);
}
