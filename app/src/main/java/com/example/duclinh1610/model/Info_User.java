package com.example.duclinh1610.model;

import java.io.Serializable;

public class Info_User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String maSV;
    private String tenND;
    private String ngayDK;
    private String ngaySinh;

    public Info_User(String maSV, String tenND, String ngayDK, String ngaySinh) {
        super();
        this.maSV = maSV;
        this.tenND = tenND;
        this.ngayDK = ngayDK;
        this.ngaySinh = ngaySinh;
    }

    public Info_User(){
        super();
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

    public String getNgayDK() {
        return ngayDK;
    }

    public void setNgayDK(String ngayDK) {
        this.ngayDK = ngayDK;
    }

    public String getTenND() {
        return tenND;
    }

    public void setTenND(String tenND) {
        this.tenND = tenND;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
