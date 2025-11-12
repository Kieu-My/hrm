package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection
 * ----------------------
 * - Dùng để quản lý kết nối tới SQL Server.
 * - Thông tin cấu hình (URL, username, password) có thể lấy từ file cấu hình .properties.
 */
public class DBConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=HRMsystem;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";       // thay bằng user SQL Server của bạn
    private static final String PASSWORD = "123"; // thay bằng mật khẩu SQL Server của bạn

    /**
     * Hàm mở kết nối tới SQL Server
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load driver JDBC cho SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("❌ Không tìm thấy JDBC Driver cho SQL Server", e);
        }
    }

    /**
     * Đóng kết nối an toàn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Hàm kiểm tra kết nối (dùng để test)
     */
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Kết nối SQL Server thành công!");
            } else {
                System.out.println("❌ Kết nối SQL Server thất bại!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
