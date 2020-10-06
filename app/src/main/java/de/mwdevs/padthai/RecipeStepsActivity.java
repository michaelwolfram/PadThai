package de.mwdevs.padthai;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import de.mwdevs.padthai.recipe_steps.RecipeStepsPagerAdapter;

interface OnVerticalFlingListener {
    void onFlingUp();

    void onFlingDown();
}

public class RecipeStepsActivity extends AppCompatActivity implements OnVerticalFlingListener {
    public static final String DISH_COMPONENT_QUANTITIES = "DISH_COMPONENT_QUANTITIES";
    public static final String JSON_FILENAMES = "JSON_FILENAMES";
    public static final String INITIAL_COMPONENT = "INITIAL_COMPONENT";
    public static final String NAME_IDS = "NAME_IDS";

    private ViewPager mViewPager;
    private RecipeStepsPagerAdapter recipeStepsPagerAdapter;
    private int visible_component = 0;
    private int num_components;
    private int[] mComponentQuantityArray;
    private String[] mJsonFilenameArray;
    private int[] mNameIdArray;
    private int[] mLastActiveItemArray;
    private boolean[][][] mQuantityPressedStatesMatrix = new boolean[3][10][10];
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        consumeIndent();
        setupViewPagerAndStuff();

        mDetector = new GestureDetectorCompat(this, new MyGestureListener(this));
    }

    public boolean getQuantityPressedState(int component_step, int quantity_view) {
        return mQuantityPressedStatesMatrix[visible_component][component_step][quantity_view];
    }

    public void toggleQuantityPressedState(int component_step, int quantity_view) {
        mQuantityPressedStatesMatrix[visible_component][component_step][quantity_view] ^= true;
    }

    private void setupViewPagerAndStuff() {
        recipeStepsPagerAdapter = new RecipeStepsPagerAdapter(
                getSupportFragmentManager(), this,
                mComponentQuantityArray[visible_component], mJsonFilenameArray[visible_component]);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(recipeStepsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);

        final FloatingActionButton fab1 = findViewById(R.id.fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = mViewPager.getCurrentItem();
                if (currentItem + 1 < recipeStepsPagerAdapter.getCount())
                    mViewPager.setCurrentItem(currentItem + 1);
            }
        });

        final FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = mViewPager.getCurrentItem();
                if (currentItem - 1 >= 0)
                    mViewPager.setCurrentItem(currentItem - 1);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                fab2.setAlpha(i == 0 ? 0.0f : 1.0f);
                fab2.setClickable(i != 0);
                fab1.setAlpha(i + 1 == recipeStepsPagerAdapter.getCount() ? 0.0f : 1.0f);
                fab1.setClickable(i + 1 != recipeStepsPagerAdapter.getCount());
            }
        });

        setRecipeComponentNameTextView();
    }

    private void setRecipeComponentNameTextView() {
        TextView textView = findViewById(R.id.recipe_component_name);
        textView.setText(mNameIdArray[visible_component]);
    }

    private void consumeIndent() {
        Intent intent = getIntent();
        mComponentQuantityArray = intent.getIntArrayExtra(DISH_COMPONENT_QUANTITIES);
        mJsonFilenameArray = intent.getStringArrayExtra(JSON_FILENAMES);
        mNameIdArray = intent.getIntArrayExtra(NAME_IDS);
        visible_component = intent.getIntExtra(INITIAL_COMPONENT, 0);
        num_components = mNameIdArray.length;
        mLastActiveItemArray = new int[num_components];
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onFlingUp() {
        onFlingUpOrDown(visible_component, num_components - 1, 1);
    }

    @Override
    public void onFlingDown() {
        onFlingUpOrDown(visible_component, 0, -1);
    }

    private void onFlingUpOrDown(int curr_component, int reference_index, int component_delta) {
        if (curr_component == reference_index)
            return;

        int new_component = curr_component + component_delta;

        if (mComponentQuantityArray[new_component] != 0) {
            changeDishComponent(new_component);
        }
        else {
            onFlingUpOrDown(new_component, reference_index, component_delta);
        }
    }

    private void changeDishComponent(int new_component) {
        mLastActiveItemArray[visible_component] = mViewPager.getCurrentItem();
        visible_component = new_component;

        recipeStepsPagerAdapter.updateData(
                mComponentQuantityArray[visible_component], mJsonFilenameArray[visible_component]);

        mViewPager.setCurrentItem(mLastActiveItemArray[visible_component]);

        setRecipeComponentNameTextView();
    }

    static class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        OnVerticalFlingListener onVerticalFlingListener_;

        MyGestureListener(OnVerticalFlingListener onVerticalFlingListener) {
            onVerticalFlingListener_ = onVerticalFlingListener;
        }

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            float dx = event2.getX() - event1.getX();
            float dy = event2.getY() - event1.getY();
            float dx_abs = Math.abs(dx);
            float dy_abs = Math.abs(dy);

            boolean is_horizontal = dx_abs >= dy_abs;
            boolean is_down = dy > 0;

            boolean fling_up = !is_horizontal && !is_down;
            boolean fling_down = !is_horizontal && is_down;

            if (fling_up) {
                onVerticalFlingListener_.onFlingUp();
                return false;
            } else if (fling_down) {
                onVerticalFlingListener_.onFlingDown();
                return false;
            }
            return true;
        }
    }
}