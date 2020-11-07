package com.example.dragonhack.api;

import com.example.dragonhack.models.dto.ProductDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestApi {

    //ingredient lookup
    @GET("product/{barcodeId}")
    Call<ProductDTO> getIngredientByBarcode(@Path("barcodeId") String content);



}
