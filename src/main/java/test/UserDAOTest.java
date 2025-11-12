package test;

import dao.UserDAO;
import dao.RoleDAO;
import dao.EmployeeDAO;
import model.User;
import model.Role;
import model.Employee;

import java.util.List;

public class UserDAOTest {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        RoleDAO roleDAO = new RoleDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        System.out.println("===== TEST GET ALL USERS =====");
        List<User> users = userDAO.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("❌ Không có user nào trong DB!");
        } else {
            for (User u : users) {
                System.out.println(
                        "ID: " + u.getUser_id() +
                                ", Email: " + u.getEmail() +
                                ", RoleID: " + u.getRole_id() +
                                ", EmpID: " + u.getEmp_id() +
                                ", CreatedAt: " + u.getCreated_at()
                );
            }
        }

        System.out.println("\n===== TEST GET ALL ROLES =====");
        List<Role> roles = roleDAO.getAllRoles();
        if (roles.isEmpty()) {
            System.out.println("❌ Không có role nào trong DB!");
        } else {
            for (Role r : roles) {
                System.out.println("RoleID: " + r.getRole_id() + ", Name: " + r.getRole_name());
            }
        }

        System.out.println("\n===== TEST GET ALL EMPLOYEES =====");
        List<Employee> employees = employeeDAO.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("❌ Không có employee nào trong DB!");
        } else {
            for (Employee e : employees) {
                System.out.println("EmpID: " + e.getEmp_id() + ", Name: " + e.getName());
            }
        }
    }
}
