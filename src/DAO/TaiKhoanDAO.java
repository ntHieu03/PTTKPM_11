/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.TaiKhoan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class TaiKhoanDAO {
    
    public List<TaiKhoan> getAllTaiKhoan(){
        List<TaiKhoan> taiKhoans = new ArrayList<TaiKhoan>();
        
        Connection connection = ConnectSQL.getConnection();
        
        String sql = "  SELECT tenDangNhap, tenHienThi,loaiTaiKhoan FROM TaiKhoan";
        
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                TaiKhoan taiKhoan = new TaiKhoan();
                
                //bien tu csdl
                taiKhoan.setTenDangNhap(rs.getString("tenDangNhap"));
                taiKhoan.setTenHienThi(rs.getString("tenHienThi"));
                taiKhoan.setLoaiTaiKhoan(rs.getInt("loaiTaiKhoan"));
                
                taiKhoans.add(taiKhoan);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return taiKhoans;
    }
    
    public void themTaiKhoan(TaiKhoan taiKhoan) {
        Connection connection = ConnectSQL.getConnection();
            String sql = "INSERT INTO TaiKhoan VALUES (?, ?, ?, ?)";
            try {           
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, taiKhoan.getTenDangNhap());
                preparedStatement.setString(2, taiKhoan.getTenHienThi());
                preparedStatement.setInt(3, taiKhoan.getLoaiTaiKhoan());
                preparedStatement.setString(4, taiKhoan.getMatKhau());
                
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
     
    public void suaTaiKhoan(TaiKhoan taiKhoan) {
        Connection connection = ConnectSQL.getConnection();
            String sql = "UPDATE TaiKhoan SET tenHienThi = ? WHERE  tenDangNhap = ?";
            try {           
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, taiKhoan.getTenHienThi());
                preparedStatement.setString(2, taiKhoan.getTenDangNhap());
                
                preparedStatement.executeUpdate();
                System.out.println(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    
    public void xoaTaiKhoan(String tenDangNhap) {
        Connection connection = ConnectSQL.getConnection();
            String sql = "DELETE FROM TaiKhoan WHERE tenDangNhap = ?";
            try {           
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, tenDangNhap);
                preparedStatement.executeUpdate();
                System.out.println(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    
}
