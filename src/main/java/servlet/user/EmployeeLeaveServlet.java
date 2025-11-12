package servlet.user;

import dao.LeaveDAO;
import model.LeaveRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/user/employeeLeave")   // truy cập: http://localhost:8080/<context>/user/employeeLeave
public class EmployeeLeaveServlet extends HttpServlet {
    private LeaveDAO leaveDAO;

    @Override
    public void init() {
        leaveDAO = new LeaveDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "edit":
                    // load dữ liệu đơn cần sửa rồi forward về same JSP (leaves.jsp)
                    int leaveId = Integer.parseInt(request.getParameter("leave_id"));
                    LeaveRequest editLeave = leaveDAO.getLeaveById(leaveId);
                    request.setAttribute("editLeave", editLeave);
                    listAndForward(request, response);
                    break;
                case "delete":
                    doDeleteAction(request, response);
                    break;
                default: // list
                    listAndForward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi trong doGet: " + e.getMessage(), e);
        }
    }

    // POST handles insert and update
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "insert":
                    insertLeave(request, response);
                    break;
                case "update":
                    updateLeave(request, response);
                    break;
                default:
                    // fallback: show list
                    listAndForward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi trong doPost: " + e.getMessage(), e);
        }
    }

    // ========== helpers ==========

    // Lấy danh sách của user hiện tại rồi forward tới JSP
    private void listAndForward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp");
            return;
        }
        Integer emp_id = (Integer) session.getAttribute("emp_id");
        if (emp_id == null) {
            response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp");
            return;
        }

        List<LeaveRequest> leaves = leaveDAO.getLeavesByEmpId(emp_id);
        request.setAttribute("leaves", leaves);

        // forward tới same JSP (gộp form tạo/sửa)
        request.getRequestDispatcher("/view/user/leave.jsp").forward(request, response);
    }

    // Insert new leave
    private void insertLeave(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp");
            return;
        }
        Integer emp_id = (Integer) session.getAttribute("emp_id");
        if (emp_id == null) {
            response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp");
            return;
        }

        try {
            Date start_date = Date.valueOf(request.getParameter("start_date"));
            Date end_date = Date.valueOf(request.getParameter("end_date"));
            String reason = request.getParameter("reason");

            LeaveRequest newLeave = new LeaveRequest();
            newLeave.setEmp_id(emp_id);
            newLeave.setStart_date(start_date);
            newLeave.setEnd_date(end_date);
            newLeave.setReason(reason);
            newLeave.setStatus("PENDING");

            leaveDAO.insertLeave(newLeave);
            response.sendRedirect(request.getContextPath() + "/user/employeeLeave");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi tạo đơn nghỉ: " + e.getMessage(), e);
        }
    }

    // Update existing leave (only dates and reason; keep status unchanged)
    private void updateLeave(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            int leave_id = Integer.parseInt(request.getParameter("leave_id"));
            Date start_date = Date.valueOf(request.getParameter("start_date"));
            Date end_date = Date.valueOf(request.getParameter("end_date"));
            String reason = request.getParameter("reason");

            LeaveRequest exist = leaveDAO.getLeaveById(leave_id);
            if (exist != null) {
                exist.setStart_date(start_date);
                exist.setEnd_date(end_date);
                exist.setReason(reason);
                // giữ nguyên status hiện có
                leaveDAO.updateLeave(exist);
            }

            response.sendRedirect(request.getContextPath() + "/user/employeeLeave");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi cập nhật đơn nghỉ: " + e.getMessage(), e);
        }
    }

    // Delete action (via GET link)
    private void doDeleteAction(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            int leave_id = Integer.parseInt(request.getParameter("leave_id"));
            leaveDAO.deleteLeave(leave_id);
            response.sendRedirect(request.getContextPath() + "/user/employeeLeave");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi xóa đơn nghỉ: " + e.getMessage(), e);
        }
    }
}
