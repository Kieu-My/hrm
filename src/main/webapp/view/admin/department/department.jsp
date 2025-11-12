<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý phòng ban</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin/department.css">
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
            <li><a href="${pageContext.request.contextPath}/admin/payroll"><i class="fa fa-money-bill"></i> Quản lý lương</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/leaves"><i class="fa fa-plane"></i> Quản lý nghỉ phép</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users"><i class="fa fa-users"></i> Quản lý tài khoản</a></li>
            <li><a href="${pageContext.request.contextPath}/view/auth/logout.jsp"><i class="fa fa-sign-out-alt"></i> Đăng xuất</a></li>
        </ul>
    </div>

    <!-- Content -->
    <div class="content">
        <h2>Danh sách phòng ban</h2>

        <c:if test="${not empty deptList}">
            <p class="debug">🔹 Tổng số phòng ban: ${deptList.size()}</p>
        </c:if>

        <!-- Bảng danh sách phòng ban -->
        <table class="department-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Tên phòng ban</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="dept" items="${deptList}">
                <tr>
                    <td>${dept.dept_id}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/departments?action=viewEmployees&id=${dept.dept_id}">
                            ${dept.dept_name}
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/departments?action=edit&id=${dept.dept_id}">Sửa</a> |
                        <a href="${pageContext.request.contextPath}/admin/departments?action=delete&id=${dept.dept_id}"
                           onclick="return confirm('Bạn có chắc muốn xóa phòng ban này?')">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- Hiển thị danh sách nhân viên của phòng ban được chọn -->
        <c:if test="${not empty employeeList}">
            <div class="employee-section">
                <h3>Danh sách nhân viên thuộc phòng ban:
                    <span>${selectedDept.dept_name}</span>
                </h3>

                <table class="employee-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Họ tên</th>
                        <th>Email</th>
                        <th>Số điện thoại</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="emp" items="${employeeList}">
                        <tr>
                            <td>${emp.emp_id}</td>
                            <td>${emp.name}</td>
                            <td>${emp.email}</td>
                            <td>${emp.phone}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <!-- Form thêm phòng ban -->
        <h3>Thêm phòng ban mới</h3>
        <form action="${pageContext.request.contextPath}/admin/departments" method="post" class="dept-form">
            <input type="hidden" name="action" value="insert">
            <label>Tên phòng ban:</label>
            <input type="text" name="dept_name" required>
            <input type="submit" value="Thêm">
        </form>

        <!-- Form sửa phòng ban -->
        <c:if test="${not empty editDept}">
            <h3>Sửa phòng ban</h3>
            <form action="${pageContext.request.contextPath}/admin/departments" method="post" class="dept-form">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="dept_id" value="${editDept.dept_id}">
                <label>Tên phòng ban:</label>
                <input type="text" name="dept_name" value="${editDept.dept_name}" required>
                <input type="submit" value="Cập nhật">
            </form>
        </c:if>
    </div>
</div>
</body>
</html>
