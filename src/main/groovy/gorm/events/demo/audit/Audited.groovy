package gorm.events.demo.audit

trait Audited {
    Date dateCreated
    Date lastUpdated
    String createdBy
}
