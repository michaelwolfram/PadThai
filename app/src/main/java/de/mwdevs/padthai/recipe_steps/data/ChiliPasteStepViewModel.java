package de.mwdevs.padthai.recipe_steps.data;

import android.app.Application;
import android.support.annotation.NonNull;

import de.mwdevs.padthai.Utils;

public class ChiliPasteStepViewModel extends RecipeStepsViewModel {

    public ChiliPasteStepViewModel(@NonNull Application application) {
        super(application);

        Recipe recipe = Utils.loadRecipeFromJSON(application.getBaseContext(), "chili_paste.json");
        init(recipe);
    }
}
