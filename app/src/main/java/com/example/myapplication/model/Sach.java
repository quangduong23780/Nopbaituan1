package com.example.myapplication.model;

public class Sach {
    private int masach;
    private String tensach;
    private int giathue;
    private int maloai;
    private int soluongmuon;
    private String tenloai;
    private int namxb;

    public Sach(int masach, String tensach, int giathue, int maloai, int anInt, String string,int namxb) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
        this.namxb = namxb;

    }
    public Sach(int masach, String tensach, int soluongmuon) {
        this.masach = masach;
        this.tensach = tensach;
        this.soluongmuon = soluongmuon;
    }
    public Sach(int masach, String tensach, int giathue, int maloai, String tenloai, int namxb) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
        this.tenloai = tenloai;
        this.namxb = namxb;
    }

    public Integer getNamxb() {return namxb;}

    public void setNamxb(Integer namxb) {
        this.namxb = namxb;
    }

    public String getTenloai() {return tenloai;}

    public void setTenloai(String tenloai) {this.tenloai = tenloai;}

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public int getGiathue() {
        return giathue;
    }

    public void setGiathue(int giathue) {
        this.giathue = giathue;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }
    public int getSoluongmuon() {return soluongmuon;}
    public void setSoluongmuon(int soluongmuon) {this.soluongmuon = soluongmuon;}

}
