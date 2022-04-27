package com.example.chunglvps19319_mob103_assignment1.model;

import java.io.Serializable;
import java.util.Date;

public class SinhVien implements Serializable {
    private String MaSv;
    private String tenSv;
    private Date ngaySinh;
    private int hinh;
    private String maLopHoc;

    public SinhVien() {
    }

    public SinhVien(String maSv, String tenSv, Date ngaySinh, int hinh, String maLopHoc) {
        MaSv = maSv;
        this.tenSv = tenSv;
        this.ngaySinh = ngaySinh;
        this.hinh = hinh;
        this.maLopHoc = maLopHoc;
    }

    public SinhVien(String tenSv, Date ngaySinh, int hinh, String maLopHoc) {
        this.tenSv = tenSv;
        this.ngaySinh = ngaySinh;
        this.hinh = hinh;
        this.maLopHoc = maLopHoc;
    }

    public String getMaSv() {
        return MaSv;
    }

    public void setMaSv(String maSv) {
        MaSv = maSv;
    }

    public String getTenSv() {
        return tenSv;
    }

    public void setTenSv(String tenSv) {
        this.tenSv = tenSv;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getMaLopHoc() {
        return maLopHoc;
    }

    public void setMaLopHoc(String maLopHoc) {
        this.maLopHoc = maLopHoc;
    }
}
