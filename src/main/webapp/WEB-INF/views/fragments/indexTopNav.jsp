<header class="w-[100%] h-[60px] bg-bg_color1 shadow shadow-bg_color2 text-center py-2 px-3 fixed top-0 left-0 z-30 top-navbar">
  <nav class="grid grid-cols-3 align-middle w-full">
    <div class="col-span-1">
      <div class="flex-auto flex items-center gap-3">
          <span class="center-icon w-[40px] h-[40px] rounded-full hover:border hover:border-fade_text hover:bg-bg_color3 cursor-pointer">
            <img class="h-[36px] w-[36px] object-cover" src="<c:url value="/assets/images/logo.png" />" alt="">
          </span>
          
          <div class="bg-bg_color2 px-1  py-[2px] hidden lg:flex items-center rounded-[30px]">
            <span class="flex-none  text-title_text_clr text-[18px] w-fit p-1 border-spacing-6">
              <i class="ri-search-line"></i>
            </span>
            <input class="flex-auto bg-bg_color2 border-none outline-none text-main_text px-1 " type="text" placeholder="Search Post" name="search">
          </div>
      </div>
    </div>
    <div class="col-span-1 flex justify-center gap-20 items-center center-icons text-title_text_clr">
      <span class="hidden md:flex justify-center items-center rounded-full w-[35px] h-[35px] hover:bg-bg_color2  text-[20px] cursor-pointer">
          <i class="ri-home-4-line"></i>
      </span>
      <!-- <span class=" text-[20px]">
            <i class="ri-group-line"></i>
      </span> -->
      <span class="hidden md:flex justify-center items-center rounded-full w-[35px] h-[35px] hover:bg-bg_color2  text-[20px] cursor-pointer index-create-post">
        <i class="ri-quill-pen-ai-line"></i>
      </span>
    </div>
    <div class="col-span-1 flex justify-end gap-7 items-center text-title_text_clr">
      <!-- profile menu icon/button -->
      <span class="w-[35px] h-[35px] bg-bg_color2  flex justify-center items-center text-[20px] rounded-full cursor-pointer relative hover:border hover:border-fade_text" id="navPfp">
        <i class="ri-menu-3-fill"></i>
        <span class="w-[18px] h-[18px] center-icon bg-bg_color2 text-[20px] text-title_text_clr absolute bottom-[-3px] right-[-3px] border-[1.3px] border-main_text">
          <i class="ri-arrow-drop-down-line"></i>
        </span>
      </span>
    </div>
  </nav>
</header>