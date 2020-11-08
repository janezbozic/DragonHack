package com.example.dragonhack.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("label")
    @Expose
    private String recipeLabel;

    @SerializedName("url")
    @Expose
    private String recipeUrl;

    public String getRecipeLabel() {
        return recipeLabel;
    }

    public void setRecipeLabel(String recipeLabel) {
        this.recipeLabel = recipeLabel;
    }

    public String getRecipeUrl() {
        return recipeUrl;
    }

    public void setRecipeUrl(String recipeUrl) {
        this.recipeUrl = recipeUrl;
    }
}
