package com.example.dragonhack.api;

import com.example.dragonhack.models.dto.TestDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestApi {

    //ingredient lookup
    @GET("product/{barcodeId}")
    Call<TestDTO> getIngredientByBarcode(@Path("barcodeId") String content);



}
