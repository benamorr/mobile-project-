package com.ironfit.app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ironfit.app.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDao {

    private final DatabaseHelper dbHelper;

    public ExerciseDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EXERCISES, null, null, null, null, null, DatabaseHelper.COL_EX_NAME + " ASC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(cursorToExercise(cursor));
            }
            cursor.close();
        }
        return list;
    }

    public List<Exercise> getExercisesByCategory(String category) {
        List<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = DatabaseHelper.COL_EX_CATEGORY + " = ?";
        String[] args = new String[]{category};
        Cursor cursor = db.query(DatabaseHelper.TABLE_EXERCISES, null, selection, args, null, null, DatabaseHelper.COL_EX_NAME + " ASC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(cursorToExercise(cursor));
            }
            cursor.close();
        }
        return list;
    }

    public Exercise getExerciseById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = DatabaseHelper.COL_EX_ID + " = ?";
        String[] args = new String[]{String.valueOf(id)};
        Cursor cursor = db.query(DatabaseHelper.TABLE_EXERCISES, null, selection, args, null, null, null);
        Exercise exercise = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                exercise = cursorToExercise(cursor);
            }
            cursor.close();
        }
        return exercise;
    }

    private Exercise cursorToExercise(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EX_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EX_NAME));
        String category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EX_CATEGORY));
        String target = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EX_TARGET));
        String secondary = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EX_SECONDARY));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EX_DESCRIPTION));
        String instructions = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EX_INSTRUCTIONS));
        String difficulty = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EX_DIFFICULTY));
        int sets = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EX_SETS));
        String reps = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EX_REPS));
        int rest = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EX_REST));
        String image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EX_IMAGE));

        return new Exercise(id, name, category, target, secondary, description, instructions, difficulty, sets, reps, rest, image);
    }
}
