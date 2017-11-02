package gorm.events.demo

import geb.Page

class CarCreatePage extends Page {

    static url = '/car/create'

    static at = {
        title == 'Create Car'
    }

    static content = {
        createButton { $('#create', 0) }
        makeInputField { $('#make', 0) }
        modelInputField { $('#model', 0) }
        logoutLink { $('#logout', 0) }
    }

    void logout() {
        logoutLink.click()
    }

    void saveCar(String make, String model) {
        makeInputField << make
        modelInputField << model
        createButton.click()
    }
}
