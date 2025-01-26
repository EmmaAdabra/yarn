<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Viewnet
  Date: 1/23/2025
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link href="<c:url value='/assets/css/tailwind.styles.css' />" rel="stylesheet">
    <title>ChatApp</title>
    <style>
        body{
            padding-inline: 30px;
            color: #333;
        }

        /*#dashboard{*/
        /*    padding: 20px;*/
        /*    border: 1px solid #222;*/
        /*}*/
        header{
            min-height: 90px;
            margin-bottom: 20px;
        }
        nav{
            position: relative;
        }
        h1{
            text-align: center;
            font-family: -apple-system;
            font-weight: 400;
            font-size: 22px;
            text-transform: uppercase;
        }
        #logout{
            text-decoration: none;
            color: #333;
            text-align: right;
            letter-spacing: 1.7px;
            cursor: pointer;
            position: absolute;
            right: 20px;
            top: 0;
        }
        #paneContainer{
            /*width: 100%;*/
            display: grid;
            grid-template-columns: 2fr 4fr 2fr;
            gap: 20px;
            min-height: 100vh;
        }
        header, .pane{
            border: 1px solid #333;
        }
        .pane{
            width: 100%;
        }
        #rightPane{
            max-height: 50%;
        }
    </style>
</head>
<body class="bg-bg_color1 text-gray-800">
    <section id="dashboard">
        <header>
            <nav>
                <h1 class="">Dashboard</h1>
                <a id="logout" href="<C:url value="/logout" />">Logout</a>
            </nav>
        </header>
        <section id="paneContainer">
            <section class="pane" id="leftPane"></section>
            <section class="pane" id="centerPane">

            </section>
            <section class="pane" id="rightPane"></section>
        </section>
    </section>
    <script src="<c:url value="/assets/js/index.js" />"></script>
</body>
</html>
