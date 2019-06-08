package de.mwdevs.padthai.ui.recipe_steps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mwdevs.padthai.R;

public class PadThaiSteps0Fragment extends RecipeStepsBaseFragment {

    public static PadThaiSteps0Fragment newInstance(int quantity) {
        PadThaiSteps0Fragment fragment = new PadThaiSteps0Fragment();
        addArguments(fragment, quantity, R.string.prepare_ingredients, R.string.soak_and_fry);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step3.g_noodles, R.string.g, R.mipmap.reisnudeln_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step1.el_tofu, R.string.el, R.mipmap.tofu_round);

        return root;
    }

}
