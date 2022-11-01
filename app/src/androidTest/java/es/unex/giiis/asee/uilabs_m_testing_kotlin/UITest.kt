package es.unex.giiis.asee.uilabs_m_testing_kotlin


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class UITest {
    @get:Rule
    var mActivityRule: ActivityTestRule<ToDoManagerActivity> = ActivityTestRule(
        ToDoManagerActivity::class.java
    )

    @Test
    fun shouldAddToDoItemToRecyclerView() {
        val testingString = "Test Text"
        // Perform a click() action on R.id.fab
        onView(withId(R.id.fab)).perform(click())
        // Perform typeText() and closeSoftKeyboard() actions on R.id.title
        onView(withId(R.id.title)).perform(ViewActions.typeText(testingString), closeSoftKeyboard())
        // Perform a click() action on R.id.submitButton
        onView(withId(R.id.submitButton)).perform(click())
        // Check that R.id.my_recycler_view hasDescendant withId R.id.titleView
        onView(withId(R.id.my_recycler_view)).check(matches(hasDescendant(withId(R.id.titleView))))
        // Check that R.id.my_recycler_view hasDescendant with the input text
        onView(withId(R.id.my_recycler_view)).check(matches(hasDescendant(withText(testingString))))
        // Check that R.id.my_recycler_view hasDescendant withId R.id.statusCheckBox
        onView(withId(R.id.my_recycler_view)).check(matches(hasDescendant(withId(R.id.statusCheckBox))))
    }

    @After
    fun deleteElements() {
        // Open Contextual Action Mode Overflow Menu
        openContextualActionModeOverflowMenu()
        // Perform a click() action on the view withText "Delete all" (Should be a R.string.* reference)
        onView(withText(R.string.delete_all_menu_button)).perform(click())
    }
}
