package de.mwdevs.padthai;

import android.support.annotation.NonNull;
import android.util.Log;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;

import java.util.ArrayList;

class WorkbookFacade {
    private static final int EXCEL_SHEET_ROW_OFFSET = 7;

    private final int mPaste_quantity;
    private final int mSosse_quantity;
    private final int mPad_thai_quantity;
    private Workbook mWorkbook;
    private Sheet mSheet0;

    WorkbookFacade(int mPaste_quantity, int mSosse_quantity, int mPad_thai_quantity) {
        this.mPaste_quantity = mPaste_quantity;
        this.mSosse_quantity = mSosse_quantity;
        this.mPad_thai_quantity = mPad_thai_quantity;
    }

    void setWorkbook(Workbook workbook) {
        mWorkbook = workbook;
        extractSheetAndUpdateComponentQuantities();
    }

    private void extractSheetAndUpdateComponentQuantities() {
        mSheet0 = mWorkbook.getSheetAt(0);

        setCellValueInColumnB(mSheet0, 1, mPaste_quantity);
        setCellValueInColumnB(mSheet0, 2, mSosse_quantity);
        setCellValueInColumnB(mSheet0, 3, mPad_thai_quantity);

        // TODO: 20.05.19 this is taking some time...if you think about refactoring,
        //  think about refactoring the excel sheet such that the content is extracted
        //  rather than working on the excel sheet from code
        XSSFFormulaEvaluator.evaluateAllFormulaCells(mWorkbook);
    }

    ArrayList<ShoppingListContent.ShoppingItem> createShoppingItems() {
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
