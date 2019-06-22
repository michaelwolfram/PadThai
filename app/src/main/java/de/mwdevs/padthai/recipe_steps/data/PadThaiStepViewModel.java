package de.mwdevs.padthai.recipe_steps.data;

import android.arch.core.util.Function;
import android.arch.lifecycle.Transformations;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.Arrays;

import de.mwdevs.padthai.R;

public class PadThaiStepViewModel extends BaseStepViewModel {
    @StringRes
    private static final ArrayList<Integer> TEXT_1 = new ArrayList<>(Arrays.asList(
            R.string.prepare_ingredients,
            R.string.put_in_the_wok,
            R.string.add,
            R.string.add,
            R.string.add_to_taste
    ));

    @StringRes
    private static final ArrayList<Integer> TEXT_2 = new ArrayList<>(Arrays.asList(
            R.string.soak_and_fry,
            R.string.low_heat,
            R.string.no_egg,
            R.string.mix_and_heat_up,
            R.string.empty_string
    ));

    private static final ArrayList<ArrayList<RecipeQuantityInfo>> RECIPE_QUANTITY_INFO_LIST = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(
                    new RecipeQuantityInfo(R.string.rice_noodles, RecipeQuantities.PadThai.Step3.g_noodles, R.string.g, R.mipmap.reisnudeln_round),
                    new RecipeQuantityInfo(R.string.tofu, RecipeQuantities.PadThai.Step1.el_tofu, R.string.el, R.mipmap.tofu_round)
            )),
            new ArrayList<>(Arrays.asList(
                    new RecipeQuantityInfo(R.string.oil, RecipeQuantities.PadThai.Step1.el_oil, R.string.el, R.mipmap.sojaoel_round),
                    new RecipeQuantityInfo(R.string.carrots, RecipeQuantities.PadThai.Step1.el_carrot, R.string.el, R.mipmap.karotten_round),
                    new RecipeQuantityInfo(R.string.onions, RecipeQuantities.PadThai.Step1.el_onion, R.string.el, R.mipmap.zwiebeln_round),
                    new RecipeQuantityInfo(R.string.tomatoes, RecipeQuantities.PadThai.Step1.el_tomato, R.string.el, R.mipmap.tomaten_round),
                    new RecipeQuantityInfo(R.string.tofu, RecipeQuantities.PadThai.Step1.el_tofu, R.string.el, R.mipmap.tofu_round),
                    new RecipeQuantityInfo(R.string.garlic, RecipeQuantities.PadThai.Step1.tl_garlic, R.string.tl, R.mipmap.knoblauch_round)
            )),
            new ArrayList<>(Arrays.asList(
                    new RecipeQuantityInfo(R.string.soja_sauce, RecipeQuantities.PadThai.Step2.el_soy_sauce, R.string.el, R.mipmap.sojasosse_round),
                    new RecipeQuantityInfo(R.string.chili_paste, RecipeQuantities.PadThai.Step2.tl_chili_paste, R.string.tl, R.mipmap.chili_paste_round)
            )),
            new ArrayList<>(Arrays.asList(
                    new RecipeQuantityInfo(R.string.water, RecipeQuantities.PadThai.Step3.el_water, R.string.el, R.mipmap.water_round),
                    new RecipeQuantityInfo(R.string.rice_noodles, RecipeQuantities.PadThai.Step3.g_noodles, R.string.g, R.mipmap.reisnudeln_round),
                    new RecipeQuantityInfo(R.string.soja_sauce, RecipeQuantities.PadThai.Step4.el_soy_sauce, R.string.el, R.mipmap.sojasosse_round),
                    new RecipeQuantityInfo(R.string.sugar, RecipeQuantities.PadThai.Step4.tl_sugar, R.string.tl, R.mipmap.braunerzucker_round),
                    new RecipeQuantityInfo(R.string.sprouts, RecipeQuantities.PadThai.Step4.el_sprouts, R.string.el, R.mipmap.mungobohnensprossen_round),
                    new RecipeQuantityInfo(R.string.onions, RecipeQuantities.PadThai.Step4.el_sprint_onions, R.string.el, R.mipmap.fruehlingszwiebeln_round),
                    new RecipeQuantityInfo(R.string.peanuts, RecipeQuantities.PadThai.Step4.el_peanuts, R.string.el, R.mipmap.erdnuesse_round)
            )),
            new ArrayList<>(Arrays.asList(
                    new RecipeQuantityInfo(R.string.chili_flakes, RecipeQuantities.PadThai.Step5.tl_chili_flakes, R.string.tl, R.mipmap.chili_flakes_round),
                    new RecipeQuantityInfo(R.string.lime_juice, RecipeQuantities.PadThai.Step5.stk_lime_juice, R.string.stk, R.mipmap.limetten_round)
            ))
    ));

    public PadThaiStepViewModel() {
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