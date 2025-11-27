package dao;

import java.sql.*;
import java.util.*;
import model.SanPham;

public class SanPhamDAO {
    public static List<SanPham> getAllSanPham() {
        List<SanPham> list = new ArrayList<>();
        try (Connection conn = KetNoiAccess.getConnection()) {
            String sql = "SELECT * FROM sanpham";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String ma = rs.getString("maSp");
                String ten = rs.getString("tenSp");
                String loai = rs.getString("loai");
                double gia = rs.getDouble("gia");

                list.add(new SanPham(ma, ten, loai, gia));
            }
        } catch (Exception e) {
            System.out.println("Loi doc du lieu san pham: " + e.getMessage());
        }
        return list;
    }
    public static SanPham getById(String maSP) {
    SanPham sp = null;
    try (Connection conn = KetNoiAccess.getConnection()) {

        String sql = "SELECT * FROM sanpham WHERE maSP = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, maSP);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            sp = new SanPham(
                    rs.getString("maSP"),
                    rs.getString("tenSP"),
                    rs.getString("loai"),
                    rs.getDouble("gia")
            );
        }

    } catch (Exception e) {
        System.out.println("Loi getById SanPham: " + e.getMessage());
    }

    return sp;
}


    // Hàm này giao diện đang gọi,phải hoạt động chứ không được throw lỗi
    public static ArrayList<SanPham> getAll() {
        return new ArrayList<>(getAllSanPham());
    }
}
