package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.DbHelper;
import com.example.myapplication.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachDao {
    DbHelper dbHelper;

    public LoaiSachDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    //lay danh sach loai sach
    public ArrayList<LoaiSach> getDSLoaiSach() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAISACH", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    //them loai sach
    public  boolean themLoaiSach(String tenloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai",tenloai);
        long check = sqLiteDatabase.insert("LOAISACH",null,contentValues);
        if(check == -1){
            return false;
        }else {
            return true;
        }
    }
    //xoa loai sach
    public int xoaLoaiSach(int id){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SACH WHERE maloai = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() !=0){
            return -1;
        }

        long check = sqLiteDatabase.delete("LOAISACH","maloai =?",new String[]{String.valueOf(id)});
        if (check == -1)
            return 0;
            return 1;
    }
}
