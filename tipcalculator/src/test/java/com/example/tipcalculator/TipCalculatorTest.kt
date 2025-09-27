package com.example.tipcalculator

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.NumberFormat

class TipCalculatorTest {

    @Test
    fun calculateTip_20percentNoRoundup_returnsCorrectTip() {
        val amount = 10.00
        val tipPercent = 20.00
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = calculateTipAmount(amount, tipPercent, isRounded = false)
        assertEquals(actualTip, expectedTip)
    }

    @Test
    fun calculateTip_10percentWithRoundup_returnsCorrectTip() {
        val amount = 11.00
        val tipPercent = 10.00
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = calculateTipAmount(amount, tipPercent, isRounded = true)
        assertEquals(expectedTip, actualTip)
    }
}
