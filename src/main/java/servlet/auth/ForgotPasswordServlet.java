package servlet.auth;

import dao.UserDAO;
import model.User;
import util.EmailUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển đến trang forgot password
        request.getRequestDispatcher("view/auth/forgotPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        User user = userDAO.getUserByEmail(email);

        if (user != null) {
            // Sinh mật khẩu mới ngẫu nhiên (8 ký tự)
            String newPass = UUID.randomUUID().toString().substring(0, 8);

            // Update vào DB
            userDAO.changePassword(user.getUser_id(), newPass);

            // Gửi email
            String subject = "Khôi phục mật khẩu HRM";
            String content = "Xin chào " + user.getEmail() +
                    ",\n\nMật khẩu mới của bạn là: " + newPass +
                    "\nVui lòng đăng nhập và đổi mật khẩu ngay.";
            EmailUtil.sendEmail(email, subject, content);

            // Thông báo thành công và quay về login
            request.setAttribute("message", "Mật khẩu mới đã được gửi đến email của bạn.");
            request.getRequestDispatcher("view/auth/login.jsp").forward(request, response);
        } else {
            // Email không tồn tại
            request.setAttribute("error", "Email không tồn tại!");
            request.getRequestDispatcher("view/auth/forgotPassword.jsp").forward(request, response);
        }
    }
}
