package servlet.user;

import dao.AttendanceDAO;
import model.Attendance;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@WebServlet("/user/attendance/*")
public class EmployeeAttendanceServlet extends HttpServlet {
    private AttendanceDAO attendanceDAO;

    @Override
    public void init() throws ServletException {
        attendanceDAO = new AttendanceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        try {
            switch (action) {
                case "/form":
                    showForm(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/delete":
                    deleteAttendance(request, response);
                    break;
                case "/list":
                default:
                    listAttendance(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Có lỗi xảy ra: " + e.getMessage());
            request.setAttribute("messageType", "error");
            listAttendance(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        if (action == null) action = "/list";

        try {
            switch (action) {
                case "/insert":
                    insertAttendance(request, response);
                    break;
                case "/update":
                    updateAttendance(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/user/attendance/list");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Có lỗi xảy ra khi xử lý: " + e.getMessage());
            request.setAttribute("messageType", "error");
            listAttendance(request, response);
        }
    }

    // ================== ACTIONS ===================

    private void listAttendance(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Attendance> list = attendanceDAO.getAllAttendance();
        request.setAttribute("attendanceList", list);
        request.setAttribute("currentPage", "list");
        request.getRequestDispatcher("/view/user/attendance.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("currentPage", "form");
        request.getRequestDispatcher("/view/user/attendance.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Attendance att = attendanceDAO.getAttendanceById(id);
        request.setAttribute("attendance", att);
        request.setAttribute("currentPage", "edit");
        request.getRequestDispatcher("/view/user/attendance.jsp").forward(request, response);
    }

    private void insertAttendance(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int empId = Integer.parseInt(request.getParameter("emp_id"));
            Date workDate = Date.valueOf(request.getParameter("work_date"));
            Time checkIn = Time.valueOf(request.getParameter("check_in") + ":00");
            String checkOutStr = request.getParameter("check_out");
            Time checkOut = (checkOutStr == null || checkOutStr.isEmpty()) ? null : Time.valueOf(checkOutStr + ":00");
            String status = request.getParameter("status");

            Attendance att = new Attendance();
            att.setEmpId(empId);
            att.setWorkDate(workDate);
            att.setCheckIn(checkIn);
            att.setCheckOut(checkOut);
            att.setStatus(status);

            attendanceDAO.insertAttendance(att);

            response.sendRedirect(request.getContextPath() + "/user/attendance/list");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/user/attendance/form?error=true");
        }
    }

    private void updateAttendance(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int attendanceId = Integer.parseInt(request.getParameter("attendance_id"));
            int empId = Integer.parseInt(request.getParameter("emp_id"));
            Date workDate = Date.valueOf(request.getParameter("work_date"));
            Time checkIn = Time.valueOf(request.getParameter("check_in") + ":00");
            String checkOutStr = request.getParameter("check_out");
            Time checkOut = (checkOutStr == null || checkOutStr.isEmpty()) ? null : Time.valueOf(checkOutStr + ":00");
            String status = request.getParameter("status");

            Attendance att = new Attendance();
            att.setAttendanceId(attendanceId);
            att.setEmpId(empId);
            att.setWorkDate(workDate);
            att.setCheckIn(checkIn);
            att.setCheckOut(checkOut);
            att.setStatus(status);

            attendanceDAO.updateAttendance(att);

            response.sendRedirect(request.getContextPath() + "/user/attendance/list");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/user/attendance/edit?error=true");
        }
    }

    private void deleteAttendance(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            attendanceDAO.deleteAttendance(id);
            response.sendRedirect(request.getContextPath() + "/user/attendance/list");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/user/attendance/list?error=true");
        }
    }
}
