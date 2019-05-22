package de.mwdevs.padthai;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.SparseArray;

class ShoppingListContent {

    static final SparseArray<ShoppingItem> ITEM_PROPERTY_MAP = new SparseArray<>(15);

    static void initItemPropertyMap(Context context) {
        addItem(new ShoppingItem(1, context.getString(R.string.karotten), R.mipmap.karotten_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(2, context.getString(R.string.zwiebeln), R.mipmap.zwiebeln_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(3, context.getString(R.string.knoblauch), R.mipmap.knoblauch_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(4, context.getString(R.string.chili), R.mipmap.chilischoten_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(5, context.getString(R.string.oel), R.mipmap.sojaoel_round, R.string.ml, R.string.pkg));
        addItem(new ShoppingItem(6, context.getString(R.string.soja_sosse), R.mipmap.sojasosse_round, R.string.ml, R.string.pkg));
        addItem(new ShoppingItem(7, context.getString(R.string.zucker), R.mipmap.braunerzucker_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(8, context.getString(R.string.limettensaft), R.mipmap.limetten_round, R.string.ml, R.string.stk));
        addItem(new ShoppingItem(9, context.getString(R.string.tomaten), R.mipmap.tomaten_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(10, context.getString(R.string.erdnuesse), R.mipmap.erdnuesse_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(11, context.getString(R.string.kokosmilch), R.mipmap.kokosmilch_round, R.string.ml, R.string.pkg));
        addItem(new ShoppingItem(12, context.getString(R.string.fruehlingszwiebeln), R.mipmap.fruehlingszwiebeln_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(13, context.getString(R.string.sprossen), R.mipmap.mungobohnensprossen_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(14, context.getString(R.string.reisnudeln), R.mipmap.reisnudeln_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(15, context.getString(R.string.tofu), R.mipmap.tofu_round, R.string.g, R.string.pkg));
    }

    private static void addItem(ShoppingItem item) {
        ITEM_PROPERTY_MAP.put(item.id, item);
    }

    static void resetItems() {
        for (int i = 0; i < ITEM_PROPERTY_MAP.size(); i++) {
            int key = ITEM_PROPERTY_MAP.keyAt(i);
            ShoppingItem item = ITEM_PROPERTY_MAP.get(key);
            item.resetAlpha();
        }
    }

    public static class ShoppingItem implements Parcelable {
        public static final Creator<ShoppingItem> CREATOR = new Creator<ShoppingItem>() {
            @Override
            public ShoppingItem createFromParcel(Parcel in) {
                return new ShoppingItem(in);
            }

            @Override
            public ShoppingItem[] newArray(int size) {
                return new ShoppingItem[size];
            }
        };
        final int id;
        final String name;
        final int image_id;
        final int gram_ml_text;
        final int stk_text;
        double gram_ml;
        double stk;
        private float alpha = 1.0f;

        ShoppingItem(int id, String name, int image_id, int gram_ml_text, int stk_text) {
            this.id = id;
            this.name = name;
            this.image_id = image_id;
            this.gram_ml_text = gram_ml_text;
            this.stk_text = stk_text;
        }

        ShoppingItem(Parcel in) {
            id = in.readInt();
            name = in.readString();
            image_id = in.readInt();
            gram_ml_text = in.readInt();
            stk_text = in.readInt();
            gram_ml = in.readDouble();
            stk = in.readDouble();
            alpha = in.readFloat();
        }

        void setGramAndStk(double gram_ml, double stk) {
            this.gram_ml = gram_ml;
            this.stk = stk;
        }

        float getAlpha() {
            return alpha;
        }

        void resetAlpha() {
            alpha = 1.0f;
        }

        void toggleAlpha() {
            if (alpha == 1.0f)
                alpha = 0.2f;
            else
                alpha = 1.0f;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeInt(image_id);
            dest.writeInt(gram_ml_text);
            dest.writeInt(stk_text);
            dest.writeDouble(gram_ml);
            dest.writeDouble(stk);
            dest.writeFloat(alpha);
        }
    }
}
