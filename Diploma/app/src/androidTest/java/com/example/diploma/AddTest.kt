import android.view.Gravity
import org.junit.runner.RunWith
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.example.diploma.R
import com.example.diploma.core.activity.MainActivity
import org.junit.Rule

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest

class AddTest {
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun AddTest()
    {
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.etTestName)).perform(click())
        onView(withId(R.id.etTestName)).perform(typeText("Test-2"))
        onView(withId(R.id.questionText)).perform(closeSoftKeyboard())
        onView(withId(R.id.etTestTheme)).perform(click())
        onView(withId(R.id.etTestTheme)).perform(typeText("Math"))
        onView(withId(R.id.etTestTheme)).perform(closeSoftKeyboard())
        onView(withId(R.id.questionText)).perform(click())
        onView(withId(R.id.questionText)).perform(typeText("5+5"))
        onView(withId(R.id.questionText)).perform(closeSoftKeyboard())
        onView(withId(R.id.answ1)).perform(click())
        onView(withId(R.id.answ1)).perform(typeText("15"))
        onView(withId(R.id.answ1)).perform(closeSoftKeyboard())
        onView(withId(R.id.answ2)).perform(click())
        onView(withId(R.id.answ2)).perform(typeText("10"))
        onView(withId(R.id.answ2)).perform(closeSoftKeyboard())
        onView(withId(R.id.answ3)).perform(click())
        onView(withId(R.id.answ3)).perform(typeText("12"))
        onView(withId(R.id.answ3)).perform(closeSoftKeyboard())
        onView(withId(R.id.answ4)).perform(click())
        onView(withId(R.id.answ4)).perform(typeText("1"))
        onView(withId(R.id.answ4)).perform(closeSoftKeyboard())
        onView(withId(R.id.npicker)).perform((swipeLeft()))
        onView(withId(R.id.addNewTest)).perform(click())



    }
}