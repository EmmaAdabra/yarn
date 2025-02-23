<%@taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Created by IntelliJ
  IDEA. User: Viewnet
  Date: 1/23/2025
  Time: 11:08 To change this template use File
| Settings | File Templates. --%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<html class="h-[100%] overflow-hidden">
<head>
    <%@include file="/WEB-INF/views/fragments/head.jsp"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>ChatApp Dashboard</title>
</head>
<body class="bg-bg_color1 h-[100%] overflow-hidden overflow-y-scroll relative">
<!-- create post modal -->
<%@include file="/WEB-INF/views/fragments/createPost.jsp"%>
<%@include file="/WEB-INF/views/fragments/indexCreatePostNotice.jsp"%>
<!-- profile menu drop down -->
<%@include file="/WEB-INF/views/fragments/indexNavDropdownMenu.jsp"%>
<main class="h-[100vh w-[100%] flex flex-col container">
    <!-- include navbar -->
    <%@include file="/WEB-INF/views/fragments/indexTopNav.jsp"%>
    <section class="grid lg:grid-cols-4 md:grid-cols-3 grid-cols-1 mt-[60px] w-[100vw] h-[calc(100vh-60px)] main-content relative">

        <!-- left pane -->
        <div class="md:col-span-3 col-span-full grid md:grid-cols-3 grid-cols-1 md:px-0 px-10 max-h-[calc(100vh-60px)] md:overflow-hidden overflow-auto" id="heroAndPostContainer">
            <div class="flex flex-col md:px-3 sm:px-10 justify-center items-center md:col-span-1 col-span-full left-sidebar" >
                <div class="w-full relative flex flex-col items-center gap-3 p-5 bg-[#1e1e20]  text-center md:overflow-hidden ">
                    <!-- Content -->
                    <div>
                        <h1 class="text-[5.5vmin] font-semibold text-title_text_clr hover:text-glow transition-colors duration-500">
                            <span class="block line mx-auto">Join</span>
                             Interesting Conversations
                        </h1>
                        <p class="text-lg text-[#c2c2bf] font-light opacity-90 mt-3">
                            Say What's on Your Mind
                        </p>
                    </div>
                </div>
                <div class="bg-bg_color2 flex flex-col gap-3 items-center py-10 w-full  rounded-lg overflow-hidden">
                    <a class="block text-logo_clr mt-2" href="<c:url value="/login"/>">
                        <button class="yellow-btn primary-btns" id="signIn">Sign In</button>
                    </a>
                    <div class="flex items-center w-full text-fade_text text-sm">
                        <div class="flex-grow border-t border-borderClr"></div>
                        <span class="mx-4">OR</span>
                        <div class="flex-grow border-t  border-borderClr"></div>
                    </div>
                    <a href="<c:url value="/register"/>">
                        <button class="bg-gray-300 hover:bg-gray-200 text-gray-900 primary-btns" id="signUP">Sign UP</button>
                    </a>
                </div>
            </div>
            <!-- center pane -->
            <div class="md:col-span-2 col-span-full sm:mx-10 md:overflow-auto center-content" id="scrollContainer">
                <div class="flex flex-col gap-5 items-center pt-6 text-main_text">
                    <div class="flex items-center">
                      <h2 class="font-semibold text-[4vmin] line">Recent Posts</h2>
                    </div>
                    <!-- display post -->
                    <%@include file="/WEB-INF/views/fragments/displayPosts.jsp"%>
                </div>
            </div>
        </div>

        <!-- right pane -->
        <div class="hidden lg:block col-span-1 right-sidebar lg:pl-7  md:pt-5 md:relative md:top-0 md:right-0 md:w-full sm:w-[250px] w-[200px] absolute top-5 right-5 index-right-pane" id="rightPane">
            <%@include file="/WEB-INF/views/fragments/rightPane.jsp" %>
        </div>
    </section>

    <!-- Create Post Button (Always Visible) -->
    <button id="createPost" class="index-create-post fixed w-[40px] h-[40px] center-icon bottom-16 right-10 p-2 rounded-full bg-[#26a5f1] text-bg_color2 shadow-lg hover:bg-[#fdbf07] hover:scale-105 transition-all duration-300">
        <i class="ri-quill-pen-line text-2xl"></i>
    </button>

    <!-- Scroll to Top Button (Appears on Scroll) -->
    <button id="scrollToTopBtn"
            class="hidden fixed w-[40px] h-[40px] justify-center bottom-5 right-16 p-2 rounded-full bg-[#26a5f1] text-bg_color2 shadow-lg
            hover:bg-[#fdbf07] hover:scale-105 transition-all duration-300">
        ▲
    </button>
</main>

<script src="<c:url value='/assets/js/index.js'/>?v=<%= System.currentTimeMillis() %>"></script>
<script src="<c:url value="/assets/js/quote.js" />"></script>
</body>
</html>