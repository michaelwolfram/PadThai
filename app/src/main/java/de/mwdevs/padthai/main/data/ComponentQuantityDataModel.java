package de.mwdevs.padthai.main.data;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;

import de.mwdevs.padthai.Utils;

public class ComponentQuantityDataModel {
    private final int mMaximumComponentRows;
    private int[][] mComponentQuantities;

    public ComponentQuantityDataModel(int maximum_component_rows) {
        mMaximumComponentRows = maximum_component_rows;
        mComponentQuantities = new int[DishInfo.values().length][mMaximumComponentRows];
    }

    public int getQuantity(DishInfo dishInfo, int row) {
        return getDishQuantities(dishInfo)[row];
    }

    public int[] getDishQuantities(DishInfo dishInfo) {
        int num_components = dishInfo.getNumDishComponents();
        int[] dishQuantities = new int[num_components];
        System.arraycopy(mComponentQuantities[dishInfo.getId()], 0, dishQuantities, 0, num_components);
        return dishQuantities;
    }

    public int[] getAllQuantities(Context context, DishInfo dishInfo) {
        int[] allQuantities = new int[Utils.getNumComponents(context)];
        for (int row = 0; row < dishInfo.getNumDishComponents(); row++) {
            int componentIndex = row + dishInfo.getDishComponentOffset();
            allQuantities[componentIndex] = getQuantity(dishInfo, row);
        }
        return allQuantities;
    }

    public void setQuantity(DishInfo dishInfo, int col, int value) {
        setQuantity(dishInfo.getId(), col, value);
    }

    private void setQuantity(int dishRow, int col, int value) {
        mComponentQuantities[dishRow][col] = value;
    }

    public boolean hasValues(DishInfo dishInfo) {
        boolean result = false;
        for (int row = 0; row < mMaximumComponentRows; row++) {
            result = result || hasValue(dishInfo, row);
        }
        return result;
    }

    public boolean hasValue(DishInfo dishInfo, int row) {
        return getQuantity(dishInfo, row) > 0;
    }

    @NonNull
    @Override
    public String toString() {
        JSONArray jsonArray = new JSONArray();
        for (int[] mComponentQuantity : mComponentQuantities) {
            for (int j = 0; j < mMaximumComponentRows; j++) {
                jsonArray.put(mComponentQuantity[j]);
            }
        }
        return jsonArray.toString();
    }

    public void fromString(String string) {
        try {
            JSONArray jsonArray = new JSONArray(string);
            for (int i = 0; i < jsonArray.length(); i++) {
                int dishRow = i / mMaximumComponentRows;
                int col = i % mMaximumComponentRows;
                setQuantity(dishRow, col, jsonArray.getInt(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
