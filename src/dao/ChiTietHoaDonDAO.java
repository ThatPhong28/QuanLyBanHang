package dao;

import java.sql.*;
import java.util.ArrayList;
import model.ChiTietHoaDon;
import java.util.List;

public class ChiTietHoaDonDAO {

    public static void luuChiTietHoaDon(List<ChiTietHoaDon> dsCT) {
        if (dsCT == null || dsCT.isEmpty()) {
            System.out.println("Khong co chi tiet HD de luu!");
            return;
        }

        try (Connection conn = KetNoiAccess.getConnection()) {
         
           String sql = "INSERT INTO ChiTietHoaDon (maHD, maSp, soLuong, thanhTien) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            int count = 0;
            for (ChiTietHoaDon ct : dsCT) {
                System.out.println("Luu chi tiet: " +
                    ct.getMaHD() + " | " + ct.getmaSp() + " | " +
                    ct.getSoLuong() + " | " + ct.getThanhTien());

                ps.setString(1, ct.getMaHD());
                ps.setString(2, ct.getmaSp());
                ps.setInt(3, ct.getSoLuong());
                ps.setDouble(4, ct.getThanhTien());
                ps.executeUpdate();
                count++;
            }

            System.out.println("Da luu " + count + " chi tiet HD vao CSDL thanh cong!");

        } catch (SQLException e) {
            System.err.println("Loi SQL sau khi luu CTHD: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("LOI khac khi luu CTHD: " + e.getMessage());
        }
    }
    
    
    public static List<ChiTietHoaDon> getByMaHD(String maHD) {

    List<ChiTietHoaDon> list = new ArrayList<>();

    try (Connection conn = KetNoiAccess.getConnection()) {

        String sql = "SELECT * FROM chitiethoadon WHERE maHD = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, maHD);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new ChiTietHoaDon(
                rs.getString("maHD"),
                rs.getString("maSp"),
                rs.getInt("soLuong"),
                rs.getDouble("thanhTien")
            ));
        }

    } catch (Exception e) {
        System.out.println("Loi getByMaHD: " + e.getMessage());
    }

    return list;
}

}
