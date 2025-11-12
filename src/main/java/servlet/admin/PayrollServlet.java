package servlet.admin;

import dao.PayrollDAO;
import model.Payroll;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * Servlet quản lý bảng lương (Payroll) cho Admin
 */
@WebServlet("/admin/payroll")
public class PayrollServlet extends HttpServlet {
    private PayrollDAO payrollDAO;

    @Override
    public void init() {
        payrollDAO = new PayrollDAO();
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
                deletePayroll(request, response);
                break;
            case "new":
                showNewForm(request, response);
                break;
            case "search":
                searchPayroll(request, response);
                break;
            default:
                listPayroll(request, response);
                break;
        }
    }

    /** 🔹 Danh sách tất cả bảng lương */
    private void listPayroll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Payroll> payrolls = payrollDAO.getAllPayrolls();
        request.setAttribute("payrolls", payrolls);
        request.setAttribute("payroll", null);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/admin/payroll/payroll.jsp");
        dispatcher.forward(request, response);
    }

    /** 🔹 Hiển thị form sửa */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Payroll payroll = payrollDAO.getPayrollById(id);
        request.setAttribute("payroll", payroll);
        request.setAttribute("payrolls", payrollDAO.getAllPayrolls());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/admin/payroll/payroll.jsp");
        dispatcher.forward(request, response);
    }

    /** 🔹 Hiển thị form thêm mới */
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("payroll", null);
        request.setAttribute("payrolls", payrollDAO.getAllPayrolls());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/admin/payroll/payroll.jsp");
        dispatcher.forward(request, response);
    }

    /** 🔹 Xóa lương */
    private void deletePayroll(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        payrollDAO.deletePayroll(id);
        response.sendRedirect(request.getContextPath() + "/admin/payroll");
    }

    /** 🔹 Tìm kiếm / lọc bảng lương */
    private void searchPayroll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String empIdParam = request.getParameter("empId");
        Integer empId = (empIdParam != null && !empIdParam.isEmpty()) ? Integer.parseInt(empIdParam) : null;

        String month = request.getParameter("month");
        String sort = request.getParameter("sort");

        List<Payroll> payrolls = payrollDAO.searchPayroll(empId, month, sort);
        request.setAttribute("payrolls", payrolls);
        request.setAttribute("payroll", null);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/admin/payroll/payroll.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                ? Integer.parseInt(request.getParameter("id")) : 0;

        int emp_id = Integer.parseInt(request.getParameter("emp_id"));
        Date pay_date = Date.valueOf(request.getParameter("pay_date"));
        BigDecimal base_salary = parseBigDecimal(request.getParameter("base_salary"));
        BigDecimal bonus = parseBigDecimal(request.getParameter("bonus"));
        BigDecimal deductions = parseBigDecimal(request.getParameter("deductions"));

        Payroll payroll = new Payroll();
        payroll.setEmp_id(emp_id);
        payroll.setPay_date(pay_date);
        payroll.setBase_salary(base_salary);
        payroll.setBonus(bonus);
        payroll.setDeductions(deductions);

        if (id > 0) {
            payroll.setPayroll_id(id);
            payrollDAO.updatePayroll(payroll);
        } else {
            payrollDAO.insertPayroll(payroll);
        }

        response.sendRedirect(request.getContextPath() + "/admin/payroll");
    }

    private BigDecimal parseBigDecimal(String val) {
        if (val == null || val.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(val.trim());
    }
}
