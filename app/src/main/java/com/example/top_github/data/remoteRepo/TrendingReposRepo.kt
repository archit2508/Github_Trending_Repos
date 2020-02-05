package com.example.top_github.data.remoteRepo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.top_github.R
import com.example.top_github.data.model.TrendingRepos
import com.example.top_github.data.response.Response
import com.example.top_github.data.service.TrendingReposService
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Repository to fetch data from remote source
 */
class TrendingReposRepo(private val trendingReposService: TrendingReposService, private val context: Application) {

    fun fetchTrendingRepos(language: String): MutableLiveData<Response>{
        val reposLiveData = MutableLiveData<Response>()
        trendingReposService.getTrendingRepos(language, context.resources.getString(R.string.period))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { EspressoTestingIdlingResource.increment() }
            .doFinally { EspressoTestingIdlingResource.decrement() }
            .subscribe(object: SingleObserver<List<TrendingRepos>>{
                override fun onSuccess(t: List<TrendingRepos>) {
                    reposLiveData.postValue(Response(t))
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    reposLiveData.postValue(Response(e))
                }
            })
        return reposLiveData
    }
}