import {createAction} from "../../libs/redux-toolkit.esm.js";
import store from "../../store.js";
import {deleteBreweryRemote,newBreweryRemote} from "../../remote/control/ServerControl.js";


export const newBreweryAction = createAction("newBreweryAction");
export const newBrewery = (brewery) => {
    store.dispatch(newBreweryAction(brewery));
    newBreweryRemote(brewery);
}

export const deleteBreweryAction = createAction("deleteBreweryAction");
export const deleteBrewery = (id) => {
    store.dispatch(deleteBreweryAction(id));
    deleteBreweryRemote(id);
}
export const updateRemoteCallAction = createAction("updateRemoteCallAction");
export const updateRemoteCall = (value) => {
    store.dispatch(updateRemoteCallAction(value));
}


export const updatedBreweryAction = createAction("updatedBreweryAction");
export const updatedBrewery = (name, value) => {
    store.dispatch(updatedBreweryAction({name, value}));
}

export const loadBreweriesAction = createAction("loadBreweriesAction");
export const loadBreweries = (breweries) => {
    store.dispatch(loadBreweriesAction(breweries));
}
const UUID = () => {
    return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, c =>
        (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16))
}