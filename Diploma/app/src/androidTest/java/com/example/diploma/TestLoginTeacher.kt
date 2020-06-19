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
import com.example.diploma.core.activity.LoginActivity
import org.junit.Rule

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class TestLoginTeacher
{
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(LoginActivity:: class.java)
    @Test
    fun TestLoginTeacher ()
    {
        onView(withId(R.id.tvLog)).perform(click())
        onView(withId(R.id.tvLog)).perform(typeText("1234"))
        onView(withId(R.id.tvLog)).perform(closeSoftKeyboard())
        onView(withId(R.id.tvPass)).perform(click())
        onView(withId(R.id.tvPass)).perform(typeText("123"))
        onView(withId(R.id.tvPass)).perform(closeSoftKeyboard())
        onView(withId(R.id.loginBut)).perform(click())
        Thread.sleep(2000)
    }

}