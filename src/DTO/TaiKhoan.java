/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ADMIN
 */
public class TaiKhoan {

    public TaiKhoan(String tenDangNhap, String tenHienThi, String matKhau, int loaiTaiKhoan) {
        this.tenDangNhap = tenDangNhap;
        this.tenHienThi = tenHienThi;
        this.matKhau = matKhau;
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public TaiKhoan() {
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public int getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public void setTenHienThi(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public void setLoaiTaiKhoan(int loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }
    
    public String tenDangNhap;
    public String tenHienThi;
    public String matKhau;
    public int loaiTaiKhoan;
}
