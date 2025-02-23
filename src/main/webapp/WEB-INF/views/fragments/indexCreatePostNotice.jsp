<div class="modal-container hidden bg-[#1e1e20e7]" id="IndexCreatePost">
  <div class="modal-content pb-8 max-w-[550px] w-[90%] mx-auto">
      <header class="modal-header">
          <h1 class="text-[18px]">Create post</h1>
          <span class="modal-close-icon " href="">
              <i class="ri-close-large-fill text-[25px]"></i>
          </span>
      </header>
      <div class="flex flex-col items-center text-center text-main_text px-[10px] py-5">
        <div class="flex justify-center items-center gap-2">
           <span class="italic text-[20px] text-fade_text">Oops</span>
           <i class="ri-error-warning-line text-[20px] text-error"></i>
        </div>
        <p class="py-5 text-[18px]">
          Login to create a post
        </p>
        <a class="block mt-2 w-[80%]" href="<c:url value="/login"/>">
          <button class="yellow-btn w-full primary-btns" id="signIn">
            Sign In
          </button>
        </a>
        <div class="flex items-center mt-2 w-full text-fade_text text-sm">
          <div class="flex-grow border-t border-borderClr"></div>
          <span class="mx-4">OR</span>
          <div class="flex-grow border-t  border-borderClr"></div>
      </div>
      <a class="block mt-2 w-[80%]" href="<c:url value="/register"/>">
        <button class="bg-gray-300 w-full hover:bg-gray-200 text-gray-900 primary-btns" id="signUP">
          Sign UP
        </button>
      </a>
      </div>
  </div>
</div>