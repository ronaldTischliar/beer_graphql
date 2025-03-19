import {createReducer} from "../../libs/redux-toolkit.esm.js"
//import { createAsyncThunk, createSlice } from '../../libs/redux-toolkit.esm.js'
import {
    deleteBreweryAction,
    newBreweryAction,
    updatedBreweryAction,
    loadBreweriesAction, updateServerEventAction
} from "../control/CRUDControl.js";

const initialState = {
    list: [],
    brewery: {},
    serverEvent: "",
    //remoteActions :[],
}


const removeBreweryWithId = (list, id) => {
    return list.filter(brewery => brewery.name !== id);
}

export const breweries = createReducer(initialState, (builder) => {
    builder.addCase(deleteBreweryAction, (state, {payload}) => {
        state.list = removeBreweryWithId(state.list, payload);
        state.serverEvent = "delete";

    }).addCase(newBreweryAction, (state, {payload}) => {
        state.list = state.list.concat(payload);
        state.serverEvent = "update";
    }).addCase(updatedBreweryAction, (state, {payload: {name, value}}) => {
        state.brewery[name] = value;
    }).addCase(loadBreweriesAction, (state, {payload}) => {
        state.list = payload;
    }).addCase(updateServerEventAction,(state, {payload}) => {
        state.serverEvent = payload;
    })
})