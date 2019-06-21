package de.mwdevs.padthai.main;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;

import de.mwdevs.padthai.R;
import de.mwdevs.padthai.main.data.DishInfo;
import de.mwdevs.padthai.main.ui.OnRecipeInteractionListener;

public class DishPagerAdapter extends PagerAdapter {

    private static final long INITIAL_DELAY = 1000;
    private static final long PERIODIC_DELAY = 5000;
    private final OnRecipeInteractionListener mListener;
    private Context mContext;
    private ArrayList<ObjectAnimator> mViewAnimations = new ArrayList<>(DishInfo.values().length);

    public DishPagerAdapter(Context context, OnRecipeInteractionListener listener) {
        mContext = context;
        mListener = listener;

        setupAnimationHandler();
    }

    private void setupAnimationHandler() {
        final Handler handler = new Handler();
        Runnable animationLoop = new Runnable() {
            @Override
            public void run() {
                for (ObjectAnimator anim : mViewAnimations) {
                    anim.start();
                }
                handler.postDelayed(this, PERIODIC_DELAY);
            }
        };
        handler.postDelayed(animationLoop, INITIAL_DELAY);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        DishInfo dishInfo = DishInfo.values()[position];

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View root = inflater.inflate(R.layout.dish_view_pager_content, collection, false);

        ImageView imageView = root.findViewById(R.id.dish_button);
        imageView.setImageResource(dishInfo.getImageResId());
        imageView.setTag(dishInfo);
        setViewListener(imageView);
        setupViewAnimation(imageView);

        collection.addView(root);
        return root;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return DishInfo.values().length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        DishInfo dishInfo = DishInfo.values()[position];
        return mContext.getString(dishInfo.getTitleResId());
    }

    private void setViewListener(@NonNull final View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onRecipeClick((DishInfo) view.getTag());
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    mListener.onRecipeLongClick((DishInfo) view.getTag());
                }
                return true;
            }
        });
    }

    private void setupViewAnimation(View view) {
        final ObjectAnimator scaleUp = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat("scaleX", 1.08f),
                PropertyValuesHolder.ofFloat("scaleY", 1.08f));
        scaleUp.setDuration(400);

        scaleUp.setInterpolator(new AccelerateInterpolator());
        scaleUp.setRepeatMode(ValueAnimator.REVERSE);
        scaleUp.setRepeatCount(1);
        mViewAnimations.add(scaleUp);
    }
}

