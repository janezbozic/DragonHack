package com.example.dragonhack.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetailsDTO {

    public String getProduct_name() {

        return product_name.replace("&quot;", "\"");
    }

    @SerializedName("product_name")
    @Expose
    private String product_name;
}
