import {createReducer} from "../../libs/redux-toolkit.esm.js"
//import { createAsyncThunk, createSlice } from '../../libs/redux-toolkit.esm.js'
import {
    deleteBreweryAction,
    newBreweryAction,
    updatedBreweryAction,
    loadBreweriesAction, updateRemoteCallAction
} from "../control/CRUDControl.js";

const initialState = {
    list: [],
    brewery: {},
    remoteCall: false
    //remoteActions :[],
}


const removeBreweryWithId = (list, id) => {
    return list.filter(brewery => brewery.name !== id);
}

export const breweries = createReducer(initialState, (builder) => {
    builder.addCase(deleteBreweryAction, (state, {payload}) => {
        state.list = removeBreweryWithId(state.list, payload);
        state.remoteCall = true;

    }).addCase(newBreweryAction, (state, {payload}) => {
        state.list = state.list.concat(payload);
        state.remoteCall = true;
    }).addCase(updatedBreweryAction, (state, {payload: {name, value}}) => {
        state.brewery[name] = value;
    }).addCase(loadBreweriesAction, (state, {payload}) => {
        state.list = payload;
    }).addCase(updateRemoteCallAction,(state, {payload}) => {
        state.remoteCall = payload;
    })
})