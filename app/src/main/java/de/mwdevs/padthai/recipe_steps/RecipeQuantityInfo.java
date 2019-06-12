package de.mwdevs.padthai.recipe_steps;

class RecipeQuantityInfo {
    int name_id;
    float el;
    int string_id;
    int image_id;

    RecipeQuantityInfo(int name_id, float el, int string_id, int image_id) {
        this.name_id = name_id;
        this.el = el;
        this.string_id = string_id;
        this.image_id = image_id;
    }
}
