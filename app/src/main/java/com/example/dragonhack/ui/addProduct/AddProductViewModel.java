package com.example.dragonhack.ui.addProduct;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dragonhack.TestRepository;
import com.example.dragonhack.database.entity.ProductDetails;

import java.util.List;

public class AddProductViewModel extends AndroidViewModel {

    private String productName;
    private TestRepository rep;
    private List<ProductDetails> allProducts;
    private String keywords;

    public AddProductViewModel(Application application) {
        super(application);
        rep = new TestRepository(application);
        allProducts = rep.getAllProducts();
        productName="";
        keywords = "";
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

    public void setAllProducts(List<ProductDetails> allProducts) {
        this.allProducts = allProducts;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public void insertRec(ProductDetails productDetails){
        rep.insertProduct(productDetails);
    }

    public void deleteRec(long id){
        rep.deleteProduct(id);
    }

    public String findRec(long id){
        return rep.getProduct(id);
    }
}