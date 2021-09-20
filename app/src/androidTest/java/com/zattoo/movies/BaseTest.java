package com.zattoo.movies;

import androidx.test.espresso.IdlingResource;
import androidx.test.rule.ActivityTestRule;

import com.zattoo.movies.presentation.MainActivity;

import org.junit.Rule;

public class BaseTest{

    final int timeout = 10000;
    private IdlingResource resources;

    public IdlingResource getResources() {
        return resources;
    }

    public void setResources(IdlingResource resources) {
        this.resources = resources;
    }

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);
}
