package de.mwdevs.padthai;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.lang.ref.WeakReference;

import de.mwdevs.padthai.shopping_list.OnListInteractionListener;
import de.mwdevs.padthai.shopping_list.ShoppingListRVAdapter;
import de.mwdevs.padthai.shopping_list.data.ShoppingListContent;

import static java.lang.Math.max;

public class ShoppingListActivity extends AppCompatActivity implements OnListInteractionListener {

    public static final String CACHED_SHOPPING_LIST = "CACHED_SHOPPING_LIST";
    private static final String EXCEL_FILENAME = "Pad Thai Angaben.xls";
    /**
     * Can be calculated with e.g.:
     * <p>
     * private int getViewWidthForShoppingItemGrid() {
     * View view = getLayoutInflater().inflate(R.layout.shopping_item_grid, null, false);
     * <p>
     * ((TextView) view.findViewById(R.id.ingredient_g_value)).setText(R.string._888);
     * ((TextView) view.findViewById(R.id.ingredient_stk_value)).setText(R.string._88dot0);
     * ((TextView) view.findViewById(R.id.ingredient_g)).setText(R.string.g);
     * ((TextView) view.findViewById(R.id.ingredient_stk)).setText(R.string.stk);
     * <p>
     * view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
     * return view.getMeasuredWidth();
     * }
     */
    private static final int MIN_ITEM_WIDTH_DP = 168;
    private int mColumnCount = 2;
    private int[] component_quantities = null;
    private RecyclerView recyclerView = null;
    private RecyclerView.LayoutManager layoutManager = null;
    private ShoppingListRVAdapter mAdapter = null;
    private boolean showListAsGrid;
    private Snackbar snackbar;
    private ShowcaseView.Builder showcaseViewBuilder;
    private ShowcaseView showcaseView;
    private RecyclerViewScrollDisabler recyclerViewScrollDisabler;
    private boolean gotAdapterDataFromSavedInstanceState;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mAdapter.hasData())
            outState.putParcelableArrayList(CACHED_SHOPPING_LIST, mAdapter.getData());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        consumeIndent();
        initRecyclerViewAdapter();
        retrieveAdapterData(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        hideProgressBarIfDataWasAlreadySet();

        initShowcaseViewBuilder();
        setupRecyclerView();
    }

    private void consumeIndent() {
        Intent intent = getIntent();
        component_quantities = intent.getIntArrayExtra(MainActivity.COMPONENT_QUANTITIES);
    }

    public void initRecyclerViewAdapter() {
        mAdapter = new ShoppingListRVAdapter(this, this, component_quantities, showListAsGrid);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                triggerShowcaseView();
            }
        });
    }

    private void retrieveAdapterData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mAdapter.setData(savedInstanceState.<ShoppingListContent.ShoppingItem>getParcelableArrayList(CACHED_SHOPPING_LIST));
        }

        if (!mAdapter.hasData()) {
            loadDataInBackground();
        } else {
            gotAdapterDataFromSavedInstanceState = true;
        }
    }

    private void loadDataInBackground() {
        new LoadExcelSheetTask(this).execute(EXCEL_FILENAME);
    }

    private void hideProgressBarIfDataWasAlreadySet() {
        if (gotAdapterDataFromSavedInstanceState)
            hideProgressBar();
    }

    private void triggerShowcaseView() {
        boolean isShowing = showcaseView != null && showcaseView.isShowing();
        if (showcaseViewBuilder.hasShowcaseViewShot() || isShowing) {
            // there's no need to continue building/showing the ShowcaseView since
            // a) it has been shot before
            // b) it is already/still showing.
            return;
        }

        Runnable runnableShowShowcaseView = new Runnable() {
            @Override
            public void run() {
                View view = getItemToFocusInShowcaseView(0);
                if (view != null) {
                    showShowcaseView(view);
                } else {
                    recyclerView.postDelayed(this, 1);
                }
            }
        };
        recyclerView.post(runnableShowShowcaseView);
    }

    @Nullable
    private View getItemToFocusInShowcaseView(int rowFromBottom) {
        if (layoutManager == null)
            return null;

        int itemId = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        itemId = max(0, itemId - rowFromBottom * mColumnCount - itemId % mColumnCount);
        return recyclerView.getChildAt(itemId);
    }

    private void showShowcaseView(View view) {
        recyclerViewScrollDisabler = new RecyclerViewScrollDisabler();
        recyclerView.addOnScrollListener(recyclerViewScrollDisabler);

        showcaseView = showcaseViewBuilder
                .withMaterialShowcase()
                .setStyle(R.style.PadThaiShowcaseView)
                .setContentTitle(R.string.item_in_cart)
                .setContentText(R.string.click_will_mark)
                .setFadeInDurations(800)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showcaseView.setContentTitle(getString(R.string.what_that_ingredient));
                        showcaseView.setContentText(getString(R.string.try_long_click));
                        View view2 = getItemToFocusInShowcaseView(1);
                        showcaseView.setShowcase(new ViewTarget(view2), true);
                        showcaseView.overrideButtonClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                recyclerView.removeOnScrollListener(recyclerViewScrollDisabler);
                                showcaseView.hide();
                                mAdapter.resetData();
                            }
                        });
                    }
                })
                .setTarget(new ViewTarget(view))
                .build();
    }

    private void initShowcaseViewBuilder() {
        showcaseViewBuilder = new ShowcaseView.Builder(this)
                .singleShot(22);
    }

    private void setupRecyclerView() {
        initRecyclerView();
        updateLayoutManagerParameters();
        updateRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.shopping_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerViewDismissSnackBarOnScroll());
    }

    private void updateLayoutManagerParameters() {
        int availableDisplayWidthPx = Utils.getDisplayWidth(getWindowManager());
        int minItemWidthPx = Utils.convertDpToPixel(getResources(), MIN_ITEM_WIDTH_DP);

        showListAsGrid = availableDisplayWidthPx >= 2 * minItemWidthPx;
        mColumnCount = availableDisplayWidthPx / minItemWidthPx;
    }

    public void updateRecyclerView() {
        mAdapter.setShowListAsGrid(showListAsGrid);
        updateLayoutManager();
    }

    private void updateLayoutManager() {
        if (showListAsGrid) {
            layoutManager = new GridLayoutManager(this, mColumnCount);
        } else {
            layoutManager = new LinearLayoutManager(this);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ShoppingListContent.ShoppingItem item) {
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();
    }

    @Override
    public void onListItemLongClick(ShoppingListContent.ShoppingItem item) {
        if (snackbar == null) {
            initSnackBar();
        }
        snackbar.setText(item.getNameId());
        snackbar.show();
    }

    private void initSnackBar() {
        snackbar = Snackbar.make(recyclerView, R.string._0, Snackbar.LENGTH_SHORT);
    }

    private void updateAdapter(Workbook workbook) {
        mAdapter.updateDataFromWorkbook(workbook);
        hideProgressBar();
    }

    private void hideProgressBar() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private static class LoadExcelSheetTask extends AsyncTask<String, Void, Workbook> {
        private WeakReference<ShoppingListActivity> activityReference;

        LoadExcelSheetTask(ShoppingListActivity activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override
        protected Workbook doInBackground(String... strings) {
            try {
                String file_name = strings[0];
                InputStream myInput = activityReference.get().getAssets().open(file_name);
                return new HSSFWorkbook(myInput);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Workbook workbook) {
            ShoppingListActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing())
                return;

            activity.updateAdapter(workbook);
        }
    }

    private class RecyclerViewScrollDisabler extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            recyclerView.stopScroll();
        }
    }

    private class RecyclerViewDismissSnackBarOnScroll extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (snackbar != null && snackbar.isShown())
                snackbar.dismiss();
        }
    }
}
