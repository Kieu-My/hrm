package servlet.admin;

import dao.DepartmentDAO;
import dao.EmployeeDAO;
import model.Department;
import model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/departments")
public class DepartmentServlet extends HttpServlet {
    private DepartmentDAO departmentDAO;
    private EmployeeDAO employeeDAO;

    @Override
    public void init() throws ServletException {
        departmentDAO = new DepartmentDAO();
        employeeDAO = new EmployeeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteDepartment(request, response);
                break;
            case "viewEmployees":
                viewEmployeesByDepartment(request, response);
                break;
            default:
                listDepartments(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        switch (action) {
            case "insert":
                insertDepartment(request, response);
                break;
            case "update":
                updateDepartment(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/departments");
                break;
        }
    }

    // 🔹 Hiển thị danh sách tất cả phòng ban
    private void listDepartments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Department> list = departmentDAO.getAllDepartments();
        System.out.println("🔹 [Servlet] Số lượng phòng ban lấy từ DAO: " + list.size());
        for (Department d : list) {
            System.out.println("   -> " + d.getDept_id() + " - " + d.getDept_name());
        }

        request.setAttribute("deptList", list);
        request.getRequestDispatcher("/view/admin/department/department.jsp").forward(request, response);
    }

    // 🔹 Hiển thị form chỉnh sửa phòng ban
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Department dept = departmentDAO.getDepartmentById(id);
        request.setAttribute("editDept", dept);
        listDepartments(request, response);
    }

    // 🔹 Thêm phòng ban mới
    private void insertDepartment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("dept_name");
        Department dept = new Department(0, name);
        boolean result = departmentDAO.insertDepartment(dept);
        System.out.println("🔹 [Servlet] Thêm phòng ban: " + name + " -> " + (result ? "Thành công" : "Thất bại"));
        response.sendRedirect(request.getContextPath() + "/admin/departments");
    }

    // 🔹 Cập nhật thông tin phòng ban
    private void updateDepartment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("dept_id"));
        String name = request.getParameter("dept_name");
        Department dept = new Department(id, name);
        boolean result = departmentDAO.updateDepartment(dept);
        System.out.println("🔹 [Servlet] Cập nhật phòng ban ID " + id + " -> " + (result ? "Thành công" : "Thất bại"));
        response.sendRedirect(request.getContextPath() + "/admin/departments");
    }

    // 🔹 Xóa phòng ban
    private void deleteDepartment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean result = departmentDAO.deleteDepartment(id);
        System.out.println("🔹 [Servlet] Xóa phòng ban ID " + id + " -> " + (result ? "Thành công" : "Thất bại"));
        response.sendRedirect(request.getContextPath() + "/admin/departments");
    }

    // 🔹 Xem danh sách nhân viên thuộc phòng ban
    private void viewEmployeesByDepartment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int deptId = Integer.parseInt(request.getParameter("id"));
        Department selectedDept = departmentDAO.getDepartmentById(deptId);
        List<Employee> employeeList = employeeDAO.getEmployeesByDepartment(deptId);

        System.out.println("🔹 [Servlet] Hiển thị nhân viên phòng ban ID " + deptId +
                " - " + selectedDept.getDept_name() +
                " (" + employeeList.size() + " nhân viên)");

        // Gửi dữ liệu sang JSP
        request.setAttribute("selectedDept", selectedDept);
        request.setAttribute("employeeList", employeeList);
        request.setAttribute("deptList", departmentDAO.getAllDepartments());

        // Giữ nguyên trang department.jsp
        request.getRequestDispatcher("/view/admin/department/department.jsp").forward(request, response);
    }
}
