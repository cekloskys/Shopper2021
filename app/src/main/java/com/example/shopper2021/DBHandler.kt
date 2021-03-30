package com.example.shopper2021

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class DBHandler
/**
 * Initializes a DBHandler.  Creates version of the database.
 * @param context reference to an activity that initializes the
 * DBHandler
 * @param cursorFactory null
 */
(context: Context?, cursorFactory: CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION) {
    /**
     * Creates database tables.
     * @param db reference to shopper database
     */
    override fun onCreate(db: SQLiteDatabase) {

        // define create statement for shoppinglist table
        val query = "CREATE TABLE " + TABLE_SHOPPING_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_STORE + " TEXT, " +
                COLUMN_LIST_DATE + " TEXT" +
                ");"

        // execute create statement
        db.execSQL(query)

        // define create statement for shoppinglist table
        val query2 = "CREATE TABLE " + TABLE_SHOPPING_LIST_ITEM + "(" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_ITEM_PRICE + " DECIMAL(10,2), " +
                COLUMN_ITEM_QUANTITY + " INTEGER, " +
                COLUMN_ITEM_HAS + " TEXT, " +
                COLUMN_ITEM_LIST_ID + " INTEGER" +
                ");"

        // execute create statement
        db.execSQL(query2)
    }

    /**
     * Creates a new version of the database.
     * @param db reference to shopper database
     * @param oldVersion old version of database
     * @param newVersion new version of database
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        // define drop statement
        val query = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST

        // execute drop statement that drops shoppinglist table
        db.execSQL(query)

        // define drop statement
        val query2 = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST_ITEM

        // execute drop statement that drops shoppinglistitem table
        db.execSQL(query2)

        // call method that creates tables
        onCreate(db)
    }

    /**
     * This method gets called when the add button in the Action Bar of the
     * CreateList Activity gets clicked.  It inserts a new row in the
     * shoppinglist table.
     * @param name shopping list name
     * @param store shopping list store
     * @param date shopping list date
     */
    fun addShoppingList(name: String?, store: String?, date: String?) {

        // get reference to shopper database
        val db = writableDatabase

        // initialize a ContentValues object
        val values = ContentValues()

        // put data into ContentValues object
        values.put(COLUMN_LIST_NAME, name)
        values.put(COLUMN_LIST_STORE, store)
        values.put(COLUMN_LIST_DATE, date)

        // insert data in ContentValues object into shoppinglist table
        db.insert(TABLE_SHOPPING_LIST, null, values)

        // close database reference
        db.close()
    }// get reference to shopper database

    // define select statement

    // execute select statement and return it as a Cursor
    /**
     * This method gets called when the MainActivity is created.
     * @return Cursor that contains all rows in shoppinglist table.
     */
    val shoppingLists: Cursor
        get() {

            // get reference to shopper database
            val db = writableDatabase

            // define select statement
            val query = "SELECT * FROM " + TABLE_SHOPPING_LIST

            // execute select statement and return it as a Cursor
            return db.rawQuery(query, null)
        }

    /**
     * This method gets called when the ViewList Activity is created.
     * @param id shopping list id
     * @return shopping list name
     */
    fun getShoppingListName(id: Int): String {

        // get reference to shopper database
        val db = writableDatabase

        // declare and initialize Sting that will be returned by method
        var name = ""

        // define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id

        // execute select statement and store it in a Cursor
        val cursor = db.rawQuery(query, null)

        // move to the first row in the Cursor
        cursor.moveToFirst()

        // get the String in the name column of the Cursor and check
        // if it's not null
        if (cursor.getString(cursor.getColumnIndex("name")) != null) {
            name = cursor.getString(cursor.getColumnIndex("name"))
        }

        // close reference to shopper database
        db.close()

        // return shopping list name
        return name
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
    fun addItemToList(name: String?, price: Double?, quantity: Int?, listId: Int?) {

        // get reference to shopper database
        val db = writableDatabase

        // initialize a ContentValues object
        val values = ContentValues()

        // put data into ContentValues object
        values.put(COLUMN_ITEM_NAME, name)
        values.put(COLUMN_ITEM_PRICE, price)
        values.put(COLUMN_ITEM_QUANTITY, quantity)
        values.put(COLUMN_ITEM_HAS, "false")
        values.put(COLUMN_ITEM_LIST_ID, listId)

        // insert data in ContentValues object into shoppinglist table
        db.insert(TABLE_SHOPPING_LIST_ITEM, null, values)

        // close database reference
        db.close()
    }

    /**
     * This method gets called when the ViewList Activity is created.
     * @param listId shopping list id
     * @return Cursor that contains all of the items associated with
     * the specified shopping list id
     */
    fun getShoppingListItems(listId: Int): Cursor {

        // get reference to shopper database
        val db = writableDatabase

        // define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId

        // execute select statement and return it as a Cursor
        return db.rawQuery(query, null)
    }

    /**
     * This method gets called when a shopping list item is selected.
     * @param itemId id of selected shopping list item
     * @return 1 if the selected shopping list item is
     * un-purchased, else 0.
     */
    fun isItemUnPurchased(itemId: Int): Int {

        // get reference to shopper database
        val db = writableDatabase

        // define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_HAS + " = \"false\" " +
                " AND " + COLUMN_ITEM_ID + " = " + itemId

        // execute select statement and store result in a Cursor
        val cursor = db.rawQuery(query, null)

        // return a count of the rows in the Cursor
        return cursor.count
    }

    /**
     * This method gets called when an un-purchased shopping list item is selected.
     * @param itemId id of selected shopping list item
     */
    fun updateItem(itemId: Int) {

        // get reference to shopper database
        val db = writableDatabase

        // define update statement
        val query = "UPDATE " + TABLE_SHOPPING_LIST_ITEM + " SET " +
                COLUMN_ITEM_HAS + " = \"true\" " + " WHERE " +
                COLUMN_ITEM_ID + " = " + itemId

        // execute update statement
        db.execSQL(query)

        // close database reference
        db.close()
    }

    /**
     * This method gets called when the ViewItem Activity is created.
     * @param itemId shopping list item id
     * @return Cursor that contains all of the data associated with
     * the specified shopping list item id
     */
    fun getShoppingListItem(itemId: Int): Cursor {

        // get reference to shopper database
        val db = writableDatabase

        // define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_ID + " = " + itemId

        // execute select statement and return it as a Cursor
        return db.rawQuery(query, null)
    }

    /**
     * This method gets called when the delete button in the Action Bar of the
     * ViewItem Activity gets clicked.  It deletes a row in the
     * shoppinglistitem table.
     * @param itemId id of shopping list item
     */
    fun deleteShoppingListItem(itemId: Int) {

        // get reference to shopper database
        val db = writableDatabase

        // define delete statement
        val query = "DELETE FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_ID + " = " + itemId

        // execute delete statement
        db.execSQL(query)

        // close database reference
        db.close()
    }

    /**
     * This method gets called when the delete button in the Action Bar of the
     * ViewList Activity gets clicked.  It deletes a row in the
     * shoppinglistitem and shoppinglist tables.
     * @param listId id of shopping list
     */
    fun deleteShoppingList(listId: Int) {

        // get reference to shopper database
        val db = writableDatabase

        // define delete statement to delete from shoppinglistitem table
        val query1 = "DELETE FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId

        // execute delete statement
        db.execSQL(query1)

        // define delete statement to delete from shoppinglist table
        val query2 = "DELETE FROM " + TABLE_SHOPPING_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + listId

        // execute delete statement
        db.execSQL(query2)

        // close database reference
        db.close()
    }

    /**
     * This method gets called when the ViewList Activity is created.
     * @param listId shopping list id
     * @return Shopping list total cost
     */
    fun getShoppingListTotalCost(listId: Int): String {

        // get reference to shopper database
        val db = writableDatabase

        // declare and initialize String that will be returned by method
        var cost = ""

        // declare a Double that will be used to compute total cost
        var totalCost = 0.0

        // define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId

        // execute select statement and store its return in a Cursor
        val c = db.rawQuery(query, null)

        // loop through the rows in the Cursor
        while (c.moveToNext()) {
            // add the cost of the current row into the total cost
            totalCost += c.getDouble(c.getColumnIndex("price")) *
                    c.getInt(c.getColumnIndex("quantity"))
        }

        // convert the total cost to a String
        cost = totalCost.toString()

        // close database reference
        db.close()

        // return String
        return cost
    }

    /**
     * This method gets called when a shopping list item is selected.
     * @param listId id of selected shopping list item
     * @return number of un-purchased shopping list items
     */
    fun getUnpurchasedItems(listId: Int): Int {

        // get reference to shopper database
        val db = writableDatabase

        // define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_HAS + " = \"false\" " +
                " AND " + COLUMN_ITEM_LIST_ID + " = " + listId

        // execute select statement and store its return in a Cursor
        val cursor = db.rawQuery(query, null)

        // return count of number of rows in Cursor
        return cursor.count
    }

    companion object {
        // initialize constants for DB version and name
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "shopper.db"

        // initialize constants for shoppinglist table
        private const val TABLE_SHOPPING_LIST = "shoppinglist"
        private const val COLUMN_LIST_ID = "_id"
        private const val COLUMN_LIST_NAME = "name"
        private const val COLUMN_LIST_STORE = "store"
        private const val COLUMN_LIST_DATE = "date"

        // initialize constants for shoppinglistitem table
        private const val TABLE_SHOPPING_LIST_ITEM = "shoppinglistitem"
        private const val COLUMN_ITEM_ID = "_id"
        private const val COLUMN_ITEM_NAME = "name"
        private const val COLUMN_ITEM_PRICE = "price"
        private const val COLUMN_ITEM_QUANTITY = "quantity"
        private const val COLUMN_ITEM_HAS = "item_has"
        private const val COLUMN_ITEM_LIST_ID = "list_id"
    }
}