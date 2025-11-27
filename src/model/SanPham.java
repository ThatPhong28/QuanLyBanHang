
package model;

public class SanPham {
    private String maSp;
    private String tenSp;
    private String loai;
    private double gia;
 

    public SanPham() {
    }
    
    
    public SanPham(String maSp, String tenSp, String loai, double gia) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.loai = loai;
        this.gia = gia;
    }

    public String getMaSP() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
    @Override
    public String toString(){
        return maSp + " - " + tenSp + "(" + loai + ")" + gia + "VND";
    }

   
}
