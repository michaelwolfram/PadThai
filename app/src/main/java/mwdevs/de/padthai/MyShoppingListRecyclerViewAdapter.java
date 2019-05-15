package mwdevs.de.padthai;

import android.content.res.AssetManager;
import android.support.design.widget.Snackbar;
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

import mwdevs.de.padthai.ShoppingListContent.ShoppingItem;

//class MyWorkbookTask extends AsyncTask<String, Void, Workbook> {
//
//    @Override
//    protected Workbook doInBackground(String... params) {
//        Workbook workbook = null;
//        try {
//            URL url = new URL(params[0]);
//            URLConnection uc = url.openConnection();
//            workbook = new HSSFWorkbook(uc.getInputStream());
//        } catch (MalformedURLException e) {
//        } catch (IOException e) {
//        }
//        return workbook;
//    }
//}

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
    private boolean showListAsGrid = false;

    public MyShoppingListRecyclerViewAdapter(List<ShoppingItem> items,
                                             int paste_quantity, int sosse_quantity, int padthai_quantity,
                                             OnListFragmentInteractionListener listener,
                                             AssetManager assetManager, boolean showListAsGrid) {
        mValues = items;
        mListener = listener;
        mAssetManager = assetManager;
        m_paste_quantity = paste_quantity;
        m_sosse_quantity = sosse_quantity;
        m_padthai_quantity = padthai_quantity;
        this.showListAsGrid = showListAsGrid;

        mWorkBook = openExcelFileWorkBook("Pad Thai Angaben.xls");
//        mWorkBook = openExcelFileWorkBook("https://www.dropbox.com/s/zb0ahtftqu0ta7y/Pad%20Thai%20Angaben.xls");

        mSheet = mWorkBook.getSheetAt(0);

        setCellValue(mSheet, "B", 1, m_paste_quantity);
        setCellValue(mSheet, "B", 2, m_sosse_quantity);
        setCellValue(mSheet, "B", 3, m_padthai_quantity);

        XSSFFormulaEvaluator.evaluateAllFormulaCells(mWorkBook);
    }

    public void setShowListAsGrid(boolean showListAsGrid) {
        this.showListAsGrid = showListAsGrid;
    }

    public Workbook openExcelFileWorkBook(String file_name) {
        try {
//            return new MyWorkbookTask().execute(file_name).get();

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
                .inflate(showListAsGrid ? R.layout.shopping_item_grid : R.layout.shopping_item_linear,
                        parent, false);
        return new ViewHolder(view);
    }

//    public int getActionBarSize(View view) {
//        TypedArray styledAttributes = view.getContext().getTheme()
//                .obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
//        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
//        styledAttributes.recycle();
//        return actionBarSize;
//    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        if (position == 0) {
//            TypedValue tv = new TypedValue();
//            holder.mView.getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
//            int actionBarHeight = holder.mView.getResources().getDimensionPixelSize(tv.resourceId);
//            holder.mView.setAlpha(0.0f);
//            android.support.v7.widget.RecyclerView.LayoutParams params =
//                    (android.support.v7.widget.RecyclerView.LayoutParams) holder.mView.getLayoutParams();
//            params.height = actionBarHeight;
//            params.width = getActionBarSize(holder.mView) * 3;
//            holder.mView.setLayoutParams(params);
//            return;
//        }

//        position = position - 1;
        String ingredient_name = getStringCellValue(mSheet, "B", 7 + position);
        double ingredient_gramm = getNumericCellValue(mSheet, "J", 7 + position);
        double ingredient_stk = getNumericCellValue(mSheet, "N", 7 + position);
//        position = position + 1;

        holder.mItem = mValues.get(position);
        holder.updateAlpha();

        holder.mImage.setImageResource(holder.mItem.image_id);
        if (holder.mHeader != null)
            holder.mHeader.setText("" + ingredient_name);
        holder.mGramm.setText(holder.mItem.gramm_ml_text);
        holder.mGrammValue.setText("" + (int) Math.round(ingredient_gramm));
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
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v, holder.mItem.toString(), Snackbar.LENGTH_SHORT)
                        .show();
                return true;
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
