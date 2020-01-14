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
        clickOnCellWithContentDescription("Cell0")
            .check(matches(not(isClickable())))
    }

    @Test
    fun checkedCellClickableAfterReset() {
        //Click on Cell 0
        clickOnCellWithContentDescription("Cell0")

        //Click on reset menu option
        onView(withId(R.id.action_reset))
            .perform(click())

        //Check that Cell 0 is clickable again
        onView(withContentDescription("Cell0"))
            .check(matches(isClickable()))
    }

    @Test
    fun showWinnerResultDialogAfterGameOver() {
        val expectedDialogContent = "RESULT: PLAYER 1 WINS"
        val xWinnerArray = intArrayOf(0,3,1,5,2)
        xWinnerArray.runGame()

        onView(withText(expectedDialogContent))
            .check(matches(isDisplayed()))
    }

    @Test
    fun showTieResultDialogAfterGameOver() {
        val expectedDialogContent = "RESULT: TIE"
        val tieArray = intArrayOf(0,1,2,3,4,6,7,8,5)
        tieArray.runGame()

        onView(withText(expectedDialogContent))
            .check(matches(isDisplayed()))
    }

    private fun IntArray.runGame() {
        forEach { id -> clickOnCellWithContentDescription("Cell$id") }
    }

    private fun clickOnCellWithContentDescription(contentDescription: String) =
        onView(withContentDescription(contentDescription))
            .perform(click())

}