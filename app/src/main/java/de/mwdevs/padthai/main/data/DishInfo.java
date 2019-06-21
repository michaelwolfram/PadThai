package de.mwdevs.padthai.main.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import de.mwdevs.padthai.R;
import de.mwdevs.padthai.recipe_steps.data.ChiliPasteStepViewModel;
import de.mwdevs.padthai.recipe_steps.data.PadThaiStepViewModel;
import de.mwdevs.padthai.recipe_steps.data.PeanutSauceStepViewModel;

public enum DishInfo {

    PAD_THAI(0, R.string.pad_thai, R.drawable.pad_thai_512, new ArrayList<>(Arrays.asList(
            new DishComponentInfo(R.string.chili_paste, ChiliPasteStepViewModel.class),
            new DishComponentInfo(R.string.sosse, PeanutSauceStepViewModel.class),
            new DishComponentInfo(R.string.pad_thai, PadThaiStepViewModel.class)
    ))),
    GREEN_PAPAYA_SALAD(1, R.string.green_papaya_salad, R.drawable.green_papaya_salad_512, new ArrayList<>(Arrays.asList(
            new DishComponentInfo(R.string.chili_paste, ChiliPasteStepViewModel.class),
            new DishComponentInfo(R.string.sosse, PeanutSauceStepViewModel.class)
    ))),
    MASSAMAN_CURRY(2, R.string.massaman_curry, R.drawable.massaman_curry_512, new ArrayList<>(Collections.singletonList(
            new DishComponentInfo(R.string.chili_paste, ChiliPasteStepViewModel.class)
    ))),
    GREEN_THAI_CURRY(3, R.string.green_thai_curry, R.drawable.green_thai_curry_512, new ArrayList<>(Arrays.asList(
            new DishComponentInfo(R.string.chili_paste, ChiliPasteStepViewModel.class),
            new DishComponentInfo(R.string.sosse, PeanutSauceStepViewModel.class)
    ))),
    TOM_YAM_SOUP(4, R.string.tom_yam_soup, R.drawable.tom_yam_soup_512, new ArrayList<>(Arrays.asList(
            new DishComponentInfo(R.string.chili_paste, ChiliPasteStepViewModel.class),
            new DishComponentInfo(R.string.sosse, PeanutSauceStepViewModel.class),
            new DishComponentInfo(R.string.pad_thai, PadThaiStepViewModel.class)
    ))),
    SPRING_ROLLS(5, R.string.spring_roles, R.drawable.spring_roles_512, new ArrayList<>(Arrays.asList(
            new DishComponentInfo(R.string.chili_paste, ChiliPasteStepViewModel.class),
            new DishComponentInfo(R.string.sosse, PeanutSauceStepViewModel.class)
    ))),
    PUMPKIN_HUMUS(6, R.string.pumpkin_humus, R.drawable.pumpkin_humus_512, new ArrayList<>(Collections.singletonList(
            new DishComponentInfo(R.string.chili_paste, ChiliPasteStepViewModel.class)
    ))),
    MANGO_WITH_STICKY_RICE(7, R.string.mango_sticky_rice, R.drawable.mango_sticky_rice_512, new ArrayList<>(Arrays.asList(
            new DishComponentInfo(R.string.chili_paste, ChiliPasteStepViewModel.class),
            new DishComponentInfo(R.string.sosse, PeanutSauceStepViewModel.class)
    )));

    private int mId;
    private int mTitleResId;
    private int mImageResId;
    private ArrayList<DishComponentInfo> mDishComponentInfos;

    DishInfo(int id, int titleResId, int imageResId, ArrayList<DishComponentInfo> components) {
        mId = id;
        mTitleResId = titleResId;
        mImageResId = imageResId;
        mDishComponentInfos = components;
    }

    public int getId() {
        return mId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getImageResId() {
        return mImageResId;
    }

    public DishComponentInfo getDishComponentInfo(int row) {
        return mDishComponentInfos.get(row);
    }

    public int getNumDishComponents() {
        return mDishComponentInfos.size();
    }
}
