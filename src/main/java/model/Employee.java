package model;

import java.math.BigDecimal;
import java.util.Date;

public class Employee {
    private int emp_id;
    private int user_id;
    private int role_id;         // 🔹 Lấy từ bảng Roles
    private String name;
    private String email;
    private String phone;
    private Date dob;
    private Date hire_date;
    private BigDecimal salary;
    private int dept_id;
    private int position_id;

    // Thông tin mở rộng khi JOIN
    private String dept_name;
    private String position_name;
    private String role_name;

    private Department department;   // Quan hệ với bảng Department
    private Position position;       // Quan hệ với bảng Position
    private Role role;               // Quan hệ với bảng Role (nếu cần)

    // Constructor mặc định
    public Employee() {}

    // Constructor đầy đủ
    public Employee(int emp_id, int user_id, String name, String email, String phone,
                    Date dob, Date hire_date, BigDecimal salary,
                    int dept_id, int position_id,
                    String dept_name, String position_name, int role_id) {
        this.emp_id = emp_id;
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.hire_date = hire_date;
        this.salary = salary;
        this.dept_id = dept_id;
        this.position_id = position_id;
        this.dept_name = dept_name;
        this.position_name = position_name;
        this.role_id = role_id;
    }

    // ===== GETTERS & SETTERS =====
    public int getEmp_id() { return emp_id; }
    public void setEmp_id(int emp_id) { this.emp_id = emp_id; }

    public int getUser_id() { return user_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }

    public int getRole_id() { return role_id; }
    public void setRole_id(int role_id) { this.role_id = role_id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }

    public Date getHire_date() { return hire_date; }
    public void setHire_date(Date hire_date) { this.hire_date = hire_date; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public int getDept_id() { return dept_id; }
    public void setDept_id(int dept_id) { this.dept_id = dept_id; }

    public int getPosition_id() { return position_id; }
    public void setPosition_id(int position_id) { this.position_id = position_id; }

    public String getDept_name() { return dept_name; }
    public void setDept_name(String dept_name) { this.dept_name = dept_name; }

    public String getPosition_name() { return position_name; }
    public void setPosition_name(String position_name) { this.position_name = position_name; }

    public String getRole_name() { return role_name; }
    public void setRole_name(String role_name) { this.role_name = role_name; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
