package com.example.tipcalculator

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

class TipCalculatorUiTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun essentialComponents_visibleOnStartup() {
        composeTestRule.setContent { TipApp() }

        // 1. Assert the Bill Input field is visible
        composeTestRule
            .onNodeWithStringId(R.string.bill_amount)
            .assertIsDisplayed()

        // 2. Assert the Tip Percentage input field is visible
        composeTestRule
            .onNodeWithStringId(R.string.calculate_tip)
            .assertIsDisplayed()

        // 3. Assert the Round Up switch is displayed
        composeTestRule
            .onNodeWithTagID(R.string.round_up_tip)
            .assertIsDisplayed()

        // 3. Assert the Tip Amount text is visible
        val tip = NumberFormat.getCurrencyInstance().format(0)
        composeTestRule
            .onNodeWithStringId(R.string.tip_amount, tip)
            .assertIsDisplayed()
        }


}