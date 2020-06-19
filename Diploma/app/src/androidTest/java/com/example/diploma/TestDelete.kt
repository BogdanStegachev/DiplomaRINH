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

class TestDelete {
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun TestDelete()
    {
        onView(withText("Test-2")).perform(longClick())
        onView(withText("Удалить")).perform(click())
        onView(withText("Удалить")).perform(click())
    }
}