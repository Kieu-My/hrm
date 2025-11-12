package servlet.admin;

import dao.LeaveDAO;
import model.LeaveRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/leaves")   // URL: http://localhost:8080/hrm/admin/leaves
public class LeaveAdminServlet extends HttpServlet {

    private LeaveDAO leaveDAO;

    @Override
    public void init() {
        leaveDAO = new LeaveDAO();
    }

    /**
     * GET: Hiển thị danh sách tất cả đơn nghỉ phép
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 🔹 Lấy tất cả đơn nghỉ phép
            List<LeaveRequest> leaveList = leaveDAO.getAllRequests();
            request.setAttribute("leaves", leaveList);

            // 🔹 Forward tới JSP hiển thị
            request.getRequestDispatcher("/view/admin/leave/leaves.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi lấy danh sách đơn nghỉ!", e);
        }
    }

    /**
     * POST: Admin duyệt hoặc từ chối đơn nghỉ
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String leaveIdStr = request.getParameter("leave_id");
        String action = request.getParameter("action"); // "approve" hoặc "reject"

        if (leaveIdStr != null && action != null) {
            try {
                int leaveId = Integer.parseInt(leaveIdStr);
                String status;

                switch (action.toLowerCase()) {
                    case "approve":
                        status = "APPROVED";
                        break;
                    case "reject":
                        status = "REJECTED";
                        break;
                    default:
                        status = "PENDING"; // fallback
                        break;
                }

                // 🔹 Cập nhật trạng thái đơn nghỉ
                leaveDAO.updateStatus(leaveId, status);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // 🔹 Sau khi xử lý xong -> load lại danh sách
        response.sendRedirect(request.getContextPath() + "/admin/leaves");
    }
}
