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

class TestRefactor {
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun TestRefactor()
    {
        onView(withText("Test-2")).perform(longClick())
        onView(withText("Изменить")).perform(click())
        onView(withId(R.id.answ3)).perform(click())
        onView(withId(R.id.answ3)).perform(typeText("100"))
        onView(withId(R.id.answ3)).perform(closeSoftKeyboard())
        onView(withId(R.id.addNewTest)).perform(click())
    }
}