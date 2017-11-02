package gorm.events.demo.listener

import gorm.events.demo.audit.Audited
import grails.events.annotation.gorm.Listener
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.datastore.mapping.engine.event.ValidationEvent

@Slf4j
@CompileStatic
class AuditListener {

    @Listener(Audited)
    void validateEvent(ValidationEvent event) {
        Audited entity = (Audited)event.entityObject
        if(!entity.createdBy) {
            log.debug "Setting createdBy for instance [${entity}]"
            // Could get user info from security context, etc...
            entity.createdBy = 'some-user-id'
        }
    }
}
