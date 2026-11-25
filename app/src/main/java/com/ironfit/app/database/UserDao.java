package com.ironfit.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ironfit.app.models.User;

public class UserDao {

    private final DatabaseHelper dbHelper;

    public UserDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public long saveUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_USER_NAME, user.getName());
        cv.put(DatabaseHelper.COL_USER_AGE, user.getAge());
        cv.put(DatabaseHelper.COL_USER_HEIGHT, user.getHeight());
        cv.put(DatabaseHelper.COL_USER_WEIGHT, user.getWeight());
        cv.put(DatabaseHelper.COL_USER_EXPERIENCE, user.getExperienceLevel());
        cv.put(DatabaseHelper.COL_USER_GOAL, user.getGoal());

        // Single user table: clear old if any, then insert
        db.delete(DatabaseHelper.TABLE_USERS, null, null);
        return db.insert(DatabaseHelper.TABLE_USERS, null, cv);
    }

    public User getUser() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null, null, null, null, null, null, "1");
        User user = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_NAME));
                int age = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_AGE));
                float height = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_HEIGHT));
                float weight = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_WEIGHT));
                String experience = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_EXPERIENCE));
                String goal = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_GOAL));
                user = new User(id, name, age, height, weight, experience, goal);
            }
            cursor.close();
        }
        return user;
    }

    public void updateUserWeight(float newWeight) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_USER_WEIGHT, newWeight);
        db.update(DatabaseHelper.TABLE_USERS, cv, null, null);
    }

    public boolean hasUser() {
        return getUser() != null;
    }

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_USERS, null, null);
    }
}
