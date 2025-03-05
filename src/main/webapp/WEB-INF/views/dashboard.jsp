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
    <title>Yarn | Dashboard</title>
  </head>
  <body class="bg-bg_color1 h-[100%] overflow-hidden overflow-y-scroll relative" data-user="${sessionScope.sessionUser.userId}">
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
        <div class="md:col-span-2 md:px-0 px-10 overflow-auto sm:mx-10 col-span-full center-content scroll-container" id="scrollContainer">
          <%@include file="/WEB-INF/views/fragments/centerPane.jsp" %>
      </div>

      <!-- right pane -->
      <div class="hidden md:block col-span-1 right-sidebar lg:pl-7  md:pt-5 md:relative md:top-0 md:right-0 md:w-full sm:w-[250px] w-[200px] absolute top-5 right-5" id="rightPane">
          <%@include file="/WEB-INF/views/fragments/rightPane.jsp" %>
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
       ▲
    </button>
    </main>
     
    <script src="<c:url value='/assets/js/index.js'/>?v=<%= System.currentTimeMillis() %>"></script>
    <script src="<c:url value="/assets/js/quote.js" />"></script>
  </body>
</html>