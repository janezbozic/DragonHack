package com.example.dragonhack.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dragonhack.database.entity.ProductDetails;

import java.util.List;

@Dao
public interface SestavinaDao {

    @Query("SELECT product_name FROM ProductDetails where id = :id")
    String getProduct(Long id);

    @Query("Select * FROM ProductDetails")
    List<ProductDetails> getAllProducts();

    @Insert
    void insertProduct(ProductDetails productDetails);

    @Query("Delete FROM ProductDetails where id = :id")
    void deleteProduct(Long id);
}
