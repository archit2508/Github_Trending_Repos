package com.example.top_github.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.example.top_github.R
import com.example.top_github.data.model.TrendingRepos
import com.example.top_github.ui.activities.RepoDetailsActivity

/**
 * Utility class which navigation related methods
 */
class NavigationUtils {

    companion object{
        /**
         * navigates to repo detail screen using SHARED ELEMENT TRANSITION
         */
        fun navigateToDetailsScreen(context: Activity, repoDetails: TrendingRepos, imageView: ImageView, titleView: TextView, descView: TextView){
            val imageViewPair = Pair.create<View, String>(imageView, context.getString(R.string.transition_image))
            val titleViewPair = Pair.create<View, String>(titleView, context.getString(R.string.transition_title))
            val descViewPair = Pair.create<View, String>(descView, context.getString(R.string.transition_desc))
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context,
                imageViewPair,
                titleViewPair,
                descViewPair)
            val intent = Intent(context, RepoDetailsActivity::class.java)
            intent.putExtra(AppConstants.INTENT_DATA, repoDetails)
            ActivityCompat.startActivity(context, intent, options.toBundle())
        }
    }
}