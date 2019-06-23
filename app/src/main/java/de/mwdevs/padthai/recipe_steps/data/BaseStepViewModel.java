package de.mwdevs.padthai.recipe_steps.data;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public abstract class BaseStepViewModel extends AndroidViewModel {
    private Recipe mRecipe;
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<Integer> mText1;
    private LiveData<Integer> mText2;
    private LiveData<ArrayList<RecipeQuantityInfo>> mRecipeQuantityInfo;

    BaseStepViewModel(@NonNull Application application) {
        super(application);
    }

    void init(Recipe recipe) {
        mRecipe = recipe;

        mText1 = Transformations.map(mIndex, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                return mRecipe.getText_1().get(input);
            }
        });
        mText2 = Transformations.map(mIndex, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                return mRecipe.getText_2().get(input);
            }
        });
        mRecipeQuantityInfo = Transformations.map(mIndex, new Function<Integer, ArrayList<RecipeQuantityInfo>>() {
            @Override
            public ArrayList<RecipeQuantityInfo> apply(Integer input) {
                return mRecipe.getSteps().get(input);
            }
        });
    }

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