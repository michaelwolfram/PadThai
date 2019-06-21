package de.mwdevs.padthai.main.data;

import android.support.annotation.NonNull;

import org.json.JSONArray;

import de.mwdevs.padthai.main.data.DishInfo;

public class ComponentQuantityDataModel {
    private final int mMaximumComponentRows;
    private int[][] mComponentQuantities;

    public ComponentQuantityDataModel(int maximum_component_rows) {
        mMaximumComponentRows = maximum_component_rows;
        mComponentQuantities = new int[DishInfo.values().length][mMaximumComponentRows];
    }

    public int getQuantity(DishInfo dishInfo, int row) {
        return getQuantities(dishInfo)[row];
    }

    public int[] getQuantities(DishInfo dishInfo) {
        return mComponentQuantities[dishInfo.getId()];
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