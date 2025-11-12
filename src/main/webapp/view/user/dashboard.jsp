<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>HRM - User Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="container">
    <!-- Sidebar -->
    <div class="sidebar">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/assets/images/logo_hrm.jpg" alt="HRM Logo">
            <h2></h2>
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

    <!-- Content - Dashboard -->
    <div class="content">
        <h1>Xin chào, ${sessionScope.currentUser.fullName}!</h1>
        <p>Chào mừng bạn đến với hệ thống HRM. Đây là trang dashboard của bạn.</p>

        <div class="dashboard-cards">
            <div class="card">
                <h3>Bảng lương</h3>
                <p>Xem chi tiết lương hàng tháng của bạn.</p>
            </div>
            <div class="card">
                <h3>Chấm công</h3>
                <p>Theo dõi ngày công và giờ làm việc.</p>
            </div>
            <div class="card">
                <h3>Nghỉ phép</h3>
                <p>Đăng ký nghỉ phép trực tuyến.</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>