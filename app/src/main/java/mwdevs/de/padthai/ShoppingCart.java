package mwdevs.de.padthai;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class ShoppingCart extends AppCompatActivity implements OnListInteractionListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(LIST_LAYOUT, showListAsGrid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        consumeIndent();
        super.onCreate(savedInstanceState);

        consumeSavedInstanceState(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        setupRecyclerView();
        setupSnackBar();
    }

    private void setupSnackBar() {
        snackbar = Snackbar.make(recyclerView, R.string._0, Snackbar.LENGTH_SHORT);
    }

    private void consumeSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            showListAsGrid = savedInstanceState.getBoolean(LIST_LAYOUT);
    }

    private void consumeIndent() {
        Intent intent = getIntent();
        paste_quantity = intent.getIntExtra(PASTE_QUANTITY, 0);
        sosse_quantity = intent.getIntExtra(SOSSE_QUANTITY, 0);
        padthai_quantity = intent.getIntExtra(PADTHAI_QUANTITY, 0);
    }

    private MyShoppingListRecyclerViewAdapter createRecyclerViewAdapter() {
        return new MyShoppingListRecyclerViewAdapter(ShoppingListContent.ITEMS,
                paste_quantity, sosse_quantity, padthai_quantity, this, getAssets(), showListAsGrid);
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.shopping_list);
        if (mAdapter == null)
            mAdapter = createRecyclerViewAdapter();
        if (layoutManager == null)
            updateLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setKeepScreenOn(true);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (snackbar.isShown())
                    snackbar.dismiss();
                return false;
            }
        });
    }

    public void refreshRecyclerView() {
        mAdapter.setShowListAsGrid(showListAsGrid);
        updateLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private void updateLayoutManager() {
        if (showListAsGrid) {
            layoutManager = new GridLayoutManager(this, mColumnCount);
        } else {
            layoutManager = new LinearLayoutManager(this);
        }
    }

    private void showShowcaseView(View view) {
        new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.PadThaiShowcaseView)
//                .singleShot(42)
                .setTarget(new ViewTarget(view))
                .setContentTitle("Have you just put the item in your shopping cart?")
                .setContentText("...clicking will mark it!")
                .setFadeInDurations(800)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                int minWidth = 360;
                int width = recyclerView.getWidth();
                showListAsGrid = width >= 2 * minWidth;
                mColumnCount = width / minWidth;
                refreshRecyclerView();
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                View view = recyclerView.getChildAt(3);
                if (view != null) {
                    showShowcaseView(view);
                } else {
                    recyclerView.postDelayed(this, 1);
                }
            }
        };
        recyclerView.post(runnable);
    }

    @Override
    public void onListItemClick(ShoppingListContent.ShoppingItem item) {
        snackbar.dismiss();
    }

    @Override
    public void onListItemLongClick(ShoppingListContent.ShoppingItem item) {
        snackbar.setText(item.toString());
        snackbar.show();
    }
}
