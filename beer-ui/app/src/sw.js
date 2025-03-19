const cacheName = "beer-cache-v0.0.1";
const resources = [
    /*"BElement.js",
    "app.config.js",
    "index.html",
    "style.css",
    "app.js",
    "init.js",
    "store.js",
    "webmanifest.json",
    "sw.js",
    "../images/beer.svg"*/
    ];
/*
 ├── 
  
  
  
    ├── insurances
    │   ├── boundary
    │   │   ├── Add.js
    │   │   ├── Filter.js
    │   │   ├── Insurance.js
    │   │   ├── Insurances.js
    │   │   ├── List.js
    │   │   └── Preview.js
    │   ├── control
    │   │   ├── CRUDControl.js
    │   │   └── FilterControl.js
    │   └── entity
    │       ├── FilterReducer.js
    │       ├── InsurancesReducer.js
    │       └── MatchesCriteria.js
    ├── libs
    │   ├── lit-html.js
    │   ├── redux-toolkit.esm.js
    │   └── vaadin-router.js
    ├── localstorage
    │   └── control
    │       └── StorageControl.js
    ├── microfrontend
    │   ├── boundary
    │   │   └── Microfrontend.js
    │   ├── control
    │   │   └── MicrofrontendControl.js
    │   └── entity
    │       └── MicrofrontendReducer.js
   


 */
const prefetch = (name) =>
  caches.open(name).then((cache) => cache.addAll(resources));

self.addEventListener("install", (event) => {
    self.skipWaiting();
    event.waitUntil(prefetch(cacheName));
});

/*self.addEventListener("fetch", (event) => {
    const { request } = event;
    console.log("onfetch ", request);
    event.respondWith(
        caches.match(request).then((response) => response || fetch(request)),
        );
});*/

self.addEventListener("activate", (event) => {
    console.log("cleaning old caches");
    self.clients.claim();
    const staleCaches = caches
    .keys()
    .then((keys) =>
      keys
        .filter((key) => key !== cacheName)
        .map((stale) => caches.delete(stale)),
        );
    event.waitUntil(staleCaches);
});

/*
self.addEventListener("message", (event) => {
    console.log(event);
    caches.delete(cacheName).then((_) => prefetch(cacheName));
}); */
