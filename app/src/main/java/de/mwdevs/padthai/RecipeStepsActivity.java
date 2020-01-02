package de.mwdevs.padthai;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import de.mwdevs.padthai.recipe_steps.RecipeStepsPagerAdapter;

public class RecipeStepsActivity extends AppCompatActivity {

    public static final String DISH_COMPONENT_QUANTITIES = "DISH_COMPONENT_QUANTITIES";
    public static final String JSON_FILENAMES = "JSON_FILENAMES";
    public static final String INITIAL_COMPONENT = "INITIAL_COMPONENT";

    private RecipeStepsPagerAdapter recipeStepsPagerAdapter;

    private int current_component = 0;
    private int num_components;
    private int[] mComponentQuantityArray;
    private String[] mJsonFilenameArray;
    private int[] mLastActiveItemArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        consumeIndent();
        setupViewPagerAndStuff();
    }

    private void setupViewPagerAndStuff() {
        recipeStepsPagerAdapter = new RecipeStepsPagerAdapter(
                getSupportFragmentManager(), this,
                mComponentQuantityArray[current_component], mJsonFilenameArray[current_component]);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(recipeStepsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        final FloatingActionButton fab1 = findViewById(R.id.fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem + 1 < recipeStepsPagerAdapter.getCount())
                    viewPager.setCurrentItem(currentItem + 1);
            }
        });

        final FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem - 1 >= 0)
                    viewPager.setCurrentItem(currentItem - 1);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                fab2.setAlpha(i == 0 ? 0.0f : 1.0f);
                fab2.setClickable(i != 0);
                fab1.setAlpha(i + 1 == recipeStepsPagerAdapter.getCount() ? 0.0f : 1.0f);
                fab1.setClickable(i + 1 != recipeStepsPagerAdapter.getCount());
            }
        });
    }

    private void consumeIndent() {
        Intent intent = getIntent();
        mComponentQuantityArray = intent.getIntArrayExtra(DISH_COMPONENT_QUANTITIES);
        mJsonFilenameArray = intent.getStringArrayExtra(JSON_FILENAMES);
        current_component = intent.getIntExtra(INITIAL_COMPONENT, 0);
        num_components = mComponentQuantityArray.length;
        mLastActiveItemArray = new int[num_components];
    }
}