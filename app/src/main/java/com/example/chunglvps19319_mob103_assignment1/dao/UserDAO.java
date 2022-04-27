package com.example.chunglvps19319_mob103_assignment1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import com.example.chunglvps19319_mob103_assignment1.database.DbHelper;
import com.example.chunglvps19319_mob103_assignment1.model.LopHoc;
import com.example.chunglvps19319_mob103_assignment1.model.SinhVien;
import com.example.chunglvps19319_mob103_assignment1.model.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class UserDAO {
    DbHelper helper;
    Context context;

    public UserDAO(Context context) {
        helper = new DbHelper(context);
        this.context = context;
    }

    public ArrayList<User> getAll(){
        ArrayList<User> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM USER";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();   //đưa con trỏ về dòng đầu tiên
        while(cs.isAfterLast() == false){
            String username = cs.getString(0);
            String password = cs.getString(1);
            User user = new User(username,password);
            list.add(user);
            cs.moveToNext();
        }
        return list;
    }

    public boolean insert(User user){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",user.getUsername());
        values.put("password",user.getPassword());
        long row = db.insert("USER",null,values);
        if(row>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkLogin(User user) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM USER WHERE username=? AND password=?";
        Cursor cs = db.rawQuery(sql, new String[]{user.getUsername(), user.getPassword()});
//        if(cs.getCount() > 0){
//            return true;
//        }else{
//            return false;
//        }
        return (cs.getCount() > 0);
    }

    public boolean changePassword(String username, String mkMoi) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", mkMoi);
        int row = db.update("USER", values, "username=?", new String[]{username});
        if(row>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkUsernameExists(String username){
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM USER WHERE username=?";
        Cursor cs = db.rawQuery(sql, new String[]{username});
        return (cs.getCount()>0);
    }

    public boolean forgotPassword(String username, String passwordReset){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password",passwordReset);
        long row = db.update("USER",values,"username=?",new String[]{username});
        if(row>0){
            return true;
        }else{
            return false;
        }
    }
}
