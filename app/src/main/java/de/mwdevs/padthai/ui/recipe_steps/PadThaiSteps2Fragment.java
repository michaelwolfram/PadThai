package de.mwdevs.padthai.ui.recipe_steps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mwdevs.padthai.R;

public class PadThaiSteps2Fragment extends RecipeStepsBaseFragment {

    public static PadThaiSteps2Fragment newInstance(int quantity) {
        PadThaiSteps2Fragment fragment = new PadThaiSteps2Fragment();
        addArguments(fragment, quantity, R.string.add, R.string.no_egg);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step2.el_soy_sauce, R.string.el, R.mipmap.sojasosse_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step2.tl_chili_paste, R.string.tl, R.mipmap.chili_paste_round);

        return root;
    }

}
