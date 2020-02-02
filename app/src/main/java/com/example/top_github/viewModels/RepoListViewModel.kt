package com.example.top_github.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.top_github.data.model.TrendingRepos
import com.example.top_github.data.remoteRepo.TrendingReposRepo
import javax.inject.Inject

class RepoListViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var trendingReposRepo: TrendingReposRepo
    var repoListLiveData = MutableLiveData<List<TrendingRepos>>()

    fun setRepoListLiveData(repoList: List<TrendingRepos>){
        repoListLiveData.postValue(repoList)
    }

    fun fetchTrendingRepos(language: String): MutableLiveData<List<TrendingRepos>>{
        return trendingReposRepo.fetchTrendingRepos(language)
    }
}