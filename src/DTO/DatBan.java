/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class DatBan {

    public int maKH;
    public String tenKhachHang;
    public String soDT;
    public int maSoBan;
    public String ngayDat;
    public String trangThaiDat;
    public String ghiChu;

    public void DatBan(int maKH, String tenKhachHang, String soDT, int maSoBan, String tenBan, String ngayDat, String trangThaiDat, String ghiChu){
        this.maKH =maKH;
        this.tenKhachHang =tenKhachHang;
        this.soDT = soDT;
        this.maSoBan = maSoBan;
        this.ngayDat = ngayDat;
        this.trangThaiDat = trangThaiDat;
        this.ghiChu = ghiChu;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public void setMaSoBan(int maSoBan) {
        this.maSoBan = maSoBan;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public void setTrangThaiDat(String trangThaiDat) {
        this.trangThaiDat = trangThaiDat;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getMaKH() {
        return maKH;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public String getSoDT() {
        return soDT;
    }

    public int getMaSoBan() {
        return maSoBan;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public String getTrangThaiDat() {
        return trangThaiDat;
    }

    public String getGhiChu() {
        return ghiChu;
    }
    
}
