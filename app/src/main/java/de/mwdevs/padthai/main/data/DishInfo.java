package de.mwdevs.padthai.main.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import de.mwdevs.padthai.R;

public enum DishInfo {

    PAD_THAI(0, R.string.pad_thai, R.drawable.pad_thai_512,
            new ArrayList<>(Arrays.asList(
                    new DishComponentInfo(R.string.chili_paste, "chili_paste.json", 0),
                    new DishComponentInfo(R.string.peanut_sauce, "peanut_sauce.json", 1),
                    new DishComponentInfo(R.string.pad_thai, "pad_thai.json", 2)
            ))),
    GREEN_PAPAYA_SALAD(1, R.string.green_papaya_salad, R.drawable.green_papaya_salad_512,
            new ArrayList<>(Collections.singletonList(
                    new DishComponentInfo(R.string.green_papaya_salad, "green_papaya_salad.json", 3)
            ))),
    MASSAMAN_CURRY(2, R.string.massaman_curry, R.drawable.massaman_curry_512,
            new ArrayList<>(Collections.singletonList(
                    new DishComponentInfo(R.string.massaman_curry, "massaman_curry.json", 4)
            ))),
    GREEN_THAI_CURRY(3, R.string.green_thai_curry, R.drawable.green_thai_curry_512,
            new ArrayList<>(Collections.singletonList(
                    new DishComponentInfo(R.string.green_thai_curry, "green_thai_curry.json", 5)
            ))),
    TOM_YAM_SOUP(4, R.string.tom_yam_soup, R.drawable.tom_yam_soup_512,
            new ArrayList<>(Collections.singletonList(
                    new DishComponentInfo(R.string.tom_yam_soup, "tom_yam_soup.json", 6)
            ))),
    SPRING_ROLLS(5, R.string.spring_rolls, R.drawable.spring_rolls_512,
            new ArrayList<>(Arrays.asList(
                    new DishComponentInfo(R.string.peanut_sauce, "peanut_sauce.json", 1),
                    new DishComponentInfo(R.string.spring_rolls, "spring_rolls.json", 7)
            ))),
    PUMPKIN_HUMUS(6, R.string.pumpkin_humus, R.drawable.pumpkin_humus_512,
            new ArrayList<>(Collections.singletonList(
                    new DishComponentInfo(R.string.pumpkin_humus, "pumpkin_humus.json", 8)
            ))),
    MANGO_WITH_STICKY_RICE(7, R.string.mango_with_sticky_rice, R.drawable.mango_with_sticky_rice_512,
            new ArrayList<>(Arrays.asList(
                    new DishComponentInfo(R.string.sticky_rice, "sticky_rice.json", 9),
                    new DishComponentInfo(R.string.mango_with_sticky_rice, "mango_with_sticky_rice.json", 10)
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

    public String[] getDishComponentJsonFilenames() {
        String[] json_filenames = new String[getNumDishComponents()];
        for (int i = 0; i < getNumDishComponents(); i++) {
            json_filenames[i] = mDishComponentInfos.get(i).json_filename;
        }
        return json_filenames;
    }

    public int[] getDishComponentNameIds() {
        int[] name_ids = new int[getNumDishComponents()];
        for (int i = 0; i < getNumDishComponents(); i++) {
            name_ids[i] = mDishComponentInfos.get(i).name_id;
        }
        return name_ids;
    }

    public int getNumDishComponents() {
        return mDishComponentInfos.size();
    }
}
