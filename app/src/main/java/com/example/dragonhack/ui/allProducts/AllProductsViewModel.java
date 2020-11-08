package com.example.dragonhack.ui.allProducts;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dragonhack.TestRepository;
import com.example.dragonhack.database.entity.ProductDetails;

import java.util.ArrayList;
import java.util.List;

public class AllProductsViewModel extends AndroidViewModel {

    private TestRepository rep;
    private List<ProductDetails> allProducts;

    public AllProductsViewModel(Application application) {
        super(application);
        rep = new TestRepository(application);
        allProducts = rep.getAllProducts();
    }

    public TestRepository getRep() {
        return rep;
    }

    public void setRep(TestRepository rep) {
        this.rep = rep;
    }

    public List<ProductDetails> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(ArrayList<ProductDetails> allProducts) {
        this.allProducts = allProducts;
    }
}