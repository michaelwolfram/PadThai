package de.mwdevs.padthai.ui.recipe_steps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mwdevs.padthai.R;

public class PadThaiSteps5Fragment extends RecipeStepsBaseFragment {

    public static PadThaiSteps5Fragment newInstance(int quantity) {
        PadThaiSteps5Fragment fragment = new PadThaiSteps5Fragment();
        addArguments(fragment, quantity, R.string.add_to_taste, R.string.empty_string);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step5.tl_chili_flakes, R.string.tl, R.mipmap.chili_flakes_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step5.stk_lime_juice, R.string.stk, R.mipmap.limetten_round);

        return root;
    }

}
