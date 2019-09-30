package com.chandu.chandu_udacity_capstone;

import android.os.SystemClock;
import android.widget.TextView;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;


import com.chandu.chandu_udacity_capstone.HikeActivity.MainActivity;
import com.chandu.chandu_udacity_capstone.HikeActivity.StatesAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class verifyTrailOptions {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity .class);

    public static void pressBack() {
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    @Test
    public void verifyDropDown() {
        onView(withId(R.id.static_spinner)).perform(click());
        onData(anything()).atPosition(1).check(matches(withText(containsString("Montgomery, Alabama"))));
        onData(anything()).atPosition(10).check(matches(withText(containsString("Atlanta, Georgia"))));
    }

    @Test
    public void verifyGoogleSearchBar() {
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.place_autocomplete_fragment)).perform(click());
        onView(withId(R.id.places_autocomplete_edit_text)).perform(typeText("vmware"));
    }
}
