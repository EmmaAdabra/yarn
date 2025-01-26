<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Viewnet
  Date: 1/22/2025
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/fragments/head.jspf"%>
    <title>Login to ChatApp</title>
</head>
<body class="bg-bg_color1">
    <main class="text-center">
        <h1 class="text-logo_clr1 text-2xl my-8">ChatApp</h1>
        <section id="loginContainer" class="bg-bg_color2 px-8 pb-11 mx-auto">
            <p class="text-title_text_clr py-7">Login to ChatApp</p>
            <c:if test="${error != null}">
                <p class="text-red-200 text-center pb-10" id="error">${error}</p>
            </c:if>
            <form method="post" action="/login">
                <label class="" for="email"></label>
                <input class="input-field" type="email" id="email" name="email" placeholder="Email" required/>
                <label class="text-title_text_clr" for="password"></label>
                <input class="input-field" type="password" id="password" name="password" placeholder="Password" pattern=".{4,18}" required/>
                <button class="bg-yellow-700 hover:bg-yellow-600 text-gray-200 login-sign-btns" id="signIn">Sign In</button>
                
                <div class="bg-bg_color1 rounded-md mt-7 mb-10" id="divider"></div>
            </form>
            <a href="<c:url value="/register"/>">
                <button class="bg-gray-300 hover:bg-gray-200 text-gray-900 login-sign-btns" id="signUP">Create Account</button>
            </a>
        </section>
    </main>
</body>
</html>
