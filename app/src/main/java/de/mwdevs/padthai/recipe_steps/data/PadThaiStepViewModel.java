package de.mwdevs.padthai.recipe_steps.data;

import android.app.Application;
import android.support.annotation.NonNull;

import de.mwdevs.padthai.Utils;

public class PadThaiStepViewModel extends BaseStepViewModel {

    public PadThaiStepViewModel(@NonNull Application application) {
        super(application);

        Recipe recipe = Utils.loadRecipeFromJSON(application.getBaseContext(), "pad_thai.json");
        init(recipe);
    }
}








