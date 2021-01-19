package com.example.shopper2021;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * The ShoppingListItems class will map the data selected from the shoppinglistitem
 * table to the li_shopping_list_item layout resource.
 */
public class ShoppingListItems extends CursorAdapter {

    /**
     * Initialize ShoppingListItems CursorAdapter.
     * @param context reference to Activity that initializes the ShoppingListItems CursorAdapter
     * @param c reference to Cursor that contains data from database
     * @param flags determines special behavior of the CursorAdapter.  Will always be 0
     *              which means the CursorAdapter shouldn't have any special behavior.
     */
    public ShoppingListItems(Context context, Cursor c, int flags) {
        // call superclass constructor
        super(context, c, flags);
    }

    /**
     * Make a new View to hold the data in the Cursor.
     * @param context reference to Activity that initializes the ShoppingListItems CursorAdapter
     * @param cursor reference to Cursor that contains data from database
     * @param parent reference to shopperListView that will contain the new View
     * @return reference to the new View
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.li_shopping_list_item, parent, false);
    }

    /**
     * Bind new View to data in Cursor.  Called at some point when setAdapter is called on
     * shopperListView.
     * @param view reference to new View
     * @param context reference to Activity that initializes the ShoppingListItems CursorAdapter
     * @param cursor reference to Cursor that contains data from database
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.nameTextView)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
        ((TextView) view.findViewById(R.id.priceTextView)).
                setText(cursor.getString(cursor.getColumnIndex("price")));
        ((TextView) view.findViewById(R.id.quantityTextView)).
                setText(cursor.getString(cursor.getColumnIndex("quantity")));
        ((TextView) view.findViewById(R.id.hasTextView)).
                setText("Item Purchased? " + cursor.getString(cursor.getColumnIndex("item_has")));
    }
}
