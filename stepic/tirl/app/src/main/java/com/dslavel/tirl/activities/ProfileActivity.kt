package com.dslavel.tirl.activities



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import com.dslavel.tirl.R
import com.dslavel.tirl.models.User
import com.dslavel.tirl.utils.FirebaseHelper
import com.dslavel.tirl.utils.GlideApp
import com.dslavel.tirl.utils.ValueEventListenerAdapter
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity(4) {
    private val TAG = "ProfileActivity"
    private lateinit var mFirebase: FirebaseHelper
    private lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupBottomNavigation()
        Log.d(TAG, "onCreate")

        edit_profile_btn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        mFirebase = FirebaseHelper(this)
        mFirebase.currentUserReference().addValueEventListener(ValueEventListenerAdapter {
            mUser = it.getValue(User::class.java)!!
            profile_image.loadUserPhoto(mUser.photo)
            username_text.text = mUser.username
        })

        image_recycler.layoutManager = GridLayoutManager(this, 3)
        mFirebase.database.child("images").child(mFirebase.auth.currentUser!!.uid)
            .addValueEventListener(ValueEventListenerAdapter {
                val images = it.children.map { it.getValue(String::class.java)!! }
                image_recycler.adapter = ImagesAdapter(images + images + images + images)
            })
    }
}

class ImagesAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    class ViewHolder(val image: ImageView) : RecyclerView.ViewHolder(image)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val image = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false) as ImageView
        return ViewHolder(image)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.loadImage(images[position])
    }

    private fun ImageView.loadImage(image: String) {
        GlideApp.with(this).load(image).centerCrop().into(this)
    }

    override fun getItemCount(): Int = images.size
}

class SquareImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}