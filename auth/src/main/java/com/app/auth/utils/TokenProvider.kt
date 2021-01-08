package com.app.auth.utils


import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import okhttp3.Interceptor
import okhttp3.Response

class TokenProvider : Interceptor {

    private val FIREBASE_ID_TOKEN = "authorization"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {

            var task: Task<GetTokenResult> = user.getIdToken(true)
            var idToken = Tasks.await(task).token
            if (idToken != null) {
                val modifiedRequest = request.newBuilder()
                    .addHeader(FIREBASE_ID_TOKEN, idToken)
                    .build()
                return chain.proceed(modifiedRequest)
            }
        }
        return chain.proceed(request)
    }
}