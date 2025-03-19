import BElement from "../../BElement.js";
import {html} from "../../libs/lit-html.js";
import {deleteBrewery} from "../control/CRUDControl.js";

class List extends BElement {


    extractState(redux) {
        return redux;
    }

    view() {
        const {breweries: {list}} = this.state

        return html`
            <h2>Breweries</h2>
            <mdui-list>
                ${list.map((brewery) => html`
                    <mdui-list-item href="viewer/${brewery.name}" target="">${brewery.name}
                        <mdui-avatar slot="icon" src="../../../images/beer.svg"></mdui-avatar>
                        <span slot="description"></span>
                        <mdui-button-icon icon="delete" slot="end-icon"
                                          @click="${event => this.deleteBrewery(event, brewery)}">
                        </mdui-button-icon>

                    </mdui-list-item>`
        )}
            </mdui-list>
        `;
    }

    deleteBrewery(event, brewery) {
        event.preventDefault();
        deleteBrewery(brewery.name);
    }
}

customElements.define('beer-list', List);