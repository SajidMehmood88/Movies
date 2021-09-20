package com.zattoo.movies;

import com.zattoo.movies.pageObject.MoviesScreen;

import org.junit.Test;

public class loadingDataTest {

    private MoviesScreen moviesScreen = new MoviesScreen();

    @Test
    public void loadingData() throws Exception {
        Thread.sleep(3000);
        moviesScreen.swipeDownToRefresh();
        moviesScreen.loadingStatus();
    }
}
