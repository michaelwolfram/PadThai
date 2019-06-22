package de.mwdevs.padthai;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;

import de.mwdevs.padthai.main.DishPagerAdapter;
import de.mwdevs.padthai.main.data.ComponentQuantityDataModel;
import de.mwdevs.padthai.main.data.DishComponentInfo;
import de.mwdevs.padthai.main.data.DishInfo;
import de.mwdevs.padthai.main.ui.DishPageTransformer;
import de.mwdevs.padthai.main.ui.OnDishInteractionListener;
import de.mwdevs.padthai.shopping_list.data.ShoppingListContent;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MainActivity extends AppCompatActivity implements OnDishInteractionListener {
    public static final String DISH_INDEX = "DISH_INDEX";
    public static final String COMPONENT_QUANTITIES = "COMPONENT_QUANTITIES";
    private static final int MAXIMUM_COMPONENT_ROWS = 3;
    private ArrayList<View> mComponentRowViews = new ArrayList<>(MAXIMUM_COMPONENT_ROWS);
    private ComponentQuantityDataModel componentQuantityDataModel = new ComponentQuantityDataModel(MAXIMUM_COMPONENT_ROWS);
    private ViewPager dishViewPager;
    private ShowcaseView showcaseView;
    private boolean allowDishOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShoppingListContent.initItemPropertyMap();

        setupToolbar();
        setupShowcaseView();
        setupDishViewPager();
        initComponentRows();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupShowcaseView() {
        final View view_to_be_focused = findViewById(R.id.pager_image_view_position);
        showcaseView = new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.PadThaiShowcaseView)
//                .singleShot(11) // TODO: 21.06.19 remove comment here
                .setTarget(new ViewTarget(view_to_be_focused))
                .setContentTitle(R.string.shopping_list)
                .setContentText(R.string.click_me_then_shopping_list_shows)
                .setFadeInDurations(800)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showcaseView.setContentTitle(getString(R.string.recipe_steps));
                        showcaseView.setContentText(getString(R.string.click_me_then_recipe_steps_show));
                        View showcase_focus_2 = findViewById(R.id.showcase_focus_2);
                        showcaseView.setShowcase(new ViewTarget(showcase_focus_2), true);
                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                        showcaseView.overrideButtonClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showcaseView.setContentTitle(getString(R.string.several_dishes));
                                showcaseView.setContentText(getString(R.string.swipe_through_dishes));
                                showcaseView.setShowcase(new ViewTarget(view_to_be_focused), true);
                                showcaseView.overrideButtonClick(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        allowDishOnClickListener = true;
                                        showcaseView.hide();
                                    }
                                });
                            }
                        });
                        final View padThaiText = findViewById(R.id.third_component_row).findViewById(R.id.dish_component_name);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ObjectAnimator scaleUpSet =
                                        (ObjectAnimator) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.showcase_scale_up);
                                scaleUpSet.setTarget(padThaiText);
                                scaleUpSet.start();
                            }
                        }, 666);
                    }
                })
                .build();

        if (!showcaseView.isShowing())
            allowDishOnClickListener = true;
    }

    private void setupDishViewPager() {
        dishViewPager = findViewById(R.id.recipe_view_pager);
        dishViewPager.setAdapter(new DishPagerAdapter(this, this));
        dishViewPager.setPageTransformer(false, new DishPageTransformer());
        dishViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                updateViews(i);
            }
        });
    }

    private void initComponentRows() {
        mComponentRowViews.add(0, findViewById(R.id.first_component_row));
        mComponentRowViews.add(1, findViewById(R.id.second_component_row));
        mComponentRowViews.add(2, findViewById(R.id.third_component_row));
    }

    private void updateViews(int dish_id) {
        DishInfo dishInfo = DishInfo.values()[dish_id];
        ((TextView) findViewById(R.id.header)).setText(dishInfo.getTitleResId());

        int first_invisible_row_idx = dishInfo.getNumDishComponents();
        for (int i = 0; i < first_invisible_row_idx; i++) {
            setupComponentRow(dishInfo, i);
        }
        for (int i = mComponentRowViews.size() - 1; i >= first_invisible_row_idx; i--) {
            hideComponentRow(i);
        }
    }

    private void hideComponentRow(int row) {
        toggleComponentRow(row, false);
    }

    private void setupComponentRow(final DishInfo dishInfo, final int row) {
        toggleComponentRow(row, true);
        setQuantityText(row, componentQuantityDataModel.getQuantity(dishInfo, row));
        setupChangeButtons(dishInfo, row);
        setupRecipeStepsButton(dishInfo, row);
    }

    private void setupChangeButtons(final DishInfo dishInfo, final int row) {
        View componentRowView = mComponentRowViews.get(row);
        ImageButton button_decrease = componentRowView.findViewById(R.id.dish_component_decrease);
        ImageButton button_increase = componentRowView.findViewById(R.id.dish_component_increase);
        View.OnClickListener component_ocl = new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                final int min_value = 0;
                final int max_value = 99;
                int current_quantity = componentQuantityDataModel.getQuantity(dishInfo, row);
                int view_int_tag = Integer.parseInt(v.getTag().toString());
                int new_quantity = min(max_value, max(min_value, current_quantity + view_int_tag));

                if (new_quantity != current_quantity) {
                    componentQuantityDataModel.setQuantity(dishInfo, row, new_quantity);
                    setQuantityText(row, new_quantity);
                    ObjectAnimator scaleUpSet =
                            (ObjectAnimator) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.change_button_scale_up);
                    scaleUpSet.setTarget(v);
                    scaleUpSet.start();
                }
            }
        };
        button_decrease.setOnClickListener(component_ocl);
        button_increase.setOnClickListener(component_ocl);
    }

    private void setQuantityText(int row, int componentQuantity) {
        View componentRowView = mComponentRowViews.get(row);
        TextView quantityTextView = componentRowView.findViewById(R.id.dish_component_quantity);
        quantityTextView.setText(getString(R.string.placeholder_d, componentQuantity));
    }

    private void setupRecipeStepsButton(final DishInfo dishInfo, final int row) {
        View componentRowView = mComponentRowViews.get(row);
        final DishComponentInfo dishComponentInfo = dishInfo.getDishComponentInfo(row);
        TextView textView = componentRowView.findViewById(R.id.dish_component_name);
        textView.setText(dishComponentInfo.name_id);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allowDishOnClickListener)
                    return;

                // TODO: 16.06.19 take care of this when more dishes are added
                if (dishInfo.getTitleResId() != R.string.pad_thai) {
                    Snackbar.make(dishViewPager, R.string.dish_not_available_yet, Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (componentQuantityDataModel.hasValue(dishInfo, row))
                    startRecipeStepsActivity(componentQuantityDataModel.getQuantity(dishInfo, row),
                            dishComponentInfo.viewModelClass);
                else
                    Snackbar.make(v, R.string.no_component_selected, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void startRecipeStepsActivity(int component_quantity, Class viewModelClass) {
        Intent intent = new Intent(MainActivity.this, RecipeStepsActivity.class);
        intent.putExtra(RecipeStepsActivity.COMPONENT_QUANTITY, component_quantity);
        intent.putExtra(RecipeStepsActivity.VIEW_MODEL_CLASS, viewModelClass);
        startActivity(intent);
    }

    private void toggleComponentRow(int row, boolean enable) {
        View componentRowView = mComponentRowViews.get(row);
        componentRowView.setAlpha(enable ? 1.0f : 0.0f);
        componentRowView.findViewById(R.id.dish_component_name).setClickable(enable);
        componentRowView.findViewById(R.id.dish_component_decrease).setClickable(enable);
        componentRowView.findViewById(R.id.dish_component_increase).setClickable(enable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFromPreferences();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveToPreferences();
    }

    private void loadFromPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String quantities = preferences.getString(COMPONENT_QUANTITIES, "[]");
        componentQuantityDataModel.fromString(quantities);

        int previous_item = preferences.getInt(DISH_INDEX, 0);
        int current_item = dishViewPager.getCurrentItem();
        dishViewPager.setCurrentItem(previous_item);

        if (previous_item == current_item)
            // this extra call is needed since the pager listener is not called in this case.
            updateViews(previous_item);
    }

    private void saveToPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(DISH_INDEX, dishViewPager.getCurrentItem());
        editor.putString(COMPONENT_QUANTITIES, componentQuantityDataModel.toString());

        editor.apply();
    }

    @Override
    public void onDishClick(DishInfo dishInfo) {
        if (!allowDishOnClickListener)
            return;

        // TODO: 16.06.19 take care of this when more dishes are added
        if (dishInfo.getTitleResId() != R.string.pad_thai) {
            Snackbar.make(dishViewPager, R.string.dish_not_available_yet, Snackbar.LENGTH_LONG).show();
            return;
        }

        if (componentQuantityDataModel.hasValues(dishInfo))
            openShoppingCart(componentQuantityDataModel.getQuantities(dishInfo));
        else
            Snackbar.make(dishViewPager, R.string.no_component_selected, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDishLongClick(DishInfo dishInfo) {

    }

    private void openShoppingCart(int[] quantities) {
        ShoppingListContent.resetItems();

        Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
        intent.putExtra(COMPONENT_QUANTITIES, quantities);
        startActivity(intent);
    }


}
