<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>HRM - Quản lý chấm công</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin/attendance.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/filter-form.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>
<div class="container">
    <!-- Sidebar -->
    <aside class="sidebar">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/assets/images/logo_hrm.jpg" alt="HRM Logo">
        </div>
        <ul class="menu">
            <li><a href="${pageContext.request.contextPath}/view/admin/dashboard.jsp"><i class="fa fa-home"></i> Trang chủ</a></li>
            <li><a href="${pageContext.request.contextPath}/employee"><i class="fa fa-user"></i> Quản lý nhân viên</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/departments"><i class="fa fa-building"></i> Quản lý phòng ban</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/attendance" class="active"><i class="fa fa-calendar-check"></i> Quản lý chấm công</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/payroll"><i class="fa fa-money-bill"></i> Quản lý lương</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/leaves"><i class="fa fa-plane"></i> Quản lý nghỉ phép</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users"><i class="fa fa-users"></i> Quản lý tài khoản</a></li>
            <li><a href="${pageContext.request.contextPath}/view/auth/logout.jsp"><i class="fa fa-sign-out-alt"></i> Đăng xuất</a></li>
        </ul>
    </aside>

    <!-- Main Content -->
    <main class="content">
        <h2 class="page-title"><i class="fa fa-calendar-check"></i> Danh sách chấm công nhân viên</h2>

        <!-- Form tìm kiếm và lọc -->
        <form action="${pageContext.request.contextPath}/admin/attendance" method="get" class="filter-form">
            <div class="filter-group">
                <label for="empId">Mã nhân viên:</label>
                <input type="text" id="empId" name="empId" value="${param.empId}" placeholder="Nhập mã nhân viên">
            </div>

            <div class="filter-group">
                <label for="workDate">Ngày làm việc:</label>
                <input type="date" id="workDate" name="workDate" value="${param.workDate}">
            </div>

            <div class="filter-group buttons">
                <button type="submit" class="btn-search"><i class="fa fa-search"></i> Tìm kiếm</button>
                <a href="${pageContext.request.contextPath}/admin/attendance" class="btn-reset"><i class="fa fa-undo"></i> Làm mới</a>
            </div>
        </form>

        <!-- Bảng danh sách chấm công -->
        <div class="table-container">
            <table class="attendance-table">
                <thead>
                    <tr>
                        <th>Mã chấm công</th>
                        <th>Mã nhân viên</th>
                        <th>Ngày làm việc</th>
                        <th>Giờ vào</th>
                        <th>Giờ ra</th>
                        <th>Trạng thái</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty attendanceList}">
                            <c:forEach var="att" items="${attendanceList}">
                                <tr>
                                    <td>${att.attendanceId}</td>
                                    <td>${att.empId}</td>
                                    <td>${att.workDate}</td>
                                    <td>${att.checkIn}</td>
                                    <td>${att.checkOut}</td>
                                    <td>
                                        <span style="color:${att.statusColor}; font-weight:bold;">
                                            ${att.status}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="6" style="text-align:center; color:#888;">Không có dữ liệu chấm công.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </main>
</div>
</body>
</html>
