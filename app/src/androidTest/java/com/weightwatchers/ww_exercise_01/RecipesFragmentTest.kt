package com.weightwatchers.ww_exercise_01

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.weightwatchers.ww_exercise_01.ui.recipes.RecipesFragment
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class RecipesFragmentTest {
    @get:Rule
    var activityRule: ActivityTestRule<FragmentScenario.EmptyFragmentActivity> =
            ActivityTestRule(FragmentScenario.EmptyFragmentActivity::class.java)

    @Test
    fun initialState() {
        val mockNavController = mockk<NavController>()
        launchFragment(mockNavController)
        Espresso.onView(
                withId(R.id.rvRecipes)
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.pbLoading)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rvRecipes)).check(RecyclerViewItemCountAssertion(0)) //no items initially
    }

    private fun launchFragment(navController: NavController?) {
        val bundle = Bundle()
        val scenario = launchFragmentInContainer<RecipesFragment>(bundle, R.style.AppTheme)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }

}