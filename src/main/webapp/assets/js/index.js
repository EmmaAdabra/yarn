// show nav profile menu
{
  const profileIcon = document.querySelector("#navPfp");
  const profileMenu = document.querySelector("#profileMenu");
  profileIcon.addEventListener("click", (event) => {
    event.stopPropagation();
    profileMenu.classList.toggle("hidden");
  });

  function hideProfileMenu() {
    const profileMenu = document.querySelector("#profileMenu");
    profileMenu.classList.add("hidden");
  }

  // close top nave profile menu on click even not within profile menu
  document.addEventListener("click", function (event) {
    if (!profileMenu.contains(event.target)) {
      hideProfileMenu();
    }

    // create post
    if(event.target.classList.contains("create-post") || event.target.parentElement.classList.contains("create-post")){
      createPost();
    }
  });
}

// open create post modal
function createPost(){
  const createPostModal = document.querySelector("#createPostModal");

  createPostModal.classList.remove("hidden");
  createPostModal.classList.add("flex");
}

// close modal
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

// auto resize post text area
// ToDO: make the resizable for all text area
{
  const postTextArea = document.querySelector("#postTextArea");
  postTextArea.addEventListener("input", function () {
    this.style.height = "auto";

    this.style.height = Math.min(this.scrollHeight, 320) + "px";
  });
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

// submit post
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
            method: "POST",
            body: postData,
          });

          const result = await response.json();

          if (result.status !== 201) {
            throw new Error(result.message || "Something went wrong.");
          }

          // add a notification
          console.log(result.message);
          location.reload();
        } catch (error) {
          // add a notification
          console.error(`Error: ${error.message}`);
        }
      } else {
        // add a notification
        alert("Can't submit a empty post (Test)");
      }
    });
  }
}

// validate post attachment
{
  const postImage = document.querySelector("#postImage");

  postImage.addEventListener("change", function () {
    const file = this.files[0];
    const maxSize = 500 * 1024;
    const parentForm = this.closest("form");
    const fileName = parentForm.querySelector(".file-name");

    if (file.size > maxSize) {
      displayEditProfileError(
        parentForm,
        "Attach image should not be greater than 500kb"
      );
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

// post text with more that 6 lines
{
  const allPostText = document.querySelectorAll(".post-text");
  let updates = [];

  // Batch read operations
  allPostText.forEach((postText) => {
    const seeMoreBtn = postText.closest(".post").querySelector(".see-more-btn");

    // Read layout properties (forces reflow if done later with DOM updates)
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

// show comment modal
{
  const commentBtns = document.querySelectorAll(".comment-icon");

  commentBtns.forEach((btn) => {
    btn.addEventListener("click", async function (event) {
      event.preventDefault();
      const modalElement = this.closest(".post");
      let postId = btn.getAttribute("id");

      const commentModal = modalElement.querySelector(
        '[data-comment-modal = "ModalContainer"]'
      );
      commentModal.classList.add("comment-modal");

      const header = modalElement.querySelector(
        '[data-comment-modal = "modalHeader"]'
      );
      header.classList.remove("hidden");

      // for show more button
      const postText = modalElement.querySelector(".post-text");
      if (postText.getAttribute("data-big-text") === "true") {
        postText.classList.remove("ellipsis");

        const seeMoreBtn = modalElement.querySelector(".see-more-btn");
        seeMoreBtn.classList.add("hidden");
      }

      const content = modalElement.querySelector(
        '[data-comment-modal = "postContainer"]'
      );
      content.classList.add("comment-modal-content");

      const postTextAndComment = modalElement.querySelector(
        '[data-comment-modal = "postTextComment"]'
      );
      postTextAndComment.classList.add("post-text-comment-wrapper-on-modal");

      const commentContainer = modalElement.querySelector(
        '[data-comment-modal = "commentContainer"]'
      );
      commentContainer.classList.remove("hidden");

      const commentBox = modalElement.querySelector(
        '[data-comment-modal = "commentBox"]'
      );
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
      } else {
        noComment.classList.remove("hidden");
      }
    });
  });
}

// close comment modal
{
  const closeCommentBtn = document.querySelectorAll(".close-comment-btn");
  closeCommentBtn.forEach((btn) => {
    btn.addEventListener("click", function () {
      const modalElement = this.closest(".post");
      const commentModal = modalElement.querySelector(
        '[data-comment-modal = "ModalContainer"]'
      );
      commentModal.classList.remove("comment-modal");

      const header = modalElement.querySelector(
        '[data-comment-modal = "modalHeader"]'
      );
      header.classList.add("hidden");

      const content = modalElement.querySelector(
        '[data-comment-modal = "postContainer"]'
      );
      content.classList.remove("comment-modal-content");

      // big text and see more button
      const postText = modalElement.querySelector(".post-text");
      if (postText.getAttribute("data-big-text") === "true") {
        postText.classList.add("ellipsis");

        const seeMoreBtn = modalElement.querySelector(".see-more-btn");
        seeMoreBtn.classList.remove("hidden");
      }

      const postTextAndComment = modalElement.querySelector(
        '[data-comment-modal = "postTextComment"]'
      );
      postTextAndComment.classList.remove("post-text-comment-wrapper-on-modal");

      const commentContainer = modalElement.querySelector(
        '[data-comment-modal = "commentContainer"]'
      );
      commentContainer.classList.add("hidden");

      const commentBox = modalElement.querySelector(
        '[data-comment-modal = "commentBox"]'
      );
      commentBox.classList.add("hidden");
      commentBox.classList.remove("flex");

      const noComment = commentModal.querySelector(".no-comment");

      if (!noComment.classList.contains("hidden")) {
        noComment.classList.add("hidden");
      }

      const allComment = commentModal.querySelector(".all-comment");
      allComment.innerHTML = "";
    });
  });
}

// deactivate comment button
{
  const commentBoxes = document.querySelectorAll(".comment-form textarea");
  commentBoxes.forEach((commentBox) => {
    const commentBtn = commentBox.parentElement.querySelector("button");

    commentBox.addEventListener("input", function () {
      if (commentBox.value.length > 0) {
        commentBtn.disabled = false;
        commentBtn.classList.remove("cursor-not-allowed");
        commentBtn.classList.add("text-logo_clr1");
      } else {
        commentBtn.disabled = true;
        commentBtn.classList.remove("text-logo_clr1");
        commentBtn.classList.add("cursor-not-allowed");
        commentBtn.classList.add("text-fade_text");
      }
    });
  });
}

// AJAX for submitting comment on posts
{
  const commentForms = document.querySelectorAll(".comment-form");

  commentForms.forEach((form) => {
    form.addEventListener("submit", async function (event) {
      event.preventDefault();

      const submitBtn = form.querySelector("button");
      const postId = submitBtn.getAttribute("data-post-id");
      const commentBox = form.querySelector("textarea");
      const comment = commentBox.value;
      let commentList = [];
      console.log(postId, comment);
      const postContainer = form.closest(".post");

      const loader = form.querySelector(".loader");
      loader.classList.remove("hidden");

      try {
        const response = await fetch("/add_comment", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ postId, comment }),
        });

        console.log("response info:", response.ok, response.status);

        if (!response.ok) {
          throw new Error(`HTTP Error: ${response.status}`);
        }

        let result;

        if (
          response.headers.get("content-type")?.includes("application/json")
        ) {
          result = await response.json();
        } else {
          throw new Error("Server returned non-JSON response");
        }

        // Application-level error handling
        if (result.status !== 201) {
          console.error(result.message || "Something went wrong");
        } else {
          if (result.data) {
            commentBox.value = "";
            console.log(result.data);
            commentList.push(result.data);
            addComment(commentList, postContainer, "prepend");
            const noComment = postContainer.querySelector(".no-comment");

            loader.classList.add("hidden");

            updateCommentCount(postContainer, 1, "add");
            if (!noComment.classList.contains("hidden")) {
              noComment.classList.add("hidden");
            }
          }
        }
      } catch (error) {
        loader.classList.add("hidden");
        // alert("Something went wrong, check console for details");
        console.error("Error:", error);
      }
    });
  });
}

function addComment(commentList, post, position) {
  const commentContainer = post.querySelector(".all-comment");

  if (commentList.length > 0) {
    commentList.forEach((comment) => {
      let commentDiv = document.createElement("div");
      let pfp;

      if (comment.pfp !== null) {
        pfp = `<img class="w-full h-full rounded-full object-cover" src="${comment.pfp}" alt="commenter pfp">`;
      } else {
        pfp = `<span>${comment.initial}</span>`;
      }

      commentDiv.classList.add("comment", "mb-5");
      commentDiv.setAttribute("data-comment-id", comment.commentId);
      commentDiv.innerHTML = `
        <div class="flex gap-[5px]">
          <span class="flex justify-center items-center w-[35px] h-[35px] text-fade_text text-[25px] uppercase rounded-full">
            ${pfp}
          </span>
          <div class="bg-bg_color3 w-fit text-main_text p-2 rounded-lg">
            <h3 class="text-title_text_clr mb-1 text-[12px]">${comment.name}
              <span class="text-fade_text text-[12px]"> ${comment.time}</span>
            </h3>
            <p>${comment.comment}</p>
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
        return 0; // Post not found, handle it separately
      }
      throw new Error(`HTTP error: ${response.status}`); // Handle other errors generically
    }

    if (!response.headers.get("content-type")?.includes("application/json")) {
      throw new Error("Server returned non-JSON response");
    }

    const result = await response.json();

    console.log(result.message);
    return result.data;
  } catch (error) {
    console.error("Error:", error);
    return -1;
  }
}

function updateCommentCount(post, value, action = "") {
  const commentCountElem = post.querySelector(".comment-number");
  const commentCount = Number.parseInt(
    commentCountElem.getAttribute("data-comment-count")
  );

  if (action === "add") {
    commentCountElem.textContent = commentCount + value;
    value = commentCount + value;
  } else {
    commentCountElem.textContent = value;
  }

  commentCountElem.setAttribute("data-comment-count", value);
}

// scroll to top

// Show or hide the button on scroll
const scrollContainer = document.getElementById("scrollContainer");
const scrollToTopBtn = document.getElementById("scrollToTopBtn");

// Show button when user scrolls inside the container
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
  scrollContainer.scrollTo({ top: 0, behavior: "smooth" });
});

function toggleQuotes() {
  const quotePane = document.getElementById("rightPane");

  if (quotePane.classList.contains("hidden")) {
    quotePane.classList.remove("hidden");
  } else {
    quotePane.classList.add("hidden");
  }
}

// show quote btn
document.getElementById("getInspired")
  .addEventListener("click", () => {
    toggleQuotes();
    hideProfileMenu();
  });

