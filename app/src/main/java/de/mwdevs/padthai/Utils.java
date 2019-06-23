package de.mwdevs.padthai;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.github.amlcurran.showcaseview.ShowcaseView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import de.mwdevs.padthai.recipe_steps.data.Recipe;
import de.mwdevs.padthai.recipe_steps.data.RecipeQuantityInfo;

public class Utils {
    @SuppressWarnings("SameParameterValue")
    static int convertDpToPixel(Resources resources, float dp) {
        return Math.round(dp *
                ((float) resources.getDisplayMetrics().densityDpi
                        / (float) DisplayMetrics.DENSITY_DEFAULT));
    }

    static int getDisplayWidth(WindowManager windowManager) {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    static ShowcaseView.Builder getInitializedShowcaseViewBuilder(Activity activity, long shotId) {
        return getInitializedShowcaseViewBuilder(activity)
                .singleShot(shotId);
    }

    @SuppressWarnings("WeakerAccess")
    static ShowcaseView.Builder getInitializedShowcaseViewBuilder(Activity activity) {
        return new ShowcaseView.Builder(activity)
                .withMaterialShowcase()
                .setStyle(R.style.PadThaiShowcaseView)
                .setFadeInDurations(800);
    }

    public static Recipe loadRecipeFromJSON(Context context, String file_name) {
        try {
            JSONObject json_recipe = new JSONObject(loadJSONFromAsset(context, file_name));

            final int name_id = getRecipeName(context, json_recipe);
            final int step_count = getRecipeStepCount(json_recipe);
            final ArrayList<Integer> text_1 = getStringResourceArray(context, json_recipe, "text_1", step_count);
            final ArrayList<Integer> text_2 = getStringResourceArray(context, json_recipe, "text_2", step_count);
            final ArrayList<ArrayList<RecipeQuantityInfo>> steps =
                    getRecipeSteps(context, json_recipe, step_count);

            return new Recipe(name_id, step_count, text_1, text_2, steps);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int getRecipeName(Context context, JSONObject json_recipe) throws JSONException {
        String name_id_string = json_recipe.getString("name_id");
        return getResourceId(context, name_id_string, "string");
    }

    private static int getRecipeStepCount(JSONObject json_recipe) throws JSONException {
        return json_recipe.getInt("step_count");
    }

    private static ArrayList<Integer> getStringResourceArray(Context context, JSONObject parent,
                                                             String array_name, int array_size) throws JSONException {
        JSONArray json_text1_array = parent.getJSONArray(array_name);
        ArrayList<Integer> array = new ArrayList<>(array_size);

        for (int i = 0; i < json_text1_array.length(); i++) {
            String name_id_string = json_text1_array.getString(i);
            int name_id = getResourceId(context, name_id_string, "string");
            array.add(name_id);
        }
        return array;
    }

    private static ArrayList<ArrayList<RecipeQuantityInfo>> getRecipeSteps(Context context,
                                                                           JSONObject json_recipe,
                                                                           int step_count)
            throws JSONException {
        JSONArray json_steps_array = json_recipe.getJSONArray("steps");
        ArrayList<ArrayList<RecipeQuantityInfo>> steps = new ArrayList<>(step_count);

        for (int i = 0; i < json_steps_array.length(); i++) {
            JSONArray json_step_array = json_steps_array.getJSONArray(i);
            ArrayList<RecipeQuantityInfo> step = new ArrayList<>(json_steps_array.length());

            for (int j = 0; j < json_step_array.length(); j++) {
                JSONObject json_quantity_info = json_step_array.getJSONObject(j);
                RecipeQuantityInfo recipeQuantityInfo =
                        createRecipeQuantityInfo(context, json_quantity_info);

                step.add(recipeQuantityInfo);
            }

            steps.add(step);
        }
        return steps;
    }

    private static String loadJSONFromAsset(Context context, String file_name) {
        String json;
        try {
            InputStream is = context.getAssets().open(file_name);
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private static RecipeQuantityInfo createRecipeQuantityInfo(Context context, JSONObject step)
            throws JSONException {

        return createRecipeQuantityInfo(context,
                step.getString("name_id"),
                (float) step.getDouble("el"),
                step.getString("string_id"),
                step.getString("image_id"));
    }

    private static RecipeQuantityInfo createRecipeQuantityInfo(Context context,
                                                               String nameIdName, float el,
                                                               String elIdName, String imageIdName) {
        return new RecipeQuantityInfo(
                getResourceId(context, nameIdName, "string"),
                el,
                getResourceId(context, elIdName, "string"),
                getResourceId(context, imageIdName, "mipmap")
        );
    }

    private static int getResourceId(Context context, String name, String defType) {
        return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }
//    static int getResourceId(Context context, String typedName) {
//        return getResourceId(context, typedName, null);
//    }
}
