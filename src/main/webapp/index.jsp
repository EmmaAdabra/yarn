<%@taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Created by IntelliJ
  IDEA. User: Viewnet
  Date: 1/23/2025
  Time: 11:08 To change this template use File
| Settings | File Templates. --%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<html class="h-[100%] overflow-hidden">
<head>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@include file="/WEB-INF/views/fragments/head.jsp"%>
    <%@include file="WEB-INF/views/fragments/tinySliderLink.jsp"%>
    <title>Yarn | Home</title>
</head>
<body class="bg-bg_color1 h-[100%] overflow-hidden overflow-y-scroll relative">
    <%--page loader--%>
    <%@include file="WEB-INF/views/fragments/pageLoader.jsp"%>
    <!-- Toast Container -->
    <div id="toast-container" class="fixed top-4 right-4 z-[100] space-y-2"></div>

    <div class="" id="content">
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
                <div
                        class="md:col-span-3 col-span-full grid md:grid-cols-3 grid-cols-1 md:px-0 px-[10px] max-h-[calc(100vh-60px)] md:overflow-hidden overflow-auto" id="heroAndPostContainer">
                    <div
                            class="flex flex-col md:px-3 xl:max-w-[300px] xl:mx-auto sm:px-10 justify-center items-center md:col-span-1 col-span-full left-sidebar" >
                        <div class="w-full relative flex flex-col items-center gap-3 p-5 text-center md:overflow-hidden ">
                            <!-- Content -->
                            <div>
                                <h1
                                        class="lg:text-[5.5vmin] md:text-[4.5vmin] text-[5.5vmin] font-semibold text-title_text_clr hover:text-glow transition-colors duration-500">
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
                    <div
                            class="md:col-span-2 col-span-full sm:mx-10 md:overflow-auto center-content" id="scrollContainer">
                        <div
                                class="flex flex-col gap-3 items-center pt-6 text-main_text max-w-[700px] mx-auto">
                            <div class="text-center relative">
                                <h2
                                        class="font-semibold text-[4vmin] line">Recent Posts</h2>
                            </div>
                            <!-- display post -->
                            <%@include file="/WEB-INF/views/fragments/displayPosts.jsp"%>
                        </div>
                    </div>
                </div>
                <!-- right pane -->
                <div
                        class="hidden lg:block col-span-1 right-sidebar xl:pl-0 lg:pl-5  md:pt-5 index-right-pane"
                        id="rightPane">
                    <div class="mt-[60px] flex flex-col xl:items-center">
                        <%@include file="/WEB-INF/views/fragments/sponsoredAds.jsp"%>
                    </div>
                </div>
                <!-- quote container -->
                <div
                        class="hidden md:w-[280px] sm:w-[250px] w-[200px] bg-bg_color1 absolute top-5 right-3 rounded-2xl" id="quoteContainer">
                    <%@include file="/WEB-INF/views/fragments/quotes.jsp" %>
                </div>
            </section>

            <!-- Create Post Button (Always Visible) -->
            <button id="createPost" class="index-create-post fixed w-[40px] h-[40px] center-icon bottom-16 right-10 p-2 rounded-full bg-[#26a5f1] text-bg_color2 shadow-lg hover:bg-[#fdbf07] hover:scale-105 transition-all duration-300">
                <i class="ri-quill-pen-line text-2xl"></i>
            </button>

            <!-- Scroll to Top Button (Appears on Scroll) -->
            <button id="scrollToTopBtn"
                    class="hidden fixed w-[40px] h-[40px] justify-center bottom-5 right-16 p-2 rounded-full bg-[#26a5f1] text-bg_color2 text-[20px] font-semibold shadow-lg
                hover:bg-[#fdbf07] hover:scale-105 transition-all duration-300">
                <i class="ri-arrow-up-line"></i>
            </button>
        </main>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/tiny-slider/2.9.4/min/tiny-slider.js" integrity="sha512-j+F4W//4Pu39at5I8HC8q2l1BNz4OF3ju39HyWeqKQagW6ww3ZF9gFcu8rzUbyTDY7gEo/vqqzGte0UPpo65QQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src="<c:url value='/assets/js/toast.js'/>"></script>
    <script src="<c:url value='/assets/js/index.js'/>"></script>
    <script src="<c:url value='/assets/js/quote.js'/>"></script>
    <%--  Initialize sponsored ads slider  --%>
    <script>
        var slide = tns({
            container: '.food-carousel',
            items: 1,
            slideBy: 1,
            autoplay: true,
            autoplayTimeout: 8000,
            autoplayButtonOutput: false,
            speed: 600,
            controlsContainer: "#food-nav",
            // navPosition: "bottom",
            rewind: false,
            mode: "gallery",
            gutter: 20,
        });
    </script>
</body>
</html>