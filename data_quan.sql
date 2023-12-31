
/****** Object:  Database [DoAnCoSoQLTCF] ******/
CREATE DATABASE [DoAnCoSoQLTCF] 
GO

USE [DoAnCoSoQLTCF]
GO
  
/****** Object:  Table [dbo].[ChiTietHD] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietHD](
	[maHoaDon] [int] NULL,
	[maMonAn] [int] NULL,
	[tenMonAn] [nvarchar](100) NULL,
	[giaBan] [float] NULL,
	[soLuong] [int] NULL,
	[thanhTien] [float] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DanhMucMon] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DanhMucMon](
	[maLoai] [int] IDENTITY(1,1) NOT NULL,
	[tenLoai] [nvarchar](100) NULL,
	[imageDM] [varchar](150) NULL,
PRIMARY KEY CLUSTERED 
(
	[maLoai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HoaDonTT] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDonTT](
	[maHD] [int] IDENTITY(1,1) NOT NULL,
	[maNhanVien] [int] NULL,
	[ngayXuatHoaDon] [date] NULL,
	[giamGia] [int] NULL,
	[VAT] [int] NULL,
	[tongTien] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[maHD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhuyenMai] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhuyenMai](
	[maKhuyenMai] [int] IDENTITY(1,1) NOT NULL,
	[tenKhuyenMai] [nvarchar](200) NOT NULL,
	[giaKhuyenMai] [float] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maKhuyenMai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MonAn] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MonAn](
	[maMon] [int] IDENTITY(1,1) NOT NULL,
	[tenMon] [nvarchar](100) NULL,
	[maLoaiMon] [int] NULL,
	[donVi] [nvarchar](50) NULL,
	[giaTien] [float] NULL,
	[soLuong] [int] NULL,
	[imageMonAn] [varchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[maMon] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[maNV] [int] IDENTITY(1,1) NOT NULL,
	[tenNV] [nvarchar](100) NULL,
	[ngaySinh] [date] NULL,
	[gioiTinh] [nvarchar](5) NULL,
	[diaChi] [nvarchar](100) NULL,
	[soDT] [nvarchar](11) NULL,
	[chucVu] [nvarchar](100) NULL,
	[imageNV] [varchar](200) NULL,
	[tenDangNhap] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[maNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaiKhoan] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoan](
	[tenDangNhap] [varchar](100) NOT NULL,
	[tenHienThi] [nvarchar](100) NOT NULL,
	[matKhau] [varchar](100) NOT NULL,
	[loaiTaiKhoan] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[tenDangNhap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[DanhMucMon] ADD  DEFAULT (N'Chưa đặt tên danh mục') FOR [tenLoai]
GO
ALTER TABLE [dbo].[HoaDonTT] ADD  DEFAULT (getdate()) FOR [ngayXuatHoaDon]
GO
ALTER TABLE [dbo].[MonAn] ADD  DEFAULT (N'Chưa đặt tên món') FOR [tenMon]
GO
ALTER TABLE [dbo].[MonAn] ADD  DEFAULT ((0)) FOR [giaTien]
GO
ALTER TABLE [dbo].[TaiKhoan] ADD  DEFAULT (N'') FOR [tenHienThi]
GO
ALTER TABLE [dbo].[TaiKhoan] ADD  DEFAULT ((0)) FOR [matKhau]
GO
ALTER TABLE [dbo].[TaiKhoan] ADD  DEFAULT ((0)) FOR [loaiTaiKhoan]
GO
ALTER TABLE [dbo].[ChiTietHD]  WITH CHECK ADD FOREIGN KEY([maMonAn])
REFERENCES [dbo].[MonAn] ([maMon])
GO
ALTER TABLE [dbo].[ChiTietHD]  WITH CHECK ADD FOREIGN KEY([maHoaDon])
REFERENCES [dbo].[HoaDonTT] ([maHD])
GO
ALTER TABLE [dbo].[HoaDonTT]  WITH CHECK ADD FOREIGN KEY([maNhanVien])
REFERENCES [dbo].[NhanVien] ([maNV])
GO
ALTER TABLE [dbo].[MonAn]  WITH CHECK ADD FOREIGN KEY([maLoaiMon])
REFERENCES [dbo].[DanhMucMon] ([maLoai])
GO
USE [master]
GO
ALTER DATABASE [DoAnCoSoQLTCF] SET  READ_WRITE 
GO
