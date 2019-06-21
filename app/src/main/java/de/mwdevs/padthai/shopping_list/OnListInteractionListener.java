package de.mwdevs.padthai.shopping_list;

import de.mwdevs.padthai.shopping_list.data.ShoppingListContent;

public interface OnListInteractionListener {
    void onListItemClick(ShoppingListContent.ShoppingItem item);

    void onListItemLongClick(ShoppingListContent.ShoppingItem item);
}
