package com.aasif.myappium

import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.Before
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

open class TestBase {
    object ProjectCapabilities {

        fun create(): DesiredCapabilities {
            return DesiredCapabilities().apply {
                setCapability("appium:deviceName", "Saurabh's A70s")
                setCapability("appium:udid", "RZ8M93EFYLV")
//                setCapability("appium:udid", "emulator-5554")
                setCapability("platformName", "Android")
                setCapability("appium:platformVersion", "11")
                setCapability("appium:automationName", "UiAutomator2")
//                setCapability("appium:appPackage", "com.sec.android.app.popupcalculator")
//                setCapability(
//                    "appium:appActivity",
//                    "com.sec.android.app.popupcalculator.Calculator"
//                )
                setCapability("appium:appPackage", "com.iifl.livlong.debug")
                setCapability(
                    "appium:appActivity",
                    "com.iifl.livlong.ui.MainActivity"
                )
            }
        }
    }

    lateinit var driver: AndroidDriver
    private val caps = ProjectCapabilities.create()

    @Before
    fun setup() {
        val url = URL("http://127.0.0.1:4723/")
        driver = AndroidDriver(url, caps)
    }

    @After
    fun teardown() {
        driver.quit()
    }
}
