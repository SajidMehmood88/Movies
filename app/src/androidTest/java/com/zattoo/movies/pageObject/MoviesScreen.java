package com.zattoo.movies.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.endsWith;

import com.zattoo.movies.R;
import com.zattoo.movies.tool.Util;

import org.hamcrest.Matchers;

public class MoviesScreen extends Util {

    private final int toolbar = R.id.toolbar;
    private final int refreshPage = R.id.swipeRefreshLayout;
    private final int textViewNetworkStatus = R.id.textViewNetworkStatus;
    private final int networkStatusLayout = R.id.networkStatusLayout;
    private final int recycleView = R.id.recyclerView;
    private final int movieTitle = R.id.movies_title;
    private final int movieSubtitle = R.id.movies_subtitle;
    private final int moviePrice = R.id.movies_price_availability;
    private final int movieImage = R.id.movies_imageView;


    public int getMovieTitle() {
        return movieTitle;
    }

    public int getMovieSubtitle() {
        return movieSubtitle;
    }

    public int getMoviePrice() {
        return moviePrice;
    }

    public int getMovieImage() {
        return movieImage;
    }

    public void isToolbarDisplaying(){
        onView(withId(toolbar)).check(matches(isDisplayed()));
    }

    public void toolbarTitle(String specificTitle){
        onView(allOf(withId(toolbar), withChild(withText(specificTitle)))).check(matches(isDisplayed()));
    }

    public void swipeDownToRefresh(){
        onView(withId(refreshPage)).perform(swipeDown());
    }

    public void loadingStatus(){
        onView(withClassName(endsWith("CircleImageView"))).check(matches(isDisplayed()));
    }

    public void isTextViewNetworkStatus(String statusDisconnected, int timeout){
        onView(withId(textViewNetworkStatus)).perform(waitForText(statusDisconnected, timeout));
    }

    public void networkStatusLayoutColor(int color){
        onView(withId(networkStatusLayout))
                .check(matches(new ColorMatcher(color)));
    }
    public void isRowContain(int position, int element){
        onView(withId(R.id.recyclerView))
                .check(matches(withViewAtPosition(position, hasDescendant(Matchers.allOf(withId(element), isDisplayed())))));
    }
}
