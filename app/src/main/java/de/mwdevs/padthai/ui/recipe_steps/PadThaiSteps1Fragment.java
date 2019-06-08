package de.mwdevs.padthai.ui.recipe_steps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mwdevs.padthai.R;

public class PadThaiSteps1Fragment extends RecipeStepsBaseFragment {

    public static PadThaiSteps1Fragment newInstance(int quantity) {
        PadThaiSteps1Fragment fragment = new PadThaiSteps1Fragment();
        addArguments(fragment, quantity, R.string.put_in_the_wok, R.string.heat_low);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step1.el_oil, R.string.el, R.mipmap.sojaoel_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step1.el_carrot, R.string.el, R.mipmap.karotten_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step1.el_onion, R.string.el, R.mipmap.zwiebeln_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step1.el_tomato, R.string.el, R.mipmap.tomaten_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step1.el_tofu, R.string.el, R.mipmap.sojaoel_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step1.tl_garlic, R.string.tl, R.mipmap.knoblauch_round);

        return root;
    }

}
