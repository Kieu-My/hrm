<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>HRM - Quản lý lương</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin/payroll.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="container">

    <!-- Sidebar -->
    <div class="sidebar">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/assets/images/logo_hrm.jpg" alt="HRM Logo">
        </div>
        <ul class="menu">
            <li><a href="${pageContext.request.contextPath}/view/admin/dashboard.jsp"><i class="fa fa-home"></i> Trang Chủ</a></li>
            <li><a href="${pageContext.request.contextPath}/employee"><i class="fa fa-user"></i> Quản lý nhân viên</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/departments"><i class="fa fa-building"></i> Quản lý phòng ban</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/attendance"><i class="fa fa-calendar-check"></i> Quản lý chấm công</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/payroll" class="active"><i class="fa fa-money-bill"></i> Quản lý lương</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/leaves"><i class="fa fa-plane"></i> Quản lý nghỉ phép</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users"><i class="fa fa-users"></i> Quản lý tài khoản</a></li>
            <li><a href="${pageContext.request.contextPath}/view/auth/logout.jsp"><i class="fa fa-sign-out-alt"></i> Đăng xuất</a></li>
        </ul>
    </div>

    <!-- Content -->
    <div class="content">
        <!-- Thanh tìm kiếm -->
        <div class="search-filter-container">
            <form action="${pageContext.request.contextPath}/admin/payroll" method="get" class="search-form">
                <input type="hidden" name="action" value="search">
                <label for="empId">Tìm theo Emp ID:</label>
                <input type="number" name="empId" id="empId" placeholder="Nhập ID nhân viên..." value="${param.empId}">
                <label for="month">Tháng:</label>
                <input type="month" name="month" id="month" value="${param.month}">
            <%--
                <label for="sort">Sắp xếp theo lương:</label>
                <select name="sort" id="sort">
                    <option value="">--Chọn--</option>
                    <option value="asc" ${param.sort == 'asc' ? 'selected' : ''}>Tăng dần</option>
                    <option value="desc" ${param.sort == 'desc' ? 'selected' : ''}>Giảm dần</option>
                </select>
            --%>
                <button type="submit" class="btn btn-search"><i class="fa fa-search"></i> Lọc / Tìm kiếm</button>
                <a href="${pageContext.request.contextPath}/admin/payroll" class="btn btn-reset"><i class="fa fa-undo"></i> Làm mới</a>
            </form>
        </div>

        <h1>Quản lý lương nhân viên</h1>

        <!-- Form thêm/sửa -->
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/admin/payroll" method="post">
                <input type="hidden" name="id" value="${payroll != null ? payroll.payroll_id : 0}" />
                <label>Emp ID:
                    <input type="number" name="emp_id" value="${payroll != null ? payroll.emp_id : ''}" required>
                </label>
                <label>Ngày:
                    <input type="date" name="pay_date" value="${payroll != null ? payroll.pay_date : ''}" required>
                </label>
                <label>Lương cơ bản:
                    <input type="number" step="0.01" name="base_salary" value="${payroll != null ? payroll.base_salary : ''}" required>
                </label>
                <label>Thưởng:
                    <input type="number" step="0.01" name="bonus" value="${payroll != null ? payroll.bonus : 0}">
                </label>
                <label>Khấu trừ:
                    <input type="number" step="0.01" name="deductions" value="${payroll != null ? payroll.deductions : 0}">
                </label>
                </label>
                <button type="submit" class="btn btn-submit">${payroll != null ? "Cập nhật" : "Thêm mới"}</button>
            </form>
        </div>

        <!-- Danh sách bảng lương -->
        <table class="payroll-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Emp ID</th>
                    <th>Ngày</th>
                    <th>Lương cơ bản</th>
                    <th>Thưởng</th>
                    <th>Khấu trừ</th>
                    <th>Lương thực nhận</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${payrolls}">
                    <tr>
                        <td>${p.payroll_id}</td>
                        <td>${p.emp_id}</td>
                        <td>${p.pay_date}</td>
                        <td>${p.base_salary}</td>
                        <td>${p.bonus}</td>
                        <td>${p.deductions}</td>
                        <td>${p.net_salary}</td>
                        <td>
                            <a class="btn btn-edit" href="${pageContext.request.contextPath}/admin/payroll?action=edit&id=${p.payroll_id}">Sửa</a>
                            <a class="btn btn-delete" href="${pageContext.request.contextPath}/admin/payroll?action=delete&id=${p.payroll_id}" onclick="return confirm('Bạn có chắc muốn xóa không?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>
