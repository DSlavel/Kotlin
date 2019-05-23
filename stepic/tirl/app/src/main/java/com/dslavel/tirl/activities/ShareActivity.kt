package com.dslavel.tirl.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.dslavel.tirl.R
import com.dslavel.tirl.utils.CameraHelper
import com.dslavel.tirl.utils.FirebaseHelper
import com.dslavel.tirl.utils.GlideApp
import kotlinx.android.synthetic.main.activity_share.*


class ShareActivity : BaseActivity(2) {
    private val TAG = "ShareActivity"
    private lateinit var mCamera: CameraHelper
    private lateinit var mFirebase: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        //setupBottomNavigation()
        Log.d(TAG, "onCreate")

        mFirebase = FirebaseHelper(this)
        mCamera = CameraHelper(this)
        mCamera.takeCameraPicture()

        back_image.setOnClickListener { finish() }
        share_text.setOnClickListener { share() }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == mCamera.REQUEST_CODE){
            if ( resultCode == Activity.RESULT_OK) {
                GlideApp.with(this).load(mCamera.imageUri).centerCrop().into(post_image)
            } else {
                finish()
            }
            }
        }

    private fun share() {
        val imageUri = mCamera.imageUri
        if (imageUri != null) {
            val uid = mFirebase.auth.currentUser!!.uid
            mFirebase.storage.child("users").child(uid).child("images")
                .child(imageUri.lastPathSegment).putFile(imageUri).addOnCompleteListener{
                    if (it.isSuccessful) {
                        mFirebase.database.child("images").child(uid).push()
                            .setValue(it.result.downloadUrl!!.toString())
                            .addOnCompleteListener{
                                if (it.isSuccessful) {
                                    startActivity(Intent(this,
                                        ProfileActivity::class.java))
                                    finish()
                                } else {
                                    showToast(it.exception!!.message!!)
                                }
                            }
                    } else {
                        showToast(it.exception!!.message!!)
                    }
                }
        }
    }
}