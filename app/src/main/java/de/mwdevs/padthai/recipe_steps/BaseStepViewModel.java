package de.mwdevs.padthai.recipe_steps;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public abstract class BaseStepViewModel extends ViewModel {
    MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    LiveData<Integer> mText1;
    LiveData<Integer> mText2;
    LiveData<ArrayList<RecipeQuantityInfo>> mRecipeQuantityInfo;

    void setIndex(int index) {
        mIndex.setValue(index);
    }

    LiveData<Integer> getText1() {
        return mText1;
    }

    LiveData<Integer> getText2() {
        return mText2;
    }

    LiveData<ArrayList<RecipeQuantityInfo>> getRecipeQuantityInfo() {
        return mRecipeQuantityInfo;
    }
}