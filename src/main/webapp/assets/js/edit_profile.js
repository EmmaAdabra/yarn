// validate uploaded pfp
{
  const pfp = document.querySelector("#pfp");

  pfp.addEventListener("change", function(){
    const file = this.files[0]
    const maxSize = 500 * 1024;
    const parentForm = this.closest("form");
    const saveBtn = parentForm.querySelector(".save-icon");
    const fileName = parentForm.querySelector(".file-name");

    if(file.size > maxSize) {
       displayEditProfileError(parentForm, "File should not be greater than 500kb");
       saveBtn.classList.add("hidden");
    } else {
      displayEditProfileError(parentForm, "");
      fileName.textContent = file.name;
      saveBtn.classList.remove("hidden");
      saveBtn.classList.add("flex");
    }
  })
}

// handle error for edit user profile form
function displayEditProfileError(currentEvent, errorMsg){
  let error = currentEvent.querySelector(".error");
  error.textContent = errorMsg;
}

 function closeProfileMenu(){
  //  profileMenu already define above as global variable
  profileMenu.classList.add("hidden");
 }


 function deactivateEditForPfpAndBio(form){
  const input = form.querySelector("input");
   if(form.querySelector(".edit-bio").classList.contains("hidden")) {
     form.querySelector(".edit-bio").classList.remove("hidden");
     form.querySelector(".add-bio").classList.add("hidden");
   }
   input.readOnly = true;
   input.classList.remove("text-main_text")
 }

//  upload pfp
{
  document.querySelector("#uploadPfpForm").addEventListener("submit", async function(event){
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

          let resultStatus = Number.parseInt(result.status);

          switch (resultStatus) {
            case 201:
              showToast("success", "pfp updated successfully, refresh page")
              deactivateEditForPfpAndBio(this);
              break;
            case 404:
            case 401:
              window.location.href = "/authenticateUser";
              break;
            default:
              showToast("error", result.message);
              console.error(`Error: ${result.message}`)
          }
        } catch(error){
          showToast("error", "Couldn't connect to server");
          console.error("Fect error", error);
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
      // showBio();
      activateEditBio();
      countCharacters(userBio, userBioTextContainer.querySelector(".count-bar"), 166);
    })
  }
}

function countCharacters (textField, countBar, max){
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
  async function (event) {
    event.preventDefault();
    const userBio = document.querySelector("#userBio");
    const bioText = userBio.value;
    const maxBioLength = 166;



    if(bioText.length > maxBioLength){
      displayEditProfileError(event.target, "Bio should not be greater than " + maxBioLength + " character")
    } else{
        displayEditProfileError(event.target, "");
        try{
          const response = await fetch("/updateBio",
          {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: "userBio=" + encodeURIComponent(bioText.trim()),
          })

          const result = await response.json();

          let resultStatus = Number.parseInt(result.status);

          switch (resultStatus) {
            case 201:
              showToast("success", "Bio updated successfully")
              this.querySelector("#saveBio").classList.add("hidden");
              this.querySelector(".count-bar").textContent = "";
              deactivateEditForPfpAndBio(this);
              break;
            case 404:
            case 401:
              window.location.href = "/home";
              break;
            default:
              console.error("error", result.message)
              console.error(`Error: ${result.message}`)
          }
        } catch(error){
          showToast("error", "Couldn't connect to server");
          console.error("Fect error", error);
        }
    }
  })
}

{
  const editBioIcon = document.querySelector("#editBio");
  if(editBioIcon){
    editBioIcon.addEventListener("click", function(){
      activateEditBio();
      countCharacters(userBio, this.closest("form").querySelector(".count-bar"), 166);
    })
  }
}

function activateEditBio(){
  const userBio = document.querySelector("#userBio");
  userBio.classList.remove("hidden");
  userBio.readOnly = false;
  userBio.classList.add("text-main_text");
  document.querySelector("#saveBio").classList.remove("hidden")
}

// delegate click event listener to edit data buttons
const editPersonalDataForm = document.getElementById("editPersonalDataForm");
editPersonalDataForm.addEventListener("click", function (event){
  let target = event.target;

  if(target.closest(".edit-data")){
    const dataInput = target.closest(".data-wrapper").querySelector("input");
    dataInput.removeAttribute("readonly");
    dataInput.classList.add("text-main_text");
  }
})


// Note: sharing common global variable (editDataInputs, submitButton)
{
  // activate save btn for personal date edit
// Note: 'editDataInputs' used by other function and methods
  const editDataInputs = document.querySelectorAll("#editPersonalDataForm input");
  editDataInputs.forEach(input => {
    const saveBtn = input.closest("form").querySelector("button");
    ["input", "change"].forEach(eventType => {
      input.addEventListener(eventType, activateSavePersonalDataBtn.bind(null, saveBtn));
    })
  })

  function activateSavePersonalDataBtn(saveBtn, event){
    const input = event.target;
    if(input.value.trim().length > 0){

      saveBtn.disabled = false;
      saveBtn.classList.remove("pointer-events-none");

    } else {
      saveBtn.disabled = true;
      saveBtn.classList.add("pointer-events-none");
    }
  }

  // submitting personal edited personal data
  editPersonalDataForm.addEventListener("submit", async function(event){
    event.preventDefault();
    const email = this.querySelector('#email').value;
    const firstName = this.querySelector('#firstName').value;
    const lastName = this.querySelector("#lastName").value;

    const personalData = new URLSearchParams();
    personalData.append("email", email.trim());
    personalData.append("firstName", firstName.trim());
    personalData.append("lastName", lastName.trim());

    try {
      const response = await fetch("/editPersonalData", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: personalData,
      })

      const result = await response.json();

      let resultStatus = Number.parseInt(result.status);

      switch (resultStatus) {
        case 201:
          showToast("success", "Data updated successfully")
          deactivatePersonalDataFormEdit();
          break;
        case 404:
        case 401:
          alert(result.message)
          window.location.href = "/home";
          break;
        default:
          showToast("error", result.message)
          console.error(`Error: ${result.message}`)
      }
    } catch(error){
      showToast("error", "Couldn't connect to server");
      console.error("Fect error", error);
    }
  })

  // Note: sharing common global variable (editDataInputs, submitButton)
// Select the form and submit button
  const submitButton = editPersonalDataForm.querySelector("button");

// Regex for name validation
  const nameRegex = /^[A-Za-z]+([-' ][A-Za-z]+)*$/;

// Function to validate a single input field
  function validateInput(input) {
    const errorElement = document.getElementById(`${input.id}-error`);

    if (input.type === "email") {
      // Validate email using built-in validation
      if (input.validity.valid) {
        errorElement.textContent = ""; // Clear error message
        return true;
      } else {
        errorElement.textContent = "Please enter a valid email address.";
        return false;
      }
    } else if (input.type === "text") {
      // Validate name using custom regex
      if (nameRegex.test(input.value) && input.value.length < 50) {
        errorElement.textContent = ""; // Clear error message
        return true;
      } else {
        errorElement.textContent = "Please enter a valid name (letters, apostrophes, or hyphens" +
            " only) and less than 50 characters.";
        return false;
      }
    }
  }

// Function to check the validity of the entire form
  function isFormValid() {
    const inputs = editPersonalDataForm.querySelectorAll("input");
    for (const input of inputs) {
      if (!validateInput(input)) {
        return false; // Form is invalid if any input is invalid
      }
    }
    return true; // Form is valid
  }

// Function to handle input or change events
  function handleInputChange(event) {
    // Validate only the input that triggered the event
    const input = event.target;
    validateInput(input);

    // Update the submit button state based on the form's validity
    submitButton.disabled = !isFormValid();
    submitButton.classList.toggle("pointer-events-none", !isFormValid());
  }

// Attach event listeners to all input fields
  function attachValidationListeners() {
    const inputs = editPersonalDataForm.querySelectorAll("input");
    inputs.forEach((input) => {
      input.addEventListener("input", handleInputChange); // Validate on input change
      input.addEventListener("change", handleInputChange); // Validate on blur
    });
  }

  function deactivatePersonalDataFormEdit(){
    editPersonalDataForm.querySelectorAll("input").forEach(input => {
      input.readOnly = true;
      input.classList.remove("text-main_text")

      submitButton.disabled = true;
      submitButton.classList.toggle("pointer-events-none");
    })
  }

// Initialize the form validation
  attachValidationListeners();

// Validate the form on page load (in case of pre-filled values)
  submitButton.disabled = !isFormValid();
}

