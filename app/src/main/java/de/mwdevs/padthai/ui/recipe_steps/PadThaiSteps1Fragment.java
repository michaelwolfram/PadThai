package de.mwdevs.padthai.ui.recipe_steps;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.mwdevs.padthai.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PadThaiSteps1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PadThaiSteps1Fragment extends Fragment {
    private static final String ARG_QUANTITY = "param1";

    private int mQuantity;

    public PadThaiSteps1Fragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param quantity Parameter 1.
     * @return A new instance of fragment PadThaiSteps1Fragment.
     */
    public static PadThaiSteps1Fragment newInstance(int quantity) {
        PadThaiSteps1Fragment fragment = new PadThaiSteps1Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QUANTITY, quantity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuantity = getArguments().getInt(ARG_QUANTITY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pad_thai_steps1, container, false);
        adaptQuantityView(root, R.id.step1_oil, RecipeQuantities.PadThai.Step1.el_oil, "EL", R.mipmap.sojaoel_round);
        adaptQuantityView(root, R.id.step1_carrot, RecipeQuantities.PadThai.Step1.el_carrot, "EL", R.mipmap.karotten_round);
        adaptQuantityView(root, R.id.step1_onion, RecipeQuantities.PadThai.Step1.el_onion, "EL", R.mipmap.zwiebeln_round);
        adaptQuantityView(root, R.id.step1_tomato, RecipeQuantities.PadThai.Step1.el_tomato, "EL", R.mipmap.tomaten_round);
        adaptQuantityView(root, R.id.step1_tofu, RecipeQuantities.PadThai.Step1.el_tofu, "EL", R.mipmap.sojaoel_round);
        adaptQuantityView(root, R.id.step1_garlic, RecipeQuantities.PadThai.Step1.el_garlic, "EL", R.mipmap.knoblauch_round);
        return root;
    }

    private void adaptQuantityView(View parent, int layout_id, float el, String el_string, int image_id) {
        ((TextView) parent.findViewById(layout_id).findViewById(R.id.ingredient_g_value))
                .setText(getString(R.string.placeholder_d, Math.round(el * mQuantity)));
        ((TextView) parent.findViewById(layout_id).findViewById(R.id.ingredient_g)).setText(el_string);
        ((ImageView) parent.findViewById(layout_id).findViewById(R.id.ingredient_image)).setImageResource(image_id);
    }

}
