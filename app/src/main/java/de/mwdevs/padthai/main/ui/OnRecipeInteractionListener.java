package de.mwdevs.padthai.main.ui;

import de.mwdevs.padthai.main.data.DishInfo;

public interface OnRecipeInteractionListener {
    void onRecipeClick(DishInfo dishInfo);

    void onRecipeLongClick(DishInfo dishInfo);
}
