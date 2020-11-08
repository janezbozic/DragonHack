package com.example.dragonhack.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductDetailsDTO {

    public String getProduct_name() {

        return product_name.replace("&quot;", "\"");
    }

    @SerializedName("product_name")
    @Expose
    private String product_name;

    public ArrayList<String> get_keywords() {
        return _keywords;
    }

    @SerializedName("_keywords")
    @Expose
    private ArrayList<String> _keywords;
}
