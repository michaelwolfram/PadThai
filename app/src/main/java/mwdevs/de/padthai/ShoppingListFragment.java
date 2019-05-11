package mwdevs.de.padthai;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import mwdevs.de.padthai.ShoppingListContent.ShoppingItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ShoppingListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String LIST_LAYOUT = "list_layout";
    public static final String PASTE_QUANTITY = "paste_quantity";
    public static final String SOSSE_QUANTITY = "sosse_quantity";
    public static final String PADTHAI_QUANTITY = "padthai_quantity";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private int paste_quantity = 0;
    private int sosse_quantity = 0;
    private int padthai_quantity = 0;
    private Context context = null;
    private RecyclerView recyclerView = null;
    private RecyclerView.LayoutManager layoutManager = null;
    private MyShoppingListRecyclerViewAdapter mAdapter = null;
    private boolean showListAsGrid = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ShoppingListFragment newInstance(int columnCount) {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null)
            showListAsGrid = savedInstanceState.getBoolean(LIST_LAYOUT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        view.post(new Runnable() {
            @Override
            public void run() {
                int minWidth = 380;
                int width=view.getWidth();
                showListAsGrid = width > 2*minWidth;
                mColumnCount = width / minWidth;
                refreshRecyclerView(showListAsGrid, mColumnCount);
            }
        });

        if (view instanceof RecyclerView) {
            context = view.getContext();
            AssetManager assetManager = getActivity().getAssets();
            if (mAdapter == null)
                mAdapter = new MyShoppingListRecyclerViewAdapter(ShoppingListContent.ITEMS,
                        paste_quantity, sosse_quantity, padthai_quantity, mListener, assetManager, showListAsGrid);
            if (layoutManager == null)
                updateLayoutManager();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void refreshRecyclerView(boolean showListAsGrid) {
        refreshRecyclerView(showListAsGrid, mColumnCount);
    }
    public void refreshRecyclerView(boolean showListAsGrid, int mColumnCount) {
        this.showListAsGrid = showListAsGrid;
        mAdapter.setShowListAsGrid(showListAsGrid);
        this.mColumnCount = mColumnCount;
        updateLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private void updateLayoutManager() {
        if (showListAsGrid) {
            layoutManager = new GridLayoutManager(context, mColumnCount);
        } else {
            layoutManager = new LinearLayoutManager(context);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggleLayoutManager:
                refreshRecyclerView(!showListAsGrid);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(LIST_LAYOUT, showListAsGrid);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

        if (context instanceof ShoppingCart) {
            paste_quantity = ((ShoppingCart) context).getPaste_quantity();
            sosse_quantity = ((ShoppingCart) context).getSosse_quantity();
            padthai_quantity = ((ShoppingCart) context).getPadthai_quantity();
        } else {
            throw new RuntimeException(context.toString()
                    + " must extend ShoppingCart");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ShoppingItem item);
    }
}
