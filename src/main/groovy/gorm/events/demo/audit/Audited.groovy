package gorm.events.demo.audit

import groovy.transform.SelfType
import org.grails.datastore.gorm.GormEntity

@SelfType(GormEntity)
trait Audited {
    Date dateCreated
    Date lastUpdated
    String createdBy
}
