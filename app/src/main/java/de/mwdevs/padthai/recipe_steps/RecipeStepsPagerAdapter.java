package de.mwdevs.padthai.recipe_steps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import de.mwdevs.padthai.Utils;

public class RecipeStepsPagerAdapter extends FragmentStatePagerAdapter {
    private final Context mContext;
    private int mQuantity;
    private String mRecipeFilename;
    @StringRes
    private ArrayList<Integer> mTabTitles;

    public RecipeStepsPagerAdapter(FragmentManager fragmentManager, Context context, int quantity, String json_filename) {
        super(fragmentManager);
        mContext = context;
        mQuantity = quantity;
        mRecipeFilename = json_filename;
        mTabTitles = Utils.readTabTitlesFromRecipe(mContext, mRecipeFilename);
    }

    public void updateData(int quantity, String json_filename) {
        mQuantity = quantity;
        mRecipeFilename = json_filename;
        mTabTitles = Utils.readTabTitlesFromRecipe(mContext, mRecipeFilename);

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return RecipeStepsFragment.newInstance(position, mQuantity, mRecipeFilename);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        RecipeStepsFragment fragment = (RecipeStepsFragment) object;
        if (fragment.getQuantity() == mQuantity && fragment.getRecipeId().equals(mRecipeFilename))
            return POSITION_UNCHANGED;
        else
            return POSITION_NONE;
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
