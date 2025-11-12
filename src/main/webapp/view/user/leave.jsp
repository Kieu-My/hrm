<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đơn nghỉ phép</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/user/leave.css">
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
            <li><a href="${pageContext.request.contextPath}/user/attendance"><i class="fa fa-calendar-check"></i> Bảng chấm công</a></li>
            <li><a href="${pageContext.request.contextPath}/user/payroll"><i class="fa fa-money-bill"></i> Bảng lương</a></li>
            <li class="active"><a href="${pageContext.request.contextPath}/user/employeeLeave"><i class="fa fa-plane"></i> Đơn nghỉ phép</a></li>
            <li><a href="${pageContext.request.contextPath}/view/auth/logout.jsp"><i class="fa fa-sign-out-alt"></i> Đăng xuất</a></li>
        </ul>
    </div>

    <!-- Content -->
    <div class="content">
        <h2>Đăng ký / Sửa đơn nghỉ phép</h2>

        <!-- Nếu editLeave tồn tại thì form ở trạng thái update, ngược lại insert -->
        <c:set var="isEdit" value="${not empty editLeave}" />
        <form action="${pageContext.request.contextPath}/user/employeeLeave" method="post" class="leave-form">
            <c:if test="${isEdit}">
                <input type="hidden" name="action" value="update"/>
                <input type="hidden" name="leave_id" value="${editLeave.leave_id}" />
            </c:if>
            <c:if test="${not isEdit}">
                <input type="hidden" name="action" value="insert"/>
            </c:if>

            <div class="form-group">
                <label>Từ ngày:</label>
                <input type="date" name="start_date" required
                       value="${isEdit ? editLeave.start_date : ''}" />
            </div>

            <div class="form-group">
                <label>Đến ngày:</label>
                <input type="date" name="end_date" required
                       value="${isEdit ? editLeave.end_date : ''}" />
            </div>

            <div class="form-group">
                <label>Lý do:</label>
                <textarea name="reason" rows="3" required>${isEdit ? editLeave.reason : ''}</textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-submit">
                    <c:choose>
                        <c:when test="${isEdit}">Cập nhật</c:when>
                        <c:otherwise>Gửi đơn</c:otherwise>
                    </c:choose>
                </button>

                <c:if test="${isEdit}">
                    <a href="${pageContext.request.contextPath}/user/employeeLeave" class="btn-cancel">Hủy</a>
                </c:if>
            </div>
        </form>

        <h2>Danh sách đơn nghỉ phép của bạn</h2>
        <div class="table-wrap">
            <table class="leave-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Từ ngày</th>
                        <th>Đến ngày</th>
                        <th>Lý do</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty leaves}">
                            <c:forEach var="l" items="${leaves}">
                                <tr>
                                    <td>${l.leave_id}</td>
                                    <td>${l.start_date}</td>
                                    <td>${l.end_date}</td>
                                    <td>${l.reason}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${l.status eq 'APPROVED'}">Đã duyệt</c:when>
                                            <c:when test="${l.status eq 'REJECTED'}">Bị từ chối</c:when>
                                            <c:otherwise>Chờ duyệt</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/user/employeeLeave?action=edit&leave_id=${l.leave_id}" class="btn-edit">
                                            <i class="fa fa-edit"></i> Sửa
                                        </a>
                                        <a href="${pageContext.request.contextPath}/user/employeeLeave?action=delete&leave_id=${l.leave_id}" class="btn-delete"
                                           onclick="return confirm('Bạn có chắc muốn xóa đơn nghỉ này?');">
                                            <i class="fa fa-trash"></i> Xóa
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="6" style="text-align:center; color:gray;">Chưa có đơn nghỉ nào</td>
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
