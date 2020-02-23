package com.davidbragadeveloper.prognet.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.davidbragadeveloper.prognet.R
import com.davidbragadeveloper.prognet.data.remote.DiscogsDb
import com.davidbragadeveloper.prognet.ui.main.MainActivity
import com.davidbragadeveloper.prognet.utils.fromJson
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.internal.waitMillis
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get
import java.lang.Thread.sleep


class UiTest : KoinTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val activityTestRule =
        ActivityTestRule(MainActivity::class.java, false, false)


    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson(
                ApplicationProvider.getApplicationContext(),
                "discoverAlbums.json"
            )

        )

        val resource = OkHttp3IdlingResource.create("OkHttp", get<DiscogsDb>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun clickAMovieNavigatesToDetail() {
        activityTestRule.launchActivity(null)

        sleep(500)

        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )

        sleep(300)

        onView(withId(R.id.titleTextView))
            .check(matches(withText(containsString("Flock* - Inside Out"))))

    }
}

