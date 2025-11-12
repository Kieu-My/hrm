package dao;

import config.DBConnection;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /** 🔹 Lấy tất cả Users */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.user_id, u.email, u.password, u.role_id, " +
                "e.emp_id, e.name AS emp_name, r.role_name, u.created_at " +
                "FROM Users u " +
                "JOIN Employees e ON u.user_id = e.user_id " +
                "JOIN Roles r ON u.role_id = r.role_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapUser(rs));
            }

        } catch (Exception e) {
            System.err.println("❌ getAllUsers() error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    /** 🔹 Lấy User theo ID */
    public User getUserById(int id) {
        String sql = "SELECT u.user_id, u.email, u.password, u.role_id, " +
                "e.emp_id, e.name AS emp_name, r.role_name, u.created_at " +
                "FROM Users u " +
                "JOIN Employees e ON u.user_id = e.user_id " +
                "JOIN Roles r ON u.role_id = r.role_id " +
                "WHERE u.user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }

        } catch (Exception e) {
            System.err.println("❌ getUserById() error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /** 🔹 Thêm User mới */
    public boolean addUser(User user) {
        String sql = "INSERT INTO Users(email, password, role_id, created_at) VALUES (?, ?, ?, GETDATE())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRole_id());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                // cập nhật Employees.emp_id với user_id vừa tạo
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int newUserId = rs.getInt(1);
                        linkEmployeeToUser(user.getEmp_id(), newUserId);
                    }
                }
                return true;
            }

        } catch (Exception e) {
            System.err.println("❌ addUser() error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /** 🔹 Gán user_id cho employee */
    private void linkEmployeeToUser(int empId, int userId) {
        String sql = "UPDATE Employees SET user_id = ? WHERE emp_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, empId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ linkEmployeeToUser() error: " + e.getMessage());
        }
    }

    /** 🔹 Cập nhật User (có password) */
    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET email=?, password=?, role_id=? WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRole_id());
            ps.setInt(4, user.getUser_id());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                updateEmployeeLink(user.getEmp_id(), user.getUser_id());
                return true;
            }

        } catch (Exception e) {
            System.err.println("❌ updateUser() error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /** 🔹 Cập nhật User (không đổi password) */
    public boolean updateUserNoPassword(User user) {
        String sql = "UPDATE Users SET email=?, role_id=? WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getEmail());
            ps.setInt(2, user.getRole_id());
            ps.setInt(3, user.getUser_id());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                updateEmployeeLink(user.getEmp_id(), user.getUser_id());
                return true;
            }

        } catch (Exception e) {
            System.err.println("❌ updateUserNoPassword() error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /** 🔹 Cập nhật link giữa Employee và User */
    private void updateEmployeeLink(int empId, int userId) {
        String sql = "UPDATE Employees SET user_id=? WHERE emp_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, empId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ updateEmployeeLink() error: " + e.getMessage());
        }
    }

    /** 🔹 Đổi mật khẩu */
    public boolean changePassword(int userId, String newPassword) {
        String sql = "UPDATE Users SET password=? WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("❌ changePassword() error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /** 🔹 Xóa User */
    public boolean deleteUser(int userId) {
        // Xóa liên kết trong Employees trước
        String unlinkSql = "UPDATE Employees SET user_id=NULL WHERE user_id=?";
        String deleteSql = "DELETE FROM Users WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(unlinkSql);
             PreparedStatement ps2 = conn.prepareStatement(deleteSql)) {

            ps1.setInt(1, userId);
            ps1.executeUpdate();

            ps2.setInt(1, userId);
            return ps2.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("❌ deleteUser() error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /** 🔹 Map dữ liệu từ ResultSet sang User */
    private User mapUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUser_id(rs.getInt("user_id"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setRole_id(rs.getInt("role_id"));
        u.setEmp_id(rs.getInt("emp_id"));
        u.setEmp_name(rs.getString("emp_name"));
        u.setRole_name(rs.getString("role_name"));
        u.setCreated_at(rs.getTimestamp("created_at"));
        return u;
    }
    /** 🔹 Lấy User theo Email */
    public User getUserByEmail(String email) {
        String sql = "SELECT u.user_id, u.email, u.password, u.role_id, " +
                "e.emp_id, e.name AS emp_name, r.role_name, u.created_at " +
                "FROM Users u " +
                "JOIN Employees e ON u.user_id = e.user_id " +
                "JOIN Roles r ON u.role_id = r.role_id " +
                "WHERE u.email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }

        } catch (Exception e) {
            System.err.println("❌ getUserByEmail() error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
