import React, {Component} from 'react';
import AddonView from "./AddonSpecificationView";
import "./SpecificationView.css";

export default class SpecificationView extends Component {
    render() {
        return (
            <thead>
                <tr className="job-grid-specification">
                    {this.props.specification.getAddons().map(addon =>
                        (<AddonView key={addon.getId()} addon={addon}/>)
                    )}
                </tr>
            </thead>
        )
    }
}