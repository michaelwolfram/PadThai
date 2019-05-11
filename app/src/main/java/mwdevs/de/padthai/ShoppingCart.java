package mwdevs.de.padthai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ShoppingCart extends AppCompatActivity implements ShoppingListFragment.OnListFragmentInteractionListener {

    private int paste_quantity;
    private int sosse_quantity;
    private int padthai_quantity;

    public int getPaste_quantity() {
        return paste_quantity;
    }

    public int getSosse_quantity() {
        return sosse_quantity;
    }

    public int getPadthai_quantity() {
        return padthai_quantity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        paste_quantity = intent.getIntExtra(ShoppingListFragment.PASTE_QUANTITY, 0);
        sosse_quantity = intent.getIntExtra(ShoppingListFragment.SOSSE_QUANTITY, 0);
        padthai_quantity = intent.getIntExtra(ShoppingListFragment.PADTHAI_QUANTITY, 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
    }

    @Override
    public void onListFragmentInteraction(ShoppingListContent.ShoppingItem item) {
    }
}
