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
    <%@include file="/WEB-INF/views/fragments/head.jsp"%>
    <title>Yarn | Sign up</title>
</head>
<body class="text-center bg-bg_color1">
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
    <main class="pb-7">
        <header class="pb-[25px]" id="signupHeader">
            <h1 class="text-title_text_clr text-2xl mb-2 mx-auto line">Create Account</h1>
            <p class="text-title_text_clr">It's quick and easy.</p>
        </header>
        <section class="bg-bg_color2 sm:px-8 px-3 py-7 mx-auto max-w-[600px] rounded">
            <form method="post" action="/register" >
                <c:if test="${error != null}">
                    <p class="text-red-200 text-center pb-10" id="errors">${error}</p>
                </c:if>

                <label for="firstName"></label>
                <input class="input-field text-title_text_clr" type="text" id="firstName" name="firstName"
                       placeholder="First name" pattern="^[A-Za-z]+(['-]?[A-Za-z]*){0,49}$"
                       title="
                       Input must start with a letter, be 2 to 50 characters long, and
                       can optionally contain apostrophes (') or hyphens (-) only after a letter.
                       " maxlength=50 minlength=2
                />

                <label for="lastName"></label>
                <input class="input-field text-title_text_clr" type="text" id="lastName" name="lastName"
                       placeholder="Surname" pattern="^[A-Za-z]+(['-]?[A-Za-z]*){0,49}$"
                       title="
                       Input must start with a letter, be 2 to 50 characters long, and
                       can optionally contain apostrophes (') or hyphens (-) only after a letter.
                       " maxlength="50" minlength="2"
                />

                <label for="username"></label>
                <input class="input-field text-title_text_clr" type="text" id="username" name="username" placeholder="Username"
                       pattern="[a-zA-Z][a-zA-Z0-9.]{4,50}(?<!\.)$"
                       title="
                        Input must start with a letter, be 5 to 50 characters long, can contain letters,
                        digits, and dots (.), but cannot end with a dot." minlength=4 maxlength=50
                />

                <label for="email"></label>
                <input class="input-field text-title_text_clr" type="email" id="email" name="email" placeholder="Email" required />

                <label for="password"></label>
                <input class="input-field text-title_text_clr" type="password" id="password" name="password" placeholder="Password" pattern=".{4,18}"
                   title="Input must be between 4 and 18 characters long." required
                />

                <label for="confirmPassword"></label>
                <input class="input-field text-title_text_clr" type="password" id="confirmPassword" name="confirmPassword" pattern=".{4,18}" placeholder="confirm Password" title="Input must be between 4 and 18 characters long." required
                />

                <button class="bg-gray-300 hover:bg-gray-200 text-gray-900 primary-btns">Sign Up</button>
            </form>
            <a class="block text-logo_clr mt-2" href="<c:url value="/login"/>">Already have an account</a>
        </section>
    </main>
</body>
</html>
