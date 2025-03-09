<%@taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <%@include file="/WEB-INF/views/fragments/tinySliderLink.jsp"%>
    <title>Yarn | Dashboard</title>
  </head>
  <body class="bg-bg_color1 h-[100%] overflow-hidden overflow-y-scroll scroll-smooth relative"
        data-user="${sessionScope.sessionUser.userId}">
    <%--page loader--%>
    <%@include file="/WEB-INF/views/fragments/pageLoader.jsp"%>

    <div class="hidden" id="content">
      <!-- confirm modal -->
      <%@include file="/WEB-INF/views/fragments/deleteConfirmationModal.jsp"%>
      <!-- create post modal -->
      <%@include file="/WEB-INF/views/fragments/createPost.jsp"%>
      <!-- profile menu drop down -->
      <%@include file="/WEB-INF/views/fragments/topNavDropdownMenu.jsp"%>
      <main class="h-[100vh w-[100%] flex flex-col container">
        <!-- include navbar -->
        <%@include file="/WEB-INF/views/fragments/topNavBar.jsp"%>
        <section class="grid lg:grid-cols-4 md:grid-cols-3 grid-cols-1 mt-[60px] w-[100vw] h-[calc(100vh-60px)] main-content relative">

          <!-- left nav bar -->
          <div class="hidden lg:block col-span-1 left-sidebar row-span-full" >
            <%@include file="/WEB-INF/views/fragments/leftNavBar.jsp" %>
          </div>

          <!-- center pane -->
          <div
                  class="md:col-span-2 md:px-0 px-10 overflow-auto sm:mx-10 col-span-full center-content scroll-container" id="scrollContainer">
            <%@include file="/WEB-INF/views/fragments/centerPane.jsp" %>
          </div>

          <!-- right pane -->
          <div
                  class="hidden md:block col-span-1 right-sidebar lg:pl-5  md:pt-5"
                  id="rightPane">
            <div class="mt-[60px] flex flex-col xl:items-center">
              <div class="w-full">
                <h3
                        class="text-title_text_clr  sm:text-xl text-[16px] pl-2 pb-2 tracking-wide relative
                     before:absolute before:w-full before:h-full before:left-0 before:top-0 before:blur-md
                    before:content-['Get\20Inspired'] before:text-extra1_clr1/80 before:-z-10 before:animate-pulse">
                  Sponsored
                </h3>
              </div>

              <%@include file="/WEB-INF/views/fragments/sponsoredAds.jsp"%>
            </div>
          </div>

          <%--     quote container   --%>
          <div class="hidden md:w-[280px] sm:w-[250px] w-[200px] bg-bg_color1 absolute top-5 right-3 rounded-2xl" id="quoteContainer">
            <%@include file="/WEB-INF/views/fragments/quotes.jsp" %>
          </div>
        </section>

        <!-- Create Post Button (Always Visible) -->
        <button id="createPost" class="create-post fixed w-[40px] h-[40px] center-icon bottom-16 right-10 p-2 rounded-full bg-[#26a5f1] text-bg_color2 shadow-lg hover:bg-[#fdbf07] hover:scale-105 transition-all duration-300">
          <i class="ri-quill-pen-line text-2xl"></i>
        </button>

        <!-- Scroll to Top Button (Appears on Scroll) -->
        <button id="scrollToTopBtn"
                class="hidden fixed w-[40px] h-[40px] justify-center bottom-5 right-16 p-2 rounded-full bg-[#26a5f1] text-bg_color2 shadow-lg
            hover:bg-[#fdbf07] hover:scale-105 transition-all duration-300">
          <i class="ri-arrow-up-line"></i>
        </button>
      </main>
    </div>
    <script src="<c:url value='/assets/js/index.js'/>?v=<%= System.currentTimeMillis() %>"></script>
    <script src="<c:url value='/assets/js/quote.js'/>?v=<%= System.currentTimeMillis() %>"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tiny-slider/2.9.4/min/tiny-slider.js" integrity="sha512-j+F4W//4Pu39at5I8HC8q2l1BNz4OF3ju39HyWeqKQagW6ww3ZF9gFcu8rzUbyTDY7gEo/vqqzGte0UPpo65QQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

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