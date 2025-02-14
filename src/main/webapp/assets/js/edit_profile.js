// close profile settings
{
  document.querySelector("#closeProfileSetting").addEventListener("click", function(){
    const profileModal = document.querySelector("#settingsModal");
    if(!profileModal.classList.contains("hidden")){
      profileModal.classList.add("hidden")
    }
  })
}

// validate uploaded pfp
{
  const pfp = document.querySelector("#pfp");

  pfp.addEventListener("change", function(){
    const file = this.files[0]
    const maxSize = 500 * 1024;
    const parentForm = this.closest("form");
    const fileName = parentForm.querySelector(".file-name");

    if(file.size > maxSize) {
       displayEditProfileError(parentForm, "File should not be greater than 500kb");
    } else {
      displayEditProfileError(parentForm, "");
      fileName.textContent = file.name;
    }
  })
}

// handle error for edit user profile form
function displayEditProfileError(currentEvent, errorMsg){
  let error = currentEvent.querySelector(".error");
  error.textContent = errorMsg;
}
// open profile editing modal
// {
//   const profileSettings = document.querySelector("#profileSettings");
  
//   profileSettings.addEventListener("click", function(event){
//     event.preventDefault();
//     const profileModal = document.querySelector("#settingsModal");
//     const modalContent = document.getElementById('modalContent');
//     modalContent.setAttribute('tabindex', '-1');
//     modalContent.focus();
    
//     closeProfileMenu();
//     profileModal.classList.remove("hidden");
//     profileModal.classList.add("flex");
//   })
// }

 function closeProfileMenu(){
  //  profileMenu already define above as global variable
  profileMenu.classList.add("hidden");
 }

//  upload pfp
{
  document.querySelector("#uploadForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    const inputFile = document.querySelector("#pfp");
    const formData = new FormData();
    const uploadedFile = inputFile.files[0]

    if(uploadedFile === undefined) {
      displayEditProfileError(event.target, "No file selected")
    } else{
        displayEditProfileError(event.target, "");
        formData.append("image", uploadedFile);
        try{
          const response = await fetch("/uploadPfp", {
            method: "POST",
            body: formData
          })
    
          const result = await response.json();
    
          if(response.status != 201){
            console.log("Error:", result.message);
            alert(result.message);
          } else if(response.status == 401){
            console.log("Test here:", result.message);
            alert(result.message)
            window.location.href = "/authenticateUser";
          } else {
            console.log("Success:", result.message);
            alert(result.message);
        }
        } catch(error){
          console.error("Request failed");
          alert("An error occurred while uploading the file.");
        }
      }
  })
}

// add bio -- profile setting
{
  const addBioBtn = document.querySelector("#addBio");
  if(addBioBtn){
    addBioBtn.addEventListener("click", function(){
      const userBioTextContainer = document.querySelector("#bioTextContainer");
      const userBio = document.querySelector("#userBio");
  
      if(userBioTextContainer.classList.contains("hidden")){
        showBio();
        activateEditBio();
        countCharacters(userBio, userBioTextContainer.querySelector(".count-bar"), 166);
      }
    })
  }
}

function countCharacters (textField, countBar, max){
  console.log(countBar);
  textField.addEventListener("input", function(){
    if(textField.value.length > max){
      textField.value = textField.value.substring(0, max);
    }
    countBar.textContent = textField.value.length + "/" + max;
  })
}

// save user bio
{
document.querySelector("#bioForm").addEventListener("submit", 
  async(event) => {
    event.preventDefault();
    const userBio = document.querySelector("#userBio");
    const bioText = userBio.value;
    const maxBioLength = 166;



    if(bioText.length > maxBioLength){
      displayEditProfileError(event.target, "Bio should not be greater than " + maxBioLength + " character")
    } else{
        displayEditProfileError(event.target, "");
        try{
          console.log(bioText)
          const response = await fetch("/updateBio", 
          {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: "userBio=" + encodeURIComponent(bioText),
          })

          let result = await response.text()
          alert(result);
        } catch(error){
          alert(error);
        }
    }
  })
}

// edit bio
{
  const editBioIcon = document.querySelector("#editBio");
  if(editBioIcon){
    editBioIcon.addEventListener("click", function(){
      activateEditBio();
      countCharacters(userBio, this.closest("form").querySelector(".count-bar"), 166);
    })
  }
}
hideOrShowBio();
// show or hide bio textarea
function showBio(){
  const userBioTextContainer = document.querySelector("#bioTextContainer");
    if(userBioTextContainer.classList.contains("hidden")){
      userBioTextContainer.classList.remove("hidden");
      userBioTextContainer.classList.add("flex");
    }
}

function activateEditBio(){
  const userBio = document.querySelector("#userBio");
  document.querySelector("#saveBio").classList.remove("hidden")
  userBio.removeAttribute("readonly")
}

function hideOrShowBio(){
  const userBio = document.querySelector("#userBio");
  if(userBio.value.trim().length > 0){
    showBio();
  }
}