package com.example.duclinh1610.model;

import java.io.Serializable;

public class Info_Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String maSach;
    private String tenSach;
    private String tacGia;
    private String namXB;

    public Info_Book(String maSach, String tenSach, String tacGia, String namXB) {
        super();
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.namXB = namXB;
    }

    public Info_Book(){
        super();
    }
//--------------------------------------------------------------------
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getNamXB() {
        return namXB;
    }

    public void setNamXB(String namXB) {
        this.namXB = namXB;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }
}
