import {APP_SERVER_URL, appName} from "../../app.config.js";

const KEY = `${appName}.localstorage.control`;


const save = (object) => {
    const serialized = JSON.stringify(object);
    localStorage.setItem(KEY, serialized);
}

const load = _ => {
    const storeObject = {
        list: [],
        brewery: {},
        remoteCall: ""
    };
    const ob = {
        "breweries": storeObject
    }
    if (localStorage.getItem(KEY)===undefined) {
        //localStorage.removeItem(KEY);
        localStorage.setItem(KEY, JSON.stringify(ob));
    }

    const serialized = localStorage.getItem(KEY);
    return JSON.parse(serialized);
}


export {load, save};