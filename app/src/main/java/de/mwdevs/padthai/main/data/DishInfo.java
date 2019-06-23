package de.mwdevs.padthai.main.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import de.mwdevs.padthai.R;

public enum DishInfo {

    // TODO: 23.06.19 put these also in json file
    PAD_THAI(0, R.string.pad_thai, R.drawable.pad_thai_512, new ArrayList<>(Arrays.asList(
            new DishComponentInfo(R.string.chili_paste, "chili_paste.json"),
            new DishComponentInfo(R.string.peanut_sauce, "peanut_sauce.json"),
            new DishComponentInfo(R.string.pad_thai, "pad_thai.json")
    ))),
    GREEN_PAPAYA_SALAD(1, R.string.green_papaya_salad, R.drawable.green_papaya_salad_512, new ArrayList<>(Arrays.asList(
            new DishComponentInfo(R.string.chili_paste, "chili_paste.json"),
            new DishComponentInfo(R.string.peanut_sauce, "peanut_sauce.json")
    ))),
    MASSAMAN_CURRY(2, R.string.massaman_curry, R.drawable.massaman_curry_512, new ArrayList<>(Collections.singletonList(
            new DishComponentInfo(R.string.chili_paste, "chili_paste.json")
    ))),
    GREEN_THAI_CURRY(3, R.string.green_thai_curry, R.drawable.green_thai_curry_512, new ArrayList<>(Arrays.asList(
            new DishComponentInfo(R.string.chili_paste, "chili_paste.json"),
            new DishComponentInfo(R.string.peanut_sauce, "peanut_sauce.json")
    ))),
    TOM_YAM_SOUP(4, R.string.tom_yam_soup, R.drawable.tom_yam_soup_512, new ArrayList<>(Arrays.asList(
            new DishComponentInfo(R.string.chili_paste, "chili_paste.json"),
            new DishComponentInfo(R.string.peanut_sauce, "peanut_sauce.json"),
            new DishComponentInfo(R.string.pad_thai, "pad_thai.json")
    ))),
    SPRING_ROLLS(5, R.string.spring_roles, R.drawable.spring_roles_512, new ArrayList<>(Arrays.asList(
            new DishComponentInfo(R.string.chili_paste, "chili_paste.json"),
            new DishComponentInfo(R.string.peanut_sauce, "peanut_sauce.json")
    ))),
    PUMPKIN_HUMUS(6, R.string.pumpkin_humus, R.drawable.pumpkin_humus_512, new ArrayList<>(Collections.singletonList(
            new DishComponentInfo(R.string.chili_paste, "chili_paste.json")
    ))),
    MANGO_WITH_STICKY_RICE(7, R.string.mango_sticky_rice, R.drawable.mango_sticky_rice_512, new ArrayList<>(Arrays.asList(
            new DishComponentInfo(R.string.chili_paste, "chili_paste.json"),
            new DishComponentInfo(R.string.peanut_sauce, "peanut_sauce.json")
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
