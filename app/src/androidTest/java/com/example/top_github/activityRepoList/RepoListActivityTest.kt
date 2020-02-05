package com.example.top_github.activityRepoList

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.top_github.ui.activities.RepoListActivity
import org.junit.runner.RunWith
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.top_github.R
import com.example.top_github.data.adapter.RepoListAdapter
import com.example.top_github.data.remoteRepo.EspressoTestingIdlingResource
import org.junit.*
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.example.top_github.ui.activities.RepoDetailsActivity

@RunWith(AndroidJUnit4::class)
class RepoListActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<RepoListActivity> = ActivityTestRule(RepoListActivity::class.java)

    @Before
    fun setUpTest() {
        //to let espresso wait for api response to populate recycler view
        IdlingRegistry.getInstance().register(EspressoTestingIdlingResource.idlingResource)
        //to let espresso intent to another activity if required
        Intents.init()
    }

    @After
    fun tearDownTest() {
        IdlingRegistry.getInstance().unregister(EspressoTestingIdlingResource.idlingResource)
        Intents.release()
    }

    /**
     * Verifies if edit text input for language input is available or not
     */
    @Test
    fun checkIfLanguageInputIsVisible() {
        onView(withId(R.id.searchInput)).check(matches(isDisplayed()))
    }

    /**
     * Verifies if loader gets hidden when api response comes
     */
    @Test
    fun checkIfLoaderGoneWhenResponseComes() {
        onView(withId(R.id.searchInput)).check(matches(isDisplayed())).
            perform(ViewActions.typeText("python")).
            perform(ClickDrawableAction(ClickDrawableAction.Right))
        /* Here espresso will wait for async api call to complete */
        onView(withId(R.id.loading_indicator)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    /**
     * Verifies if Repo details screen opens when item inside recycler view is clicked
     */
    @Test
    fun checkDetailsScreenOnItemClick() {
        onView(withId(R.id.searchInput)).check(matches(isDisplayed())).
            perform(ViewActions.typeText("python")).
            perform(ClickDrawableAction(ClickDrawableAction.Right))
        onView(withId(R.id.rvResults))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RepoListAdapter.RepoItemViewHolder>(0, click()))
        intended(hasComponent(RepoDetailsActivity::class.java.name))
    }
}