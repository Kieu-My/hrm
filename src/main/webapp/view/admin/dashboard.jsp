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
            <li><a href="${pageContext.request.contextPath}/view/admin/dashboard.jsp"><i class="fa fa-home"></i> Trang Chủ</a></li>
            <li><a href="${pageContext.request.contextPath}/employee"><i class="fa fa-user"></i> Quản lý nhân viên</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/departments"><i class="fa fa-building"></i> Quản lý phòng ban</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/attendance"><i class="fa fa-calendar-check"></i> Quản lý chấm công</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/payroll"><i class="fa fa-money-bill"></i> Quản lý lương</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/leaves"><i class="fa fa-plane"></i> Quản lý nghỉ phép</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users" class="active"><i class="fa fa-users"></i> Quản lý tài khoản</a></li>
            <li><a href="${pageContext.request.contextPath}/view/auth/logout.jsp"><i class="fa fa-sign-out-alt"></i> Đăng xuất</a></li>

        </ul>
    </div>

    <!-- Content - Dashboard -->
    <div class="content">
        <h1>Xin chào, ${sessionScope.currentUser.fullName}!</h1>
        <p>Chào mừng bạn đến với hệ thống HRM. Đây là trang dashboard admin dành cho bạn.</p>
    </div>
</div>
</body>
</html>