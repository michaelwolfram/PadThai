package de.mwdevs.padthai;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import de.mwdevs.padthai.recipe_steps.ChiliPasteStepViewModel;
import de.mwdevs.padthai.recipe_steps.PadThaiStepViewModel;
import de.mwdevs.padthai.recipe_steps.PeanutSauceStepViewModel;
import de.mwdevs.padthai.shopping_list.ShoppingListContent;

import static java.lang.Math.max;

public class MainActivity extends AppCompatActivity {
    public static final String PASTE_QUANTITY = "PASTE_QUANTITY";
    public static final String SOSSE_QUANTITY = "SOSSE_QUANTITY";
    public static final String PAD_THAI_QUANTITY = "PAD_THAI_QUANTITY";
    private TextView paste_quantity_text;
    private TextView sosse_quantity_text;
    private TextView pad_thai_quantity_text;
    private ImageView padThaiImage;
    private ShowcaseView showcaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShoppingListContent.initItemPropertyMap(this);

        setupToolbar();
        setupComponentRows();
        setupImageViewWithAnimation();
        setupShowcaseViewAndImageViewOnClickListener();
        setupRecipeStepsButtons();
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
            public void onClick(@NonNull View v) {
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

    private void setPadThaiImageOnClickListener(@NonNull ImageView padThaiImage) {
        padThaiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int paste_quantity = getIntFromTextView(paste_quantity_text);
                int sosse_quantity = getIntFromTextView(sosse_quantity_text);
                int pad_thai_quantity = getIntFromTextView(pad_thai_quantity_text);
                if (paste_quantity > 0 || sosse_quantity > 0 || pad_thai_quantity > 0)
                    openShoppingCart(paste_quantity, sosse_quantity, pad_thai_quantity);
                else
                    Snackbar.make(view, R.string.no_component_selected, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void openShoppingCart(int paste_quantity, int sosse_quantity, int pad_thai_quantity) {
        ShoppingListContent.resetItems();

        Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
        intent.putExtra(PASTE_QUANTITY, paste_quantity);
        intent.putExtra(SOSSE_QUANTITY, sosse_quantity);
        intent.putExtra(PAD_THAI_QUANTITY, pad_thai_quantity);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    private int getIntFromTextView(@NonNull TextView textView) {
        return Integer.parseInt(textView.getText().toString());
    }

    private void setupRecipeStepsButtons() {
        setupRecipeStepsButton(R.id.first_component_row, paste_quantity_text, ChiliPasteStepViewModel.class);
        setupRecipeStepsButton(R.id.second_component_row, sosse_quantity_text, PeanutSauceStepViewModel.class);
        setupRecipeStepsButton(R.id.third_component_row, pad_thai_quantity_text, PadThaiStepViewModel.class);
    }

    private void setupRecipeStepsButton(int view_id, final TextView quantityTextView, final Class viewModelClass) {
        TextView textView = findViewById(view_id).findViewById(R.id.dish_component_name);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = getIntFromTextView(quantityTextView);
                if (quantity > 0)
                    startRecipeStepsActivity(quantity, viewModelClass);
                else
                    Snackbar.make(v, R.string.no_component_selected, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void startRecipeStepsActivity(int quantity, Class viewModelClass) {
        Intent intent = new Intent(MainActivity.this, RecipeStepsActivity.class);
        intent.putExtra(RecipeStepsActivity.COMPONENT_QUANTITY, quantity);
        intent.putExtra(RecipeStepsActivity.VIEW_MODEL_CLASS, viewModelClass);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
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
        int paste_quantity_value = preferences.getInt(PASTE_QUANTITY, 0);
        int sosse_quantity_value = preferences.getInt(SOSSE_QUANTITY, 0);
        int pad_thai_quantity_value = preferences.getInt(PAD_THAI_QUANTITY, 0);
        paste_quantity_text.setText(getString(R.string.placeholder_d, paste_quantity_value));
        sosse_quantity_text.setText(getString(R.string.placeholder_d, sosse_quantity_value));
        pad_thai_quantity_text.setText(getString(R.string.placeholder_d, pad_thai_quantity_value));
    }

    private void saveComponentQuantities() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PASTE_QUANTITY, getIntFromTextView(paste_quantity_text));
        editor.putInt(SOSSE_QUANTITY, getIntFromTextView(sosse_quantity_text));
        editor.putInt(PAD_THAI_QUANTITY, getIntFromTextView(pad_thai_quantity_text));
        editor.apply();
    }
}
