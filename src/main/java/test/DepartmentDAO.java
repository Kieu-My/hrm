package test;

import model.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private String jdbcURL = "jdbc:sqlserver://localhost:1433;databaseName=HRMSystem;encrypt=true;trustServerCertificate=true";
    private String jdbcUsername = "sa";
    private String jdbcPassword = "123";

    private static final String SELECT_ALL = "SELECT * FROM Departments";

    // Kết nối cơ sở dữ liệu
    protected Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // Đảm bảo driver được load
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Không tìm thấy driver SQL Server JDBC!");
            e.printStackTrace();
        }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Lấy tất cả phòng ban
    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL)) {

            System.out.println("✅ Kết nối DB thành công, thực thi query: " + SELECT_ALL);
            ResultSet rs = ps.executeQuery();

            int count = 0;
            while (rs.next()) {
                Department dept = new Department(
                        rs.getInt("dept_id"),
                        rs.getString("dept_name")
                );
                list.add(dept);
                count++;
            }
            System.out.println("✅ Lấy được " + count + " phòng ban từ database.");

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi truy vấn danh sách phòng ban:");
            e.printStackTrace();
        }
        return list;
    }

    // Test main để chạy nhanh
    public static void main(String[] args) {
        DepartmentDAO dao = new DepartmentDAO();
        List<Department> list = dao.getAllDepartments();
        System.out.println("=== Kết quả phòng ban ===");
        for (Department d : list) {
            System.out.println(d.getDept_id() + " - " + d.getDept_name());
        }

        if (list.isEmpty()) {
            System.out.println("⚠️ Không có dữ liệu nào trả về. Kiểm tra bảng Departments và kết nối DB.");
        }
    }
}
