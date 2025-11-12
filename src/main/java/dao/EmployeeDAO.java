package dao;

import config.DBConnection;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private String jdbcURL = "jdbc:sqlserver://localhost:1433;databaseName=HRMSystem;encrypt=true;trustServerCertificate=true";
    private String jdbcUsername = "sa";
    private String jdbcPassword = "123";

    // Các câu truy vấn chính
    private static final String SELECT_ALL =
            "SELECT e.*, d.dept_name, p.position_name " +
                    "FROM Employees e " +
                    "LEFT JOIN Departments d ON e.dept_id = d.dept_id " +
                    "LEFT JOIN Positions p ON e.position_id = p.position_id";

    private static final String SELECT_BY_ID =
            "SELECT * FROM Employees WHERE emp_id = ?";

    private static final String INSERT_EMP =
            "INSERT INTO Employees (user_id, name, email, phone, dob, hire_date, salary, dept_id, position_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_EMP =
            "UPDATE Employees SET name=?, email=?, phone=?, dob=?, hire_date=?, salary=?, dept_id=?, position_id=? WHERE emp_id=?";

    private static final String DELETE_EMP =
            "DELETE FROM Employees WHERE emp_id=?";

    private static final String INSERT_USER =
            "INSERT INTO Users (email, password, role_id) VALUES (?, ?, ?)";

    private static final String UPDATE_USER =
            "UPDATE Users SET email=?, role_id=? WHERE user_id=?";

    private static final String DELETE_USER =
            "DELETE FROM Users WHERE user_id=(SELECT user_id FROM Employees WHERE emp_id=?)";

    protected Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLServer Driver not found!", e);
        }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Kiểm tra email tồn tại
    private boolean isEmailExists(Connection conn, String email) throws SQLException {
        String sql = "SELECT 1 FROM Users WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    // Lấy toàn bộ nhân viên
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employees.add(mapEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Tìm kiếm nhân viên theo tên hoặc ID, có sắp xếp
    public List<Employee> searchEmployees(String keyword, String sortOrder) {
        List<Employee> list = new ArrayList<>();
        String sql =
                "SELECT e.*, d.dept_name, p.position_name " +
                        "FROM Employees e " +
                        "LEFT JOIN Departments d ON e.dept_id = d.dept_id " +
                        "LEFT JOIN Positions p ON e.position_id = p.position_id " +
                        "WHERE e.name LIKE ? OR CAST(e.emp_id AS NVARCHAR) LIKE ? " +
                        "ORDER BY e.hire_date " + ("asc".equalsIgnoreCase(sortOrder) ? "ASC" : "DESC");

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy nhân viên theo ID
    public Employee getEmployeeById(int id) {
        Employee emp = null;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                emp = mapEmployee(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emp;
    }

    // Thêm nhân viên mới (và tài khoản User)
    public void insertEmployee(Employee emp) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            int userId = 0;

            // Kiểm tra email trùng
            if (isEmailExists(conn, emp.getEmail())) {
                throw new SQLException("Email already exists: " + emp.getEmail());
            }

            // Insert Users
            try (PreparedStatement psUser = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
                psUser.setString(1, emp.getEmail());
                psUser.setString(2, "123456");  // mật khẩu mặc định
                psUser.setInt(3, (emp.getRole_id() != 0) ? emp.getRole_id() : 101);
                psUser.executeUpdate();

                ResultSet rs = psUser.getGeneratedKeys();
                if (rs.next()) {
                    userId = rs.getInt(1);
                }
            }

            // Insert Employees
            try (PreparedStatement psEmp = conn.prepareStatement(INSERT_EMP)) {
                psEmp.setInt(1, userId);
                psEmp.setString(2, emp.getName());
                psEmp.setString(3, emp.getEmail());
                psEmp.setString(4, emp.getPhone());
                psEmp.setDate(5, emp.getDob() != null ? new java.sql.Date(emp.getDob().getTime()) : null);
                psEmp.setDate(6, emp.getHire_date() != null ? new java.sql.Date(emp.getHire_date().getTime()) : null);
                psEmp.setBigDecimal(7, emp.getSalary());
                psEmp.setInt(8, emp.getDept_id());
                psEmp.setInt(9, emp.getPosition_id());
                psEmp.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật nhân viên
    public void updateEmployee(Employee emp) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psUser = conn.prepareStatement(UPDATE_USER)) {
                psUser.setString(1, emp.getEmail());
                psUser.setInt(2, (emp.getRole_id() != 0) ? emp.getRole_id() : 101);
                psUser.setInt(3, emp.getUser_id());
                psUser.executeUpdate();
            }

            try (PreparedStatement psEmp = conn.prepareStatement(UPDATE_EMP)) {
                psEmp.setString(1, emp.getName());
                psEmp.setString(2, emp.getEmail());
                psEmp.setString(3, emp.getPhone());
                psEmp.setDate(4, emp.getDob() != null ? new java.sql.Date(emp.getDob().getTime()) : null);
                psEmp.setDate(5, emp.getHire_date() != null ? new java.sql.Date(emp.getHire_date().getTime()) : null);
                psEmp.setBigDecimal(6, emp.getSalary());
                psEmp.setInt(7, emp.getDept_id());
                psEmp.setInt(8, emp.getPosition_id());
                psEmp.setInt(9, emp.getEmp_id());
                psEmp.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa nhân viên và user tương ứng
    public void deleteEmployee(int empId) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psEmp = conn.prepareStatement(DELETE_EMP)) {
                psEmp.setInt(1, empId);
                psEmp.executeUpdate();
            }
            try (PreparedStatement psUser = conn.prepareStatement(DELETE_USER)) {
                psUser.setInt(1, empId);
                psUser.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Map dữ liệu từ ResultSet -> Object
    private Employee mapEmployee(ResultSet rs) throws SQLException {
        Employee emp = new Employee();
        emp.setEmp_id(rs.getInt("emp_id"));
        emp.setUser_id(rs.getInt("user_id"));
        emp.setName(rs.getString("name"));
        emp.setEmail(rs.getString("email"));
        emp.setPhone(rs.getString("phone"));
        emp.setDob(rs.getDate("dob"));
        emp.setHire_date(rs.getDate("hire_date"));
        emp.setSalary(rs.getBigDecimal("salary"));
        emp.setDept_id(rs.getInt("dept_id"));
        emp.setPosition_id(rs.getInt("position_id"));
        try {
            emp.setDept_name(rs.getString("dept_name"));
            emp.setPosition_name(rs.getString("position_name"));
        } catch (SQLException ignored) {}
        return emp;
    }
    // 🔹 Lấy thông tin nhân viên theo user_id
    public Employee getEmployeeByUserId(int userId) {
        String sql = """
        SELECT e.emp_id, e.user_id, e.name, e.email, e.phone, e.salary,
               d.dept_name, p.position_name
        FROM Employees e
        LEFT JOIN Departments d ON e.dept_id = d.dept_id
        LEFT JOIN Positions p ON e.position_id = p.position_id
        WHERE e.user_id = ?
    """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Employee emp = new Employee();
                emp.setEmp_id(rs.getInt("emp_id"));
                emp.setUser_id(rs.getInt("user_id"));
                emp.setName(rs.getString("name"));
                emp.setEmail(rs.getString("email"));
                emp.setPhone(rs.getString("phone"));
                emp.setSalary(rs.getBigDecimal("salary"));
                emp.setDept_name(rs.getString("dept_name"));
                emp.setPosition_name(rs.getString("position_name"));
                return emp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 🔹 Lấy danh sách nhân viên theo mã phòng ban (dept_id)
    public List<Employee> getEmployeesByDepartment(int deptId) {
        List<Employee> employees = new ArrayList<>();

        String sql = """
        SELECT e.emp_id, e.user_id, e.name, e.email, e.phone, e.salary,
               d.dept_name, p.position_name
        FROM Employees e
        LEFT JOIN Departments d ON e.dept_id = d.dept_id
        LEFT JOIN Positions p ON e.position_id = p.position_id
        WHERE e.dept_id = ?
    """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, deptId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmp_id(rs.getInt("emp_id"));
                emp.setUser_id(rs.getInt("user_id"));
                emp.setName(rs.getString("name"));
                emp.setEmail(rs.getString("email"));
                emp.setPhone(rs.getString("phone"));
                emp.setSalary(rs.getBigDecimal("salary"));
                emp.setDept_name(rs.getString("dept_name"));
                emp.setPosition_name(rs.getString("position_name"));
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

}
