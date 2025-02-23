<div class="hidden p-3 bg-bg_color1 w-[350px] h-fit shadow shadow-fade_text rounded-xl absolute top-[58px] right-[10px] z-40" id="profileMenu">
  <div class="p-2 mb-5 rounded shadow shadow-fade_text">
      <a class="flex items-center p-2 gap-5 option hover:bg-bg_color2 rounded" href="">
          <span class="text-fade_text flex justify-center items-center w-[40px] h-[40px] uppercase rounded-full bg-bg_color3">
            <img class="object-cover h-[34px] w-[34px]" src="<c:url value="/assets/images/logo.png" />" alt="">
          </span>
          <span class="text-[18px] text-title_text_clr">
              Yarn
          </span>
      </a>
  </div>
  <ul class="flex flex-col">
    <li class="flex items-center gap-5 lg:hidden">
      <span class="rounded hover:bg-bg_color2 p-2 w-full" id="getInspired">
        <span class="text-fade_text text-[20px]">
          <i class="ri-double-quotes-l"></i>
          </span><span class="text-title_text_clr">Get Inspired</span>
      </span>
  </li>
      <li class="flex items-center gap-5">
          <a class="rounded hover:bg-bg_color2 p-2 w-full" id="profileSettings"  href="<c:url value="/login" />">
            <span class="text-fade_text text-[20px]">
              <i class="ri-login-circle-line"></i>
              </span><span class="text-title_text_clr">Login</span>
          </a>
      </li>
      <li class="flex items-center gap-5">
          <a class="rounded hover:bg-bg_color2 p-2 w-full"  href="<c:url value="/register" />">
            <span class="text-fade_text text-[20px]">
              <i class="ri-user-add-line"></i>
            </span>
              <span class="text-title_text_clr">Sign UP</span>
          </a>
      </li>
  </ul>
</div>