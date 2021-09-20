package com.zattoo.movies;

import com.zattoo.movies.pageObject.MoviesScreen;

import org.junit.Test;

public class NetworkStatusTest extends BaseTest {

    private MoviesScreen moviesScreen = new MoviesScreen();
    private String connected = "Connected";
    private String notConnected = "No internet connection";


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
