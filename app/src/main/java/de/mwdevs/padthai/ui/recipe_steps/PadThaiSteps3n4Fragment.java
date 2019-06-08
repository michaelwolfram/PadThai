package de.mwdevs.padthai.ui.recipe_steps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mwdevs.padthai.R;

public class PadThaiSteps3n4Fragment extends RecipeStepsBaseFragment {

    public static PadThaiSteps3n4Fragment newInstance(int quantity) {
        PadThaiSteps3n4Fragment fragment = new PadThaiSteps3n4Fragment();
        addArguments(fragment, quantity, R.string.add, R.string.mix_and_heat_up);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step3.el_water, R.string.el, R.mipmap.water_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step3.g_noodles, R.string.g, R.mipmap.reisnudeln_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step4.el_soy_sauce, R.string.el, R.mipmap.sojasosse_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step4.tl_sugar, R.string.tl, R.mipmap.braunerzucker_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step4.el_sprouts, R.string.el, R.mipmap.mungobohnensprossen_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step4.el_sprint_onions, R.string.el, R.mipmap.fruehlingszwiebeln_round);
        addQuantityView(root, inflater, RecipeQuantities.PadThai.Step4.el_peanuts, R.string.el, R.mipmap.erdnuesse_round);

        return root;
    }

}
