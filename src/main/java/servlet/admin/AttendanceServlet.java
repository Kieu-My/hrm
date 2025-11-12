package servlet.admin;

import dao.AttendanceDAO;
import model.Attendance;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/attendance")
public class AttendanceServlet extends HttpServlet {

    private AttendanceDAO attendanceDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        attendanceDAO = new AttendanceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String empId = request.getParameter("empId");
        String workDate = request.getParameter("workDate");

        List<Attendance> attendanceList;

        if ((empId != null && !empId.isEmpty()) || (workDate != null && !workDate.isEmpty())) {
            attendanceList = attendanceDAO.searchAttendance(empId, workDate);
        } else {
            attendanceList = attendanceDAO.getAllAttendance();
        }

        request.setAttribute("attendanceList", attendanceList);
        request.getRequestDispatcher("/view/admin/attendance/attendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
