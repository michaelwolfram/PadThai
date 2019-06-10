package de.mwdevs.padthai.recipe_steps;

import android.arch.core.util.Function;
import android.arch.lifecycle.Transformations;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import de.mwdevs.padthai.R;

public class ChiliPasteStepViewModel extends BaseStepViewModel {
    @StringRes
    private static final ArrayList<Integer> TEXT_1 = new ArrayList<>(Arrays.asList(
            R.string.put_in_the_wok,
            R.string.add,
            R.string.add,
            R.string.put_everything_in_the_mixer
    ));

    @StringRes
    private static final ArrayList<Integer> TEXT_2 = new ArrayList<>(Arrays.asList(
            R.string.cook_until_brown,
            R.string.empty_string,
            R.string.empty_string,
            R.string.empty_string
    ));

    private static final ArrayList<ArrayList<RecipeQuantityInfo>> RECIPE_QUANTITY_INFO_LIST = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(
                    new RecipeQuantityInfo(RecipeQuantities.ChiliPaste.Step1.el_carrot, R.string.el, R.mipmap.karotten_round),
                    new RecipeQuantityInfo(RecipeQuantities.ChiliPaste.Step1.el_onion, R.string.el, R.mipmap.zwiebeln_round),
                    new RecipeQuantityInfo(RecipeQuantities.ChiliPaste.Step1.el_garlic, R.string.el, R.mipmap.knoblauch_round),
                    new RecipeQuantityInfo(RecipeQuantities.ChiliPaste.Step1.el_oil, R.string.el, R.mipmap.sojaoel_round)
            )),
            new ArrayList<>(Collections.singletonList(
                    new RecipeQuantityInfo(RecipeQuantities.ChiliPaste.Step2.el_chili_schoten, R.string.el, R.mipmap.chilischoten_round)
            )),
            new ArrayList<>(Arrays.asList(
                    new RecipeQuantityInfo(RecipeQuantities.ChiliPaste.Step3.el_soy_sauce, R.string.el, R.mipmap.sojasosse_round),
                    new RecipeQuantityInfo(RecipeQuantities.ChiliPaste.Step3.tl_sugar, R.string.tl, R.mipmap.braunerzucker_round),
                    new RecipeQuantityInfo(RecipeQuantities.ChiliPaste.Step3.tl_lime_juice, R.string.tl, R.mipmap.limetten_round)
            )),
            new ArrayList<RecipeQuantityInfo>()
    ));

    ChiliPasteStepViewModel() {
        mText1 = Transformations.map(mIndex, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                return TEXT_1.get(input);
            }
        });
        mText2 = Transformations.map(mIndex, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                return TEXT_2.get(input);
            }
        });
        mRecipeQuantityInfo = Transformations.map(mIndex, new Function<Integer, ArrayList<RecipeQuantityInfo>>() {
            @Override
            public ArrayList<RecipeQuantityInfo> apply(Integer input) {
                return RECIPE_QUANTITY_INFO_LIST.get(input);
            }
        });
    }
}