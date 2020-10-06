package de.mwdevs.padthai.recipe_steps;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import de.mwdevs.padthai.Utils;

public class RecipeStepsPagerAdapter extends FragmentStatePagerAdapter {
    private final Context mContext;
    private int mQuantity;
    private String mRecipeFilename;
    @StringRes
    private ArrayList<Integer> mTabTitles;

    @SuppressLint("WrongConstant")
    public RecipeStepsPagerAdapter(FragmentManager fragmentManager, Context context, int quantity, String json_filename) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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

    @NonNull
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
