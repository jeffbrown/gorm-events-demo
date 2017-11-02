package gorm.events.demo

import geb.Page

class CarIndexPage extends Page {

    static url = '/car/index'

    static at = {
        title == 'Car List'
    }

    static content = {
        createCarButton { $('a.create', 0) }
        logoutLink { $('#logout', 0) }
    }

    void logout() {
        logoutLink.click()
    }

    void createCar() {
        createCarButton.click()
    }
}
