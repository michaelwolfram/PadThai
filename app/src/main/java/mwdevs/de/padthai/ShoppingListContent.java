package mwdevs.de.padthai;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

class ShoppingListContent {

    static final Map<String, ShoppingItem> ITEM_PROPERTY_MAP = new HashMap<>();

    static {
        addItem(new ShoppingItem(1, "Karotten", R.mipmap.karotten_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(2, "Zwiebeln", R.mipmap.zwiebeln_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(3, "Knoblauch", R.mipmap.knoblauch_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(4, "Chili (trocken)", R.mipmap.chilischoten_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(5, "Öl", R.mipmap.sojaoel_round, R.string.ml, R.string.pkg));
        addItem(new ShoppingItem(6, "Soja Soße", R.mipmap.sojasosse_round, R.string.ml, R.string.pkg));
        addItem(new ShoppingItem(7, "br. Zucker", R.mipmap.braunerzucker_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(8, "Limettensaft", R.mipmap.limetten_round, R.string.ml, R.string.stk));
        addItem(new ShoppingItem(9, "Tomaten", R.mipmap.tomaten_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem(10, "Erdnüsse", R.mipmap.erdnuesse_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(11, "Kokosmilch", R.mipmap.kokosmilch_round, R.string.ml, R.string.pkg));
        addItem(new ShoppingItem(12, "Frühl.zwiebel", R.mipmap.fruehlingszwiebeln_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(13, "Sprossen", R.mipmap.mungobohnensprossen_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(14, "Reisnudeln", R.mipmap.reisnudeln_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem(15, "Tofu", R.mipmap.tofu_round, R.string.g, R.string.pkg));
    }

    private static void addItem(ShoppingItem item) {
        ITEM_PROPERTY_MAP.put(item.name, item);
    }

    static void resetItems() {
        for (ShoppingItem item : ITEM_PROPERTY_MAP.values()) {
            item.resetAlpha();
        }
    }

    public static class ShoppingItem {
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
    }
}
