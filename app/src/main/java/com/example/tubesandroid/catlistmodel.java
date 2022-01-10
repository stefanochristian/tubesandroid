package com.example.tubesandroid;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class catlistmodel {

    private MutableLiveData<List<catmodel>> ccat = new MutableLiveData<>();


    public catlistmodel() {

    }

    public LiveData<List<catmodel>> getcat(){
        return ccat;
    }

    public void searchcatapi(String query, int pagenumber) {
    }


}
