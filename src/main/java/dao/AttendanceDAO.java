package dao;

import model.Attendance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    private String jdbcURL = "jdbc:sqlserver://localhost:1433;databaseName=HRMSystem;encrypt=true;trustServerCertificate=true";
    private String jdbcUsername = "sa";
    private String jdbcPassword = "123";

    // SQL Statements
    private static final String SELECT_ALL = "SELECT * FROM Attendance";
    private static final String SELECT_BY_ID = "SELECT * FROM Attendance WHERE attendance_id = ?";
    private static final String INSERT = "INSERT INTO Attendance (emp_id, work_date, check_in, check_out, status) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE Attendance SET emp_id = ?, work_date = ?, check_in = ?, check_out = ?, status = ? WHERE attendance_id = ?";
    private static final String DELETE = "DELETE FROM Attendance WHERE attendance_id = ?";

    public AttendanceDAO() {}

    protected Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLServerDriver not found", e);
        }
    }

    // ✅ Lấy tất cả bản ghi
    public List<Attendance> getAllAttendance() {
        List<Attendance> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Attendance att = new Attendance();
                att.setAttendanceId(rs.getInt("attendance_id"));
                att.setEmpId(rs.getInt("emp_id"));
                att.setWorkDate(rs.getDate("work_date"));
                att.setCheckIn(rs.getTime("check_in"));
                att.setCheckOut(rs.getTime("check_out"));
                att.setStatus(rs.getString("status"));
                list.add(att);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Tìm kiếm theo mã NV hoặc ngày làm việc
    public List<Attendance> searchAttendance(String empId, String workDate) {
        List<Attendance> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Attendance WHERE 1=1");

        if (empId != null && !empId.isEmpty()) {
            sql.append(" AND emp_id LIKE ?");
        }
        if (workDate != null && !workDate.isEmpty()) {
            sql.append(" AND work_date = ?");
        }

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (empId != null && !empId.isEmpty()) {
                ps.setString(index++, "%" + empId + "%");
            }
            if (workDate != null && !workDate.isEmpty()) {
                ps.setString(index++, workDate);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Attendance att = new Attendance();
                att.setAttendanceId(rs.getInt("attendance_id"));
                att.setEmpId(rs.getInt("emp_id"));
                att.setWorkDate(rs.getDate("work_date"));
                att.setCheckIn(rs.getTime("check_in"));
                att.setCheckOut(rs.getTime("check_out"));
                att.setStatus(rs.getString("status"));
                list.add(att);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Lấy theo ID
    public Attendance getAttendanceById(int id) {
        Attendance att = null;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                att = new Attendance();
                att.setAttendanceId(rs.getInt("attendance_id"));
                att.setEmpId(rs.getInt("emp_id"));
                att.setWorkDate(rs.getDate("work_date"));
                att.setCheckIn(rs.getTime("check_in"));
                att.setCheckOut(rs.getTime("check_out"));
                att.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return att;
    }

    // ✅ Thêm mới
    public boolean insertAttendance(Attendance att) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            ps.setInt(1, att.getEmpId());
            ps.setDate(2, att.getWorkDate());
            ps.setTime(3, att.getCheckIn());
            ps.setTime(4, att.getCheckOut());
            ps.setString(5, att.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Cập nhật
    public boolean updateAttendance(Attendance att) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setInt(1, att.getEmpId());
            ps.setDate(2, att.getWorkDate());
            ps.setTime(3, att.getCheckIn());
            ps.setTime(4, att.getCheckOut());
            ps.setString(5, att.getStatus());
            ps.setInt(6, att.getAttendanceId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Xóa
    public boolean deleteAttendance(int id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
