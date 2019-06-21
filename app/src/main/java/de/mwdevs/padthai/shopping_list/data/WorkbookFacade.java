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
    private static final int EXCEL_SHEET_ROW_OFFSET = 7;

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

        // TODO: 20.05.19 this is taking some time...if you think about refactoring,
        //  think about refactoring the excel sheet such that the content is extracted
        //  rather than working on the excel sheet from code
        XSSFFormulaEvaluator.evaluateAllFormulaCells(mWorkbook);
    }

    public ArrayList<ShoppingListContent.ShoppingItem> createShoppingItems() {
        ArrayList<ShoppingListContent.ShoppingItem> items = new ArrayList<>();

        for (int pos = 0; pos <= mSheet0.getPhysicalNumberOfRows() - EXCEL_SHEET_ROW_OFFSET; pos++) {
            int item_id = getItemId(pos);
            ShoppingListContent.ShoppingItem item = ShoppingListContent.ITEM_PROPERTY_MAP.get(item_id);
            if (item == null) {
                Log.e(WorkbookFacade.class.getName(), "item was null. It was not added! Its ID was: " + item_id);
                continue;
            }
            double item_gram = getItemGram(pos);
            if (item_gram == 0)  // Do not add the item to the shopping list if it's 0 gram.
                continue;
            double item_stk = getItemStk(pos);
            item.setGramAndStk(item_gram, item_stk);
            items.add(item);
        }
        return items;
    }

    private int getItemId(int position) {
        return (int) getNumericCellValue(mSheet0, "A", EXCEL_SHEET_ROW_OFFSET + position);
    }

    private double getItemGram(int position) {
        return getNumericCellValue(mSheet0, "J", EXCEL_SHEET_ROW_OFFSET + position);
    }

    private double getItemStk(int position) {
        return getNumericCellValue(mSheet0, "N", EXCEL_SHEET_ROW_OFFSET + position);
    }

    private void setCellValueInColumnB(@NonNull Sheet sheet, int row, double value) {
        Cell cell = sheet.getRow(row - 1).getCell(CellReference.convertColStringToIndex("B"));
        cell.setCellValue(value);
    }

    private double getNumericCellValue(@NonNull Sheet sheet, String column, int row) {
        Cell cell = sheet.getRow(row - 1).getCell(CellReference.convertColStringToIndex(column));
        return cell.getNumericCellValue();
    }
}
