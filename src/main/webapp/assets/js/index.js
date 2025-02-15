// show nav profile menu
{
  const profileIcon = document.querySelector("#navPfp");
  const profileMenu = document.querySelector("#profileMenu");
  profileIcon.addEventListener("click", (event) => {
    event.stopPropagation();
  profileMenu.classList.toggle("hidden");
  });

  // close top nave profile menu on click even not within profile menu
  document.addEventListener("click", function(event){
    if(!profileMenu.contains(event.target)){
      profileMenu.classList.add("hidden");
    }
  })
}




// open create post modal
{
  const createPostBtn = document.querySelector("#createPostBtn");
  
  createPostBtn.addEventListener("click", function(event){
    event.preventDefault();
    const createPostModal = document.querySelector("#createPostModal");
    
    createPostModal.classList.remove("hidden");
    createPostModal.classList.add("flex");
  })
}

// close modal
{
  const closeModalBtns = document.querySelectorAll(".modal-close-icon");
  closeModalBtns.forEach(icon => {
    icon.addEventListener("click", function(event){
      event.preventDefault();
      const modalElement = event.target.closest(".modal-container");

      if(modalElement){
        modalElement.classList.remove("flex");
        modalElement.classList.add("hidden");
      }
    })
  })
}

// auto resize post text area
// ToDO: make the resizable for all text area
{
  const postTextArea = document.querySelector("#postTextArea");
  postTextArea.addEventListener("input", function(){
    this.style.height = "auto";

    this.style.height = Math.min(this.scrollHeight, 320) + 'px';
  })
}

// activate character count on element with attribute maxlength
{
  const maxLengthElem = document.querySelectorAll("[maxlength]");

  maxLengthElem.forEach(elem => {
    elem.addEventListener("input", function(){
      const parentElement = this.parentElement;
      const maxLength = parseInt(elem.getAttribute("maxlength")) - 1;
      const countBar = parentElement.querySelector(".count-bar");

      if(countBar){
        if(elem.value.length > maxLength){
          countBar.classList.remove("hidden");
          elem.value = elem.value.substring(0, maxLength);
        } else{
          countBar.classList.add("hidden")
        }
  
        countBar.textContent = `${elem.value.length}/${maxLength}`;
      }
    })
  })
}



// submit post
{
  const postForm = document.querySelector("#postForm");

if(postForm){
  postForm.addEventListener("submit", async(event) => {
    event.preventDefault();

    // const postTitle = document.querySelector("#postTitle");
    const postText = document.querySelector("#postTextArea");
    const postImage = document.querySelector("#postImage").files[0]

    const postData = new FormData();

    if (postText.value.length > 0 || postImage != undefined) {
      // postData.append("postTitle", postTitle.value);
      postData.append("postContent", postText.value);

      if (postImage !== undefined) {
        postData.append("postImage", postImage);
      }

      try {
        const response = await fetch("/submit_post", {
          method: "POST",
          body: postData
        })

        const result = await response.json();

        if (result.status !== 201) {
          throw new Error(result.message || "Something went wrong.")
        } 

        alert(result.message);
        location.reload();
        
      } catch (error) {
        alert(error.message);
        console.error(`Error: ${error.message}`);
      }
    } else {
      alert("Can't submit a empty post (Test)")
    }
  })
}
}

// validate post attachment
{
  const postImage = document.querySelector("#postImage");

  postImage.addEventListener("change", function(){
    const file = this.files[0]
    const maxSize = 500 * 1024;
    const parentForm = this.closest("form");
    const fileName = parentForm.querySelector(".file-name");

    if(file.size > maxSize) {
       displayEditProfileError(parentForm, "Attach image should not be greater than 500kb");
    } else {
      displayEditProfileError(parentForm, "");
      fileName.textContent = file.name;
    }
  })
}

function displayEditProfileError(currentEvent, errorMsg){
  let error = currentEvent.querySelector(".error");
  error.textContent = errorMsg;
}

// post text with more that 6 lines
{
  const allPostText = document.querySelectorAll(".post-text");
  
  allPostText.forEach(postText => {
    const seeMoreBtn = postText.closest(".post").querySelector(".see-more-btn");
    if(postText.scrollHeight > postText.clientHeight){
      postText.setAttribute("data-big-text", "true");
      seeMoreBtn.classList.remove("hidden");
    }
  })
}

// all see more buttons
{
  const seeMoreBtns = document.querySelectorAll(".see-more-btn");
  
  seeMoreBtns.forEach(btn => {
    btn.addEventListener("click", function(){
      const postText = this.closest(".post").querySelector(".post-text");
      postText.classList.remove("ellipsis");
      btn.classList.add("hidden");
      
    })
  })
}

// show comment modal
{
  const commentBtns = document.querySelectorAll(".comment-icon");
  
  commentBtns.forEach(btn => {
    btn.addEventListener("click", function(event){
      event.preventDefault();
      const modalElement = this.closest(".post");
      
      const commentModal = modalElement.querySelector('[data-comment-modal = "ModalContainer"]');
    commentModal.classList.add("comment-modal");

    const header = modalElement.querySelector('[data-comment-modal = "modalHeader"]');
    header.classList.remove("hidden");

    // for show more button
    const postText = modalElement.querySelector(".post-text");
    if(postText.getAttribute("data-big-text") == "true"){
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

    })
  })
}

// close comment modal
{
  const closeCommentBtn = document.querySelectorAll(".close-comment-btn");
  closeCommentBtn.forEach(btn => {
    btn.addEventListener("click", function(){
      const modalElement = this.closest(".post");
      const commentModal = modalElement.querySelector('[data-comment-modal = "ModalContainer"]');
      commentModal.classList.remove("comment-modal");
  
      const header = modalElement.querySelector('[data-comment-modal = "modalHeader"]');
      header.classList.add("hidden");
  
      const content = modalElement.querySelector('[data-comment-modal = "postContainer"]');
      content.classList.remove("comment-modal-content");

      // big text and see more button
    const postText = modalElement.querySelector(".post-text");
    if(postText.getAttribute("data-big-text") == "true"){
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
      // commentContainer.innerHTML = "";
    })
  })
}

// deactivate comment button
{
  const commentBoxes = document.querySelectorAll(".comment-form textarea");
  commentBoxes.forEach(commentBox => {
    const commentBtn = commentBox.parentElement.querySelector("button");

    commentBox.addEventListener("input", function(){
      console.log(commentBox.value)
      if(commentBox.value.length > 0){
        commentBtn.disabled = false;
        commentBtn.classList.remove("cursor-not-allowed")
        commentBtn.classList.add("text-logo_clr1")
      } else {
        commentBtn.disabled = true;
        commentBtn.classList.remove("text-logo_clr1")
        commentBtn.classList.add("cursor-not-allowed")
        commentBtn.classList.add("text-fade_text")
      }
    })
  })
}


// AJAX for submitting comment on posts
{
  const commentForms = document.querySelectorAll(".comment-form");
  
  commentForms.forEach(form => {
    form.addEventListener("submit", async function(event){
      event.preventDefault();
      
      const submitBtn = form.querySelector("button");
      const postId = submitBtn.getAttribute("data-post-id");
      const comment = form.querySelector("textarea").value;

      try{
        const response = await fetch("/add_comment", {
          method: "POST",
          header: {'Content-Type': 'application/json'},
          body: JSON.stringify({postId: postId, comment: comment})
        });


        console.log("response info: ", response.ok, response.status);

        if(response.ok){
          const result = await response.json();

          console.log("result:", result)

          if(result.status !== 201){
            alert(result.message || "something went wrong");
            console.error(result.message || "something went wrong");
          } else{
            alert(result.message);
          }
        }
        
      } catch(error){
        alert("some thing went wrong, check console for detail");
        console.error(error.message);
      }
    })
  })
}