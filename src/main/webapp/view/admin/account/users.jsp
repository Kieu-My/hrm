<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý tài khoản</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin/account.css">
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
            <li><a href="${pageContext.request.contextPath}/admin/users" class="active"><i class="fa fa-users"></i> Quản lý tài khoản</a></li>
            <li><a href="${pageContext.request.contextPath}/view/auth/logout.jsp"><i class="fa fa-sign-out-alt"></i> Đăng xuất</a></li>
        </ul>
    </div>

    <!-- Content -->
    <div class="content">
        <h2>Quản lý tài khoản</h2>

        <!-- Form thêm tài khoản -->
        <div class="form">
            <h3>Thêm tài khoản mới</h3>
            <form action="${pageContext.request.contextPath}/admin/users" method="post">
                <input type="hidden" name="action" value="add">

                <label>Email:</label>
                <input type="email" name="email" required>

                <label>Password:</label>
                <input type="password" name="password" required>

                <label>Role:</label>
                <select name="role_id" required>
                    <c:forEach var="r" items="${listRoles}">
                        <option value="${r.role_id}">${r.role_name}</option>
                    </c:forEach>
                </select>


                <button type="submit">Thêm mới</button>
            </form>
        </div>

        <!-- Form cập nhật tài khoản (chỉ hiển thị khi bấm Sửa) -->
        <c:if test="${not empty user}">
            <div class="form">
                <h3>Cập nhật tài khoản</h3>
                <form action="${pageContext.request.contextPath}/admin/users" method="post">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="user_id" value="${user.user_id}">

                    <label>Email:</label>
                    <input type="email" name="email" value="${user.email}" required>

                    <label>Password (bỏ trống nếu không đổi):</label>
                    <input type="password" name="password">

                    <label>Role:</label>
                    <select name="role_id" required>
                        <c:forEach var="r" items="${listRoles}">
                            <option value="${r.role_id}" <c:if test="${r.role_id == user.role_id}">selected</c:if>>
                                ${r.role_name}
                            </option>
                        </c:forEach>
                    </select>

                    <label>Employee:</label>
                    <select name="emp_id" required>
                        <c:forEach var="e" items="${listEmployees}">
                            <option value="${e.emp_id}" <c:if test="${e.emp_id == user.emp_id}">selected</c:if>>
                                ${e.name}
                            </option>
                        </c:forEach>
                    </select>

                    <button type="submit">Cập nhật</button>
                </form>
            </div>
        </c:if>

        <!-- Danh sách tài khoản -->
        <h3>Danh sách tài khoản</h3>
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Email</th>
                <th>Role</th>
                <th>Employee</th>
                <th>Created At</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="u" items="${listUsers}">
                <tr>
                    <td>${u.user_id}</td>
                    <td>${u.email}</td>
                    <td>${u.role_name}</td>
                    <td>${u.emp_name}</td>
                    <td>${u.created_at}</td>
                    <td>
                        <a class="btn edit" href="${pageContext.request.contextPath}/admin/users?action=edit&user_id=${u.user_id}">Sửa</a>
                        <a class="btn delete" href="${pageContext.request.contextPath}/admin/users?action=delete&user_id=${u.user_id}"
                           onclick="return confirm('Bạn có chắc chắn muốn xóa?');">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
