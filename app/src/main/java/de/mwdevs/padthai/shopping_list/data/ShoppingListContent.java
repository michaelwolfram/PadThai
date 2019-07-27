package de.mwdevs.padthai.shopping_list.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import de.mwdevs.padthai.R;

public class ShoppingListContent {

    static final SparseArray<ShoppingItem> ITEM_PROPERTY_MAP = new SparseArray<>(41);

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
        addItem(new ShoppingItem(17, R.string.mixed_vegetables_tom_yam_soup, R.mipmap.mixed_vegetables_tom_yam_soup_round, R.string.g, R.string.handful));
        addItem(new ShoppingItem(18, R.string.rice, R.mipmap.rice_round, R.string.g, R.string.cups));
        addItem(new ShoppingItem(19, R.string.red_and_green_chilies, R.mipmap.red_and_green_chilies_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(20, R.string.green_beans, R.mipmap.green_beans_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(21, R.string.sugar_water, R.mipmap.sugar_water_round, R.string.ml, R.string.cups));
        addItem(new ShoppingItem(22, R.string.root_vegetables, R.mipmap.root_vegetables_round, R.string.g, R.string.handful));
        addItem(new ShoppingItem(23, R.string.green_chili_paste, R.mipmap.green_chili_paste_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(24, R.string.kaffir_lime_leaves, R.mipmap.kaffir_lime_leaves_round, R.string.g, R.string.leaves));
        addItem(new ShoppingItem(25, R.string.galangal, R.mipmap.galangal_round, R.string.g, R.string.slices));
        addItem(new ShoppingItem(26, R.string.lemon_grass, R.mipmap.lemon_grass_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(27, R.string.thai_basil_v_mint, R.mipmap.thai_basil_v_mint_round, R.string.g, R.string.leaves));
        addItem(new ShoppingItem(28, R.string.coriander_leaves, R.mipmap.coriander_leaves_round, R.string.g, R.string.leaves));
        addItem(new ShoppingItem(29, R.string.spring_roll_wrapper, R.mipmap.spring_roll_wrapper_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(30, R.string.mint_leaves, R.mipmap.mint_leaves_round, R.string.g, R.string.leaves));
        addItem(new ShoppingItem(31, R.string.bean_sprouts, R.mipmap.bean_sprouts_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(32, R.string.coconut_mango_avocado, R.mipmap.coconut_mango_avocado_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(33, R.string.sesame_seeds, R.mipmap.sesame_seeds_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(34, R.string.cashew_nuts, R.mipmap.cashew_nuts_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(35, R.string.pumpkin, R.mipmap.pumpkin_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(36, R.string.butterfly_pea_tea, R.mipmap.butterfly_pea_tea_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(37, R.string.salt, R.mipmap.salt_round, R.string.g, R.string.pinches));
        addItem(new ShoppingItem(38, R.string.mango, R.mipmap.mango_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(39, R.string.banana, R.mipmap.banana_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(40, R.string.vegetables_massaman_curry, R.mipmap.vegetables_massaman_curry_round, R.string.g, R.string.handful));
        addItem(new ShoppingItem(41, R.string.vegetables_green_thai_curry, R.mipmap.vegetables_green_thai_curry_round, R.string.g, R.string.handful));

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
