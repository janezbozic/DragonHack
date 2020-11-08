package com.example.dragonhack.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IngredientDTO {

    @SerializedName("idIngredient")
    @Expose
    private String idIngredient;
    @SerializedName("strIngredient")
    @Expose
    private String strIngredient;
    @SerializedName("strDescription")
    @Expose
    private String strDescription;

    public String getIdIngredient() {
        return idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }
}
