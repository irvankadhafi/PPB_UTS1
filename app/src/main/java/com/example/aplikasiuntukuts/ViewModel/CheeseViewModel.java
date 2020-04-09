package com.example.aplikasiuntukuts.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aplikasiuntukuts.data.Cheese;
import com.example.aplikasiuntukuts.data.CheeseRepository;

import java.util.List;

public class CheeseViewModel extends AndroidViewModel {

    private CheeseRepository repository;
    private LiveData<List<Cheese>> mAllCheese;

    public CheeseViewModel (Application application) {
        super(application);
        repository = new CheeseRepository (application);
        mAllCheese = repository.getAllCheeses ();
    }
    public void deleteCheese(Cheese cheese) {
        repository.deleteCheese(cheese);
    }
    public LiveData<List<Cheese>> getAllCheeses() { return mAllCheese; }
    public void insert(Cheese cheese) {
        repository.insert (cheese);
    }
}
