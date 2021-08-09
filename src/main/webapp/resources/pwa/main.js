// const divInstall = document.getElementById('installContainer');
// const butInstall = document.getElementById('butInstall');

/* Put code here */

let deferredPrompt;
window.addEventListener('beforeinstallprompt', (e) => {
  // Prevent the mini-infobar from appearing on mobile
  // Stash the event so it can be triggered later.
  deferredPrompt = e;
  // Update UI notify the user they can install the PWA
  // Optionally, send analytics event that PWA install promo was shown.
  console.log(`'beforeinstallprompt' event was fired.`);
});

/* Only register a service worker if it's supported */
if ('serviceWorker' in navigator) {
  navigator.serviceWorker.register('/resources/pwa/service-worker.js');
}

/**
 * Warn the page must be served over HTTPS
 * The `beforeinstallprompt` event won't fire if the page is served over HTTP.
 * Installability requires a service worker with a fetch event handler, and
 * if the page isn't served over HTTPS, the service worker won't load.
 */
if (window.location.protocol === 'http:') {
//   const requireHTTPS = document.getElementById('requireHTTPS');
//   const link = requireHTTPS.querySelector('a');
//   link.href = window.location.href.replace('http://', 'https://');
//   requireHTTPS.classList.remove('hidden');
}