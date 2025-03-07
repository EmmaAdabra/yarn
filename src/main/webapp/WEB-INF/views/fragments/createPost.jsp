<div class="modal-container hidden bg-[#1e1e20e7]" id="createPostModal">
    <div class="modal-content pb-8 max-w-[550px] max-h-[90vh]">
        <header class="modal-header">
            <h1 class="text-[18px]">Create post</h1>
            <a class="modal-close-icon " href="">
                <i class="ri-close-large-fill text-[25px]"></i>
            </a>
        </header>
        <div class="flex items-center gap-2 option mb-5">
          <span class="flex justify-center items-center w-[35px] h-[35px] text-fade_text text-[20px] uppercase rounded-full bg-bg_color3">
            <%@include file="/WEB-INF/views/fragments/pfpWrapper.jsp"%>
          </span>
            <a class="text-title_text_clr text-[16px]" href="">${sessionScope.sessionUser.getUserFullName()}</a>
        </div>
        <form class="mt-3 px-4" enctype="multipart/form-data" id="postForm">
            <div>
                <textarea class="p-3 border-none outline-none resize-none w-full overflow-auto bg-bg_color1 text-main_text rounded-md min-h-[100px] max-h-[320px]" placeholder="Let's have it !!" name="post" id="postTextArea" maxlength="10001"></textarea>
                <p class="count-bar hidden mt-[3px] text-error text-right text-[12px] pr-2 font-mono"></p>
            </div>
            <input class="hidden" type="file" accept=".jpeg, .jpg, .png" name="postImage" id="postImage">
            <div>
                <p class="text-red-200 error mt-2"></p>
                <p class="text-green-700 mt-2 overflow-hidden file-name"></p>
            </div>
            <div class="mt-7 flex gap-2 items-center">
                <label class="text-[30px] text-title_text_clr flex justify-center items-center cursor-pointer"
                       for="postImage">
                    <i class="ri-image-add-line"></i>
                </label>
                <button class="yellow-btn primary-btns w-full rounded-md" id="submitPost">Post</button>
            </div>
        </form>
    </div>
</div>