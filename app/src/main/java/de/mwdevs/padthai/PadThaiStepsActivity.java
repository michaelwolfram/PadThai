package de.mwdevs.padthai;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.mwdevs.padthai.recipe_steps.pad_thai.PTStepsPagerAdapter;

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

        setContentView(R.layout.activity_recipe_steps);
        final PTStepsPagerAdapter pTStepsPagerAdapter = new PTStepsPagerAdapter(this,
                getSupportFragmentManager(), pad_thai_quantity);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pTStepsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem + 1 < pTStepsPagerAdapter.getCount())
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

    private void consumeIndent() {
        Intent intent = getIntent();
        pad_thai_quantity = intent.getIntExtra(MainActivity.PAD_THAI_QUANTITY, 0);
    }
}