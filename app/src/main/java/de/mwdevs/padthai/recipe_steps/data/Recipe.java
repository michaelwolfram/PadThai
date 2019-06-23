package de.mwdevs.padthai.recipe_steps.data;

import java.util.ArrayList;

public class Recipe {
    private final int name_id;
    private final int step_count;
    private final ArrayList<Integer> text_1;
    private final ArrayList<Integer> text_2;
    private final ArrayList<ArrayList<RecipeQuantityInfo>> steps;

    public Recipe(int name, int step_count, ArrayList<Integer> text_1, ArrayList<Integer> text_2,
                  ArrayList<ArrayList<RecipeQuantityInfo>> steps) {
        this.name_id = name;
        this.step_count = step_count;
        this.text_1 = text_1;
        this.text_2 = text_2;
        this.steps = steps;
    }

    public int getName() {
        return name_id;
    }

    public int getStepCount() {
        return step_count;
    }

    public ArrayList<Integer> getText_1() {
        return text_1;
    }

    public ArrayList<Integer> getText_2() {
        return text_2;
    }

    public ArrayList<ArrayList<RecipeQuantityInfo>> getSteps() {
        return steps;
    }
}
