package de.mwdevs.padthai.recipe_steps.data;

import android.app.Application;
import android.support.annotation.NonNull;

import de.mwdevs.padthai.Utils;

public class PeanutSauceStepViewModel extends BaseStepViewModel {

    public PeanutSauceStepViewModel(@NonNull Application application) {
        super(application);

        Recipe recipe = Utils.loadRecipeFromJSON(application.getBaseContext(), "peanut_sauce.json");
        init(recipe);
    }
}