package com.example.shopper2021;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // initialize constants for DB version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shopper.db";

    // initialize constants for shoppinglist table
    private static final String TABLE_SHOPPING_LIST = "shoppinglist";
    private static final String COLUMN_LIST_ID = "_id";
    private static final String COLUMN_LIST_NAME = "name";
    private static final String COLUMN_LIST_STORE = "store";
    private static final String COLUMN_LIST_DATE = "date";

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
}
