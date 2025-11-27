package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KetNoiAccess {
    private static final String DB_URL = "jdbc:ucanaccess://D:\\BaitapJava\\QuanLyBanHang\\data.accdb";   
    private static Connection conn = null;
    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URL);
                System.out.println("Ket Noi Aceess thanh cong!");
            }
        } catch (SQLException e) {
            System.out.println("Loi ket noi CSDL: " + e.getMessage());
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Da dong ket noi.");
            }
        } catch (SQLException e) {
            System.out.println("Loi khi dong ket noi: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Connection testConn = KetNoiAccess.getConnection();
        KetNoiAccess.closeConnection();
    }
}
