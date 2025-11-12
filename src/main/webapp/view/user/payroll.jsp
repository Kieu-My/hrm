<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>User - Payroll</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/user/payroll.css">
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

    <!-- Content Payroll -->
    <div class="content">
        <h2>Bảng lương của bạn</h2>
        <div class="table-wrap">
            <table class="salary-table">
                <thead>
                    <tr>
                        <th>Tháng</th>
                        <th>Lương cơ bản</th>
                        <th>Thưởng</th>
                        <th>Khấu trừ</th>
                        <th>Lương thực nhận</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty payrollList}">
                            <c:forEach var="p" items="${payrollList}">
                                <tr>
                                    <td>${p.pay_date}</td>
                                    <td>${p.base_salary}</td>
                                    <td>${p.bonus}</td>
                                    <td>${p.deductions}</td>
                                    <td>${p.net_salary}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5">Không có dữ liệu bảng lương</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
