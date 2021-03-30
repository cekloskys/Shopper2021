package com.example.shopper2021

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ViewItem : AppCompatActivity() {
    // declare an Intent
    // var intent: Intent? = null

    // declare a Bundle and a long for data passed from ViewList in Bundle
    var bundle: Bundle? = null
    var id: Long = 0
    var listId: Long = 0

    // declare a DBHandler
    var dbHandler: DBHandler? = null

    // declare EditTexts
    var nameEditText: EditText? = null
    var priceEditText: EditText? = null
    var quantityEditText: EditText? = null

    // declare Strings for selected shopping list's name, price, and quantity
    var name: String? = null
    var price: String? = null
    var quantity: String? = null

    /**
     * This method initializes the Action Bar and View of the ViewItem Activity.
     * @param savedInstanceState a Bundle object that is passed into the
     * onCreate method of every Android Activity.
     * Activities have the ability, under special
     * circumstances, to restore themselves to a
     * previous state using the data stored in this
     * object.  If there is no available instance
     * data, the object will be null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // code generated by Android Studio that initializes the View and
        // Action Bar of the ViewList Activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // initialize Bundle
        bundle = getIntent().extras

        // get shopping list id passed in Bundle and store it in long
        id = bundle!!.getLong("_id")

        // get shopping list id passed in Bundle and store it in long
        listId = bundle!!.getLong("_list_id")

        // initialize DBHandler
        dbHandler = DBHandler(this, null)

        // initialize EditTexts
        nameEditText = findViewById<View>(R.id.nameEditText) as EditText
        priceEditText = findViewById<View>(R.id.priceEditText) as EditText
        quantityEditText = findViewById<View>(R.id.quantityEditText) as EditText

        // disable EditTexts
        nameEditText!!.isEnabled = false
        priceEditText!!.isEnabled = false
        quantityEditText!!.isEnabled = false

        // get shopping list item selected in ViewList Activity
        val cursor = dbHandler!!.getShoppingListItem(id.toInt())

        // move to the first row in the Cursor
        cursor.moveToFirst()

        // get the name, price, and quantity data in the Cursor
        name = cursor.getString(cursor.getColumnIndex("name"))
        price = java.lang.Double.toString(cursor.getDouble(cursor.getColumnIndex("price")))
        quantity = Integer.toString(cursor.getInt(cursor.getColumnIndex("quantity")))

        // set the name, price, and quantity data in the Cursor in the EditTexts
        nameEditText!!.setText(name)
        priceEditText!!.setText(price)
        quantityEditText!!.setText(quantity)
    }

    /**
     * This method further initializes the Action Bar of the ViewItem Activity.
     * It gets the code in the menu main resource file and incorporates it
     * into the Action Bar.
     * @param menu menu view list resource file
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu view item; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_view_item, menu)
        return true
    }

    /**
     * This method gets called when a menu item in the overflow menu is
     * selected and it controls what happens when the menu item is selected.
     * @param item selected menu item in overflow menu
     * @return true if menu item is selected, else false
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // get the id of the menu item selected
        return when (item.itemId) {
            R.id.action_home -> {
                // initialize an Intent for the MainActivity and start it
                // if the id is for the MainActivity
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_create_list -> {
                // initialize an Intent for the CreateList Activity and start it
                // if the id is for the CreateList Activity
                intent = Intent(this, CreateList::class.java)
                startActivity(intent)
                true
            }
            R.id.action_add_item -> {
                // initialize an Intent for the AddItem Activity
                intent = Intent(this, AddItem::class.java)
                // put the database id in the Intent
                intent!!.putExtra("_id", id)
                // start the Activity
                startActivity(intent)
                true
            }
            R.id.action_view_list -> {
                // initialize an Intent for the ViewList Activity
                intent = Intent(this, ViewList::class.java)
                // put the database id in the Intent
                intent!!.putExtra("_id", listId)
                // start the Activity
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method gets called when the delete button in the Action Bar of the
     * ViewItem Activity gets clicked.  It deletes a row in the
     * shoppinglistitem table.
     * @param menuItem delete item menu item
     */
    fun deleteItem(menuItem: MenuItem?) {

        // delete shopping list item from database
        dbHandler!!.deleteShoppingListItem(id.toInt())

        // display "Item Deleted!" Toast
        Toast.makeText(this, "Item Deleted!", Toast.LENGTH_LONG).show()
    }
}