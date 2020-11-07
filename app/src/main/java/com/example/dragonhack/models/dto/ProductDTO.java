package com.example.dragonhack.models.dto;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProductDTO {


    @SerializedName("product")
    @Expose
    private ProductDetailsDTO product;

    public String getStatus_verbose() {
        return status_verbose;
    }

    @SerializedName("status_verbose")
    @Expose
    private String status_verbose;

    public ProductDetailsDTO getProduct() {
        return product;
    }

}
