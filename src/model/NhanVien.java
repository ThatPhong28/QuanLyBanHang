package model;
public class NhanVien {
    private String maNV;
    private String tenNV;
    private String username;
    private String password;
    public NhanVien() {}
    public NhanVien(String maNV, String tenNV, 
            String username, String password) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.username = username;
        this.password = password;
    }
    public String getMaNV() {
        return maNV;
    }
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    public String getTenNV() {
        return tenNV;
    }
    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
