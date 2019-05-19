package mwdevs.de.padthai;

public interface OnListInteractionListener {
    void onListItemClick(ShoppingListContent.ShoppingItem item);

    void onListItemLongClick(ShoppingListContent.ShoppingItem item);
}
