<div class="post-container flex flex-col gap-5 items-center pt-6 pb-10 text-main_text w-full">
  <c:forEach var="post" items="${posts}">
    <div class="bg-bg_color2 w-full border border-borderClr shadow-lg rounded-md post" id="post-${post.postId}">
      <div class="" data-comment-modal="ModalContainer">
        <div data-comment-modal="postContainer">
          <header class="hidden comment-modal-header border-b-borderClr bg-bg_color2" data-comment-modal="modalHeader">
            <h2 class="text-title_text_clr text-[18px]">${post.posterData.firstName}'s Post</h2>
            <button
                    class="modal-close-icon border-borderClr shadow-lg rounded-full close-full-post">
              <i class="ri-close-large-fill text-[25px]"></i>
            </button>
          </header>
          <div class="px-3 scroll-container" data-comment-modal="postTextComment">
            <div class="flex items-center justify-start gap-3 post-header pt-3 pb-1.5 relative overflow-hidden">
              <!-- post more icon -->
              <c:if test="${not empty sessionScope.sessionUser and post.posterData.id == sessionScope.sessionUser.userId}">
                <div class="absolute top-2 right-0 more-menu-container">
                  <div class="center-icon w-[35px] h-[35px] bg-bg_color3 text-[20px] text-fade_text cursor-pointer  hover:border hover:border-borderClr post-see-more">
                    <i class="ri-more-2-line"></i>
                  </div>
                  <div class="hidden w-fit p-2 bg-bg_color3 border border-borderClr text-main_text text-sm rounded absolute top-0 right-0 transition post-more-menu">
                    <button class="cursor-pointer hover:text-title_text_clr delete-item" data-type="post" data-itemid="${post.postId}" data-ownerid="${sessionScope.sessionUser.userId}">
                      <i class="ri-delete-bin-2-line text-[25px] text-fade_text hover:text-title_text_clr"></i>
                    </button>
                  </div>
                </div>
              </c:if>

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
                <span class="post-time text-fade_text text-[12px] short-date">
                  ${post.shortDate}
                </span>
                <span class="hidden post-time text-fade_text text-[12px] long-date">
                    ${post.longDate}
                </span>
              </div>
            </div>
            <div class="text-container">
              <p class="post-text ellipsis">
                ${post.content}
              </p>
            </div>
            <div class="">
              <button class="see-more-btn cursor-pointer  text-logo_clr1 hidden"> See more</button>
            </div>
            <c:if test="${not empty post.media}">
              <div class="w-full pt-2 mt-3 border-t border-borderClr">
                <img class="mx-auto object-cover max-w-[550px] w-full h-auto rounded-md" src="${post.media}"
                    alt="">
              </div>
            </c:if>
            <div class="hidden comments-container mt-5 pt-5 border-t border-borderClr" data-comment-modal="commentContainer">
              <div class="loader hidden mb-5 bg-bg_color3 py-3 rounded-md">
                <%@include file="/WEB-INF/views/fragments/loader.jsp"%>
              </div>
              <p class="bg-bg_color3 rounded-lg py-2 px-7 text-fade_text text-center w-1/2 mx-auto mb-5 no-comment hidden">No comment</p>
              <div class="all-comment"></div>
            </div> 
          </div>
          <div class="hidden post-comment-box border-t-borderClr bg-bg_color2" data-comment-modal="commentBox">
            <span class="center-icon bg-bg_color3 w-[40px] h-[40px] text-fade_text text-[20px]">
              <%@include file="/WEB-INF/views/fragments/pfpWrapper.jsp"%>
            </span>
            <form class="w-full comment-form" action="" >
              <div class="relative">
                <textarea class="p-3 border-none outline-none resize-none w-full overflow-auto bg-bg_color1 text-main_text rounded-md min-h-[60px] max-h-[320px]" placeholder="Let's have it !!" name="comment" maxlength="1001"></textarea>
                <button class="absolute top-[50%] translate-y-[-50%] right-4 text-[25px] text-fade_text cursor-not-allowed" data-post-id="${post.getPostId()}" disabled>
                  <span class="w-[40px] h-[40px] center-icon p-2 hover:bg-bg_color2">
                    <i class="ri-send-plane-2-fill"></i>
                  </span>
                  <span class="loader hidden justify-center items-center absolute inset-0 w-full h-full rounded-full bg-bg_color1">
                    <%@include file="/WEB-INF/views/fragments/loader.jsp"%>
                  </span>
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
      <div class="post-props px-3 mt-3 bg-bg_color3">
        <ul>
          <li class="comments w-fit">
            <div class="text-fade_text hover:text-main_text relative z-30 comment-btn-container">
              <button class="flex gap-2 items-center text-[25px] relative z-10 comment-btn"
                      id="${post.postId}">
                <span class="flex items-center gap-[2px]">
                  <i class="ri-chat-1-line"></i>
                <c:choose>
                  <c:when test="${post.comment > 0}">
                    <span class="comment-number text-[12px]" data-comment-count="${post.comment}">
                      ${post.comment}
                    </span>
                  </c:when>
                  <c:otherwise>
                    <span class="comment-number text-[12px]" data-comment-count="${post.comment}"></span>
                  </c:otherwise>
                </c:choose>
                </span>
                <span class="text-[14px]"> comment</span>
              </button>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </c:forEach>
</div>