function scrapeEmails() {
  const emailRegex = /([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z0-9._-]+)/gi;
  const emails = document.body.innerText.match(emailRegex);
  return emails ? [...new Set(emails)] : [];
}

chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
  if (request.action === "scrapeEmails") {
    const emails = scrapeEmails();
    sendResponse({ emails: emails });
  }
});
