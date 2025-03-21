// Toast function
function showToast(type, message) {
    const toastContainer = document.getElementById("toast-container");

    // Create toast element
    const toast = document.createElement("div");
    toast.className = `toast flex items-center p-4 rounded-lg shadow-lg ${type === "success" ? "bg-[#15803D]" : "bg-[#DC2626]"} text-white`;

    // Add icon based on type
    const icon = document.createElement("i");
    icon.className = type === "success" ? "fas fa-check-circle mr-2" : "fas fa-exclamation-circle mr-2";
    toast.appendChild(icon);

    // Add message
    const messageElement = document.createElement("span");
    messageElement.textContent = message;
    toast.appendChild(messageElement);

    // Add close button
    const closeButton = document.createElement("button");
    closeButton.innerHTML = "&times;";
    closeButton.className = "ml-4 text-lg hover:text-gray-300";
    closeButton.onclick = () => dismissToast(toast);
    toast.appendChild(closeButton);

    // Append toast to container
    toastContainer.appendChild(toast);

    // Auto-dismiss after 5 seconds
    setTimeout(() => dismissToast(toast), 5000);
}

// Dismiss toast function
function dismissToast(toast) {
    toast.classList.add("fade-out");
    toast.addEventListener("animationend", () => toast.remove());
}