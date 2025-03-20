import { configureStore } from "./libs/redux-toolkit.esm.js";
import { load } from "./localstorage/control/StorageControl.js";
import { breweries } from "./breweries/entity/BreweriesReducer.js"
import {loadBreweriesRemote} from "./remote/control/ServerControl.js";


const reducer = {
   breweries
}

const preloadedState = load();
const config = preloadedState ? { reducer, preloadedState } : {reducer};
const store = configureStore(config);
loadBreweriesRemote();

export default store;