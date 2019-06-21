package de.mwdevs.padthai.main.ui;

import de.mwdevs.padthai.main.data.DishInfo;

public interface OnDishInteractionListener {
    void onDishClick(DishInfo dishInfo);

    void onDishLongClick(DishInfo dishInfo);
}
