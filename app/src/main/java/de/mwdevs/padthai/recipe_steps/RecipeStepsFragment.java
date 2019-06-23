package de.mwdevs.padthai.recipe_steps;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import de.mwdevs.padthai.R;
import de.mwdevs.padthai.recipe_steps.data.RecipeQuantityInfo;
import de.mwdevs.padthai.recipe_steps.data.RecipeStepsViewModel;

public class RecipeStepsFragment extends Fragment {
    protected static final String ARG_QUANTITY = "ARG_QUANTITY";
    protected static final String ARG_RECIPE_ID = "ARG_RECIPE_ID";
    private static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";
    private RecipeStepsViewModel mViewModel;
    private int mQuantity;
    private GridLayout gridLayout;
    private Snackbar snackbar;

    public static RecipeStepsFragment newInstance(int index, int quantity, String recipeID) {
        RecipeStepsFragment fragment = new RecipeStepsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putInt(ARG_QUANTITY, quantity);
        bundle.putSerializable(ARG_RECIPE_ID, recipeID);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        digestArguments();
    }

    private void digestArguments() {
        assert getArguments() != null;

        int index = getArguments().getInt(ARG_SECTION_NUMBER);
        mQuantity = getArguments().getInt(ARG_QUANTITY);
        getViewModel(getArguments());

        mViewModel.setIndex(index);
    }

    private void getViewModel(Bundle arguments) {
        String recipeID = arguments.getString(ARG_RECIPE_ID);
        mViewModel = ViewModelProviders.of(this, new RecipeStepsViewModel.Factory(
                Objects.requireNonNull(this.getActivity()).getApplication(), recipeID)).get(RecipeStepsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        setText(root, R.id.step_text1, mViewModel.getText1());
        setText(root, R.id.step_text2, mViewModel.getText2());
        createQuantityViews(inflater, root);

        return root;
    }

    private void setText(View root, int textId, LiveData<Integer> textLiveData) {
        final TextView textView = root.findViewById(textId);
        textLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer id) {
                assert id != null;
                textView.setText(id);
            }
        });
    }

    private void createQuantityViews(@NonNull final LayoutInflater inflater, View root) {
        gridLayout = root.findViewById(R.id.gridLayout);
        mViewModel.getRecipeQuantityInfo().observe(this, new Observer<ArrayList<RecipeQuantityInfo>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RecipeQuantityInfo> recipeQuantityInfoList) {
                assert recipeQuantityInfoList != null;
                for (RecipeQuantityInfo info : recipeQuantityInfoList) {
                    addQuantityView(gridLayout, inflater, info);
                }
            }
        });
    }

    protected void addQuantityView(GridLayout gridLayout, @NonNull LayoutInflater inflater,
                                   RecipeQuantityInfo info) {
        View newQuantityView = addViewToParent(gridLayout, inflater);
        setValuesInQuantityView(newQuantityView, info);
        createListeners(newQuantityView);
    }

    private View addViewToParent(GridLayout gridLayout, @NonNull LayoutInflater inflater) {
        View newQuantityView = inflater.inflate(R.layout.recipe_steps_item, gridLayout, false);
        gridLayout.addView(newQuantityView);
        return newQuantityView;
    }

    private void setValuesInQuantityView(View newQuantityView, RecipeQuantityInfo info) {
        String el_string = removeZero(info.el * mQuantity);
        ((TextView) newQuantityView.findViewById(R.id.ingredient_g_value)).setText(el_string);
        ((TextView) newQuantityView.findViewById(R.id.ingredient_g)).setText(info.string_id);
        ((ImageView) newQuantityView.findViewById(R.id.ingredient_image)).setImageResource(info.image_id);
        newQuantityView.setTag(info.name_id);
    }

    private void createListeners(View newQuantityView) {
        newQuantityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (snackbar != null && snackbar.isShown())
                    snackbar.dismiss();
                v.setAlpha((3.15f - v.getAlpha()) % 2);
            }
        });
        newQuantityView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (snackbar == null) {
                    initSnackBar();
                }
                snackbar.setText((int) v.getTag());
                snackbar.show();
                return true;
            }
        });
    }

    private void initSnackBar() {
        snackbar = Snackbar.make(gridLayout, R.string._0, Snackbar.LENGTH_SHORT);
    }

    public String removeZero(float number) {
        DecimalFormat format = new DecimalFormat("#.##");
        return format.format(number);
    }


}
