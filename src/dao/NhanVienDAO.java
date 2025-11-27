package dao;

import model.NhanVien;
import java.sql.*;

public class NhanVienDAO {

    public NhanVien checkLogin(String user, String pass) {

        String sql = "SELECT * FROM NhanVien WHERE username=? AND password=?";

        try (Connection cn = KetNoiAccess.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new NhanVien(
                        rs.getString("MaNV"),
                        rs.getString("TenNV"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }

        } catch (Exception e) {
            System.out.println("Lỗi login: " + e.getMessage());
        }

        return null;
    }
}
