<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>User - Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/user/profile.css">
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
            <li><a href="${pageContext.request.contextPath}/view/user/dashboard.jsp"><i class="fa fa-home"></i> Trang Chủ</a></li>
            <li><a href="${pageContext.request.contextPath}/user/profile"><i class="fa fa-user"></i> Thông tin cá nhân</a></li>
            <li><a href="${pageContext.request.contextPath}/user/attendance/"><i class="fa fa-calendar-check"></i> Bảng chấm công</a></li>
            <li><a href="${pageContext.request.contextPath}/user/payroll"><i class="fa fa-money-bill"></i> Bảng lương</a></li>
            <li><a href="${pageContext.request.contextPath}/user/employeeLeave"><i class="fa fa-plane"></i> Đăng ký nghỉ phép</a></li>
            <li><a href="${pageContext.request.contextPath}/view/auth/logout.jsp"><i class="fa fa-sign-out-alt"></i> Đăng xuất</a></li>
        </ul>
    </div>

    <!-- Profile Content -->
    <div class="profile-content">
        <h2>Hồ sơ cá nhân</h2>

        <c:choose>
            <c:when test="${not empty employee}">
                <table class="profile-table">
                    <tr><td class="label">Mã NV</td><td class="value">${employee.emp_id}</td></tr>
                    <tr><td class="label">Họ tên</td><td class="value">${employee.name}</td></tr>
                    <tr><td class="label">Ngày sinh</td><td class="value">${employee.dob}</td></tr>
                    <tr><td class="label">Email</td><td class="value">${employee.email}</td></tr>
                    <tr><td class="label">SĐT</td><td class="value">${employee.phone}</td></tr>
                    <tr><td class="label">Ngày vào làm</td><td class="value">${employee.hire_date}</td></tr>
                    <tr><td class="label">Lương</td><td class="value">${employee.salary}</td></tr>
                    <tr><td class="label">Phòng ban</td><td class="value">${employee.dept_name}</td></tr>
                    <tr><td class="label">Chức vụ</td><td class="value">${employee.position_name}</td></tr>
                </table>
            </c:when>
            <c:otherwise>
                <p style="color:red; text-align:center;">Không tìm thấy thông tin nhân viên!</p>
            </c:otherwise>
        </c:choose>

    </div>
</div>
</body>
</html>
