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
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.lang.ref.WeakReference;

import static java.lang.Math.max;

public class ShoppingCart extends AppCompatActivity implements OnListInteractionListener {

    private static final String EXCEL_FILENAME = "Pad Thai Angaben.xls";

    private static final String LIST_LAYOUT = "list_layout";
    public static final String PASTE_QUANTITY = "paste_quantity";
    public static final String SOSSE_QUANTITY = "sosse_quantity";
    public static final String PADTHAI_QUANTITY = "padthai_quantity";

    private int mColumnCount = 2;
    private int paste_quantity = 0;
    private int sosse_quantity = 0;
    private int padthai_quantity = 0;
    private RecyclerView recyclerView = null;
    private RecyclerView.LayoutManager layoutManager = null;
    private MyShoppingListRecyclerViewAdapter mAdapter = null;
    private boolean showListAsGrid = false;
    private Snackbar snackbar;
    private ShowcaseView showcaseView;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(LIST_LAYOUT, showListAsGrid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LoadExcelSheetTask(this).execute(EXCEL_FILENAME);
        consumeIndent();
        super.onCreate(savedInstanceState);

        consumeSavedInstanceState(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        setupRecyclerView();
        setupSnackBar();
        setupAndPostRunnables();
    }

    private void consumeIndent() {
        Intent intent = getIntent();
        paste_quantity = intent.getIntExtra(PASTE_QUANTITY, 0);
        sosse_quantity = intent.getIntExtra(SOSSE_QUANTITY, 0);
        padthai_quantity = intent.getIntExtra(PADTHAI_QUANTITY, 0);
    }

    private void consumeSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            showListAsGrid = savedInstanceState.getBoolean(LIST_LAYOUT);
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.shopping_list);
        recyclerView.setKeepScreenOn(true);
        recyclerView.addOnScrollListener(new RecyclerViewDismissSnackBarOnScroll());
    }

    private void setupSnackBar() {
        snackbar = Snackbar.make(recyclerView, R.string._0, Snackbar.LENGTH_SHORT);
    }

    private void setupAndPostRunnables() {
        Runnable runnableRefreshList = new Runnable() {
            @Override
            public void run() {
                if (mAdapter != null) {
                    int minWidth = 360;
                    int width = recyclerView.getWidth();
                    showListAsGrid = width >= 2 * minWidth;
                    mColumnCount = width / minWidth;
                    refreshRecyclerView();
                } else {
                    recyclerView.postDelayed(this, 1);
                }
            }
        };

        Runnable runnableShowShowcaseView = new Runnable() {
            @Override
            public void run() {
                View view = getItemToFocusInShowcaseView(1);
                if (view != null) {
                    showShowcaseView(view);
                } else {
                    recyclerView.postDelayed(this, 1);
                }
            }
        };

        recyclerView.post(runnableRefreshList);
        recyclerView.post(runnableShowShowcaseView);
    }

    public void refreshRecyclerView() {
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

    private void showShowcaseView(View view) {
        final RecyclerViewScrollDisabler recyclerViewScrollDisabler = new RecyclerViewScrollDisabler();
        recyclerView.addOnScrollListener(recyclerViewScrollDisabler);

        showcaseView = new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.PadThaiShowcaseView)
                .singleShot(22)
                .setTarget(new ViewTarget(view))
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
                })
                .build();

        if (!showcaseView.isShowing()) {
            recyclerView.removeOnScrollListener(recyclerViewScrollDisabler);
        }
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

    public void createRecyclerViewAdapter(Workbook mWorkbook) {
        mAdapter = new MyShoppingListRecyclerViewAdapter(ShoppingListContent.ITEMS,
                paste_quantity, sosse_quantity, padthai_quantity, this, mWorkbook, showListAsGrid);
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

            activity.createRecyclerViewAdapter(workbook);
        }
    }
}
