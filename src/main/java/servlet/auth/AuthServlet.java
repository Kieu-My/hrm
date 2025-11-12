package servlet.auth;

import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class AuthServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userDAO.getUserByEmail(email);

        if (user != null && password.equals(user.getPassword())) {
            // Đăng nhập thành công → Lưu session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("user_id", user.getUser_id());   // để ProfileServlet dùng
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("emp_id", user.getEmp_id());

            if (user.getRole_id() == 100) { // Admin
                response.sendRedirect(request.getContextPath() + "/view/admin/dashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/view/user/dashboard.jsp");
            }
        } else {
            // Đăng nhập thất bại
            request.setAttribute("error", "Email hoặc mật khẩu sai!");
            request.getRequestDispatcher("/view/auth/login.jsp").forward(request, response);
        }
    }
}
