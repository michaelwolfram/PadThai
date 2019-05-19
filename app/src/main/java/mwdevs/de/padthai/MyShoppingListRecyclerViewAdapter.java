package mwdevs.de.padthai;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

    private final List<ShoppingItem> mValues;
    private final OnListInteractionListener mListener;
    private LayoutInflater mLayoutInflater = null;
    private Context mContext = null;
    private Sheet mSheet;
    private boolean mShowListAsGrid;

    MyShoppingListRecyclerViewAdapter(List<ShoppingItem> items,
                                      int paste_quantity, int sosse_quantity, int pad_thai_quantity,
                                      OnListInteractionListener listener, Workbook workbook,
                                      boolean showListAsGrid) {
        mValues = items;
        mListener = listener;
        mShowListAsGrid = showListAsGrid;

        mSheet = workbook.getSheetAt(0);

        setCellValue(mSheet, 1, paste_quantity);
        setCellValue(mSheet, 2, sosse_quantity);
        setCellValue(mSheet, 3, pad_thai_quantity);

        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
    }

    void setShowListAsGrid(boolean ShowListAsGrid) {
        mShowListAsGrid = ShowListAsGrid;
    }

    private void setCellValue(Sheet sheet, int row, double value) {
        Cell cell = sheet.getRow(row - 1).getCell(CellReference.convertColStringToIndex("B"));
        cell.setCellValue(value);
    }

    private double getNumericCellValue(Sheet sheet, String column, int row) {
        Cell cell = sheet.getRow(row - 1).getCell(CellReference.convertColStringToIndex(column));
        return cell.getNumericCellValue();
    }

    private String getStringCellValue(Sheet sheet, int row) {
        Cell cell = sheet.getRow(row - 1).getCell(CellReference.convertColStringToIndex("B"));
        return cell.getStringCellValue();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
            mContext = mLayoutInflater.getContext();
        }
        View view = mLayoutInflater.inflate(mShowListAsGrid ?
                        R.layout.shopping_item_grid : R.layout.shopping_item_linear,
                        parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mImage.setImageDrawable(null);

        String ingredient_name = getStringCellValue(mSheet, 7 + position);
        double ingredient_gramm = getNumericCellValue(mSheet, "J", 7 + position);
        double ingredient_stk = getNumericCellValue(mSheet, "N", 7 + position);

        holder.mItem = mValues.get(position);
        holder.updateAlpha();

        holder.mImage.setImageResource(holder.mItem.image_id);
        if (holder.mHeader != null)
            holder.mHeader.setText(mContext.getString(R.string.placeholder_s, ingredient_name));
        holder.mGramm.setText(holder.mItem.gramm_ml_text);
        holder.mGrammValue.setText(mContext.getString(R.string.placeholder_d, (int) Math.round(ingredient_gramm)));
        if (ingredient_stk == -1.0f) {
            holder.mStk.setText("");
            holder.mStkValue.setText("---");
        } else {
            holder.mStk.setText(holder.mItem.stk_text);
            holder.mStkValue.setText(String.format("%.1f", ingredient_stk));
        }

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
        final ImageView mImage;
        final TextView mHeader;
        final TextView mGramm;
        final TextView mStk;
        final TextView mGrammValue;
        final TextView mStkValue;
        ShoppingItem mItem;

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

        void updateAlpha() {
            float alpha = mItem.getAlpha();
            if (mHeader != null)
                mHeader.setAlpha(alpha);
            mImage.setAlpha(alpha);
            mGramm.setAlpha(alpha);
            mGrammValue.setAlpha(alpha);
            mStk.setAlpha(alpha);
            mStkValue.setAlpha(alpha);
        }

        void toggleAlpha() {
            mItem.toggleAlpha();
            updateAlpha();
        }
    }
}
