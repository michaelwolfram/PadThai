package de.mwdevs.padthai;

class DishComponentInfo {
    int name_id;
    Class viewModelClass;

    DishComponentInfo(int name_id, Class viewModelClass) {
        this.name_id = name_id;
        this.viewModelClass = viewModelClass;
    }
}

