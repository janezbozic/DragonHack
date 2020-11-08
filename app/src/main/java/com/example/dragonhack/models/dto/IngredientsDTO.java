package com.example.dragonhack.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class IngredientsDTO {

    @SerializedName("meals")
    @Expose
    private IngredientDTO ingredients;

    public IngredientDTO getIngredients() {
        return ingredients;
    }
}
