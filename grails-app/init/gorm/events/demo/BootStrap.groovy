package gorm.events.demo

import gorm.events.demo.auth.Role
import gorm.events.demo.auth.User
import gorm.events.demo.auth.UserRole

class BootStrap {

    def init = { servletContext ->
        def userRole = new Role(authority: 'ROLE_USER').save()

        def geddyUser = new User(username: 'geddy', password: 'password').save()
        UserRole.create geddyUser, userRole
        def neilUser = new User(username: 'neil', password: 'password').save()
        UserRole.create neilUser, userRole
        def alexUser = new User(username: 'alex', password: 'password').save()
        UserRole.create alexUser, userRole
    }

    def destroy = {
    }
}
