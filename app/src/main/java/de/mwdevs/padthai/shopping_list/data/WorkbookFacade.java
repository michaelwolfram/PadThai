package de.mwdevs.padthai.shopping_list.data;

import android.support.annotation.NonNull;
import android.util.Log;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;

import java.util.ArrayList;

public class WorkbookFacade {
    private static final int FIRST_ID_ROW_INDEX = 6;
    private static final String COLUMN_ID = "D";
    private static final String COLUMN_GRAM = "M";
    private static final String COLUMN_STK = "Q";

    private final int[] mComponentQuantities;
    private Workbook mWorkbook;
    private Sheet mSheet0;

    public WorkbookFacade(int[] component_quantities) {
        if (component_quantities == null) {
            throw new IllegalArgumentException("No component quantities provided.");
        }
        mComponentQuantities = component_quantities;
    }

    public void setWorkbook(Workbook workbook) {
        mWorkbook = workbook;
        extractSheetAndUpdateComponentQuantities();
    }

    private void extractSheetAndUpdateComponentQuantities() {
        mSheet0 = mWorkbook.getSheetAt(0);

        for (int i = 0; i < mComponentQuantities.length; i++) {
            setCellValueInColumnB(mSheet0, i + 1, mComponentQuantities[i]);
        }

        // this is taking some time...if you think about refactoring,
        // think about refactoring the excel sheet such that the content is extracted
        // rather than working on the excel sheet from code
        XSSFFormulaEvaluator.evaluateAllFormulaCells(mWorkbook);
    }

    public ArrayList<ShoppingListContent.ShoppingItem> createShoppingItems() {
        ArrayList<ShoppingListContent.ShoppingItem> items = new ArrayList<>();

        for (int row = FIRST_ID_ROW_INDEX; row <= mSheet0.getLastRowNum(); ++row) {
            int item_id = getItemId(row);
            ShoppingListContent.ShoppingItem item = ShoppingListContent.ITEM_PROPERTY_MAP.get(item_id);
            if (item == null) {
                Log.e(WorkbookFacade.class.getName(), "item was null. It was not added! Its ID was: " + item_id);
                continue;
            }
            double item_gram = getItemGram(row);
            if (item_gram == 0)  // Do not add the item to the shopping list if it's 0 gram.
                continue;
            double item_stk = getItemStk(row);
            item.setGramAndStk(item_gram, item_stk);
            items.add(item);
        }
        return items;
    }

    private int getItemId(int row) {
        return (int) getNumericCellValue(mSheet0, COLUMN_ID, row);
    }

    private double getItemGram(int row) {
        return getNumericCellValue(mSheet0, COLUMN_GRAM, row);
    }

    private double getItemStk(int row) {
        return getNumericCellValue(mSheet0, COLUMN_STK, row);
    }

    private void setCellValueInColumnB(@NonNull Sheet sheet, int row, double value) {
        Cell cell = sheet.getRow(row - 1).getCell(CellReference.convertColStringToIndex("B"));
        if (cell == null) {
            Log.e(WorkbookFacade.class.getName(), "cell was null. It was not updated! The cell was: B" + row);
            return;
        }
        cell.setCellValue(value);
    }

    private double getNumericCellValue(@NonNull Sheet sheet, String column, int row) {
        Cell cell = sheet.getRow(row).getCell(CellReference.convertColStringToIndex(column));
        return cell.getNumericCellValue();
    }
}
