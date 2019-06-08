package de.mwdevs.padthai.recipe_steps.peanut_sauce;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import de.mwdevs.padthai.R;

public class PTStepsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final ArrayList<Integer> TAB_TITLES = new ArrayList<>(Arrays.asList(
            R.string.tab_text_0,
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3n4,
            R.string.tab_text_5));

    private final Context mContext;
    private final int mQuantity;

    public PTStepsPagerAdapter(Context context, FragmentManager fm, int quantity) {
        super(fm);
        mContext = context;
        mQuantity = quantity;
    }

    @Override
    public Fragment getItem(int position) {
        return PTStepsFragment.newInstance(position, mQuantity);
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