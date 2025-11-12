package dao;

import config.DBConnection;
import model.LeaveRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveDAO {

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    /** 🔹 Lấy tất cả đơn nghỉ (cho Admin) */
    public List<LeaveRequest> getAllRequests() {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM Leaves ORDER BY leave_id DESC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setLeave_id(rs.getInt("leave_id"));
                lr.setEmp_id(rs.getInt("emp_id"));
                lr.setStart_date(rs.getDate("start_date"));
                lr.setEnd_date(rs.getDate("end_date"));
                lr.setReason(rs.getString("reason"));
                lr.setStatus(rs.getString("status"));
                list.add(lr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** 🔹 Lấy đơn nghỉ theo ID */
    public LeaveRequest getLeaveById(int leave_id) {
        String sql = "SELECT * FROM Leaves WHERE leave_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, leave_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setLeave_id(rs.getInt("leave_id"));
                lr.setEmp_id(rs.getInt("emp_id"));
                lr.setStart_date(rs.getDate("start_date"));
                lr.setEnd_date(rs.getDate("end_date"));
                lr.setReason(rs.getString("reason"));
                lr.setStatus(rs.getString("status"));
                return lr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 🔹 Lấy tất cả đơn nghỉ theo nhân viên */
    public List<LeaveRequest> getLeavesByEmpId(int emp_id) {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM Leaves WHERE emp_id=? ORDER BY leave_id DESC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, emp_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setLeave_id(rs.getInt("leave_id"));
                lr.setEmp_id(rs.getInt("emp_id"));
                lr.setStart_date(rs.getDate("start_date"));
                lr.setEnd_date(rs.getDate("end_date"));
                lr.setReason(rs.getString("reason"));
                lr.setStatus(rs.getString("status"));
                list.add(lr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** 🔹 Thêm đơn nghỉ */
    public void insertLeave(LeaveRequest leave) {
        String sql = "INSERT INTO Leaves(emp_id, start_date, end_date, reason, status) VALUES(?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, leave.getEmp_id());
            ps.setDate(2, leave.getStart_date());
            ps.setDate(3, leave.getEnd_date());
            ps.setString(4, leave.getReason());
            ps.setString(5, leave.getStatus());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 🔹 Cập nhật đơn nghỉ */
    public void updateLeave(LeaveRequest leave) {
        String sql = "UPDATE Leaves SET start_date=?, end_date=?, reason=?, status=? WHERE leave_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, leave.getStart_date());
            ps.setDate(2, leave.getEnd_date());
            ps.setString(3, leave.getReason());
            ps.setString(4, leave.getStatus());
            ps.setInt(5, leave.getLeave_id());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 🔹 Admin duyệt / từ chối */
    public void updateStatus(int leave_id, String status) {
        String sql = "UPDATE Leaves SET status=? WHERE leave_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, leave_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 🔹 Xóa đơn nghỉ */
    public void deleteLeave(int leave_id) {
        String sql = "DELETE FROM Leaves WHERE leave_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, leave_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
