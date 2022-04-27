package com.example.chunglvps19319_mob103_assignment1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chunglvps19319_mob103_assignment1.database.DbHelper;
import com.example.chunglvps19319_mob103_assignment1.model.LopHoc;
import com.example.chunglvps19319_mob103_assignment1.model.SinhVien;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LopHocDAO {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DbHelper helper;
    Context context;

    public LopHocDAO(Context context){
        helper = new DbHelper(context);
        this.context = context;
    }

    public ArrayList<LopHoc> getAll(){
        ArrayList<LopHoc> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM LOPHOC";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();   //đưa con trỏ về dòng đầu tiên
        while(cs.isAfterLast() == false){
            try{
                String maLop = cs.getString(0);
                String tenLop = cs.getString(1);
                String tenHinh = cs.getString(2);
                int hinh = context.getResources().getIdentifier(tenHinh, "drawable",context.getPackageName());
                LopHoc lh = new LopHoc(maLop,tenLop,hinh);
                list.add(lh);

            } catch (Exception e) {
                e.printStackTrace();
            }
            cs.moveToNext();
        }
        return list;
    }

//    private ArrayList<LopHoc> getData(String sql, String... arg){
//        ArrayList<LopHoc> list = new ArrayList<>();
//        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor cs = db.rawQuery(sql,arg);
//        cs.moveToFirst();
//        while(cs.isAfterLast() == false){
//            String maLop = cs.getString(0);
//            String tenLop = cs.getString(1);
//            String tenHinh = cs.getString(2);
//            int hinh = context.getResources().getIdentifier(tenHinh, "drawable",context.getPackageName());
//            LopHoc lh = new LopHoc(maLop, tenLop, hinh);
//            list.add(lh);
//            cs.moveToNext();
//        }
//        return list;
//    }

    //them dữ liệu vào database, trả về boolean?
    public boolean insert(LopHoc lh){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maLop",lh.getMaLop());
        values.put("tenLop",lh.getTenLop());
        values.put("hinh",lh.getHinh());
        long row = db.insert("LOPHOC",null,values);
        return (row>0);
    }
    public boolean update(LopHoc lh){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLop",lh.getTenLop());
        int row = db.update("LOPHOC", values, "maLop=?",new String[]{lh.getMaLop()});
        return (row>0);
    }
    public boolean delete(String maLop){
        SQLiteDatabase db = helper.getWritableDatabase();
        int row = db.delete("LOPHOC", "maLop=?",new String[]{maLop});
        return (row>0);
    }
}
