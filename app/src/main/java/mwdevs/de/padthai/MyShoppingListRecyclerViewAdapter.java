package mwdevs.de.padthai;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;

import java.util.List;

import mwdevs.de.padthai.ShoppingListContent.ShoppingItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ShoppingItem} and makes a call to the
 * specified {@link OnListInteractionListener}.
 */
public class MyShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<MyShoppingListRecyclerViewAdapter.ViewHolder> {

    private static final int EXCEL_SHEET_ROW_OFFSET = 7;
    private final List<ShoppingItem> mValues;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final OnListInteractionListener mListener;
    private final int mPaste_quantity;
    private final int mSosse_quantity;
    private final int mPad_thai_quantity;

    private Workbook mWorkbook = null;
    private Sheet mSheet0 = null;
    private boolean mShowListAsGrid;

    MyShoppingListRecyclerViewAdapter(List<ShoppingItem> items, Context context,
                                      OnListInteractionListener listener,
                                      int paste_quantity, int sosse_quantity, int pad_thai_quantity,
                                      boolean showListAsGrid) {
        mValues = items;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mListener = listener;
        this.mPaste_quantity = paste_quantity;
        this.mSosse_quantity = sosse_quantity;
        this.mPad_thai_quantity = pad_thai_quantity;
        mShowListAsGrid = showListAsGrid;

        setHasStableIds(true);
    }

    void setWorkbook(Workbook workbook) {
        if (workbook == null) {
            Log.e(MyShoppingListRecyclerViewAdapter.class.getName(), "Workbook was null. Was not set internally.");
            return;
        }
        mWorkbook = workbook;
        extractSheetAndUpdateComponentQuantities();

        notifyDataSetChanged();
    }

    private void extractSheetAndUpdateComponentQuantities() {
        mSheet0 = mWorkbook.getSheetAt(0);

        setCellValueInColumnB(mSheet0, 1, mPaste_quantity);
        setCellValueInColumnB(mSheet0, 2, mSosse_quantity);
        setCellValueInColumnB(mSheet0, 3, mPad_thai_quantity);

        // TODO: 20.05.19 this is taking some time...think about refactoring the excel sheet such
        //  that the content is extracted rather than working on the excel sheet from code
        XSSFFormulaEvaluator.evaluateAllFormulaCells(mWorkbook);
    }

    void setShowListAsGrid(boolean ShowListAsGrid) {
        mShowListAsGrid = ShowListAsGrid;
    }

    private void setCellValueInColumnB(Sheet sheet, int row, double value) {
        Cell cell = sheet.getRow(row - 1).getCell(CellReference.convertColStringToIndex("B"));
        cell.setCellValue(value);
    }

    private double getNumericCellValue(Sheet sheet, String column, int row) {
        Cell cell = sheet.getRow(row - 1).getCell(CellReference.convertColStringToIndex(column));
        return cell.getNumericCellValue();
    }

    private String getStringCellValueInColumnB(Sheet sheet, int row) {
        Cell cell = sheet.getRow(row - 1).getCell(CellReference.convertColStringToIndex("B"));
        return cell.getStringCellValue();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(
                mShowListAsGrid ? R.layout.shopping_item_grid : R.layout.shopping_item_linear,
                parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (mSheet0 == null) {
            holder.setEmptyView();
            return;
        }

        String ingredient_name = getStringCellValueInColumnB(mSheet0, EXCEL_SHEET_ROW_OFFSET + position);
        double ingredient_gramm = getNumericCellValue(mSheet0, "J", EXCEL_SHEET_ROW_OFFSET + position);
        double ingredient_stk = getNumericCellValue(mSheet0, "N", EXCEL_SHEET_ROW_OFFSET + position);

        holder.update(position, ingredient_name, ingredient_gramm, ingredient_stk);

        setViewListener(holder);
    }

    private void setViewListener(@NonNull final ViewHolder holder) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.toggleAlpha();

                if (null != mListener) {
                    mListener.onListItemClick(holder.mItem);
                }
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    mListener.onListItemLongClick(holder.mItem);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        private final ImageView mImage;
        private final TextView mHeader;
        private final TextView mGramm;
        private final TextView mStk;
        private final TextView mGrammValue;
        private final TextView mStkValue;
        private ShoppingItem mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mImage = view.findViewById(R.id.ingredient_image);
            mHeader = view.findViewById(R.id.ingredient_name);
            mGramm = view.findViewById(R.id.ingredient_g);
            mStk = view.findViewById(R.id.ingredient_stk);
            mGrammValue = view.findViewById(R.id.ingredient_g_value);
            mStkValue = view.findViewById(R.id.ingredient_stk_value);
        }

        void update(int position, String ingredient_name, double ingredient_gramm, double ingredient_stk) {
            updateShoppingItem(position);
            updateView(ingredient_name, ingredient_gramm, ingredient_stk);
        }

        private void updateShoppingItem(int position) {
            mItem = mValues.get(position);
        }

        void toggleAlpha() {
            mItem.toggleAlpha();
            updateAlpha();
        }

        private void updateView(String ingredient_name, double ingredient_gramm, double ingredient_stk) {
            updateImage();
            updateHeader(ingredient_name);
            updateGramm(ingredient_gramm);
            updateStk(ingredient_stk);
            updateAlpha();
        }

        private void updateImage() {
            mImage.setImageResource(mItem.image_id);
        }

        private void updateStk(double ingredient_stk) {
            boolean is_stk_invalid = ingredient_stk == -1.0f;
            mStk.setText(is_stk_invalid ? R.string.empty_string : mItem.stk_text);
            mStkValue.setText(is_stk_invalid ? "---" : mContext.getString(R.string.placeholder_dot1f, ingredient_stk));
        }

        private void updateGramm(double ingredient_gramm) {
            mGramm.setText(mItem.gramm_ml_text);
            mGrammValue.setText(mContext.getString(R.string.placeholder_d, (int) Math.round(ingredient_gramm)));
        }

        private void updateHeader(String ingredient_name) {
            if (mHeader != null)
                mHeader.setText(mContext.getString(R.string.placeholder_s, ingredient_name));
        }

        private void updateAlpha() {
            float alpha = mItem.getAlpha();
            if (mHeader != null)
                mHeader.setAlpha(alpha);
            mImage.setAlpha(alpha);
            mGramm.setAlpha(alpha);
            mGrammValue.setAlpha(alpha);
            mStk.setAlpha(alpha);
            mStkValue.setAlpha(alpha);
        }

        void setEmptyView() {
            mImage.setImageDrawable(null);
            if (mHeader != null)
                setDefaultText(mHeader);
            setDefaultText(mGramm);
            setDefaultText(mGrammValue);
            setDefaultText(mStk);
            setDefaultText(mStkValue);
        }

        private void setDefaultText(TextView textView) {
            textView.setText(R.string.empty_string);
        }
    }
}
