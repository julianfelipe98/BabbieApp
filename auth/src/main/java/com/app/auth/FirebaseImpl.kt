package com.app.auth

import com.app.base.FirebaseHandler
import com.google.firebase.auth.FirebaseAuth

class FirebaseImpl : FirebaseHandler {

    override fun logOut(): Boolean {
        FirebaseAuth.getInstance().signOut()
        return (FirebaseAuth.getInstance().currentUser == null)
    }
}