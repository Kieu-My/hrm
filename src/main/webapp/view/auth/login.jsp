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
    <div class="login-left">
        <div class="banner-section">
            <h1 class="system-title">Hệ Thống Quản Lí Nhân Sự Công Ty</h1>
            <div class="banner-image">
                <img src="<%=request.getContextPath()%>/assets/images/4565.jpg" alt="Login Banner">
            </div>
        </div>
    </div>

    <div class="login-right">
        <div class="login-form-container">
            <h2 class="login-title">Đăng nhập</h2>

            <form action="<%=request.getContextPath()%>/login" method="post">
                <div class="form-group">
                    <label for="email"><i class="fas fa-user"></i></label>
                    <input type="text" id="email" name="email" placeholder="Email" required>
                </div>

                <div class="form-group">
                    <label for="password"><i class="fas fa-lock"></i></label>
                    <input type="password" id="password" name="password" placeholder="Mật khẩu" required>
                    <span class="toggle-password" onclick="togglePassword()">
                        <i class="fas fa-eye" id="eye-icon"></i>
                    </span>
                </div>

                <div class="form-options">
                    <label class="remember-me">
                        <input type="checkbox" name="remember" value="true">
                        <span class="checkmark"></span>
                        Ghi nhớ đăng nhập
                    </label>
                    <a href="view/auth/forgotPassword.jsp" class="forgot-password">Quên mật khẩu?</a>
                </div>

                <button type="submit" class="login-btn">Đăng nhập</button>
            </form>

            <% if (request.getAttribute("error") != null) { %>
                <div class="error-message">
                    <i class="fas fa-exclamation-circle"></i>
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>

            <% if (request.getAttribute("success") != null) { %>
                <div class="success-message">
                    <i class="fas fa-check-circle"></i>
                    <%= request.getAttribute("success") %>
                </div>
            <% } %>
        </div>
    </div>
</div>

<script>
    function togglePassword() {
        const passwordField = document.getElementById('password');
        const eyeIcon = document.getElementById('eye-icon');
        if (passwordField.type === 'password') {
            passwordField.type = 'text';
            eyeIcon.classList.remove('fa-eye');
            eyeIcon.classList.add('fa-eye-slash');
        } else {
            passwordField.type = 'password';
            eyeIcon.classList.remove('fa-eye-slash');
            eyeIcon.classList.add('fa-eye');
        }
    }
</script>
</body>
</html>
