package com.example.myapplication.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.DbHelper;

public class ThuThuDAO {
    DbHelper dbHelper;
    SharedPreferences sharedPreferences;
    public ThuThuDAO(Context context) {
        dbHelper = new DbHelper(context);
        sharedPreferences =context.getSharedPreferences("THONGTIN", MODE_PRIVATE);
    }
    public boolean checkDangNhap(String matt, String matkhau){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE  matt = ? AND matkhau = ?",
                new String[]{matt,matkhau});
        if (cursor.getCount() !=0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("matt",cursor.getString(0));
            editor.putString("loaitaikhoan",cursor.getString(3));
            editor.putString("hoten",cursor.getString(1));
            editor.commit();
            return true;
        }else {
            return false;
        }
    }
    public boolean CapNhatMatKhau(String username, String oldpass, String newpass){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        //matt,hoten,matkhau,loaitaikhoan
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt =? AND matkhau =?",new String[]{username,oldpass});
        if (cursor.getCount() >0 ){
            cursor.moveToFirst();
            //luu sharedpreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("matt",cursor.getString(0));
            editor.commit();

            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau",newpass);
       long check = sqLiteDatabase.update("THUTHU",contentValues,"mathuthu =?",new String[]{username});
       if (check == -1)
       return false;
       return true;
        }
        return false;
    }
    public boolean ThemThuThu(String matt, String hoten, String matkhau,String loaitaikhoan){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt",matt);
        contentValues.put("hoten",hoten);
        contentValues.put("matkhau",matkhau);
        contentValues.put("loaitaikhoan",loaitaikhoan);
        long check = sqLiteDatabase.insert("THUTHU",null,contentValues);
        if (check == -1)
            return false;
        return true;
    }
}
