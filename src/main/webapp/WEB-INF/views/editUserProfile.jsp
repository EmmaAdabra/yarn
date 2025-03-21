<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Viewnet
  Date: 2/9/2025
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/views/fragments/head.jsp"%>
    <title>ChatApp - Edit Profile</title>
</head>
<body class="bg-bg_color1 h-[100%] overflow-hidden overflow-y-scroll relative">
    <!-- Toast Container -->
    <div id="toast-container" class="fixed top-4 right-4 z-[100] space-y-2"></div>
    <div class="modal-container flex bg-modalBg" id="settingsModal">
        <!-- Modal Content -->
        <div class="modal-content max-w-[700px]" id="modalContent">
            <header class="modal-header">
                <h1 class="text-[18px]">Edit profile</h1>
                <a class="modal-close-icon " href="<C:url value="/dashboard" />">
                    <i class="ri-close-large-fill text-[25px]"></i>
                </a>
            </header>
            <section class= "sm:px-8 px-3 py-10 text-title_text_clr">
                <form class="text-center mb-8 data-edit-wrapper" id="uploadPfpForm"
                      enctype="multipart/form-data">
                    <div class="flex  justify-between items-center w-full">
                        <span class="">Profile Picture</span>
                        <input class="hidden" type="file" accept=".jpeg, .jpg, .png" name="pfp" id="pfp">
                        <div class="flex items-center justify-end gap-7 flex-auto">
                            <label
                                    class="center-icon bg-bg_color3 w-[35px] h-[35px] p-1 text-main_text text-[25px] cursor-pointer edit-icon"
                                   for="pfp">
                                <C:choose>
                                    <C:when test="${empty sessionScope.sessionUser.pfpUrl}">
                                        <i class="ri-add-line add-bio"></i>
                                        <i class="hidden ri-edit-line edit-bio"></i>
                                    </C:when>
                                    <C:otherwise>
                                        <i class="hidden ri-add-line add-bio"></i>
                                        <i class="ri-edit-line edit-bio"></i>
                                    </C:otherwise>
                                </C:choose>
                            </label>
                            <button
                                    class="hidden w-[35px] h-[35px] text-main_text text-[25px] cursor-pointer save-icon justify-center items-center rounded-full bg-bg_color3 p-1"
                                    id="uploadPfp">
                                <i class="ri-save-line"></i>
                            </button>
                        </div>
                    </div>
                    <span
                            class="text-fade_text flex justify-center items-center w-[150px] h-[150px] mt-10 mx-auto text-[50px] uppercase rounded-full bg-bg_color3">
                        <%@include file="/WEB-INF/views/fragments/pfpWrapper.jsp"%>
                    </span>
                    <p class="text-red-200 error mt-2"></p>
                    <p class="text-green-700 mt-2 overflow-hidden file-name"></p>
                </form>
                <form class="flex flex-col items-center gap-3 w-full mb-4" id="bioForm">
                    <div class=" flex justify-center items-center gap-1" >
                        <span class="text-title_text_clr">Bio</span>
                        <label
                                class="bg-bg_color3 text-main_text w-[35px] h-[35px] p-1 text-[25px] cursor-pointer center-icon" id="addBio" for="userBio">
                            <c:choose>
                                <c:when test="${empty sessionScope.sessionUser.getBio()}">
                                    <i class="ri-add-line add-bio"></i>
                                    <i class="hidden ri-edit-line edit-bio"></i>
                                </c:when>
                                <c:otherwise>
                                    <i class="hidden ri-add-line add-bio"></i>
                                    <i class="ri-edit-line edit-bio"></i>
                                </c:otherwise>
                            </c:choose>
                        </label>
                    </div>
                    <div class="sm:w-[70%] w-full flex-col items-center gap-2" id="bioTextContainer">
                        <div class="flex items-center gap-2 w-full">
                            <C:if test="${not empty sessionScope.sessionUser.bio}">
                                <input
                                        class="flex-auto bg-bg_color1 sm:w-[90%] w-full h-min-[60px] h-[fit-content] p-3 text-center resize-none text-fade_text h-max-[fit-content] rounded-md border-none outline-none" name="userBio" type="text" placeholder="Bio" value="${sessionScope.sessionUser.bio}" readonly id="userBio" />
                            </C:if>
                            <C:if test="${empty sessionScope.sessionUser.bio}">
                                <input
                                        class="hidden flex-auto bg-bg_color1 sm:w-[90%] w-full h-min-[60px] h-[fit-content] p-3 text-center resize-none text-fade_text h-max-[fit-content] rounded-md border-none outline-none" name="userBio" type="text" placeholder="Bio" value="${sessionScope.sessionUser.bio}" readonly id="userBio" />
                            </C:if>
                            <button class="hidden gray-btn px-2 py-1 rounded-md" id="saveBio">Save</button>
                        </div>
                        <p
                                class="count-bar w-[85%] text-main_text text-right text-[10px] sm:px-0 px-2 font-mono"></p>
                        <p class="text-error text-center error"></p>
                    </div>
                </form>
                <div class="border-b border-borderClr my-5 rounded border-[1.3px]"></div>
                <form id="editPersonalDataForm">
                    <div class="data-wrapper mb-9">
                        <p class="block text-title_text_clr mb-2 p-1">First Name</p>
                        <div class="bg-bg_color1 pl-3 pr-2 flex items-center rounded ">
                            <input
                                    class="flex-auto bg-bg_color1 border-none outline-none text-fade_text px-1 py-2 " placeholder="First Name" type="text" id="firstName" name="firstName" value="${sessionScope.sessionUser.fName}" title="
                                    Please enter a valid name (letters, apostrophes, or hyphens only, max 50 character
                                    " required readonly maxlength=50 minlength=2/>
                            <label
                                    class="flex-none text-fade_text w-[35px] h-[35px] my-1 p-1  text-[20px] cursor-pointer center-icon edit-data" for="firstName">
                                <i class="ri-edit-line edit-bio"></i>
                            </label>
                        </div>
                        <div id="firstName-error" class="error mt-1"></div>
                    </div>

                    <div class="data-wrapper mb-9">
                        <p class="block text-title_text_clr mb-2 p-1">Last Name</p>
                        <div class="bg-bg_color1 pl-3 pr-2 flex items-center rounded">
                            <input
                                    class="flex-auto text-fade_text border-none outline-none px-1 py-2" placeholder="Last Name" type="text" id="lastName" name="lastName" value="${sessionScope.sessionUser.lName}"
                                    placeholder="Last Name"
                                    title="
                                    Please enter a valid name (letters, apostrophes, or hyphens only, max 50 character
                                    " maxlength=50 minlength=1 readonly required/>
                            <label
                                    class="flex-none  text-fade_text w-[35px] h-[35px] my-1 p-1  text-[20px] cursor-pointer center-icon edit-data" for="lastName">
                                <i class="ri-edit-line edit-bio"></i>
                            </label>
                        </div>
                        <div id="lastName-error" class="error mt-1"></div>
                    </div>

                    <div class="data-wrapper mb-9">
                        <p class="block text-title_text_clr mb-2 p-1">Email</p>
                        <div class="bg-bg_color1 pl-3 pr-2 flex items-center rounded ">
                            <input
                                    class="flex-auto border-none outline-none text-fade_text px-1 py-2" type="email" id="email" name="email" placeholder="Email" readonly required value="${sessionScope.sessionUser.email}"/>
                            <label
                                    class="flex-none text-fade_text w-[35px] h-[35px] my-1 p-1 text-[20px] cursor-pointer center-icon edit-data" for="email">
                                <i class="ri-edit-line"></i>
                            </label>
                        </div>
                        <div id="email-error" class="error mt-1"></div>
                    </div>
                    <button
                            class="bg-gray-300 font-semibold hover:bg-gray-200 text-gray-900 primary-btns pointer-events-none" disabled>
                        Save</button>
                </form>
                <!-- <button class="bg-gray-300 hover:bg-gray-200 text-gray-900 login-sign-btns">Save</button> -->
            </section>
        </div>
    </div>
    <%@include file="/WEB-INF/views/fragments/topNavBar.jsp"%>
    <script
            src="<c:url value='/assets/js/edit_profile.js'/>"></script>
    <script src="<c:url value='/assets/js/toast.js'/>"></script>
</body>
</html>
