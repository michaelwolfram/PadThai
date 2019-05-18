package mwdevs.de.padthai;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import static java.lang.Math.max;

public class MainActivity extends AppCompatActivity {
    private TextView paste_text_quantity;
    private TextView sosse_text_quantity;
    private TextView padthai_text_quantity;
    private ShowcaseView showcaseView;

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
        paste_text_quantity = findViewById(R.id.paste_quantity);
        View.OnClickListener paste_ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_quantity = Integer.parseInt(paste_text_quantity.getText().toString());
                int new_quantity = max(0, current_quantity + Integer.parseInt(v.getTag().toString()));
                paste_text_quantity.setText("" + new_quantity);
            }
        };
        paste_button_down.setOnClickListener(paste_ocl);
        paste_button_up.setOnClickListener(paste_ocl);

        ImageButton sosse_button_down = findViewById(R.id.sosse_down);
        sosse_button_down.setTag(-1);
        ImageButton sosse_button_up = findViewById(R.id.sosse_up);
        sosse_button_up.setTag(+1);
        sosse_text_quantity = findViewById(R.id.sosse_quantity);
        View.OnClickListener sosse_ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_quantity = Integer.parseInt(sosse_text_quantity.getText().toString());
                int new_quantity = max(0, current_quantity + Integer.parseInt(v.getTag().toString()));
                sosse_text_quantity.setText("" + new_quantity);
            }
        };
        sosse_button_down.setOnClickListener(sosse_ocl);
        sosse_button_up.setOnClickListener(sosse_ocl);

        ImageButton padthai_button_down = findViewById(R.id.padthai_down);
        padthai_button_down.setTag(-1);
        ImageButton padthai_button_up = findViewById(R.id.padthai_up);
        padthai_button_up.setTag(+1);
        padthai_text_quantity = findViewById(R.id.padthai_quantity);
        View.OnClickListener padthai_ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_quantity = Integer.parseInt(padthai_text_quantity.getText().toString());
                int new_quantity = max(0, current_quantity + Integer.parseInt(v.getTag().toString()));
                padthai_text_quantity.setText("" + new_quantity);
            }
        };
        padthai_button_down.setOnClickListener(padthai_ocl);
        padthai_button_up.setOnClickListener(padthai_ocl);

        final ImageView padThaiImage = findViewById(R.id.padThaiImage);

        final ObjectAnimator scaleUp = ObjectAnimator.ofPropertyValuesHolder(
                padThaiImage,
                PropertyValuesHolder.ofFloat("scaleX", 1.08f),
                PropertyValuesHolder.ofFloat("scaleY", 1.08f));
        scaleUp.setDuration(400);

        scaleUp.setInterpolator(new AccelerateInterpolator());
        scaleUp.setRepeatMode(ValueAnimator.REVERSE);
        scaleUp.setRepeatCount(1);
        final Handler handler = new Handler();
        Runnable animationLoop = new Runnable() {
            @Override
            public void run() {
                scaleUp.start();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(animationLoop, 1000);

        showcaseView = new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.PadThaiShowcaseView)
                .singleShot(11)
                .setTarget(new ViewTarget(padThaiImage))
                .setContentTitle(R.string.click_me)
                .setContentText(R.string.then_shopping_list_shows)
                .setFadeInDurations(800)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPadThaiImageOnClickListener(padThaiImage);
                        showcaseView.hide();
                    }
                })
                .build();

        if (!showcaseView.isShowing())
            setPadThaiImageOnClickListener(padThaiImage);
    }

    private void setPadThaiImageOnClickListener(ImageView padThaiImage) {
        padThaiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingListContent.resetItems();

                Intent intent = new Intent(MainActivity.this, ShoppingCart.class);
                intent.putExtra(ShoppingCart.PASTE_QUANTITY, getIntFromTextView(paste_text_quantity));
                intent.putExtra(ShoppingCart.SOSSE_QUANTITY, getIntFromTextView(sosse_text_quantity));
                intent.putExtra(ShoppingCart.PADTHAI_QUANTITY, getIntFromTextView(padthai_text_quantity));
                startActivity(intent);
            }
        });
    }

    private int getIntFromTextView(TextView textView) {
        return Integer.parseInt(textView.getText().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        paste_text_quantity.setText("" + preferences.getInt(ShoppingCart.PASTE_QUANTITY, 0));
        sosse_text_quantity.setText("" + preferences.getInt(ShoppingCart.SOSSE_QUANTITY, 0));
        padthai_text_quantity.setText("" + preferences.getInt(ShoppingCart.PADTHAI_QUANTITY, 0));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ShoppingCart.PASTE_QUANTITY, getIntFromTextView(paste_text_quantity));
        editor.putInt(ShoppingCart.SOSSE_QUANTITY, getIntFromTextView(sosse_text_quantity));
        editor.putInt(ShoppingCart.PADTHAI_QUANTITY, getIntFromTextView(padthai_text_quantity));
        editor.commit();
    }
}
