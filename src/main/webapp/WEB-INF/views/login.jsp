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
    <title>Sign In</title>
    <style>
        h1{
            text-align: center;
        }
        section{
            max-width: 600px;
            width: 100%;
            margin-inline: auto;
        }
        label, input {
            display: block;
            padding-block: 8px;
        }
    </style>
</head>
<body>
    <main>
        <section>
            <h1>Sign In</h1>
            <c:if test="${error}">
                <p id="error">${error}</p>
            </c:if>
            <form method="post">
                <label for="email"></label>
                <input type="email" id="email" name="email" placeholder="Email" required/>
                <label for="password"></label>
                <input type="password" id="password" name="password"/>

                <button>Sign In</button>
            </form>
        </section>
    </main>
</body>
</html>
