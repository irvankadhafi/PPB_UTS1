package com.example.aplikasiuntukuts.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CheeseRepository {
    private CheeseDao mCheeseDao;
    private LiveData<List<Cheese>> mAllCheese;

    public CheeseRepository(Application application) {
        CheeseDatabase db = CheeseDatabase.getDatabase (application);
        mCheeseDao = db.cheese ();
        mAllCheese = mCheeseDao.getAlphabetizedCheeses();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Cheese>> getAllCheeses() {
        return mAllCheese;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Cheese cheese) {
        CheeseDatabase.databaseWriteExecutor.execute(() -> {
            mCheeseDao.insert(cheese);
        });
    }

    private static class deleteCheeseAsyncTask extends AsyncTask<Cheese, Void, Void> {
        private CheeseDao mAsyncTaskDao;

        deleteCheeseAsyncTask(CheeseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cheese... params) {
            mAsyncTaskDao.deleteCheese (params[0]);
            return null;
        }
    }

    public void deleteCheese(Cheese cheese)  {
        new deleteCheeseAsyncTask(mCheeseDao).execute(cheese);
    }
}


