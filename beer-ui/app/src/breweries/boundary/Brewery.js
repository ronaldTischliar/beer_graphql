import BElement from "../../BElement.js";
import {html} from "../../libs/lit-html.js";
import {router} from '../../app.js';


class Brewery extends BElement {


    extractState(redux) {

        const id = router.location.params.id;
        return redux.breweries.list.filter(t => t.name === id)[0];
    }

    view() {
        const brewery = this.state;
        return html`
            <h2>Brewery ${brewery.name}</h2>
            <strong>Year of Founding :</strong> ${brewery.yearOfFounding}
            <mdui-divider></mdui-divider>
            <details>
                <summary>History</summary>
                ${brewery.history}
            </details>
            <mdui-divider></mdui-divider>
            ${this.showBeers(brewery)}
        `;
    }


    showBeers = (brewery) => {
        if (brewery.beers === undefined) {
            return html`<h3>No Beers</h3>`;
        }
        return html`<h3>Beers</h3>
        <mdui-list>
            <mdui-list>
                ${brewery.beers.map((beer) => html`
                            <mdui-list-item>${beer.name} with ${beer.alcByVol} %</mdui-list-item>
                        `
                )}
            </mdui-list>
        </mdui-list>`;
    }
}

customElements.define('beer-brewery', Brewery);