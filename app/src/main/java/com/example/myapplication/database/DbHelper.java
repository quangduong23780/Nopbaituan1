package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){
        super(context,"QUANLYTHUVIEN",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String dbThuThu = "CREATE TABLE THUTHU(matt TEXT PRIMARY KEY, hoten TEXT, matkhau TEXT, loaitaikhoan TEXT)";
        sqLiteDatabase.execSQL(dbThuThu);
        String dbThanhVien = "CREATE TABLE THANHVIEN(matv INTEGER PRIMARY KEY AUTOINCREMENT, hoten TEXT, namsinh TEXT)";
        sqLiteDatabase.execSQL(dbThanhVien);
        String dbLoai = "CREATE TABLE LOAISACH(maloai INTEGER PRIMARY KEY  AUTOINCREMENT, tenloai TEXT)";
        sqLiteDatabase.execSQL(dbLoai);
        String dbSach = "CREATE TABLE SACH(masach INTEGER PRIMARY KEY AUTOINCREMENT, tensach TEXT,  giathue INTEGER,namxb INTEGER," + " maloai  INTEGER REFERENCES LOAISACH(maloai))";
        sqLiteDatabase.execSQL(dbSach);
        String dbPhieuMuon = "CREATE TABLE PHIEUMUON(mapm INTEGER PRIMARY KEY AUTOINCREMENT, matv  INTEGER REFERENCES THANHVIEN(matv)," +
                " matt TEXT REFERENCES THUTHU(matt), masach  INTEGER REFERENCES SACH(masach), ngay TEXT, trasach INTEGER, tienthue INTEGER)";
        sqLiteDatabase.execSQL(dbPhieuMuon);
        //data mau
        String qLoai = "INSERT INTO LOAISACH VALUES(1 ,'Thiếu nhi'),(2,'Ngôn tình'),(3,'Giáo khoa')";
        sqLiteDatabase.execSQL(qLoai);
        String qSach = "INSERT INTO SACH VALUES(1,'Rùa và thỏ',10000,2010,1),(2,'Bến xe',15000,2011,2),(3,'Toán 12',20000,2012,3)";
        sqLiteDatabase.execSQL(qSach);
        String qThuThu = "INSERT INTO THUTHU VALUES('admin','Tống Quang A','123','admin'),('thuthu01','Tống Quang B','123','thuthu'),('thuthu02','Tống Quang C','123','thuthu')";
        sqLiteDatabase.execSQL(qThuThu);
        String qThanhVien = "INSERT INTO THANHVIEN VALUES(1,'Nguyễn Quang A','2003'),(2,'Nguyễn Quang B','2002'),(3,'Nguyễn Quang C','2001')";
        sqLiteDatabase.execSQL(qThanhVien);
        String qPhieuMuon = "INSERT INTO PHIEUMUON VALUES(1,1,'thuthu01',1,'20/9/2023',1,10000),(2,2,'thuthu02',2,'21/9/2023',2,15000),(3,3,'thuthu03',3,'22/9/2023',3,20000)";
        sqLiteDatabase.execSQL(qPhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i  != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THUTHU");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LOAISACH");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SACH");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            onCreate(sqLiteDatabase);
        }
    }
}
