package model;

import java.sql.Timestamp;

public class User {
    private int user_id;
    private String email;
    private String password;
    private int role_id;
    private Timestamp created_at;

    // 🔹 Thuộc tính mở rộng từ JOIN
    private int emp_id;
    private String emp_name;
    private String role_name;

    // ✅ Constructor mặc định
    public User() {}

    // ✅ Constructor đầy đủ
    public User(int user_id, String email, String password, int role_id, Timestamp created_at,
                int emp_id, String emp_name, String role_name) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.role_id = role_id;
        this.created_at = created_at;
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.role_name = role_name;
    }

    // ✅ Getter & Setter
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
