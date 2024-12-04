package com.example.seg_project_d11;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UnitTest {

    @Rule
    public ActivityScenarioRule<Attendee_1> activityRule = new ActivityScenarioRule<>(Attendee_1.class);

    @Test
    public void firstNameWithNumbers() {
        Espresso.onView(withId(R.id.attendeeName)).perform(typeText("John123"));
        Espresso.onView(withId(R.id.attendeeLastName)).perform(typeText("Adam"));
        Espresso.onView(withId(R.id.attendeePhone)).perform(typeText("1112223333"));
        Espresso.onView(withId(R.id.attendeeAddress)).perform(typeText("12 Ottawa Rd"));

        Espresso.onView(withId(R.id.nextButton)).perform(click());
        // Check that the TextView with "Length is too long" is displayed
        Espresso.onView(withId(R.id.nextButton))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void LastNameWithNumbers() {
        Espresso.onView(withId(R.id.attendeeName)).perform(typeText("John"));
        Espresso.onView(withId(R.id.attendeeLastName)).perform(typeText("Adam123"));
        Espresso.onView(withId(R.id.attendeePhone)).perform(typeText("1112223333"));
        Espresso.onView(withId(R.id.attendeeAddress)).perform(typeText("12 Ottawa Rd"));
        Espresso.onView(withId(R.id.nextButton)).perform(click());
        // Check that the TextView with "Length is too long" is displayed
        Espresso.onView(withId(R.id.nextButton))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void phoneNumberWithoutNumbers() {
        Espresso.onView(withId(R.id.attendeePhone)).perform(typeText("hello"));

        Espresso.onView(withId(R.id.attendeeName)).perform(typeText("John"));
        Espresso.onView(withId(R.id.attendeeLastName)).perform(typeText("Adam123"));
        Espresso.onView(withId(R.id.attendeeAddress)).perform(typeText("12 Ottawa Rd"));
        Espresso.onView(withId(R.id.nextButton)).perform(click());
        // Check that the TextView with "Length is too long" is displayed
        Espresso.onView(withId(R.id.nextButton))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void firstNameWithoutNumbers() {
        Espresso.onView(withId(R.id.attendeeName)).perform(typeText("John"));

        //Following are correct, isolates attendeeName to make sure it functions
        Espresso.onView(withId(R.id.attendeeLastName)).perform(typeText("Adam"));
        Espresso.onView(withId(R.id.attendeePhone)).perform(typeText("1112223333"));
        Espresso.onView(withId(R.id.attendeeAddress)).perform(typeText("12 Ottawa Rd"));

        Espresso.onView(withId(R.id.nextButton)).perform(click());
        // Check that the TextView with "Accepted" is displayed
        Espresso.onView(withId(R.id.backButton_A2))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void lastNameWithNumbers() {
        Espresso.onView(withId(R.id.attendeeLastName)).perform(typeText("Adam"));

        //Following are correct, isolates attendeeLastName to make sure it functions
        Espresso.onView(withId(R.id.attendeeName)).perform(typeText("John"));
        Espresso.onView(withId(R.id.attendeePhone)).perform(typeText("1112223333"));
        Espresso.onView(withId(R.id.attendeeAddress)).perform(typeText("12 Ottawa Rd"));
        Espresso.onView(withId(R.id.nextButton)).perform(click());
        // Check that the TextView with "Accepted" is displayed
        Espresso.onView(withId(R.id.backButton_A2))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void phoneNumberWithNumbers() {
        Espresso.onView(withId(R.id.attendeePhone)).perform(typeText("1234567891"));

        Espresso.onView(withId(R.id.attendeeName)).perform(typeText("John"));
        Espresso.onView(withId(R.id.attendeeLastName)).perform(typeText("Adam123"));
        Espresso.onView(withId(R.id.attendeeAddress)).perform(typeText("12 Ottawa Rd"));
        Espresso.onView(withId(R.id.nextButton)).perform(click());
        // Check that the TextView with "Length is too long" is displayed
        Espresso.onView(withId(R.id.nextButton))
                .check(ViewAssertions.matches(isDisplayed()));
    }
}