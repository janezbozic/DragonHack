package com.example.dragonhack.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IngredientDTO {

    @SerializedName("hits")
    @Expose
    private ArrayList<Hit> hits;

    public ArrayList<Hit> getHits() {
        return hits;
    }

    public void setHits(ArrayList<Hit> hits) {
        this.hits = hits;
    }
}
