package de.mwdevs.padthai.ui.recipe_steps;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
//        adaptQuantityView(root, 42);
//        adaptQuantityView(root, 43);
//        adaptQuantityView(root, 44);
        return root;
    }

    private void adaptQuantityView(View parent, int layout_id) {
        int el = 42; // TODO: 25.05.19 read from TextView
        ((TextView) parent.findViewById(layout_id))
                .setText(getString(R.string.placeholder_d, el * mQuantity));
    }

}
