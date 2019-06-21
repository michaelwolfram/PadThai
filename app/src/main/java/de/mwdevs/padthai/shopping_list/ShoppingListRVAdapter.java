package de.mwdevs.padthai.shopping_list;

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

import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;

import de.mwdevs.padthai.R;
import de.mwdevs.padthai.shopping_list.data.ShoppingListContent.ShoppingItem;
import de.mwdevs.padthai.shopping_list.data.WorkbookFacade;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ShoppingItem} and makes a call to the
 * specified {@link OnListInteractionListener}.
 */
public class ShoppingListRVAdapter extends RecyclerView.Adapter<ShoppingListRVAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final OnListInteractionListener mListener;
    private final WorkbookFacade mWorkbookFacade;

    private ArrayList<ShoppingItem> mValues;
    private boolean mShowListAsGrid;

    public ShoppingListRVAdapter(Context context, OnListInteractionListener listener,
                                 int[] component_quantities, boolean showListAsGrid) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mListener = listener;
        mWorkbookFacade = new WorkbookFacade(component_quantities);
        mShowListAsGrid = showListAsGrid;

        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return mValues.get(position).id;
    }

    public void updateDataFromWorkbook(Workbook workbook) {
        if (workbook == null) {
            Log.e(ShoppingListRVAdapter.class.getName(), "Workbook was null. Data not updated!");
            return;
        }
        mWorkbookFacade.setWorkbook(workbook);
        mValues = mWorkbookFacade.createShoppingItems();
        notifyDataSetChanged();
    }

    public void setShowListAsGrid(boolean ShowListAsGrid) {
        mShowListAsGrid = ShowListAsGrid;
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
        if (mValues == null || mValues.isEmpty()) {
            holder.setEmptyView();
            return;
        }

        holder.update(position);

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

    public ArrayList<ShoppingItem> getData() {
        return mValues;
    }

    public void setData(ArrayList<ShoppingItem> mValues) {
        if (mValues == null) {
            Log.e(ShoppingListRVAdapter.class.getName(), "Provided data was null. Data not updated!");
            return;
        }
        this.mValues = mValues;
    }

    public boolean hasData() {
        return getItemCount() > 0;
    }

    public void resetData() {
        for (ShoppingItem item : mValues) {
            item.resetAlpha();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mValues == null || mValues.isEmpty()) {
            return 0;
        }
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        private final ImageView mImage;
        private final TextView mHeader;
        private final TextView mGram;
        private final TextView mStk;
        private final TextView mGramValue;
        private final TextView mStkValue;
        private ShoppingItem mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mImage = view.findViewById(R.id.ingredient_image);
            mHeader = view.findViewById(R.id.ingredient_name);
            mGram = view.findViewById(R.id.ingredient_g);
            mStk = view.findViewById(R.id.ingredient_stk);
            mGramValue = view.findViewById(R.id.ingredient_g_value);
            mStkValue = view.findViewById(R.id.ingredient_stk_value);
        }

        void update(int position) {
            updateShoppingItem(position);
            updateView();
        }

        private void updateShoppingItem(int position) {
            mItem = mValues.get(position);
        }

        void toggleAlpha() {
            mItem.toggleAlpha();
            updateAlpha();
        }

        private void updateView() {
            updateImage();
            updateHeader();
            updateGram();
            updateStk();
            updateAlpha();
        }

        private void updateImage() {
            mImage.setImageResource(mItem.image_id);
        }

        private void updateHeader() {
            if (mHeader != null) {
                String name = mContext.getString(mItem.name_id);
                mHeader.setText(mContext.getString(R.string.placeholder_s, name));
            }
        }

        private void updateGram() {
            mGram.setText(mItem.gram_ml_text);
            mGramValue.setText(mContext.getString(R.string.placeholder_d, (int) Math.round(mItem.gram_ml)));
        }

        private void updateStk() {
            boolean is_stk_invalid = mItem.stk == -1.0f;
            mStk.setText(is_stk_invalid ? R.string.empty_string : mItem.stk_text);
            mStkValue.setText(is_stk_invalid ? "---" : mContext.getString(R.string.placeholder_dot1f, mItem.stk));
        }

        private void updateAlpha() {
            float alpha = mItem.getAlpha();
            if (mHeader != null)
                mHeader.setAlpha(alpha);
            mImage.setAlpha(alpha);
            mGram.setAlpha(alpha);
            mGramValue.setAlpha(alpha);
            mStk.setAlpha(alpha);
            mStkValue.setAlpha(alpha);
        }

        void setEmptyView() {
            mImage.setImageDrawable(null);
            if (mHeader != null)
                setDefaultText(mHeader);
            setDefaultText(mGram);
            setDefaultText(mGramValue);
            setDefaultText(mStk);
            setDefaultText(mStkValue);
        }

        private void setDefaultText(@NonNull TextView textView) {
            textView.setText(R.string.empty_string);
        }
    }
}
