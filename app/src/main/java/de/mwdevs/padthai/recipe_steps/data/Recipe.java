package de.mwdevs.padthai.recipe_steps.data;

import java.util.ArrayList;

public class Recipe {
    private final int name_id;
//    private final int step_count;
    private final ArrayList<Integer> tab_titles;
    private final ArrayList<Integer> text_1;
    private final ArrayList<Integer> text_2;
    private final ArrayList<ArrayList<RecipeQuantityInfo>> steps;

    public Recipe(int name, ArrayList<Integer> tab_titles,
                  ArrayList<Integer> text_1, ArrayList<Integer> text_2,
                  ArrayList<ArrayList<RecipeQuantityInfo>> steps) {
        this.name_id = name;
//        this.step_count = step_count;
        this.tab_titles = tab_titles;
        this.text_1 = text_1;
        this.text_2 = text_2;
        this.steps = steps;
    }

    public int getName() {
        return name_id;
    }

//    public int getStepCount() {
//        return step_count;
//    }

    public ArrayList<Integer> getTabTitles() {
        return tab_titles;
    }

    ArrayList<Integer> getText1() {
        return text_1;
    }

    ArrayList<Integer> getText2() {
        return text_2;
    }

    ArrayList<ArrayList<RecipeQuantityInfo>> getSteps() {
        return steps;
    }
}
