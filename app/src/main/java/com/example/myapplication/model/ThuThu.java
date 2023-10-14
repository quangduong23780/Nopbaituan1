package com.example.myapplication.model;

public class ThuThu {
    private int matt;
    private String hoten;
    private String matkhau;

    public ThuThu(int matt, String hoten, String matkhau) {
        this.matt = matt;
        this.hoten = hoten;
        this.matkhau = matkhau;
    }

    public int getMatt() {
        return matt;
    }

    public void setMatt(int matt) {
        this.matt = matt;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
