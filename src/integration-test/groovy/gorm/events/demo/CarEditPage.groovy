package gorm.events.demo

import geb.Page

class CarEditPage extends Page {

    static at = {
        title == 'Edit Car'
    }

    static content = {
        saveButton { $('#save', 0) }
        makeInputField { $('#make', 0) }
        modelInputField { $('#model', 0) }
    }

    void updateCar(String make, String model) {
        makeInputField = make
        modelInputField = model
        saveButton.click()
    }
}
