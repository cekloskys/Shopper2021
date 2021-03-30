package com.example.shopper2021

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

/**
 * The ShoppingListItems class will map the data selected from the shoppinglistitem
 * table to the li_shopping_list_item layout resource.
 */
class ShoppingListItems
/**
 * Initialize ShoppingListItems CursorAdapter.
 * @param context reference to Activity that initializes the ShoppingListItems CursorAdapter
 * @param c reference to Cursor that contains data from database
 * @param flags determines special behavior of the CursorAdapter.  Will always be 0
 * which means the CursorAdapter shouldn't have any special behavior.
 */
(context: Context?, c: Cursor?, flags: Int) : CursorAdapter(context, c, flags) {
    /**
     * Make a new View to hold the data in the Cursor.
     * @param context reference to Activity that initializes the ShoppingListItems CursorAdapter
     * @param cursor reference to Cursor that contains data from database
     * @param parent reference to shopperListView that will contain the new View
     * @return reference to the new View
     */
    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.li_shopping_list_item, parent, false)
    }

    /**
     * Bind new View to data in Cursor.  Called at some point when setAdapter is called on
     * shopperListView.
     * @param view reference to new View
     * @param context reference to Activity that initializes the ShoppingListItems CursorAdapter
     * @param cursor reference to Cursor that contains data from database
     */
    override fun bindView(view: View, context: Context, cursor: Cursor) {
        (view.findViewById<View>(R.id.nameTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("name"))
        (view.findViewById<View>(R.id.priceTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("price"))
        (view.findViewById<View>(R.id.quantityTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("quantity"))
        (view.findViewById<View>(R.id.hasTextView) as TextView).text = "Item Purchased? " + cursor.getString(cursor.getColumnIndex("item_has"))
    }
}