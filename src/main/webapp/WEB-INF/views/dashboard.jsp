<%@taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Created by IntelliJ
  IDEA. User: Viewnet
  Date: 1/23/2025
  Time: 11:08 To change this template use File
| Settings | File Templates. --%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<html class="h-[100%] overflow-hidden">
  <head>
     <%@include file="/WEB-INF/views/fragments/head.jspf"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>ChatApp Dashboard</title>
  </head>
  <body class="bg-bg_color1 h-[100%] overflow-hidden overflow-y-scroll relative">
    <!-- create post modal -->
    <%@include file="/WEB-INF/views/fragments/createPost.jspf"%>
    <!-- profile menu drop down -->
    <%@include file="/WEB-INF/views/fragments/topNavDropdownMenu.jspf"%>
    <main class="h-[100vh w-[100%] flex flex-col container">
      <!-- include navbar -->
      <%@include file="/WEB-INF/views/fragments/topNavBar.jspf"%>
      <section class="grid grid-cols-4 mt-[60px] w-[100vw] h-[calc(100vh-60px)] main-content">
        <div class="col-span-1  left-sidebar" >
          <div class="side-menu overflow-auto " data-simplebar>
            <ul class="list-none menu-options ps-4 pt-8">
              <li class="flex items-center gap-2 option">
                <span class="flex justify-center items-center w-[35px] h-[35px] text-fade_text text-[20px] uppercase rounded-full bg-bg_color3">
                  <%@include file="/WEB-INF/views/fragments/pfpWrapper.jspf"%>
                </span>
                <a class="text-title_text_clr" href="">${sessionScope.sessionUser.getUserFullName()}</a>
              </li>
            </ul>
          </div>
        </div>
        <div class="col-span-2 overflow-auto px-7 mx-10 center-content">
          <div class="flex flex-col gap-5 items-center pt-6 text-main_text">
            <div class="flex items-center gap-3 bg-bg_color2 px-3 py-2 w-full rounded-md shadow-sm shadow-fade_text">
              <div class="w-[40px] h-[40px]">
                <%@include file="/WEB-INF/views/fragments/pfpWrapper.jspf" %>
              </div>
              <button class="flex-auto bg-bg_color1 px-1 py-[4px] flex items-center rounded-[30px] cursor-pointer hover:shadow-sm hover:shadow-fade_text" id="createPostBtn">
              <span class="flex-none block text-fade_text text-[18px] w-fit p-1">
                <i class="ri-pencil-line"></i>
              </span>
              <span class="flex-auto bg-[inherit] text-main_text text-left px-1 pointer-events-none">Create a post</span>
              </button>
            </div>
            <!-- display post -->
            <div class="post-container flex flex-col gap-5 items-center pt-6 pb-10 text-main_text w-full">
              <c:forEach var="post" items="${posts}">
                <div class="bg-bg_color2 w-full rounded-md post">
                  <div data-comment-modal="ModalContainer">
                    <div data-comment-modal="postContainer">
                      <header class="comment-modal-header hidden border-b-borderClr bg-bg_color2" data-comment-modal="modalHeader">
                        <h2 class="text-title_text_clr text-[18px]">${post.posterData.firstName}'s Post</h2>
                        <button class="modal-close-icon close-comment-btn">
                          <i class="ri-close-large-fill text-[25px]"></i>
                      </button>
                      </header>
                      <div class="px-1 py-0" data-comment-modal="postTextComment" data-simplebar>
                        <div class="px-3">
                          <div class="flex items-center justify-start gap-3 post-header pt-3 b-1.5">
                            <span class="flex justify-center items-center w-[40px] h-[40px] text-fade_text text-[20px] uppercase rounded-full bg-bg_color3">
                              <c:choose>
                                <c:when test="${not empty post.posterData.pfp}">
                                  <img class="object-cover w-[100%] h-[100%] rounded-full" src="${post.posterData.pfp}" alt="profile picture">
                                </c:when>
                                <c:otherwise>
                                  <span class="p-2">
                                      ${post.posterData.initial}
                                  </span>
                                </c:otherwise>
                              </c:choose>
                            </span>
                            <div class="flex flex-col g-[10px]">
                              <h3 class="poster">
                                <a class="text-title_text_clr text-[16px]" href="">${post.posterData.name}</a>
                              </h3>
                              <span class="post-time text-fade_text text-[12px]">
                                ${post.postDate}
                              </span>
                            </div>
                          </div>
                          <div class="pt-2 text-container">
                            <p class="post-text ellipsis">
                              ${post.content}
                            </p>
                          </div>
                          <div class="">
                            <button class="see-more-btn cursor-pointer  text-logo_clr1 hidden"> See more</button>
                          </div>
                          <div class="w-full mt-2 flex justify-center">
                            <c:if test="${not empty post.media}">
                              <img class="object-cover max-w-[500px] w-full h-auto rounded-md" src="${post.media}"
                                    alt="">
                            </c:if>
                          </div>
                          <div class="comments-container hidden mt-5 pt-5 border-t border-borderClr" data-comment-modal="commentContainer">
                            <!-- <p class="bg-bg_color3 rounded-lg py-2 text-fade_text text-center mb-5">No comment</p> -->
                            <div class="comment mb-5">
                              <div class="flex gap-1">
                                <span class="flex justify-center items-center w-[40px] h-[40px] text-fade_text text-[25px] uppercase rounded-full">
                                  <span>mj</span>
                                </span>
                                <div class="bg-bg_color3 w-fit text-main_text p-2 rounded-lg">
                                  <h3 class="text-title_text_clr mb-1 text-[12px]">John Doe <span class="text-fade_text text-[12px]"> Today at 7:50</span></h3>
                                  <p>I love your post</p>
                                </div>
                                
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="post-comment-box hidden border-t-borderClr bg-bg_color2" data-comment-modal="commentBox">
                        <span class="flex justify-center items-center w-[40px] h-[40px] text-fade_text text-[20px] uppercase rounded-full bg-bg_color3">
                          <%@include file="/WEB-INF/views/fragments/pfpWrapper.jspf"%>
                        </span>
                        <form class="w-full comment-form" action="" >
                          <div class="relative">
                            <textarea class="p-3 border-none outline-none resize-none w-full overflow-auto bg-bg_color1 text-main_text rounded-md min-h-[60px] max-h-[320px]" placeholder="Let's have it !!" name="comment" maxlength="1001"></textarea>
                            <button class="absolute top-[50%] translate-y-[-50%] right-4 text-[25px] text-fade_text cursor-not-allowed" data-post-id="${post.getPostId()}" disabled>
                              <i class="ri-send-plane-2-fill"></i>
                            </button>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                  <div class="post-props px-3 mt-2">
                    <ul>
                      <li class="comment w-fit">
                        <a class="flex items-center gap-[5px] text-fade_text relative z-10 comment-icon" href="">
                          <button class="icon text-[25px]">
                            <i class="ri-chat-1-line"></i>
                          </button>
                          <span class="comment-number text-[12px]">3</span>
                        </a>
                      </li>
                    </ul>
                  </div>
                </div>
              </c:forEach>
            </div>
          </div>
        </div>
        <div class="col-span-1 right-sidebar">

        </div>
      </section>
    </main>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/simplebar/6.3.0/simplebar.min.js"
            integrity="sha512-YumGHjm0sYk55Xdh6t6Uo/mHqBhDBNrW46HZKSBwkjq3X1Knnj7e3UUom2SE9zPpfjlTyJqSHnd4No1ca156cQ=="
            crossorigin="anonymous" referrerpolicy="no-referrer">
    </script>
    <script src="<c:url value='/assets/js/index.js'/>?v=<%= System.currentTimeMillis() %>"></script>
  </body>
</html>