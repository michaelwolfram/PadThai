package mwdevs.de.padthai;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.PointTarget;

public class ShoppingCart extends AppCompatActivity implements OnListFragmentInteractionListener {

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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(LIST_LAYOUT, showListAsGrid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        paste_quantity = intent.getIntExtra(PASTE_QUANTITY, 0);
        sosse_quantity = intent.getIntExtra(SOSSE_QUANTITY, 0);
        padthai_quantity = intent.getIntExtra(PADTHAI_QUANTITY, 0);

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            showListAsGrid = savedInstanceState.getBoolean(LIST_LAYOUT);

        setContentView(R.layout.activity_shopping_cart);

//
//
//

        recyclerView = findViewById(R.id.shopping_list);

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

        if (mAdapter == null)
            mAdapter = new MyShoppingListRecyclerViewAdapter(ShoppingListContent.ITEMS,
                    paste_quantity, sosse_quantity, padthai_quantity, this, getAssets(), showListAsGrid);
        if (layoutManager == null)
            updateLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setKeepScreenOn(true);
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

    @Override
    protected void onResume() {
        super.onResume();

        new ShowcaseView.Builder(this)
                .withMaterialShowcase()
//                .singleShot(42)
                .setTarget(new PointTarget(300, 300))
                .setContentTitle("ShowcaseView")
                .setContentText("This is highlighting the Home button")
                .hideOnTouchOutside()
                .build();
    }

    @Override
    public void onListFragmentInteraction(ShoppingListContent.ShoppingItem item) {

    }

}
