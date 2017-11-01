package gorm.events.demo.listener

import gorm.events.demo.audit.Audited
import grails.events.annotation.gorm.Listener
import groovy.util.logging.Slf4j
import org.grails.datastore.mapping.engine.event.ValidationEvent

@Slf4j
class AuditListener {

    @Listener(Audited)
    void validateEvent(ValidationEvent event) {
        log.debug "Validating [${event.entityObject}]"
        if(!event.entityObject.attached) {
            // Could get user info from security context, etc...
            event.entityObject.createdBy = 'some-user-id'
        }
    }
}
