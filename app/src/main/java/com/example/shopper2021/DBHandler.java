package com.example.shopper2021;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // initialize constants for DB version and name
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "shopper.db";

    // initialize constants for shoppinglist table
    private static final String TABLE_SHOPPING_LIST = "shoppinglist";
    private static final String COLUMN_LIST_ID = "_id";
    private static final String COLUMN_LIST_NAME = "name";
    private static final String COLUMN_LIST_STORE = "store";
    private static final String COLUMN_LIST_DATE = "date";

    // initialize constants for shoppinglistitem table
    private static final String TABLE_SHOPPING_LIST_ITEM = "shoppinglistitem";
    private static final String COLUMN_ITEM_ID = "_id";
    private static final String COLUMN_ITEM_NAME = "name";
    private static final String COLUMN_ITEM_PRICE = "price";
    private static final String COLUMN_ITEM_QUANTITY = "quantity";
    private static final String COLUMN_ITEM_HAS = "item_has";
    private static final String COLUMN_ITEM_LIST_ID = "list_id";

    /**
     * Initializes a DBHandler.  Creates version of the database.
     * @param context reference to an activity that initializes the
     *                DBHandler
     * @param cursorFactory null
     */
    public DBHandler (Context context, SQLiteDatabase.CursorFactory cursorFactory) {

        // call superclass constructor
        super(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION);
    }

    /**
     * Creates database tables.
     * @param db reference to shopper database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // define create statement for shoppinglist table
        String query = "CREATE TABLE " + TABLE_SHOPPING_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_STORE + " TEXT, " +
                COLUMN_LIST_DATE + " TEXT" +
                ");";

        // execute create statement
        db.execSQL(query);

        // define create statement for shoppinglist table
        String query2 = "CREATE TABLE " + TABLE_SHOPPING_LIST_ITEM + "(" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_ITEM_PRICE + " DECIMAL(10,2), " +
                COLUMN_ITEM_QUANTITY + " INTEGER, " +
                COLUMN_ITEM_HAS + " TEXT, " +
                COLUMN_ITEM_LIST_ID + " INTEGER" +
                ");";

        // execute create statement
        db.execSQL(query2);
    }

    /**
     * Creates a new version of the database.
     * @param db reference to shopper database
     * @param oldVersion old version of database
     * @param newVersion new version of database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // define drop statement
        String query = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST;

        // execute drop statement that drops shoppinglist table
        db.execSQL(query);

        // define drop statement
        String query2 = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST_ITEM;

        // execute drop statement that drops shoppinglistitem table
        db.execSQL(query2);

        // call method that creates tables
        onCreate(db);
    }

    /**
     * This method gets called when the add button in the Action Bar of the
     * CreateList Activity gets clicked.  It inserts a new row in the
     * shoppinglist table.
     * @param name shopping list name
     * @param store shopping list store
     * @param date shopping list date
     */
    public void addShoppingList(String name, String store, String date) {

        // get reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // initialize a ContentValues object
        ContentValues values = new ContentValues();

        // put data into ContentValues object
        values.put(COLUMN_LIST_NAME, name);
        values.put(COLUMN_LIST_STORE, store);
        values.put(COLUMN_LIST_DATE, date);

        // insert data in ContentValues object into shoppinglist table
        db.insert(TABLE_SHOPPING_LIST, null, values);

        // close database reference
        db.close();
    }

    /**
     * This method gets called when the MainActivity is created.
     * @return Cursor that contains all rows in shoppinglist table.
     */
    public Cursor getShoppingLists() {

        // get reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST;

        // execute select statement and return it as a Cursor
        return db.rawQuery(query, null);
    }

    /**
     * This method gets called when the ViewList Activity is created.
     * @param id shopping list id
     * @return shopping list name
     */
    public String getShoppingListName(int id) {

        // get reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // declare and initialize Sting that will be returned by method
        String name = "";

        // define select statement
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        // execute select statement and store it in a Cursor
        Cursor cursor = db.rawQuery(query, null);

        // move to the first row in the Cursor
        cursor.moveToFirst();

        // get the String in the name column of the Cursor and check
        // if it's not null
        if ((cursor.getString(cursor.getColumnIndex("name")) != null)){
            name = cursor.getString(cursor.getColumnIndex("name"));
        }

        // close reference to shopper database
        db.close();

        // return shopping list name
        return name;
    }

    /**
     * This method gets called when the add button in the Action Bar of the
     * AddItem Activity gets clicked.  It inserts a new row in the
     * shoppinglistitem table.
     * @param name item name
     * @param price item price
     * @param quantity item quantity
     * @param listId id of shopping list to which item is being added
     */
    public void addItemToList(String name, Double price, Integer quantity, Integer listId) {

        // get reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // initialize a ContentValues object
        ContentValues values = new ContentValues();

        // put data into ContentValues object
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_PRICE, price);
        values.put(COLUMN_ITEM_QUANTITY, quantity);
        values.put(COLUMN_ITEM_HAS, "false");
        values.put(COLUMN_ITEM_LIST_ID, listId);

        // insert data in ContentValues object into shoppinglist table
        db.insert(TABLE_SHOPPING_LIST_ITEM, null, values);

        // close database reference
        db.close();
    }

    /**
     * This method gets called when the ViewList Activity is created.
     * @param listId shopping list id
     * @return Cursor that contains all of the items associated with
     * the specified shopping list id
     */
    public Cursor getShoppingListItems(Integer listId) {

        // get reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId;

        // execute select statement and return it as a Cursor
        return db.rawQuery(query, null);
    }

    /**
     * This method gets called when a shopping list item is selected.
     * @param itemId id of selected shopping list item
     * @return 1 if the selected shopping list item is
     * un-purchased, else 0.
     */
    public int isItemUnPurchased(Integer itemId) {

        // get reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_HAS + " = \"false\" " +
                " AND " + COLUMN_ITEM_ID + " = " + itemId;

        // execute select statement and store result in a Cursor
        Cursor cursor = db.rawQuery(query, null);

        // return a count of the rows in the Cursor
        return (cursor.getCount());
    }

    /**
     * This method gets called when an un-purchased shopping list item is selected.
     * @param itemId id of selected shopping list item
     */
    public void updateItem(Integer itemId) {

        // get reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define update statement
        String query = "UPDATE " + TABLE_SHOPPING_LIST_ITEM + " SET " +
                COLUMN_ITEM_HAS + " = \"true\" " + " WHERE " +
                COLUMN_ITEM_ID + " = " + itemId;

        // execute update statement
        db.execSQL(query);

        // close database reference
        db.close();
    }

    /**
     * This method gets called when the ViewItem Activity is created.
     * @param itemId shopping list item id
     * @return Cursor that contains all of the data associated with
     * the specified shopping list item id
     */
    public Cursor getShoppingListItem (Integer itemId) {

        // get reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_ID + " = " + itemId;

        // execute select statement and return it as a Cursor
        return db.rawQuery(query, null);
    }

    /**
     * This method gets called when the delete button in the Action Bar of the
     * ViewItem Activity gets clicked.  It deletes a row in the
     * shoppinglistitem table.
     * @param itemId id of shopping list item
     */
    public void deleteShoppingListItem (Integer itemId){

        // get reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define delete statement
        String query = "DELETE FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_ID + " = " + itemId;

        // execute delete statement
        db.execSQL(query);

        // close database reference
        db.close();
    }

    /**
     * This method gets called when the delete button in the Action Bar of the
     * ViewList Activity gets clicked.  It deletes a row in the
     * shoppinglistitem and shoppinglist tables.
     * @param listId id of shopping list
     */
    public void deleteShoppingList(Integer listId) {

        // get reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define delete statement to delete from shoppinglistitem table
        String query1 = "DELETE FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId;

        // execute delete statement
        db.execSQL(query1);

        // define delete statement to delete from shoppinglist table
        String query2 = "DELETE FROM " + TABLE_SHOPPING_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + listId;

        // execute delete statement
        db.execSQL(query2);

        // close database reference
        db.close();
    }

    /**
     * This method gets called when the ViewList Activity is created.
     * @param listId shopping list id
     * @return Shopping list total cost
     */
    public String getShoppingListTotalCost(int listId){

        // get reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // declare and initialize String that will be returned by method
        String cost = "";

        // declare a Double that will be used to compute total cost
        Double totalCost = 0.0;

        // define select statement
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId;

        // execute select statement and store its return in a Cursor
        Cursor c = db.rawQuery(query, null);

        // loop through the rows in the Cursor
        while (c.moveToNext()) {
            // add the cost of the current row into the total cost
            totalCost += (c.getDouble(c.getColumnIndex("price")) *
                    (c.getInt(c.getColumnIndex("quantity"))));
        }

        // convert the total cost to a String
        cost = String.valueOf(totalCost);

        // close database reference
        db.close();

        // return String
        return cost;
    }

    /**
     * This method gets called when a shopping list item is selected.
     * @param listId id of selected shopping list item
     * @return number of un-purchased shopping list items
     */
    public int getUnpurchasedItems (Integer listId){

        // get reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_HAS + " = \"false\" " +
                " AND " + COLUMN_ITEM_LIST_ID + " = " + listId;

        // execute select statement and store its return in a Cursor
        Cursor cursor = db.rawQuery(query, null);

        // return count of number of rows in Cursor
        return (cursor.getCount());
    }
}
