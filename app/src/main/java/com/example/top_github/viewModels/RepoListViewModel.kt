package com.example.top_github.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.top_github.data.model.TrendingRepos
import com.example.top_github.data.remoteRepo.TrendingReposRepo
import com.example.top_github.data.response.Response
import javax.inject.Inject

/**
 * Used by Repo list screen to fetch and store repo list data into live data
 */
class RepoListViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var trendingReposRepo: TrendingReposRepo
    var repoListLiveData = MutableLiveData<List<TrendingRepos>>()

    fun setRepoListLiveData(repoList: List<TrendingRepos>){
        repoListLiveData.postValue(repoList)
    }

    fun fetchTrendingRepos(language: String): MutableLiveData<Response>{
        return trendingReposRepo.fetchTrendingRepos(language)
    }
}