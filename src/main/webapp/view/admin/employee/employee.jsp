<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>HRM - Quản lý nhân viên</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin/employee.css">
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
            <li><a href="${pageContext.request.contextPath}/employee" class="active"><i class="fa fa-user"></i> Quản lý nhân viên</a></li>
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
    <div class="search-filter">
        <form action="employee" method="get" class="search-form">
            <input type="hidden" name="action" value="search">
            <label for="empId">Tìm kiếm nhân viên:</label>
            <input type="text" name="keyword" placeholder="Tìm theo tên hoặc ID..." value="${param.keyword}">

            <select name="sortOrder">
                <option value="desc" ${param.sortOrder == 'desc' ? 'selected' : ''}>Nhân viên mới nhất</option>
                <option value="asc" ${param.sortOrder == 'asc' ? 'selected' : ''}>Nhân viên cũ nhất</option>
            </select>

            <button type="submit" class="btn search"><i class="fa fa-search"></i> Tìm kiếm</button>
            <a href="employee" class="btn reset"><i class="fa fa-rotate-left"></i> Làm mới</a>
        </form>
    </div>

        <h2>Danh sách nhân viên</h2>

        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Tên</th>
                <th>Email</th>
                <th>SĐT</th>
                <th>Ngày sinh</th>
                <th>Ngày vào</th>
                <th>Lương</th>
                <th>Phòng ban</th>
                <th>Chức vụ</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="emp" items="${listEmployees}">
                <tr>
                    <td>${emp.emp_id}</td>
                    <td>${emp.name}</td>
                    <td>${emp.email}</td>
                    <td>${emp.phone}</td>
                    <td><fmt:formatDate value="${emp.dob}" pattern="yyyy-MM-dd"/></td>
                    <td><fmt:formatDate value="${emp.hire_date}" pattern="yyyy-MM-dd"/></td>
                    <td>${emp.salary}</td>
                    <td>${emp.department.dept_name}</td>
                    <td>${emp.position.position_name}</td>
                    <td>
                        <a class="btn edit" href="employee?action=edit&id=${emp.emp_id}">Sửa</a>
                        <a class="btn delete" href="employee?action=delete&id=${emp.emp_id}"
                           onclick="return confirm('Bạn có chắc chắn muốn xóa nhân viên này?');">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <hr/>

        <div class="form-container">
            <h3>
                <c:choose>
                    <c:when test="${not empty employee}">Sửa nhân viên</c:when>
                    <c:otherwise>Thêm nhân viên mới</c:otherwise>
                </c:choose>
            </h3>

            <form action="employee" method="post">
                <input type="hidden" name="action" value="save"/>
                <input type="hidden" name="emp_id" value="${employee.emp_id}"/>

                <p>Tên: <input type="text" name="name" value="${employee.name}" required/></p>
                <p>Email: <input type="email" name="email" value="${employee.email}" required/></p>
                <p>SĐT: <input type="text" name="phone" value="${employee.phone}"/></p>

                <p>Ngày sinh:
                    <input type="date" name="dob"
                           value="<fmt:formatDate value='${employee.dob}' pattern='yyyy-MM-dd'/>"/>
                </p>
                <p>Ngày vào:
                    <input type="date" name="hire_date"
                           value="<fmt:formatDate value='${employee.hire_date}' pattern='yyyy-MM-dd'/>"/>
                </p>

                <p>Lương: <input type="number" step="0.01" name="salary" value="${employee.salary}"/></p>

                <p>Phòng ban:
                    <select name="dept_id">
                        <c:forEach var="dept" items="${departments}">
                            <option value="${dept.dept_id}"
                                    <c:if test="${employee.department.dept_id == dept.dept_id}">selected</c:if>>
                                ${dept.dept_name}
                            </option>
                        </c:forEach>
                    </select>
                </p>

                <p>Chức vụ:
                    <select name="position_id">
                        <c:forEach var="pos" items="${positions}">
                            <option value="${pos.position_id}"
                                    <c:if test="${employee.position.position_id == pos.position_id}">selected</c:if>>
                                ${pos.position_name}
                            </option>
                        </c:forEach>
                    </select>
                </p>

                <button type="submit" class="btn save">Lưu</button>
                <a href="employee" class="btn cancel">Hủy</a>
            </form>
        </div>
    </div>
</div>
</body>
</html>
