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
    private TextView paste_quantity_text;
    private TextView sosse_quantity_text;
    private TextView pad_thai_quantity_text;
    private ImageView padThaiImage;
    private ShowcaseView showcaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupComponentRows();
        setupImageViewWithAnimation();
        setupShowcaseViewAndImageViewOnClickListener();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupComponentRows() {
        paste_quantity_text = findViewById(R.id.first_component_row).findViewById(R.id.dish_component_quantity);
        sosse_quantity_text = findViewById(R.id.second_component_row).findViewById(R.id.dish_component_quantity);
        pad_thai_quantity_text = findViewById(R.id.third_component_row).findViewById(R.id.dish_component_quantity);
        setupComponentRow(R.id.first_component_row, R.string.paste, paste_quantity_text);
        setupComponentRow(R.id.second_component_row, R.string.sosse, sosse_quantity_text);
        setupComponentRow(R.id.third_component_row, R.string.pad_thai, pad_thai_quantity_text);
    }

    private void setupComponentRow(int componentRowId, int component_name, final TextView quantity_view) {
        View componentRow = findViewById(componentRowId);
        ((TextView) componentRow.findViewById(R.id.dish_component_name)).setText(component_name);
        ImageButton button_decrease = componentRow.findViewById(R.id.dish_component_decrease);
        ImageButton button_increase = componentRow.findViewById(R.id.dish_component_increase);
        View.OnClickListener component_ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_quantity = Integer.parseInt(quantity_view.getText().toString());
                int new_quantity = max(0, current_quantity + Integer.parseInt(v.getTag().toString()));
                quantity_view.setText(getString(R.string.placeholder_d, new_quantity));
            }
        };
        button_decrease.setOnClickListener(component_ocl);
        button_increase.setOnClickListener(component_ocl);
    }

    private void setupImageViewWithAnimation() {
        padThaiImage = findViewById(R.id.padThaiImage);

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
    }

    private void setupShowcaseViewAndImageViewOnClickListener() {
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
                intent.putExtra(ShoppingCart.PASTE_QUANTITY, getIntFromTextView(paste_quantity_text));
                intent.putExtra(ShoppingCart.SOSSE_QUANTITY, getIntFromTextView(sosse_quantity_text));
                intent.putExtra(ShoppingCart.PAD_THAI_QUANTITY, getIntFromTextView(pad_thai_quantity_text));
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
        loadComponentQuantities();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveComponentQuantities();
    }

    private void loadComponentQuantities() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int paste_quantity_value = preferences.getInt(ShoppingCart.PASTE_QUANTITY, 0);
        int sosse_quantity_value = preferences.getInt(ShoppingCart.SOSSE_QUANTITY, 0);
        int pad_thai_quantity_value = preferences.getInt(ShoppingCart.PAD_THAI_QUANTITY, 0);
        paste_quantity_text.setText(getString(R.string.placeholder_d, paste_quantity_value));
        sosse_quantity_text.setText(getString(R.string.placeholder_d, sosse_quantity_value));
        pad_thai_quantity_text.setText(getString(R.string.placeholder_d, pad_thai_quantity_value));
    }

    private void saveComponentQuantities() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ShoppingCart.PASTE_QUANTITY, getIntFromTextView(paste_quantity_text));
        editor.putInt(ShoppingCart.SOSSE_QUANTITY, getIntFromTextView(sosse_quantity_text));
        editor.putInt(ShoppingCart.PAD_THAI_QUANTITY, getIntFromTextView(pad_thai_quantity_text));
        editor.apply();
    }
}
