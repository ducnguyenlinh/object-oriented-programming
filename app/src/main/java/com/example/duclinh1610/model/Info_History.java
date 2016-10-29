package com.example.duclinh1610.model;

import java.io.Serializable;

public class Info_History implements Serializable{
    private static final long serialVersionUID = 1L;

    private String maSV;
    private String tenND;
    private String ngayMuon;

    public Info_History(String maSV, String tenND, String ngayMuon) {
        super();
        this.maSV = maSV;
        this.tenND = tenND;
        this.ngayMuon = ngayMuon;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getTenND() {
        return tenND;
    }

    public void setTenND(String tenND) {
        this.tenND = tenND;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }
}
