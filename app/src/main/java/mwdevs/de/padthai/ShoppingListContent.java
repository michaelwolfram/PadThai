package mwdevs.de.padthai;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

class ShoppingListContent {

    static final Map<String, ShoppingItem> ITEM_PROPERTY_MAP = new HashMap<>();

    static {
        addItem(new ShoppingItem("Karotten", R.mipmap.karotten_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem("Zwiebeln", R.mipmap.zwiebeln_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem("Knoblauch", R.mipmap.knoblauch_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem("Chili (trocken)", R.mipmap.chilischoten_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem("Öl", R.mipmap.sojaoel_round, R.string.ml, R.string.pkg));
        addItem(new ShoppingItem("Soja Soße", R.mipmap.sojasosse_round, R.string.ml, R.string.pkg));
        addItem(new ShoppingItem("br. Zucker", R.mipmap.braunerzucker_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem("Limettensaft", R.mipmap.limetten_round, R.string.ml, R.string.stk));
        addItem(new ShoppingItem("Tomaten", R.mipmap.tomaten_round, R.string.g, R.string.stk));
        addItem(new ShoppingItem("Erdnüsse", R.mipmap.erdnuesse_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem("Kokosmilch", R.mipmap.kokosmilch_round, R.string.ml, R.string.pkg));
        addItem(new ShoppingItem("Frühl.zwiebel", R.mipmap.fruehlingszwiebeln_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem("Sprossen", R.mipmap.mungobohnensprossen_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem("Reisnudeln", R.mipmap.reisnudeln_round, R.string.g, R.string.pkg));
        addItem(new ShoppingItem("Tofu", R.mipmap.tofu_round, R.string.g, R.string.pkg));
    }

    private static void addItem(ShoppingItem item) {
        ITEM_PROPERTY_MAP.put(item.id, item);
    }

    static void resetItems() {
        for (ShoppingItem item : ITEM_PROPERTY_MAP.values()) {
            item.resetAlpha();
        }
    }

    public static class ShoppingItem {
        public final String id;
        final int image_id;
        final int gram_ml_text;
        final int stk_text;
        double gram_ml;
        double stk;
        private float alpha = 1.0f;

        ShoppingItem(String id, int image_id, int gram_ml_text, int stk_text) {
            this.id = id;
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
            return id;
        }
    }
}
