package com.example.loramessenger;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createGroupMessageTable = "CREATE TABLE GROUP_MESSAGE_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, MESSAGE BLOB)";
        String createUsernameTable = "CREATE TABLE USERNAME (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)";
        sqLiteDatabase.execSQL(createGroupMessageTable);
        sqLiteDatabase.execSQL(createUsernameTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public synchronized void addMessage(LoRaMessage loRaMessage) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MESSAGE", SerializationUtils.serialize(loRaMessage));
        database.insert("GROUP_MESSAGE_TABLE", null, contentValues);
    }

    public void addUsername(String username){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", username);
        database.insert("USERNAME", null, contentValues);
    }

    public List<LoRaMessage> getMessages() {
        List<LoRaMessage> loRaMessageList = new ArrayList<>();
        String queryString = "SELECT * FROM GROUP_MESSAGE_TABLE";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                LoRaMessage loRaMessage = SerializationUtils.deserialize(cursor.getBlob(1));
                loRaMessageList.add(loRaMessage);
            } while (cursor.moveToNext());
        }
        return loRaMessageList;
    }

    public String getUsername() {
        String queryString = "SELECT * FROM USERNAME LIMIT 1";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(1);
        }
        return "";
    }

    public void truncateMessages(){
        String queryString = "DELETE FROM GROUP_MESSAGE_TABLE";
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(queryString);
    }
}
