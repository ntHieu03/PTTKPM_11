/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VIEW;

import DAO.ChiTietHDDAO;
import DAO.ConnectSQL;
import DAO.DanhMucDAO;
import DAO.HoaDonDAO;
import DAO.KhuyenMaiDAO;
import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO;
import DAO.ThucDonDAO;
import DTO.ChiTietHD;
import DTO.DanhMuc;
import DTO.HoaDon;
import DTO.KhuyenMai;
import DTO.NhanVien;
import DTO.TaiKhoan;
import DTO.ThucDon;
import SERVICE.DanhMucSERVICE;
import SERVICE.HoaDonSERVICE;
import SERVICE.KhuyenMaiSERVICE;
import SERVICE.NhanVienSERVICE;
import SERVICE.TaiKhoanSERVICE;
import SERVICE.ThucDonSERVICE;
import java.awt.Color;
import java.awt.Image;
import java.time.LocalDate;
import java.awt.Rectangle;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ADMIN
 */
public class QuanLy extends javax.swing.JFrame {

    /**
     * Creates new form TrangChinh
     */
    String filename = null;
    byte[] image = null;
    NhanVien nhanVien;
    NhanVienDAO nhanVienDAO;
    NhanVienSERVICE nhanVienSERVICE;
    DefaultTableModel modelNhanVien;
    
    
   HoaDon hoaDon; 
   HoaDonDAO hoaDonDAO;
    HoaDonSERVICE hoaDonSERVICE;
    public Connection conn = ConnectSQL.getConnection();
    public PreparedStatement ps = null;
    public ResultSet rs = null;
    DefaultTableModel modelHoaDon;
   
   
    ThucDon thucDon;
    ThucDonDAO thucDonDAO;
    ThucDonSERVICE thucDonSERVICE;
    DefaultTableModel modelThucDon;
    
    
    DanhMuc danhMuc;
    DanhMucDAO danhMucDAO;
    DanhMucSERVICE danhMucSERVICE;
    DefaultTableModel modelDanhMuc;
    
    
    TaiKhoan taiKhoan;
    TaiKhoanDAO taiKhoanDAO;
    TaiKhoanSERVICE taiKhoanSERVICE;
    DefaultTableModel modelTaiKhoan;
    
    ChiTietHD chiTietHD;
    ChiTietHDDAO chiTietHDDAO;
    DefaultTableModel modelLSHD;
    DefaultTableModel modelCTHD;
    
    DefaultTableModel modelDoanhThuMon;
    DefaultTableModel modelDoanhthuNV;
    DefaultTableModel modelDoanhThuTG;
    
    KhuyenMai khuyenMai;
    KhuyenMaiDAO khuyenMaiDAO;
    KhuyenMaiSERVICE khuyenMaiSERVICE;
    DefaultTableModel modelkhuyenMai;
    DefaultTableModel modelDSKhuyenMai;
    
    
    DefaultTableModel modelTatCaMon;
    DefaultTableModel modelDoAn;
    DefaultTableModel modelNuocUong;
    
    public QuanLy() {
        initComponents();
        LayThoiGianHT();
        
        //NHÂN VIÊN
        DisableNV();
        nhanVienSERVICE = new NhanVienSERVICE();
        nhanVien = new NhanVien();
        
        modelNhanVien = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { 
                return false;
            }
        };
        
        tableNV.setModel(modelNhanVien);
        modelNhanVien.addColumn("Mã NV");
        modelNhanVien.addColumn("Tên NV");
        modelNhanVien.addColumn("Ngày sinh");
        modelNhanVien.addColumn("Giới tính");
        modelNhanVien.addColumn("SĐT");
        modelNhanVien.addColumn("Địa chỉ");
        modelNhanVien.addColumn("Chức vụ");
        modelNhanVien.addColumn("Tên đăng nhập");
        modelNhanVien.addColumn("Hình ảnh");
        
        setDataTableNV( nhanVienSERVICE.getAllNhanVien()); //để dữ liệu vào bảng
    
        
        //HÓA ĐƠN
        modelHoaDon = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { 
                return false;
            }
        };
        
        hoaDon = new HoaDon();
        hoaDonDAO  = new HoaDonDAO();
        hoaDonSERVICE  = new HoaDonSERVICE();
        
        loadTenMon();
        loadTenNV();
        modelHoaDon = (DefaultTableModel)tableHoaDon.getModel();
        
        tableHoaDon.setModel(modelHoaDon);
        modelHoaDon.addColumn("Mã món");
        modelHoaDon.addColumn("Tên món");
        modelHoaDon.addColumn("Giá tiền");
        modelHoaDon.addColumn("Số lượng");
        modelHoaDon.addColumn("Thành tiền");
        
        //THỰC ĐƠN
        DisableMA();
        thucDon = new ThucDon();
        thucDonSERVICE = new ThucDonSERVICE();
        
        modelThucDon = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { 
                return false;
            }
        };
        
        tableFood.setModel(modelThucDon);
        modelThucDon.addColumn("Mã món");
        modelThucDon.addColumn("Tên món");
        modelThucDon.addColumn("Loại món");
        modelThucDon.addColumn("Đơn vị tính");
        modelThucDon.addColumn("Giá tiền");
        modelThucDon.addColumn("Số lượng");
        modelThucDon.addColumn("Hình ảnh");
        
        setDataTableTD( thucDonSERVICE.getAllThucDon()); //để dữ liệu vào bảng
        
        //DANH MỤC
        DisableDM();
        danhMuc = new DanhMuc();
        danhMucSERVICE = new DanhMucSERVICE();
        
        modelDanhMuc = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { 
                return false;
            }
        };
        
        tableDanhMuc.setModel(modelDanhMuc);
        modelDanhMuc.addColumn("Mã loại món");
        modelDanhMuc.addColumn("Tên loại món");
        modelDanhMuc.addColumn("Hình ảnh");
        
        setDataTableDM( danhMucSERVICE.getAllDanhMuc()); //để dữ liệu vào bảng
        
        //TÀI KHOẢN
        //DisableDM();
        taiKhoan = new TaiKhoan();
        taiKhoanSERVICE = new TaiKhoanSERVICE();
        
        modelTaiKhoan = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { 
                return false;
            }
        };
        
        tableTaiKhoan.setModel(modelTaiKhoan);
        modelTaiKhoan.addColumn("Tên đăng nhập");
        modelTaiKhoan.addColumn("Tên hiển thị");
        modelTaiKhoan.addColumn("Loại tài khoản");
        
        setDataTableTK( taiKhoanSERVICE.getAllTaiKhoan()); //để dữ liệu vào bảng
        
        
        modelCTHD=(DefaultTableModel) tableCTHD.getModel();
        modelLSHD=(DefaultTableModel) tableLSHD.getModel();
        HienThiCboTenMon();
        HienThiCboNhanVien();
        hienThiTableHoaDon();
        
        //DOANH THU
        modelDoanhThuMon=(DefaultTableModel) tableThongkeMon.getModel();
        modelDoanhthuNV=(DefaultTableModel) tableThongkeNV.getModel();
        modelDoanhThuTG=(DefaultTableModel) tableThongke.getModel();
        
        //KHUYẾN MÃI
        khuyenMaiSERVICE = new KhuyenMaiSERVICE();
        khuyenMai = new KhuyenMai();
        
        modelkhuyenMai=(DefaultTableModel) tableKhuyenMai.getModel();
        modelDSKhuyenMai=(DefaultTableModel) tableDSKhuyenMai.getModel();
        hienThiKhuyenMai();
        
        //THỰC ĐƠN MÓN
        
        modelTatCaMon=(DefaultTableModel) tabTatCaMon3.getModel();
        modelDoAn=(DefaultTableModel) tabDoAn.getModel();
        modelNuocUong=(DefaultTableModel) tabNuocUong.getModel();
        hienThiTatCa();
        hienThiDoAn();
        hienThiNuocUong();
        
    } //end
    
    
    private void setDataTableNV(  List<NhanVien> nhanViens ){ //lấy dữ liệu cho bảng
            for(NhanVien nhanVien : nhanViens){
            modelNhanVien.addRow(new Object[]{nhanVien.getMaNV(),nhanVien.getTenNV(),
                nhanVien.getNgaySinh(),nhanVien.getGioiTinh(),nhanVien.getSoDT(),nhanVien.getDiaChi(),nhanVien.getChucVu(),
                nhanVien.getTenDangNhap(), nhanVien.getImageNV()});
        }
    }
    
    private void setDataTableTD(  List<ThucDon> thucDons ){ //lấy dữ liệu cho bảng
                for(ThucDon thucDon : thucDons){
            modelThucDon.addRow(new Object[]{thucDon.getMaMonAn(),thucDon.getTenMonAn(),
                thucDon.getMaLoaiMon(),thucDon.getDonVi(),thucDon.getGiaTien(),thucDon.getSoLuong(),
                thucDon.getImageMonAn()});
        }
}
    
    private void setDataTableDM(  List<DanhMuc> danhMucs ){ //lấy dữ liệu cho bảng
                for(DanhMuc danhMuc : danhMucs){
            modelDanhMuc.addRow(new Object[]{danhMuc.getMaLoai(),danhMuc.getTenLoai(),
                danhMuc.getImageDM()});
        }
}
    
    private void setDataTableTK(  List<TaiKhoan> taiKhoans ){ //lấy dữ liệu cho bảng
                for(TaiKhoan taiKhoan : taiKhoans){
            modelTaiKhoan.addRow(new Object[]{taiKhoan.getTenDangNhap(),taiKhoan.getTenHienThi(),
                taiKhoan.getLoaiTaiKhoan()});
        }
}
    
    public void DisableNV(){
        tfMaNV.setEnabled(false);
        tfHoten.setEnabled(false);
        tfDiachi.setEnabled(false);
        tfSDT.setEnabled(false);
        tfNgaySinh.setEnabled(false);
        tfTaiKhoan.setEnabled(false);
        tfChucVu.setEnabled(false);
        rdoNam.setEnabled(false);
        rdoNu.setEnabled(false);
        btnLuuNV.setEnabled(false);
        btnXoaNV.setEnabled(false);
        btnSuaNV.setEnabled(false);
    }
    
    public void EnableNV(){
        tfHoten.setEnabled(true);
        tfDiachi.setEnabled(true);
        tfSDT.setEnabled(true);
        tfNgaySinh.setEnabled(true);
        tfTaiKhoan.setEnabled(true);
        rdoNam.setEnabled(true);
        rdoNu.setEnabled(true);
        btnLuuNV.setEnabled(true);
        btnXoaNV.setEnabled(true);
        btnSuaNV.setEnabled(true);
    }
            

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        panelMenu = new javax.swing.JPanel();
        panelMonAn = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        panelHoaDon = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        panelDanhMuc = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        panelDoanhThu = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        panelNhanVien = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        panelTaiKhoan = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        panelTrangChu = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        panelCTHD = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        panelKhuyenMai = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        tfTenDangNhapNV = new javax.swing.JTextField();
        panelGiaoDien = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnTrangChu = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        btnMinimize6 = new javax.swing.JLabel();
        lbThoat8 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        btnExit = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        pnHoaDon = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnMinimize = new javax.swing.JLabel();
        lbThoat2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        tfGiaTien = new javax.swing.JTextField();
        tfMaMon = new javax.swing.JTextField();
        tfSoLuong = new javax.swing.JTextField();
        cbTenMon = new javax.swing.JComboBox<>();
        cbTenNV = new javax.swing.JComboBox<>();
        tfMaNVHD = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        tfTenLoaiMon = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        tfSoLuongTon = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        tfThanhTien = new javax.swing.JTextField();
        cbCTGiamGia = new javax.swing.JComboBox<>();
        jLabel80 = new javax.swing.JLabel();
        lbMaKM = new javax.swing.JLabel();
        tfVATHD = new javax.swing.JTextField();
        checkVATHD = new javax.swing.JCheckBox();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel62 = new javax.swing.JPanel();
        jPanel63 = new javax.swing.JPanel();
        panelThucDonMon3 = new javax.swing.JTabbedPane();
        paneThucDon3 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tabTatCaMon3 = new javax.swing.JTable();
        panelDoAn = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tabDoAn = new javax.swing.JTable();
        panelNuocUong = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        tabNuocUong = new javax.swing.JTable();
        jPanel55 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel56 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tableKhuyenMai = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        btnThanhToan = new javax.swing.JButton();
        jLabel65 = new javax.swing.JLabel();
        lbTienthua = new javax.swing.JLabel();
        lbTongTien = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        tfGiamGia = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableHoaDon = new javax.swing.JTable();
        jPanel30 = new javax.swing.JPanel();
        btnThemMoi = new javax.swing.JButton();
        btnXoa4 = new javax.swing.JButton();
        btnLamMoi3 = new javax.swing.JButton();
        panelChiTietHD = new javax.swing.JPanel();
        jpnalLSHĐ = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableLSHD = new javax.swing.JTable();
        jpnalCTHD = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableCTHD = new javax.swing.JTable();
        jPanel44 = new javax.swing.JPanel();
        tfThanhTienCTHD = new javax.swing.JTextField();
        cboTenMonAn = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        tfGiaBanCTHD = new javax.swing.JTextField();
        tfSoLuongCTHD = new javax.swing.JTextField();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnInHoaDonCT = new javax.swing.JButton();
        jPanel45 = new javax.swing.JPanel();
        btnMinimize8 = new javax.swing.JLabel();
        lbThoat10 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfMaHD = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboTenNV = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        tfTongTienHD = new javax.swing.JTextField();
        tfTenNV = new javax.swing.JTextField();
        pnDanhMuc = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDanhMuc = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tfMaDM = new javax.swing.JTextField();
        tfTenDM = new javax.swing.JTextField();
        tfLinkDM = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnMinimize1 = new javax.swing.JLabel();
        lbThoat = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        btnThemDM = new javax.swing.JButton();
        btnSuaDM = new javax.swing.JButton();
        btnLuuDM = new javax.swing.JButton();
        btnXoaDM = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        lbImageDM = new javax.swing.JLabel();
        pnMonAn = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfMaMonAn = new javax.swing.JTextField();
        tfTenMonAn = new javax.swing.JTextField();
        btnOpen = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        tfDonViTinh = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tfGiaTienMon = new javax.swing.JTextField();
        tfTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        lbLoai = new javax.swing.JLabel();
        cbLoaiMon = new javax.swing.JComboBox<>();
        lbGia1 = new javax.swing.JLabel();
        tfTenLoai = new javax.swing.JTextField();
        tfSoLuongMon = new javax.swing.JTextField();
        tfLinkMonAn = new javax.swing.JTextField();
        jPanel39 = new javax.swing.JPanel();
        lbImageFood = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        btnMinimize2 = new javax.swing.JLabel();
        lbThoat1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableFood = new javax.swing.JTable();
        jPanel41 = new javax.swing.JPanel();
        btnLuuMon = new javax.swing.JButton();
        btnSuaMon = new javax.swing.JButton();
        btnXoaMon = new javax.swing.JButton();
        btnThemMon = new javax.swing.JButton();
        pnDoanhThu = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel25 = new javax.swing.JPanel();
        jPanel60 = new javax.swing.JPanel();
        jLabel99 = new javax.swing.JLabel();
        cbMonAnDoanhThu = new javax.swing.JComboBox<>();
        btnTraCuuMon = new javax.swing.JButton();
        jPanel66 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tableThongkeMon = new javax.swing.JTable();
        jPanel68 = new javax.swing.JPanel();
        lbTongDoanhThuMon = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jPanel69 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        lbTongHDMon = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        lbTongDoanhThuNV = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jPanel61 = new javax.swing.JPanel();
        jLabel100 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableThongkeNV = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        btnTraCuuNV = new javax.swing.JButton();
        cbNVDoanhThu = new javax.swing.JComboBox<>();
        jLabel104 = new javax.swing.JLabel();
        jPanel67 = new javax.swing.JPanel();
        jLabel106 = new javax.swing.JLabel();
        lbTongHDNV = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        jPanel70 = new javax.swing.JPanel();
        jPanel71 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tableThongke = new javax.swing.JTable();
        jPanel72 = new javax.swing.JPanel();
        lbTongDoanhThuTG = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        lbTongHDTG = new javax.swing.JLabel();
        btnTraCuuTKTG = new javax.swing.JButton();
        tfNgayHoaDon = new com.toedter.calendar.JDateChooser();
        jLabel105 = new javax.swing.JLabel();
        jPanel64 = new javax.swing.JPanel();
        jLabel101 = new javax.swing.JLabel();
        jPanel65 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        btnMinimize7 = new javax.swing.JLabel();
        lbThoat7 = new javax.swing.JLabel();
        pnNhanVien = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        lbMaNV = new javax.swing.JLabel();
        lbHoten = new javax.swing.JLabel();
        lbGioitinh = new javax.swing.JLabel();
        lbNgaysinh = new javax.swing.JLabel();
        lbSDT = new javax.swing.JLabel();
        lbDiachi = new javax.swing.JLabel();
        tfMaNV = new javax.swing.JTextField();
        tfHoten = new javax.swing.JTextField();
        tfSDT = new javax.swing.JTextField();
        tfDiachi = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel37 = new javax.swing.JLabel();
        btnUpload = new javax.swing.JButton();
        tfLinkNV = new javax.swing.JTextField();
        lbThoat3 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        btnLuuNV = new javax.swing.JButton();
        btnXoaNV = new javax.swing.JButton();
        btnThemNV = new javax.swing.JButton();
        btnSuaNV = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        tfChucVu = new javax.swing.JTextField();
        tfNgaySinh = new com.toedter.calendar.JDateChooser();
        jPanel13 = new javax.swing.JPanel();
        lbImageNV = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        tfTaiKhoan = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        btnMinimize3 = new javax.swing.JLabel();
        lbThoat4 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableNV = new javax.swing.JTable();
        pnTaiKhoan = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tfTenTaiKhoan = new javax.swing.JTextField();
        tfTenHienThi = new javax.swing.JTextField();
        btnThemTK = new javax.swing.JButton();
        btnSuaTK = new javax.swing.JButton();
        btnXoaTK = new javax.swing.JButton();
        btnLuuTK = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        btnCaiDatMK = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        tfLoaiTK = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableTaiKhoan = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        btnMinimize4 = new javax.swing.JLabel();
        lbThoat5 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        pnDSBan = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tableDSBan = new javax.swing.JTable();
        jPanel27 = new javax.swing.JPanel();
        btnMinimize9 = new javax.swing.JLabel();
        lbThoat11 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        btnLuuBan = new javax.swing.JButton();
        btnXoaBan = new javax.swing.JButton();
        btnThemBan = new javax.swing.JButton();
        btnSuaBan = new javax.swing.JButton();
        pnKhuyenMai = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tableDSKhuyenMai = new javax.swing.JTable();
        jPanel54 = new javax.swing.JPanel();
        btnMinimize11 = new javax.swing.JLabel();
        lbThoat13 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jPanel57 = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        tfMaKM = new javax.swing.JTextField();
        tfTenKM = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        btnLuuKM = new javax.swing.JButton();
        btnXoaKM = new javax.swing.JButton();
        btnThemKM = new javax.swing.JButton();
        btnSuaKM = new javax.swing.JButton();
        tfGiaKM = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(new java.awt.Dimension(1249, 654));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        panelMenu.setBackground(new java.awt.Color(51, 51, 51));
        panelMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelMonAn.setBackground(new java.awt.Color(51, 51, 51));
        panelMonAn.setPreferredSize(new java.awt.Dimension(188, 72));
        panelMonAn.setRequestFocusEnabled(false);
        panelMonAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelMonAnMouseClicked(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Cascadia Code", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Món ăn");

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/food_23px.png"))); // NOI18N

        javax.swing.GroupLayout panelMonAnLayout = new javax.swing.GroupLayout(panelMonAn);
        panelMonAn.setLayout(panelMonAnLayout);
        panelMonAnLayout.setHorizontalGroup(
            panelMonAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMonAnLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelMonAnLayout.setVerticalGroup(
            panelMonAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMonAnLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelMonAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        panelMenu.add(panelMonAn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 397, 218, 53));

        panelHoaDon.setBackground(new java.awt.Color(51, 51, 51));
        panelHoaDon.setPreferredSize(new java.awt.Dimension(188, 72));
        panelHoaDon.setRequestFocusEnabled(false);
        panelHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelHoaDonMouseClicked(evt);
            }
        });

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/receipt_26px.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Cascadia Code", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Hóa đơn");

        javax.swing.GroupLayout panelHoaDonLayout = new javax.swing.GroupLayout(panelHoaDon);
        panelHoaDon.setLayout(panelHoaDonLayout);
        panelHoaDonLayout.setHorizontalGroup(
            panelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHoaDonLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelHoaDonLayout.setVerticalGroup(
            panelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHoaDonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        panelMenu.add(panelHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 218, 50));

        panelDanhMuc.setBackground(new java.awt.Color(51, 51, 51));
        panelDanhMuc.setPreferredSize(new java.awt.Dimension(188, 72));
        panelDanhMuc.setRequestFocusEnabled(false);
        panelDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelDanhMucMouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Cascadia Code", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Danh mục");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/sorting_23px.png"))); // NOI18N

        javax.swing.GroupLayout panelDanhMucLayout = new javax.swing.GroupLayout(panelDanhMuc);
        panelDanhMuc.setLayout(panelDanhMucLayout);
        panelDanhMucLayout.setHorizontalGroup(
            panelDanhMucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDanhMucLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelDanhMucLayout.setVerticalGroup(
            panelDanhMucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDanhMucLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelDanhMucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        panelMenu.add(panelDanhMuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 344, 218, 53));

        panelDoanhThu.setBackground(new java.awt.Color(51, 51, 51));
        panelDoanhThu.setPreferredSize(new java.awt.Dimension(188, 72));
        panelDoanhThu.setRequestFocusEnabled(false);
        panelDoanhThu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelDoanhThuMouseClicked(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Cascadia Code", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Doanh thu");

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/presentation_24px.png"))); // NOI18N

        javax.swing.GroupLayout panelDoanhThuLayout = new javax.swing.GroupLayout(panelDoanhThu);
        panelDoanhThu.setLayout(panelDoanhThuLayout);
        panelDoanhThuLayout.setHorizontalGroup(
            panelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDoanhThuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelDoanhThuLayout.setVerticalGroup(
            panelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDoanhThuLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        panelMenu.add(panelDoanhThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 218, 53));

        panelNhanVien.setBackground(new java.awt.Color(51, 51, 51));
        panelNhanVien.setPreferredSize(new java.awt.Dimension(188, 72));
        panelNhanVien.setRequestFocusEnabled(false);
        panelNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelNhanVienMouseClicked(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Cascadia Code", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Nhân viên");

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/staff_24px.png"))); // NOI18N

        javax.swing.GroupLayout panelNhanVienLayout = new javax.swing.GroupLayout(panelNhanVien);
        panelNhanVien.setLayout(panelNhanVienLayout);
        panelNhanVienLayout.setHorizontalGroup(
            panelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNhanVienLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelNhanVienLayout.setVerticalGroup(
            panelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNhanVienLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        panelMenu.add(panelNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 503, 218, 60));

        panelTaiKhoan.setBackground(new java.awt.Color(51, 51, 51));
        panelTaiKhoan.setPreferredSize(new java.awt.Dimension(188, 72));
        panelTaiKhoan.setRequestFocusEnabled(false);
        panelTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelTaiKhoanMouseClicked(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Cascadia Code", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Tài khoản");

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/user_24px.png"))); // NOI18N

        javax.swing.GroupLayout panelTaiKhoanLayout = new javax.swing.GroupLayout(panelTaiKhoan);
        panelTaiKhoan.setLayout(panelTaiKhoanLayout);
        panelTaiKhoanLayout.setHorizontalGroup(
            panelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTaiKhoanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTaiKhoanLayout.setVerticalGroup(
            panelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTaiKhoanLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        panelMenu.add(panelTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 560, 218, 53));

        panelTrangChu.setBackground(new java.awt.Color(51, 51, 51));
        panelTrangChu.setPreferredSize(new java.awt.Dimension(188, 72));
        panelTrangChu.setRequestFocusEnabled(false);
        panelTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelTrangChuMouseClicked(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Cascadia Code", 0, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Trang chủ");

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/home_23px.png"))); // NOI18N

        javax.swing.GroupLayout panelTrangChuLayout = new javax.swing.GroupLayout(panelTrangChu);
        panelTrangChu.setLayout(panelTrangChuLayout);
        panelTrangChuLayout.setHorizontalGroup(
            panelTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTrangChuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        panelTrangChuLayout.setVerticalGroup(
            panelTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTrangChuLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(panelTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        panelMenu.add(panelTrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 83, 218, 71));

        jLabel60.setBackground(new java.awt.Color(51, 51, 51));
        jLabel60.setFont(new java.awt.Font("Cascadia Code", 1, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("QUẢN LÝ TC FOOD");
        panelMenu.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 800, 150, 30));

        panelCTHD.setBackground(new java.awt.Color(51, 51, 51));
        panelCTHD.setPreferredSize(new java.awt.Dimension(188, 72));
        panelCTHD.setRequestFocusEnabled(false);
        panelCTHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCTHDMouseClicked(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Cascadia Code", 0, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("CT Hóa Đơn");

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/clickcollect_28px.png"))); // NOI18N

        javax.swing.GroupLayout panelCTHDLayout = new javax.swing.GroupLayout(panelCTHD);
        panelCTHD.setLayout(panelCTHDLayout);
        panelCTHDLayout.setHorizontalGroup(
            panelCTHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCTHDLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelCTHDLayout.setVerticalGroup(
            panelCTHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCTHDLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelCTHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        panelMenu.add(panelCTHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 218, 53));

        panelKhuyenMai.setBackground(new java.awt.Color(51, 51, 51));
        panelKhuyenMai.setPreferredSize(new java.awt.Dimension(188, 72));
        panelKhuyenMai.setRequestFocusEnabled(false);
        panelKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelKhuyenMaiMouseClicked(evt);
            }
        });

        jLabel78.setFont(new java.awt.Font("Cascadia Code", 0, 18)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("Khuyến mãi");

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/cash_register_28px.png"))); // NOI18N

        javax.swing.GroupLayout panelKhuyenMaiLayout = new javax.swing.GroupLayout(panelKhuyenMai);
        panelKhuyenMai.setLayout(panelKhuyenMaiLayout);
        panelKhuyenMaiLayout.setHorizontalGroup(
            panelKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelKhuyenMaiLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelKhuyenMaiLayout.setVerticalGroup(
            panelKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelKhuyenMaiLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        panelMenu.add(panelKhuyenMai, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 218, 53));

        tfTenDangNhapNV.setBackground(new java.awt.Color(51, 51, 51));
        tfTenDangNhapNV.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfTenDangNhapNV.setForeground(new java.awt.Color(255, 255, 255));
        tfTenDangNhapNV.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfTenDangNhapNV.setText("Tên nhân viên");
        tfTenDangNhapNV.setBorder(null);
        tfTenDangNhapNV.setEnabled(false);
        panelMenu.add(tfTenDangNhapNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 760, 200, 40));

        panelGiaoDien.setBackground(new java.awt.Color(255, 255, 255));
        panelGiaoDien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnTrangChu.setBackground(new java.awt.Color(255, 255, 255));
        pnTrangChu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        btnMinimize6.setBackground(new java.awt.Color(49, 139, 130));
        btnMinimize6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/subtract_22px.png"))); // NOI18N
        btnMinimize6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimize6MouseClicked(evt);
            }
        });

        lbThoat8.setBackground(new java.awt.Color(49, 139, 130));
        lbThoat8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Close_22px.png"))); // NOI18N
        lbThoat8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThoat8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMinimize6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbThoat8)
                .addGap(18, 18, 18))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbThoat8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnMinimize6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/exit.png"))); // NOI18N
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/logout_36px.png"))); // NOI18N
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(49, 139, 130));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Đăng xuất");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(49, 139, 130));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Thoát");
        jLabel26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(445, 445, 445)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel26))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel25)))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        jLabel53.setBackground(new java.awt.Color(255, 255, 255));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/hellohome.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1211, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnTrangChu.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        jTabbedPane1.addTab("tab8", pnTrangChu);

        pnHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        pnHoaDon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        btnMinimize.setBackground(new java.awt.Color(49, 139, 130));
        btnMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/subtract_22px.png"))); // NOI18N
        btnMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseClicked(evt);
            }
        });

        lbThoat2.setBackground(new java.awt.Color(49, 139, 130));
        lbThoat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Close_22px.png"))); // NOI18N
        lbThoat2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThoat2MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Cascadia Code", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(49, 139, 130));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/receipt_48px.png"))); // NOI18N
        jLabel4.setText(" HÓA ĐƠN");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1063, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMinimize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbThoat2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnMinimize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbThoat2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnHoaDon.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 16, 1194, 80));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(49, 139, 130));
        jLabel54.setText("Tên món:");

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(49, 139, 130));
        jLabel55.setText("Mã món:");

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(49, 139, 130));
        jLabel56.setText("Nhân viên:");

        jLabel57.setBackground(new java.awt.Color(49, 139, 130));
        jLabel57.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(49, 139, 130));
        jLabel57.setText("Mã NV:");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(49, 139, 130));
        jLabel58.setText("Giá tiền:");

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(49, 139, 130));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("Số lượng:");

        tfGiaTien.setEnabled(false);

        tfMaMon.setEnabled(false);

        tfSoLuong.setText("1");
        tfSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSoLuongActionPerformed(evt);
            }
        });

        cbTenMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTenMonActionPerformed(evt);
            }
        });

        cbTenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTenNVActionPerformed(evt);
            }
        });

        tfMaNVHD.setEnabled(false);

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(49, 139, 130));
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("Loại món:");
        jLabel62.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tfTenLoaiMon.setEnabled(false);

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(49, 139, 130));
        jLabel63.setText("SL còn:");

        tfSoLuongTon.setEnabled(false);

        jLabel73.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(49, 139, 130));
        jLabel73.setText("Thành tiền:");

        tfThanhTien.setEnabled(false);

        cbCTGiamGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCTGiamGiaActionPerformed(evt);
            }
        });

        jLabel80.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(49, 139, 130));
        jLabel80.setText("Tên CT khuyến mãi: ");

        lbMaKM.setBackground(new java.awt.Color(255, 255, 255));
        lbMaKM.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbMaKM.setForeground(new java.awt.Color(255, 255, 255));
        lbMaKM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbMaKM.setText("Mã KM");

        tfVATHD.setForeground(new java.awt.Color(49, 139, 130));
        tfVATHD.setText("0");
        tfVATHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfVATHDActionPerformed(evt);
            }
        });

        checkVATHD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        checkVATHD.setForeground(new java.awt.Color(49, 139, 130));
        checkVATHD.setText("VAT(%)");
        checkVATHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkVATHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfSoLuong)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(checkVATHD, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(tfVATHD, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfSoLuongTon, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(tfTenLoaiMon))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbMaKM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel29Layout.createSequentialGroup()
                                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(tfMaMon))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel29Layout.createSequentialGroup()
                                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbTenNV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfMaNVHD, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel73)
                                    .addGroup(jPanel29Layout.createSequentialGroup()
                                        .addComponent(jLabel80)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbCTGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(cbTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(tfMaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfSoLuongTon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(tfTenLoaiMon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbMaKM, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)))
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(cbTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(tfMaNVHD, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1))
                            .addComponent(cbCTGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)))
                .addGap(10, 10, 10)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfVATHD, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkVATHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
        );

        jTabbedPane4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel62.setBackground(new java.awt.Color(255, 255, 255));

        jPanel63.setBackground(new java.awt.Color(255, 255, 255));

        paneThucDon3.setBackground(new java.awt.Color(255, 255, 255));

        tabTatCaMon3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabTatCaMon3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã món", "Tên món", "Đơn vị tính", "Giá bán", "Số lượng"
            }
        ));
        tabTatCaMon3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabTatCaMon3MouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tabTatCaMon3);

        javax.swing.GroupLayout paneThucDon3Layout = new javax.swing.GroupLayout(paneThucDon3);
        paneThucDon3.setLayout(paneThucDon3Layout);
        paneThucDon3Layout.setHorizontalGroup(
            paneThucDon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
        );
        paneThucDon3Layout.setVerticalGroup(
            paneThucDon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneThucDon3Layout.createSequentialGroup()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelThucDonMon3.addTab("Tất cả", paneThucDon3);

        tabDoAn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabDoAn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã món", "Tên món", "Đơn vị tính", "Giá bán", "Số lượng"
            }
        ));
        tabDoAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabDoAnMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(tabDoAn);

        javax.swing.GroupLayout panelDoAnLayout = new javax.swing.GroupLayout(panelDoAn);
        panelDoAn.setLayout(panelDoAnLayout);
        panelDoAnLayout.setHorizontalGroup(
            panelDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
        );
        panelDoAnLayout.setVerticalGroup(
            panelDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDoAnLayout.createSequentialGroup()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelThucDonMon3.addTab("Đồ ăn", panelDoAn);

        tabNuocUong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabNuocUong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã món", "Tên món", "Đơn vị tính", "Giá bán", "Số lượng"
            }
        ));
        tabNuocUong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabNuocUongMouseClicked(evt);
            }
        });
        jScrollPane22.setViewportView(tabNuocUong);

        javax.swing.GroupLayout panelNuocUongLayout = new javax.swing.GroupLayout(panelNuocUong);
        panelNuocUong.setLayout(panelNuocUongLayout);
        panelNuocUongLayout.setHorizontalGroup(
            panelNuocUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
        );
        panelNuocUongLayout.setVerticalGroup(
            panelNuocUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNuocUongLayout.createSequentialGroup()
                .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelThucDonMon3.addTab("Nước uống", panelNuocUong);

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelThucDonMon3)
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelThucDonMon3)
        );

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("Thực đơn món", jPanel62);

        jScrollPane11.setBackground(new java.awt.Color(255, 255, 255));

        tableKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã KM", "Tên khuyến mãi", "Khuyến mãi (%)"
            }
        ));
        tableKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableKhuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tableKhuyenMai);

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
        );

        jTabbedPane5.addTab("Tất cả ", jPanel56);

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5)
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5)
        );

        jTabbedPane4.addTab("CT Khuyến mãi", jPanel55);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnHoaDon.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 102, -1, 580));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(49, 139, 130));
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/pos_30px.png"))); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jLabel65.setBackground(new java.awt.Color(49, 139, 130));
        jLabel65.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(49, 139, 130));
        jLabel65.setText("Tiền cần thanh toán:");

        lbTienthua.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbTienthua.setForeground(new java.awt.Color(49, 139, 130));
        lbTienthua.setText("0 VNĐ");
        lbTienthua.setEnabled(false);

        lbTongTien.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbTongTien.setForeground(new java.awt.Color(49, 139, 130));
        lbTongTien.setText("0 VNĐ");
        lbTongTien.setEnabled(false);

        jLabel66.setBackground(new java.awt.Color(49, 139, 130));
        jLabel66.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(49, 139, 130));
        jLabel66.setText("Tổng tiền:");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(49, 139, 130));
        jLabel61.setText("Giảm giá:");

        jLabel74.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(49, 139, 130));
        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("%");

        tfGiamGia.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tfGiamGia.setForeground(new java.awt.Color(49, 139, 130));
        tfGiamGia.setText("0.0");
        tfGiamGia.setEnabled(false);
        tfGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfGiamGiaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel66, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                        .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTienthua, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(tfGiamGia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnThanhToan)
                .addGap(33, 33, 33))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfGiamGia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTienthua, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(126, 126, 126))
        );

        pnHoaDon.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 700, 600, 130));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableHoaDon.setForeground(new java.awt.Color(49, 139, 130));
        tableHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableHoaDon);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 580));

        pnHoaDon.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(724, 108, 480, 580));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        btnThemMoi.setForeground(new java.awt.Color(49, 139, 130));
        btnThemMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/clickcollect_28px.png"))); // NOI18N
        btnThemMoi.setText("Thêm món");
        btnThemMoi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThemMoi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThemMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMoiActionPerformed(evt);
            }
        });

        btnXoa4.setForeground(new java.awt.Color(49, 139, 130));
        btnXoa4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Close_24px.png"))); // NOI18N
        btnXoa4.setText("Xóa món");
        btnXoa4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnXoa4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXoa4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa4ActionPerformed(evt);
            }
        });

        btnLamMoi3.setForeground(new java.awt.Color(49, 139, 130));
        btnLamMoi3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/reset_18px.png"))); // NOI18N
        btnLamMoi3.setText("Làm mới");
        btnLamMoi3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLamMoi3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLamMoi3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoi3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(btnLamMoi3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnXoa4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(btnThemMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnXoa4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemMoi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(btnLamMoi3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pnHoaDon.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 720, 520, 110));

        jTabbedPane1.addTab("tab1", pnHoaDon);

        panelChiTietHD.setBackground(new java.awt.Color(255, 255, 255));

        jpnalLSHĐ.setBackground(new java.awt.Color(255, 255, 255));

        tableLSHD.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        tableLSHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HD", "Nhân Viên", "Ngày Xuất HD", "Tổng Tiền"
            }
        ));
        tableLSHD.setRowHeight(40);
        tableLSHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableLSHDMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tableLSHD);

        javax.swing.GroupLayout jpnalLSHĐLayout = new javax.swing.GroupLayout(jpnalLSHĐ);
        jpnalLSHĐ.setLayout(jpnalLSHĐLayout);
        jpnalLSHĐLayout.setHorizontalGroup(
            jpnalLSHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnalLSHĐLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnalLSHĐLayout.setVerticalGroup(
            jpnalLSHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnalLSHĐLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpnalCTHD.setBackground(new java.awt.Color(255, 255, 255));

        tableCTHD.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        tableCTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên món", "Giá bán", "Số lượng", "Thành tiền"
            }
        ));
        tableCTHD.setRowHeight(40);
        tableCTHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCTHDMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tableCTHD);

        javax.swing.GroupLayout jpnalCTHDLayout = new javax.swing.GroupLayout(jpnalCTHD);
        jpnalCTHD.setLayout(jpnalCTHDLayout);
        jpnalCTHDLayout.setHorizontalGroup(
            jpnalCTHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnalCTHDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE))
        );
        jpnalCTHDLayout.setVerticalGroup(
            jpnalCTHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnalCTHDLayout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));

        tfThanhTienCTHD.setEditable(false);
        tfThanhTienCTHD.setBackground(new java.awt.Color(255, 255, 255));
        tfThanhTienCTHD.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        tfThanhTienCTHD.setEnabled(false);

        cboTenMonAn.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        cboTenMonAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenMonAnActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(49, 139, 130));
        jLabel41.setText("Số lượng");

        jLabel42.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(49, 139, 130));
        jLabel42.setText("Giá bán");

        jLabel43.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(49, 139, 130));
        jLabel43.setText("Tên món ăn");

        jLabel44.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(49, 139, 130));
        jLabel44.setText("Thành tiền");

        tfGiaBanCTHD.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        tfGiaBanCTHD.setEnabled(false);
        tfGiaBanCTHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfGiaBanCTHDMouseClicked(evt);
            }
        });

        tfSoLuongCTHD.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        tfSoLuongCTHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfSoLuongCTHDMouseClicked(evt);
            }
        });
        tfSoLuongCTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSoLuongCTHDActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(49, 139, 130));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLuu.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(49, 139, 130));
        btnLuu.setText("Cập nhật");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnInHoaDonCT.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnInHoaDonCT.setForeground(new java.awt.Color(49, 139, 130));
        btnInHoaDonCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/receipt_26px.png"))); // NOI18N
        btnInHoaDonCT.setText("In HĐ");
        btnInHoaDonCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonCTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(btnInHoaDonCT)
                .addGap(12, 12, 12)
                .addComponent(btnXoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLuu)
                .addContainerGap(9, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfSoLuongCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel43)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboTenMonAn, javax.swing.GroupLayout.Alignment.TRAILING, 0, 252, Short.MAX_VALUE)
                            .addComponent(tfGiaBanCTHD, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addComponent(jLabel42)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfThanhTienCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addGap(6, 6, 6)
                .addComponent(cboTenMonAn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel42)
                .addGap(6, 6, 6)
                .addComponent(tfGiaBanCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel41)
                .addGap(6, 6, 6)
                .addComponent(tfSoLuongCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfThanhTienCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInHoaDonCT, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));

        btnMinimize8.setBackground(new java.awt.Color(49, 139, 130));
        btnMinimize8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/subtract_22px.png"))); // NOI18N
        btnMinimize8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimize8MouseClicked(evt);
            }
        });

        lbThoat10.setBackground(new java.awt.Color(49, 139, 130));
        lbThoat10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Close_22px.png"))); // NOI18N
        lbThoat10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThoat10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addContainerGap(328, Short.MAX_VALUE)
                .addComponent(btnMinimize8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbThoat10)
                .addGap(6, 6, 6))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMinimize8, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(lbThoat10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(49, 139, 130));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("LỊCH SỬ HÓA ĐƠN");

        jLabel45.setBackground(new java.awt.Color(255, 255, 255));
        jLabel45.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(49, 139, 130));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("CHI TIẾT HÓA ĐƠN");

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(49, 139, 130));
        jLabel2.setText("Mã hóa đơn");

        tfMaHD.setEditable(false);
        tfMaHD.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(49, 139, 130));
        jLabel3.setText("Nhân viên");

        cboTenNV.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        cboTenNV.setEnabled(false);
        cboTenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenNVActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(49, 139, 130));
        jLabel36.setText("Tổng tiền");

        tfTongTienHD.setEditable(false);
        tfTongTienHD.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        tfTenNV.setEnabled(false);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel36)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(tfTongTienHD)
                    .addComponent(cboTenNV, 0, 238, Short.MAX_VALUE)
                    .addComponent(tfMaHD)
                    .addComponent(tfTenNV))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfTongTienHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelChiTietHDLayout = new javax.swing.GroupLayout(panelChiTietHD);
        panelChiTietHD.setLayout(panelChiTietHDLayout);
        panelChiTietHDLayout.setHorizontalGroup(
            panelChiTietHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChiTietHDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelChiTietHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChiTietHDLayout.createSequentialGroup()
                        .addGap(0, 851, Short.MAX_VALUE)
                        .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63))
                    .addGroup(panelChiTietHDLayout.createSequentialGroup()
                        .addGroup(panelChiTietHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelChiTietHDLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jpnalLSHĐ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panelChiTietHDLayout.createSequentialGroup()
                        .addGroup(panelChiTietHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jpnalCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
        );
        panelChiTietHDLayout.setVerticalGroup(
            panelChiTietHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChiTietHDLayout.createSequentialGroup()
                .addGroup(panelChiTietHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChiTietHDLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelChiTietHDLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChiTietHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelChiTietHDLayout.createSequentialGroup()
                        .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4))
                    .addComponent(jpnalLSHĐ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChiTietHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnalCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelChiTietHDLayout.createSequentialGroup()
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab8", panelChiTietHD);

        pnDanhMuc.setBackground(new java.awt.Color(255, 255, 255));
        pnDanhMuc.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableDanhMuc.setForeground(new java.awt.Color(49, 139, 130));
        tableDanhMuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã loại món ăn", "Tên loại món ăn"
            }
        ));
        tableDanhMuc.setGridColor(new java.awt.Color(255, 255, 255));
        tableDanhMuc.setSelectionBackground(new java.awt.Color(183, 221, 217));
        tableDanhMuc.setSelectionForeground(new java.awt.Color(49, 139, 130));
        tableDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDanhMucMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableDanhMuc);

        pnDanhMuc.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 142, 677, 690));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(49, 139, 130));
        jLabel5.setText("Mã loại:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(49, 139, 130));
        jLabel6.setText("Tên loại:");

        tfMaDM.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tfMaDM.setEnabled(false);

        tfTenDM.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        tfLinkDM.setForeground(new java.awt.Color(255, 255, 255));
        tfLinkDM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(tfLinkDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(21, 21, 21)
                                .addComponent(tfMaDM, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(tfTenDM, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(83, 83, 83))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(tfMaDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTenDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addComponent(tfLinkDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnDanhMuc.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 142, 510, 170));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        btnMinimize1.setBackground(new java.awt.Color(49, 139, 130));
        btnMinimize1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/subtract_22px.png"))); // NOI18N
        btnMinimize1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimize1MouseClicked(evt);
            }
        });

        lbThoat.setBackground(new java.awt.Color(49, 139, 130));
        lbThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Close_22px.png"))); // NOI18N
        lbThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThoatMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Cascadia Code", 1, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(49, 139, 130));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/chef_60px.png"))); // NOI18N
        jLabel7.setText(" QUẢN LÝ DANH MỤC MÓN ĂN");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 939, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMinimize1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbThoat)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel7))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMinimize1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 28, Short.MAX_VALUE))
        );

        pnDanhMuc.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 1201, -1));

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnDanhMuc.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(689, 142, -1, 617));

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));

        btnThemDM.setForeground(new java.awt.Color(49, 139, 130));
        btnThemDM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/add_28px.png"))); // NOI18N
        btnThemDM.setText("THÊM");
        btnThemDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDMActionPerformed(evt);
            }
        });

        btnSuaDM.setForeground(new java.awt.Color(49, 139, 130));
        btnSuaDM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/edit_28px.png"))); // NOI18N
        btnSuaDM.setText("SỬA");
        btnSuaDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaDMActionPerformed(evt);
            }
        });

        btnLuuDM.setForeground(new java.awt.Color(49, 139, 130));
        btnLuuDM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/reset_28px.png"))); // NOI18N
        btnLuuDM.setText("LƯU");
        btnLuuDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuDMActionPerformed(evt);
            }
        });

        btnXoaDM.setForeground(new java.awt.Color(49, 139, 130));
        btnXoaDM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Delete_28px.png"))); // NOI18N
        btnXoaDM.setText("XÓA");
        btnXoaDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemDM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuaDM, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLuuDM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaDM, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaDM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemDM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuaDM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuuDM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        pnDanhMuc.add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 610, 510, 150));

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));
        jPanel43.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbImageDM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImageDM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/no-image209X214.png"))); // NOI18N
        lbImageDM.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbImageDM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbImageDMMouseClicked(evt);
            }
        });
        jPanel43.add(lbImageDM, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, 240, 230));

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnDanhMuc.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 310, 510, 290));

        jTabbedPane1.addTab("tab2", pnDanhMuc);

        pnMonAn.setBackground(new java.awt.Color(255, 255, 255));
        pnMonAn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(49, 139, 130));
        jLabel8.setText("Mã món ăn:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(49, 139, 130));
        jLabel9.setText("Tên món ăn:");

        tfMaMonAn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tfMaMonAn.setEnabled(false);

        tfTenMonAn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        btnOpen.setForeground(new java.awt.Color(49, 139, 130));
        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/openfolder_26px.png"))); // NOI18N
        btnOpen.setText("Mở ảnh");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(49, 139, 130));
        jLabel11.setText("Đơn vị tính:");

        tfDonViTinh.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(49, 139, 130));
        jLabel12.setText("Giá tiền:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(49, 139, 130));
        jLabel13.setText("Số lượng:");

        tfGiaTienMon.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        tfTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tfTimKiem.setForeground(new java.awt.Color(49, 139, 130));

        btnTimKiem.setForeground(new java.awt.Color(49, 139, 130));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/search_18px.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm:");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        lbLoai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbLoai.setForeground(new java.awt.Color(49, 139, 130));
        lbLoai.setText("Mã loại món:");
        lbLoai.setToolTipText("");

        cbLoaiMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbLoaiMon.setForeground(new java.awt.Color(49, 139, 130));
        cbLoaiMon.setToolTipText("");
        cbLoaiMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLoaiMonActionPerformed(evt);
            }
        });

        lbGia1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbGia1.setForeground(new java.awt.Color(49, 139, 130));
        lbGia1.setText("Tên loại:");
        lbGia1.setToolTipText("");

        tfTenLoai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfTenLoai.setForeground(new java.awt.Color(49, 139, 130));
        tfTenLoai.setToolTipText("");
        tfTenLoai.setEnabled(false);
        tfTenLoai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfTenLoaiKeyReleased(evt);
            }
        });

        tfLinkMonAn.setForeground(new java.awt.Color(255, 255, 255));
        tfLinkMonAn.setText("jTextField1");
        tfLinkMonAn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));

        lbImageFood.setBackground(new java.awt.Color(255, 255, 255));
        lbImageFood.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImageFood.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/no-image209X214.png"))); // NOI18N
        lbImageFood.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbImageFood.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbImageFoodMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(lbImageFood, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lbImageFood, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(tfLinkMonAn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbLoai)
                            .addComponent(lbGia1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbLoaiMon, 0, 270, Short.MAX_VALUE)
                            .addComponent(tfTenLoai)))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9)
                                .addComponent(jLabel8))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tfMaMonAn, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                                .addComponent(tfTenMonAn))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfDonViTinh)
                            .addComponent(tfGiaTienMon)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(tfSoLuongMon, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                                .addComponent(btnOpen)
                                .addGap(23, 23, 23))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)))
                .addGap(62, 62, 62))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tfMaMonAn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tfTenMonAn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbLoaiMon, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbGia1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTenLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(tfGiaTienMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(tfSoLuongMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(tfLinkMonAn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
        );

        pnMonAn.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(692, 136, -1, 620));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        btnMinimize2.setBackground(new java.awt.Color(49, 139, 130));
        btnMinimize2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/subtract_22px.png"))); // NOI18N
        btnMinimize2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimize2MouseClicked(evt);
            }
        });

        lbThoat1.setBackground(new java.awt.Color(49, 139, 130));
        lbThoat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Close_22px.png"))); // NOI18N
        lbThoat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThoat1MouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Cascadia Code", 1, 48)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(49, 139, 130));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/dinner_60px.png"))); // NOI18N
        jLabel14.setText(" QUẢN LÝ MÓN ĂN");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMinimize2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbThoat1)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbThoat1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(btnMinimize2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnMonAn.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 1204, -1));

        tableFood.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tableFood.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã món ăn", "Tên món ăn", "Loại món ăn", "Đơn vị tính", "Giá tiền", "Số lượng", "Hình ảnh"
            }
        ));
        tableFood.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableFoodMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableFood);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );

        pnMonAn.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 106, -1, 730));

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLuuMon.setForeground(new java.awt.Color(49, 139, 130));
        btnLuuMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/save_28px.png"))); // NOI18N
        btnLuuMon.setText("LƯU");
        btnLuuMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuMonActionPerformed(evt);
            }
        });
        jPanel41.add(btnLuuMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 16, -1, 48));

        btnSuaMon.setForeground(new java.awt.Color(49, 139, 130));
        btnSuaMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/edit_28px.png"))); // NOI18N
        btnSuaMon.setText("SỬA");
        btnSuaMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaMonActionPerformed(evt);
            }
        });
        jPanel41.add(btnSuaMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(257, 16, -1, 48));

        btnXoaMon.setForeground(new java.awt.Color(49, 139, 130));
        btnXoaMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Delete_28px.png"))); // NOI18N
        btnXoaMon.setText("XÓA");
        btnXoaMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaMonActionPerformed(evt);
            }
        });
        jPanel41.add(btnXoaMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(363, 16, -1, 48));

        btnThemMon.setForeground(new java.awt.Color(49, 139, 130));
        btnThemMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/add_28px.png"))); // NOI18N
        btnThemMon.setText("THÊM");
        btnThemMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMonActionPerformed(evt);
            }
        });
        jPanel41.add(btnThemMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 16, -1, 48));

        pnMonAn.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 750, 490, 70));

        jTabbedPane1.addTab("tab3", pnMonAn);

        pnDoanhThu.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        jPanel60.setBackground(new java.awt.Color(247, 247, 247));

        jLabel99.setBackground(new java.awt.Color(247, 247, 247));
        jLabel99.setFont(new java.awt.Font("Cascadia Code", 1, 24)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(49, 139, 130));
        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel99.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/dinner_60px.png"))); // NOI18N
        jLabel99.setText("DOANH THU THEO MÓN ĂN");

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel99, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnTraCuuMon.setForeground(new java.awt.Color(49, 139, 130));
        btnTraCuuMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/search_18px.png"))); // NOI18N
        btnTraCuuMon.setText("Tra cứu");
        btnTraCuuMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraCuuMonActionPerformed(evt);
            }
        });

        jPanel66.setBackground(new java.awt.Color(255, 255, 255));

        tableThongkeMon.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableThongkeMon.setForeground(new java.awt.Color(49, 139, 130));
        tableThongkeMon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Tên món", "Số lượng", "Tổng tiền hóa đơn", "Ngày bán"
            }
        ));
        tableThongkeMon.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableThongkeMon.setGridColor(new java.awt.Color(0, 102, 102));
        tableThongkeMon.setSelectionBackground(new java.awt.Color(109, 182, 179));
        jScrollPane16.setViewportView(tableThongkeMon);

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE))
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel68.setBackground(new java.awt.Color(255, 255, 255));

        lbTongDoanhThuMon.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbTongDoanhThuMon.setForeground(new java.awt.Color(49, 139, 130));
        lbTongDoanhThuMon.setText("0 VNĐ");

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(49, 139, 130));
        jLabel67.setText("Tổng tiền hóa đơn:");

        javax.swing.GroupLayout jPanel68Layout = new javax.swing.GroupLayout(jPanel68);
        jPanel68.setLayout(jPanel68Layout);
        jPanel68Layout.setHorizontalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbTongDoanhThuMon, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        jPanel68Layout.setVerticalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTongDoanhThuMon)
                    .addComponent(jLabel67))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel69.setBackground(new java.awt.Color(255, 255, 255));

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(49, 139, 130));
        jLabel70.setText("Tổng số hóa đơn:");

        lbTongHDMon.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbTongHDMon.setForeground(new java.awt.Color(49, 139, 130));
        lbTongHDMon.setText("0");

        javax.swing.GroupLayout jPanel69Layout = new javax.swing.GroupLayout(jPanel69);
        jPanel69.setLayout(jPanel69Layout);
        jPanel69Layout.setHorizontalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel69Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lbTongHDMon, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(215, Short.MAX_VALUE))
        );
        jPanel69Layout.setVerticalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel69Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTongHDMon))
                .addContainerGap())
        );

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(49, 139, 130));
        jLabel50.setText("Chọn tên món:");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel25Layout.createSequentialGroup()
                                            .addGap(152, 152, 152)
                                            .addComponent(btnTraCuuMon, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel25Layout.createSequentialGroup()
                                            .addGap(66, 66, 66)
                                            .addComponent(cbMonAnDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(88, 88, 88))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel68, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(jPanel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel50)
                        .addGap(18, 18, 18)
                        .addComponent(cbMonAnDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnTraCuuMon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))))
        );

        jTabbedPane2.addTab("MÓN ĂN", jPanel25);

        jPanel58.setBackground(new java.awt.Color(255, 255, 255));

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));

        lbTongDoanhThuNV.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbTongDoanhThuNV.setForeground(new java.awt.Color(49, 139, 130));
        lbTongDoanhThuNV.setText("0 VNĐ");

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(49, 139, 130));
        jLabel69.setText("Tổng tiền hóa đơn:");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbTongDoanhThuNV, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(212, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTongDoanhThuNV, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel61.setBackground(new java.awt.Color(247, 247, 247));

        jLabel100.setFont(new java.awt.Font("Cascadia Code", 1, 24)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(49, 139, 130));
        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/users_96px.png"))); // NOI18N
        jLabel100.setText("DOANH THU THEO NHÂN VIÊN");

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel100, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        tableThongkeNV.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableThongkeNV.setForeground(new java.awt.Color(49, 139, 130));
        tableThongkeNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Tên nhân viên", "Tổng tiền hóa đơn", "Ngày bán"
            }
        ));
        tableThongkeNV.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableThongkeNV.setGridColor(new java.awt.Color(0, 102, 102));
        tableThongkeNV.setSelectionBackground(new java.awt.Color(109, 182, 179));
        jScrollPane7.setViewportView(tableThongkeNV);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        btnTraCuuNV.setForeground(new java.awt.Color(49, 139, 130));
        btnTraCuuNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/search_18px.png"))); // NOI18N
        btnTraCuuNV.setText("Tra cứu");
        btnTraCuuNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraCuuNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(cbNVDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnTraCuuNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTraCuuNV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbNVDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel104.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(49, 139, 130));
        jLabel104.setText("Chọn tên nhân viên:");

        jPanel67.setBackground(new java.awt.Color(255, 255, 255));

        jLabel106.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(49, 139, 130));
        jLabel106.setText("Tổng số hóa đơn:");

        lbTongHDNV.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbTongHDNV.setForeground(new java.awt.Color(49, 139, 130));
        lbTongHDNV.setText("0");

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lbTongHDNV, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(366, Short.MAX_VALUE))
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(lbTongHDNV))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel58Layout.createSequentialGroup()
                .addGroup(jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel58Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel58Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel58Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel67, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addComponent(jPanel61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel58Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel58Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jLabel104)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
        );

        jTabbedPane2.addTab("NHÂN VIÊN", jPanel58);

        jPanel59.setBackground(new java.awt.Color(255, 255, 255));

        jPanel70.setBackground(new java.awt.Color(255, 255, 255));

        jPanel71.setBackground(new java.awt.Color(255, 255, 255));

        tableThongke.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableThongke.setForeground(new java.awt.Color(49, 139, 130));
        tableThongke.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Tổng tiền hóa đơn", "Ngày bán"
            }
        ));
        tableThongke.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableThongke.setGridColor(new java.awt.Color(0, 102, 102));
        tableThongke.setSelectionBackground(new java.awt.Color(109, 182, 179));
        tableThongke.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane17.setViewportView(tableThongke);

        javax.swing.GroupLayout jPanel71Layout = new javax.swing.GroupLayout(jPanel71);
        jPanel71.setLayout(jPanel71Layout);
        jPanel71Layout.setHorizontalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel71Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel71Layout.setVerticalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel71Layout.createSequentialGroup()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel72.setBackground(new java.awt.Color(255, 255, 255));

        lbTongDoanhThuTG.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbTongDoanhThuTG.setForeground(new java.awt.Color(49, 139, 130));
        lbTongDoanhThuTG.setText("0 VNĐ");

        jLabel102.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(49, 139, 130));
        jLabel102.setText("Tổng số hóa đơn:");

        jLabel103.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(49, 139, 130));
        jLabel103.setText("Tổng tiền hóa đơn:");

        lbTongHDTG.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbTongHDTG.setForeground(new java.awt.Color(49, 139, 130));
        lbTongHDTG.setText("0");

        javax.swing.GroupLayout jPanel72Layout = new javax.swing.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel72Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTongHDTG, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTongDoanhThuTG, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(242, Short.MAX_VALUE))
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel72Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTongHDTG))
                .addGap(18, 18, 18)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTongDoanhThuTG, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        btnTraCuuTKTG.setForeground(new java.awt.Color(49, 139, 130));
        btnTraCuuTKTG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/search_18px.png"))); // NOI18N
        btnTraCuuTKTG.setText("Tra cứu");
        btnTraCuuTKTG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraCuuTKTGActionPerformed(evt);
            }
        });

        jLabel105.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(49, 139, 130));
        jLabel105.setText("Chọn thời gian cần xem:");

        javax.swing.GroupLayout jPanel70Layout = new javax.swing.GroupLayout(jPanel70);
        jPanel70.setLayout(jPanel70Layout);
        jPanel70Layout.setHorizontalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel70Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel70Layout.createSequentialGroup()
                        .addComponent(jPanel72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel70Layout.createSequentialGroup()
                        .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel70Layout.createSequentialGroup()
                                .addComponent(btnTraCuuTKTG, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tfNgayHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(163, 163, 163)))
                .addComponent(jPanel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel70Layout.setVerticalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel70Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel105)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfNgayHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnTraCuuTKTG, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126)
                .addComponent(jPanel72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(jPanel70Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel64.setBackground(new java.awt.Color(247, 247, 247));

        jLabel101.setBackground(new java.awt.Color(255, 255, 255));
        jLabel101.setFont(new java.awt.Font("Cascadia Code", 1, 24)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(49, 139, 130));
        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel101.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/time_60px.png"))); // NOI18N
        jLabel101.setText("DOANH THU THEO THỜI GIAN");
        jLabel101.setFocusable(false);
        jLabel101.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, 1163, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("THỜI GIAN", jPanel59);

        jPanel65.setBackground(new java.awt.Color(247, 247, 247));

        jLabel47.setBackground(new java.awt.Color(247, 247, 247));
        jLabel47.setFont(new java.awt.Font("Cascadia Code", 1, 48)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(49, 139, 130));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("THỐNG KÊ DOANH THU");

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));

        btnMinimize7.setBackground(new java.awt.Color(49, 139, 130));
        btnMinimize7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/subtract_22px.png"))); // NOI18N
        btnMinimize7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimize7MouseClicked(evt);
            }
        });

        lbThoat7.setBackground(new java.awt.Color(49, 139, 130));
        lbThoat7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Close_22px.png"))); // NOI18N
        lbThoat7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThoat7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap(1150, Short.MAX_VALUE)
                .addComponent(btnMinimize7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbThoat7)
                .addGap(10, 10, 10))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMinimize7, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(lbThoat7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnDoanhThuLayout = new javax.swing.GroupLayout(pnDoanhThu);
        pnDoanhThu.setLayout(pnDoanhThuLayout);
        pnDoanhThuLayout.setHorizontalGroup(
            pnDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnDoanhThuLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnDoanhThuLayout.setVerticalGroup(
            pnDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", pnDoanhThu);

        pnNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        pnNhanVien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setForeground(new java.awt.Color(49, 139, 130));

        lbMaNV.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbMaNV.setForeground(new java.awt.Color(49, 139, 130));
        lbMaNV.setText("Mã nhân viên:");

        lbHoten.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbHoten.setForeground(new java.awt.Color(49, 139, 130));
        lbHoten.setText("Họ và tên:");

        lbGioitinh.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbGioitinh.setForeground(new java.awt.Color(49, 139, 130));
        lbGioitinh.setText("Giới tính:");

        lbNgaysinh.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbNgaysinh.setForeground(new java.awt.Color(49, 139, 130));
        lbNgaysinh.setText("Ngày sinh:");

        lbSDT.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbSDT.setForeground(new java.awt.Color(49, 139, 130));
        lbSDT.setText("Số điện thoại:");

        lbDiachi.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbDiachi.setForeground(new java.awt.Color(49, 139, 130));
        lbDiachi.setText("Địa chỉ:");

        tfMaNV.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tfMaNV.setEnabled(false);

        tfHoten.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        tfSDT.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tfSDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSDTKeyReleased(evt);
            }
        });

        tfDiachi.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        rdoNam.setForeground(new java.awt.Color(49, 139, 130));
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        rdoNu.setForeground(new java.awt.Color(49, 139, 130));
        rdoNu.setText("Nữ");

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(49, 139, 130));
        jLabel37.setText("Thông tin:");

        btnUpload.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnUpload.setForeground(new java.awt.Color(49, 139, 130));
        btnUpload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/openfolder_26px.png"))); // NOI18N
        btnUpload.setText("Mở hình");
        btnUpload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUploadMouseClicked(evt);
            }
        });
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        tfLinkNV.setForeground(new java.awt.Color(255, 255, 255));
        tfLinkNV.setText("jTextField1");
        tfLinkNV.setBorder(null);

        lbThoat3.setBackground(new java.awt.Color(49, 139, 130));
        lbThoat3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThoat3MouseClicked(evt);
            }
        });

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        btnLuuNV.setForeground(new java.awt.Color(49, 139, 130));
        btnLuuNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/save_28px.png"))); // NOI18N
        btnLuuNV.setText("Lưu");
        btnLuuNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuNVActionPerformed(evt);
            }
        });

        btnXoaNV.setForeground(new java.awt.Color(49, 139, 130));
        btnXoaNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Delete_28px.png"))); // NOI18N
        btnXoaNV.setText("Xóa");
        btnXoaNV.setToolTipText("");
        btnXoaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNVActionPerformed(evt);
            }
        });

        btnThemNV.setForeground(new java.awt.Color(49, 139, 130));
        btnThemNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/add_28px.png"))); // NOI18N
        btnThemNV.setText("Thêm");
        btnThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNVActionPerformed(evt);
            }
        });

        btnSuaNV.setForeground(new java.awt.Color(49, 139, 130));
        btnSuaNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/edit_28px.png"))); // NOI18N
        btnSuaNV.setText("Sửa");
        btnSuaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLuuNV, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSuaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuaNV))
                .addGap(30, 30, 30)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXoaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLuuNV))
                .addGap(29, 29, 29))
        );

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(49, 139, 130));
        jLabel38.setText("Chức vụ:");

        tfChucVu.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tfChucVu.setText("Nhân viên");
        tfChucVu.setEnabled(false);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbImageNV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImageNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/no-image209X214.png"))); // NOI18N
        lbImageNV.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbImageNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbImageNVMouseClicked(evt);
            }
        });
        jPanel13.add(lbImageNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 190));

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(49, 139, 130));
        jLabel68.setText("Tài khoản:");

        tfTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbSDT)
                                    .addComponent(lbDiachi)
                                    .addComponent(lbNgaysinh))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfDiachi)
                                    .addComponent(tfSDT)
                                    .addComponent(tfNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(lbGioitinh)
                                .addGap(50, 50, 50)
                                .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(rdoNu, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbMaNV)
                                    .addComponent(lbHoten))
                                .addGap(16, 16, 16)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                                    .addComponent(tfHoten))))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(356, 356, 356)
                                .addComponent(tfLinkNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 417, Short.MAX_VALUE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGap(88, 88, 88)
                                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(90, 90, 90))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnUpload)
                                        .addGap(127, 127, 127)))
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfTaiKhoan))
                                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(tfChucVu)))
                                .addContainerGap(132, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 842, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbThoat3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbThoat3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel37)))
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbMaNV))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfHoten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbHoten))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNgaysinh)
                            .addComponent(tfNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfDiachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbDiachi))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoNu)
                            .addComponent(rdoNam)
                            .addComponent(lbGioitinh))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpload)
                                .addGap(25, 25, 25))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel38)
                                    .addComponent(tfChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel68)
                                    .addComponent(tfTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(tfLinkNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pnNhanVien.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 48, -1, -1));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        btnMinimize3.setBackground(new java.awt.Color(49, 139, 130));
        btnMinimize3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/subtract_22px.png"))); // NOI18N
        btnMinimize3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimize3MouseClicked(evt);
            }
        });

        lbThoat4.setBackground(new java.awt.Color(49, 139, 130));
        lbThoat4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Close_22px.png"))); // NOI18N
        lbThoat4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThoat4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMinimize3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbThoat4)
                .addGap(5, 5, 5))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMinimize3, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
            .addComponent(lbThoat4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnNhanVien.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 1204, -1));

        tableNV.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Họ và Tên", "Giới tính", "Ngày sinh", "Số ĐT", "Địa chỉ", "Hình ảnh"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableNVMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableNV);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnNhanVien.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 1180, -1));

        jTabbedPane1.addTab("tab6", pnNhanVien);

        pnTaiKhoan.setBackground(new java.awt.Color(255, 255, 255));
        pnTaiKhoan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(49, 139, 130));
        jLabel15.setText("Tên tài khoản:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(49, 139, 130));
        jLabel16.setText("Tên hiển thị:");

        btnThemTK.setForeground(new java.awt.Color(49, 139, 130));
        btnThemTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/add_28px.png"))); // NOI18N
        btnThemTK.setText("THÊM");
        btnThemTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTKActionPerformed(evt);
            }
        });

        btnSuaTK.setForeground(new java.awt.Color(49, 139, 130));
        btnSuaTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/edit_28px.png"))); // NOI18N
        btnSuaTK.setText("SỬA");
        btnSuaTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaTKActionPerformed(evt);
            }
        });

        btnXoaTK.setForeground(new java.awt.Color(49, 139, 130));
        btnXoaTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Delete_28px.png"))); // NOI18N
        btnXoaTK.setText("XÓA");
        btnXoaTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTKActionPerformed(evt);
            }
        });

        btnLuuTK.setForeground(new java.awt.Color(49, 139, 130));
        btnLuuTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/save_28px.png"))); // NOI18N
        btnLuuTK.setText("LƯU");
        btnLuuTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuTKActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(49, 139, 130));
        jLabel17.setText("Loại tài khoản:");

        btnCaiDatMK.setForeground(new java.awt.Color(49, 139, 130));
        btnCaiDatMK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/reset_28px.png"))); // NOI18N
        btnCaiDatMK.setText("CÀI ĐẶT LẠI MẬT KHẨU");
        btnCaiDatMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaiDatMKActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(49, 139, 130));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Loại tài khoản = 1 : Quản lý");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(49, 139, 130));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Loại tài khoản = 0 : Nhân viên");

        tfLoaiTK.setEnabled(false);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfTenTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfTenHienThi)
                            .addComponent(tfLoaiTK)))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnCaiDatMK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnThemTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSuaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(63, 63, 63)
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnLuuTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnXoaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel39)
                .addGap(70, 70, 70)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTenTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(tfTenHienThi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(tfLoaiTK, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemTK, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuuTK, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCaiDatMK, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        pnTaiKhoan.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(651, 148, -1, 670));

        tableTaiKhoan.setForeground(new java.awt.Color(49, 139, 130));
        tableTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tên tài khoản", "Tên hiển thị", "Loại tài khoản"
            }
        ));
        tableTaiKhoan.setGridColor(new java.awt.Color(255, 255, 255));
        tableTaiKhoan.setSelectionBackground(new java.awt.Color(183, 221, 217));
        tableTaiKhoan.setSelectionForeground(new java.awt.Color(49, 139, 130));
        tableTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTaiKhoanMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableTaiKhoan);

        pnTaiKhoan.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 118, 635, 710));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        btnMinimize4.setBackground(new java.awt.Color(49, 139, 130));
        btnMinimize4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/subtract_22px.png"))); // NOI18N
        btnMinimize4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimize4MouseClicked(evt);
            }
        });

        lbThoat5.setBackground(new java.awt.Color(49, 139, 130));
        lbThoat5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Close_22px.png"))); // NOI18N
        lbThoat5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThoat5MouseClicked(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Cascadia Code", 1, 48)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(49, 139, 130));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/staff_48px.png"))); // NOI18N
        jLabel40.setText(" QUẢN LÝ TÀI KHOẢN");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 1116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMinimize4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbThoat5)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbThoat5, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(btnMinimize4, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pnTaiKhoan.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, -1, -1));

        jTabbedPane1.addTab("tab7", pnTaiKhoan);

        pnDSBan.setBackground(new java.awt.Color(255, 255, 255));

        tableDSBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã bàn", "Tên bàn", "Trạng thái"
            }
        ));
        tableDSBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDSBanMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tableDSBan);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        btnMinimize9.setBackground(new java.awt.Color(49, 139, 130));
        btnMinimize9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/subtract_22px.png"))); // NOI18N
        btnMinimize9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimize9MouseClicked(evt);
            }
        });

        lbThoat11.setBackground(new java.awt.Color(49, 139, 130));
        lbThoat11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Close_22px.png"))); // NOI18N
        lbThoat11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThoat11MouseClicked(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Cascadia Code", 1, 48)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(49, 139, 130));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/table60px.png"))); // NOI18N
        jLabel34.setText(" QUẢN LÝ BÀN ĂN");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMinimize9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbThoat11)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbThoat11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMinimize9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel46.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(49, 139, 130));
        jLabel1.setText("Mã bàn:");

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(49, 139, 130));
        jLabel46.setText("Tên bàn:");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel75.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(49, 139, 130));
        jLabel75.setText("Trạng thái:");

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel76.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(49, 139, 130));
        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/whatsapp_48px.png"))); // NOI18N

        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(49, 139, 130));
        jLabel77.setText("TCFOOD: 01111222333");

        btnLuuBan.setForeground(new java.awt.Color(49, 139, 130));
        btnLuuBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/save_28px.png"))); // NOI18N
        btnLuuBan.setText("LƯU");
        btnLuuBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuBanActionPerformed(evt);
            }
        });

        btnXoaBan.setForeground(new java.awt.Color(49, 139, 130));
        btnXoaBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Delete_28px.png"))); // NOI18N
        btnXoaBan.setText("XÓA");
        btnXoaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaBanActionPerformed(evt);
            }
        });

        btnThemBan.setForeground(new java.awt.Color(49, 139, 130));
        btnThemBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/add_28px.png"))); // NOI18N
        btnThemBan.setText("THÊM");
        btnThemBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemBanActionPerformed(evt);
            }
        });

        btnSuaBan.setForeground(new java.awt.Color(49, 139, 130));
        btnSuaBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/edit_28px.png"))); // NOI18N
        btnSuaBan.setText("SỬA");
        btnSuaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaBanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addComponent(jLabel75)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46)
                            .addComponent(jLabel1)
                            .addGroup(jPanel46Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel76)))
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel46Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                                    .addComponent(jTextField1)))
                            .addGroup(jPanel46Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(81, 81, 81))
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLuuBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel77))
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel76)))
                .addGap(59, 59, 59)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemBan, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(216, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnDSBanLayout = new javax.swing.GroupLayout(pnDSBan);
        pnDSBan.setLayout(pnDSBanLayout);
        pnDSBanLayout.setHorizontalGroup(
            pnDSBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnDSBanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnDSBanLayout.setVerticalGroup(
            pnDSBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDSBanLayout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnDSBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane10)))
        );

        jTabbedPane1.addTab("tab9", pnDSBan);

        pnKhuyenMai.setBackground(new java.awt.Color(255, 255, 255));

        tableDSKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã khuyến mãi", "Tên khuyến mãi", "Khuyến mãi"
            }
        ));
        tableDSKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDSKhuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(tableDSKhuyenMai);

        jPanel54.setBackground(new java.awt.Color(255, 255, 255));

        btnMinimize11.setBackground(new java.awt.Color(49, 139, 130));
        btnMinimize11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/subtract_22px.png"))); // NOI18N
        btnMinimize11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimize11MouseClicked(evt);
            }
        });

        lbThoat13.setBackground(new java.awt.Color(49, 139, 130));
        lbThoat13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Close_22px.png"))); // NOI18N
        lbThoat13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThoat13MouseClicked(evt);
            }
        });

        jLabel95.setFont(new java.awt.Font("Cascadia Code", 1, 48)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(49, 139, 130));
        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel95.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/sale_50px.png"))); // NOI18N
        jLabel95.setText(" QUẢN LÝ KHUYẾN MÃI");

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel95, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMinimize11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbThoat13)
                .addContainerGap())
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel54Layout.createSequentialGroup()
                        .addGap(0, 18, Short.MAX_VALUE)
                        .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbThoat13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMinimize11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel57.setBackground(new java.awt.Color(255, 255, 255));

        jLabel96.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(49, 139, 130));
        jLabel96.setText("Mã khuyến mãi:");

        jLabel97.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(49, 139, 130));
        jLabel97.setText("Tên khuyến mãi:");

        tfMaKM.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tfMaKM.setEnabled(false);

        tfTenKM.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel98.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(49, 139, 130));
        jLabel98.setText("Giá khuyến mãi:");

        btnLuuKM.setForeground(new java.awt.Color(49, 139, 130));
        btnLuuKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/save_28px.png"))); // NOI18N
        btnLuuKM.setText("LƯU");
        btnLuuKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuKMActionPerformed(evt);
            }
        });

        btnXoaKM.setForeground(new java.awt.Color(49, 139, 130));
        btnXoaKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Delete_28px.png"))); // NOI18N
        btnXoaKM.setText("XÓA");
        btnXoaKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKMActionPerformed(evt);
            }
        });

        btnThemKM.setForeground(new java.awt.Color(49, 139, 130));
        btnThemKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/add_28px.png"))); // NOI18N
        btnThemKM.setText("THÊM");
        btnThemKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKMActionPerformed(evt);
            }
        });

        btnSuaKM.setForeground(new java.awt.Color(49, 139, 130));
        btnSuaKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/edit_28px.png"))); // NOI18N
        btnSuaKM.setText("SỬA");
        btnSuaKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKMActionPerformed(evt);
            }
        });

        tfGiaKM.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel57Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel97)
                    .addComponent(jLabel96)
                    .addComponent(jLabel98))
                .addGap(33, 33, 33)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfGiaKM)
                    .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tfTenKM, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                        .addComponent(tfMaKM)))
                .addGap(60, 60, 60))
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemKM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuaKM, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLuuKM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaKM, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel96)
                    .addComponent(tfMaKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel97)
                    .addComponent(tfTenKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel98)
                    .addComponent(tfGiaKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemKM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaKM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuaKM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuuKM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(213, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnKhuyenMaiLayout = new javax.swing.GroupLayout(pnKhuyenMai);
        pnKhuyenMai.setLayout(pnKhuyenMaiLayout);
        pnKhuyenMaiLayout.setHorizontalGroup(
            pnKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel54, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnKhuyenMaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnKhuyenMaiLayout.setVerticalGroup(
            pnKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnKhuyenMaiLayout.createSequentialGroup()
                .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane13)))
        );

        jTabbedPane1.addTab("tab9", pnKhuyenMai);

        panelGiaoDien.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, 1210, 880));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelGiaoDien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGiaoDien, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
            .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lbThoat5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThoat5MouseClicked
        int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_lbThoat5MouseClicked

    private void btnMinimize4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimize4MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMinimize4MouseClicked

    public void RemoveTK(){
        tfTenTaiKhoan.setText("");
        tfTenHienThi.setText("Nhân viên");
        tfLoaiTK.setText("0");
        tfTenHienThi.setEnabled(false);
        tfLoaiTK.setEnabled(false);
        tableTaiKhoan.clearSelection();
    }
    
    public void DisableTK(){
        tfTenTaiKhoan.setEnabled(false);
        btnLuuTK.setEnabled(false);
        btnXoaTK.setEnabled(false);
        btnSuaTK.setEnabled(false);
    }
    
    public void EnableTK(){
        tfTenHienThi.setEnabled(true);
        btnLuuTK.setEnabled(true);
        btnXoaTK.setEnabled(true);
        btnSuaTK.setEnabled(true);
    }
    
    private void btnThemTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTKActionPerformed
        EnableTK();
        RemoveTK();
        tfTenTaiKhoan.setEnabled(true);
        btnXoaTK.setEnabled(false);
        btnSuaTK.setEnabled(false);
    }//GEN-LAST:event_btnThemTKActionPerformed

    private void lbThoat1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThoat1MouseClicked
        int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_lbThoat1MouseClicked

    private void btnMinimize2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimize2MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMinimize2MouseClicked

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        ChonAnhNV();
    }//GEN-LAST:event_btnOpenActionPerformed

    public void DisableMA(){
        tfMaMon.setEnabled(false);
        tfTenMonAn.setEnabled(false);
        cbLoaiMon.setEnabled(false);
        tfTenLoai.setEnabled(false);
        tfDonViTinh.setEnabled(false);
        tfGiaTienMon.setEnabled(false);
        tfSoLuongMon.setEnabled(false);
        btnLuuNV.setEnabled(false);
        btnXoaDM.setEnabled(false);
        btnSuaDM.setEnabled(false);
    }
    
    public void EnableMA(){
        tfTenMonAn.setEnabled(true);
        cbLoaiMon.setEnabled(true);
        tfDonViTinh.setEnabled(true);
        tfGiaTienMon.setEnabled(true);
        tfSoLuongMon.setEnabled(true);
        btnThemDM.setEnabled(true);
    }
    
     private void loadLoaiMonAn() {
        String sql = " SELECT * FROM DanhMucMon WHERE maLoai = maLoai";
        try{
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                this.cbLoaiMon.addItem(rs.getString("maLoai"));
            }
            } catch(Exception e){

            }
      }
     
    public void RemoveMA(){
        tfMaMonAn.setText("");
        tfTenMonAn.setText("");
        tfTenLoai.setText("");
        tfDonViTinh.setText("");
        tfGiaTienMon.setText("");
        tfSoLuongMon.setText("");
        ImageIcon icon = new ImageIcon((getClass().getResource("/IMAGES/no-image.png")));
        Image img = icon.getImage().getScaledInstance(lbImageFood.getWidth(), lbImageFood.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaIcon = new ImageIcon(img);
        lbImageFood.setIcon(scaIcon);
        tableFood.clearSelection();
    }
    
    private void btnThemMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMonActionPerformed
        EnableMA();
        RemoveMA();
        cbLoaiMon.removeAllItems();
            loadLoaiMonAn();
        btnLuuMon.setEnabled(true);
        btnXoaMon.setEnabled(false);
        btnSuaMon.setEnabled(false);
        //lbImageFood.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/no-image.png")));
    }//GEN-LAST:event_btnThemMonActionPerformed

    private void lbThoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThoatMouseClicked
        int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_lbThoatMouseClicked

    private void btnMinimize1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimize1MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMinimize1MouseClicked

    public void RemoveDM(){
        tfMaDM.setText("");
        tfTenDM.setText("");
        ImageIcon icon = new ImageIcon((getClass().getResource("/IMAGES/no-image.png")));
        Image img = icon.getImage().getScaledInstance(lbImageDM.getWidth(), lbImageDM.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaIcon = new ImageIcon(img);
        lbImageDM.setIcon(scaIcon);
        tableDanhMuc.clearSelection();
    }
    
    public void DisableDM(){
        tfMaDM.setEnabled(false);
        tfTenDM.setEnabled(false);
        btnLuuNV.setEnabled(false);
        btnXoaDM.setEnabled(false);
        btnSuaDM.setEnabled(false);
    }
    
    public void EnableDM(){
        tfTenDM.setEnabled(true);
        btnThemDM.setEnabled(true);
        btnLuuDM.setEnabled(true);
        btnXoaDM.setEnabled(true);
        btnSuaDM.setEnabled(true);
    }
    
    private void btnThemDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDMActionPerformed
        EnableDM();
        RemoveDM();
        btnXoaDM.setEnabled(false);
        btnSuaDM.setEnabled(false);
    }//GEN-LAST:event_btnThemDMActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất khỏi tài khoản hay không?", "Thông Báo",2);
        if(Click ==JOptionPane.YES_OPTION){
            this.setVisible(false);
            DangNhap dangnhap = new DangNhap();
            dangnhap.setVisible(true);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void lbThoat4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThoat4MouseClicked
        int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_lbThoat4MouseClicked

    private void btnMinimize3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimize3MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMinimize3MouseClicked

    private void lbImageNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbImageNVMouseClicked
        ChonAnhNV();
    }//GEN-LAST:event_lbImageNVMouseClicked

    public void RemoveNV(){
        tfMaNV.setText("");
        tfHoten.setText("");
        tfSDT.setText("");
        tfDiachi.setText("");
        tfChucVu.setText("Nhân viên");
        tfTaiKhoan.setText("");
        ImageIcon icon = new ImageIcon((getClass().getResource("/IMAGES/no-image.png")));
        Image img = icon.getImage().getScaledInstance(lbImageNV.getWidth(), lbImageNV.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaIcon = new ImageIcon(img);
        lbImageNV.setIcon(scaIcon);
        tableNV.clearSelection();
    }
    
    private void btnThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNVActionPerformed
        EnableNV();
        RemoveNV();
        btnXoaDM.setEnabled(false);
        btnSuaDM.setEnabled(false);
    }//GEN-LAST:event_btnThemNVActionPerformed

    private boolean kiemtraNullNV(){
        if(tfHoten.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập họ tên nhân viên!");
            return false;
        }
        else
        if(rdoNam.isSelected()==false && rdoNu.isSelected()==false){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa chọn giới tính!");
            return false;
        }
        else
        if(((JTextField)tfNgaySinh.getDateEditor().getUiComponent()).getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập ngày sinh!");
            return false;
        }
        else   
        if(tfSDT.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập số điện thoại!");
            return false;
        }
        else   
        if(tfDiachi.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập địa chỉ!");
            return false;
        }
        else
        if(tfChucVu.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập chức vụ!");
            return false;
        }else
        if(tfTaiKhoan.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập tên tài khoản!");
            return false;
        }else{
            return true;
        }
    }
    
    private void btnLuuNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuNVActionPerformed
            if(kiemtraNullNV()) {                        
            nhanVien.setTenNV(tfHoten.getText());
            nhanVien.setNgaySinh(new java.sql.Date(tfNgaySinh.getDate().getTime()).toString());      
            String gioiTinh ="";
            if (rdoNam.isSelected()) {
                gioiTinh += "Nam";
            }
            if (rdoNu.isSelected()) {
                gioiTinh += "Nữ";
            }
            nhanVien.setGioiTinh(gioiTinh);
            nhanVien.setSoDT(tfSDT.getText()); 
            nhanVien.setDiaChi(tfDiachi.getText()); 
            nhanVien.setChucVu(tfChucVu.getText());
            nhanVien.setTenDangNhap(tfTaiKhoan.getText());
            nhanVien.setImageNV(tfLinkNV.getText());
        
            nhanVienSERVICE.themNhanVien(nhanVien);
            JOptionPane.showMessageDialog(null, "Đã thêm nhân viên mới thành công!^^");
            RefeshNV();
        }
    }//GEN-LAST:event_btnLuuNVActionPerformed

    private void lbThoat3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThoat3MouseClicked
        int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_lbThoat3MouseClicked

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        //ChonAnhMonAn();
        //        JFileChooser chooser = new JFileChooser("E:\\IT\\JavaSwing\\DoAnTCFood\\src\\IMAGES");
        //            chooser.showOpenDialog(null);
        //            File f = chooser.getSelectedFile();
        //            filename = f.getAbsolutePath();
        //            ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lbImage.getWidth(),lbImage.getHeight(),Image.SCALE_SMOOTH));
        //            lbImage.setIcon(imageIcon);
        //            try
        //            {
            //                File imageFile = new File(filename);
            //                FileInputStream fileInputStream = new  FileInputStream(imageFile);
            //                ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //                byte[] buf = new byte[1024];
            //                for(int readNum; (readNum=fileInputStream.read(buf)) !=-1;)
            //                {
                //                    bos.write(buf,0,readNum);
                //                }
            //                image = bos.toByteArray();
            //            }catch(Exception ex){
            //                JOptionPane.showMessageDialog( null, ex);
            //            }
    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnUploadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUploadMouseClicked
//        JFileChooser open=new JFileChooser();
//        open.setDialogTitle("Chọn ảnh");
//        int file=open.showOpenDialog(null);
//        if(file==JFileChooser.APPROVE_OPTION){
//            tfLinkNV.setText(open.getCurrentDirectory().toString()+"\\"+open.getSelectedFile().getName());
//                ImageIcon icon = new ImageIcon(tfLinkNV.getText());
//                lbImageFood.setIcon(icon);
//            }
        ChonAnhNV();
    }//GEN-LAST:event_btnUploadMouseClicked

    private void tfSDTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSDTKeyReleased

    }//GEN-LAST:event_tfSDTKeyReleased

    private void tableNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableNVMouseClicked
        EnableNV();
        int row = tableNV.getSelectedRow();
        TableModel model = tableNV.getModel();
        tfMaNV.setText(model.getValueAt(row ,0).toString());
        tfHoten.setText(model.getValueAt(row ,1).toString());
        ((JTextField)tfNgaySinh.getDateEditor().getUiComponent()).setText(model.getValueAt(row, 2).toString());

        /*Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String)model.getValueAt(row, 2));
        tfNgaySinh.setDate(date);*/
        String gioiTinh = model.getValueAt(row,3).toString();
        if(gioiTinh.equals("Nữ")){
            rdoNu.setSelected(true);
        }else{
            rdoNam.setSelected(true);
        }
        tfSDT.setText(model.getValueAt(row ,4).toString());
        tfDiachi.setText(model.getValueAt(row ,5).toString());
        tfChucVu.setText(model.getValueAt(row ,6).toString());
        tfTaiKhoan.setText(model.getValueAt(row ,7).toString());
        String imageNV = model.getValueAt(row, 8).toString();
        tfLinkNV.setText(model.getValueAt(row, 8).toString());
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/IMAGES/NhanVien/"+imageNV));
        Image image = imageIcon.getImage().getScaledInstance(lbImageNV.getWidth(), lbImageNV.getHeight(), Image.SCALE_SMOOTH);
        lbImageNV.setIcon(new ImageIcon(image));
        //            byte[] img = (nhanVienSERVICE.getAllNhanVien().get(row).getImageNV());
        //            ImageIcon imgIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(lbImage.getWidth(), lbImage.getHeight(), Image.SCALE_SMOOTH));
        //           lbImage.setIcon(imgIcon);
    }//GEN-LAST:event_tableNVMouseClicked

    private void btnMinimize7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimize7MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMinimize7MouseClicked

    private void lbThoat7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThoat7MouseClicked
        int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_lbThoat7MouseClicked

    private void btnCaiDatMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaiDatMKActionPerformed
          String a = tfTenTaiKhoan.getText();
          String b = tfTenHienThi.getText();
          CaiDatLaiMK mK=new CaiDatLaiMK();
          mK.tfTenDangNhap.setText(a);
          mK.tfTenHienThi.setText(b);
          mK.setVisible(true);
    }//GEN-LAST:event_btnCaiDatMKActionPerformed

    private void btnXoaMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaMonActionPerformed
        int row = tableFood.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(QuanLy.this, "Vui lòng chọn bàn cần xóa","Thông báo",JOptionPane.ERROR_MESSAGE);
        }else{
            int xacNhan = JOptionPane.showConfirmDialog(QuanLy.this, "Bạn có chắc chắn muốn xóa không?","Thông báo",JOptionPane.YES_NO_OPTION);
            if(xacNhan == JOptionPane.YES_OPTION){
                String maMonAn = String.valueOf(tableFood.getValueAt(row, 0));
                thucDonSERVICE.xoaThucDon(maMonAn);
            }
        }
        RefeshMA();
    }//GEN-LAST:event_btnXoaMonActionPerformed

    private void cbLoaiMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLoaiMonActionPerformed
        String sql = "SELECT tenLoai FROM DanhMucMon WHERE maLoai = ?";
        try{
            String maTheLoaiString = (String) cbLoaiMon.getSelectedItem();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maTheLoaiString);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                tfTenLoai.setText(rs.getString("tenLoai"));
            }
        } catch(Exception e){

        }
    }//GEN-LAST:event_cbLoaiMonActionPerformed

    private void tfTenLoaiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTenLoaiKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTenLoaiKeyReleased

    private void tableFoodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableFoodMouseClicked
        EnableMA();
        cbLoaiMon.removeAllItems();
        int click=tableFood.getSelectedRow();
        TableModel model=tableFood.getModel();

        tfMaMonAn.setText(model.getValueAt(click, 0).toString());
        tfTenMonAn.setText(model.getValueAt(click, 1).toString());
        cbLoaiMon.addItem(model.getValueAt(click, 2).toString());
        tfDonViTinh.setText(model.getValueAt(click, 3).toString());
        String []s1=model.getValueAt(click,4).toString().split("\\s");
        tfGiaTienMon.setText(s1[0]);
        tfSoLuongMon.setText(model.getValueAt(click, 5).toString());
        String imageFood = model.getValueAt(click, 6).toString();
        tfLinkMonAn.setText(model.getValueAt(click, 6).toString());
        
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/AnhMonAn/"+imageFood));
        Image image = imageIcon.getImage().getScaledInstance(lbImageFood.getWidth(), lbImageFood.getHeight(), Image.SCALE_SMOOTH);
        lbImageFood.setIcon(new ImageIcon(image)); 
        
        btnLuuMon.setEnabled(false);
        btnSuaMon.setEnabled(true);
        btnXoaMon.setEnabled(true);
        tfMaMonAn.setEnabled(false);
    }//GEN-LAST:event_tableFoodMouseClicked

    private void tableTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTaiKhoanMouseClicked
         int row = tableTaiKhoan.getSelectedRow();
        TableModel model = tableTaiKhoan.getModel();
        tfTenTaiKhoan.setText(model.getValueAt(row ,0).toString());
        tfTenHienThi.setText(model.getValueAt(row ,1).toString());
        tfLoaiTK.setText(model.getValueAt(row, 2).toString());
        tfTenTaiKhoan.setEnabled(false);
        EnableTK();
    }//GEN-LAST:event_tableTaiKhoanMouseClicked

    private boolean kiemtraNullMonAn(){
        if(tfTenMonAn.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập tên món ăn!");
            return false;
        }
        else
        if(tfDonViTinh.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập đơn vị tính!");
            return false;
        }
        else   
        if(tfSoLuongMon.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập số lượng món ăn!");
            return false;
        }
        else
        if(cbLoaiMon.getSelectedItem().equals("")){
           JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa chọn loại món ăn!");
            return false;
        }
        else{ 
        if(tfGiaTienMon.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập giá tiền!");
            return false;
        }
        else{ 
            return true;
        }
        }
    }
    
    private void btnLuuMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuMonActionPerformed
       if(kiemtraNullMonAn()){                 
        thucDon.setTenMonAn(tfTenMonAn.getText());              
        thucDon.setMaLoaiMon(Integer.parseInt(cbLoaiMon.getSelectedItem().toString()));              
        thucDon.setDonVi(tfDonViTinh.getText());         
        thucDon.setGiaTien(Float.valueOf(tfGiaTienMon.getText()));                 
        thucDon.setSoLuong(Integer.parseInt(tfSoLuongMon.getText()));   
        thucDon.setImageMonAn(tfLinkMonAn.getText()); 
        
        thucDonSERVICE.themThucDon(thucDon);
            JOptionPane.showMessageDialog(null, "Đã thêm món ăn mới thành công!^^");
        RefeshMA();
        hienThiTatCa();
        hienThiDoAn();
        hienThiNuocUong();
       }
    }//GEN-LAST:event_btnLuuMonActionPerformed

    private void btnSuaMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaMonActionPerformed
        btnLuuNV.setEnabled(true);
        tfMaMon.setEnabled(false);
        
        int row = tableFood.getSelectedRow();   
        if(row == -1){
            JOptionPane.showMessageDialog(QuanLy.this, "Vui lòng chọn món cần sửa","Thông báo",JOptionPane.ERROR_MESSAGE);
        }else{
            int xacNhan = JOptionPane.showConfirmDialog(QuanLy.this, "Bạn có chắc chắn muốn sửa không?","Thông báo",JOptionPane.YES_NO_OPTION);
            if(xacNhan == JOptionPane.YES_OPTION){
                
            thucDon.setMaMonAn(Integer.parseInt(tfMaMonAn.getText()));
            thucDon.setTenMonAn(tfTenMonAn.getText());
            thucDon.setMaLoaiMon(Integer.parseInt(cbLoaiMon.getItemAt(cbLoaiMon.getSelectedIndex())));
            thucDon.setDonVi(tfDonViTinh.getText());   
            thucDon.setGiaTien(Float.valueOf(tfGiaTienMon.getText()));       
            thucDon.setSoLuong(Integer.parseInt(tfSoLuongMon.getText()));   
            thucDon.setImageMonAn(tfLinkMonAn.getText()); 
            
               // String maBan = String.valueOf(tableNV.getValueAt(row, 0));
                thucDonSERVICE.suaThucDon(thucDon);
            JOptionPane.showMessageDialog(null, "Đã sửa thông tin món ăn thành công!^^");
                RefeshMA();
            }
        }
    }//GEN-LAST:event_btnSuaMonActionPerformed

    private void btnSuaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaNVActionPerformed
        tfMaNV.setEnabled(false);
        int row = tableNV.getSelectedRow();   
        if(row == -1){ // Không có hàng nào được chọn
            JOptionPane.showMessageDialog(QuanLy.this, "Vui lòng chọn bàn cần sửa","Thông báo",JOptionPane.ERROR_MESSAGE);
        }else{
            int xacNhan = JOptionPane.showConfirmDialog(QuanLy.this, "Bạn có chắc chắn muốn sửa không?","Thông báo",JOptionPane.YES_NO_OPTION);
            if(xacNhan == JOptionPane.YES_OPTION){
                
            nhanVien.setMaNV(Integer.parseInt(tfMaNV.getText()));
            nhanVien.setTenNV(tfHoten.getText());
            nhanVien.setNgaySinh(new java.sql.Date(tfNgaySinh.getDate().getTime()).toString());
            String gioiTinh;
            if(rdoNam.isSelected()){
                gioiTinh = "Nam";
            }else
                gioiTinh = "Nữ";
            nhanVien.setGioiTinh(gioiTinh);
            nhanVien.setSoDT(tfSDT.getText());   
            nhanVien.setDiaChi(tfDiachi.getText());           
            nhanVien.setChucVu(tfChucVu.getText());   
            nhanVien.setTenDangNhap(tfTaiKhoan.getText());
            nhanVien.setImageNV(tfLinkNV.getText());
           
            nhanVienSERVICE.suaNhanVien(nhanVien);
            JOptionPane.showMessageDialog(null, "Đã sửa thông tin nhân viên thành công!^^");
                RefeshNV();
            }
        }
    }//GEN-LAST:event_btnSuaNVActionPerformed

    private void btnXoaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNVActionPerformed
        int row = tableNV.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(QuanLy.this, "Vui lòng chọn bàn cần xóa","Thông báo",JOptionPane.ERROR_MESSAGE);
        }else{
            int xacNhan = JOptionPane.showConfirmDialog(QuanLy.this, "Bạn có chắc chắn muốn xóa không?","Thông báo",JOptionPane.YES_NO_OPTION);
            if(xacNhan == JOptionPane.YES_OPTION){
                String maNV = String.valueOf(tableNV.getValueAt(row, 0));
                nhanVienSERVICE.xoaNhanVien(maNV);
            }
        }
        RefeshNV();
    }//GEN-LAST:event_btnXoaNVActionPerformed

    private void lbImageFoodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbImageFoodMouseClicked
            ChonAnhMA();
    }//GEN-LAST:event_lbImageFoodMouseClicked
    
    private void timkiemMonAn(String query){
           TableRowSorter<DefaultTableModel> tbl = new TableRowSorter<DefaultTableModel>(modelThucDon);
           tableFood.setRowSorter(tbl);
           tbl.setRowFilter(RowFilter.regexFilter(query));
    }
           
    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        String query = tfTimKiem.getText();
        timkiemMonAn(query);
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnSuaDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaDMActionPerformed
        tfMaDM.setEnabled(false);
        
        int row = tableDanhMuc.getSelectedRow();   
        if(row == -1){
            JOptionPane.showMessageDialog(QuanLy.this, "Vui lòng chọn bàn cần sửa","Thông báo",JOptionPane.ERROR_MESSAGE);
        }else{
            int xacNhan = JOptionPane.showConfirmDialog(QuanLy.this, "Bạn có chắc chắn muốn sửa không?","Thông báo",JOptionPane.YES_NO_OPTION);
            if(xacNhan == JOptionPane.YES_OPTION){
                
            danhMuc.setMaLoai(Integer.parseInt(tfMaDM.getText()));
            danhMuc.setTenLoai(tfTenDM.getText());
            danhMuc.setImageDM(tfLinkDM.getText()); 
            
               // String maBan = String.valueOf(tableNV.getValueAt(row, 0));
                danhMucSERVICE.suaDanhMuc(danhMuc);
                
            JOptionPane.showMessageDialog(null, "Đã sửa thông tin món ăn thành công!^^");
                RefeshDM();
            }
        }
    }//GEN-LAST:event_btnSuaDMActionPerformed

    private void tableDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDanhMucMouseClicked
        EnableDM();
        int click=tableDanhMuc.getSelectedRow();
        TableModel model=tableDanhMuc.getModel();

        tfMaDM.setText(model.getValueAt(click, 0).toString());
        tfTenDM.setText(model.getValueAt(click, 1).toString());
        String imageFood = model.getValueAt(click, 2).toString();
        tfLinkDM.setText(model.getValueAt(click, 2).toString());
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/AnhMonAn/"+imageFood));
        Image image = imageIcon.getImage().getScaledInstance(lbImageDM.getWidth(), lbImageDM.getHeight(), Image.SCALE_SMOOTH);
        lbImageDM.setIcon(new ImageIcon(image)); 
//        }
        
        tfMaDM.setEnabled(false);
    }//GEN-LAST:event_tableDanhMucMouseClicked

    private void btnLuuDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuDMActionPerformed
        if(kiemtraNullDM()){                           
        danhMuc.setTenLoai(tfTenDM.getText());       
        danhMuc.setImageDM(tfLinkDM.getText()); 
        
        danhMucSERVICE.themDanhMuc(danhMuc);
            JOptionPane.showMessageDialog(null, "Đã thêm món ăn mới thành công!^^");
        RefeshDM();
        }
    }//GEN-LAST:event_btnLuuDMActionPerformed

    private boolean kiemtraNullDM(){
        if(tfTenDM.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập tên loại món ăn");
            return false;
        }
        else{ 
            return true;
        }
     }
    
    private void btnXoaDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDMActionPerformed
        int row = tableDanhMuc.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(QuanLy.this, "Vui lòng chọn bàn cần xóa","Thông báo",JOptionPane.ERROR_MESSAGE);
        }else{
            int xacNhan = JOptionPane.showConfirmDialog(QuanLy.this, "Bạn có chắc chắn muốn xóa không?","Thông báo",JOptionPane.YES_NO_OPTION);
            if(xacNhan == JOptionPane.YES_OPTION){
                String maDanhMuc = String.valueOf(tableDanhMuc.getValueAt(row, 0));
                danhMucSERVICE.xoaDanhMuc(maDanhMuc);
            }
        }
        RefeshDM();
    }//GEN-LAST:event_btnXoaDMActionPerformed

    private void lbThoat8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThoat8MouseClicked
         int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_lbThoat8MouseClicked

    private void btnMinimize6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimize6MouseClicked
            this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMinimize6MouseClicked

    private void congLaiSoLuong(int maMon, int soLuong){
        try{
            String sql="UPDATE MonAn SET soLuong=soLuong + ? WHERE maMon=?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1, soLuong);
            ps.setInt(2, maMon);
            ps.executeUpdate(); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
         try{
            int selectedRowHD=tableLSHD.getSelectedRow();
            int selectedrowCTHD=tableCTHD.getSelectedRow();
            int maHD = Integer.parseInt(tableLSHD.getValueAt(selectedRowHD, 0).toString());
            int maMonAn = LayMaMonAnTheoTen(cboTenMonAn.getSelectedItem().toString());
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Chưa chọn dữ liệu để xóa");
            return;
        }  
         
         if(tfGiaBanCTHD.getText().equals("") || tfThanhTienCTHD.getText().equals("") || tfSoLuongCTHD.getText().equals("") || cboTenMonAn.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa chọn món cần xóa!!!");
         }
        else{
        int selectedRowHD=tableLSHD.getSelectedRow();
        int selectedrowCTHD=tableCTHD.getSelectedRow();
        int soLuongBan = Integer.parseInt(tfSoLuongCTHD.getText());
        int maHD=Integer.parseInt(tableLSHD.getValueAt(selectedRowHD, 0).toString());
        int maMonAn=LayMaMonAnTheoTen(cboTenMonAn.getSelectedItem().toString());
        String tenMonAn = cboTenMonAn.getSelectedItem().toString();

        if(selectedRowHD!=-1 &&selectedrowCTHD!=-1){

            int result= JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn xóa món "+tenMonAn+" này không?",
                "Thông báo", JOptionPane.YES_NO_OPTION);
            if(result==JOptionPane.YES_OPTION){

                xoaCTHD(maHD, maMonAn);

                float tongtiensauKhiXoa=layTongTienSauKhiXoaHoaDon(maHD);
                capNhatTongTienHoaDonSauKhiXoaCTHD(maHD, tongtiensauKhiXoa);

                hienThiTableHoaDon();
                hienThitableCTHDTheoMaHD(maHD);
                dongTextBox();
                JOptionPane.showMessageDialog(rootPane, "Xóa thành công!!"); 
                congLaiSoLuong(maMonAn,soLuongBan);
//                for (int i = 0; i < rowCount; i++) {
//                int soLuongBan = Integer.parseInt(tableCTHD.getValueAt(i, 2).toString());
//                congLaiSoLuong(maMonAn,soLuongBan);}
                return;
            }
        }
        else if(selectedRowHD!=-1){
            int result=JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn xóa hóa đơn "+maHD+" không?"
                , "Xóa Hóa Đơn"
                , JOptionPane.YES_NO_OPTION);
            if(result==JOptionPane.YES_OPTION){
                //xoaHDTheoMaHD(maHD);
                hienThiTableHoaDon();
                dongTextBox();

                return;
            }
        }
         }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void truSoLuongSauKhiCN(String tenMon, int soLuong){
        try{
            String sql="UPDATE MonAn SET soLuong=soLuong - ? WHERE tenMon=?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1, soLuong);
            ps.setString(2, tenMon);
            ps.executeUpdate(); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        try{
            int selectedRowHD=tableLSHD.getSelectedRow();
            int selectedrowCTHD=tableCTHD.getSelectedRow();
            int maHD=Integer.parseInt(tableLSHD.getValueAt(selectedRowHD, 0).toString());
            int maLK=LayMaMonAnTheoTen(cboTenMonAn.getSelectedItem().toString());
            int maNV=LayMaNhanVienTheoTen(cboTenNV.getSelectedItem().toString());
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn dữ liệu và thay đổi các giá trị ở textbox"
                + "hoặc combobox để lưu !");
            return;
        }

        int selectedRowHD=tableLSHD.getSelectedRow();
        int selectedrowCTHD=tableCTHD.getSelectedRow();
        int maHD=Integer.parseInt(tableLSHD.getValueAt(selectedRowHD, 0).toString());
        int maLK=LayMaMonAnTheoTen(cboTenMonAn.getSelectedItem().toString());
        int maNV=LayMaNhanVienTheoTen(cboTenNV.getSelectedItem().toString());

        if(selectedRowHD != -1 && selectedrowCTHD != -1){
            try {
                int soLuong=Integer.parseInt(tfSoLuongCTHD.getText());
                if(soLuong<0){
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
                    return;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số");
                return;
            }

            try {
                float giaBan=Float.parseFloat(tfGiaBanCTHD.getText());
                if(giaBan<0){
                    JOptionPane.showMessageDialog(this, "Giá bán phải lớn hơn 0");
                    return;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Giá bán phải là số");
                return;
            }
            int soLuong=Integer.parseInt(tfSoLuongCTHD.getText());
            Float giaBan=Float.parseFloat(tfGiaBanCTHD.getText());
            float thanhTien=soLuong*giaBan;

                String tenMonAn = tableCTHD.getValueAt(selectedrowCTHD, 0).toString();
                int soLuongBan = Integer.parseInt(tableCTHD.getValueAt(selectedrowCTHD, 2).toString());
            capNhatCTHD(maHD, maLK, giaBan, soLuong, thanhTien);
            Float tongtien=layTongTienSauKhiXoaHoaDon(maHD);
            capNhatTongTienHoaDonSauKhiXoaCTHD(maHD, tongtien);
            hienThiTableHoaDon();
            hienThitableCTHDTheoMaHD(maHD);
            dongTextBoxCTHD();
            //truSoLuongSauKhiCN(tenMonAn, soLuongBan);
            tfTongTienHD.setText("");
            JOptionPane.showMessageDialog(this, "Cập nhật số lượng cho hóa đơn "+maHD+" thành công");
            return;
        }
        else{
            hienThiTableHoaDon();
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món cần thay đổi!!!");
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void tableLSHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableLSHDMouseClicked
        dongTextBoxCTHD();
        int row=tableLSHD.getSelectedRow();
        int maHD=Integer.parseInt(tableLSHD.getValueAt(row, 0).toString());
        String tenNV=tableLSHD.getValueAt(row, 1).toString();
        float tongTien=Float.parseFloat(tableLSHD.getValueAt(row, 3).toString());
        Locale VN = new Locale("vi", "VN");
        Currency vnd = Currency.getInstance(VN);
        NumberFormat VNDFormat = NumberFormat.getCurrencyInstance(VN);
        tfTongTienHD.setText(VNDFormat.format(tongTien));
        tfMaHD.setText(tableLSHD.getValueAt(row, 0).toString());
        cboTenNV.getModel().setSelectedItem(tenNV);
        hienThitableCTHDTheoMaHD(maHD);
    }//GEN-LAST:event_tableLSHDMouseClicked

    private void tableCTHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCTHDMouseClicked
        int row=tableCTHD.getSelectedRow();
        cboTenMonAn.getModel().setSelectedItem(tableCTHD.getValueAt(row, 0));
        tfGiaBanCTHD.setText(tableCTHD.getValueAt(row, 1).toString());
        tfSoLuongCTHD.setText(tableCTHD.getValueAt(row, 2).toString());
        float thanhtien=Float.parseFloat(tableCTHD.getValueAt(row, 3).toString());
        Locale VN = new Locale("vi", "VN");
        Currency vnd = Currency.getInstance(VN);
        NumberFormat VNDFormat = NumberFormat.getCurrencyInstance(VN);
        tfThanhTienCTHD.setText(VNDFormat.format(thanhtien));
    }//GEN-LAST:event_tableCTHDMouseClicked

    private void tfGiaBanCTHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfGiaBanCTHDMouseClicked
        // TODO add your handling code here:
        tfThanhTienCTHD.setText("");
    }//GEN-LAST:event_tfGiaBanCTHDMouseClicked

    private void tfSoLuongCTHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfSoLuongCTHDMouseClicked
        // TODO add your handling code here:
        tfThanhTienCTHD.setText("");
    }//GEN-LAST:event_tfSoLuongCTHDMouseClicked

    private void btnMinimize8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimize8MouseClicked
         this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMinimize8MouseClicked

    private void lbThoat10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThoat10MouseClicked
         int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_lbThoat10MouseClicked

    private void cboTenNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenNVActionPerformed
       String sql = "SELECT tenNV FROM NhanVien WHERE maNV = ?";
        try{
            String maNV = (String) cboTenNV.getSelectedItem();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maNV);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                tfTenNV.setText(rs.getString("tenNV"));
            }
        } catch(Exception e){

        }
    }//GEN-LAST:event_cboTenNVActionPerformed

    private void lbImageDMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbImageDMMouseClicked
        ChonAnhDM();
    }//GEN-LAST:event_lbImageDMMouseClicked

    private int LayDoanhThuMonAn(String tenMon) {
       int dem = 0;
        try {
            modelDoanhThuMon.setRowCount(0);
            String sql = "SELECT maHD, tenMon, ct.soLuong, ngayXuatHoaDon,tongtien FROM HoaDonTT, MonAn, ChiTietHD ct WHERE maMon = maMonAn and mahd = maHoaDon and tenMon = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, tenMon);
            rs = ps.executeQuery();
            while (rs.next()) {
                Vector<Object> vec=new Vector<>();
                vec.add(rs.getInt("maHD"));
                vec.add(rs.getString("tenMon"));
                vec.add(rs.getInt("soLuong"));
                vec.add(rs.getFloat("tongTien"));
                vec.add(rs.getDate("ngayXuatHoaDon"));
                modelDoanhThuMon.addRow(vec);
                dem++;
            }
            lbTongHDMon.setText(String.valueOf(dem));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
    
    private float tinhTongDoanhThuMon() {
        float tongtienHD = 0;
        int row = tableThongkeMon.getRowCount();
        for (int i = 0; i < row; i++) {
            float tongtien = Float.parseFloat(tableThongkeMon.getValueAt(i, 3).toString());
            tongtienHD += tongtien;
        }
        return tongtienHD;
    }
    
    private int LayNVDoanhThu(String tenNV) {
       int dem = 0;
        try {
            modelDoanhthuNV.setRowCount(0);
            String sql = "  SELECT maHD, tenNV, ngayXuatHoaDon,tongTien FROM HoaDonTT, NhanVien WHERE maNhanVien = maNV and tenNV = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, tenNV);
            rs = ps.executeQuery();
            while (rs.next()) {
                Vector<Object> vec=new Vector<>();
                vec.add(rs.getInt("maHD"));
                vec.add(rs.getString("tenNV"));
                vec.add(rs.getFloat("tongTien"));
                vec.add(rs.getDate("ngayXuatHoaDon"));
                modelDoanhthuNV.addRow(vec);
                dem++;
            }
            lbTongHDNV.setText(String.valueOf(dem));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
    
    private float tinhTongDoanhNV() {
        float tongtienHD = 0;
        int row = tableThongkeNV.getRowCount();
        for (int i = 0; i < row; i++) {
            float tongtien = Float.parseFloat(tableThongkeNV.getValueAt(i, 2).toString());
            tongtienHD += tongtien;
        }
        return tongtienHD;
    }
    
    private void btnTraCuuNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraCuuNVActionPerformed
       String tenNV = String.valueOf(cbNVDoanhThu.getSelectedItem());
        LayNVDoanhThu(tenNV);
        
                float tongtienHD = tinhTongDoanhNV();
                Locale VN = new Locale("vi", "VN");
                Currency dollars = Currency.getInstance(VN);
                NumberFormat VNDFormat = NumberFormat.getCurrencyInstance(VN);
                lbTongDoanhThuNV.setText(VNDFormat.format(tongtienHD));
                return;
    }//GEN-LAST:event_btnTraCuuNVActionPerformed

    private void btnXoaTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTKActionPerformed
        int row = tableTaiKhoan.getSelectedRow();
        if(tfTenTaiKhoan.getText().equals("Admin")){
            JOptionPane.showMessageDialog(pnTaiKhoan, "Không thể xóa tài khoản Admin");
        }else{
            if(row == -1){
            JOptionPane.showMessageDialog(QuanLy.this, "Vui lòng chọn tài khoản cần xóa","Thông báo",JOptionPane.ERROR_MESSAGE);
            }else{
            int xacNhan = JOptionPane.showConfirmDialog(QuanLy.this, "Bạn có chắc chắn muốn xóa không?","Thông báo",JOptionPane.YES_NO_OPTION);
            if(xacNhan == JOptionPane.YES_OPTION){
                String tenTaiKhoan = String.valueOf(tableTaiKhoan.getValueAt(row, 0));
                taiKhoanSERVICE.xoaTaiKhoan(tenTaiKhoan);
            }
        }
        }
        RefeshTK();
    }//GEN-LAST:event_btnXoaTKActionPerformed

    private boolean kiemtraNullTK(){
        if(tfTenTaiKhoan.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập tên đăng nhập");
            return false;
        }
        else{    
            if(tfTenHienThi.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập tên hiển thị tài khoản");
            return false;
        }   if(tfLoaiTK.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Loại tài khoản không được để trống");
            return false;
        }
            return true;
        }
     }
    
    private void btnLuuTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuTKActionPerformed
            if(kiemtraNullTK()) {                        
            taiKhoan.setTenDangNhap(tfTenTaiKhoan.getText());                
            taiKhoan.setTenHienThi(tfTenHienThi.getText());       
            taiKhoan.setLoaiTaiKhoan(Integer.parseInt(tfLoaiTK.getText()));
            taiKhoan.setMatKhau("0");
        
            taiKhoanSERVICE.themTaiKhoan(taiKhoan);
            JOptionPane.showMessageDialog(null, "Đã thêm tài khoản mới thành công!^^");
        }
        RefeshTK();
    }//GEN-LAST:event_btnLuuTKActionPerformed

    private void btnSuaTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaTKActionPerformed
        
        tfTenTaiKhoan.setEnabled(false);
        int row = tableTaiKhoan.getSelectedRow();   
        if(row == -1){ // Không có hàng nào được chọn
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần sửa","Thông báo",JOptionPane.ERROR_MESSAGE);
        }else{
            int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa không?","Thông báo",JOptionPane.YES_NO_OPTION);
            if(xacNhan == JOptionPane.YES_OPTION){
                
            taiKhoan.setTenHienThi(tfTenHienThi.getText());
            taiKhoan.setTenDangNhap(tfTenTaiKhoan.getText());
           
            taiKhoanSERVICE.suaTaiKhoan(taiKhoan);
            JOptionPane.showMessageDialog(null, "Đã sửa thông tin tài khoản thành công!^^");
                RefeshTK();
            }
        }
    }//GEN-LAST:event_btnSuaTKActionPerformed

    private void btnMinimize9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimize9MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMinimize9MouseClicked

    private void lbThoat11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThoat11MouseClicked
        int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_lbThoat11MouseClicked

    private void btnThemBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemBanActionPerformed

    private void tableDSBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDSBanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableDSBanMouseClicked

    private void btnSuaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaBanActionPerformed

    private void btnXoaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaBanActionPerformed

    private void btnLuuBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLuuBanActionPerformed

    private void btnMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMinimizeMouseClicked

    private void lbThoat2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThoat2MouseClicked
        int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_lbThoat2MouseClicked

    private void tfSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSoLuongActionPerformed
        float thanhTienMua = Float.valueOf(tfSoLuong.getText())*Float.valueOf(tfGiaTien.getText());
        tfThanhTien.setText(""+thanhTienMua);
    }//GEN-LAST:event_tfSoLuongActionPerformed

    private void cbTenMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTenMonActionPerformed
        String sql = "  SELECT maMon, tenLoai, soLuong, giaTien FROM MonAn, DanhMucMon WHERE maLoai=maLoaiMon AND tenMon = ?";
        try{
            String maTheLoaiString = (String) cbTenMon.getSelectedItem();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maTheLoaiString);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                tfMaMon.setText(rs.getString("maMon"));
                tfTenLoaiMon.setText(rs.getString("tenLoai"));
                tfSoLuongTon.setText(rs.getString("soLuong"));
                tfGiaTien.setText(rs.getString("giaTien"));
            }
        } catch(Exception e){

        }
    }//GEN-LAST:event_cbTenMonActionPerformed

    private void cbTenNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTenNVActionPerformed
        String sql = "SELECT maNV FROM NhanVien WHERE tenNV = ?";
        try{
            String maTheLoaiString = (String) cbTenNV.getSelectedItem();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maTheLoaiString);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                tfMaNVHD.setText(rs.getString("maNV"));
            }
        } catch(Exception e){

        }
    }//GEN-LAST:event_cbTenNVActionPerformed

    private void cbCTGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCTGiamGiaActionPerformed
        String sql = "  SELECT maKhuyenMai, tenKhuyenMai, giaKhuyenMai FROM KhuyenMai WHERE tenKhuyenMai = ?";
        try{
            String maKM = (String) cbCTGiamGia.getSelectedItem();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maKM);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                lbMaKM.setText(rs.getString("maKM"));
                cbCTGiamGia.setSelectedItem(rs.getString("tenKhuyenMai"));
                tfGiamGia.setText(rs.getString("giaKM"));
            }
        } catch(Exception e){

        }
    }//GEN-LAST:event_cbCTGiamGiaActionPerformed

    private void tabTatCaMon3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabTatCaMon3MouseClicked
        cbTenMon.removeAllItems();
        int row=tabTatCaMon3.getSelectedRow();
        int maMon=Integer.parseInt(tabTatCaMon3.getValueAt(row, 0).toString());
        String tenMon=tabTatCaMon3.getValueAt(row, 1).toString();
        String donVi=tabTatCaMon3.getValueAt(row, 2).toString();
        float giaBan= Float.parseFloat(tabTatCaMon3.getValueAt(row, 3).toString());
        int soLuong=Integer.parseInt(tabTatCaMon3.getValueAt(row, 4).toString());
        //float tongTien=Float.parseFloat(tableLSHD.getValueAt(row, 3).toString());
        //tfTongTienHD.setText(VNDFormat.format(tongTien));
        tfMaMon.setText(tabTatCaMon3.getValueAt(row, 0).toString());
        cbTenMon.getModel().setSelectedItem(tenMon);
    }//GEN-LAST:event_tabTatCaMon3MouseClicked

    private void tabDoAnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabDoAnMouseClicked

        cbTenMon.removeAllItems();
        int row=tabDoAn.getSelectedRow();
        int maMon=Integer.parseInt(tabDoAn.getValueAt(row, 0).toString());
        String tenMon=tabDoAn.getValueAt(row, 1).toString();
        String donVi=tabDoAn.getValueAt(row, 2).toString();
        float giaBan= Float.parseFloat(tabDoAn.getValueAt(row, 3).toString());
        int soLuong=Integer.parseInt(tabDoAn.getValueAt(row, 4).toString());
        //float tongTien=Float.parseFloat(tableLSHD.getValueAt(row, 3).toString());
        //tfTongTienHD.setText(VNDFormat.format(tongTien));
        tfMaMon.setText(tabDoAn.getValueAt(row, 0).toString());
        cbTenMon.getModel().setSelectedItem(tenMon);
    }//GEN-LAST:event_tabDoAnMouseClicked

    private void tabNuocUongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabNuocUongMouseClicked

        cbTenMon.removeAllItems();
        int row=tabNuocUong.getSelectedRow();
        int maMon=Integer.parseInt(tabNuocUong.getValueAt(row, 0).toString());
        String tenMon=tabNuocUong.getValueAt(row, 1).toString();
        String donVi=tabNuocUong.getValueAt(row, 2).toString();
        float giaBan= Float.parseFloat(tabNuocUong.getValueAt(row, 3).toString());
        int soLuong=Integer.parseInt(tabNuocUong.getValueAt(row, 4).toString());
        //float tongTien=Float.parseFloat(tableLSHD.getValueAt(row, 3).toString());
        //tfTongTienHD.setText(VNDFormat.format(tongTien));
        tfMaMon.setText(tabNuocUong.getValueAt(row, 0).toString());
        cbTenMon.getModel().setSelectedItem(tenMon);
    }//GEN-LAST:event_tabNuocUongMouseClicked

    private float tinhTongTienKM() {
        float tongtien = 0;
        float tongtienk = 0;
        float giamGia = 0;
            float giamGiakm = Float.parseFloat(tfGiamGia.getText());
        
        if(tfGiamGia.getText().equals("0")){
            tinhTongTien();
            }
        else{
            tongtienk = tinhTongTien()*(giamGiakm/100);
            tongtien = tinhTongTien() - tongtienk;
            //tongtien = giamGia * giamGiakm;
             //double finalTotalPrice = totalPrice - (totalPrice / 100) * discount; //Tính tổng tiền khi có giảm giá
            }
        return tongtien;
    }    
    
    private void tableKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableKhuyenMaiMouseClicked
        cbCTGiamGia.removeAllItems();       
        cbCTGiamGia.addItem("0"); 
        int row=tableKhuyenMai.getSelectedRow();
        int maBan=Integer.parseInt(tableKhuyenMai.getValueAt(row, 0).toString());
        String tenKM=tableKhuyenMai.getValueAt(row, 1).toString();
        String trangThaiKM=tableKhuyenMai.getValueAt(row, 2).toString();
        //float tongTien=Float.parseFloat(tableLSHD.getValueAt(row, 3).toString());
        //tfTongTienHD.setText(VNDFormat.format(tongTien));
        lbMaKM.setText(tableKhuyenMai.getValueAt(row, 0).toString());
        cbCTGiamGia.getModel().setSelectedItem(tenKM);
        tfGiamGia.setText(trangThaiKM);

        float tongTien = tinhTongTienKM();
        lbTienthua.setText(""+tongTien);
        return;
        //hienThiHoaDon(maBan);
        //hienThitableCTHDTheoMaBan(maBan);
    }//GEN-LAST:event_tableKhuyenMaiMouseClicked

    private float tinhTongTien() {
        float tongtien = 0;
        int row = tableHoaDon.getRowCount();
        for (int i = 0; i < row; i++) {
            float ThanhTien = Float.parseFloat(tableHoaDon.getValueAt(i, 4).toString());
            tongtien += ThanhTien;
        }
        return tongtien;
    }
    
    private void truSoLuongSauKhiBan(int maMon, int soLuong){
        try{
            String sql="UPDATE MonAn SET soLuong=soLuong - ? WHERE maMon=?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1, soLuong);
            ps.setInt(2, maMon);
            ps.executeUpdate(); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void themHoaDon(int maNV, float tongTien, float giamGia, float VAT) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String dateNow = formatter.format(date).toString();
        try {   
            String sql = "INSERT INTO HoaDonTT(maNhanVien,ngayXuatHoaDon,tongTien,giamGia,VAT)  VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maNV);
            ps.setString(2, dateNow);
            ps.setFloat(3, tongTien);
            ps.setFloat(4, giamGia);
            ps.setFloat(5, VAT);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void themCTHD(int maHoaDon, int maMonAn, String tenMonAn, float giaBan, int soLuong, float thanhTien) {
        try {
            String sql = "INSERT INTO ChiTietHD(maHoaDon,maMonAn,tenMonAn,giaBan,soLuong,thanhTien)  VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maHoaDon);
            ps.setInt(2, maMonAn);
            ps.setString(3, tenMonAn);
            ps.setFloat(4, giaBan);
            ps.setInt(5, soLuong);
            ps.setFloat(6, thanhTien);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void lamMoiPhieuHD() {
        tfMaMon.setText("");
        tfGiaTien.setText("");
        tfSoLuong.setText("1");
        tfMaMon.setText("");
        tfTenLoaiMon.setText("");
    }
    
    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        int rowCount = tableHoaDon.getRowCount();
        if (rowCount == -1) {
            JOptionPane.showMessageDialog(this, "Chưa thêm sản phẩm ");
            return;
        } else {
            int maNV = LayMaNhanVienTheoTen(cbTenNV.getSelectedItem().toString());
            float giamGia= Float.parseFloat(tfGiamGia.getText());
            float VAT= Float.parseFloat(tfVATHD.getText());
            float tongTien = Float.parseFloat( lbTienthua.getText());

            for (int i = 0; i < rowCount; i++) {
                int maMonAn=Integer.parseInt(tableHoaDon.getValueAt(i, 0).toString());
                int soLuongBan=Integer.parseInt(tableHoaDon.getValueAt(i, 3).toString());
                int soLuongton= Integer.parseInt(tfSoLuongTon.getText());

                //String tenMonAn = tableHoaDon.getValueAt(i, 1).toString();
                String tenMon=tableHoaDon.getValueAt(i, 1).toString();

                if(soLuongBan>soLuongton){
                    JOptionPane.showMessageDialog(this, "Số lượng món " +tenMon+ " chỉ còn lại: " +soLuongton);
                    return;
                }
            }

            themHoaDon(maNV, tongTien,giamGia,VAT);
            int maHoaDon = layMaHoaDon();
            for (int i = 0; i < rowCount; i++) {
                int maMonAn = Integer.parseInt(tableHoaDon.getValueAt(i, 0).toString());
                String tenMonAn = tableHoaDon.getValueAt(i, 1).toString();
                float giaBan = Float.parseFloat(tableHoaDon.getValueAt(i, 2).toString());
                int soLuongBan = Integer.parseInt(tableHoaDon.getValueAt(i, 3).toString());
                float thanhTien = Float.parseFloat(tableHoaDon.getValueAt(i, 4).toString());
                themCTHD(maHoaDon, maMonAn, tenMonAn, giaBan, soLuongBan, thanhTien);
                truSoLuongSauKhiBan(maMonAn, soLuongBan);
            }
            modelHoaDon.setRowCount(0);
            tfGiaTien.setText("");
            tfMaMon.setText("");
            tfSoLuong.setText("");
            tfVATHD.setText("0");
            tfGiamGia.setText("0");
            lbTienthua.setText("0 VNĐ");
            lbTongTien.setText("0 VNĐ");
            checkVATHD.setSelected(false);
            JOptionPane.showMessageDialog(rootPane, "Thanh toán thành công ");
            hienThiTableHoaDon();
            hienThiTatCa();
            hienThiNuocUong();
            hienThiDoAn();
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void tableHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHoaDonMouseClicked
        cbTenMon.removeAllItems();

        int click=tableHoaDon.getSelectedRow();
        TableModel model=tableHoaDon.getModel();

        tfMaMon.setText(model.getValueAt(click,0).toString());
        cbTenMon.addItem(model.getValueAt(click, 1).toString());
        tfGiaTien.setText(model.getValueAt(click,2).toString());
        tfSoLuong.setText(model.getValueAt(click,3).toString());
        tfThanhTien.setText(model.getValueAt(click,4).toString());
    }//GEN-LAST:event_tableHoaDonMouseClicked

    private void btnThemMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMoiActionPerformed
        if (tfSoLuong.getText().equals("") || tfGiaTien.getText().equals("")
            || tfMaMon.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập đủ thông tin mua!!");
            return;
        }
        try {
            float giaBan = Float.parseFloat(tfGiaTien.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Giá tiền phải là số!!");
            return;
        }

        int soLuongNhap = Integer.parseInt(tfSoLuong.getText());
        float giaBan = Float.parseFloat(tfGiaTien.getText());
        float thanhTien = soLuongNhap * giaBan;
        int row = tableHoaDon.getRowCount();

        //Kiểm tra thông tin món để thêm món
        for (int i = 0; i < row; i++) {

            if (tableHoaDon.getValueAt(i, 0).equals(tfMaMon.getText())) { //Kiểm tra mã món ăn
                int soLuongKho = Integer.parseInt(tableHoaDon.getValueAt(i, 3).toString()); //Lấy số lượng trong hóa
                int soLuongMua = Integer.parseInt(tfSoLuong.getText()); //Lấy số lượng muốn nhập thêm
                int tongSoLuong = soLuongKho + soLuongMua; //Cộng số lượng trong hóa đơn với số lượng mới vừa nhập lại

                float thanhTienHD = giaBan * tongSoLuong;

                tableHoaDon.setValueAt(giaBan + "", i, 2); //Cột giá tiền món
                tableHoaDon.setValueAt(tongSoLuong + "", i, 3); //Cột số lượng đã thêm
                tableHoaDon.setValueAt(thanhTienHD + "", i, 4); //Cột thành tiền món ăn

                tfGiaTien.setText("");
                tfSoLuong.setText("");
                tfMaMon.setText("");
                cbTenMon.removeAllItems();
                loadTenMon();

                return;
            }
        }

        //Thêm các thông tin đã chọn từ textfield vào table hóa đơn
        Vector<Object> vec = new Vector<>();
        vec.add(tfMaMon.getText());
        vec.add(cbTenMon.getSelectedItem().toString());
        vec.add(tfGiaTien.getText());
        vec.add(tfSoLuong.getText());
        vec.add(thanhTien + "");
        modelHoaDon.addRow(vec); //Thêm dòng mới

        tfGiaTien.setText("");
        tfSoLuong.setText("");
        tfMaMon.setText("");

        float tongTien = tinhTongTien();
        ////          Locale VN = new Locale("vi", "VN");
        ////          Currency dollars = Currency.getInstance(VN);
        ////          NumberFormat VNDFormat = NumberFormat.getCurrencyInstance(VN);
        //          lbTongTien.setText(VNDFormat.format(tongTien));
        lbTongTien.setText(""+tongTien);
        lbTienthua.setText(""+tongTien);
        return;
    }//GEN-LAST:event_btnThemMoiActionPerformed

    private void btnXoa4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa4ActionPerformed
        int row = tableHoaDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập hàng trong bảng để xóa");
            return;
        } else {
            modelHoaDon.removeRow(row);
            double tongTien = tinhTongTien();
            Locale VN = new Locale("vi", "VN");
            Currency dollars = Currency.getInstance(VN);
            NumberFormat VNDFormat = NumberFormat.getCurrencyInstance(VN);
            lbTongTien.setText(VNDFormat.format(tongTien));
            lamMoiPhieuHD();
        }
    }//GEN-LAST:event_btnXoa4ActionPerformed

    private void btnLamMoi3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoi3ActionPerformed
        tfGiaTien.setText("");
        tfSoLuong.setText("");
        tfMaMon.setText("");
        tfGiamGia.setText("0");
        tfVATHD.setText("0");
        cbTenMon.removeAllItems();
        loadTenMon();
    }//GEN-LAST:event_btnLamMoi3ActionPerformed

    private void cboTenMonAnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenMonAnActionPerformed
        String sql = "  SELECT giaTien FROM MonAn WHERE tenMon = ?";
        try{
            String maTheLoaiString = (String) cboTenMonAn.getSelectedItem();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maTheLoaiString);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                tfGiaBanCTHD.setText(rs.getString("giaTien"));
            }
        } catch(Exception e){

        }
    }//GEN-LAST:event_cboTenMonAnActionPerformed

    private void tfSoLuongCTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSoLuongCTHDActionPerformed
        float thanhTienMua = Float.valueOf(tfSoLuongCTHD.getText())*Float.valueOf(tfGiaBanCTHD.getText());
        tfThanhTienCTHD.setText(""+thanhTienMua);
    }//GEN-LAST:event_tfSoLuongCTHDActionPerformed

    public void  XuatHoaDon(int maBanAn){
        try{
            Hashtable map = new Hashtable();
            JasperReport report = JasperCompileManager.compileReport("src/REPORT/rpHoaDon.jrxml");
            map.put("maHoaDon", maBanAn);
            JasperPrint p = JasperFillManager.fillReport(report, map, ConnectSQL.getConnection());
            JasperViewer.viewReport(p, false);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void btnInHoaDonCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHoaDonCTActionPerformed
        int maHoaDon = Integer.parseInt(tfMaHD.getText());
        XuatHoaDon(maHoaDon);
    }//GEN-LAST:event_btnInHoaDonCTActionPerformed

    private void panelMonAnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMonAnMouseClicked
        panelHoaDon.setBackground(new Color(51,51,51));
        panelDanhMuc.setBackground(new Color(51,51,51));
        panelMonAn.setBackground(new Color(49,139,130));
        panelNhanVien.setBackground(new Color(51,51,51));
        panelDoanhThu.setBackground(new Color(51,51,51));
        panelTaiKhoan.setBackground(new Color(51,51,51));
        panelKhuyenMai.setBackground(new Color(51,51,51));
        panelCTHD.setBackground(new Color(51,51,51));
        //panelDSBan.setBackground(new Color(51,51,51));
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_panelMonAnMouseClicked

    private void panelHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHoaDonMouseClicked
        panelHoaDon.setBackground(new Color(49,139,130));
        panelDanhMuc.setBackground(new Color(51,51,51));
        panelMonAn.setBackground(new Color(51,51,51));
        panelNhanVien.setBackground(new Color(51,51,51));
        panelDoanhThu.setBackground(new Color(51,51,51));
        panelTaiKhoan.setBackground(new Color(51,51,51));
        panelCTHD.setBackground(new Color(51,51,51));
        panelKhuyenMai.setBackground(new Color(51,51,51));
        //panelDSBan.setBackground(new Color(51,51,51));
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_panelHoaDonMouseClicked

    private void panelDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDanhMucMouseClicked
        panelDanhMuc.setBackground(new Color(49,139,130));
        panelHoaDon.setBackground(new Color(51,51,51));
        panelMonAn.setBackground(new Color(51,51,51));
        panelNhanVien.setBackground(new Color(51,51,51));
        panelDoanhThu.setBackground(new Color(51,51,51));
        panelTaiKhoan.setBackground(new Color(51,51,51));
        panelCTHD.setBackground(new Color(51,51,51));
        panelKhuyenMai.setBackground(new Color(51,51,51));
        //panelDSBan.setBackground(new Color(51,51,51));
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_panelDanhMucMouseClicked

    private void panelDoanhThuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoanhThuMouseClicked
        panelHoaDon.setBackground(new Color(51,51,51));
        panelDanhMuc.setBackground(new Color(51,51,51));
        panelMonAn.setBackground(new Color(51,51,51));
        panelNhanVien.setBackground(new Color(51,51,51));
        panelDoanhThu.setBackground(new Color(49,139,130));
        panelTaiKhoan.setBackground(new Color(51,51,51));
        panelCTHD.setBackground(new Color(51,51,51));
        panelKhuyenMai.setBackground(new Color(51,51,51));
        //panelDSBan.setBackground(new Color(51,51,51));
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_panelDoanhThuMouseClicked

    private void panelNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNhanVienMouseClicked
        panelHoaDon.setBackground(new Color(51,51,51));
        panelDanhMuc.setBackground(new Color(51,51,51));
        panelMonAn.setBackground(new Color(51,51,51));
        panelNhanVien.setBackground(new Color(49,139,130));
        panelDoanhThu.setBackground(new Color(51,51,51));
        panelTaiKhoan.setBackground(new Color(51,51,51));
        panelCTHD.setBackground(new Color(51,51,51));
        panelKhuyenMai.setBackground(new Color(51,51,51));
        //panelDSBan.setBackground(new Color(51,51,51));
        jTabbedPane1.setSelectedIndex(6);
    }//GEN-LAST:event_panelNhanVienMouseClicked

    private void panelTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTaiKhoanMouseClicked
        panelHoaDon.setBackground(new Color(51,51,51));
        panelDanhMuc.setBackground(new Color(51,51,51));
        panelMonAn.setBackground(new Color(51,51,51));
        panelNhanVien.setBackground(new Color(51,51,51));
        panelDoanhThu.setBackground(new Color(51,51,51));
        panelTaiKhoan.setBackground(new Color(49,139,130));
        panelCTHD.setBackground(new Color(51,51,51));
        panelKhuyenMai.setBackground(new Color(51,51,51));
        //panelDSBan.setBackground(new Color(51,51,51));
        jTabbedPane1.setSelectedIndex(7);
    }//GEN-LAST:event_panelTaiKhoanMouseClicked

    private void panelTrangChuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTrangChuMouseClicked
        panelHoaDon.setBackground(new Color(51,51,51));
        panelDanhMuc.setBackground(new Color(51,51,51));
        panelMonAn.setBackground(new Color(51,51,51));
        panelNhanVien.setBackground(new Color(51,51,51));
        panelDoanhThu.setBackground(new Color(51,51,51));
        panelTaiKhoan.setBackground(new Color(51,51,51));
        panelCTHD.setBackground(new Color(51,51,51));
        panelKhuyenMai.setBackground(new Color(51,51,51));
        //panelDSBan.setBackground(new Color(51,51,51));
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_panelTrangChuMouseClicked

    private void panelCTHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCTHDMouseClicked
        panelCTHD.setBackground(new Color(49,139,130));
        panelDanhMuc.setBackground(new Color(51,51,51));
        panelMonAn.setBackground(new Color(51,51,51));
        panelNhanVien.setBackground(new Color(51,51,51));
        panelDoanhThu.setBackground(new Color(51,51,51));
        panelTaiKhoan.setBackground(new Color(51,51,51));
        panelHoaDon.setBackground(new Color(51,51,51));
        panelKhuyenMai.setBackground(new Color(51,51,51));
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_panelCTHDMouseClicked

    private void panelKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelKhuyenMaiMouseClicked
        //panelDSBan.setBackground(new Color(51,51,51));
        panelKhuyenMai.setBackground(new Color(49,139,130));
        panelDanhMuc.setBackground(new Color(51,51,51));
        panelMonAn.setBackground(new Color(51,51,51));
        panelNhanVien.setBackground(new Color(51,51,51));
        panelDoanhThu.setBackground(new Color(51,51,51));
        panelTaiKhoan.setBackground(new Color(51,51,51));
        panelHoaDon.setBackground(new Color(51,51,51));
        panelCTHD.setBackground(new Color(51,51,51));
        jTabbedPane1.setSelectedIndex(9);
    }//GEN-LAST:event_panelKhuyenMaiMouseClicked

    private void btnMinimize11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimize11MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMinimize11MouseClicked

    private void lbThoat13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThoat13MouseClicked
        int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_lbThoat13MouseClicked

    public void RemoveKM(){
        tfTenKM.setText("");
        tfMaKM.setText("");
        tfGiaKM.setText("");
    }
    
    private void btnThemKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKMActionPerformed
        RemoveKM();
        btnXoaKM.setEnabled(false);
        btnSuaKM.setEnabled(false);
    }//GEN-LAST:event_btnThemKMActionPerformed

    private void btnXoaKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKMActionPerformed
        int row = tableDSKhuyenMai.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(QuanLy.this, "Vui lòng chọn khuyến mãi cần xóa","Thông báo",JOptionPane.ERROR_MESSAGE);
        }else{
            int xacNhan = JOptionPane.showConfirmDialog(QuanLy.this, "Bạn có chắc chắn muốn xóa không?","Thông báo",JOptionPane.YES_NO_OPTION);
            if(xacNhan == JOptionPane.YES_OPTION){
                String maKM = String.valueOf(tableDSKhuyenMai.getValueAt(row, 0));
                khuyenMaiSERVICE.xoaKhuyenMai(maKM);
            }
        }
        hienThiKhuyenMai();
    }//GEN-LAST:event_btnXoaKMActionPerformed

    private void btnSuaKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKMActionPerformed
        btnLuuKM.setEnabled(true);
        
        int row = tableDSKhuyenMai.getSelectedRow();   
        if(row == -1){
            JOptionPane.showMessageDialog(QuanLy.this, "Vui lòng chọn khuyến mãi cần sửa","Thông báo",JOptionPane.ERROR_MESSAGE);
        }else{
            int xacNhan = JOptionPane.showConfirmDialog(QuanLy.this, "Bạn có chắc chắn muốn sửa  khuyến mãi này không?","Thông báo",JOptionPane.YES_NO_OPTION);
            if(xacNhan == JOptionPane.YES_OPTION){
                
            khuyenMai.setMaKhuyenMai(Integer.parseInt(tfMaKM.getText()));
            khuyenMai.setTenKhuyenMai(tfTenKM.getText());
            khuyenMai.setGiaKhuyenMai(Integer.valueOf(tfGiaKM.getText()));       
            
               // String maBan = String.valueOf(tableNV.getValueAt(row, 0));
                khuyenMaiSERVICE.suaKhuyenMai(khuyenMai);
            JOptionPane.showMessageDialog(null, "Đã sửa thông tin  khuyến mãi thành công!^^");
                hienThiKhuyenMai();
            }
        }
    }//GEN-LAST:event_btnSuaKMActionPerformed

    private boolean kiemtraNullKM(){
        if(tfTenKM.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập tên khuyến mãi!");
            return false;
        }
        else
        if(tfGiaKM.getText().equals("")){
            JOptionPane.showMessageDialog(QuanLy.this,"Bạn chưa nhập giá trị khuyến mãi!");
            return false;
        }
        else{ 
            return true;
        }
    }
    
    private void btnLuuKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuKMActionPerformed
        if(kiemtraNullKM()){                 
        khuyenMai.setTenKhuyenMai(tfTenKM.getText());        
        khuyenMai.setGiaKhuyenMai(Integer.valueOf(tfGiaKM.getText()));                 
        
        khuyenMaiSERVICE.themKhuyenMai(khuyenMai);
            JOptionPane.showMessageDialog(null, "Đã thêm khuyến mãi mới thành công!^^");
            hienThiKhuyenMai();
        }
    }//GEN-LAST:event_btnLuuKMActionPerformed

    private float tinhTongTienVAT() {
        float tongtien = 0;
        float tongtienk = 0;
        float giamGia = 0;
            float giamGiakm = Float.parseFloat(tfGiamGia.getText());
            float vatHD = Float.parseFloat(tfVATHD.getText());
        
        if(tfGiamGia.getText().equals("0") && !checkVATHD.isSelected()){
            tinhTongTien();
         }   
        else if(tfGiamGia.getText().equals("0") && checkVATHD.isSelected()){
            tongtienk = tinhTongTien()*(vatHD/100);
            tongtien = tinhTongTien() + tongtienk;
            }
        else if(!tfGiamGia.getText().equals("0") && checkVATHD.isSelected()){           
            tongtienk = tinhTongTien()*(vatHD/100);
            tongtien = (tinhTongTien() + tongtienk) - (tinhTongTien()*(giamGiakm/100));
            //tongtien = giamGia * giamGiakm;
             //double finalTotalPrice = totalPrice - (totalPrice / 100) * discount; //Tính tổng tiền khi có giảm giá
            }
        return tongtien;
    }    
    
    private void checkVATHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkVATHDActionPerformed
        if(checkVATHD.isSelected()){
            tfVATHD.setText("5");
            tinhTongTienVAT();
            float tongTien = tinhTongTienVAT();
        lbTienthua.setText(""+tongTien);
        return;
        }else{
            //float tientong = Float.parseFloat(lbTongTien.getText());
           // lbTongTien.getText();
           tfVATHD.setText("0");
            lbTienthua.setText(lbTongTien.getText());
        }
    }//GEN-LAST:event_checkVATHDActionPerformed

    private void tableDSKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDSKhuyenMaiMouseClicked
        int row=tableDSKhuyenMai.getSelectedRow();
        int maKM=Integer.parseInt(tableDSKhuyenMai.getValueAt(row, 0).toString());
        String tenKM=tableDSKhuyenMai.getValueAt(row, 1).toString();
        float giaKM=Float.parseFloat(tableDSKhuyenMai.getValueAt(row, 2).toString());
        
        tfMaKM.setText(tableDSKhuyenMai.getValueAt(row, 0).toString());
        tfTenKM.setText(tableDSKhuyenMai.getValueAt(row, 1).toString());
        tfGiaKM.setText(tableDSKhuyenMai.getValueAt(row, 2).toString());
    }//GEN-LAST:event_tableDSKhuyenMaiMouseClicked

    private void btnTraCuuMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraCuuMonActionPerformed
        if(cbMonAnDoanhThu.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(this,"Chưa chọn món cần tìm");
        }else{
            String tenMon = String.valueOf(cbMonAnDoanhThu.getSelectedItem());
            LayDoanhThuMonAn(tenMon);

            float tongtienHD = tinhTongDoanhThuMon();
            Locale VN = new Locale("vi", "VN");
            Currency dollars = Currency.getInstance(VN);
            NumberFormat VNDFormat = NumberFormat.getCurrencyInstance(VN);
            lbTongDoanhThuMon.setText(VNDFormat.format(tongtienHD));
            return;
        }
    }//GEN-LAST:event_btnTraCuuMonActionPerformed

    private int LayDoanhThuThoiGian(java.sql.Date tenMon) {
       int dem = 0;
        try {
            modelDoanhThuTG.setRowCount(0);
            String sql = "  SELECT maHD, ngayXuatHoaDon, tongtien FROM HoaDonTT hd  WHERE ngayXuatHoaDon = ?";
            ps = conn.prepareStatement(sql);
            ps.setDate(1, tenMon);
            rs = ps.executeQuery();
            while (rs.next()) {
                Vector<Object> vec=new Vector<>();
                vec.add(rs.getInt("maHD"));
                vec.add(rs.getFloat("tongTien"));
                vec.add(rs.getDate("ngayXuatHoaDon"));
                modelDoanhThuTG.addRow(vec);
                dem++;
            }
            lbTongHDTG.setText(String.valueOf(dem));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
    
    private float tinhTongDoanhThuTG() {
        float tongtienHD = 0;
        int row = tableThongke.getRowCount();
        for (int i = 0; i < row; i++) {
            float tongtien = Float.parseFloat(tableThongke.getValueAt(i, 1).toString());
            tongtienHD += tongtien;
        }
        return tongtienHD;
    }
    
    public void LayThoiGianHT(){
        Date now = new Date();
        tfNgayHoaDon.setDate(now); //Lấy ngày hiện tại gán vào tfNgayHoaDon
    }
    
    private void btnTraCuuTKTGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraCuuTKTGActionPerformed
        java.sql.Date ngayHD = java.sql.Date.valueOf(new java.sql.Date(tfNgayHoaDon.getDate().getTime()).toString());

        LayDoanhThuThoiGian(ngayHD);
        float tongtienHD = tinhTongDoanhThuTG();
        Locale VN = new Locale("vi", "VN");
        Currency dollars = Currency.getInstance(VN);
        NumberFormat VNDFormat = NumberFormat.getCurrencyInstance(VN);
        lbTongDoanhThuTG.setText(VNDFormat.format(tongtienHD));
        return;
    }//GEN-LAST:event_btnTraCuuTKTGActionPerformed

    private void tfVATHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfVATHDActionPerformed
        tinhTongTienVAT();
        float tongTien = tinhTongTienVAT();
        lbTienthua.setText(""+tongTien);
        return; 
    }//GEN-LAST:event_tfVATHDActionPerformed

    private void tfGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfGiamGiaMouseClicked
        tfGiamGia.setText("0.0");
        cbCTGiamGia.removeAllItems();
        tinhTongTienKM();
        float tongTien = tinhTongTienKM();
        lbTienthua.setText(""+tongTien);
        return; 
    }//GEN-LAST:event_tfGiamGiaMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLy().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCaiDatMK;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnInHoaDonCT;
    private javax.swing.JButton btnLamMoi3;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnLuuBan;
    private javax.swing.JButton btnLuuDM;
    private javax.swing.JButton btnLuuKM;
    private javax.swing.JButton btnLuuMon;
    private javax.swing.JButton btnLuuNV;
    private javax.swing.JButton btnLuuTK;
    private javax.swing.JLabel btnMinimize;
    private javax.swing.JLabel btnMinimize1;
    private javax.swing.JLabel btnMinimize11;
    private javax.swing.JLabel btnMinimize2;
    private javax.swing.JLabel btnMinimize3;
    private javax.swing.JLabel btnMinimize4;
    private javax.swing.JLabel btnMinimize6;
    private javax.swing.JLabel btnMinimize7;
    private javax.swing.JLabel btnMinimize8;
    private javax.swing.JLabel btnMinimize9;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSuaBan;
    private javax.swing.JButton btnSuaDM;
    private javax.swing.JButton btnSuaKM;
    private javax.swing.JButton btnSuaMon;
    private javax.swing.JButton btnSuaNV;
    private javax.swing.JButton btnSuaTK;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemBan;
    private javax.swing.JButton btnThemDM;
    private javax.swing.JButton btnThemKM;
    private javax.swing.JButton btnThemMoi;
    private javax.swing.JButton btnThemMon;
    private javax.swing.JButton btnThemNV;
    private javax.swing.JButton btnThemTK;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTraCuuMon;
    private javax.swing.JButton btnTraCuuNV;
    private javax.swing.JButton btnTraCuuTKTG;
    private javax.swing.JButton btnUpload;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoa4;
    private javax.swing.JButton btnXoaBan;
    private javax.swing.JButton btnXoaDM;
    private javax.swing.JButton btnXoaKM;
    private javax.swing.JButton btnXoaMon;
    private javax.swing.JButton btnXoaNV;
    private javax.swing.JButton btnXoaTK;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbCTGiamGia;
    private javax.swing.JComboBox<String> cbLoaiMon;
    private javax.swing.JComboBox<String> cbMonAnDoanhThu;
    private javax.swing.JComboBox<String> cbNVDoanhThu;
    private javax.swing.JComboBox<String> cbTenMon;
    public static javax.swing.JComboBox<String> cbTenNV;
    private javax.swing.JComboBox<String> cboTenMonAn;
    private javax.swing.JComboBox<String> cboTenNV;
    private javax.swing.JCheckBox checkVATHD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JPanel jpnalCTHD;
    private javax.swing.JPanel jpnalLSHĐ;
    private javax.swing.JLabel lbDiachi;
    private javax.swing.JLabel lbGia1;
    private javax.swing.JLabel lbGioitinh;
    private javax.swing.JLabel lbHoten;
    private javax.swing.JLabel lbImageDM;
    private javax.swing.JLabel lbImageFood;
    private javax.swing.JLabel lbImageNV;
    private javax.swing.JLabel lbLoai;
    private javax.swing.JLabel lbMaKM;
    private javax.swing.JLabel lbMaNV;
    private javax.swing.JLabel lbNgaysinh;
    private javax.swing.JLabel lbSDT;
    private javax.swing.JLabel lbThoat;
    private javax.swing.JLabel lbThoat1;
    private javax.swing.JLabel lbThoat10;
    private javax.swing.JLabel lbThoat11;
    private javax.swing.JLabel lbThoat13;
    private javax.swing.JLabel lbThoat2;
    private javax.swing.JLabel lbThoat3;
    private javax.swing.JLabel lbThoat4;
    private javax.swing.JLabel lbThoat5;
    private javax.swing.JLabel lbThoat7;
    private javax.swing.JLabel lbThoat8;
    private javax.swing.JLabel lbTienthua;
    private javax.swing.JLabel lbTongDoanhThuMon;
    private javax.swing.JLabel lbTongDoanhThuNV;
    private javax.swing.JLabel lbTongDoanhThuTG;
    private javax.swing.JLabel lbTongHDMon;
    private javax.swing.JLabel lbTongHDNV;
    private javax.swing.JLabel lbTongHDTG;
    private javax.swing.JLabel lbTongTien;
    private javax.swing.JPanel paneThucDon3;
    private javax.swing.JPanel panelCTHD;
    private javax.swing.JPanel panelChiTietHD;
    private javax.swing.JPanel panelDanhMuc;
    private javax.swing.JPanel panelDoAn;
    private javax.swing.JPanel panelDoanhThu;
    private javax.swing.JPanel panelGiaoDien;
    private javax.swing.JPanel panelHoaDon;
    private javax.swing.JPanel panelKhuyenMai;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelMonAn;
    private javax.swing.JPanel panelNhanVien;
    private javax.swing.JPanel panelNuocUong;
    private javax.swing.JPanel panelTaiKhoan;
    private javax.swing.JTabbedPane panelThucDonMon3;
    private javax.swing.JPanel panelTrangChu;
    private javax.swing.JPanel pnDSBan;
    private javax.swing.JPanel pnDanhMuc;
    private javax.swing.JPanel pnDoanhThu;
    private javax.swing.JPanel pnHoaDon;
    private javax.swing.JPanel pnKhuyenMai;
    private javax.swing.JPanel pnMonAn;
    private javax.swing.JPanel pnNhanVien;
    private javax.swing.JPanel pnTaiKhoan;
    private javax.swing.JPanel pnTrangChu;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tabDoAn;
    private javax.swing.JTable tabNuocUong;
    private javax.swing.JTable tabTatCaMon3;
    private javax.swing.JTable tableCTHD;
    private javax.swing.JTable tableDSBan;
    private javax.swing.JTable tableDSKhuyenMai;
    private javax.swing.JTable tableDanhMuc;
    private javax.swing.JTable tableFood;
    private javax.swing.JTable tableHoaDon;
    private javax.swing.JTable tableKhuyenMai;
    private javax.swing.JTable tableLSHD;
    private javax.swing.JTable tableNV;
    private javax.swing.JTable tableTaiKhoan;
    private javax.swing.JTable tableThongke;
    private javax.swing.JTable tableThongkeMon;
    private javax.swing.JTable tableThongkeNV;
    private javax.swing.JTextField tfChucVu;
    private javax.swing.JTextField tfDiachi;
    private javax.swing.JTextField tfDonViTinh;
    private javax.swing.JTextField tfGiaBanCTHD;
    private javax.swing.JTextField tfGiaKM;
    private javax.swing.JTextField tfGiaTien;
    private javax.swing.JTextField tfGiaTienMon;
    private javax.swing.JLabel tfGiamGia;
    private javax.swing.JTextField tfHoten;
    private javax.swing.JTextField tfLinkDM;
    private javax.swing.JTextField tfLinkMonAn;
    private javax.swing.JTextField tfLinkNV;
    private javax.swing.JTextField tfLoaiTK;
    private javax.swing.JTextField tfMaDM;
    private javax.swing.JTextField tfMaHD;
    private javax.swing.JTextField tfMaKM;
    private javax.swing.JTextField tfMaMon;
    private javax.swing.JTextField tfMaMonAn;
    private javax.swing.JTextField tfMaNV;
    private javax.swing.JTextField tfMaNVHD;
    private com.toedter.calendar.JDateChooser tfNgayHoaDon;
    private com.toedter.calendar.JDateChooser tfNgaySinh;
    private javax.swing.JTextField tfSDT;
    private javax.swing.JTextField tfSoLuong;
    private javax.swing.JTextField tfSoLuongCTHD;
    private javax.swing.JTextField tfSoLuongMon;
    private javax.swing.JTextField tfSoLuongTon;
    private javax.swing.JTextField tfTaiKhoan;
    private javax.swing.JTextField tfTenDM;
    public static javax.swing.JTextField tfTenDangNhapNV;
    private javax.swing.JTextField tfTenHienThi;
    private javax.swing.JTextField tfTenKM;
    private javax.swing.JTextField tfTenLoai;
    private javax.swing.JTextField tfTenLoaiMon;
    private javax.swing.JTextField tfTenMonAn;
    private javax.swing.JTextField tfTenNV;
    private javax.swing.JTextField tfTenTaiKhoan;
    private javax.swing.JTextField tfThanhTien;
    private javax.swing.JTextField tfThanhTienCTHD;
    private javax.swing.JTextField tfTimKiem;
    private javax.swing.JTextField tfTongTienHD;
    private javax.swing.JTextField tfVATHD;
    // End of variables declaration//GEN-END:variables

    //CẬP NHẬT DỮ LIỆU
    public void RefeshMA(){
       modelThucDon.setRowCount(0); //Xóa dữ liệu cũ về 0
       setDataTableTD( thucDonSERVICE.getAllThucDon()); // lấy dữ liệu mới
    }
    
    public void RefeshNV(){
       modelNhanVien.setRowCount(0); //Xóa dữ liệu cũ về 0
       setDataTableNV(nhanVienSERVICE.getAllNhanVien()); // lấy dữ liệu mới
    }
    
    public void RefeshDM(){
       modelDanhMuc.setRowCount(0); //Xóa dữ liệu cũ về 0
       setDataTableDM(danhMucSERVICE.getAllDanhMuc()); // lấy dữ liệu mới
    }
    
    public void RefeshTK(){
       modelTaiKhoan.setRowCount(0); //Xóa dữ liệu cũ về 0
       setDataTableTK(taiKhoanSERVICE.getAllTaiKhoan()); // lấy dữ liệu mới
    }
    //LẤY MÃ 
    private int layMaHoaDon() {
        int ma = 0;
        try {
            String sql = "SELECT top 1 maHD FROM HoaDonTT ORDER BY maHD DESC"; //DESC Lấy mã hóa đơn đầu tiên từ dưới lên || ASC từ trên xuống
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ma = rs.getInt("maHD");
                return ma;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ma;
    }
    
    private int LayMaNhanVienTheoTen(String TenNV) {
        int maNV = 0;
        try {
            String sql = "SELECT maNV FROM NhanVien WHERE tenNV=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, TenNV);
            rs = ps.executeQuery();
            while (rs.next()) {
                maNV = rs.getInt("maNV");
                return maNV;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // xong gán nó cho mã này
        return maNV;
    }
    
    private int LayMaMonTheoTen(String TenMon) {
        int maMon = 0;
        try {
            String sql = "SELECT maMon FROM MonAn WHERE tenMon= ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, TenMon);
            rs = ps.executeQuery();
            while (rs.next()) {
                maMon = rs.getInt("maMon");
                return maMon;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maMon;
    }
    
    //LẤY DỮ LIỆU VÀO COMBOBOX
     private void loadTenNV() {
        String sql = "  SELECT tenNV FROM NhanVien WHERE maNV = maNV ";
        try{
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                //this.cbTenNV.addItem(rs.getString("tenNV"));
                this.cbNVDoanhThu.addItem(rs.getString("tenNV"));
            }
            } catch(Exception e){

        }
            }
     
     private void loadTenMon() {
        String sql = "  SELECT tenMon FROM MonAn WHERE maMon = maMon  ";
        try{
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                this.cbTenMon.addItem(rs.getString("tenMon"));
                this.cbMonAnDoanhThu.addItem(rs.getString("tenMon"));
            }
            } catch(Exception e){

        }
            }
     
     //CHỌN HÌNH ẢNH
        File file = null;
        public void ChonAnhNV(){
        JFileChooser open = new JFileChooser("F:\\JavaSwing\\DoAnCoSoTCF\\DoAnCoSo_TCFood\\src");
        open.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int res = open.showOpenDialog(QuanLy.this);
        if(res==JFileChooser.APPROVE_OPTION){
            file = open.getSelectedFile();
            String path = file.getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
            Rectangle rerc =lbImageNV.getBounds();
            Image scaImage = imageIcon.getImage().getScaledInstance(rerc.width, rerc.height, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaImage);
            lbImageNV.setIcon(imageIcon);
            tfLinkNV.setText(file.getName());
        }else{
            JOptionPane.showMessageDialog(this, "Không có ảnh mới được chọn!!");
        }
    }
        public void ChonAnhMA(){
        JFileChooser open = new JFileChooser("F:\\JavaSwing\\DoAnCoSoTCF\\DoAnCoSo_TCFood\\src");
        open.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int res = open.showOpenDialog(QuanLy.this);
        if(res==JFileChooser.APPROVE_OPTION){
            file = open.getSelectedFile();
            String path = file.getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
            Rectangle rerc =lbImageFood.getBounds();
            Image scaImage = imageIcon.getImage().getScaledInstance(rerc.width, rerc.height, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaImage);
            lbImageFood.setIcon(imageIcon);
            tfLinkMonAn.setText(file.getName());
        }else{
            JOptionPane.showMessageDialog(this, "Không có ảnh mới được chọn!!");
        }
    }
        public void ChonAnhDM(){
        JFileChooser open = new JFileChooser("F:\\JavaSwing\\DoAnCoSoTCF\\DoAnCoSo_TCFood\\src");
        open.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int res = open.showOpenDialog(QuanLy.this);
        if(res==JFileChooser.APPROVE_OPTION){
            file = open.getSelectedFile();
            String path = file.getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
            Rectangle rerc =lbImageDM.getBounds();
            Image scaImage = imageIcon.getImage().getScaledInstance(rerc.width, rerc.height, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaImage);
            lbImageDM.setIcon(imageIcon);
            tfLinkDM.setText(file.getName());
        }else{
            JOptionPane.showMessageDialog(this, "Không có ảnh mới được chọn!!");
        }
    }
        
     //FORM CT HÓA ĐƠN
     public void HienThiCboTenMon(){
        try{
            String sql="select tenMon from MonAn where maMon=maMon";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            cboTenMonAn.removeAllItems();
            while(rs.next()){
                cboTenMonAn.addItem(rs.getString("tenMon"));
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void HienThiCboNhanVien(){
        try{
            String sql="SELECT tenNV FROM NhanVien WHERE maNV=maNV";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            cboTenNV.removeAllItems();
            while(rs.next()){
                cboTenNV.addItem(rs.getString("tenNV"));
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void hienThiKhuyenMai(){
        try {
            modelkhuyenMai.setRowCount(0);
            modelDSKhuyenMai.setRowCount(0);
            String sql=" SELECT km.maKhuyenMai, tenKhuyenMai, giaKhuyenMai FROM KhuyenMai km WHERE maKhuyenMai=km.maKhuyenMai";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                Vector<Object> vec=new Vector<>();
                vec.add(rs.getInt("maKhuyenMai"));
                vec.add(rs.getString("tenKhuyenMai"));
                vec.add(rs.getFloat("giaKhuyenMai"));
                modelkhuyenMai.addRow(vec);
                modelDSKhuyenMai.addRow(vec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void hienThiTatCa(){
        try {
            modelTatCaMon.setRowCount(0);
            String sql="SELECT maMon, tenMon, donVi, giaTien, soLuong FROM MonAn";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                Vector<Object> vec=new Vector<>();
                vec.add(rs.getInt("maMon"));
                vec.add(rs.getString("tenMon"));
                vec.add(rs.getString("donVi"));
                vec.add(rs.getFloat("giaTien"));
                vec.add(rs.getInt("soLuong"));
                modelTatCaMon.addRow(vec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void hienThiDoAn(){
        try {
            modelDoAn.setRowCount(0);
            String sql="SELECT maMon, tenMon, donVi, giaTien, soLuong FROM MonAn, DanhMucMon WHERE maLoaiMon = maLoai and tenLoai != N'Nước uống' ";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                Vector<Object> vec=new Vector<>();
                vec.add(rs.getInt("maMon"));
                vec.add(rs.getString("tenMon"));
                vec.add(rs.getString("donVi"));
                vec.add(rs.getFloat("giaTien"));
                vec.add(rs.getInt("soLuong"));
                modelDoAn.addRow(vec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void hienThiNuocUong(){
        try {
            modelNuocUong.setRowCount(0);
            String sql="SELECT maMon, tenMon, donVi, giaTien, soLuong FROM MonAn, DanhMucMon WHERE maLoaiMon = maLoai and tenLoai = N'Nước uống' ";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                Vector<Object> vec=new Vector<>();
                vec.add(rs.getInt("maMon"));
                vec.add(rs.getString("tenMon"));
                vec.add(rs.getString("donVi"));
                vec.add(rs.getFloat("giaTien"));
                vec.add(rs.getInt("soLuong"));
                modelNuocUong.addRow(vec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int LayMaNhanVienTheoTenCTHD(String tenNV){
        int Ma=0;
        try{
            String sql="SELECT maNV FROM NhanVien WHERE tenNV=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1, tenNV);
            rs=ps.executeQuery();
            while(rs.next()){
                 Ma=rs.getInt("maNV");
                 return Ma;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
            // xong gán nó cho thằng mã này
        return Ma;
    }
    
    private int LayMaMonAnTheoTen(String tenMon){
        int Ma=0;
        try{
            String sql="SELECT maMon FROM MonAn WHERE tenMon=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1, tenMon);
            rs=ps.executeQuery();
            while(rs.next()){
                 Ma=rs.getInt("maMon");
                 return Ma;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
            // xong gán nó cho thằng mã này
        return Ma;
    }
    
    private void hienThiTableHoaDon(){
        try {
            modelLSHD.setRowCount(0);
            String sql="SELECT a.maHD, c.tenNV, ngayXuatHoaDon, tongtien FROM HoaDonTT a, NhanVien c WHERE a.maNhanVien=c.maNV";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                Vector<Object> vec=new Vector<>();
                vec.add(rs.getInt("maHD"));
                vec.add(rs.getString("tenNV"));
                vec.add(rs.getDate("ngayXuatHoaDon"));
                vec.add(rs.getFloat("tongtien"));
                modelLSHD.addRow(vec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void hienThitableCTHDTheoMaHD(int maHD){
        try {
            modelCTHD.setRowCount(0);
            String sql="SELECT tenMonAn, giaBan, soLuong, thanhTien FROM ChiTietHD WHERE maHoaDon=?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1, maHD);
            rs=ps.executeQuery();
            while(rs.next()){
                Vector<Object> vec=new Vector<>();
                vec.add(rs.getString("tenMonAn"));
                vec.add(rs.getFloat("giaBan"));
                vec.add(rs.getInt("soLuong"));
                vec.add(rs.getFloat("thanhTien"));
                modelCTHD.addRow(vec);
            }
        } catch (Exception e) {
        }
    }

    private void xoaHDTheoMaHD(int maHD){
        try {
            String sql="UPDATE HoaDonTT SET DaXoa=1 WHERE maHD=?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1, maHD);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void xoaCTHD(int maHD, int maMA){
        try {
            String sql="DELETE FROM ChiTietHD WHERE maHoaDon=? AND maMonAn=?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1, maHD);
            ps.setInt(2, maMA);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private float layTongTienSauKhiXoaHoaDon(int maHD){
        float tongtien=0;
        try {
            String sql="SELECT thanhTien FROM ChiTietHD WHERE maHoaDon=?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1, maHD);
            rs=ps.executeQuery();
            while(rs.next()){
                float thanhtien=rs.getFloat("thanhTien");
                tongtien+=thanhtien;
            }
            return tongtien;
        } catch (Exception e) {
        }
        return tongtien;
    }
    
    private void capNhatTongTienHoaDonSauKhiXoaCTHD(int maHD, float tongTien){
        try {
            String sql="UPDATE HoaDonTT SET tongTien=? WHERE maHD=?";
             ps=conn.prepareStatement(sql);
             ps.setFloat(1, tongTien);
             ps.setInt(2, maHD);
             ps.executeUpdate();
        } catch (Exception e) {
        }
    }
    void dongTextBox(){
        tfGiaBanCTHD.setText("");
        tfMaHD.setText("");
        tfSoLuongCTHD.setText("");
        tfThanhTienCTHD.setText("");
        tfTongTienHD.setText("");
    }
    void dongTextBoxCTHD(){
        tfGiaBanCTHD.setText("");
        tfSoLuongCTHD.setText("");
        tfThanhTienCTHD.setText("");
    }
    
    private void capNhatHoaDon(int maHD, int maNV){
        try {
            String sql="UPDATE HoaDonTT SET maNhanVien=? WHERE maHD=?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1, maNV);
            ps.setInt(2, maHD);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void capNhatCTHD(int maHD, int maMA, float giaBan, int soLuong, float thanhTien){
        try {
            String sql="UPDATE ChiTietHD SET giaBan=?, soLuong=?, thanhTien=? WHERE maHoaDon=? and maMonAn=?";
            ps=conn.prepareStatement(sql);
            ps.setFloat(1, giaBan);
            ps.setInt(2, soLuong);
            ps.setFloat(3, thanhTien);
            ps.setInt(4, maHD);
            ps.setInt(5, maMA);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}