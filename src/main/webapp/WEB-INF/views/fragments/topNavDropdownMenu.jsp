<div class="hidden p-3 bg-bg_color1 w-[350px] h-fit shadow shadow-fade_text rounded-xl absolute top-[58px] right-[10px] z-40" id="profileMenu">
    <div class="p-2 mb-5 rounded shadow shadow-fade_text">
        <a class="flex items-center p-2 gap-5 option hover:bg-bg_color2 rounded" href="">
            <span class="text-fade_text flex justify-center items-center w-[35px] h-[35px] uppercase rounded-full bg-bg_color3">
              <%@include file="/WEB-INF/views/fragments/pfpWrapper.jsp"%>
            </span>
            <span class=" text-title_text_clr text-[18px]">
                ${sessionScope.sessionUser.getUserFullName()}
            </span>
        </a>
    </div>
    <ul class="flex flex-col">
      <li class="flex items-center gap-5 md:hidden">
        <span class="rounded hover:bg-bg_color2 p-2 w-full" id="getInspired">
          <span class="text-fade_text text-[20px]">
            <i class="ri-double-quotes-l"></i>
            </span><span class="text-title_text_clr">Get Inspired</span>
        </span>
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