package servlet.user;

import dao.EmployeeDAO;
import model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/user/profile")
public class ProfileServlet extends HttpServlet {

    private EmployeeDAO employeeDAO;

    @Override
    public void init() {
        employeeDAO = new EmployeeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy session hiện tại, không tạo mới nếu chưa có
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            System.out.println("DEBUG: Session null hoặc chưa có user_id → chuyển hướng login");
            response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp");
            return;
        }

        // Lấy user_id từ session
        Object userObj = session.getAttribute("user_id");
        int user_id;
        try {
            if (userObj instanceof Integer) {
                user_id = (Integer) userObj;
            } else {
                user_id = Integer.parseInt(userObj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp");
            return;
        }

        System.out.println("DEBUG: user_id = " + user_id);

        // Lấy thông tin nhân viên từ database
        Employee emp = employeeDAO.getEmployeeByUserId(user_id);

        if (emp != null) {
            System.out.println("DEBUG: Employee found = " + emp.getName());
            request.setAttribute("employee", emp);
        } else {
            System.out.println("DEBUG: Không tìm thấy Employee cho user_id = " + user_id);
            request.setAttribute("employee", null);
        }

        // Forward sang JSP
        request.getRequestDispatcher("/view/user/profile.jsp").forward(request, response);
    }
}
