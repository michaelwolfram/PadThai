package de.mwdevs.padthai;

public interface OnRecipeInteractionListener {
    void onRecipeClick(DishInfo dishInfo);

    void onRecipeLongClick(DishInfo dishInfo);
}
