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
    <%@include file="/WEB-INF/views/fragments/head.jsp"%>
    <title>Login to Yarn</title>
</head>
<body class="bg-bg_color1">
    <!-- brand logo -->
    <div class="p-2">
        <a class="flex w-fit items-center py-1 px-2 gap-2 option hover:bg-bg_color2 rounded-2xl" href="<c:url value="/"/>">
            <span class="text-fade_text flex justify-center items-center w-[40px] h-[40px] uppercase rounded-full bg-bg_color3">
              <img class="object-cover h-[34px] w-[34px]" src="<c:url value="/assets/images/logo.png" />" alt="">
            </span>
            <span class="text-[18px] text-title_text_clr">
                Yarn
            </span>
        </a>
    </div>
    <main class="text-center ">
        <h1 class="text-title_text_clr text-2xl mb-8 line mx-auto">Sign In</h1>
        <section id="loginContainer" class="bg-bg_color2 px-8 pb-11 mx-auto rounded">
            <p class="text-title_text_clr py-7">Login to Yarn</p>
            <c:if test="${error != null}">
                <p class="text-red-200 text-center pb-10" id="error">${error}</p>
            </c:if>
            <form method="post" action="/login">
                <label class="" for="email"></label>
                <input class="input-field text-title_text_clr" type="email" id="email" name="email" placeholder="Email" autocomplete="email" required/>
                <label class="text-title_text_clr" for="password"></label>
                <input class="input-field text-title_text_clr" type="password" id="password" name="password" placeholder="Password" autocomplete="current-password" pattern=".{4,18}" required/>
                <button class="yellow-btn primary-btns" id="signIn">Sign In</button>
                
                <div class="bg-borderClr rounded-md mt-7 mb-10" id="divider"></div>
            </form>
            <a href="<c:url value="/register"/>">
                <button class="bg-gray-300 hover:bg-gray-200 text-gray-900 primary-btns" id="signUP">Create Account</button>
            </a>
        </section>
    </main>
<%--    <script src="<c:url value="/assets/js/index.js" />"></script>--%>
</body>
</html>
