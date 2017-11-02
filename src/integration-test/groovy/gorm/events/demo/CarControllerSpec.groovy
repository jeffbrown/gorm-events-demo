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
            User sherlock = User.where { username == 'sherlock' }.get() ?: new User(username: 'sherlock', password: 'elementary')
            sherlock.save()
            User watson = User.where { username == 'watson' }.get() ?: new User(username: 'watson', password: 'elementary')
            watson.save()
            Role role = Role.where { authority == 'ROLE_USER' }.get() ?: new Role(authority: 'ROLE_USER')
            role.save()

            UserRole.create sherlock, role
            UserRole.create watson, role
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

        and:
        createdBy() == 'sherlock'
        lastUpdatedBy() == 'sherlock'
        make() == 'Audi'
        model() == 'A3'

        when:
        Long carId = carId()

        then:
        carId
        noExceptionThrown()

        when:
        logout()
        go "/car/edit/${carId}"

        then: 'it is redirected to login page'
        at LoginPage

        when: 'signs in with a ROLE_USER roel'
        login('watson', 'elementary')

        then: 'he gets access to the car listing page'
        at CarEditPage

        when:
        updateCar 'Ford', 'Fusion'

        then: 'he gets redirected to the car listing page'
        at CarShowPage

        and:
        createdBy() == 'sherlock'
        lastUpdatedBy() == 'watson'
        make() == 'Ford'
        model() == 'Fusion'

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
