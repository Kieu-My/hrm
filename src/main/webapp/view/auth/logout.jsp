<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Hủy session hiện tại
    session.invalidate();

    // Chuyển hướng về trang login.jsp
    response.sendRedirect("login.jsp");
%>
