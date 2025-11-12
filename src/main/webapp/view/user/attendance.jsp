<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>HRM - Hệ Thống Chấm Công</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/user/attendance.css">
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
            <li><a href="${pageContext.request.contextPath}/user/employeeLeave"><i class="fa fa-plane"></i> Nghỉ phép</a></li>
            <li><a href="${pageContext.request.contextPath}/view/auth/logout.jsp"><i class="fa fa-sign-out-alt"></i> Đăng xuất</a></li>
        </ul>
    </div>

    <!-- Content -->
    <div class="content">

        <!-- Header -->
        <div class="header">
            <h1>🕐 Hệ Thống Chấm Công</h1>
            <p>Quản lý thời gian làm việc hiệu quả</p>
        </div>

        <!-- Tabs -->
        <div class="tabs">
            <a href="${pageContext.request.contextPath}/user/attendance/form"
               class="tab ${currentPage eq 'form' ? 'active' : ''}">
                📝 Biểu Mẫu Chấm Công
            </a>
            <a href="${pageContext.request.contextPath}/user/attendance/list"
               class="tab ${currentPage eq 'list' or empty currentPage ? 'active' : ''}">
                📊 Danh Sách Chấm Công
            </a>
        </div>

        <!-- Biểu mẫu chấm công -->
        <c:if test="${currentPage eq 'form' or currentPage eq 'edit'}">
            <div class="form-section">
                <h2>
                    <c:choose>
                        <c:when test="${currentPage eq 'edit'}">✏️ Cập Nhật Chấm Công</c:when>
                        <c:otherwise>📝 Biểu Mẫu Chấm Công</c:otherwise>
                    </c:choose>
                </h2>

                <!-- ✅ form action sửa đúng /insert -->
                <form action="${pageContext.request.contextPath}/user/attendance/${currentPage eq 'edit' ? 'update' : 'insert'}"
                      method="post" class="attendance-form">

                    <c:if test="${currentPage eq 'edit'}">
                        <input type="hidden" name="attendance_id" value="${attendance.attendanceId}">
                    </c:if>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="emp_id">Mã Nhân Viên *</label>
                            <input type="number" id="emp_id" name="emp_id"
                                   value="${attendance.empId}"
                                   required min="1" placeholder="Nhập mã nhân viên">
                        </div>

                        <div class="form-group">
                            <label for="work_date">Ngày Làm Việc *</label>
                            <input type="date" id="work_date" name="work_date"
                                   value="<fmt:formatDate value='${attendance.workDate}' pattern='yyyy-MM-dd'/>"
                                   required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="check_in">Giờ Vào *</label>
                            <input type="time" id="check_in" name="check_in"
                                   value="${fn:substring(attendance.checkIn,0,5)}"
                                   required>
                        </div>
                        <div class="form-group">
                            <label for="check_out">Giờ Tan</label>
                            <input type="time" id="check_out" name="check_out"
                                   value="${fn:substring(attendance.checkOut,0,5)}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="status">Trạng Thái *</label>
                        <select id="status" name="status" required>
                            <option value="">-- Chọn trạng thái --</option>
                            <option value="Present" <c:if test="${attendance.status eq 'Present'}">selected</c:if>>✅ Đang Làm</option>
                            <option value="Late" <c:if test="${attendance.status eq 'Late'}">selected</c:if>>⏰ Đi Muộn</option>
                            <option value="Absent" <c:if test="${attendance.status eq 'Absent'}">selected</c:if>>❌ Vắng</option>
                            <option value="On Leave" <c:if test="${attendance.status eq 'On Leave'}">selected</c:if>>🏖️ Nghỉ Phép</option>
                        </select>
                    </div>


                    <div class="actions">
                        <button type="submit" class="btn btn-primary">
                            <c:choose>
                                <c:when test="${currentPage eq 'edit'}">✏️ Cập Nhật</c:when>
                                <c:otherwise>💾 Lưu Chấm Công</c:otherwise>
                            </c:choose>
                        </button>
                        <a href="${pageContext.request.contextPath}/user/attendance/list" class="btn btn-secondary">📊 Danh Sách</a>
                        <button type="reset" class="btn btn-secondary">🔄 Làm Mới</button>
                    </div>
                </form>
            </div>
        </c:if>

        <!-- Danh sách chấm công -->
        <c:if test="${currentPage eq 'list' or empty currentPage}">
            <div class="form-section">
                <h2>📊 Danh Sách Chấm Công</h2>

                <table class="table">
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>Mã NV</th>
                        <th>Ngày</th>
                        <th>Giờ Vào</th>
                        <th>Giờ Tan</th>
                        <th>Trạng Thái</th>
                        <th>Thao Tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${not empty attendanceList}">
                            <c:forEach items="${attendanceList}" var="att" varStatus="st">
                                <tr>
                                    <td>${st.index + 1}</td>
                                    <td>${att.empId}</td>
                                    <td><fmt:formatDate value="${att.workDate}" pattern="dd/MM/yyyy"/></td>
                                    <td><fmt:formatDate value="${att.checkIn}" pattern="HH:mm"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty att.checkOut}">
                                                <fmt:formatDate value="${att.checkOut}" pattern="HH:mm"/>
                                            </c:when>
                                            <c:otherwise><i>Chưa tan</i></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${att.status}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/user/attendance/edit?id=${att.attendanceId}" class="btn btn-small btn-success">✏️</a>
                                        <a href="${pageContext.request.contextPath}/user/attendance/delete?id=${att.attendanceId}"
                                           class="btn btn-small btn-danger"
                                           onclick="return confirm('Bạn có chắc muốn xóa bản ghi này?')">🗑️</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr><td colspan="7" style="text-align:center;">📋 Chưa có dữ liệu</td></tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </c:if>

    </div> <!-- content -->
</div> <!-- container -->
</body>
</html>
