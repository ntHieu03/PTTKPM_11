/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SERVICE;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoan;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class TaiKhoanSERVICE {
    
    private TaiKhoanDAO taiKhoanDAO;
    
    public TaiKhoanSERVICE() {
        taiKhoanDAO = new TaiKhoanDAO();
    }
    
    public List<TaiKhoan> getAllTaiKhoan(){
            return taiKhoanDAO.getAllTaiKhoan();
    }
    
    public void themTaiKhoan(TaiKhoan taiKhoan) {
        taiKhoanDAO.themTaiKhoan(taiKhoan);
    }
    
    public void suaTaiKhoan(TaiKhoan taiKhoan) {
        taiKhoanDAO.suaTaiKhoan(taiKhoan);
    }
    
    public void xoaTaiKhoan(String tenDangNhap) {
        taiKhoanDAO.xoaTaiKhoan(tenDangNhap);
    }
}
