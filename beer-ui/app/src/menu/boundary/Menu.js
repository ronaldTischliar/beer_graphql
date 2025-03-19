import BElement from "../../BElement.js";
import {html} from "../../libs/lit-html.js";
import {appName} from "../../app.config.js";

class Menu extends BElement {

    view() {

        return html`
            <mdui-top-app-bar style="position: relative;">
                <mdui-button-icon icon="list" href="/"></mdui-button-icon>
                <mdui-top-app-bar-title>${appName}</mdui-top-app-bar-title>
                <mdui-button-icon icon="edit" href="/add"></mdui-button-icon>
            </mdui-top-app-bar>
        `
    }

}

customElements.define('beer-menu', Menu);