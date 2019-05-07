package com.dslavel.tirl.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.dslavel.tirl.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(0) {
    private val TAG = "HomeActivity"
    private lateinit var nAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Log.d(TAG, "onCreate")
        setupBottomNavigation()


        nAuth = FirebaseAuth.getInstance()
        //nAuth.signOut()
        /*
       auth.signInWithEmailAndPassword("dslavel@dslavel.com","password")
           .addOnCompleteListener(){
               if (it.isSuccessful){
                   Log.d(TAG, "signIn: succes")
               } else {
                   Log.e(TAG, "signIn: falure", it.exception )
               }
           }*/
        sign_out_text.setOnClickListener {
            nAuth.signOut()
        }
        nAuth.addAuthStateListener {
            if (it.currentUser == null){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (nAuth.currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


}