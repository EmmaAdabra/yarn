// fetchPostComment() function response code
const POST_NOT_EXIST = 0;
const FAILED_TO_FETCH_POST = -1;

const SESSION_USER_ID = Number.parseInt(document.querySelector("body").dataset.user);

{
    // confirmation modal, delete post or comment related variables and functions
    const confirmModal = document.getElementById("confirmModal");
    const confirmDeleteBtn = document.getElementById("confirmDelete");
    const cancelDeleteBtn = document.getElementById("cancelDelete");
    let itemToBeDeletedId; // could be comment or post
    let loginUserId; // login user
    let itemType; // comment or post

    if (confirmModal) {
        confirmModal.addEventListener("click", function (event) {
            event.stopPropagation();
        })
    }

    // activate confirm modal
    function showConfirmActionModal() {
        const deleteCommentMsg = "Do you want to delete this comment ?";
        const deletePostMsg = "Do you want to delete this Post ?";
        confirmModal.querySelector("#deleteModalMessage").textContent = (itemType === "post") ? deletePostMsg : deleteCommentMsg;

        confirmModal.classList.add("show")
    }

    // assigning event listener to center pane element for event delegation
    const centerPaneContent = document.querySelector("#scrollContainer.center-content");

    if (centerPaneContent) {

        // click event delegation
        centerPaneContent.addEventListener("click", function (event) {
            const target = event.target;
            // delete post
            if (target.classList.contains("delete-item") || target.parentElement.classList.contains("delete-item")) {
                event.stopPropagation() // prevent the click event on the delete button from
                // spreading
                const deleteBtn = target.closest(".more-menu-container").querySelector(".delete-item")
                itemToBeDeletedId = deleteBtn.dataset.itemid;
                loginUserId = deleteBtn.dataset.ownerid;
                itemType = deleteBtn.dataset.type;
                showConfirmActionModal();
                deleteBtn.parentElement.classList.remove("show");
            }

            //  show full post
            const commentBtnParent = target.closest(".comment-btn-container")
            if(commentBtnParent){
                const commentBtn = commentBtnParent.querySelector(".comment-btn")
                showFullPost(commentBtn);
            }

            //  close full post
            const closePostIcon = target.closest(".close-full-post");
            if(closePostIcon){
                closePost(closePostIcon);
            }

            // like post
            const likeBtn = target.closest(".like-btn")
            if(likeBtn){
                likePost(likeBtn)
            }

            // unlike post
            const unlikeBtn = target.closest(".unlike-btn")
            if(unlikeBtn){
                unlikePost(unlikeBtn)
            }
        })
    }


    // choose action
    if (cancelDeleteBtn && confirmDeleteBtn) {
        cancelDeleteBtn.addEventListener("click", cancelDelete)

        confirmDeleteBtn.addEventListener("click", delegateDelete);
    }


    function cancelDelete(){
        confirmModal.classList.remove("show");
    }

    function delegateDelete(){
        if (itemType === "post") {
            deletePost(itemToBeDeletedId, loginUserId);
        }
        if (itemType === "comment") {
            deleteComment(itemToBeDeletedId, loginUserId);
        }
        confirmModal.classList.remove("show");
    }
}

// prepare delete post
function deletePost(postId, userId) {
    const url = `/delete_post?postId=${postId}&ownerId=${userId}`;
    const post = document.getElementById(`post-${postId}`);

    (deleteItem(postId, userId, url)).then(response => {
        if (response && post) {
            post.remove();
        }
    }).catch(error => console.error(error));
}

// prepare delete comment
function deleteComment(commentId, ownerId) {
    const url = `/delete_comment?commentId=${commentId}&ownerId=${ownerId}`;
    const comment = document.getElementById(`comment-${commentId}`);
    const post = comment.closest(".post");

    (deleteItem(commentId, ownerId, url)).then(response => {
        if (response && comment) {
            comment.remove();
            updateCommentCount(post, -1, "add");
            checkIfCommentIsEmpty(post);
        }
    }).catch(error => console.error(error));
}

// delete item
async function deleteItem(itemId, ownerId, route) {
    try {
        let response = await fetch(route, {
            method: "DELETE"
        })

        if (response.ok) {
            showToast("success", "Deleted successfully")
            return true;
        }

        switch (response.status){
            case 404:
                showToast("error", "Can't find this comment or post")
                return true;
            case 401:
                window.location.href = "/home";
                return false
            case 500:
                showToast("error", "Internal server error");
                return false
            default:
                showToast("error", "Something went wrong")
                return false;
        }
    } catch (error) {
        showToast("error", "Couldn't connect to server")
        console.error("Fetch Error:", error);
        return false;
    }
}

// like post
async function likePost(likeBtn){
    const likeBtnContainer = likeBtn.closest(".like-btn-container")
    const postId = likeBtnContainer.dataset.likepost;
    const unlikeBtn = likeBtnContainer.querySelector(".unlike-btn");

    submitLike(postId).then(result => {
        if(result.status){
            likeBtn.classList.add("hidden");
            unlikeBtn.classList.remove("hidden")
            unlikeBtn.setAttribute("data-like", result.likeId)
            console.log(`likeID: ${unlikeBtn.dataset.like}`);
            handleLikeCount(likeBtnContainer, "like");

            if(result.user === "guest"){
                persistGuestLike(postId, result.likeId);
            }
        }
    }).catch(error => {
        showToast("error", "Something went wrong")
    })

}

// unlike post
function unlikePost(unlikeBtn){
    const likeBtnContainer = unlikeBtn.closest(".like-btn-container")
    const postId = likeBtnContainer.dataset.likepost;
    const likeId = unlikeBtn.dataset.like;
    const likeBtn = likeBtnContainer.querySelector(".like-btn");

    removeLike(likeId, postId).then(result => {
        if(result.status){
            unlikeBtn.classList.add("hidden");
            likeBtn.classList.remove("hidden")
            handleLikeCount(likeBtnContainer);

            if(result.user === "guest"){
                persistGuestLike(postId);
            }
        }
    }).catch(error => {
        console.error("Error submitting like:", error);
    })
}

// save guest user like post to browser storage
function persistGuestLike(postID, likeId, isDeletedPost=false){
    let postId = postID;
    let likedPosts = JSON.parse(localStorage.getItem("likedPosts")) || [];

    const postIndex = likedPosts.findIndex(post => post.postId === postId);

    if(postIndex === -1 && !isDeletedPost){
        likedPosts.push({postId: postId, likeId: likeId})
    } else if (postIndex !== -1 && isDeletedPost) {
        likedPosts.splice(postIndex, 1)
    }
    else {
        likedPosts.splice(postIndex, 1)
    }

    localStorage.setItem("likedPosts", JSON.stringify(likedPosts));
}

function handleLikeCount(likeBtnContainer, action=""){
    const totalLikeElem = likeBtnContainer.querySelector(".total-like");
    const totalLike = Number.parseInt(totalLikeElem.textContent);

    if(action === "like"){
        totalLikeElem.textContent = totalLike > 0 ? totalLike + 1 : 1;
    } else {
        totalLikeElem.textContent = totalLike > 1 ? totalLike - 1 : "";
    }
}

// submit like
async function submitLike(postId) {
    try {
        // Send the like request
        const response = await fetch("/likePost", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `postId=${postId}`,
        });

        // Check if the response is JSON
        if (!response.headers.get("content-type")?.includes("application/json")) {
            showToast("error", "Something went wrong")
            throw new Error("Server returned a non-JSON response");
        }

        // Parse the JSON response
        const result = await response.json();

        // Handle different response statuses
        switch (response.status) {
            case 200: // Success (logged-in user)
                return { status: true, user: "user", likeId: result.likeId};

            case 202: // Success (guest user)
                // alert("Please log in for a better experience.");
                showToast("success", "Login or sign up for better experience")
                return { status: true, user: "guest", likeId: result.likeId};

            case 404: // Post not found
                showToast("error", result.message);
                document.getElementById(`post-${postId}`)?.remove();
                persistGuestLike(postId, 0, true);
                return {status: false}
            case 500:
                showToast("error", "Internal server error");
                return { status: false};
            default: // Other errors
                showToast("error", "Something went wrong");
                return {status: false};
        }
    } catch (error) {
        showToast("error", "couldn't not connect to server");
        console.error("fetch error:", error);
        return { status: false, user: undefined };
    }
}

// Unlike post
async function removeLike(likeId, postId) {
    try {
        // Send the like request
        const response = await fetch("/deleteLike", {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({likedId: likeId}),
        });


        // Handle different response statuses
        switch (response.status) {
            case 200: // Success (logged-in user)
                return { status: true, user: "user"};

            case 202: // Success (guest user)
                return { status: true, user: "guest"};

            case 404: // Post not found
                showToast("error", response.statusText)
                document.getElementById(`post-${postId}`)?.remove(); // Safely remove the post element
                return {status: false};
            case 500:
                showToast("error", "Internal server error");
                return { status: false};
            default: // Other errors
                showToast("error", "Something went wrong")
        }
    } catch (error) {
        // Handle errors
        showToast("error", "couldn't not connect to server")
        console.error("fetch error", error);
        return { status: false, user: undefined };
    }
}

function initializeGuestLikeBtn(){
    const guestLikedPost = JSON.parse(localStorage.getItem("likedPosts")) || [];

    if(guestLikedPost.length > 0){
        const allLikeBtnContainer = document.querySelectorAll(".like-btn-container");
        allLikeBtnContainer.forEach(eachContainer => {
            let postId = eachContainer.dataset.likepost;
            const likedPost = guestLikedPost.find(post => post.postId === postId)

            if(likedPost){
                const likeBtn = eachContainer.querySelector(".like-btn");
                const unlikeBtn = eachContainer.querySelector(".unlike-btn");

                likeBtn.classList.add("hidden")
                unlikeBtn.classList.remove("hidden");
                unlikeBtn.setAttribute("data-like", likedPost.likeId);
            }
        })
    }
}

// top nav dropdown menu
const profileIcon = document.querySelector("#navPfp");
const profileMenu = document.querySelector("#profileMenu");
profileIcon.addEventListener("click", (event) => {
    event.stopPropagation();
    profileMenu.classList.toggle("hidden");
});

function hideProfileMenu() {
    const profileMenu = document.querySelector("#profileMenu");
    if(!profileMenu.classList.contains("hidden")){
        profileMenu.classList.add("hidden");
    }
}

// document click event delegation
document.addEventListener("click", function (event) {

    // close top nave profile menu on click even not within profile menu
    if (!profileMenu.contains(event.target)) {
        hideProfileMenu();
    }

    // create post
    if (event.target.classList.contains("create-post") || event.target.parentElement.classList.contains("create-post")) {
        createPost();
    }

    // create post on index page
    if (event.target.classList.contains("index-create-post") || event.target.parentElement.classList.contains("index-create-post")) {
        openCreatePostNotice();
    }

    // post see more button (for accessing delete post btn), this is not the see more for excess
    // post text
    if (event.target.classList.contains("post-see-more") || event.target.parentElement.classList.contains("post-see-more")) {
        hideShowElement();
        const postMoreMenu = event.target.closest(".more-menu-container").querySelector(".post-more-menu");
        postMoreMenu.removeEventListener("click", stopClickPropagate)
        postMoreMenu.addEventListener("click", stopClickPropagate)
        postMoreMenu.classList.add("show");
    } else {
        hideShowElement();
    }
});


//stop post more menu container click propagation
function stopClickPropagate(event) {
    if (event.target.classList.contains("post-more-menu")) {
        event.stopPropagation();
    }
}

// hide show element on scroll
document.querySelectorAll(".scroll-container").forEach((scrollElem) => {
    scrollElem.addEventListener("scroll", hideShowElement);
})

// hide all show more menu and confirmation modal
function hideShowElement() {
    let showElement = document.querySelector(".show");
    if (showElement) {
        showElement.classList.remove("show");
    }
}

// for index page
function openCreatePostNotice() {
    let noticeModal;

    if (document.getElementById("IndexCreatePost")) {
        noticeModal = document.getElementById("IndexCreatePost");
        noticeModal.classList.remove("hidden");
    }
}

// open create post modal
function createPost() {
    const createPostModal = document.querySelector("#createPostModal");

    createPostModal.classList.remove("hidden");
    createPostModal.classList.add("flex");
}



// general close modal
{
    const closeModalBtns = document.querySelectorAll(".modal-close-icon");
    closeModalBtns.forEach((icon) => {
        icon.addEventListener("click", function (event) {
            event.preventDefault();
            const modalElement = event.target.closest(".modal-container");

            if (modalElement) {
                modalElement.classList.remove("flex");
                modalElement.classList.add("hidden");
            }
        });
    });
}

// auto resize textarea height on create post modal
{
    const postTextArea = document.querySelector("#postTextArea");
    postTextArea.addEventListener("input", resizeTextBox);
}

// auto resize textarea height on commnet box
{
    const commentBoxes = document.querySelectorAll(".comment-form textarea");
    commentBoxes.forEach(commentBox => {
        commentBox.addEventListener("input", resizeTextBox);
    })
}

// Resize comment and post text box height during typing
function resizeTextBox(event) {
    const target = event.target;
    target.style.height = "auto";

    // Adjust height based on content
    if (target.name === "comment") {
        target.style.height = Math.min(target.scrollHeight, 110) + "px";
    } else {
        target.style.height = Math.min(target.scrollHeight, 320) + "px";
    }
}


// activate character count on element with attribute maxlength
{
    const maxLengthElem = document.querySelectorAll("[maxlength]");

    maxLengthElem.forEach((elem) => {
        elem.addEventListener("input", function () {
            const parentElement = this.parentElement;
            const maxLength = parseInt(elem.getAttribute("maxlength")) - 1;
            const countBar = parentElement.querySelector(".count-bar");

            if (countBar) {
                if (elem.value.length > maxLength) {
                    countBar.classList.remove("hidden");
                    elem.value = elem.value.substring(0, maxLength);
                } else {
                    countBar.classList.add("hidden");
                }
                countBar.textContent = `${elem.value.length}/${maxLength}`;
            }
        });
    });
}


//  submit create post form
{
    const postForm = document.querySelector("#postForm");

    if (postForm) {
        postForm.addEventListener("submit", async (event) => {
            event.preventDefault();

            // const postTitle = document.querySelector("#postTitle");
            const postText = document.querySelector("#postTextArea");
            const postImage = document.querySelector("#postImage").files[0];

            const postData = new FormData();

            if (postText.value.length > 0 || postImage !== undefined) {
                postData.append("postContent", postText.value);

                if (postImage !== undefined) {
                    postData.append("postImage", postImage);
                }

                try {
                    const response = await fetch("/submit_post", {
                        method: "POST", body: postData,
                    });

                    const result = await response.json();
                    let resultStatus = Number.parseInt(result.status);

                    switch (resultStatus) {
                        case 201:
                            showToast("success", "Post created successfully!");
                            location.reload();
                            break;
                        case 401:
                            window.location.href = "/home";
                            break;
                        default:
                            showToast("error", result.message);
                            console.error(`Error: ${result.message}`)
                    }


                } catch (error) {
                    // add a notification
                    showToast("error", "Something went wrong")
                    console.error(`Error: ${error.message? error.message : "Something went wrong"}: ${e}`);
                }
            } else {
                // add a notification
                showToast("error", "Can't submit an empty post")
            }
        });
    }
}

// validate post image attachment
{
    const postImage = document.querySelector("#postImage"); // hidden input element

    postImage.addEventListener("change", function () {
        const file = this.files[0];
        const maxSize = 500 * 1024;
        const parentForm = this.closest("form");
        const fileName = parentForm.querySelector(".file-name");

        if (file.size > maxSize) {
            displayEditProfileError(parentForm, "Attach image should not be greater than 500kb");
        } else {
            displayEditProfileError(parentForm, "");
            fileName.textContent = file.name;
        }
    });
}

function displayEditProfileError(currentEvent, errorMsg) {
    let error = currentEvent.querySelector(".error");
    error.textContent = errorMsg;
}


// all see more buttons
{
    const seeMoreBtns = document.querySelectorAll(".see-more-btn");
    seeMoreBtns.forEach((btn) => {
        btn.addEventListener("click", function () {
            const postText = this.closest(".post").querySelector(".post-text");
            postText.classList.remove("ellipsis");
            btn.classList.add("hidden");
        });
    });
}

// show full post modal
async function showFullPost(commentBtn){
    const modalElement = commentBtn.closest(".post");
    const postId = commentBtn.getAttribute("id");

    // push more button down
    let postMore = modalElement.querySelector(".more-menu-container");
    if (postMore) {
        postMore.classList.add("top-16");
    }


    // hide short post date
    modalElement.querySelector(".short-date").classList.add("hidden");
    // show full post date
    modalElement.querySelector(".long-date").classList.remove("hidden");

    const commentModal = modalElement.querySelector('[data-comment-modal = "ModalContainer"]');
    commentModal.classList.add("comment-modal");

    const header = modalElement.querySelector('[data-comment-modal = "modalHeader"]');
    header.classList.remove("hidden");

    // for show more button
    const postText = modalElement.querySelector(".post-text");
    if (postText.getAttribute("data-big-text") === "true") {
        postText.classList.remove("ellipsis");

        const seeMoreBtn = modalElement.querySelector(".see-more-btn");
        seeMoreBtn.classList.add("hidden");
    }

    const content = modalElement.querySelector('[data-comment-modal = "postContainer"]');
    content.classList.add("comment-modal-content");

    const postTextAndComment = modalElement.querySelector('[data-comment-modal = "postTextComment"]');
    postTextAndComment.classList.add("post-text-comment-wrapper-on-modal");

    const commentContainer = modalElement.querySelector('[data-comment-modal = "commentContainer"]');
    commentContainer.classList.remove("hidden");

    const commentBox = modalElement.querySelector('[data-comment-modal = "commentBox"]');
    commentBox.classList.remove("hidden");
    commentBox.classList.add("flex");
    const noComment = commentModal.querySelector(".no-comment");

    const loader = modalElement.querySelector(".loader");
    loader.classList.remove("hidden");

    let comments = await fetchPostComment(postId);

    loader.classList.add("hidden");

    if (comments.length > 0) {
        addComment(comments, commentModal);
        updateCommentCount(modalElement, comments.length);
    } else if (comments === POST_NOT_EXIST) {
        showToast("error", "This post have been deleted")
        document.getElementById(`post-${postId}`)?.remove();
    } else if (comments === FAILED_TO_FETCH_POST) {
        showToast("error", "Failed to load post comment")
        noComment.classList.remove("hidden");
        noComment.classList.add("error");
        noComment.textContent = "Failed to load comment";
    } else {
        noComment.classList.remove("error");
        noComment.textContent = "No comment"
        noComment.classList.remove("hidden");
    }
}

// close full post
function closePost(closePostIcon){
    const modalElement = closePostIcon.closest(".post");
    const commentModal = modalElement.querySelector('[data-comment-modal = "ModalContainer"]');
    commentModal.classList.remove("comment-modal");

    // return more buttons to normal position
    let postMore = modalElement.querySelector(".more-menu-container");
    if (postMore) {
        postMore.classList.remove("top-16");
    }

    // show short post date
    modalElement.querySelector(".short-date").classList.remove("hidden");
    // hide full post date
    modalElement.querySelector(".long-date").classList.add("hidden");

    const header = modalElement.querySelector('[data-comment-modal = "modalHeader"]');
    header.classList.add("hidden");

    const content = modalElement.querySelector('[data-comment-modal = "postContainer"]');
    content.classList.remove("comment-modal-content");

    // big text and see more button
    const postText = modalElement.querySelector(".post-text");
    if (postText.getAttribute("data-big-text") === "true") {
        postText.classList.add("ellipsis");

        const seeMoreBtn = modalElement.querySelector(".see-more-btn");
        seeMoreBtn.classList.remove("hidden");
    }

    const postTextAndComment = modalElement.querySelector('[data-comment-modal = "postTextComment"]');
    postTextAndComment.classList.remove("post-text-comment-wrapper-on-modal");

    const commentContainer = modalElement.querySelector('[data-comment-modal = "commentContainer"]');
    commentContainer.classList.add("hidden");

    const commentBox = modalElement.querySelector('[data-comment-modal = "commentBox"]');
    commentBox.classList.add("hidden");
    commentBox.classList.remove("flex");

    const noComment = commentModal.querySelector(".no-comment");

    if (!noComment.classList.contains("hidden")) {
        noComment.classList.add("hidden");
    }

    const allComment = commentModal.querySelector(".all-comment");
    allComment.innerHTML = "";
}

// deactivate comment button
{
    const commentBoxes = document.querySelectorAll(".comment-form textarea");
    commentBoxes.forEach((commentBox) => {
        commentBox.addEventListener("input", activateAndDeactivateCommentBtn);
    });
}


function activateAndDeactivateCommentBtn(event) {
    let commentBox;

    if (event.target) {
        commentBox = event.target;
    } else {
        commentBox = event;
    }

    const commentBtn = commentBox.parentElement.querySelector("button");

    if (commentBox.value.trim().length > 0) {
        commentBtn.disabled = false;
        commentBtn.classList.remove("cursor-not-allowed");
        commentBtn.classList.add("text-logo_clr1");
    } else {
        commentBtn.disabled = true;
        commentBtn.classList.remove("text-logo_clr1");
        commentBtn.classList.add("cursor-not-allowed");
        commentBtn.classList.add("text-fade_text");
    }
}

// AJAX for submitting comment on posts
{
    const commentForms = document.querySelectorAll(".comment-form");

    commentForms.forEach((form) => {
        form.addEventListener("submit", async function (event) {
            event.preventDefault();
            submitComment(form);
        });
    });
}

async function submitComment(form){
    const submitBtn = form.querySelector("button");
    const postId = submitBtn.getAttribute("data-post-id");
    const commentBox = form.querySelector("textarea");
    const comment = commentBox.value;
    let commentList = [];
    const postContainer = form.closest(".post");

    const loader = form.querySelector(".loader");
    loader.classList.remove("hidden");

    try {
        const response = await fetch("/add_comment", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({postId, comment}),
        });

        if (!response.ok) {
            throw new Error(`HTTP Error: ${response.status}`);
        }

        let result;

        if (response.headers.get("content-type")?.includes("application/json")) {
            result = await response.json();
        } else {
            throw new Error("Server returned non-JSON response");
        }

        let resultStatus = Number.parseInt(result.status);
        // Application-level error handling

        // Handle different response statuses
        if(resultStatus === 201){
            commentBox.value = "";
            commentBox.style.height = "60px";
            commentList.push(result.data);
            addComment(commentList, postContainer, "prepend");
            const noComment = postContainer.querySelector(".no-comment");

            loader.classList.add("hidden");
            activateAndDeactivateCommentBtn(commentBox);

            updateCommentCount(postContainer, 1, "add");
            if (!noComment.classList.contains("hidden")) {
                noComment.classList.add("hidden");
            }
        } else if(resultStatus === 404){
            showToast("error", result.message)
            document.getElementById(`post-${postId}`)?.remove();
        } else if(resultStatus === 500){
            showToast("error", result.message);
            loader.classList.add("hidden");
        } else{
            showToast("error", "Something went wrong");
            loader.classList.add("hidden");
        }

    } catch (error) {
        loader.classList.add("hidden");
        showToast("error", "Couldn't connect to server")
        console.error("fetch error:", error);
    }
}

// prevent uwanted character in user comment
function escapeHtml(text) {
    const div = document.createElement("div");
    div.innerText = text; // Automatically escapes HTML
    return div.innerHTML; // Returns the escaped content
}

// escape '"' in links to avoid XSS
function escapeHtmlAttribute(value) {
    return value.replace(/"/g, '&quot;');
}

// replace link with anchor tags
function linkify(text) {
    // Regex to match URLs with or without protocol
    const urlRegex = /(\b(https?|ftp):\/\/[-A-Z0-9+&@#/%?=~_|!:,.;]*[-A-Z0-9+&@#/%=~_|]|\bwww\.[-A-Z0-9+&@#/%?=~_|!:,.;]*[-A-Z0-9+&@#/%=~_|])/gi;

    return text.replace(urlRegex, (url) => {
        // Add https:// to URLs that start with www
        const href = url.startsWith('http') || url.startsWith('ftp') ? url : `https://${url}`;
        // Escape the href attribute to prevent XSS
        const safeHref = escapeHtmlAttribute(href);
        // Return the link
        return `<a href="${safeHref}" target="_blank">${url}</a>`;
    });
}

// normalized user comment
function sanitizeAndLinkify(text) {
    const escapedText = escapeHtml(text);

    const linkifiedText = linkify(escapedText);

    return linkifiedText;
}

function addComment(commentList, post, position) {
    const commentContainer = post.querySelector(".all-comment");

    if (commentList.length > 0) {
        commentList.forEach((comment) => {
            let commentDiv = document.createElement("div");
            let pfp;
            let seeMore;

            commentDiv.classList.add("comment", "mb-5", "flex", "flex-row-reverse", "justify-end", "gap-1", "w-fit", "pr-3", "overflow-hidden");
            commentDiv.setAttribute("id", `comment-${comment.commentId}`);

            if (comment.userId === SESSION_USER_ID) {
                seeMore = `
          <div class="more-menu-container relative">
            <div class="center-icon w-[25px] h-[25px] bg-bg_color3 text-[18px] text-fade_text cursor-pointer  hover:border hover:border-borderClr post-see-more">
              <i class="ri-more-2-line"></i>
            </div>
            <div class="hidden w-fit p-[3px] bg-bg_color3 border border-borderClr text-main_text text-sm rounded absolute top-0 right-0 transition post-more-menu">
              <button class="cursor-pointer text-[12px] flex items-center gap-[3px] hover:text-title_text_clr delete-item" data-type="comment" data-itemid="${comment.commentId}" data-ownerid="${comment.userId}">
                <i class="ri-delete-bin-2-line text-[18px] text-fade_text hover:text-title_text_clr"></i>
              </button>
            </div>
          </div>
        `
            } else {
                seeMore = ``;
            }

            if (comment.pfp !== null) {
                pfp = `<img class="w-full h-full rounded-full object-cover" src="${comment.pfp}" alt="commenter pfp">`;
            } else {
                pfp = `<span>${comment.initial}</span>`;
            }

            commentDiv.innerHTML = `
         ${seeMore}
        <div class="flex gap-[5px]">
          <span class="center-icon w-[35px] h-[35px] text-fade_text text-[20px] bg-bg_color3">
            ${pfp}
          </span>
          <div class="bg-bg_color3 w-fit max-w-full text-main_text p-2 rounded-lg">
            <h3 class="text-title_text_clr mb-1 text-[12px]">${comment.name}
              <span class="text-fade_text text-[12px] ps-[3px]"> ${comment.time}</span>
            </h3>
            <p class="comment-text">${sanitizeAndLinkify(comment.comment)}</p>
          </div>
        </div>`;

            if (position === "prepend") {
                commentContainer.prepend(commentDiv);
            } else {
                commentContainer.appendChild(commentDiv);
            }
        });
    }
}

// Retrieve post comment
async function fetchPostComment(postId) {
    try {
        const response = await fetch(`fetch_post_comment?postId=${postId}`, {
            method: "GET",
        });

        if (!response.ok) {
            if (response.status === 404) {
                return POST_NOT_EXIST; // Post not found, handle it separately
            }
            throw new Error(`HTTP error: ${response.status}`); // Handle other errors generically
        }

        if (!response.headers.get("content-type")?.includes("application/json")) {
            console.error("Server returned non-JSON response");
            return FAILED_TO_FETCH_POST;
        }

        const result = await response.json();

        // console.log(result.message);
        return result.data;
    } catch (error) {
        console.error("Error:", error);
        return FAILED_TO_FETCH_POST;
    }
}

function updateCommentCount(post, value, action = "") {
    const totalCommentElem = post.querySelector(".comment-number");
    const commentCount = Number.parseInt(totalCommentElem.getAttribute("data-comment-count"));

    if (action === "add") {
        let totalComment = commentCount + value
        totalCommentElem.textContent = totalComment === 0 ? "" : totalComment;
        value += commentCount;
    } else {
        totalCommentElem.textContent = value;
    }

    totalCommentElem.setAttribute("data-comment-count", value);
}

function checkIfCommentIsEmpty(post) {
    const commentList = post.querySelectorAll(".comment");

    if (commentList.length === 0) {
        post.querySelector(".no-comment").classList.remove("hidden")
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const allPostText = document.querySelectorAll(".post-text");
    let updates = [];

    // Batch read operations
    allPostText.forEach((postText) => {
        const seeMoreBtn = postText.closest(".text-container").querySelector(".see-more-btn");

        // Force a reflow to ensure dimensions are calculated
        // postText.offsetHeight;
        // Check if the element is rendered and has content
        if (postText.scrollHeight > postText.clientHeight) {
            updates.push({ postText, seeMoreBtn });
        }
    });

    // Batch DOM updates in requestAnimationFrame
    if (updates.length) {
        requestAnimationFrame(() => {
            updates.forEach(({ postText, seeMoreBtn }) => {
                postText.setAttribute("data-big-text", "true");
                seeMoreBtn.classList.remove("hidden");
            });
        });
    }
});

// scroll to top
// Show or hide the button on scroll
// Show button when user scrolls inside the container
function gotoTop() {
    const scrollToTopBtn = document.getElementById("scrollToTopBtn");
    let scrollContainer = document.getElementById("scrollContainer");

    if (document.getElementById("heroAndPostContainer")) {
        if (window.innerWidth < 768) {
            scrollContainer = document.getElementById("heroAndPostContainer");
        }
    }

    scrollContainer.addEventListener("scroll", () => {
        if (scrollContainer.scrollTop > 200) {
            scrollToTopBtn.classList.remove("hidden");
            scrollToTopBtn.classList.add("flex");
        } else {
            scrollToTopBtn.classList.add("hidden");
            scrollToTopBtn.classList.remove("flex");
        }
    });

// Scroll to the top of the container
    scrollToTopBtn.addEventListener("click", () => {
        scrollContainer.scrollTo({top: 0, behavior: "smooth"});
    });
}

window.addEventListener("resize", () => {
    gotoTop();
    hideIndexRightPane();
})
gotoTop();

// hide index right pane on resize
function hideIndexRightPane() {
    const rightPane = document.querySelector(".index-right-pane");

    if (rightPane && window.innerWidth > 768 && window.innerWidth < 1024 && !rightPane.classList.contains("hidden")) {
        rightPane.classList.add("hidden");
    }
}

if(!SESSION_USER_ID){
    window.addEventListener("load", initializeGuestLikeBtn);
}

// make hideProfileMenu function global
window.hideProfileMenu = hideProfileMenu;

// JavaScript to hide loader and show content
window.addEventListener('load', function () {
    const pageLoader = document.getElementById('page-loader');

    // Hide loader
    pageLoader.classList.add('opacity-0', 'pointer-events-none');
});