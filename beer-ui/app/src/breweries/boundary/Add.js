import BElement from "../../BElement.js";
import {html} from "../../libs/lit-html.js";
import {newBrewery, updatedBrewery} from "../control/CRUDControl.js";
import {snackbar} from "../../libs/mdui.esm.js";

class Add extends BElement {

    view() {
        return html`
            <form action="" method="post" @keydown="${e => this.formEvent(e)}">
                <mdui-text-field label="name" required clearable name="name"
                                 @change=${e => this.onUserInput(e)}></mdui-text-field>
                <mdui-text-field type="number" label="yearOfFounding" id="yearOfFounding" required clearable name="yearOfFounding"
                                 @change=${e => this.onUserInput(e)}></mdui-text-field>
                <mdui-text-field label="history" autosize min-rows="2" required clearable name="history"
                                 @change=${e => this.onUserInput(e)}></mdui-text-field>

                <mdui-button full-width @click="${e => this.newBrewery(e)}">new Brewery</mdui-button>
            </form>
        `;
    }

    onUserInput({target: {name, value}}) {
        updatedBrewery(name, value);
    }


    newBrewery(event) {
        const {target: {formController: {form}}} = event;
        const {breweries: {brewery}} = this.state
        event.preventDefault();
        this.validateYearOfFounding( document.getElementById("yearOfFounding"));

        if (form.reportValidity() && form.checkValidity()) {
            newBrewery(brewery);
            snackbar({
                autoCloseDelay: 2000,
                placement: "top-start",
                message: "new Brewery added"
            });
        }

    }


    validateYearOfFounding(element) {
        element.setCustomValidity("");
        if (!element.validity.valid) {
            return;
        }
        if (parseInt(element.value) > 2025) {
            element.setCustomValidity(
                "Please enter an Year until 2025");
        }
    }

    formEvent(event) {
        if (event.keyCode === 13 && !event.shiftKey) {
            event.preventDefault();
        }
    }
}

customElements.define('beer-add', Add);