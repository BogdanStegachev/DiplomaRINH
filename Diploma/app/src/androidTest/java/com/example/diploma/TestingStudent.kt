import android.view.Gravity
import androidx.test.espresso.Espresso.*
import org.junit.runner.RunWith
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.example.diploma.R
import com.example.diploma.core.activity.MainActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import java.lang.Object

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class TestTestingStudent
{
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity:: class.java)
    @Test
    fun TestTesting ()
    {
        onView(withText("Тест_1")).perform(click())
        onView(withText("Пройти")).perform(click())
        onView(withText("2")).perform(click())
        onView(withText("5")).perform(click())
    }
}