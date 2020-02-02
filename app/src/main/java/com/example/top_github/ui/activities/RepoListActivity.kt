package com.example.top_github.ui.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.top_github.R
import com.example.top_github.data.model.TrendingRepos
import com.example.top_github.viewModels.RepoListViewModel
import com.example.top_github.viewModels.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_list_repo.*
import javax.inject.Inject

class RepoListActivity : DaggerAppCompatActivity() {

    lateinit var repoListViewModel: RepoListViewModel
    @Inject lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_repo)

        repoListViewModel = ViewModelProviders.of(this, viewModelFactory).get(RepoListViewModel::class.java)
        repoListViewModel.fetchTrendingRepos("java").observe(this, object: Observer<List<TrendingRepos>>{
            override fun onChanged(t: List<TrendingRepos>?) {
                if(t != null)
                    TODO()
            }
        })
    }
}
