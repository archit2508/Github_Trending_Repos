package com.example.top_github.activities

import android.os.Bundle
import com.example.top_github.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_list_repo.*
import javax.inject.Inject

class RepoListActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var disp: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_repo)

        display.text = disp
    }
}
