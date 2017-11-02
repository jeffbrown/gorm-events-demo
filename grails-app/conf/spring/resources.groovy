import gorm.events.demo.auth.UserPasswordEncoderListener
import gorm.events.demo.listener.AuditListener

beans = {
    userPasswordEncoderListener UserPasswordEncoderListener
    customAuditListener AuditListener
}
