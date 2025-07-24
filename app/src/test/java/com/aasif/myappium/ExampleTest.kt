package com.aasif.myappium

import io.appium.java_client.AppiumBy
import org.junit.Assert
import org.junit.Test
import org.openqa.selenium.interactions.Pause // Added import for Pause
import org.openqa.selenium.interactions.PointerInput
import org.openqa.selenium.interactions.Sequence // Added import for Sequence
import java.time.Duration

class ExampleTest : TestBase() {

    @Test
    fun scrollAndTap() {
        // Find and click the element using AppiumBy for modern Appium versions
        val el = driver.findElement(AppiumBy.accessibilityId("LoginButton"))
        el.click()
        // Perform the scroll/swipe action using W3C Actions
        // This translates the original TouchAction sequence:
        // 1. Press at (500, 1500)
        // 2. Wait for 800ms (while pressed)
        // 3. Move to (500, 500) (while pressed)
        // 4. Release
        val finger = PointerInput(PointerInput.Kind.TOUCH, "finger")
        val scrollSequence = Sequence(finger, 0)
        // 1. Move pointer to start position (500, 1500) and press down
        scrollSequence.addAction(
            finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                500,
                1500
            )
        )
        scrollSequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
        // 2. Wait for 800ms (while finger is still pressed)
        // Corrected to use Pause constructor
        scrollSequence.addAction(Pause(finger, Duration.ofMillis(800)))
        // 3. Move pointer to end position (500, 500) (e.g., move action takes 200ms)
        scrollSequence.addAction(
            finger.createPointerMove(
                Duration.ofMillis(200),
                PointerInput.Origin.viewport(),
                500,
                500
            )
        )
        // 4. Release the pointer
        scrollSequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))

        driver.perform(listOf(scrollSequence))
    }

    @Test
    fun performAddition() {
        // Ensure driver is initialized (from TestBase @Before)
        val twoButton = driver.findElement(AppiumBy.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_02"))
        val plusButton = driver.findElement(AppiumBy.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_add"))
        val threeButton = driver.findElement(AppiumBy.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_03"))
        val equalsButton = driver.findElement(AppiumBy.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_equal"))
        // The result display ID can vary. This is a common one.
        // It might also be 'txt_calc_result' or similar. Use Appium Inspector to confirm.
        val resultDisplay = driver.findElement(AppiumBy.id("com.sec.android.app.popupcalculator:id/calc_edt_formula"))

        twoButton.click()
        plusButton.click()
        threeButton.click()
        equalsButton.click()

        // The result might have leading/trailing spaces or other characters.
        // For Samsung calculator, the result in 'calc_edt_formula' often shows just the number.
        // If it shows "5.0" or similar, you might need to adjust the assertion.
        val resultText = resultDisplay.text
        Assert.assertEquals("5 Calculation result", resultText)
    }
}
