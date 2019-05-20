package mwdevs.de.padthai;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.lang.ref.WeakReference;

import static java.lang.Math.max;

public class ShoppingCart extends AppCompatActivity implements OnListInteractionListener {

    public static final String PASTE_QUANTITY = "paste_quantity";
    public static final String SOSSE_QUANTITY = "sosse_quantity";
    public static final String PAD_THAI_QUANTITY = "pad_thai_quantity";
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
    private int paste_quantity = 0;
    private int sosse_quantity = 0;
    private int pad_thai_quantity = 0;
    private RecyclerView recyclerView = null;
    private RecyclerView.LayoutManager layoutManager = null;
    private MyShoppingListRecyclerViewAdapter mAdapter = null;
    private boolean showListAsGrid;
    private Snackbar snackbar;
    private ShowcaseView.Builder showcaseViewBuilder;
    private ShowcaseView showcaseView;
    private RecyclerViewScrollDisabler recyclerViewScrollDisabler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startBackgroundTasks();
        consumeIndent();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shopping_cart);

        initRecyclerViewAdapter();
        initRecyclerView();
        initShowcaseViewStuff();
        initSnackBar();

        updateLayoutManagerParameters();
        updateRecyclerView();
    }

    private void startBackgroundTasks() {
        new LoadExcelSheetTask(this).execute(EXCEL_FILENAME);
    }

    private void consumeIndent() {
        Intent intent = getIntent();
        paste_quantity = intent.getIntExtra(PASTE_QUANTITY, 0);
        sosse_quantity = intent.getIntExtra(SOSSE_QUANTITY, 0);
        pad_thai_quantity = intent.getIntExtra(PAD_THAI_QUANTITY, 0);
    }

    public void initRecyclerViewAdapter() {
        // TODO: 19.05.19 instead of using .ITEMS make method etc to dynamically get list of items
        mAdapter = new MyShoppingListRecyclerViewAdapter(ShoppingListContent.ITEMS, this, this,
                paste_quantity, sosse_quantity, pad_thai_quantity, showListAsGrid);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                View view = getItemToFocusInShowcaseView(1);
                showShowcaseView(view);
            }
        });
    }

    private View getItemToFocusInShowcaseView(int rowFromBottom) {
        if (layoutManager == null)
            return null;

        int itemId;
        if (showListAsGrid) {
            itemId = ((GridLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            itemId = max(0, itemId - rowFromBottom * mColumnCount + 1);
        } else {
            itemId = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            itemId = max(0, itemId - rowFromBottom * mColumnCount + 1);
        }
        return recyclerView.getChildAt(itemId);
    }

    private void showShowcaseView(View view) {
        recyclerView.addOnScrollListener(recyclerViewScrollDisabler);

        showcaseView = showcaseViewBuilder
                .setTarget(new ViewTarget(view))
                .build();

        if (!showcaseView.isShowing()) {
            recyclerView.removeOnScrollListener(recyclerViewScrollDisabler);
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.shopping_list);
        recyclerView.setKeepScreenOn(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerViewDismissSnackBarOnScroll());
    }

    private void initShowcaseViewStuff() {
        showcaseViewBuilder = new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.PadThaiShowcaseView)
                .singleShot(22)
                .setContentTitle(R.string.item_in_cart)
                .setContentText(R.string.click_will_mark)
                .setFadeInDurations(800)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showcaseView.setContentTitle(getString(R.string.what_that_ingredient));
                        showcaseView.setContentText(getString(R.string.try_long_click));
                        View view2 = getItemToFocusInShowcaseView(2);
                        showcaseView.setShowcase(new ViewTarget(view2), true);
                        showcaseView.overrideButtonClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                recyclerView.removeOnScrollListener(recyclerViewScrollDisabler);
                                showcaseView.hide();
                            }
                        });
                    }
                });
        recyclerViewScrollDisabler = new RecyclerViewScrollDisabler();
    }

    private void initSnackBar() {
        snackbar = Snackbar.make(recyclerView, R.string._0, Snackbar.LENGTH_SHORT);
    }

    private void updateLayoutManagerParameters() {
        int availableDisplayWidthPx = getDisplayWidth();
        int minItemWidthPx = convertDpToPixel(MIN_ITEM_WIDTH_DP);

        showListAsGrid = availableDisplayWidthPx >= 2 * minItemWidthPx;
        mColumnCount = availableDisplayWidthPx / minItemWidthPx;
    }

    private int getDisplayWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public int convertDpToPixel(float dp) {
        return Math.round(dp *
                ((float) getResources().getDisplayMetrics().densityDpi
                        / (float) DisplayMetrics.DENSITY_DEFAULT));
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
        if (snackbar.isShown())
            snackbar.dismiss();
    }

    @Override
    public void onListItemLongClick(ShoppingListContent.ShoppingItem item) {
        snackbar.setText(item.toString());
        snackbar.show();
    }

    private void updateAdapter(Workbook workbook) {
        mAdapter.setWorkbook(workbook);
    }

    private static class LoadExcelSheetTask extends AsyncTask<String, Void, Workbook> {
        private WeakReference<ShoppingCart> activityReference;

        LoadExcelSheetTask(ShoppingCart activity) {
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
            ShoppingCart activity = activityReference.get();
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
            if (snackbar.isShown())
                snackbar.dismiss();
        }
    }
}
