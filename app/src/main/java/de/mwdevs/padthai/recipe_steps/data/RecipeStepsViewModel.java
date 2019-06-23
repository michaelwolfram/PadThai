package de.mwdevs.padthai.recipe_steps.data;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.mwdevs.padthai.Utils;

public class RecipeStepsViewModel extends AndroidViewModel {
    private final Recipe mRecipe;
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<Integer> mText1;
    private LiveData<Integer> mText2;
    private LiveData<ArrayList<RecipeQuantityInfo>> mRecipeQuantityInfo;

    RecipeStepsViewModel(@NonNull Application application, String recipe_filename) {
        super(application);
        mRecipe = Utils.loadRecipeFromJSON(application.getBaseContext(), recipe_filename);
        init();
    }

    private void init() {
        mText1 = Transformations.map(mIndex, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                return mRecipe.getText1().get(input);
            }
        });
        mText2 = Transformations.map(mIndex, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                return mRecipe.getText2().get(input);
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

    public static class Factory implements ViewModelProvider.Factory {
        private Application mApplication;
        private String mRecipeFilename;


        public Factory(Application application, String recipe_filename) {
            mApplication = application;
            mRecipeFilename = recipe_filename;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            @SuppressWarnings("unchecked") T viewModel = (T) new RecipeStepsViewModel(mApplication, mRecipeFilename);
            return viewModel;
        }
    }
}