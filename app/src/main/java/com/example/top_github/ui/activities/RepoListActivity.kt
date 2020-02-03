package com.example.top_github.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.top_github.R
import com.example.top_github.data.adapter.RepoListAdapter
import com.example.top_github.data.model.TrendingRepos
import com.example.top_github.utils.AppConstants
import com.example.top_github.viewModels.RepoListViewModel
import com.example.top_github.viewModels.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_list_repo.*
import javax.inject.Inject


class RepoListActivity : DaggerAppCompatActivity(), RepoListAdapter.OnItemClickListener {

    var repoList = listOf<TrendingRepos>()
    lateinit var repoListAdapter: RepoListAdapter
    lateinit var repoListViewModel: RepoListViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_repo)

        repoListAdapter = RepoListAdapter(repoList, this)
        rvResults.layoutManager = LinearLayoutManager(this)
        rvResults.adapter = repoListAdapter

        repoListViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(RepoListViewModel::class.java)

        setTextChangedListenerOnSearchInput()
        setTouchListenerOnSearchInput()
        observeRepoListData()
    }


    private fun observeRepoListData() {
        repoListViewModel.repoListLiveData.observe(this,
            Observer<List<TrendingRepos>> { t ->
                repoListAdapter.setItems(t)
                repoListAdapter.notifyDataSetChanged()
            })
    }


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
     * Will hit search api when search icon is clicked inside edit text
     */
    private fun setTouchListenerOnSearchInput() {
        searchInput.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (searchInput.right - searchInput.compoundDrawables[2].bounds.width())) {
                        if (searchInput.text.toString().isEmpty()) {
                            searchLayout.isErrorEnabled = true
                            searchLayout.error = "Search input cannot be empty"
                        } else {
                            setProgressVisible()
                            hitSearchApi(
                                searchInput.text.toString(),
                                hideKeyboard = true,
                                clearFocus = true
                            )
                        }
                        return true
                    }
                }
                return false
            }
        })
    }


    private fun hitSearchApi(
        language: String,
        hideKeyboard: Boolean,
        clearFocus: Boolean
    ) {
        repoListViewModel.fetchTrendingRepos(language).observe(this,
            Observer<List<TrendingRepos>> { t ->
                handleKeyboardVisibility(hideKeyboard)
                setProgressInvisible()
                if (t != null) {
                    handleEditTextFocus(clearFocus)
                    repoListViewModel.setRepoListLiveData(t)
                    repoListAdapter.setItems(t)
                    repoListAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this,
                        "Unable to get search results OR No results found",
                        Toast.LENGTH_LONG
                    ).show()
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
            hideSoftKeyboard(this)
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
     * hides keyboard
     */
    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (activity.currentFocus != null)
            inputMethodManager!!.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )
    }

    override fun onResultItemClick(repoDetails: TrendingRepos, imageView: ImageView, titleView: TextView, descView: TextView) {
        val imageViewPair = Pair.create<View, String>(imageView, this.getString(R.string.transition_image))
        val titleViewPair = Pair.create<View, String>(titleView, this.getString(R.string.transition_title))
        val descViewPair = Pair.create<View, String>(descView, this.getString(R.string.transition_desc))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageViewPair,
            titleViewPair,
            descViewPair)
        val intent = Intent(this, RepoDetailsActivity::class.java)
        intent.putExtra(AppConstants.INTENT_DATA, repoDetails)
        ActivityCompat.startActivity(this, intent, options.toBundle())
    }
}