package com.zattoo.movies;

import com.zattoo.movies.pageObject.MoviesScreen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoadingDataTest extends BaseTest{

    private MoviesScreen moviesScreen = new MoviesScreen();

    @Before
    public void registerIdlingResource(){
        registerIdlingResources();
    }

    @After
    public void unregisterIdlingResource(){
        unRegisterIdlingResources();
    }

    @Test
    public void loadingData() throws Exception {
        Thread.sleep(3000);
        moviesScreen.swipeDownToRefresh();
        moviesScreen.loadingStatus();
    }
}
