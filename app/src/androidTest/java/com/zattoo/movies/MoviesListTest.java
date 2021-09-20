package com.zattoo.movies;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.assertion.ViewAssertions.selectedDescendantsMatch;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.zattoo.movies.tool.Util.withViewAtPosition;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.zattoo.movies.tool.Util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MoviesListTest extends BaseTest {

    @Test
    public void checkMoviesList() throws InterruptedException {
        Thread.sleep(3000);
        onView(withId(R.id.recyclerView)).check(new Util.RecyclerViewItemCountAssertion(1));
    }
}
