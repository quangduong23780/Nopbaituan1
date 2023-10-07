package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.DbHelper;

public class ThuThuDAO {
    DbHelper dbHelper;
    public ThuThuDAO(Context context){
        dbHelper =  new DbHelper(context);
    }
    //Dang nhap
    public boolean checkDangNhap(String matt, String matkhau){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE  matt = ? AND matkhau = ?",
                new String[]{matt,matkhau});
        if (cursor.getCount() !=0){
            return true;
        }else {
            return false;
        }
    }
    public boolean CapNhatMatKhau(String username, String oldpass, String newpass){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt =? AND matkhau =?",new String[]{username,oldpass});
        if (cursor.getCount() >0 ){
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau",newpass);
       long check = sqLiteDatabase.update("THUTHU",contentValues,"mathuthu =?",new String[]{username});
       if (check == -1)
       return false;
       return true;
        }
        return false;
    }
}
