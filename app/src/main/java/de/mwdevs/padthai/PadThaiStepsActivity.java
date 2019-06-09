package de.mwdevs.padthai;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.mwdevs.padthai.recipe_steps.PadThaiStepViewModel;
import de.mwdevs.padthai.recipe_steps.RecipeStepsPagerAdapter;

public class PadThaiStepsActivity extends AppCompatActivity {

    private int pad_thai_quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            pad_thai_quantity = savedInstanceState.getInt(MainActivity.PAD_THAI_QUANTITY);
        } else {
            consumeIndent();
        }
        setupViewPagerAndStuff();
    }

    private void setupViewPagerAndStuff() {
        setContentView(R.layout.activity_recipe_steps);
        final RecipeStepsPagerAdapter pTStepsPagerAdapter = new RecipeStepsPagerAdapter(getSupportFragmentManager(), this,
                pad_thai_quantity, PadThaiStepViewModel.class);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pTStepsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        final FloatingActionButton fab1 = findViewById(R.id.fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem + 1 < pTStepsPagerAdapter.getCount())
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
                fab1.setAlpha(i+1 == pTStepsPagerAdapter.getCount() ? 0.0f : 1.0f);
                fab1.setClickable(i+1 != pTStepsPagerAdapter.getCount());
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void consumeIndent() {
        Intent intent = getIntent();
        pad_thai_quantity = intent.getIntExtra(MainActivity.PAD_THAI_QUANTITY, 0);
    }
}