package mwdevs.de.padthai;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static java.lang.Math.max;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton paste_button_down = findViewById(R.id.paste_down);
        paste_button_down.setTag(-1);
        ImageButton paste_button_up = findViewById(R.id.paste_up);
        paste_button_up.setTag(+1);
        final TextView paste_text_quantity = findViewById(R.id.paste_quantity);
        View.OnClickListener paste_ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_quantity = Integer.parseInt(paste_text_quantity.getText().toString());
                int new_quantity = max(0,current_quantity + Integer.parseInt(v.getTag().toString()));
                paste_text_quantity.setText(""+new_quantity);
            }
        };
        paste_button_down.setOnClickListener(paste_ocl);
        paste_button_up.setOnClickListener(paste_ocl);

        ImageButton sosse_button_down = findViewById(R.id.sosse_down);
        sosse_button_down.setTag(-1);
        ImageButton sosse_button_up = findViewById(R.id.sosse_up);
        sosse_button_up.setTag(+1);
        final TextView sosse_text_quantity = findViewById(R.id.sosse_quantity);
        View.OnClickListener sosse_ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_quantity = Integer.parseInt(sosse_text_quantity.getText().toString());
                int new_quantity = max(0,current_quantity + Integer.parseInt(v.getTag().toString()));
                sosse_text_quantity.setText(""+new_quantity);
            }
        };
        sosse_button_down.setOnClickListener(sosse_ocl);
        sosse_button_up.setOnClickListener(sosse_ocl);

        ImageButton padthai_button_down = findViewById(R.id.padthai_down);
        padthai_button_down.setTag(-1);
        ImageButton padthai_button_up = findViewById(R.id.padthai_up);
        padthai_button_up.setTag(+1);
        final TextView padthai_text_quantity = findViewById(R.id.padthai_quantity);
        View.OnClickListener padthai_ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_quantity = Integer.parseInt(padthai_text_quantity.getText().toString());
                int new_quantity = max(0,current_quantity + Integer.parseInt(v.getTag().toString()));
                padthai_text_quantity.setText(""+new_quantity);
            }
        };
        padthai_button_down.setOnClickListener(padthai_ocl);
        padthai_button_up.setOnClickListener(padthai_ocl);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingListContent.resetItems();

                Intent intent = new Intent(MainActivity.this, ShoppingCart.class);
                intent.putExtra(ShoppingListFragment.PASTE_QUANTITY, Integer.parseInt(paste_text_quantity.getText().toString()));
                intent.putExtra(ShoppingListFragment.SOSSE_QUANTITY, Integer.parseInt(sosse_text_quantity.getText().toString()));
                intent.putExtra(ShoppingListFragment.PADTHAI_QUANTITY, Integer.parseInt(padthai_text_quantity.getText().toString()));
                startActivity(intent);
            }
        });
    }
}
