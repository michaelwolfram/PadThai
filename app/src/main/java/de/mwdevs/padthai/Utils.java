package de.mwdevs.padthai;

import android.app.Activity;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.github.amlcurran.showcaseview.ShowcaseView;

class Utils {
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
}
