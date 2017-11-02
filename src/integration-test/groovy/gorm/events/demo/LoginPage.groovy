package gorm.events.demo

import geb.Page

class LoginPage extends Page {
    static url = '/login/auth'

    static at = {
        title == 'Login'
    }

    static content = {
        loginButton { $('#submit', 0) }
        usernameInputField { $('#username', 0) }
        passwordInputField { $('#password', 0) }
        logoutLink { $('#logout', 0) }
    }

    void logout() {
        logoutLink.click()
    }

    void login(String username, String password) {
        usernameInputField << username
        passwordInputField << password
        loginButton.click()
    }
}