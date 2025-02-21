const proxyUrl = "https://corsproxy.io/?";

const api_url ="https://zenquotes.io/api/quotes/";

// async function fetchQuote() {
//   const reloadIcon = document.getElementById("reload-icon");
//   const spinner = document.getElementById("spinner");

//   // Show spinner, hide refresh icon
//   reloadIcon.classList.add("hidden");
//   spinner.classList.remove("hidden");

//   try {
//       // const response = await fetch("https://zenquotes.io/api/random");
//       // const data = await response.json();
//       const response = await fetch(proxyUrl + api_url);
//         const data = await response.json();
//         console.log(data);
      
//       // Extract quote and author
//       const quote = data[0].q;
//       const author = data[0].a;

//       // Update UI
//       document.getElementById("quote-text").textContent = quote;
//       document.getElementById("quote-author").textContent = `- ${author}`;
//   } catch (error) {
//       console.error("Error fetching quote:", error);
//       document.getElementById("quote-text").textContent = "Failed to load quote.";
//       document.getElementById("quote-author").textContent = "";
//   } finally {
//       // Show refresh icon, hide spinner
//       reloadIcon.classList.remove("hidden");
//       spinner.classList.add("hidden");
//   }
// }

// Auto-refresh every 20 seconds (3 times per minute)
// setInterval(fetchQuote, 200000000000);

// Fetch initial quote on page load
// fetchQuote();

// Reload button click event
// document.getElementById("reload").addEventListener("click", fetchQuote);

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