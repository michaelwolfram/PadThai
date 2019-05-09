package mwdevs.de.padthai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mwdevs.de.padthai.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ShoppingListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ShoppingItem> ITEMS = new ArrayList<ShoppingItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ShoppingItem> ITEM_MAP = new HashMap<String, ShoppingItem>();

    private static final int COUNT = 15;

    static {
        // Add Pad Thai items.
        addItem(new ShoppingItem("Karotten", R.mipmap.karotten_round, "g"));
        addItem(new ShoppingItem("Zwiebeln", R.mipmap.zwiebeln_round, "g"));
        addItem(new ShoppingItem("Knoblauch", R.mipmap.knoblauch_round, "g"));
        addItem(new ShoppingItem("Chili (trocken)", R.mipmap.chilischoten_round, "g"));
        addItem(new ShoppingItem("Öl", R.mipmap.sojaoel_round, "ml"));
        addItem(new ShoppingItem("Soja Soße", R.mipmap.sojasosse_round, "ml"));
        addItem(new ShoppingItem("br. Zucker", R.mipmap.braunerzucker_round, "g"));
        addItem(new ShoppingItem("Limettensaft", R.mipmap.limetten_round, "ml"));
        addItem(new ShoppingItem("Tomaten", R.mipmap.tomaten_round, "g"));
        addItem(new ShoppingItem("Erdnüsse", R.mipmap.erdnuesse_round, "g"));
        addItem(new ShoppingItem("Kokosmilch", R.mipmap.kokosmilch_round, "ml"));
        addItem(new ShoppingItem("Frühl.zwiebel", R.mipmap.fruehlingszwiebeln_round, "g"));
        addItem(new ShoppingItem("Sprossen", R.mipmap.mungobohnensprossen_round, "g"));
        addItem(new ShoppingItem("Reisnudeln", R.mipmap.reisnudeln_round, "g"));
        addItem(new ShoppingItem("Tofu", R.mipmap.tofu_round, "g"));
    }

    private static void addItem(ShoppingItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void resetItems() {
        for (ShoppingItem item : ITEMS) {
            item.resetAlpha();
        }
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ShoppingItem {
        public final String id;
        public final int image_id;
        public final String gramm_ml_text;
        private float alpha = 1.0f;

        public ShoppingItem(String id, int image_id, String gramm_ml_text) {
            this.id = id;
            this.image_id = image_id;
            this.gramm_ml_text = gramm_ml_text;
        }

        public float getAlpha() {
            return alpha;
        }

        public void resetAlpha() {
            alpha = 1.0f;
        }

        public void toggleAlpha() {
            if (alpha == 1.0f)
                alpha = 0.2f;
            else
                alpha = 1.0f;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
