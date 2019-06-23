package de.mwdevs.padthai.recipe_steps;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import de.mwdevs.padthai.Utils;

public class RecipeStepsPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    private final int mQuantity;
    private final String mRecipeFilename;
    @StringRes
    private final ArrayList<Integer> mTabTitles;

    public RecipeStepsPagerAdapter(FragmentManager fm, Context context, int quantity, String json_filename) {
        super(fm);
        mContext = context;
        mQuantity = quantity;
        mRecipeFilename = json_filename;
        mTabTitles = Utils.readTabTitlesFromRecipe(mContext, mRecipeFilename);
    }

    @Override
    public Fragment getItem(int position) {
        return RecipeStepsFragment.newInstance(position, mQuantity, mRecipeFilename);
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
