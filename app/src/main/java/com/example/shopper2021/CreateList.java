package com.example.shopper2021;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateList extends AppCompatActivity {

    // declare an Intent
    Intent intent;

    // declare EditTexts
    EditText nameEditText;
    EditText storeEditText;
    EditText dateEditText;

    // declare Calendar
    Calendar calendar;

    // declare a DBHandler
    DBHandler dbHandler;

    /**
     * This method initializes the Action Bar and View of the CreateList Activity.
     * @param savedInstanceState a Bundle object that is passed into the
     *                           onCreate method of every Android Activity.
     *                           Activities have the ability, under special
     *                           circumstances, to restore themselves to a
     *                           previous state using the data stored in this
     *                           object.  If there is no available instance
     *                           data, the object will be null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // code generated by Android Studio that initializes the View and
        // Action Bar of the CreateList Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize EditTexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        storeEditText = (EditText) findViewById(R.id.storeEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);

        // initialize Calendar
        calendar = Calendar.getInstance();

        // initialize DatePickerDialog and register an On Date Set Listener to it
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            /**
             * This method handles the On Date Set event.
             * @param view DatePickerDialog View
             * @param year selected year
             * @param month selected month
             * @param dayOfMonth selected day
             */
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // set the Calendar year, month, and day of month to year, month, and
                // day of month selected in DatePickerDialog
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // call method that updates Date EditText with date in Calendar
                updateDueDate();
            }
        };

        // register an On Click (double click) Listener to the date EditText
        dateEditText.setOnClickListener(new View.OnClickListener() {
            /**
             * This method handles the On Click event.
             * @param v CreateList view
             */
            @Override
            public void onClick(View v) {
                // display DatePickerDialog with current date selected
                new DatePickerDialog(CreateList.this,
                        date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);
    }

    /**
     * This method gets called when a date is set in DatePickerDialog.
     */
    private void updateDueDate() {

        // create a format for the date in the calendar
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // apply format to date in calendar and set formatted date in date EditText
        dateEditText.setText(simpleDateFormat.format(calendar.getTime()));
    }

    /**
     * This method further initializes the Action Bar of the CreateList Activity.
     * It gets the code in the menu create list resource file and incorporates it
     * into the Action Bar.
     * @param menu menu create list resource file
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu create list; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_list, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the add button in the Action Bar gets clicked.
     * @param menuItem add list menu item
     */
    public void createList(MenuItem menuItem) {

        // get data input in EditTexts and store it in Strings
        String name = nameEditText.getText().toString();
        String store = storeEditText.getText().toString();
        String date = dateEditText.getText().toString();

        // trim Strings and see if they're equal to empty Strings
        if (name.trim().equals("") || store.trim().equals("") || date.trim().equals("")){
            // display "Please enter a name, store, and date!" Toast if any of the Strings are empty
            Toast.makeText(this, "Please enter a name, store, and date!", Toast.LENGTH_LONG).show();
        } else {
            // add shopping list into database
            dbHandler.addShoppingList(name, store, date);

            // display "Shopping list created!" Toast of none of the Strings are empty
            Toast.makeText(this, "Shopping list created!", Toast.LENGTH_LONG).show();
        }
    }
}