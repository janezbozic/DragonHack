package com.example.dragonhack;

//repository for handling data from database

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dragonhack.database.Database;
import com.example.dragonhack.database.dao.SestavinaDao;
import com.example.dragonhack.database.entity.ProductDetails;

import java.util.ArrayList;
import java.util.List;

public class TestRepository {

    private SestavinaDao sestavinaDao;
    private List<ProductDetails> allProducts;

    public TestRepository (Application application){
        Database db = Database.getDatabase(application);
        sestavinaDao = db.recipeDao();
        allProducts = sestavinaDao.getAllProducts();
    }

    public void insertProduct(final ProductDetails newProduct) {
        Database.dbWriteExec.execute(new Runnable() {
            @Override
            public void run() {
                sestavinaDao.insertProduct(newProduct);
            }
        });
    }

    public void deleteProduct(final Long id) {
        Database.dbWriteExec.execute(new Runnable() {
            @Override
            public void run() {
                sestavinaDao.deleteProduct(id);
            }
        });
    }

    public List<ProductDetails> getAllProducts() {
        return allProducts;
    }

    public String getProduct(Long id){
        return sestavinaDao.getProduct(id);
    }

    public List<String> getKeywords(){
        return sestavinaDao.getKeywords();
    }

    /*public void findProduct(final long id) {

        Database.dbWriteExec.execute(new Runnable() {
            @Override
            public void run() {
                searchedRec.postValue(recipeDao.getRecepiById(id+""));
            }
        });
    }*/
}
