package mwdevs.de.padthai;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
class ShoppingListContent {

    static final List<ShoppingItem> ITEMS = new ArrayList<>();

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
        ITEMS.add(item);
    }

    static void resetItems() {
        for (ShoppingItem item : ITEMS) {
            item.resetAlpha();
        }
    }

    public static class ShoppingItem {
        public final String id;
        final int image_id;
        final int gramm_ml_text;
        final int stk_text;
        private float alpha = 1.0f;

        ShoppingItem(String id, int image_id, int gramm_ml_text, int stk_text) {
            this.id = id;
            this.image_id = image_id;
            this.gramm_ml_text = gramm_ml_text;
            this.stk_text = stk_text;
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
