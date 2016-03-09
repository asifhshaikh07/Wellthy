package com.gmail.asifhshaikh07.wellthywords;

/**
 * Created by Asif on 3/6/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WordsDBOpenHelper extends SQLiteOpenHelper {

    private static final String LOGTAG =" WORDSDBOPENHELPER";

    private static final String DATABASE_NAME = "words.db";
    private static final int   DATABASE_VERSION =1;


    public static final String TABLE_WORDS ="words";
    public static final String COLUMN_ID = "wordId";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_MEANING = "meaning";
    public static final String COLUMN_THUMBNAIL="images";
//AUTOINCREMENT
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_WORDS + " ( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY , " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_MEANING + " TEXT, " +
                    COLUMN_THUMBNAIL + " TEXT " +
                    " )";

    public WordsDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        Log.i(LOGTAG,"TABle is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if EXISTS" + TABLE_WORDS);
        onCreate(db);

    }

}
