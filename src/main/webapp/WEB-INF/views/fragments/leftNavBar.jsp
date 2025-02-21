<div class="hidden lg:block col-span-1 left-sidebar row-span-full" >
  <div class="side-menu overflow-auto border-r border-r-borderClr mr-10 h-full" data-simplebar>
    <ul class="list-none menu-options ps-4 pt-8">
      <li class=" option">
        <a class="flex items-center gap-2 rounded hover:bg-bg_color2 p-2 w-full">
          <span class="flex justify-center items-center w-[35px] h-[35px] text-fade_text text-[20px] uppercase rounded-full bg-bg_color3 ">
            <%@include file="/WEB-INF/views/fragments/pfpWrapper.jsp"%>
          </span>
          <span class="text-title_text_clr">${sessionScope.sessionUser.getUserFullName()}</span>
        </a>
      </li>
      <li class="flex items-center gap-5">
        <a class="rounded hover:bg-bg_color2 p-2 w-full" id="profileSettings"  href="<c:url value="/edit_profile" />">
          <span class="text-fade_text text-[20px]">
            <i class="ri-settings-3-line"></i>
            </span><span class="text-title_text_clr">Edit profile</span>
        </a>
      </li>
      <li class="flex items-center gap-5">
          <a class="rounded hover:bg-bg_color2 p-2 w-full"  href="<c:url value="/logout" />">
            <span class="text-fade_text text-[20px]">
              <i class="ri-logout-box-r-line"></i>
            </span>
              <span class="text-title_text_clr">Logout</span>
          </a>
      </li>
    </ul>
  </div>
</div>