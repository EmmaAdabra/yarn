<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Viewnet
  Date: 1/21/2025
  Time: 00:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/fragments/head.jspf"%>
    <title>Sign up for ChatApp</title>
    <style>
    </style>
</head>
<body class="text-center bg-bg_color1">
    <main>
        <header id="signupHeader">
            <h1 class="text-logo_clr1 text-2xl mb-2">Create Account</h1>
            <p class="text-title_text_clr">It's quick and easy.</p>
        </header>
        <section class="bg-bg_color2 px-8 py-11 mx-auto max-w-[600px]">
            <form method="post" action="/register" >
                <c:if test="${error != null}">
                    <p class="text-red-200 text-center pb-10" id="errors">${error}</p>
                </c:if>

                <label for="firstName"></label>
                <input class="input-field" type="text" id="firstName" name="firstName"
                       placeholder="First name" pattern="^[A-Za-z]+(['-]?[A-Za-z]*){0,49}$"
                       title="
                       Input must start with a letter, be 2 to 50 characters long, and
                       can optionally contain apostrophes (') or hyphens (-) only after a letter.
                       " maxlength=50 minlength=2 required
                />

                <label for="lastName"></label>
                <input class="input-field" type="text" id="lastName" name="lastName"
                       placeholder="Surname" pattern="^[A-Za-z]+(['-]?[A-Za-z]*){0,49}$"
                       title="
                       Input must start with a letter, be 2 to 50 characters long, and
                       can optionally contain apostrophes (') or hyphens (-) only after a letter.
                       " maxlength="50" minlength="2" required
                />

                <label for="username"></label>
                <input class="input-field" type="text" id="username" name="username" placeholder="Username"
                       pattern="[a-zA-Z][a-zA-Z0-9.]{4,50}(?<!\.)$"
                       title="
                        Input must start with a letter, be 5 to 50 characters long, can contain letters,
                        digits, and dots (.), but cannot end with a dot." minlength=4 maxlength=50
                />

                <label for="email"></label>
                <input class="input-field" type="email" id="email" name="email" placeholder="Email" required />

                <label for="password"></label>
                <input class="input-field" type="password" id="password" name="password" placeholder="Password" pattern=".{4,18}"
                   title="Input must be between 4 and 18 characters long." required
                />

                <label for="confirmPassword"></label>
                <input class="input-field" type="password" id="confirmPassword" name="confirmPassword" pattern=".{4,18}" placeholder="confirm Password" title="Input must be between 4 and 18 characters long." required
                />

                <button class="bg-gray-300 hover:bg-gray-200 text-gray-900 login-sign-btns">Sign Up</button>
            </form>
            <a class="block text-logo_clr mt-2" href="<c:url value="/login"/>">Already have an account</a>
        </section>
    </main>
    <script src="">
    </script>
</body>
</html>
