package dao;

import model.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private String jdbcURL = "jdbc:sqlserver://localhost:1433;databaseName=HRMSystem;encrypt=true;trustServerCertificate=true";
    private String jdbcUsername = "sa";
    private String jdbcPassword = "123";

    private static final String SELECT_ALL = "SELECT * FROM Departments";
    private static final String SELECT_BY_ID = "SELECT * FROM Departments WHERE dept_id=?";
    private static final String INSERT_SQL = "INSERT INTO Departments (dept_name) VALUES (?)";
    private static final String UPDATE_SQL = "UPDATE Departments SET dept_name=? WHERE dept_id=?";
    private static final String DELETE_SQL = "DELETE FROM Departments WHERE dept_id=?";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Lấy danh sách tất cả phòng ban
    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Department(
                        rs.getInt("dept_id"),
                        rs.getString("dept_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy phòng ban theo ID
    public Department getDepartmentById(int id) {
        Department dept = null;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dept = new Department(rs.getInt("dept_id"), rs.getString("dept_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dept;
    }

    // Thêm mới
    public boolean insertDepartment(Department dept) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, dept.getDept_name());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật
    public boolean updateDepartment(Department dept) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, dept.getDept_name());
            ps.setInt(2, dept.getDept_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa
    public boolean deleteDepartment(int id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
