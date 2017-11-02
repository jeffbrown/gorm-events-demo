package gorm.events.demo.listener

import gorm.events.demo.audit.Audited
import gorm.events.demo.auth.User
import grails.events.annotation.gorm.Listener
import grails.plugin.springsecurity.SpringSecurityService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.datastore.mapping.engine.event.ValidationEvent
import org.springframework.beans.factory.annotation.Autowired

@Slf4j
@CompileStatic
class AuditListener {

    @Autowired
    SpringSecurityService springSecurityService

    @Listener(Audited)
    void validateEvent(ValidationEvent event) {
        Audited entity = (Audited)event.entityObject
        if(!entity.createdBy) {
            log.debug "Setting createdBy for instance [${entity}]"
            entity.createdBy = ((User)springSecurityService.currentUser)?.username
        }
    }
}
