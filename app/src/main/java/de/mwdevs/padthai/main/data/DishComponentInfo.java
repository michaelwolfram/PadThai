package de.mwdevs.padthai.main.data;

public class DishComponentInfo {
    public int name_id;
    String json_filename;
    public int component_index;

    DishComponentInfo(int name_id, String json_filename, int component_index) {
        this.name_id = name_id;
        this.json_filename = json_filename;
        this.component_index = component_index;
    }
}

