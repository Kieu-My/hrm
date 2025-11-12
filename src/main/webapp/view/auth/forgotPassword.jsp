<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hệ Thống Quản Lí Nhân Sự Công Ty</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="login-container">
        <!-- Phần bên trái -->
        <div class="login-left">
            <div class="banner-section">
                <h1 class="system-title">Hệ Thống Quản Lí Nhân Sự Công Ty</h1>
                <div class="banner-image">
                    <img src="<%=request.getContextPath()%>/assets/images/4565.jpg" alt="Login Banner">
                </div>
            </div>
        </div>

        <!-- Phần bên phải -->
        <div class="login-right">
            <div class="login-form-container">
                <h2 class="login-title">Quên mật khẩu</h2>
                <form action="${pageContext.request.contextPath}/forgotPassword" method="post">
                    <div class="form-group">
                        <label for="email"><i class="fas fa-user"></i></label>
                        <input type="text" id="email" name="email" placeholder="Email" required>
                    </div>
                    <button type="submit" class="login-btn">Gửi</button>
                </form>

                <!-- Thông báo lỗi hoặc thành công -->
                <c:if test="${not empty error}">
                    <p style="color:red">${error}</p>
                </c:if>
                <c:if test="${not empty message}">
                    <p style="color:green">${message}</p>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
