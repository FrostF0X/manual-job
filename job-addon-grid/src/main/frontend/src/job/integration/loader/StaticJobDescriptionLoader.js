import JobFactory from "./JobFactory";

export default class StaticJobDescriptionLoader {
    DESCRIPTION = {
        "buildDescriptions": [{
            "addonExecutions": [{
                "addon": {
                    "name": "Deploy staging",
                    "id": "deploy-stage",
                },
                "url": "http://localhost:3000/test"
            }, {
                "addon": {
                    "name": "Deploy staging",
                    "id": "deploy-prod",
                },
                "url": "http://localhost:3000/test"
            }], "id": "4"
        }, {
            "addonExecutions": [{
                "addon": {
                    "name": "Deploy staging",
                    "id": "deploy-stage",
                },
                "url": "http://localhost:3000/test"
            }, {
                "addon": {
                    "name": "Deploy staging",
                    "id": "deploy-prod",
                },
                "url": "http://localhost:3000/test"
            }], "id": "3"
        }, {
            "addonExecutions": [{
                "addon": {
                    "name": "Deploy staging",
                    "id": "deploy-stage",
                },
                "url": "http://localhost:3000/test"
            }, {
                "addon": {
                    "name": "Deploy staging",
                    "id": "deploy-prod",
                },
                "url": "http://localhost:3000/test"
            }], "id": "2"
        }, {
            "addonExecutions": [{
                "addon": {
                    "name": "Old stage",
                    "id": "old-deploy-stage",
                },
                "url": "http://localhost:3000/test"
            }], "id": "1"
        }]
    };

    async load() {
        return JobFactory.fromResponse(this.DESCRIPTION);
    }
}