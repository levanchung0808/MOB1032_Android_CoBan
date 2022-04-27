package com.example.chunglvps19319_mob103_assignment1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chunglvps19319_mob103_assignment1.database.DbHelper;
import com.example.chunglvps19319_mob103_assignment1.model.LopHoc;
import com.example.chunglvps19319_mob103_assignment1.model.SinhVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SinhVienDAO {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DbHelper helper;
    Context context;
    public SinhVienDAO(Context context){
        helper = new DbHelper(context);
        this.context = context;
    }

    public ArrayList<SinhVien> getAll(){
        ArrayList<SinhVien> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM SINHVIEN";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();   //đưa con trỏ về dòng đầu tiên
        while(cs.isAfterLast() == false){
            try{
                String masv = cs.getString(0);
                String tensv = cs.getString(1);
                Date ns = sdf.parse(cs.getString(2));
                String tenHinh = cs.getString(3);
                String maLop = cs.getString(4);
                int hinh = context.getResources().getIdentifier(tenHinh, "drawable",context.getPackageName());
                SinhVien sv = new SinhVien(masv, tensv, ns, hinh, maLop);
                list.add(sv);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            cs.moveToNext();
        }
        return list;
    }

    //them dữ liệu vào database, trả về boolean?
    public boolean insert(SinhVien sv){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maSv",sv.getMaSv());
        values.put("tenSv",sv.getTenSv());
        values.put("ngaySinh",sdf.format(sv.getNgaySinh()));
        values.put("hinh",sv.getHinh());
        values.put("maLopHoc",sv.getMaLopHoc());
        long row = db.insert("SINHVIEN",null,values);
        return (row>0);
    }

    public boolean update(SinhVien sv){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSv",sv.getTenSv());
        values.put("ngaySinh",sdf.format(sv.getNgaySinh()));
        values.put("maLopHoc",sv.getMaLopHoc());
        int row = db.update("SINHVIEN", values, "maSv=?",new String[]{sv.getMaSv()});
        return (row>0);
    }

    public boolean delete(String maSv){
        SQLiteDatabase db = helper.getWritableDatabase();
        int row = db.delete("SINHVIEN", "maSv=?",new String[]{maSv});
        return (row>0);
    }

}
