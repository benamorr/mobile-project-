package com.ironfit.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ironfit.app.models.WeightEntry;

import java.util.ArrayList;
import java.util.List;

public class WeightDao {

    private final DatabaseHelper dbHelper;

    public WeightDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public long addWeightEntry(WeightEntry entry) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_WE_WEIGHT, entry.getWeight());
        cv.put(DatabaseHelper.COL_WE_TIMESTAMP, entry.getTimestamp());
        cv.put(DatabaseHelper.COL_WE_NOTES, entry.getNotes());
        return db.insert(DatabaseHelper.TABLE_WEIGHT_ENTRIES, null, cv);
    }

    public List<WeightEntry> getAllWeightEntries() {
        List<WeightEntry> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_WEIGHT_ENTRIES, null, null, null, null, null, DatabaseHelper.COL_WE_TIMESTAMP + " DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_WE_ID));
                float weight = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_WE_WEIGHT));
                long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_WE_TIMESTAMP));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_WE_NOTES));
                list.add(new WeightEntry(id, weight, timestamp, notes));
            }
            cursor.close();
        }
        return list;
    }

    public WeightEntry getLatestWeight() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_WEIGHT_ENTRIES, null, null, null, null, null, DatabaseHelper.COL_WE_TIMESTAMP + " DESC", "1");
        WeightEntry entry = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_WE_ID));
                float weight = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_WE_WEIGHT));
                long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_WE_TIMESTAMP));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_WE_NOTES));
                entry = new WeightEntry(id, weight, timestamp, notes);
            }
            cursor.close();
        }
        return entry;
    }

    public void deleteWeightEntry(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_WEIGHT_ENTRIES, DatabaseHelper.COL_WE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_WEIGHT_ENTRIES, null, null);
    }
}
