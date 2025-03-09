const proxyUrl = "https://corsproxy.io/?";

const api_url ="https://zenquotes.io/api/quotes/";

let quote = [];
    let index = 0;
    let count = 0;
    let autoScroll;

    function fetchQuotes() {
        fetch(proxyUrl + api_url)
            .then(response => response.json())
            .then(data => {
                quote = data.slice(0, 50);
                index = 0;
                count = 0;
                updateQuote();
                startAutoScroll();
            })
            .catch(error => console.error("Error fetching quote:", error));
    }

    function updateQuote() {
        if (quote.length > 0) {
            document.getElementById("quote-text").textContent = quote[index].q;
            document.getElementById("quote-author").textContent = quote[index].a ? `- ${quote[index].a}` : "- Unknown";
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
        if (index < quote.length - 1) {
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
    const quoteContainer = document.getElementById("quoteContainer");
    if (quoteContainer.classList.contains("hidden")) {
      quoteContainer.classList.remove("hidden");
    } else {
      quoteContainer.classList.add("hidden");
    }
  }

// show quote btn
const showQuotesBtns = document.querySelectorAll(".show-quotes");
showQuotesBtns.forEach(btn => {
    btn.addEventListener("click", () => {
        toggleQuotes();
        hideProfileMenu();
    })
})

// hide quote
document.getElementById("closeQuote").addEventListener("click", toggleQuotes);