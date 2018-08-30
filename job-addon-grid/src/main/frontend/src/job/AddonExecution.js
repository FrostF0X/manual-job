import Addon from "./Addon";

export default class AddonExecution {

    constructor(info) {
        this.addon = new Addon(info.addon);
    }

    getAddon() {
        return this.addon;
    }
}