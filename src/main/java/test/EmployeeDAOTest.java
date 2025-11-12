package test;

import dao.EmployeeDAO;
import model.Employee;

import java.util.List;

public class EmployeeDAOTest {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        System.out.println("===== DANH SÁCH NHÂN VIÊN TRONG DATABASE =====");
        List<Employee> employees = dao.getAllEmployees();

        if (employees.isEmpty()) {
            System.out.println("⚠️ Không có dữ liệu trong bảng Employees.");
        } else {
            for (Employee emp : employees) {
                System.out.println(
                        "ID: " + emp.getEmp_id() +
                                ", UserID: " + emp.getUser_id() +
                                ", Name: " + emp.getName() +
                                ", Email: " + emp.getEmail() +
                                ", Phone: " + emp.getPhone() +
                                ", DOB: " + emp.getDob() +
                                ", HireDate: " + emp.getHire_date() +
                                ", Salary: " + emp.getSalary() +
                                ", DeptID: " + emp.getDept_id() +
                                ", DeptName: " + emp.getDept_name() +
                                ", PositionID: " + emp.getPosition_id() +
                                ", PositionName: " + emp.getPosition_name()
                );
            }
        }
    }
}
