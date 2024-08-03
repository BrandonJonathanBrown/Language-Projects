document.addEventListener('DOMContentLoaded', function() {
  chrome.tabs.query({ active: true, currentWindow: true }, function(tabs) {
    chrome.scripting.executeScript(
      {
        target: { tabId: tabs[0].id },
        files: ['content.js']
      },
      () => {
        chrome.tabs.sendMessage(tabs[0].id, { action: "scrapeEmails" }, (response) => {
          if (response && response.emails) {
            const emailList = document.getElementById('emailList');
            response.emails.forEach(email => {
              const listItem = document.createElement('li');
              listItem.textContent = email;
              emailList.appendChild(listItem);
            });

            chrome.storage.local.set({ emails: response.emails }, () => {
              console.log('Emails saved locally');
            });
          }
        });
      }
    );
  });

  document.getElementById('saveBtn').addEventListener('click', function() {
    chrome.storage.local.get('emails', function(data) {
      if (data.emails) {
        const blob = new Blob([data.emails.join('\n')], { type: 'text/plain' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'emails.txt';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
      }
    });
  });
});
