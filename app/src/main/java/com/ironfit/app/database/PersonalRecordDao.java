package com.ironfit.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ironfit.app.models.PersonalRecord;

import java.util.ArrayList;
import java.util.List;

public class PersonalRecordDao {

    private final DatabaseHelper dbHelper;

    public PersonalRecordDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public void upsertRecord(int exerciseId, String exerciseName, float weightKg, int reps) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        PersonalRecord existing = getRecordForExercise(exerciseId);

        if (existing == null) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.COL_PR_EXERCISE_ID, exerciseId);
            cv.put(DatabaseHelper.COL_PR_EXERCISE_NAME, exerciseName);
            cv.put(DatabaseHelper.COL_PR_MAX_WEIGHT, weightKg);
            cv.put(DatabaseHelper.COL_PR_MAX_REPS, reps);
            cv.put(DatabaseHelper.COL_PR_TIMESTAMP, System.currentTimeMillis());
            db.insert(DatabaseHelper.TABLE_PERSONAL_RECORDS, null, cv);
        } else if (weightKg > existing.getMaxWeightKg() || (weightKg == existing.getMaxWeightKg() && reps > existing.getMaxReps())) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.COL_PR_MAX_WEIGHT, weightKg);
            cv.put(DatabaseHelper.COL_PR_MAX_REPS, reps);
            cv.put(DatabaseHelper.COL_PR_TIMESTAMP, System.currentTimeMillis());
            db.update(DatabaseHelper.TABLE_PERSONAL_RECORDS, cv, DatabaseHelper.COL_PR_EXERCISE_ID + " = ?", new String[]{String.valueOf(exerciseId)});
        }
    }

    public List<PersonalRecord> getAllRecords() {
        List<PersonalRecord> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_PERSONAL_RECORDS, null, null, null, null, null, DatabaseHelper.COL_PR_MAX_WEIGHT + " DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PR_ID));
                int exId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PR_EXERCISE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PR_EXERCISE_NAME));
                float wt = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PR_MAX_WEIGHT));
                int reps = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PR_MAX_REPS));
                long time = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PR_TIMESTAMP));
                list.add(new PersonalRecord(id, exId, name, wt, reps, time));
            }
            cursor.close();
        }
        return list;
    }

    public PersonalRecord getRecordForExercise(int exerciseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_PERSONAL_RECORDS, null, DatabaseHelper.COL_PR_EXERCISE_ID + " = ?", new String[]{String.valueOf(exerciseId)}, null, null, null);
        PersonalRecord pr = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PR_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PR_EXERCISE_NAME));
                float wt = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PR_MAX_WEIGHT));
                int reps = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PR_MAX_REPS));
                long time = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PR_TIMESTAMP));
                pr = new PersonalRecord(id, exerciseId, name, wt, reps, time);
            }
            cursor.close();
        }
        return pr;
    }

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_PERSONAL_RECORDS, null, null);
    }
}
