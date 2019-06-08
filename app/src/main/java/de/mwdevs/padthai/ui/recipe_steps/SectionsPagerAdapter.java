package de.mwdevs.padthai.ui.recipe_steps;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import de.mwdevs.padthai.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final ArrayList<Integer> TAB_TITLES = new ArrayList<>(Arrays.asList(
            R.string.tab_text_0,
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3n4,
            R.string.tab_text_5));

    private final Context mContext;
    private final int mQuantity;

    public SectionsPagerAdapter(Context context, FragmentManager fm, int quantity) {
        super(fm);
        mContext = context;
        mQuantity = quantity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PadThaiSteps0Fragment.newInstance(mQuantity);
            case 1:
                return PadThaiSteps1Fragment.newInstance(mQuantity);
            case 2:
                return PadThaiSteps2Fragment.newInstance(mQuantity);
            case 3:
                return PadThaiSteps3n4Fragment.newInstance(mQuantity);
            case 4:
                return PadThaiSteps5Fragment.newInstance(mQuantity);
            default:
                return PadThaiSteps0Fragment.newInstance(mQuantity);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES.get(position));
    }

    @Override
    public int getCount() {
        return TAB_TITLES.size();
    }
}