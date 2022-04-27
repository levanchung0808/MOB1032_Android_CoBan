package com.example.chunglvps19319_mob103_assignment1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){
        super(context, "PS19319_DB",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE SINHVIEN(maSv text PRIMARY KEY , tenSv text NOT NULL, ngaySinh date NOT NULL, hinh text NOT NULL, maLopHoc text NOT NULL)";
        db.execSQL(sql);

        String sql1 = "CREATE TABLE LOPHOC(maLop text PRIMARY KEY, tenLop text NOT NULL, hinh text NOT NULL)";
        db.execSQL(sql1);

        String sql2 = "CREATE TABLE USER(username text PRIMARY KEY, password text NOT NULL)";
        db.execSQL(sql2);

        //SINHVIEN
        sql = "INSERT INTO SINHVIEN VALUES ('PS19319','Lê Văn Chung','2003-08-08','avatar1','CP17101')";
        db.execSQL(sql);
        sql = "INSERT INTO SINHVIEN VALUES ('PS19320','Nguyễn Hải Anh','2001-01-01','avatar2','CP17102')";
        db.execSQL(sql);
        sql = "INSERT INTO SINHVIEN VALUES ('PS19321','Phan Văn Hùng','2002-02-02','avatar3','CP17103')";
        db.execSQL(sql);
        sql = "INSERT INTO SINHVIEN VALUES ('PS19322','Lê Văn A','2003-03-03','avatar4','CP17103')";
        db.execSQL(sql);
        sql = "INSERT INTO SINHVIEN VALUES ('PS19323','Lê Văn B','2004-04-04','avatar5','CP17104')";
        db.execSQL(sql);

        //LOPHOC
        sql1 = "INSERT INTO LOPHOC VALUES('CP17101','Android cơ bản','avartar_c')";
        db.execSQL(sql1);
        sql1 = "INSERT INTO LOPHOC VALUES('CP17102','Java 2','avartar_c')";
        db.execSQL(sql1);
        sql1 = "INSERT INTO LOPHOC VALUES('CP17103','Java 1','avartar_c')";
        db.execSQL(sql1);
        sql1 = "INSERT INTO LOPHOC VALUES('CP17104','Android nâng cao','avartar_c')";
        db.execSQL(sql1);

        //LOPHOC
        sql2 = "INSERT INTO USER VALUES('admin','123')";
        db.execSQL(sql2);
        sql2 = "INSERT INTO USER VALUES('chung','321')";
        db.execSQL(sql2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS SINHVIEN");
        db.execSQL("DROP TABLE IF EXISTS LOPHOC");
        db.execSQL("DROP TABLE IF EXISTS USER");
        onCreate(db);
    }
}
