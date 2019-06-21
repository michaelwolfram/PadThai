package de.mwdevs.padthai.recipe_steps.data;

public class RecipeQuantityInfo {
    public int name_id;
    public float el;
    public int string_id;
    public int image_id;

    RecipeQuantityInfo(int name_id, float el, int string_id, int image_id) {
        this.name_id = name_id;
        this.el = el;
        this.string_id = string_id;
        this.image_id = image_id;
    }
}
