package com.example.top_github.data.remoteRepo

import androidx.lifecycle.MutableLiveData
import com.example.top_github.data.model.TrendingRepos
import com.example.top_github.data.service.TrendingReposService
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TrendingReposRepo(private val trendingReposService: TrendingReposService) {

    fun fetchTrendingRepos(language: String): MutableLiveData<List<TrendingRepos>>{
        val reposLiveData = MutableLiveData<List<TrendingRepos>>()
        trendingReposService.getTrendingRepos(language, "weekly")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<List<TrendingRepos>>{
                override fun onSuccess(t: List<TrendingRepos>) {
                    reposLiveData.postValue(t)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                }
            })
        return reposLiveData
    }
}