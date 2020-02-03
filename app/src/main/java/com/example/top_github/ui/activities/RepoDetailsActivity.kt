package com.example.top_github.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.top_github.R
import com.example.top_github.data.model.TrendingRepos
import com.example.top_github.utils.AppConstants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_repo_details.*
import kotlinx.android.synthetic.main.result_item.view.*

class RepoDetailsActivity : AppCompatActivity() {

    private var repoDetails: TrendingRepos? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)

        repoDetails = intent.extras?.get(AppConstants.INTENT_DATA) as TrendingRepos
        initializeViews()
    }

    private fun initializeViews() {
        if(repoDetails != null){
            if (repoDetails?.avatar != null)
                Picasso.get().load(repoDetails?.avatar).into(item_profile_img)
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
