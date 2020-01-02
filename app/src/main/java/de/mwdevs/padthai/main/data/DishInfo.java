package de.mwdevs.padthai.main.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import de.mwdevs.padthai.R;

public enum DishInfo {

    PAD_THAI(0, R.string.pad_thai, R.drawable.pad_thai_512, 0,
            new ArrayList<>(Arrays.asList(
                    new DishComponentInfo(R.string.chili_paste, "chili_paste.json"),
                    new DishComponentInfo(R.string.peanut_sauce, "peanut_sauce.json"),
                    new DishComponentInfo(R.string.pad_thai, "pad_thai.json")
            ))),
    GREEN_PAPAYA_SALAD(1, R.string.green_papaya_salad, R.drawable.green_papaya_salad_512, 3,
            new ArrayList<>(Collections.singletonList(
                    new DishComponentInfo(R.string.green_papaya_salad, "green_papaya_salad.json")
            ))),
    MASSAMAN_CURRY(2, R.string.massaman_curry, R.drawable.massaman_curry_512, 4,
            new ArrayList<>(Collections.singletonList(
                    new DishComponentInfo(R.string.massaman_curry, "massaman_curry.json")
            ))),
    GREEN_THAI_CURRY(3, R.string.green_thai_curry, R.drawable.green_thai_curry_512, 5,
            new ArrayList<>(Collections.singletonList(
                    new DishComponentInfo(R.string.green_thai_curry, "green_thai_curry.json")
            ))),
    TOM_YAM_SOUP(4, R.string.tom_yam_soup, R.drawable.tom_yam_soup_512, 6,
            new ArrayList<>(Collections.singletonList(
                    new DishComponentInfo(R.string.tom_yam_soup, "tom_yam_soup.json")
            ))),
    SPRING_ROLLS(5, R.string.spring_rolls, R.drawable.spring_rolls_512, 7,
            new ArrayList<>(Arrays.asList(
                    new DishComponentInfo(R.string.peanut_sauce, "peanut_sauce.json"),
                    new DishComponentInfo(R.string.spring_rolls, "spring_rolls.json")
            ))),
    PUMPKIN_HUMUS(6, R.string.pumpkin_humus, R.drawable.pumpkin_humus_512, 9,
            new ArrayList<>(Collections.singletonList(
                    new DishComponentInfo(R.string.pumpkin_humus, "pumpkin_humus.json")
            ))),
    MANGO_WITH_STICKY_RICE(7, R.string.mango_with_sticky_rice, R.drawable.mango_with_sticky_rice_512, 10,
            new ArrayList<>(Arrays.asList(
                    new DishComponentInfo(R.string.sticky_rice, "sticky_rice.json"),
                    new DishComponentInfo(R.string.mango_with_sticky_rice, "mango_with_sticky_rice.json")
            )));

    private int mId;
    private int mTitleResId;
    private int mImageResId;
    private int mDishComponentOffset;
    private ArrayList<DishComponentInfo> mDishComponentInfos;

    DishInfo(int id, int titleResId, int imageResId, int dishComponentOffset, ArrayList<DishComponentInfo> components) {
        mId = id;
        mTitleResId = titleResId;
        mImageResId = imageResId;
        mDishComponentOffset = dishComponentOffset;
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

    public int getDishComponentOffset() {
        return mDishComponentOffset;
    }

    public DishComponentInfo getDishComponentInfo(int row) {
        return mDishComponentInfos.get(row);
    }

    public String[] getDishComponentJsonFilenames() {
        String[] json_filenames = new String[getNumDishComponents()];
        for (int i = 0; i < getNumDishComponents(); i++) {
            json_filenames[i] = mDishComponentInfos.get(i).json_filename;
        }
        return json_filenames;
    }

    public int getNumDishComponents() {
        return mDishComponentInfos.size();
    }
}
