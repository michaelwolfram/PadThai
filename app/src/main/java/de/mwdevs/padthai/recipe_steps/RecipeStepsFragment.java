package de.mwdevs.padthai.recipe_steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import de.mwdevs.padthai.R;
import de.mwdevs.padthai.RecipeStepsActivity;
import de.mwdevs.padthai.recipe_steps.data.RecipeQuantityInfo;
import de.mwdevs.padthai.recipe_steps.data.RecipeStepsViewModel;

public class RecipeStepsFragment extends Fragment {
    protected static final String ARG_QUANTITY = "ARG_QUANTITY";
    protected static final String ARG_RECIPE_ID = "ARG_RECIPE_ID";
    private static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";
    private RecipeStepsViewModel mViewModel;
    private int mSectionNumber;
    private int mQuantity;
    private String mRecipeId;
    private GridLayout gridLayout;
    private Snackbar snackbar;

    public static RecipeStepsFragment newInstance(int index, int quantity, String recipeID) {
        RecipeStepsFragment fragment = new RecipeStepsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putInt(ARG_QUANTITY, quantity);
        bundle.putString(ARG_RECIPE_ID, recipeID);
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

        mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        mQuantity = getArguments().getInt(ARG_QUANTITY);
        mRecipeId = getArguments().getString(ARG_RECIPE_ID);

        getViewModel();
        mViewModel.setIndex(mSectionNumber);
    }

    private void getViewModel() {
        mViewModel = new ViewModelProvider(this, new RecipeStepsViewModel.Factory(
                this.requireActivity().getApplication(), mRecipeId)).get(RecipeStepsViewModel.class);
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
        textLiveData.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer id) {
                assert id != null;
                textView.setText(id);
            }
        });
    }

    private void createQuantityViews(@NonNull final LayoutInflater inflater, View root) {
        gridLayout = root.findViewById(R.id.gridLayout);
        mViewModel.getRecipeQuantityInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList<RecipeQuantityInfo>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RecipeQuantityInfo> recipeQuantityInfoList) {
                assert recipeQuantityInfoList != null;
                int index = 0;
                for (RecipeQuantityInfo info : recipeQuantityInfoList) {
                    addQuantityView(inflater, info, index++);
                }
            }
        });
    }

    protected void addQuantityView(@NonNull LayoutInflater inflater,
                                   RecipeQuantityInfo info, int quantityViewIndex) {
        View newQuantityView = addViewToParent(inflater);
        setValuesInQuantityView(newQuantityView, info, quantityViewIndex);
        createListeners(newQuantityView);
    }

    private View addViewToParent(@NonNull LayoutInflater inflater) {
        View newQuantityView = inflater.inflate(R.layout.recipe_steps_item, gridLayout, false);
        gridLayout.addView(newQuantityView);
        return newQuantityView;
    }

    private void setValuesInQuantityView(View newQuantityView, RecipeQuantityInfo info, int quantityViewIndex) {
        String el_string = removeZero(info.el * mQuantity);
        ((TextView) newQuantityView.findViewById(R.id.ingredient_g_value)).setText(el_string);
        ((TextView) newQuantityView.findViewById(R.id.ingredient_g)).setText(info.string_id);
        ((ImageView) newQuantityView.findViewById(R.id.ingredient_image)).setImageResource(info.image_id);
        newQuantityView.setTag(R.id.QUANTITY_NAME, info.name_id);
        newQuantityView.setTag(R.id.QUANTITY_INDEX, quantityViewIndex);
        setAlpha(newQuantityView, quantityViewIndex);
    }

    private void setAlpha(View newQuantityView, int quantityViewIndex) {
        RecipeStepsActivity activity = (RecipeStepsActivity) getActivity();
        boolean is_clicked = Objects.requireNonNull(activity).getQuantityPressedState(mSectionNumber, quantityViewIndex);
        newQuantityView.setAlpha(is_clicked ? 0.15f : 1.0f);
    }

    private void createListeners(View newQuantityView) {
        newQuantityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (snackbar != null && snackbar.isShown())
                    snackbar.dismiss();
                toggleAlpha(v);
                Objects.requireNonNull((RecipeStepsActivity) getActivity())
                        .toggleQuantityPressedState(mSectionNumber, (int) v.getTag(R.id.QUANTITY_INDEX));
            }

            void toggleAlpha(View v) {
                v.setAlpha((3.15f - v.getAlpha()) % 2);
            }
        });
        newQuantityView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (snackbar == null) {
                    initSnackBar();
                }
                snackbar.setText((int) v.getTag(R.id.QUANTITY_NAME));
                snackbar.show();
                return true;
            }
        });
    }

    private void initSnackBar() {
        snackbar = Snackbar.make(gridLayout, R.string._0, Snackbar.LENGTH_SHORT);
        ((TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text)).setMaxLines(3);
    }

    public String removeZero(float number) {
        DecimalFormat format = new DecimalFormat("#.##");
        return format.format(number);
    }

    public int getQuantity() {
        return mQuantity;
    }

    public String getRecipeId() {
        return mRecipeId;
    }
}
