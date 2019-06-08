package de.mwdevs.padthai.recipe_steps.pad_thai;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.mwdevs.padthai.R;

public class PTStepsFragment extends Fragment {
    protected static final String ARG_QUANTITY = "ARG_QUANTITY";
    private static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";
    private PTStepViewModel pTStepViewModel;
    private int mQuantity;

    public static PTStepsFragment newInstance(int index, int quantity) {
        PTStepsFragment fragment = new PTStepsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putInt(ARG_QUANTITY, quantity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pTStepViewModel = ViewModelProviders.of(this).get(PTStepViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            mQuantity = getArguments().getInt(ARG_QUANTITY);
        }
        pTStepViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        final TextView text1 = root.findViewById(R.id.step_text1);
        pTStepViewModel.getText1().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer id) {
                assert id != null;
                text1.setText(id);
            }
        });

        final TextView text2 = root.findViewById(R.id.step_text2);
        pTStepViewModel.getText2().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer id) {
                assert id != null;
                text2.setText(id);
            }
        });

        final GridLayout gridLayout = root.findViewById(R.id.gridLayout);
        pTStepViewModel.getRecipeQuantityInfo().observe(this, new Observer<ArrayList<PTStepViewModel.RecipeQuantityInfo>>() {
            @Override
            public void onChanged(@Nullable ArrayList<PTStepViewModel.RecipeQuantityInfo> recipeQuantityInfoList) {
                assert recipeQuantityInfoList != null;
                for (PTStepViewModel.RecipeQuantityInfo info : recipeQuantityInfoList) {
                    addQuantityView(gridLayout, inflater, info.el, info.string_id, info.image_id);
                }
            }
        });

        return root;
    }

    protected void addQuantityView(GridLayout gridLayout, @NonNull LayoutInflater inflater,
                                   float el, int el_id, int image_id) {
        View newQuantityView = addViewToParent(gridLayout, inflater);
        setValuesInQuantityView(newQuantityView, el, el_id, image_id);
    }

    private View addViewToParent(GridLayout gridLayout, @NonNull LayoutInflater inflater) {
        View newQuantityView = inflater.inflate(R.layout.recipe_steps_item, gridLayout, false);
        gridLayout.addView(newQuantityView);
        return newQuantityView;
    }

    private void setValuesInQuantityView(View newQuantityView, float el, int el_id, int image_id) {
        String el_string = removeZero(el * mQuantity);
        ((TextView) newQuantityView.findViewById(R.id.ingredient_g_value)).setText(el_string);
        ((TextView) newQuantityView.findViewById(R.id.ingredient_g)).setText(el_id);
        ((ImageView) newQuantityView.findViewById(R.id.ingredient_image)).setImageResource(image_id);
    }

    public String removeZero(float number) {
        DecimalFormat format = new DecimalFormat("#.##");
        return format.format(number);
    }


}
