package com.example.top_github.ui.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.top_github.R
import com.example.top_github.data.adapter.RepoListAdapter
import com.example.top_github.data.model.TrendingRepos
import com.example.top_github.data.response.Response
import com.example.top_github.imageCaching.core.ImageManager
import com.example.top_github.utils.AppUtils
import com.example.top_github.utils.NavigationUtils
import com.example.top_github.viewModels.RepoListViewModel
import com.example.top_github.viewModels.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_list_repo.*
import javax.inject.Inject

class RepoListActivity : DaggerAppCompatActivity(), RepoListAdapter.OnItemClickListener {

    private var repoList = listOf<TrendingRepos>()
    private lateinit var repoListAdapter: RepoListAdapter
    private lateinit var repoListViewModel: RepoListViewModel
    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var imageManager: ImageManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_repo)

        repoListAdapter = RepoListAdapter(repoList, this, imageManager)
        rvResults.layoutManager = LinearLayoutManager(this)
        rvResults.adapter = repoListAdapter

        repoListViewModel = ViewModelProviders.of(this, viewModelFactory).get(RepoListViewModel::class.java)

        setTextChangedListenerOnSearchInput()
        setTouchListenerOnSearchInput()
        observeRepoListData()
    }


    /**
     * Observing repo data stored in live data to update recycler view whenever data changes
     * This will ensure data integrity on config changes as well because live data is lifecycle aware
     */
    private fun observeRepoListData() {
        repoListViewModel.repoListLiveData.observe(this,
            Observer<List<TrendingRepos>> { t ->
                rvResults.visibility = View.VISIBLE
                repoListAdapter.setItems(t)
                repoListAdapter.notifyDataSetChanged()
            })
    }


    /**
     * Used to remove empty text error from edit text field whenever user types again
     */
    private fun setTextChangedListenerOnSearchInput() {
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (searchInput.text.toString().isNotEmpty()) {
                    searchLayout.isErrorEnabled = false
                }
            }
        })
    }


    /**
     * Will hit search api when search icon is clicked inside edit text field
     */
    private fun setTouchListenerOnSearchInput() {
        searchInput.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (searchInput.right - searchInput.compoundDrawables[2].bounds.width())) {
                        if (searchInput.text.toString().isEmpty()) {
                            searchLayout.isErrorEnabled = true
                            searchLayout.error = R.string.empty_text_error.toString()
                        } else {
                            setProgressVisible()
                            hitSearchApi(searchInput.text.toString(), hideKeyboard = true, clearFocus = true)
                        }
                        return true
                    }
                }
                return false
            }
        })
    }


    /**
     * Responsible for calling github api using injected view model to fetch repositories data
     */
    private fun hitSearchApi(
        language: String,
        hideKeyboard: Boolean,
        clearFocus: Boolean
    ) {
        repoListViewModel.fetchTrendingRepos(language).observe(this,
            Observer<Response> { t ->
                handleKeyboardVisibility(hideKeyboard)
                setProgressInvisible()
                if (t != null) {
                    if(t.getRepos()!=null && (t.getRepos() as List<TrendingRepos>).isNotEmpty()){
                        handleEditTextFocus(clearFocus)
                        repoListViewModel.setRepoListLiveData(t.getRepos() as List<TrendingRepos>)
                    }
                    else if(t.getError()!=null)
                        Toast.makeText(this, (t.getError() as Throwable).message, Toast.LENGTH_LONG).show()
                    else
                        Toast.makeText(this, R.string.api_fail_error_text, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, R.string.api_fail_error_text, Toast.LENGTH_LONG).show()
                }
            }
        )
    }


    /**
     * decides whether to clear focus from edit text
     */
    private fun handleEditTextFocus(clearFocus: Boolean) {
        if (clearFocus) {
            searchLayout.clearFocus()
        }
    }


    /**
     * decides whether to hide keyboard when response comes from api
     */
    private fun handleKeyboardVisibility(hideKeyboard: Boolean) {
        if (hideKeyboard) {
            AppUtils.hideKeyboard(this)
            searchLayout.clearFocus()
        }
    }


    /**
     * sets progress bar to visible
     */
    private fun setProgressVisible() {
        loading_indicator.visibility = View.VISIBLE
    }


    /**
     * hides progress bar usually when response comes from api
     */
    private fun setProgressInvisible() {
        loading_indicator.visibility = View.GONE
    }


    /**
     * navigates to details screen when repository item is clicked inside recycler view
     */
    override fun onResultItemClick(repoDetails: TrendingRepos, imageView: ImageView, titleView: TextView, descView: TextView) {
        NavigationUtils.navigateToDetailsScreen(this, repoDetails, imageView, titleView, descView)
    }
}