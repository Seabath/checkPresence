package com.seabath.axu614.checkPresence.handler

import com.firebase.client.AuthData
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError

class CustomAuthResultHandler() : Firebase.AuthResultHandler {

    override fun onAuthenticated(p0: AuthData?) {
    }

    override fun onAuthenticationError(p0: FirebaseError?) {
    }

}
