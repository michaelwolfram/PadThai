package de.mwdevs.padthai;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

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
}
