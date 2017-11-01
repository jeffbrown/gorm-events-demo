package gorm.events.demo

import gorm.events.demo.audit.Audited

class Car implements Audited {
    String make
    String model
}
