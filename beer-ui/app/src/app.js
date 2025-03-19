import {Router} from "./libs/vaadin-router.js";
import store from "./store.js";
import './breweries/boundary/List.js';
import './breweries/boundary/Brewery.js';
import './breweries/boundary/Add.js';
import './menu/boundary/Menu.js';


import {save} from "./localstorage/control/StorageControl.js";
import {loadBreweriesRemote} from "./server/control/ServerControl.js";

store.subscribe(_ => {
    const state = store.getState();
    save(state);
})
const outlet = document.querySelector('.view');
export const router = new Router(outlet);
router.setRoutes([
    {path: '/add', component: 'beer-add'},
    {path: '/viewer/:id', component: 'beer-brewery'},
    {path: '/', component: 'beer-list'},

]);

const timerId = setInterval(() => {
    const {breweries: {serverEvent}} = store.getState();
    if(serverEvent) {
        console.log("------------ Timeout every 10 Seconds " + new Date().toISOString());
        loadBreweriesRemote();
    }
}, 10000); // 100

//navigator.serviceWorker.register('/sw.js').
//   then(registration => console.log('registration succeeded', registration)).
//   catch(error => console.error('registration not successful',error));