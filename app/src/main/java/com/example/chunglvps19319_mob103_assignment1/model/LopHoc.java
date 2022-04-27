package com.example.chunglvps19319_mob103_assignment1.model;

import java.io.Serializable;

public class LopHoc implements Serializable {
    private String maLop;
    private String tenLop;
    private int hinh;

    public LopHoc() {
    }

    public LopHoc(String maLop, String tenLop, int hinh) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.hinh = hinh;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    @Override
    public String toString() {
        return maLop;
    }
}
