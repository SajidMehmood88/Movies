package com.zattoo.movies;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.contrib.RecyclerViewActions;

import org.junit.Test;

public class MoviesListTest {



    @Test
    public void checkMoviesList() {
//        onData(anything()).inAdapterView(withId(R.id.recyclerView)).atPosition(0)
//                .onChildView(withId(R.id.movies_title)).check(matches(withText(startsWith("Entwinted"))));

//        onView(withId(R.id.recyclerView)).check(doesNotExist());
//        onView(allOf(withId(R.id.toolbar), withChild(withText("")))).check(matches(isDisplayed()));
//        onView(ViewMatchers.withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

    }
}
