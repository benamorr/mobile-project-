package com.ironfit.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ironfit.app.models.ExerciseSet;
import com.ironfit.app.models.WorkoutSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WorkoutSessionDao {

    private final DatabaseHelper dbHelper;

    public WorkoutSessionDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public long saveSession(WorkoutSession session) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_SESSION_NAME, session.getName());
        cv.put(DatabaseHelper.COL_SESSION_START, session.getStartTime());
        cv.put(DatabaseHelper.COL_SESSION_END, session.getEndTime());
        cv.put(DatabaseHelper.COL_SESSION_DURATION, session.getDurationSeconds());
        cv.put(DatabaseHelper.COL_SESSION_NOTES, session.getNotes());

        long sessionId = db.insert(DatabaseHelper.TABLE_WORKOUT_SESSIONS, null, cv);
        session.setId((int) sessionId);

        if (session.getSets() != null && !session.getSets().isEmpty()) {
            for (ExerciseSet set : session.getSets()) {
                ContentValues scv = new ContentValues();
                scv.put(DatabaseHelper.COL_SET_SESSION_ID, sessionId);
                scv.put(DatabaseHelper.COL_SET_EXERCISE_ID, set.getExerciseId());
                scv.put(DatabaseHelper.COL_SET_EXERCISE_NAME, set.getExerciseName());
                scv.put(DatabaseHelper.COL_SET_NUMBER, set.getSetNumber());
                scv.put(DatabaseHelper.COL_SET_REPS, set.getReps());
                scv.put(DatabaseHelper.COL_SET_WEIGHT, set.getWeightKg());
                scv.put(DatabaseHelper.COL_SET_COMPLETED, set.isCompleted() ? 1 : 0);
                db.insert(DatabaseHelper.TABLE_EXERCISE_SETS, null, scv);
            }
        }
        return sessionId;
    }

    public List<WorkoutSession> getAllSessions() {
        List<WorkoutSession> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_WORKOUT_SESSIONS, null, null, null, null, null, DatabaseHelper.COL_SESSION_START + " DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(cursorToSession(cursor, db));
            }
            cursor.close();
        }
        return list;
    }

    public List<WorkoutSession> getRecentSessions(int limit) {
        List<WorkoutSession> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_WORKOUT_SESSIONS, null, null, null, null, null, DatabaseHelper.COL_SESSION_START + " DESC", String.valueOf(limit));
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(cursorToSession(cursor, db));
            }
            cursor.close();
        }
        return list;
    }

    public int getTotalSessionCount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_WORKOUT_SESSIONS, null);
        int count = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }

    public int getWeeklySessionCount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long startOfWeek = cal.getTimeInMillis();

        String query = "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_WORKOUT_SESSIONS + " WHERE " + DatabaseHelper.COL_SESSION_START + " >= ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(startOfWeek)});
        int count = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_EXERCISE_SETS, null, null);
        db.delete(DatabaseHelper.TABLE_WORKOUT_SESSIONS, null, null);
    }

    private WorkoutSession cursorToSession(Cursor cursor, SQLiteDatabase db) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SESSION_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SESSION_NAME));
        long start = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SESSION_START));
        long end = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SESSION_END));
        long duration = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SESSION_DURATION));
        String notes = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SESSION_NOTES));

        // Load sets
        List<ExerciseSet> sets = new ArrayList<>();
        Cursor setCursor = db.query(DatabaseHelper.TABLE_EXERCISE_SETS, null,
                DatabaseHelper.COL_SET_SESSION_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, DatabaseHelper.COL_SET_NUMBER + " ASC");
        if (setCursor != null) {
            while (setCursor.moveToNext()) {
                int setId = setCursor.getInt(setCursor.getColumnIndexOrThrow(DatabaseHelper.COL_SET_ID));
                int exId = setCursor.getInt(setCursor.getColumnIndexOrThrow(DatabaseHelper.COL_SET_EXERCISE_ID));
                String exName = setCursor.getString(setCursor.getColumnIndexOrThrow(DatabaseHelper.COL_SET_EXERCISE_NAME));
                int num = setCursor.getInt(setCursor.getColumnIndexOrThrow(DatabaseHelper.COL_SET_NUMBER));
                int reps = setCursor.getInt(setCursor.getColumnIndexOrThrow(DatabaseHelper.COL_SET_REPS));
                float wt = setCursor.getFloat(setCursor.getColumnIndexOrThrow(DatabaseHelper.COL_SET_WEIGHT));
                boolean done = setCursor.getInt(setCursor.getColumnIndexOrThrow(DatabaseHelper.COL_SET_COMPLETED)) == 1;
                sets.add(new ExerciseSet(setId, id, exId, exName, num, reps, wt, done));
            }
            setCursor.close();
        }

        WorkoutSession session = new WorkoutSession(id, name, start, end, duration, notes);
        session.setSets(sets);
        return session;
    }
}
