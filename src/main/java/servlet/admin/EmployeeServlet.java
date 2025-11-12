package servlet.admin;

import dao.EmployeeDAO;
import dao.DepartmentDAO;
import dao.PositionDAO;
import model.Employee;
import model.Department;
import model.Position;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {
    private EmployeeDAO employeeDAO;
    private DepartmentDAO departmentDAO;
    private PositionDAO positionDAO;

    @Override
    public void init() {
        employeeDAO = new EmployeeDAO();
        departmentDAO = new DepartmentDAO();
        positionDAO = new PositionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "form":
                    showEmptyForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteEmployee(request, response);
                    break;
                case "search":
                    searchEmployee(request, response);
                    break;
                default:
                    listEmployees(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        try {
            if ("save".equals(action)) {
                saveEmployee(request, response);
            } else {
                response.sendRedirect("employee?action=list");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Employee> employees = employeeDAO.getAllEmployees();
        request.setAttribute("listEmployees", employees);
        request.setAttribute("departments", departmentDAO.getAllDepartments());
        request.setAttribute("positions", positionDAO.getAllPositions());
        request.getRequestDispatcher("/view/admin/employee/employee.jsp").forward(request, response);
    }

    private void showEmptyForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("employee", null);
        request.setAttribute("departments", departmentDAO.getAllDepartments());
        request.setAttribute("positions", positionDAO.getAllPositions());
        request.getRequestDispatcher("/view/admin/employee/employee.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int empId = Integer.parseInt(request.getParameter("id"));
        Employee existing = employeeDAO.getEmployeeById(empId);
        request.setAttribute("employee", existing);
        request.setAttribute("departments", departmentDAO.getAllDepartments());
        request.setAttribute("positions", positionDAO.getAllPositions());
        request.getRequestDispatcher("/view/admin/employee/employee.jsp").forward(request, response);
    }

    private void saveEmployee(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int empId = (request.getParameter("emp_id") == null || request.getParameter("emp_id").isEmpty())
                ? 0 : Integer.parseInt(request.getParameter("emp_id"));

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String dobStr = request.getParameter("dob");
        String hireStr = request.getParameter("hire_date");
        BigDecimal salary = new BigDecimal(request.getParameter("salary"));
        int deptId = Integer.parseInt(request.getParameter("dept_id"));
        int positionId = Integer.parseInt(request.getParameter("position_id"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = (dobStr != null && !dobStr.isEmpty()) ? sdf.parse(dobStr) : null;
        Date hireDate = (hireStr != null && !hireStr.isEmpty()) ? sdf.parse(hireStr) : null;

        Employee emp = new Employee();
        emp.setEmp_id(empId);
        emp.setName(name);
        emp.setEmail(email);
        emp.setPhone(phone);
        emp.setDob(dob);
        emp.setHire_date(hireDate);
        emp.setSalary(salary);
        emp.setDept_id(deptId);
        emp.setPosition_id(positionId);

        if (empId == 0) {
            employeeDAO.insertEmployee(emp);
        } else {
            employeeDAO.updateEmployee(emp);
        }
        response.sendRedirect("employee?action=list");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int empId = Integer.parseInt(request.getParameter("id"));
        employeeDAO.deleteEmployee(empId);
        response.sendRedirect("employee?action=list");
    }

    private void searchEmployee(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String keyword = request.getParameter("keyword");
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder == null || sortOrder.isEmpty()) sortOrder = "desc";

        List<Employee> employees = employeeDAO.searchEmployees(keyword, sortOrder);
        request.setAttribute("listEmployees", employees);
        request.setAttribute("departments", departmentDAO.getAllDepartments());
        request.setAttribute("positions", positionDAO.getAllPositions());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/admin/employee/employee.jsp");
        dispatcher.forward(request, response);
    }
}
