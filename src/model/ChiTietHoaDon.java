package model;

public class ChiTietHoaDon {
    private String maHD;
    private String maSp;
    private int soLuong;
    private double thanhTien;

    
    public ChiTietHoaDon(String maHD, String maSp, int soLuong, double thanhTien) {
        this.maHD = maHD;
        this.maSp = maSp;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    
    public ChiTietHoaDon() {
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getmaSp() {
        return maSp;
    }

    public void setmaSp(String maSp) {
        this.maSp = maSp;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
}
