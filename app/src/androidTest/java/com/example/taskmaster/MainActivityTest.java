package com.example.taskmaster;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.button), withText("ADD TASKS"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.button2), withText("ALL TASKS"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView10), withText("Your’s tasks"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Your’s tasks")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.Task1), withText("TASK 1"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.Task2), withText("TASK 2"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));

        ViewInteraction button5 = onView(
                allOf(withId(R.id.Task3), withText("TASK 3"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button5.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView), withText("my tasks"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(withText("my tasks")));

        ViewInteraction button6 = onView(
                allOf(withId(R.id.setting), withText("SETTING"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button6.check(matches(isDisplayed()));

        ViewInteraction button7 = onView(
                allOf(withId(R.id.setting), withText("SETTING"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button7.check(matches(isDisplayed()));
    }
}
