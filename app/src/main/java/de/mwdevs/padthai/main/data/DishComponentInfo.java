package de.mwdevs.padthai.main.data;

public class DishComponentInfo {
    public int name_id;
    public Class viewModelClass;

    DishComponentInfo(int name_id, Class viewModelClass) {
        this.name_id = name_id;
        this.viewModelClass = viewModelClass;
    }
}

