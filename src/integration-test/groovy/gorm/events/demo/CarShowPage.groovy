package gorm.events.demo

import geb.Page

class CarShowPage extends Page {

    static at = {
        title == 'Show Car'
    }

    static content = {
        deleteButton { $('input.delete', 0) }
        createdByDiv { $('li.fieldcontain', text: contains('Created By')).children('div.property-value') }
        makeDiv { $('li.fieldcontain', text: contains('Make')).children('div.property-value') }
        modelDiv { $('li.fieldcontain', text: contains('Model')).children('div.property-value') }
        logoutLink { $('#logout', 0) }
    }

    void logout() {
        logoutLink.click()
    }

    String createdBy() {
        createdByDiv.text()
    }

    String make() {
        makeDiv.text()
    }

    String model() {
        modelDiv.text()
    }

    void delete() {
        withConfirm { deleteButton.click() }
    }

    Long carId() {
        String path = '/car/show/'
        String url = browser.driver.currentUrl
        url.substring(url.indexOf(path) + path.length(), url.length()) as Long
    }
}
