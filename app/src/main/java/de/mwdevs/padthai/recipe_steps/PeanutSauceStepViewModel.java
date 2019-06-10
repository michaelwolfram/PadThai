package de.mwdevs.padthai.recipe_steps;

import android.arch.core.util.Function;
import android.arch.lifecycle.Transformations;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import de.mwdevs.padthai.R;

public class PeanutSauceStepViewModel extends BaseStepViewModel {
    @StringRes
    private static final ArrayList<Integer> TEXT_1 = new ArrayList<>(Arrays.asList(
            R.string.put_in_the_wok,
            R.string.reduce_heat_then_add,
            R.string.add,
            R.string.add
    ));

    @StringRes
    private static final ArrayList<Integer> TEXT_2 = new ArrayList<>(Arrays.asList(
            R.string.medium_heat_and_crush_tomatoes,
            R.string.fry_and_water,
            R.string.medium_heat_dont_cook_too_long,
            R.string.cook_until_thick
    ));

    private static final ArrayList<ArrayList<RecipeQuantityInfo>> RECIPE_QUANTITY_INFO_LIST = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(
                    new RecipeQuantityInfo(RecipeQuantities.PeanutSauce.Step1.el_oil, R.string.el, R.mipmap.sojaoel_round),
                    new RecipeQuantityInfo(RecipeQuantities.PeanutSauce.Step1.stk_tomato, R.string.stk, R.mipmap.tomaten_round)
            )),
            new ArrayList<>(Arrays.asList(
                    new RecipeQuantityInfo(RecipeQuantities.PeanutSauce.Step2.tl_chili_paste, R.string.tl, R.mipmap.chili_paste_round),
                    new RecipeQuantityInfo(RecipeQuantities.PeanutSauce.Step2.el_peanuts, R.string.el, R.mipmap.erdnuesse_round)
            )),
            new ArrayList<>(Collections.singletonList(
                    new RecipeQuantityInfo(RecipeQuantities.PeanutSauce.Step3.el_coconut_milk, R.string.el, R.mipmap.kokosmilch_round)
            )),
            new ArrayList<>(Arrays.asList(
                    new RecipeQuantityInfo(RecipeQuantities.PeanutSauce.Step4.tl_sugar, R.string.tl, R.mipmap.braunerzucker_round),
                    new RecipeQuantityInfo(RecipeQuantities.PeanutSauce.Step4.tl_lime_juice, R.string.tl, R.mipmap.limetten_round),
                    new RecipeQuantityInfo(RecipeQuantities.PeanutSauce.Step4.el_soy_sauce, R.string.el, R.mipmap.sojasosse_round)
            ))
    ));

    public PeanutSauceStepViewModel() {
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