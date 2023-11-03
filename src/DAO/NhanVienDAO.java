/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.DatBan;
import DTO.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class NhanVienDAO {
    
    public List<NhanVien> getAllNhanVien(){
        List<NhanVien> nhanViens = new ArrayList<NhanVien>();
        
        Connection connection = ConnectSQL.getConnection();
        
        String sql = "SELECT maNV, tenNV,ngaySinh,gioiTinh,diaChi,soDT,chucVu,imageNV,tenDangNhap FROM NhanVien";
        
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                NhanVien nhanVien = new NhanVien();
                
                //bien tu csdl
                nhanVien.setMaNV(rs.getInt("maNV"));
                nhanVien.setTenNV(rs.getString("tenNV"));
                nhanVien.setNgaySinh(rs.getString("ngaySinh"));
                nhanVien.setGioiTinh(rs.getString("gioiTinh"));
                nhanVien.setSoDT(rs.getString("soDT"));
                nhanVien.setDiaChi(rs.getString("diaChi"));
                nhanVien.setChucVu(rs.getString("chucVu"));
                nhanVien.setImageNV(rs.getString("imageNV"));
                nhanVien.setTenDangNhap(rs.getString("tenDangNhap"));
                
                nhanViens.add(nhanVien);
                System.out.println(rs);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return nhanViens;
    }
    
    public List<NhanVien> getNhanVienbyMaNV(){
        List<NhanVien> nhanViens = new ArrayList<NhanVien>();
        
        Connection connection = ConnectSQL.getConnection();
        
        String sql = "SELECT tenNV,ngaySinh,gioiTinh,diaChi,soDT,chucVu,imageNV,tenDangNhap FROM NhanVien WHERE maNV =?";
        
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                NhanVien nhanVien = new NhanVien();
                
                //bien tu csdl
                nhanVien.setTenNV(rs.getString("tenNV"));
                nhanVien.setNgaySinh(rs.getString("ngaySinh"));
                nhanVien.setGioiTinh(rs.getString("gioiTinh"));
                nhanVien.setSoDT(rs.getString("soDT"));
                nhanVien.setDiaChi(rs.getString("diaChi"));
                nhanVien.setChucVu(rs.getString("chucVu"));
                nhanVien.setImageNV(rs.getString("imageNV"));
                nhanVien.setTenDangNhap(rs.getString("tenDangNhap"));
                nhanVien.setMaNV(rs.getInt("maNV"));
                
                nhanViens.add(nhanVien);
                System.out.println(rs);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return nhanViens;
    }
    
    public void themNhanVien(NhanVien nv) 
    {
        Connection connection = ConnectSQL.getConnection();
        String sql = "Insert into NhanVien(tenNV,ngaySinh,gioiTinh,soDT,diaChi,chucVu,imageNV,tenDangNhap)"
                + "values (?,?,?,?,?,?,?,?)";
        try 
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nv.getTenNV());
            preparedStatement.setString(2, nv.getNgaySinh());
            preparedStatement.setString(3, nv.getGioiTinh());
            preparedStatement.setString(4, nv.getSoDT());
            preparedStatement.setString(5, nv.getDiaChi());
            preparedStatement.setString(6, nv.getChucVu());
            preparedStatement.setString(7, nv.getImageNV());
            preparedStatement.setString(8, nv.getTenDangNhap());
            
            int rs = preparedStatement.executeUpdate();
            System.out.println(rs);
            
        } 
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void suaNhanVien(NhanVien nv) 
    {
        Connection connection = ConnectSQL.getConnection();
        String sql = "UPDATE NhanVien SET tenNV = ?, ngaySinh = ?, gioiTinh = ?, soDT = ?,"
                + "  diaChi = ?, chucVu = ?, imageNV = ?, tenDangNhap = ? WHERE maNV = ?";
        try 
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nv.getTenNV());
            preparedStatement.setString(2, nv.getNgaySinh());
            preparedStatement.setString(3, nv.getGioiTinh());
            preparedStatement.setString(4, nv.getSoDT());
            preparedStatement.setString(5, nv.getDiaChi());
            preparedStatement.setString(6, nv.getChucVu());
            preparedStatement.setString(7, nv.getImageNV());
            preparedStatement.setString(8, nv.getTenDangNhap());
            preparedStatement.setInt(9, nv.getMaNV());
            
            preparedStatement.executeUpdate();
            System.out.println(preparedStatement);
        } 
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    
    public void xoaNhanVien(String maNV) {
        Connection connection = ConnectSQL.getConnection();
            String sql = "DELETE FROM NhanVien WHERE maNV = ?";
            try {           
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, maNV);
                preparedStatement.executeUpdate();
                System.out.println(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    
    public List<NhanVien> layTaiKhoan(){
        List<NhanVien> nhanViens = new ArrayList<NhanVien>();
        
        Connection connection = ConnectSQL.getConnection();
        
        String sql = "  SELECT taiKhoan,matKhau FROM NhanVien WHERE taiKhoan = ?  AND matKhau = ?";
        
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                NhanVien nhanVien = new NhanVien();
                
                //bien tu csdl
                nhanVien.setTaiKhoan(rs.getString("taiKhoan"));
                nhanVien.setMatKhau(rs.getString("matKhau"));
                
                nhanViens.add(nhanVien);
                System.out.println(rs);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return nhanViens;
    }
}
