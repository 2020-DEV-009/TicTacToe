package com.bnp.tictactoe

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun cellNotClickableIfAlreadySelected() {
        onView(withContentDescription("Cell0"))
            .perform(click())
            .check(matches(not(isClickable())))
    }

    @Test
    fun checkedCellClickableAfterReset() {

        //Click on Cell 0
        onView(withContentDescription("Cell0"))
            .perform(click())

        //Click on reset menu option
        onView(withId(R.id.action_reset))
            .perform(click())

        //Check that Cell 0 is clickable again
        onView(withContentDescription("Cell0"))
            .check(matches(isClickable()))
    }

}