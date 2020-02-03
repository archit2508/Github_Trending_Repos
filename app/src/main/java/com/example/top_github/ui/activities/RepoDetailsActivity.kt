package com.example.top_github.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.top_github.R
import com.example.top_github.data.model.TrendingRepos
import com.example.top_github.imageCaching.core.ImageManager
import com.example.top_github.utils.AppConstants
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_repo_details.*
import kotlinx.android.synthetic.main.result_item.view.*
import javax.inject.Inject

class RepoDetailsActivity : DaggerAppCompatActivity() {

    private var repoDetails: TrendingRepos? = null
    @Inject lateinit var imageManager: ImageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)

        repoDetails = intent.extras?.get(AppConstants.INTENT_DATA) as TrendingRepos
        initializeViews()
    }

    private fun initializeViews() {
        if(repoDetails != null){
            repoDetails?.avatar.let {
                imageManager.displayImage(repoDetails?.avatar!!, item_profile_img, R.drawable.ic_adb_black_24dp)
            }
            item_title.text = repoDetails?.repo?.name
            item_username.text = repoDetails?.name + " @" + repoDetails?.username
            item_username_url.text = repoDetails?.url
            item_desc.text = repoDetails?.repo?.description
            item_desc_url.text = repoDetails?.repo?.url
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
    }
}
