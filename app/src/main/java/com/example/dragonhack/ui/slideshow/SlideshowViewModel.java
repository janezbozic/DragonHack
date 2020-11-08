package com.example.dragonhack.ui.slideshow;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dragonhack.TestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SlideshowViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private TestRepository rep;
    private List<String> keywords;


    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
    }

    private Set<String> ingredients;


    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public SlideshowViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
        rep=new TestRepository(application);
        keywords=rep.getKeywords();
    }

    public LiveData<String> getText() {
        return mText;
    }
}