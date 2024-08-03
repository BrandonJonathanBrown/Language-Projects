chrome.action.onClicked.addListener((tab) => {
  chrome.scripting.executeScript(
    {
      target: { tabId: tab.id },
      files: ['content.js']
    },
    () => {
      chrome.tabs.sendMessage(tab.id, { action: "scrapeEmails" }, (response) => {
        if (response && response.emails) {
          chrome.storage.local.set({ emails: response.emails }, () => {
            console.log('Emails saved:', response.emails);
          });
        }
      });
    }
  );
});
