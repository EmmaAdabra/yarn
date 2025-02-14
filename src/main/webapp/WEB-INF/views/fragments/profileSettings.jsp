<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="fixed inset-0 z-40 hidden justify-center items-start overflow-y-auto" id="settingsModal">
    <!-- Modal Content -->
    <div class="mb-10 mt-12 bg-bg_color2 w-full max-w-md p-4 rounded-lg " id="modalContent">
        <header class="py-3 text-center border-b border-fade_text text-logo_clr1 text-2xl mb-2 relative">
            <h1 class="">Edit profile</h1>
            <div class=" text-main_text text-[40px] p-2 flex items-center justify-center w-[40px] h-[40px] absolute right-2 top-[50%] translate-y-[-50%] z-50 bg-bg_color3 hover:bg-bg_color1 rounded-full cursor-pointer" id="closeProfileSetting">
                <i class="ri-close-large-fill text-[30px]"></i>
            </div>
        </header>
        <section class= "px-8 py-10 text-title_text_clr">
            <form class="text-center mb-8 data-edit-wrapper" id="uploadForm" enctype="multipart/form-data">
                <div class="flex  justify-between items-center w-full">
                    <span class="">Profile Picture</span>
                    <input class="hidden" type="file" accept=".jpeg, .jpg, .png" name="pfp" id="pfp">
                    <div class="flex items-center justify-end gap-7 flex-auto">
                        <label class="text-main_text text-[25px] cursor-pointer edit-icon" for="pfp">
                            <i class="ri-edit-line"></i>
                        </label>
                        <button class=" text-main_text text-[25px] cursor-pointer save-icon" id="uploadPfp">
                            <i class="ri-save-line"></i>
                        </button>
                    </div>
                </div>
                <span class="text-fade_text flex justify-center items-center w-[150px] h-[150px] mt-10 mx-auto text-[80px] uppercase rounded-full bg-bg_color3">
                <%@include file="/WEB-INF/views/fragments/pfpWrapper.jspf"%>
              </span>
                <p class="text-red-200 error mt-2"></p>
                <p class="text-green-700 mt-2 overflow-hidden file-name"></p>
            </form>
            <form class="flex flex-col items-center gap-3 w-full mb-4" id="bioForm">
                <div class=" flex justify-center items-center gap-1" >
                    <span class="text-title_text_clr">Bio</span>
                    <c:choose>
                        <c:when test="${empty sessionScope.sessionUser.getBio()}">
                            <label class="bg-bg_color3 text-main_text w-[35px] h-[35px] p-1 text-[30px] cursor-pointer center-icon" id="addBio" for="userBio">
                                <i class="ri-add-line"></i>
                            </label>
                        </c:when>
                        <c:otherwise>
                            <label class="text-main_text text-[30px] cursor-pointer edit-icon" for="userBio" id="editBio">
                                <i class="ri-edit-line"></i>
                            </label>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="w-[70%] hidden flex-col items-center gap-2" id="bioTextContainer">
                    <div class="flex items-center gap-2 w-full">
                        <input class="bg-bg_color1 w-[90%] h-min-[60px] h-[fit-content] p-3 text-center resize-none text-fade_text h-max-[fit-content] rounded-md border-none outline-none" name="userBio" type="text" value="${sessionScope.sessionUser.getBio()}" readonly id="userBio">
                        <button class="hidden gray-btn px-2 py-1 rounded-md" id="saveBio">Save</button>
                    </div>
                    <p class="count-bar w-[75%] text-main_text text-right text-[10px] px-2 font-mono"></p>
                    <p class="text-error text-center error"></p>
                </div>
            </form>
            <form>
                <label class="block text-title_text_clr mb-2 p-1" for="firstName">First Name</label>
                <input class="input-field text-fade_text rounded-md"  type="text" id="firstName" name="firstName" value="${sessionScope.sessionUser.getfName()}" readonly/>

                <label class="block text-title_text_clr mb-2 p-1" for="lastName">Last Name</label>
                <input class="input-field text-fade_text rounded-md"   type="text" id="lastName" name="firstName" value="${sessionScope.sessionUser.getlName()}"
                       placeholder="Last Name" pattern="^[A-Za-z]+(['-]?[A-Za-z]*){0,49}$"
                       title="
                  Input must start with a letter, be 2 to 50 characters long, and
                  can optionally contain apostrophes (') or hyphens (-) only after a letter.
                  " maxlength=50 minlength=2 readonly required
                />

                <label class="block text-title_text_clr mb-2 p-1" for="username">Username</label>
                <input class="input-field text-fade_text rounded-md" type="text" id="username" name="username" placeholder="Username"
                       pattern="[a-zA-Z][a-zA-Z0-9.]{4,50}(?<!\.)$"
                       title="
                    Input must start with a letter, be 5 to 50 characters long, can contain letters,
                    digits, and dots (.), but cannot end with a dot." minlength=4 maxlength=50 readonly  value="${sessionScope.sessionUser.getUsername()}"
                />

                <label class="block text-title_text_clr mb-2 p-1" for="email">Email</label>
                <input class="input-field text-fade_text rounded-md"  type="email" id="email" name="email" placeholder="Email" required readonly value="${sessionScope.sessionUser.getEmail()}" />
            </form>
            <!-- <button class="bg-gray-300 hover:bg-gray-200 text-gray-900 login-sign-btns">Save</button> -->
        </section>
    </div>
</div>