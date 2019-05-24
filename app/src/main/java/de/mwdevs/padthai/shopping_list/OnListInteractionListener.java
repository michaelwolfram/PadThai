package de.mwdevs.padthai.shopping_list;

public interface OnListInteractionListener {
    void onListItemClick(ShoppingListContent.ShoppingItem item);

    void onListItemLongClick(ShoppingListContent.ShoppingItem item);
}
