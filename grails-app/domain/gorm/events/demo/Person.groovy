package gorm.events.demo

import gorm.events.demo.audit.Audited

class Person implements Audited {
    String firstName
    String lastName
}
