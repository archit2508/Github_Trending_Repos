package com.example.top_github.activities

import android.os.Bundle
import com.example.top_github.R
import dagger.android.support.DaggerAppCompatActivity

class RepoListActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_repo)
    }
}
