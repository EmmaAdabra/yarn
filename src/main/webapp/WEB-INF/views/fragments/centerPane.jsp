
<div class="flex flex-col gap-5 items-center pt-6 text-main_text" id="centerPaneContent">
  <div class="flex items-center gap-3 bg-bg_color2 px-3 py-2 w-full rounded-md shadow-sm shadow-fade_text">
    <div class="w-[40px] h-[40px] text-[18px] bg-bg_color3 center-icon">
      <%@include file="/WEB-INF/views/fragments/pfpWrapper.jsp" %>
    </div>
    <button class="flex-auto bg-bg_color1 px-1 py-[4px] flex items-center rounded-[30px] cursor-pointer hover:shadow-sm hover:shadow-fade_text create-post" id="createPostBtn">
    <span class="flex-none block text-fade_text text-[18px] w-fit p-1">
      <i class="ri-pencil-line"></i>
    </span>
    <span class="flex-auto bg-[inherit] text-main_text text-left px-1 pointer-events-none">Create a post</span>
    </button>
  </div>
  <!-- display post -->
  <%@include file="/WEB-INF/views/fragments/displayPosts.jsp"%>
</div>