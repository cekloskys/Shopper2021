package com.example.shopper2021

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AddItem : AppCompatActivity(), OnItemSelectedListener {
    // declare an Intent
    // var intent: Intent? = null

    // declare a Bundle and a long for data passed from MainActivity in Bundle
    var bundle: Bundle? = null
    var id: Long = 0

    // declare EditTexts
    var nameEditText: EditText? = null
    var priceEditText: EditText? = null

    // declare Spinner
    var quantitySpinner: Spinner? = null

    // declare String to store quantity selected in Spinner
    var quantity: String? = null

    // declare a DBHandler
    var dbHandler: DBHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // initialize Bundle
        bundle = getIntent().extras

        // get shopping list id passed in Bundle and store it in long
        id = bundle!!.getLong("_id")

        // initialize EditTexts
        nameEditText = findViewById<View>(R.id.nameEditText) as EditText
        priceEditText = findViewById<View>(R.id.priceEditText) as EditText

        // initialize Spinner
        quantitySpinner = findViewById<View>(R.id.quantitySpinner) as Spinner

        // initialize ArrayAdapter with values in quantities string array
        // and stylize it with style defined by simple_spinner_item
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.quantities, android.R.layout.simple_spinner_item)

        // further stylize ArrayAdapter with style defined by simple_spinner_dropdown_item
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        // set ArrayAdapter on Spinner
        quantitySpinner!!.adapter = adapter

        // register On Item Selected Listener to Spinner
        quantitySpinner!!.onItemSelectedListener = this

        // initialize DBHandler
        dbHandler = DBHandler(this, null)
    }

    /**
     * This method further initializes the Action Bar of the AddItem Activity.
     * It gets the code in the menu main resource file and incorporates it
     * into the Action Bar.
     * @param menu menu add item resource file
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu view list; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_add_item, menu)
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
            R.id.action_view_list -> {
                // initialize an Intent for the ViewList Activity and start it
                // if the id is for the CreateList Activity
                intent = Intent(this, ViewList::class.java)
                // put the database id in the Intent
                intent!!.putExtra("_id", id)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method gets called when the add button in the Action Bar gets clicked.
     * @param menuItem add item menu item
     */
    fun addItem(menuItem: MenuItem?) {

        // get data input in EditTexts and store it in Strings
        val name = nameEditText!!.text.toString()
        val price = priceEditText!!.text.toString()

        // trim Strings and see if they're equal to empty Strings
        if (name.trim { it <= ' ' } == "" || price.trim { it <= ' ' } == "" || quantity!!.trim { it <= ' ' } == "") {
            // display "Please enter a name, store, and date!" Toast if any of the Strings are empty
            Toast.makeText(this, "Please enter a name, price, and quantity!", Toast.LENGTH_LONG).show()
        } else {
            // add item into database
            dbHandler!!.addItemToList(name, price.toDouble(), quantity!!.toInt(), id.toInt())

            // display "Shopping list created!" Toast of none of the Strings are empty
            Toast.makeText(this, "Item added!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * This method gets called when an item in the Spinner is selected.
     * @param parent Spinner AdapterView
     * @param view AddItem view
     * @param position position of item in Spinner that was selected
     * @param id database id of item in Spinner that was selected
     */
    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        quantity = parent.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}