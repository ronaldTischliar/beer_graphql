import {APP_SERVER_URL} from "../../app.config.js";
import {loadBreweries, updateServerEvent} from "../../breweries/control/CRUDControl.js";

const GRAPHQL_URL = APP_SERVER_URL + "/graphql";

const postGraphQL = async (postObject) => {
    const request = new Request(GRAPHQL_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(postObject),
    });
    try {
        const response = await fetch(request, {signal: AbortSignal.timeout(5000)});
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error("An error occurred during POST request:", error);
        return null;
    }
};

export const loadBreweriesRemote = async _ => {

    const query = `query allBreweryQuery {
breweries {
    name
    yearOfFounding
    createTime
    history
    beers {
      name
      alcByVol
    }
}
}`;
    const postObject = {"query": query};
    const responseData = await postGraphQL(postObject);
    if (responseData) {
        const {data: {breweries}} = responseData;
        loadBreweries(breweries);
    } else {

    }

}


export const deleteBreweryRemote = async (id) => {
    console.log(`--------->Removing brewery ${id}`);
    const query = `mutation deleteBrewery($name: String) {
          deleteBrewery(name: $name) {
            name
            yearOfFounding
            createTime} }`;
    const postObject = {"query": query, "variables": {"name": id}};
    const responseData = await postGraphQL(postObject);
    if (responseData) {
        const {data,errors} = responseData;
        if(!errors) {
            updateServerEvent("");
        }
    } else {

    }

}

export const newBreweryRemote = async (breweryObject) => {
    console.log(`--------->create brewery ${breweryObject.name}`);
    const query = `mutation createBrewery($brewery: BreweryInput) {
           createBrewery(brewery: $brewery) {
                      name
                      yearOfFounding
                      history
                    }
                    }`;
    const postObject = {"query": query, "variables": {"brewery": breweryObject}};
    const responseData = await postGraphQL(postObject);
    console.log("------->new Brewery  log" + responseData);
    if (responseData) {
        const {data,errors} = responseData;
        if(!errors) {
            updateServerEvent("");
        }
    } else {

    }
}


/* rest query example
const restQuery = async () => {
    const response = await fetch(APP_SERVER_URL + "/breweries", {signal: AbortSignal.timeout(5000)});
    await extractedResponseRest(response);
}


const extractedResponseRest = async (response) => {
    try {
        if (!response.ok) {
            console.error(`Response status: ${response.status}`);
        } else {
            const storeObject = {
                list: [],
                brewery: {}
            };
            storeObject["list"] = await response.json();
            const ob = {
                "breweries": storeObject
            }
            console.log(JSON.stringify(ob));
            localStorage.removeItem(KEY);
            localStorage.setItem(KEY, JSON.stringify(ob));
        }

    } catch (error) {
        console.error(error.message);
    }
}
*/
