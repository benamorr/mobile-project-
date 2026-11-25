package com.ironfit.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ironfit.db";
    public static final int DATABASE_VERSION = 2;

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_EXERCISES = "exercises";
    public static final String TABLE_WORKOUT_SESSIONS = "workout_sessions";
    public static final String TABLE_EXERCISE_SETS = "exercise_sets";
    public static final String TABLE_WEIGHT_ENTRIES = "weight_entries";
    public static final String TABLE_PERSONAL_RECORDS = "personal_records";
    public static final String TABLE_WORKOUT_PLANS = "workout_plans";
    public static final String TABLE_WORKOUT_DAYS = "workout_days";
    public static final String TABLE_WORKOUT_DAY_EXERCISES = "workout_day_exercises";

    // users columns
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_NAME = "name";
    public static final String COL_USER_AGE = "age";
    public static final String COL_USER_HEIGHT = "height";
    public static final String COL_USER_WEIGHT = "weight";
    public static final String COL_USER_EXPERIENCE = "experience_level";
    public static final String COL_USER_GOAL = "goal";

    // exercises columns
    public static final String COL_EX_ID = "id";
    public static final String COL_EX_NAME = "name";
    public static final String COL_EX_CATEGORY = "category";
    public static final String COL_EX_TARGET = "target_muscles";
    public static final String COL_EX_SECONDARY = "secondary_muscles";
    public static final String COL_EX_DESCRIPTION = "description";
    public static final String COL_EX_INSTRUCTIONS = "instructions";
    public static final String COL_EX_DIFFICULTY = "difficulty";
    public static final String COL_EX_SETS = "recommended_sets";
    public static final String COL_EX_REPS = "recommended_reps";
    public static final String COL_EX_REST = "rest_time_seconds";
    public static final String COL_EX_IMAGE = "image_url";

    // workout_sessions columns
    public static final String COL_SESSION_ID = "id";
    public static final String COL_SESSION_NAME = "name";
    public static final String COL_SESSION_START = "start_time";
    public static final String COL_SESSION_END = "end_time";
    public static final String COL_SESSION_DURATION = "duration_seconds";
    public static final String COL_SESSION_NOTES = "notes";

    // exercise_sets columns
    public static final String COL_SET_ID = "id";
    public static final String COL_SET_SESSION_ID = "session_id";
    public static final String COL_SET_EXERCISE_ID = "exercise_id";
    public static final String COL_SET_EXERCISE_NAME = "exercise_name";
    public static final String COL_SET_NUMBER = "set_number";
    public static final String COL_SET_REPS = "reps";
    public static final String COL_SET_WEIGHT = "weight_kg";
    public static final String COL_SET_COMPLETED = "completed";

    // weight_entries columns
    public static final String COL_WE_ID = "id";
    public static final String COL_WE_WEIGHT = "weight";
    public static final String COL_WE_TIMESTAMP = "timestamp";
    public static final String COL_WE_NOTES = "notes";

    // personal_records columns
    public static final String COL_PR_ID = "id";
    public static final String COL_PR_EXERCISE_ID = "exercise_id";
    public static final String COL_PR_EXERCISE_NAME = "exercise_name";
    public static final String COL_PR_MAX_WEIGHT = "max_weight_kg";
    public static final String COL_PR_MAX_REPS = "max_reps";
    public static final String COL_PR_TIMESTAMP = "timestamp";

    // workout_plans columns
    public static final String COL_PLAN_ID = "id";
    public static final String COL_PLAN_NAME = "name";
    public static final String COL_PLAN_DESCRIPTION = "description";
    public static final String COL_PLAN_LEVEL = "level";
    public static final String COL_PLAN_DAYS_PER_WEEK = "days_per_week";

    // workout_days columns
    public static final String COL_DAY_ID = "id";
    public static final String COL_DAY_PLAN_ID = "plan_id";
    public static final String COL_DAY_NAME = "day_name";

    // workout_day_exercises columns
    public static final String COL_WDE_ID = "id";
    public static final String COL_WDE_DAY_ID = "day_id";
    public static final String COL_WDE_EXERCISE_ID = "exercise_id";
    public static final String COL_WDE_ORDER = "exercise_order";

    // CREATE TABLE statements
    private static final String CREATE_TABLE_USERS =
        "CREATE TABLE " + TABLE_USERS + " (" +
        COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COL_USER_NAME + " TEXT NOT NULL, " +
        COL_USER_AGE + " INTEGER, " +
        COL_USER_HEIGHT + " REAL, " +
        COL_USER_WEIGHT + " REAL, " +
        COL_USER_EXPERIENCE + " TEXT, " +
        COL_USER_GOAL + " TEXT" +
        ")";

    private static final String CREATE_TABLE_EXERCISES =
        "CREATE TABLE " + TABLE_EXERCISES + " (" +
        COL_EX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COL_EX_NAME + " TEXT NOT NULL, " +
        COL_EX_CATEGORY + " TEXT, " +
        COL_EX_TARGET + " TEXT, " +
        COL_EX_SECONDARY + " TEXT, " +
        COL_EX_DESCRIPTION + " TEXT, " +
        COL_EX_INSTRUCTIONS + " TEXT, " +
        COL_EX_DIFFICULTY + " TEXT, " +
        COL_EX_SETS + " INTEGER DEFAULT 3, " +
        COL_EX_REPS + " TEXT DEFAULT '8-12', " +
        COL_EX_REST + " INTEGER DEFAULT 90, " +
        COL_EX_IMAGE + " TEXT" +
        ")";

    private static final String CREATE_TABLE_WORKOUT_SESSIONS =
        "CREATE TABLE " + TABLE_WORKOUT_SESSIONS + " (" +
        COL_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COL_SESSION_NAME + " TEXT, " +
        COL_SESSION_START + " INTEGER, " +
        COL_SESSION_END + " INTEGER, " +
        COL_SESSION_DURATION + " INTEGER, " +
        COL_SESSION_NOTES + " TEXT" +
        ")";

    private static final String CREATE_TABLE_EXERCISE_SETS =
        "CREATE TABLE " + TABLE_EXERCISE_SETS + " (" +
        COL_SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COL_SET_SESSION_ID + " INTEGER, " +
        COL_SET_EXERCISE_ID + " INTEGER, " +
        COL_SET_EXERCISE_NAME + " TEXT, " +
        COL_SET_NUMBER + " INTEGER, " +
        COL_SET_REPS + " INTEGER, " +
        COL_SET_WEIGHT + " REAL, " +
        COL_SET_COMPLETED + " INTEGER DEFAULT 0, " +
        "FOREIGN KEY(" + COL_SET_SESSION_ID + ") REFERENCES " + TABLE_WORKOUT_SESSIONS + "(" + COL_SESSION_ID + ")" +
        ")";

    private static final String CREATE_TABLE_WEIGHT_ENTRIES =
        "CREATE TABLE " + TABLE_WEIGHT_ENTRIES + " (" +
        COL_WE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COL_WE_WEIGHT + " REAL NOT NULL, " +
        COL_WE_TIMESTAMP + " INTEGER NOT NULL, " +
        COL_WE_NOTES + " TEXT" +
        ")";

    private static final String CREATE_TABLE_PERSONAL_RECORDS =
        "CREATE TABLE " + TABLE_PERSONAL_RECORDS + " (" +
        COL_PR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COL_PR_EXERCISE_ID + " INTEGER UNIQUE, " +
        COL_PR_EXERCISE_NAME + " TEXT, " +
        COL_PR_MAX_WEIGHT + " REAL, " +
        COL_PR_MAX_REPS + " INTEGER, " +
        COL_PR_TIMESTAMP + " INTEGER" +
        ")";

    private static final String CREATE_TABLE_WORKOUT_PLANS =
        "CREATE TABLE " + TABLE_WORKOUT_PLANS + " (" +
        COL_PLAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COL_PLAN_NAME + " TEXT NOT NULL, " +
        COL_PLAN_DESCRIPTION + " TEXT, " +
        COL_PLAN_LEVEL + " TEXT, " +
        COL_PLAN_DAYS_PER_WEEK + " INTEGER" +
        ")";

    private static final String CREATE_TABLE_WORKOUT_DAYS =
        "CREATE TABLE " + TABLE_WORKOUT_DAYS + " (" +
        COL_DAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COL_DAY_PLAN_ID + " INTEGER, " +
        COL_DAY_NAME + " TEXT, " +
        "FOREIGN KEY(" + COL_DAY_PLAN_ID + ") REFERENCES " + TABLE_WORKOUT_PLANS + "(" + COL_PLAN_ID + ")" +
        ")";

    private static final String CREATE_TABLE_WORKOUT_DAY_EXERCISES =
        "CREATE TABLE " + TABLE_WORKOUT_DAY_EXERCISES + " (" +
        COL_WDE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COL_WDE_DAY_ID + " INTEGER, " +
        COL_WDE_EXERCISE_ID + " INTEGER, " +
        COL_WDE_ORDER + " INTEGER DEFAULT 0, " +
        "FOREIGN KEY(" + COL_WDE_DAY_ID + ") REFERENCES " + TABLE_WORKOUT_DAYS + "(" + COL_DAY_ID + "), " +
        "FOREIGN KEY(" + COL_WDE_EXERCISE_ID + ") REFERENCES " + TABLE_EXERCISES + "(" + COL_EX_ID + ")" +
        ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_EXERCISES);
        db.execSQL(CREATE_TABLE_WORKOUT_SESSIONS);
        db.execSQL(CREATE_TABLE_EXERCISE_SETS);
        db.execSQL(CREATE_TABLE_WEIGHT_ENTRIES);
        db.execSQL(CREATE_TABLE_PERSONAL_RECORDS);
        db.execSQL(CREATE_TABLE_WORKOUT_PLANS);
        db.execSQL(CREATE_TABLE_WORKOUT_DAYS);
        db.execSQL(CREATE_TABLE_WORKOUT_DAY_EXERCISES);
        populateSampleData(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        try {
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_EXERCISES, null);
            if (cursor != null) {
                if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
                    populateSampleData(db);
                }
                cursor.close();
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT_DAY_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT_DAYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT_PLANS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAL_RECORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT_ENTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE_SETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT_SESSIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public void populateSampleData() {
        SQLiteDatabase db = getWritableDatabase();
        populateSampleData(db);
    }

    public void populateSampleData(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            insertExercise(db, "Bench Press", "Chest",
                "Pectoralis Major", "Anterior Deltoids, Triceps",
                "The classic chest compound movement. Builds overall chest size and strength.",
                "1. Lie flat on bench, feet on floor.\n2. Grip bar slightly wider than shoulder-width.\n3. Unrack and lower bar to mid-chest under control.\n4. Press bar up explosively to full extension.\n5. Lock out without hyperextending elbows.",
                "Intermediate", 4, "6-10", 120, "img_bench_press");

            insertExercise(db, "Incline Dumbbell Press", "Chest",
                "Upper Pectoralis Major", "Anterior Deltoids, Triceps",
                "Targets the upper chest for a fuller, more defined look.",
                "1. Set bench to 30-45 degrees incline.\n2. Hold dumbbells at chest level, palms forward.\n3. Press dumbbells up and slightly inward.\n4. Lower slowly to chest level.\n5. Repeat without locking out completely.",
                "Intermediate", 3, "8-12", 90, "img_incline_dumbbell");

            insertExercise(db, "Push-Ups", "Chest",
                "Pectoralis Major", "Triceps, Anterior Deltoids, Core",
                "Fundamental bodyweight exercise for chest and upper body strength.",
                "1. Place hands shoulder-width apart on floor.\n2. Extend legs behind, body in straight line.\n3. Lower chest to just above floor.\n4. Push up explosively to start position.\n5. Keep core braced throughout.",
                "Beginner", 3, "12-20", 60, "img_home_hero");

            insertExercise(db, "Cable Flyes", "Chest",
                "Pectoralis Major", "Anterior Deltoids, Biceps",
                "Isolation exercise providing constant tension across the full range of motion.",
                "1. Set cables at shoulder height, stand centered.\n2. Hold handles with slight elbow bend.\n3. Bring hands together in a hugging arc.\n4. Squeeze chest at peak contraction.\n5. Slowly return to start under tension.",
                "Intermediate", 3, "12-15", 60, "ic_cable_flyes");

            insertExercise(db, "Dips", "Chest",
                "Pectoralis Major, Triceps", "Anterior Deltoids",
                "Compound push movement excellent for lower chest and tricep development.",
                "1. Grip parallel bars, arms straight.\n2. Lean torso slightly forward for chest emphasis.\n3. Lower until elbows reach 90 degrees.\n4. Press back up to full extension.\n5. Control the descent — don't drop fast.",
                "Intermediate", 3, "8-12", 90, "ic_dips");

            // BACK
            insertExercise(db, "Deadlift", "Back",
                "Erector Spinae, Glutes, Hamstrings", "Lats, Traps, Forearms",
                "King of all compound lifts. Builds total body strength and posterior chain.",
                "1. Stand with feet hip-width, bar over mid-foot.\n2. Hinge at hips, grip bar just outside shins.\n3. Brace core, chest up, neutral spine.\n4. Drive through floor, extending hips and knees simultaneously.\n5. Lock out at top, then lower under control.",
                "Advanced", 4, "4-6", 180, "img_plan_equipment");

            insertExercise(db, "Pull-Ups", "Back",
                "Latissimus Dorsi", "Biceps, Rear Deltoids, Rhomboids",
                "Best bodyweight exercise for back width and overall pulling strength.",
                "1. Hang from bar with overhand grip, hands shoulder-width.\n2. Depress and retract scapula before pulling.\n3. Pull elbows down toward hips.\n4. Chin clears the bar at top.\n5. Lower to full hang under control.",
                "Intermediate", 4, "6-10", 90, "img_row_back");

            insertExercise(db, "Bent-Over Row", "Back",
                "Latissimus Dorsi, Rhomboids, Traps", "Biceps, Rear Deltoids, Erector Spinae",
                "Heavy compound row for overall back thickness and strength.",
                "1. Hinge to roughly 45 degrees, back neutral.\n2. Grip barbell shoulder-width overhand.\n3. Row bar to lower chest / upper abdomen.\n4. Squeeze shoulder blades at top.\n5. Lower under control, maintaining hip hinge.",
                "Intermediate", 4, "6-10", 120, "img_row_back");

            insertExercise(db, "Lat Pulldown", "Back",
                "Latissimus Dorsi", "Biceps, Rear Deltoids, Teres Major",
                "Machine-based pulling movement great for building lat width.",
                "1. Sit with thighs under pads, grip bar wide overhand.\n2. Lean back slightly, chest tall.\n3. Pull bar to upper chest, driving elbows down.\n4. Squeeze lats at bottom position.\n5. Return bar slowly to full arm extension.",
                "Beginner", 4, "10-12", 90, "img_row_back");

            insertExercise(db, "Seated Cable Row", "Back",
                "Rhomboids, Traps, Latissimus Dorsi", "Biceps, Rear Deltoids",
                "Horizontal pull that builds back thickness and scapular retraction.",
                "1. Sit at cable row station, feet on platform.\n2. Grip handle with straight arms.\n3. Row handle to lower abdomen, elbows close.\n4. Squeeze shoulder blades together at peak.\n5. Extend arms slowly back to start.",
                "Beginner", 3, "10-12", 90, "img_row_back");

            // SHOULDERS
            insertExercise(db, "Overhead Press", "Shoulders",
                "Anterior Deltoids, Medial Deltoids", "Triceps, Upper Traps, Serratus",
                "Primary compound shoulder movement for mass and strength.",
                "1. Stand or sit with bar at upper chest.\n2. Grip slightly wider than shoulders.\n3. Press bar straight up overhead.\n4. Fully lock out at top.\n5. Lower bar to clavicle level under control.",
                "Intermediate", 4, "6-10", 120, "img_incline_dumbbell");

            insertExercise(db, "Lateral Raise", "Shoulders",
                "Medial Deltoids", "Anterior Deltoids, Supraspinatus",
                "Isolation exercise for shoulder width and the classic V-taper.",
                "1. Hold dumbbells at sides, slight elbow bend.\n2. Raise arms out laterally to shoulder height.\n3. Lead with elbows, not wrists.\n4. Hold briefly at top.\n5. Lower slowly back to sides.",
                "Beginner", 3, "12-15", 60, "ic_lateral_raise");

            insertExercise(db, "Front Raise", "Shoulders",
                "Anterior Deltoids", "Medial Deltoids, Pectoralis Major",
                "Targets the front portion of the shoulder for balanced development.",
                "1. Hold dumbbells in front of thighs.\n2. Raise one or both arms to shoulder height.\n3. Keep arms nearly straight.\n4. Pause at shoulder level.\n5. Lower slowly and repeat.",
                "Beginner", 3, "12-15", 60, "ic_front_raise");

            insertExercise(db, "Arnold Press", "Shoulders",
                "Anterior Deltoids, Medial Deltoids, Posterior Deltoids", "Triceps, Rotator Cuff",
                "Invented by Arnold Schwarzenegger, this variation rotates through the full shoulder range.",
                "1. Hold dumbbells at chin with palms facing you.\n2. Press up while rotating palms outward.\n3. Finish with palms facing forward overhead.\n4. Reverse the rotation on the way down.\n5. Control the full rotational arc.",
                "Intermediate", 3, "10-12", 90, "img_incline_dumbbell");

            insertExercise(db, "Face Pull", "Shoulders",
                "Posterior Deltoids, Rear Deltoids", "Rotator Cuff, Rhomboids, Traps",
                "Essential for shoulder health, posture, and rear delt development.",
                "1. Set cable at face height with rope attachment.\n2. Grip rope ends with thumbs up.\n3. Pull to face, spreading rope apart.\n4. External rotate at peak — hands behind ears.\n5. Squeeze and return slowly.",
                "Beginner", 3, "15-20", 60, "ic_face_pull");

            // BICEPS
            insertExercise(db, "Barbell Curl", "Biceps",
                "Biceps Brachii", "Brachialis, Forearms",
                "Classic mass builder for the biceps.",
                "1. Stand holding barbell at hip level, underhand grip.\n2. Keep upper arms pinned to sides.\n3. Curl bar up to shoulder level.\n4. Squeeze biceps hard at top.\n5. Lower under control to full extension.",
                "Beginner", 3, "8-12", 60, "ic_barbell_curl");

            insertExercise(db, "Hammer Curl", "Biceps",
                "Brachialis, Biceps Brachii", "Forearms, Brachioradialis",
                "Neutral-grip curl that builds bicep peaks and forearm thickness.",
                "1. Hold dumbbells with neutral grip (thumbs up).\n2. Keep elbows at sides.\n3. Curl both dumbbells to shoulder level.\n4. Squeeze at top of movement.\n5. Lower slowly back to start.",
                "Beginner", 3, "10-12", 60, "ic_hammer_curl");

            insertExercise(db, "Preacher Curl", "Biceps",
                "Biceps Brachii (Short Head)", "Brachialis",
                "Isolates the bicep by removing momentum and arm swing.",
                "1. Sit at preacher bench, upper arms on pad.\n2. Hold EZ-bar or dumbbells at bottom position.\n3. Curl weight up to chin level.\n4. Squeeze hard at top.\n5. Lower fully to stretch position.",
                "Intermediate", 3, "10-12", 60, "ic_preacher_curl");

            // TRICEPS
            insertExercise(db, "Tricep Pushdown", "Triceps",
                "Triceps Brachii (Lateral Head)", "Medial Head, Long Head",
                "Cable isolation exercise perfect for finishing tricep workouts.",
                "1. Stand at cable machine with rope or bar attachment.\n2. Elbows pinned to sides at 90 degrees.\n3. Push attachment down to full extension.\n4. Squeeze triceps hard at bottom.\n5. Return slowly, keeping elbows fixed.",
                "Beginner", 3, "12-15", 60, "ic_pushdown");

            insertExercise(db, "Skull Crushers", "Triceps",
                "Triceps Brachii (Long Head)", "Lateral Head, Medial Head",
                "Lying tricep extension for maximum long head stretch and mass.",
                "1. Lie on bench, hold EZ-bar above chest at arms length.\n2. Bend only at elbows, lowering bar toward forehead.\n3. Stop just short of touching forehead.\n4. Extend elbows back to start.\n5. Keep upper arms vertical throughout.",
                "Intermediate", 3, "10-12", 90, "ic_skull_crushers");

            insertExercise(db, "Close-Grip Bench Press", "Triceps",
                "Triceps Brachii", "Anterior Deltoids, Pectoralis Major",
                "Compound tricep movement allowing heavy loading.",
                "1. Lie on bench, grip bar shoulder-width or narrower.\n2. Unrack and lower to lower chest.\n3. Keep elbows tucked close to torso.\n4. Press up to full extension.\n5. Focus on triceps driving the press.",
                "Intermediate", 4, "8-10", 120, "img_bench_press");

            // LEGS
            insertExercise(db, "Squat", "Legs",
                "Quadriceps, Glutes", "Hamstrings, Erector Spinae, Core",
                "The king of leg exercises. Builds total lower body mass and strength.",
                "1. Bar on upper traps, feet shoulder-width apart.\n2. Brace core, chest tall.\n3. Descend by pushing knees out and sitting back.\n4. Break parallel — thighs below hip crease.\n5. Drive through heels to stand, hips and chest rise together.",
                "Intermediate", 4, "6-10", 180, "img_squat_legs");

            insertExercise(db, "Leg Press", "Legs",
                "Quadriceps", "Glutes, Hamstrings",
                "Machine compound movement for safe leg loading at high volumes.",
                "1. Sit in leg press, back flat against pad.\n2. Place feet shoulder-width on platform.\n3. Release safety and lower platform toward chest.\n4. Stop when hips begin to lift off seat.\n5. Press platform away to near-lockout.",
                "Beginner", 4, "10-15", 120, "img_squat_legs");

            insertExercise(db, "Romanian Deadlift", "Legs",
                "Hamstrings, Glutes", "Erector Spinae, Adductors",
                "Hip hinge movement targeting posterior chain, especially hamstrings.",
                "1. Hold bar at hip level, shoulder-width grip.\n2. Hinge at hips, pushing them back.\n3. Lower bar along thighs toward mid-shin.\n4. Feel deep hamstring stretch at bottom.\n5. Drive hips forward to stand, squeezing glutes.",
                "Intermediate", 3, "8-12", 120, "img_row_back");

            insertExercise(db, "Leg Curl", "Legs",
                "Hamstrings (Biceps Femoris)", "Gastrocnemius, Popliteus",
                "Isolation machine exercise to build hamstring thickness.",
                "1. Lie face down on leg curl machine.\n2. Pad rests above Achilles tendon.\n3. Curl legs up, bringing heels toward glutes.\n4. Squeeze hamstrings hard at top.\n5. Lower under control to full extension.",
                "Beginner", 3, "12-15", 60, "ic_leg_curl");

            insertExercise(db, "Calf Raise", "Legs",
                "Gastrocnemius, Soleus", "Peroneus Longus",
                "Essential exercise for calf development and ankle stability.",
                "1. Stand on edge of platform, heels hanging off.\n2. Lower heels below platform for full stretch.\n3. Rise up on toes as high as possible.\n4. Pause and squeeze calves at top.\n5. Lower slowly back to stretch position.",
                "Beginner", 4, "15-20", 60, "ic_calf_raise");

            // ABS
            insertExercise(db, "Plank", "Abs",
                "Transverse Abdominis, Rectus Abdominis", "Obliques, Erector Spinae, Glutes",
                "Isometric core hold that builds deep core stability and endurance.",
                "1. Place forearms on ground, elbows under shoulders.\n2. Extend legs, body in straight line.\n3. Squeeze glutes and brace core.\n4. Keep hips level — don't sag or pike.\n5. Hold for target duration.",
                "Beginner", 3, "30-60s", 60, "ic_plank");

            insertExercise(db, "Crunches", "Abs",
                "Rectus Abdominis", "Obliques, Hip Flexors",
                "Classic core exercise targeting the upper abdominal muscles.",
                "1. Lie on back, knees bent, feet flat.\n2. Place hands behind head lightly.\n3. Curl shoulders off floor toward knees.\n4. Squeeze abs at top of movement.\n5. Lower back down under control.",
                "Beginner", 3, "15-25", 60, "ic_crunches");

            insertExercise(db, "Leg Raises", "Abs",
                "Lower Rectus Abdominis, Hip Flexors", "Transverse Abdominis",
                "Targets the lower abs and hip flexors for a complete core workout.",
                "1. Lie flat, hands under glutes for support.\n2. Keep legs straight, raise them to 90 degrees.\n3. Slowly lower back without touching floor.\n4. Engage abs throughout — don't use momentum.\n5. Pause at bottom before next rep.",
                "Intermediate", 3, "12-20", 60, "ic_leg_raises");

            insertExercise(db, "Russian Twists", "Abs",
                "Obliques, Rectus Abdominis", "Transverse Abdominis, Hip Flexors",
                "Rotational core exercise for oblique development and waist definition.",
                "1. Sit with knees bent, lean back 45 degrees.\n2. Hold weight or clasp hands together.\n3. Rotate torso to touch ground beside hip.\n4. Rotate to opposite side — that's one rep.\n5. Keep abs braced and feet off floor for extra difficulty.",
                "Intermediate", 3, "20-30", 60, "ic_russian_twists");

            insertExercise(db, "Cable Crunch", "Abs",
                "Rectus Abdominis", "Obliques",
                "Weighted ab exercise allowing progressive overload for the core.",
                "1. Kneel at cable machine with rope at top.\n2. Hold rope behind head, elbows wide.\n3. Crunch down, bringing elbows to thighs.\n4. Fully contract abs at bottom.\n5. Return under control, don't use hip flexors to rise.",
                "Intermediate", 3, "12-15", 60, "ic_cable_crunch");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void insertExercise(SQLiteDatabase db, String name, String category,
                                String target, String secondary, String description,
                                String instructions, String difficulty,
                                int sets, String reps, int rest, String imageUrl) {
        ContentValues cv = new ContentValues();
        cv.put(COL_EX_NAME, name);
        cv.put(COL_EX_CATEGORY, category);
        cv.put(COL_EX_TARGET, target);
        cv.put(COL_EX_SECONDARY, secondary);
        cv.put(COL_EX_DESCRIPTION, description);
        cv.put(COL_EX_INSTRUCTIONS, instructions);
        cv.put(COL_EX_DIFFICULTY, difficulty);
        cv.put(COL_EX_SETS, sets);
        cv.put(COL_EX_REPS, reps);
        cv.put(COL_EX_REST, rest);
        cv.put(COL_EX_IMAGE, imageUrl);
        db.insert(TABLE_EXERCISES, null, cv);
    }
}
