package de.mwdevs.padthai;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.Serializable;

import de.mwdevs.padthai.recipe_steps.BaseStepViewModel;
import de.mwdevs.padthai.recipe_steps.RecipeStepsPagerAdapter;

public class RecipeStepsActivity<T extends BaseStepViewModel> extends AppCompatActivity {
    public static final String COMPONENT_QUANTITY = "COMPONENT_QUANTITY";
    public static final String VIEW_MODEL_CLASS = "MODEL_CLASS";

    private int mComponentQuantity = 0;
    private Class<T> mViewModelClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityTransition();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        consumeIndent();
        setupViewPagerAndStuff();
    }

    private void setupActivityTransition() {
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        window.setEnterTransition(new Explode());
        window.setAllowEnterTransitionOverlap(true);
    }

    private void setupViewPagerAndStuff() {
        final RecipeStepsPagerAdapter recipeStepsPagerAdapter = new RecipeStepsPagerAdapter(
                getSupportFragmentManager(), this, mComponentQuantity, mViewModelClass);
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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                fab2.setAlpha(i == 0 ? 0.0f : 1.0f);
                fab2.setClickable(i != 0);
                fab1.setAlpha(i + 1 == recipeStepsPagerAdapter.getCount() ? 0.0f : 1.0f);
                fab1.setClickable(i + 1 != recipeStepsPagerAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void consumeIndent() {
        Intent intent = getIntent();
        mComponentQuantity = intent.getIntExtra(COMPONENT_QUANTITY, 0);
        getViewModelFromSerializable(intent.getSerializableExtra(VIEW_MODEL_CLASS));
    }

    private void getViewModelFromSerializable(Serializable serializable) {
        @SuppressWarnings("unchecked") Class<T> modelClass = (Class<T>) serializable;
        if (modelClass != null) {
            mViewModelClass = modelClass;
        } else {
            throw new IllegalArgumentException("Missing intent extra.");
        }
    }
}