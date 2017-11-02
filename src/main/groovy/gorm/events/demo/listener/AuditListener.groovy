package gorm.events.demo.listener

import gorm.events.demo.audit.Audited
import gorm.events.demo.auth.User
import grails.events.annotation.gorm.Listener
import grails.plugin.springsecurity.SpringSecurityService
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.datastore.mapping.engine.event.PreUpdateEvent
import org.grails.datastore.mapping.engine.event.ValidationEvent
import org.springframework.beans.factory.annotation.Autowired

@Slf4j
@CompileStatic
class AuditListener {

    @Autowired
    SpringSecurityService springSecurityService

    @Listener(Audited)
    void preUpdateEvent(PreUpdateEvent event) {
        log.debug "Setting lastUpdatedBy for instance [${event.entityObject}]"
        def username = loggedUsername()
        event.entityAccess.setProperty('lastUpdatedBy', username)
    }

    @Listener(Audited)
    void validateEvent(ValidationEvent event) {
        Audited entity = (Audited) event.entityObject
        if (!entity.createdBy) {
            log.debug "Setting createdBy and lastUpdatedBy for instance [${entity}]"
            def username = loggedUsername()
            event.entityAccess.setProperty('lastUpdatedBy', username)
            event.entityAccess.setProperty('createdBy', username)
        }
    }

    @CompileDynamic
    String loggedUsername() {
        springSecurityService.principal.username
    }
}
