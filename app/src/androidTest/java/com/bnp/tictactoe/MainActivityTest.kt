package com.bnp.tictactoe

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun itemNotClickableIfAlreadySelected() {
        Espresso.onView(ViewMatchers.withContentDescription("Cell0"))
            .perform(ViewActions.click())
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isClickable())))
    }

}