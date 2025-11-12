package servlet.admin;

import dao.UserDAO;
import dao.RoleDAO;
import dao.EmployeeDAO;
import model.User;
import model.Role;
import model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private EmployeeDAO employeeDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
        roleDAO = new RoleDAO();
        employeeDAO = new EmployeeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listUsers(request, response);
            } else {
                switch (action) {
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "delete":
                        deleteUser(request, response);
                        break;
                    default:
                        listUsers(request, response);
                        break;
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listUsers(request, response);
            } else {
                switch (action) {
                    case "add":
                        insertUser(request, response);
                        break;
                    case "update":
                        updateUser(request, response);
                        break;
                    case "changePassword":
                        changePassword(request, response);
                        break;
                    default:
                        listUsers(request, response);
                        break;
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /** Danh sách user */
    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> list = userDAO.getAllUsers();
        List<Role> roles = roleDAO.getAllRoles();
        List<Employee> employees = employeeDAO.getAllEmployees();

        request.setAttribute("listUsers", list);
        request.setAttribute("listRoles", roles);
        request.setAttribute("listEmployees", employees);

        // lấy message từ session
        HttpSession session = request.getSession();
        String message = (String) session.getAttribute("message");
        String error = (String) session.getAttribute("error");

        if (message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message");
        }
        if (error != null) {
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }

        request.getRequestDispatcher("/view/admin/account/users.jsp").forward(request, response);
    }

    /** Thêm user */
    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int roleId = parseIntSafe(request.getParameter("role_id"));
        int empId = parseIntSafe(request.getParameter("emp_id"));

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            session.setAttribute("error", "❌ Vui lòng nhập đầy đủ thông tin!");
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole_id(roleId);
        user.setEmp_id(empId);

        boolean success = userDAO.addUser(user);
        if (success) {
            session.setAttribute("message", "✅ Thêm tài khoản thành công!");
        } else {
            session.setAttribute("error", "❌ Thêm tài khoản thất bại!");
        }
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }

    /** Cập nhật user */
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        int id = parseIntSafe(request.getParameter("user_id"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int roleId = parseIntSafe(request.getParameter("role_id"));
        int empId = parseIntSafe(request.getParameter("emp_id"));

        if (id <= 0 || email == null || email.isEmpty()) {
            session.setAttribute("error", "❌ Cập nhật thất bại! Dữ liệu không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        User user = new User();
        user.setUser_id(id);
        user.setEmail(email);
        user.setRole_id(roleId);
        user.setEmp_id(empId);

        boolean success;
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
            success = userDAO.updateUser(user);
        } else {
            success = userDAO.updateUserNoPassword(user);
        }

        if (success) {
            session.setAttribute("message", "✅ Cập nhật tài khoản thành công!");
        } else {
            session.setAttribute("error", "❌ Cập nhật tài khoản thất bại!");
        }
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }

    /** Xóa user */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        int id = parseIntSafe(request.getParameter("id"));
        boolean success = false;
        if (id > 0) {
            success = userDAO.deleteUser(id);
        }
        if (success) {
            session.setAttribute("message", "✅ Xóa tài khoản thành công!");
        } else {
            session.setAttribute("error", "❌ Xóa tài khoản thất bại!");
        }
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }

    /** Đổi mật khẩu */
    private void changePassword(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        int id = parseIntSafe(request.getParameter("user_id"));
        String newPassword = request.getParameter("new_password");

        boolean success = userDAO.changePassword(id, newPassword);
        if (success) {
            session.setAttribute("message", "✅ Đổi mật khẩu thành công!");
        } else {
            session.setAttribute("error", "❌ Đổi mật khẩu thất bại!");
        }
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = parseIntSafe(request.getParameter("id"));
        if (id <= 0) {
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        User existingUser = userDAO.getUserById(id);
        List<Role> roles = roleDAO.getAllRoles();
        List<Employee> employees = employeeDAO.getAllEmployees();

        request.setAttribute("user", existingUser);
        request.setAttribute("listRoles", roles);
        request.setAttribute("listEmployees", employees);

        request.getRequestDispatcher("/view/admin/account/users.jsp").forward(request, response);
    }

    /** Hàm parse an toàn tránh NumberFormatException */
    private int parseIntSafe(String value) {
        try {
            return (value != null && !value.trim().isEmpty())
                    ? Integer.parseInt(value.trim())
                    : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
