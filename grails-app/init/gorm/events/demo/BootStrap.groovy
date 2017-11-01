package gorm.events.demo

class BootStrap {

    def init = { servletContext ->
        new Person(firstName: 'Geddy', lastName: 'Lee').save()
        new Person(firstName: 'Alex', lastName: 'Lifeson').save()
        new Person(firstName: 'Neil', lastName: 'Peart').save()

        new Car(make: 'Ford', model: 'Fusion').save()
        new Car(make: 'Chevy', model: 'Equinox').save()
    }
    def destroy = {
    }
}
