package de.mwdevs.padthai.main.ui;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

public class DishPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View view, float position) {
        if (position <= -1.0F || position >= 1.0F) {
            view.setTranslationX(view.getWidth() * position);
            view.setAlpha(0.0F);
        } else if (position == 0.0F) {
            view.setTranslationX(view.getWidth() * position);
            view.setAlpha(1.0F);
        } else {
            // position is between -1.0F & 0.0F OR 0.0F & 1.0F
            view.setTranslationX(view.getWidth() * -position * 0.55f);
            view.setAlpha(1.0F - Math.abs(position));
        }
    }
}
