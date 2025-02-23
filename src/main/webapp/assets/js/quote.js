const proxyUrl = "https://corsproxy.io/?";

const api_url ="https://zenquotes.io/api/quotes/";

let quotes = [];
    let index = 0;
    let count = 0;
    let autoScroll;

    function fetchQuotes() {
        fetch(proxyUrl + api_url)
            .then(response => response.json())
            .then(data => {
                quotes = data.slice(0, 50);
                index = 0;
                count = 0;
                updateQuote();
                startAutoScroll();
            })
            .catch(error => console.error("Error fetching quotes:", error));
    }

    function updateQuote() {
        if (quotes.length > 0) {
            document.getElementById("quote-text").textContent = quotes[index].q;
            document.getElementById("quote-author").textContent = quotes[index].a ? `- ${quotes[index].a}` : "- Unknown";
            document.getElementById("prev").disabled = (index === 0);
            document.getElementById("next").disabled = (count >= 9);
        }
    }

    function startAutoScroll() {
        clearInterval(autoScroll);
        autoScroll = setInterval(() => {
            if (count < 9) {
                nextQuote();
            } else {
                fetchQuotes();
            }
        }, 10000);
    }

    function nextQuote() {
        if (index < quotes.length - 1) {
            index++;
            count++;
            updateQuote();
        }
    }

    function prevQuote() {
        if (index > 0) {
            index--;
            updateQuote();
        }
    }

    document.getElementById("prev").addEventListener("click", () => {
        prevQuote();
        startAutoScroll();
    });

    document.getElementById("next").addEventListener("click", () => {
        nextQuote();
        startAutoScroll();
    });

fetchQuotes();

// show and hide quote
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

// hide quote
document.getElementById("closeQuote").addEventListener("click", toggleQuotes);