package de.mwdevs.padthai.ui.recipe_steps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import de.mwdevs.padthai.R;

public abstract class RecipeStepsBaseFragment extends Fragment {
    protected static final String ARG_QUANTITY = "ARG_QUANTITY";
    protected static final String ARG_TEXT_1 = "ARG_TEXT_1";
    protected static final String ARG_TEXT_2 = "ARG_TEXT_2";

    private int mQuantity;
    private int mText1;
    private int mText2;

    public RecipeStepsBaseFragment() {
    }

    protected static void addArguments(RecipeStepsBaseFragment fragment, int quantity, int text1, int text2) {
        Bundle args = new Bundle();
        args.putInt(ARG_QUANTITY, quantity);
        args.putInt(ARG_TEXT_1, text1);
        args.putInt(ARG_TEXT_2, text2);
        fragment.setArguments(args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuantity = getArguments().getInt(ARG_QUANTITY);
            mText1 = getArguments().getInt(ARG_TEXT_1);
            mText2 = getArguments().getInt(ARG_TEXT_2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        ((TextView) root.findViewById(R.id.step_text1)).setText(mText1);
        ((TextView) root.findViewById(R.id.step_text2)).setText(mText2);
        return root;
    }

    protected void addQuantityView(View parent, @NonNull LayoutInflater inflater,
                                   float el, int el_id, int image_id) {
        View newQuantityView = addViewToParent(parent, inflater);
        setValuesInQuantityView(newQuantityView, el, el_id, image_id);
    }

    private View addViewToParent(View parent, @NonNull LayoutInflater inflater) {
        GridLayout gridLayout = parent.findViewById(R.id.gridLayout);
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
