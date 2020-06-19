package com.example.diploma.core.activity

import org.junit.runner.RunWith
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.diploma.R
import org.junit.Rule


@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class LoginActivityTest{
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity:: class.java)
    @Test
    fun TestLoginStudent ()
    {
        onView(withId(R.id.tvLog)).perform(click())
        onView(withId(R.id.tvLog)).perform(typeText("123"))
        onView(withId(R.id.tvLog)).perform(closeSoftKeyboard())
        onView(withId(R.id.tvPass)).perform(click())
        onView(withId(R.id.tvPass)).perform(typeText("123"))
        onView(withId(R.id.tvPass)).perform(closeSoftKeyboard())
        onView(withId(R.id.loginBut)).perform(click())
}
}