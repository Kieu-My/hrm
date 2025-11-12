package servlet.user;

import dao.PayrollDAO;
import model.Payroll;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * EmployeePayrollServlet - Hiển thị bảng lương cho nhân viên
 * Hỗ trợ:
 * - Lấy bảng lương theo emp_id (tự động từ session)
 * - Lọc theo tháng/năm
 * - Sắp xếp tăng/giảm theo lương
 */
@WebServlet("/user/payroll")
public class EmployeePayrollServlet extends HttpServlet {
    private PayrollDAO payrollDAO;

    @Override
    public void init() throws ServletException {
        payrollDAO = new PayrollDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // 🔸 Kiểm tra session
        if (session == null || session.getAttribute("emp_id") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 🔸 Lấy emp_id từ session
        int empId;
        Object empObj = session.getAttribute("emp_id");
        if (empObj instanceof Integer) {
            empId = (Integer) empObj;
        } else {
            empId = Integer.parseInt(empObj.toString());
        }

        // 🔸 Lấy thông tin lọc từ request
        String month = request.getParameter("month");     // định dạng yyyy-MM
        String sort = request.getParameter("sort");       // asc / desc

        // 🔸 Gọi DAO để lấy danh sách lương theo empId, month, sort
        List<Payroll> payrollList = payrollDAO.searchPayroll(empId, month, sort);

        // 🔸 Gửi dữ liệu qua JSP
        request.setAttribute("payrollList", payrollList);
        request.setAttribute("selectedMonth", month);
        request.setAttribute("selectedSort", sort);
        request.getRequestDispatcher("/view/user/payroll.jsp").forward(request, response);
    }
}
