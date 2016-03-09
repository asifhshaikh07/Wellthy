package com.gmail.asifhshaikh07.wellthywords;

/**
 * Created by Asif on 3/6/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class WordsDataSource {

    private static final String LOGTAG = " WordsDataSource";
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    private static final String[] allColumns ={
            WordsDBOpenHelper.COLUMN_ID,
            WordsDBOpenHelper.COLUMN_TITLE,
            WordsDBOpenHelper.COLUMN_MEANING,
            WordsDBOpenHelper.COLUMN_THUMBNAIL,
            };

    public WordsDataSource(Context context) {
        openHelper = new WordsDBOpenHelper(context);

    }

    public void Open() {
        database = openHelper.getWritableDatabase();
    }

    public void Close() {
        database.close();
    }

    public WordItem create(WordItem wordItem) {
        ContentValues values = new ContentValues();
        values.put(WordsDBOpenHelper.COLUMN_ID, wordItem.getId());
        values.put(WordsDBOpenHelper.COLUMN_TITLE, wordItem.getTitle());
        values.put(WordsDBOpenHelper.COLUMN_MEANING, wordItem.getMeaning());
        values.put(WordsDBOpenHelper.COLUMN_THUMBNAIL, wordItem.getThumbnail());
        database.insert(WordsDBOpenHelper.TABLE_WORDS, null, values);
        return wordItem;
    }

    public List<WordItem> findAll(){
        List<WordItem> items = new ArrayList<WordItem>();

        Cursor cursor = database.query(WordsDBOpenHelper.TABLE_WORDS,allColumns,
                null,null,null,null,null);

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                WordItem item = new WordItem();
                item.setId(cursor.getLong(cursor.getColumnIndex(WordsDBOpenHelper.COLUMN_ID)));
                item.setTitle(cursor.getString(cursor.getColumnIndex(WordsDBOpenHelper.COLUMN_TITLE)));
                item.setMeaning(cursor.getString(cursor.getColumnIndex(WordsDBOpenHelper.COLUMN_MEANING)));
                item.setThumbnail(cursor.getString(cursor.getColumnIndex(WordsDBOpenHelper.COLUMN_THUMBNAIL)));
                items.add(item);

            }
        }

        return items;
    }

}

