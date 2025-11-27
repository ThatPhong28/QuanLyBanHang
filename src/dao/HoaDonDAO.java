package dao;

import java.sql.*;
import model.HoaDon;
import java.util.*;

public class HoaDonDAO {

    // Lưu hóa đơn vào Access
    public static void luuHoaDon(HoaDon hd) {
        try (Connection conn = KetNoiAccess.getConnection()) {
            
            String sql = "INSERT INTO hoadon (maHD, ngayLap, tongTien) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hd.getMaHD());
            ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            ps.setDouble(3, hd.getTongTien());

            int rows = ps.executeUpdate();
            System.out.println("Da luu HD: " + hd.getMaHD() + " | Rows affected: " + rows);

        } catch (SQLException e) {
            System.out.println("Loi SQL sau khi luu HD: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Loi khac sau khi luu HD: " + e.getMessage());
        }
    }

    //Lấy danh sách hóa đơn để kiểm tra sau
    public static List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();
        try (Connection conn = KetNoiAccess.getConnection()) {
            String sql = "SELECT * FROM hoadon";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getString("maHD"),
                    rs.getDate("ngayLap"),
                    rs.getDouble("tongTien")
                );
                list.add(hd);
            }
        } catch (Exception e) {
            System.out.println("Loi lay danh sach HD: " + e.getMessage());
        }
        return list;
    }
    
    // LẤY HÓA ĐƠN TRONG NGÀY

    public static List<HoaDon> getAllToday() {

    List<HoaDon> list = new ArrayList<>();

    try (Connection conn = KetNoiAccess.getConnection()) {

        
        String sql =
            "SELECT * FROM hoadon " +
            "WHERE FORMAT(ngayLap, 'yyyy-mm-dd') = FORMAT(NOW(), 'yyyy-mm-dd')";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new HoaDon(
                rs.getString("maHD"),
                rs.getDate("ngayLap"),
                rs.getDouble("tongTien")
            ));
        }

    } catch (Exception e) {
        System.out.println("Loi getAllToday: " + e.getMessage());
    }

    return list;
}

}
