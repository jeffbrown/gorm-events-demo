package gorm.events.demo

import geb.spock.GebSpec
import gorm.events.demo.auth.Role
import gorm.events.demo.auth.User
import gorm.events.demo.auth.UserRole
import grails.testing.mixin.integration.Integration
import org.openqa.selenium.htmlunit.HtmlUnitDriver

@Integration
class CarControllerSpec extends GebSpec {

    CarService carService

    def setup() {
        User.withTransaction {
            User user = User.where { username == 'sherlock' }.get() ?: new User(username: 'sherlock', password: 'elementary')
            user.save()
            Role role = Role.where { authority == 'ROLE_USER' }.get() ?: new Role(authority: 'ROLE_USER')
            role.save()
            UserRole userRole = new UserRole(user: user, role: role)
            userRole.save()
        }
    }

    void 'test car get enhanced with audit information'() {
        when:
        go '/car/index'

        then: 'it is redirected to login page'
        at LoginPage

        when: 'signs in with a ROLE_USER roel'
        login('sherlock', 'elementary')

        then: 'he gets access to the car listing page'
        at CarIndexPage

        when: 'he clicks to create a new car'
        createCar()

        then: 'he gets access to the car listing page'
        at CarCreatePage

        when: 'he fills the form'
        saveCar('Audi', 'A3')

        then: 'he gets redirected to the car listing page'
        at CarShowPage
        createdBy() == 'sherlock'
        make() == 'Audi'
        model() == 'A3'

        when:
        Long carId = carId()

        then:
        carId
        noExceptionThrown()

        when:
        if ( browser.driver instanceof HtmlUnitDriver ) {
            // if HtmlUnit delete it manually, problems confirming the alert dialog
            carService.delete(carId)
            to CarIndexPage
        } else {
            delete()
        }

        then:
        at CarIndexPage
    }
}
