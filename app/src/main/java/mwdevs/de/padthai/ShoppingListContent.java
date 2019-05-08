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
        addItem(new ShoppingItem("Karotten", R.mipmap.karotten_round));
        addItem(new ShoppingItem("Zwiebeln", R.mipmap.zwiebeln_round));
        addItem(new ShoppingItem("Knoblauch", R.mipmap.knoblauch_round));
        addItem(new ShoppingItem("Chili (trocken)", R.mipmap.chilischoten_round));
        addItem(new ShoppingItem("Öl", R.mipmap.sojaoel_round));
        addItem(new ShoppingItem("Soja Soße", R.mipmap.sojasosse_round));
        addItem(new ShoppingItem("br. Zucker", R.mipmap.braunerzucker_round));
        addItem(new ShoppingItem("Limettensaft", R.mipmap.limetten_round));
        addItem(new ShoppingItem("Tomaten", R.mipmap.tomaten_round));
        addItem(new ShoppingItem("Erdnüsse", R.mipmap.erdnuesse_round));
        addItem(new ShoppingItem("Kokosmilch", R.mipmap.kokosmilch_round));
        addItem(new ShoppingItem("Frühl.zwiebel", R.mipmap.fruehlingszwiebeln_round));
        addItem(new ShoppingItem("Sprossen", R.mipmap.mungobohnensprossen_round));
        addItem(new ShoppingItem("Reisnudeln", R.mipmap.reisnudeln_round));
        addItem(new ShoppingItem("Tofu", R.mipmap.tofu_round));
    }

    private static void addItem(ShoppingItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ShoppingItem {
        public final String id;
        public final int image_id;
        public boolean is_in_shopping_cart = false;

        public ShoppingItem(String id, int image_id) {
            this.id = id;
            this.image_id = image_id;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
