package com.zattoo.movies;

import com.zattoo.movies.pageObject.MoviesScreen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NetworkStatusTest extends BaseTest {

    private MoviesScreen moviesScreen = new MoviesScreen();

    private String connected = "Connected";
    private String notConnected = "No internet connection";

    @Before
    public void registerIdlingResource(){
        registerIdlingResources();
    }

    @After
    public void unregisterIdlingResource(){
        unRegisterIdlingResources();
    }

    @Test
    public void networkStatusWithNoConnection() {
        moviesScreen.isTextViewNetworkStatus(notConnected, timeout);
        moviesScreen.networkStatusLayoutColor(R.color.red);
    }

    @Test
    public void networkStatusWithConnection() {
        moviesScreen.isTextViewNetworkStatus(connected, timeout);
        moviesScreen.networkStatusLayoutColor(R.color.green);
    }
}
