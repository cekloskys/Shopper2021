package com.example.shopper2021;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // declare an Intent
    Intent intent;

    // declare a Bundle and a long for data passed from MainActivity in Bundle
    Bundle bundle;
    long id;

    // declare EditTexts
    EditText nameEditText;
    EditText priceEditText;

    // declare Spinner
    Spinner quantitySpinner;

    // declare String to store quantity selected in Spinner
    String quantity;

    // declare a DBHandler
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize Bundle
        bundle = this.getIntent().getExtras();

        // get shopping list id passed in Bundle and store it in long
        id = bundle.getLong("_id");

        // initialize EditTexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);

        // initialize Spinner
        quantitySpinner = (Spinner) findViewById(R.id.quantitySpinner);

        // initialize ArrayAdapter with values in quantities string array
        // and stylize it with style defined by simple_spinner_item
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quantities, android.R.layout.simple_spinner_item);

        // further stylize ArrayAdapter with style defined by simple_spinner_dropdown_item
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // set ArrayAdapter on Spinner
        quantitySpinner.setAdapter(adapter);

        // register On Item Selected Listener to Spinner
        quantitySpinner.setOnItemSelectedListener(this);

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);
    }

    /**
     * This method further initializes the Action Bar of the AddItem Activity.
     * It gets the code in the menu main resource file and incorporates it
     * into the Action Bar.
     * @param menu menu add item resource file
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu view list; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }

    /**
     * This method gets called when a menu item in the overflow menu is
     * selected and it controls what happens when the menu item is selected.
     * @param item selected menu item in overflow menu
     * @return true if menu item is selected, else false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // get the id of the menu item selected
        switch (item.getItemId()) {
            case R.id.action_home :
                // initialize an Intent for the MainActivity and start it
                // if the id is for the MainActivity
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_create_list :
                // initialize an Intent for the CreateList Activity and start it
                // if the id is for the CreateList Activity
                intent = new Intent(this, CreateList.class);
                startActivity(intent);
                return true;
            case R.id.action_view_list:
                // initialize an Intent for the ViewList Activity and start it
                // if the id is for the CreateList Activity
                intent = new Intent(this, ViewList.class);
                // put the database id in the Intent
                intent.putExtra("_id", id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the add button in the Action Bar gets clicked.
     * @param menuItem add item menu item
     */
    public void addItem(MenuItem menuItem) {

        // get data input in EditTexts and store it in Strings
        String name = nameEditText.getText().toString();
        String price = priceEditText.getText().toString();

        // trim Strings and see if they're equal to empty Strings
        if (name.trim().equals("") || price.trim().equals("") || quantity.trim().equals("")){
            // display "Please enter a name, store, and date!" Toast if any of the Strings are empty
            Toast.makeText(this, "Please enter a name, price, and quantity!", Toast.LENGTH_LONG).show();
        } else {
            // add item into database
            dbHandler.addItemToList(name, Double.parseDouble(price), Integer.parseInt(quantity), (int) id);

            // display "Shopping list created!" Toast of none of the Strings are empty
            Toast.makeText(this, "Item added!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method gets called when an item in the Spinner is selected.
     * @param parent Spinner AdapterView
     * @param view AddItem view
     * @param position position of item in Spinner that was selected
     * @param id database id of item in Spinner that was selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        quantity = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}