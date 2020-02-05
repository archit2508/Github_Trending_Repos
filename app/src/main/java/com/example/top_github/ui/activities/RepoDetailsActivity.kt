package com.example.top_github.ui.activities

import android.os.Build
import android.os.Bundle
import com.example.top_github.R
import com.example.top_github.data.model.TrendingRepos
import com.example.top_github.imageCaching.core.ImageManager
import com.example.top_github.utils.AppConstants
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_repo_details.*
import javax.inject.Inject

/**
 * Will be instantiated to show more details whenever a repository item is clicked in RepoListActivity
 */
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
        if(Build.VERSION.SDK_INT>=21)
            finishAfterTransition()
        else
            finish()
    }
}
