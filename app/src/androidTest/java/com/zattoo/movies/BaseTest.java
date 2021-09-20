package com.zattoo.movies;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.rule.ActivityTestRule;

import com.zattoo.movies.presentation.MainActivity;

import org.junit.Rule;

public class BaseTest{

    final int timeout = 10000;
    private IdlingResource myIdling;

    public IdlingResource getMyIdling() {
        return myIdling;
    }

    public void setMyIdling(IdlingResource myIdling) {
        this.myIdling = myIdling;
    }


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    public void registerIdlingResources() {
        setMyIdling(activityTestRule.getActivity().getIdlingResource());
        IdlingRegistry.getInstance().register(getMyIdling());
    }

    public void unRegisterIdlingResources(){
        if(myIdling !=null){
            IdlingRegistry.getInstance().unregister(myIdling);
        }
    }
}
