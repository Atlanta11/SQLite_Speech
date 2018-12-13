package com.ahotbrew.listviewsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "MyNotes";
	private static final String DATABASE_TABLE_NAME = "todo";
	public static final String COLID = "MyNotesID";
	public static final String COLTITLE = "Title";

	DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This runs once after the installation and creates a database
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Here we are creating two columns in our database.
		// MyNotesID, which is the primary key and Title which will hold the
		// todo text
		db.execSQL("CREATE TABLE " + DATABASE_TABLE_NAME + " (" + COLID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLTITLE + " TEXT)");

	}

	/**
	 * This would run after the user updates the app. This is in case you want
	 * to modify the database
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	/**
	 * This method adds a record to the database. All we pass in is the todo
	 * text
	 */
	public long addRecord(String title) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(COLTITLE, title);

		return db.insert(DATABASE_TABLE_NAME, null, cv);
	}

	/**
	 * //This method returns all notes from the database
	 */
	public ArrayList<MyNotes> getAllNotes() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<MyNotes> listItems = new ArrayList<MyNotes>();

		Cursor cursor = db.rawQuery("SELECT * from " + DATABASE_TABLE_NAME,
				new String[] {});

		if (cursor.moveToFirst()) {
			do {
				MyNotes note = new MyNotes();

				note.Id = cursor.getInt(cursor.getColumnIndex(COLID));
				
				note.Title = cursor.getString(cursor.getColumnIndex(COLTITLE));

				listItems.add(note);
			} while (cursor.moveToNext());
		}

		cursor.close();

		return listItems;
	}

	/*
	 * //This method deletes a record from the database.
	 */
	public void deleteNote(long id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String string = String.valueOf(id);
		db.execSQL("DELETE FROM " + DATABASE_TABLE_NAME + " WHERE " + COLID
				+ "=" + id + "");
	}
}