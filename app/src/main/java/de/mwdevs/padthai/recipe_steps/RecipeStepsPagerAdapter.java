package de.mwdevs.padthai.recipe_steps;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.mwdevs.padthai.R;

public class RecipeStepsPagerAdapter extends FragmentPagerAdapter {
    private static final ArrayList<ArrayList<Integer>> TAB_TITLES = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(
                    R.string.tab_text_0,
                    R.string.tab_text_1,
                    R.string.tab_text_2,
                    R.string.tab_text_3n4,
                    R.string.tab_text_5
            )),
            new ArrayList<>(Arrays.asList(
                    R.string.tab_text_0,
                    R.string.tab_text_1,
                    R.string.tab_text_2,
                    R.string.tab_text_3n4,
                    R.string.tab_text_5
            )),
            new ArrayList<>(Arrays.asList(
                    R.string.tab_text_0,
                    R.string.tab_text_1,
                    R.string.tab_text_2,
                    R.string.tab_text_3n4,
                    R.string.tab_text_5
            ))
    ));

    private static final Map<Class, Integer> VIEW_MODELS_MAP = new HashMap<>();

    static {
//        VIEW_MODELS_MAP.put(PadThaiStepViewModel.class, 0);
//        VIEW_MODELS_MAP.put(PadThaiStepViewModel.class, 1);
        VIEW_MODELS_MAP.put(PadThaiStepViewModel.class, 2);
    }

    private final Context mContext;
    private final int mQuantity;
    private final Class mViewModelClass;
    @StringRes
    private final ArrayList<Integer> mTabTitles;

    public RecipeStepsPagerAdapter(FragmentManager fm, Context context, int quantity, Class viewModelClass) {
        super(fm);
        mContext = context;
        mQuantity = quantity;
        mViewModelClass = viewModelClass;
        mTabTitles = mapClassToTabTitles(mViewModelClass);
    }

    private static ArrayList<Integer> mapClassToTabTitles(Class viewModelClass) {
        Integer i = VIEW_MODELS_MAP.get(viewModelClass);
        assert i != null;
        return TAB_TITLES.get(i);
    }

    @Override
    public Fragment getItem(int position) {
        return RecipeStepsFragment.newInstance(position, mQuantity, mViewModelClass);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(mTabTitles.get(position));
    }

    @Override
    public int getCount() {
        return mTabTitles.size();
    }
}
