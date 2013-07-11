package com.antonio081014.multidatabasetable;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManagerWithProfilePhoto";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_PROFILES = "profiles";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_BELONGTO = "belongto";

    public DatabaseHandler(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
	String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
		+ KEY_ID + " INTEGER PRIMARY KEY autoincrement," + KEY_NAME
		+ " TEXT not null," + KEY_PH_NO + " TEXT not null" + ")";
	db.execSQL(CREATE_CONTACTS_TABLE);

	String CREATE_PROFILEPHOTO_TABLE = "CREATE TABLE " + TABLE_PROFILES
		+ "(" + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
		+ KEY_BELONGTO + " INTEGER," + KEY_NAME + " TEXT not null"
		+ ")";
	db.execSQL(CREATE_PROFILEPHOTO_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// Drop older table if existed
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
	// Create tables again
	onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(Contact contact) {
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put(KEY_NAME, contact.getName()); // Contact Name
	values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone

	// Inserting Row
	db.insert(TABLE_CONTACTS, null, values);
	db.close(); // Closing database connection
    }

    void addProfilePhoto(ProfilePhoto profile) {
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put(KEY_BELONGTO, Integer.toString(profile.get_belongTo()));
	values.put(KEY_NAME, profile.getName());
	// Inserting Row
	db.insert(TABLE_PROFILES, null, values);
	db.close(); // Closing database connection
    }

    // Getting single contact
    Contact getContact(int id) {
	SQLiteDatabase db = this.getReadableDatabase();

	Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
		KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
		new String[] { String.valueOf(id) }, null, null, null, null);
	if (cursor != null)
	    cursor.moveToFirst();

	Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
		cursor.getString(1), cursor.getString(2));
	// return contact
	return contact;
    }

    // Getting All Contacts
    public List<Contact> getAllContacts() {
	List<Contact> contactList = new ArrayList<Contact>();
	// Select All Query
	String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

	SQLiteDatabase db = this.getWritableDatabase();
	Cursor cursor = db.rawQuery(selectQuery, null);

	// looping through all rows and adding to list
	if (cursor.moveToFirst()) {
	    do {
		Contact contact = new Contact(Integer.parseInt(cursor
			.getString(0)), cursor.getString(1),
			cursor.getString(2));
		// Adding contact to list
		contactList.add(contact);
	    } while (cursor.moveToNext());
	}
	return contactList;
    }

    // Updating single contact
    public int updateContact(Contact contact) {
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put(KEY_NAME, contact.getName());
	values.put(KEY_PH_NO, contact.getPhoneNumber());

	// updating row
	return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
		new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
	SQLiteDatabase db = this.getWritableDatabase();
	db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
		new String[] { String.valueOf(contact.getID()) });
	db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
	String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.rawQuery(countQuery, null);
	cursor.close();

	// return count
	return cursor.getCount();
    }

    public List<ProfilePhoto> getAllProfilesWithContact(int contactID) {
	List<ProfilePhoto> profileList = new ArrayList<ProfilePhoto>();
	String selectQ = "SELECT  * FROM " + TABLE_PROFILES + " WHERE "
		+ KEY_BELONGTO + "=" + String.valueOf(contactID) + ";";

	SQLiteDatabase db = this.getWritableDatabase();
	Cursor cursor = db.rawQuery(selectQ, null);

	Cursor cursor2 = db.query(TABLE_PROFILES, new String[] { KEY_ID,
		KEY_BELONGTO, KEY_NAME }, KEY_BELONGTO + "=?",
		new String[] { String.valueOf(contactID) }, null, null, null,
		null);

	if (cursor.moveToFirst()) {
	    do {
		ProfilePhoto profile = new ProfilePhoto(Integer.parseInt(cursor
			.getString(1)), cursor.getString(2));
		profileList.add(profile);
	    } while (cursor.moveToNext());
	}
	return profileList;
    }

    public List<ProfilePhoto> getAllProfiles() {
	List<ProfilePhoto> profileList = new ArrayList<ProfilePhoto>();
	String selectQ = "SELECT  * FROM " + TABLE_PROFILES;

	SQLiteDatabase db = this.getWritableDatabase();
	Cursor cursor = db.rawQuery(selectQ, null);

	if (cursor.moveToFirst()) {
	    do {
		ProfilePhoto profile = new ProfilePhoto(Integer.parseInt(cursor
			.getString(cursor.getColumnIndex(KEY_ID))),
			Integer.parseInt(cursor.getString(cursor
				.getColumnIndex(KEY_BELONGTO))),
			cursor.getString(cursor.getColumnIndex(KEY_NAME)));
		profileList.add(profile);
	    } while (cursor.moveToNext());
	}
	return profileList;
    }
}
