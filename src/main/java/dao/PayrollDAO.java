package dao;

import model.Payroll;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PayrollDAO - Data Access Object cho bảng Payroll
 * Hỗ trợ: CRUD, tìm kiếm theo ID nhân viên, lọc theo tháng/năm, sắp xếp theo lương
 */
public class PayrollDAO {

    private String jdbcURL = "jdbc:sqlserver://localhost:1433;databaseName=HRMSystem;encrypt=true;trustServerCertificate=true";
    private String jdbcUsername = "sa";
    private String jdbcPassword = "123";

    private static final String SELECT_ALL = "SELECT *, (base_salary + bonus - deductions) AS net_salary FROM Payroll";
    private static final String SELECT_BY_ID = "SELECT *, (base_salary + bonus - deductions) AS net_salary FROM Payroll WHERE payroll_id=?";
    private static final String INSERT = "INSERT INTO Payroll(emp_id, pay_date, base_salary, bonus, deductions) VALUES(?,?,?,?,?)";
    private static final String UPDATE = "UPDATE Payroll SET emp_id=?, pay_date=?, base_salary=?, bonus=?, deductions=? WHERE payroll_id=?";
    private static final String DELETE = "DELETE FROM Payroll WHERE payroll_id=?";

    protected Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public List<Payroll> getAllPayrolls() {
        List<Payroll> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapPayroll(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Payroll getPayrollById(int id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapPayroll(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertPayroll(Payroll p) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            ps.setInt(1, p.getEmp_id());
            ps.setDate(2, p.getPay_date());
            ps.setBigDecimal(3, p.getBase_salary());
            ps.setBigDecimal(4, p.getBonus());
            ps.setBigDecimal(5, p.getDeductions());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePayroll(Payroll p) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setInt(1, p.getEmp_id());
            ps.setDate(2, p.getPay_date());
            ps.setBigDecimal(3, p.getBase_salary());
            ps.setBigDecimal(4, p.getBonus());
            ps.setBigDecimal(5, p.getDeductions());
            ps.setInt(6, p.getPayroll_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePayroll(int id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 🔹 Tìm kiếm theo emp_id, tháng và sắp xếp */
    public List<Payroll> searchPayroll(Integer empId, String monthYear, String sort) {
        List<Payroll> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT *, (base_salary + bonus - deductions) AS net_salary FROM Payroll WHERE 1=1"
        );

        if (empId != null) sql.append(" AND emp_id = ?");
        if (monthYear != null && !monthYear.isEmpty()) sql.append(" AND FORMAT(pay_date, 'yyyy-MM') = ?");
        if (sort != null && (sort.equalsIgnoreCase("asc") || sort.equalsIgnoreCase("desc")))
            sql.append(" ORDER BY net_salary ").append(sort);

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (empId != null) ps.setInt(index++, empId);
            if (monthYear != null && !monthYear.isEmpty()) ps.setString(index++, monthYear);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapPayroll(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Payroll mapPayroll(ResultSet rs) throws SQLException {
        Payroll p = new Payroll();
        p.setPayroll_id(rs.getInt("payroll_id"));
        p.setEmp_id(rs.getInt("emp_id"));
        p.setPay_date(rs.getDate("pay_date"));
        p.setBase_salary(rs.getBigDecimal("base_salary"));
        p.setBonus(rs.getBigDecimal("bonus"));
        p.setDeductions(rs.getBigDecimal("deductions"));
        p.setNet_salary(rs.getBigDecimal("net_salary"));
        return p;
    }
}
