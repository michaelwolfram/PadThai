package de.mwdevs.padthai.shopping_list.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import de.mwdevs.padthai.R;

public class ShoppingListContent {

    static final SparseArray<ShoppingItem> ITEM_PROPERTY_MAP = new SparseArray<>(15);

    private static boolean initialized = false;

    public static void initItemPropertyMap() {
        if (initialized)
            return;

        addItem(new ShoppingItem(1, R.string.carrots, R.mipmap.carrots_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(2, R.string.onions, R.mipmap.onions_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(3, R.string.garlic, R.mipmap.garlic_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(4, R.string.chili_dry, R.mipmap.chili_dry_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(5, R.string.oil, R.mipmap.oil_round, R.string.ml, R.string.pkg));
        addItem(new ShoppingItem(6, R.string.soja_sauce, R.mipmap.soja_sauce_round, R.string.ml, R.string.pkg));
        addItem(new ShoppingItem(7, R.string.sugar, R.mipmap.sugar_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(8, R.string.lime_juice, R.mipmap.lime_juice_round, R.string.ml, R.string.stk));
        addItem(new ShoppingItem(9, R.string.tomatoes, R.mipmap.tomatoes_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(10, R.string.peanuts, R.mipmap.peanuts_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(11, R.string.coconut_milk, R.mipmap.coconut_milk_round, R.string.ml, R.string.pkg));
        addItem(new ShoppingItem(12, R.string.spring_onions, R.mipmap.spring_onions_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(13, R.string.sprouts, R.mipmap.sprouts_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(14, R.string.rice_noodles, R.mipmap.rice_noodles_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(15, R.string.tofu, R.mipmap.tofu_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(16, R.string.curry_powder, R.mipmap.curry_powder_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(17, R.string.mixed_vegetables, R.mipmap.mixed_vegetables_round, R.string.g, R.string.handful));
        addItem(new ShoppingItem(18, R.string.rice, R.mipmap.rice_round, R.string.g, R.string.c));

        initialized = true;
    }

    private static void addItem(ShoppingItem item) {
        ITEM_PROPERTY_MAP.put(item.id, item);
    }

    public static void resetItems() {
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
        final public int id;
        final public int name_id;
        final public int image_id;
        final public int gram_ml_text;
        final public int stk_text;
        public double gram_ml;
        public double stk;
        private float alpha = 1.0f;

        ShoppingItem(int id, int name_id, int image_id, int gram_ml_text, int stk_text) {
            this.id = id;
            this.name_id = name_id;
            this.image_id = image_id;
            this.gram_ml_text = gram_ml_text;
            this.stk_text = stk_text;
        }

        ShoppingItem(Parcel in) {
            id = in.readInt();
            name_id = in.readInt();
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

        public float getAlpha() {
            return alpha;
        }

        public void resetAlpha() {
            alpha = 1.0f;
        }

        public void toggleAlpha() {
            if (alpha == 1.0f)
                alpha = 0.15f;
            else
                alpha = 1.0f;
        }

        public int getNameId() {
            return name_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeInt(name_id);
            dest.writeInt(image_id);
            dest.writeInt(gram_ml_text);
            dest.writeInt(stk_text);
            dest.writeDouble(gram_ml);
            dest.writeDouble(stk);
            dest.writeFloat(alpha);
        }
    }
}
