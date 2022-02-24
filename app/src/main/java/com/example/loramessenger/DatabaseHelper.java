package com.example.loramessenger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.loramessenger.messages.LoRaTextMessage;

import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createGroupMessageTable = "CREATE TABLE GROUP_MESSAGE_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, MESSAGE BLOB, UUID TEXT)";
        String createUsernameTable = "CREATE TABLE USERNAME (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)";
        sqLiteDatabase.execSQL(createGroupMessageTable);
        sqLiteDatabase.execSQL(createUsernameTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public synchronized void createMessage(LoRaTextMessage loRaTextMessage) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UUID", loRaTextMessage.getUuid());
        contentValues.put("MESSAGE", SerializationUtils.serialize(loRaTextMessage));
        sqLiteDatabase.insert("GROUP_MESSAGE_TABLE", null, contentValues);
    }

    public synchronized void updateMessage(LoRaTextMessage loRaTextMessage) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MESSAGE", SerializationUtils.serialize(loRaTextMessage));
        sqLiteDatabase.update("GROUP_MESSAGE_TABLE", contentValues, "UUID = ?", new String[]{loRaTextMessage.getUuid()});
    }

    public synchronized long deleteMessage(LoRaTextMessage loRaTextMessage) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("GROUP_MESSAGE_TABLE", "UUID = ?", new String[]{String.valueOf(loRaTextMessage.getUuid())});
    }

    public LoRaTextMessage getMessage(String uuid) {
        LoRaTextMessage loRaTextMessage = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("GROUP_MESSAGE_TABLE", new String[]{"MESSAGE"}, "UUID = ?", new String[]{uuid}, null, null, null);
        while (cursor.moveToNext()) {
            loRaTextMessage = SerializationUtils.deserialize(cursor.getBlob(cursor.getColumnIndexOrThrow("MESSAGE")));
        }
        cursor.close();
        return loRaTextMessage;
    }

    public void addUsername(String username) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", username);
        database.insert("USERNAME", null, contentValues);
    }

    public List<LoRaTextMessage> getMessages() {
        List<LoRaTextMessage> loRaTextMessageList = new ArrayList<>();
        String queryString = "SELECT * FROM GROUP_MESSAGE_TABLE";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                LoRaTextMessage loRaTextMessage = SerializationUtils.deserialize(cursor.getBlob(1));
                loRaTextMessageList.add(loRaTextMessage);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return loRaTextMessageList;
    }

    public String getUsername() {
        String username = "";
        String queryString = "SELECT * FROM USERNAME LIMIT 1";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            username = cursor.getString(1);
            cursor.close();
            return username;
        }
        return username;
    }

    public void truncateMessages() {
        String queryString = "DELETE FROM GROUP_MESSAGE_TABLE";
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(queryString);
    }
}
