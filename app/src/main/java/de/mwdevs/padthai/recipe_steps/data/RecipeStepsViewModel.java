package de.mwdevs.padthai.recipe_steps.data;

import android.app.Application;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import java.util.ArrayList;

import de.mwdevs.padthai.Utils;

public class RecipeStepsViewModel extends AndroidViewModel {
    private final Recipe mRecipe;
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<Integer> mText1;
    private LiveData<Integer> mText2;
    private LiveData<ArrayList<RecipeQuantityInfo>> mRecipeQuantityInfo;

    private RecipeStepsViewModel(@NonNull Application application, String recipe_filename) {
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