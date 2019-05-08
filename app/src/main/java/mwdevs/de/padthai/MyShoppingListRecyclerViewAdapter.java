package mwdevs.de.padthai;

import android.content.res.AssetManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;

import java.io.InputStream;
import java.util.List;

import mwdevs.de.padthai.ShoppingListFragment.OnListFragmentInteractionListener;
import mwdevs.de.padthai.ShoppingListContent.ShoppingItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ShoppingItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<MyShoppingListRecyclerViewAdapter.ViewHolder> {

    private final List<ShoppingItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final AssetManager mAssetManager;
    private final int m_paste_quantity;
    private final int m_sosse_quantity;
    private final int m_padthai_quantity;
    private Workbook mWorkBook;
    private Sheet mSheet;

    public MyShoppingListRecyclerViewAdapter(List<ShoppingItem> items,
                                             int paste_quantity, int sosse_quantity, int padthai_quantity,
                                             OnListFragmentInteractionListener listener,
                                             AssetManager assetManager) {
        mValues = items;
        mListener = listener;
        mAssetManager = assetManager;
        m_paste_quantity = paste_quantity;
        m_sosse_quantity = sosse_quantity;
        m_padthai_quantity = padthai_quantity;

        mWorkBook = openExcelFileWorkBook("Pad Thai Angaben.xls");
        mSheet = mWorkBook.getSheetAt(0);

        setCellValue(mSheet, "B", 1, m_paste_quantity);
        setCellValue(mSheet, "B", 2, m_sosse_quantity);
        setCellValue(mSheet, "B", 3, m_padthai_quantity);

        XSSFFormulaEvaluator.evaluateAllFormulaCells(mWorkBook);
    }

    public Workbook openExcelFileWorkBook(String file_name) {
        try {
            InputStream myInput = mAssetManager.open(file_name);
            Workbook workbook = new HSSFWorkbook(myInput);
            return workbook;
        } catch (Exception e) {
            Log.e("main", "error " + e.toString());
        }
        return null;
    }

    public double getNumericCellValue(Sheet sheet, String column, int row) {
        Cell cell = sheet.getRow(row - 1).getCell(CellReference.convertColStringToIndex(column));
        return cell.getNumericCellValue();
    }

    public String getStringCellValue(Sheet sheet, String column, int row) {
        Cell cell = sheet.getRow(row - 1).getCell(CellReference.convertColStringToIndex(column));
        return cell.getStringCellValue();
    }

    public void setCellValue(Sheet sheet, String column, int row, double value) {
        Cell cell = sheet.getRow(row - 1).getCell(CellReference.convertColStringToIndex(column));
        cell.setCellValue(value);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shopping_item, parent, false);
        return new ViewHolder(view);
    }

    private float getNewAlpha(float currentAlpha) {
        if (currentAlpha == 1.0f)
            return 0.2f;
        else
            return 1.0f;
    }

//    private int getNewPaintFlags(int currentPaintFlags) {
//        int current_state = currentPaintFlags & Paint.STRIKE_THRU_TEXT_FLAG;
//        int new_state = ~current_state & Paint.STRIKE_THRU_TEXT_FLAG;
//        int all_other_flags = currentPaintFlags & ~Paint.STRIKE_THRU_TEXT_FLAG;
//        return all_other_flags | new_state;
//    }

    private void toggleAlpha(View view) {
        view.setAlpha(getNewAlpha(view.getAlpha()));
    }

//    private void togglePaintFlags(TextView textView) {
//        textView.setPaintFlags(getNewPaintFlags(textView.getPaintFlags()));
//    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String ingredient_name = getStringCellValue(mSheet, "B", 7 + position);
        double ingredient_gramm = getNumericCellValue(mSheet, "J", 7 + position);
        double ingredient_stk = getNumericCellValue(mSheet, "N", 7 + position);
        holder.mItem = mValues.get(position);
        holder.mImage.setImageResource(holder.mItem.image_id);
        holder.mHeader.setText("" + ingredient_name);
        holder.mGramm.setText(R.string.g);
        holder.mGrammValue.setText("" + (int) Math.round(ingredient_gramm));
        if (ingredient_stk == -1.0f) {
            holder.mStk.setText("");
            holder.mStkValue.setText("-");
        } else {
            holder.mStk.setText(R.string.stk);
            holder.mStkValue.setText(String.format("%.2f", ingredient_stk));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                togglePaintFlags(holder.mHeader);
                toggleAlpha(holder.mHeader);
                toggleAlpha(holder.mImage);
                toggleAlpha(holder.mGramm);
                toggleAlpha(holder.mGrammValue);
                toggleAlpha(holder.mStk);
                toggleAlpha(holder.mStkValue);

                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImage;
        public final TextView mHeader;
        public final TextView mGramm;
        public final TextView mStk;
        public final TextView mGrammValue;
        public final TextView mStkValue;
        public ShoppingItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImage = view.findViewById(R.id.ingredient_image);
            mHeader = view.findViewById(R.id.ingredient_name);
            mGramm = view.findViewById(R.id.ingredient_g);
            mStk = view.findViewById(R.id.ingredient_stk);
            mGrammValue = view.findViewById(R.id.ingredient_g_value);
            mStkValue = view.findViewById(R.id.ingredient_stk_value);
        }
    }
}
