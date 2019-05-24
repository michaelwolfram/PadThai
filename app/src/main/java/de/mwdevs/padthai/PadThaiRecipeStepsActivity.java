package de.mwdevs.padthai;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.mwdevs.padthai.ui.recipe_steps.SectionsPagerAdapter;

public class PadThaiRecipeStepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int quantity = 0;
        if (savedInstanceState != null) {
            quantity = savedInstanceState.getInt(MainActivity.PAD_THAI_QUANTITY);
        }

        setContentView(R.layout.activity_recipe_steps);
        final SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), quantity);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem + 1 < sectionsPagerAdapter.getCount())
                    viewPager.setCurrentItem(currentItem + 1);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem - 1 >= 0)
                    viewPager.setCurrentItem(currentItem - 1);
            }
        });
    }
}