package de.mwdevs.padthai.recipe_steps.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public abstract class BaseStepViewModel extends ViewModel {
    MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    LiveData<Integer> mText1;
    LiveData<Integer> mText2;
    LiveData<ArrayList<RecipeQuantityInfo>> mRecipeQuantityInfo;

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<Integer> getText1() {
        return mText1;
    }

    public LiveData<Integer> getText2() {
        return mText2;
    }

    public LiveData<ArrayList<RecipeQuantityInfo>> getRecipeQuantityInfo() {
        return mRecipeQuantityInfo;
    }
}