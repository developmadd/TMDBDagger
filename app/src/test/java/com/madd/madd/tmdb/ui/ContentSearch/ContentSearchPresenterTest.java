package com.madd.madd.tmdb.ui.ContentSearch;


import com.madd.madd.tmdb.data.entities.ContentList.ContentListDataSource;
import com.madd.madd.tmdb.data.entities.ContentList.Model.ContentList;
import com.madd.madd.tmdb.data.entities.DataSource;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContentSearchPresenterTest {

    private ContentSearchPresenter presenter;

    private ContentListDataSource.Repository mockedContentListRepository;
    private ContentSearchContract.View mockedView;

    @Before
    public void setUp(){

        mockedContentListRepository = mock(ContentListDataSource.Repository.class);
        mockedView = mock(ContentSearchContract.View.class);

        presenter = new ContentSearchPresenter(mockedContentListRepository);
        presenter.setView(mockedView);

    }

    /*

        Tests:
        1: Request list without internet
        2: Request list with empty result
        3: Request list

     */







    @Test
    public void requestListWithoutInternet(){

        String query = "pir";
        when(mockedView.getMoviePage()).thenReturn(1);
        doAnswer(invocation -> {
            ((DataSource.GetEntity)invocation.getArguments()[2]).onError("sin internet");
            return null;
        }).when(mockedContentListRepository).getMovieListByQuery(eq(query) ,eq(1),
                any(DataSource.GetEntity.class));

        presenter.requestContentList(query);
        verify(mockedView).showInternetError();
    }


    @Test
    public void requestListWithNoResults(){

        String query = "pir";
        when(mockedView.getMoviePage()).thenReturn(1);
        when(mockedView.getTVShowPage()).thenReturn(1);

        List<ContentList.Content> contents = new ArrayList<>();
        ContentList emptyContentList = new ContentList(1,contents);

        doAnswer(invocation -> {
            ((DataSource.GetEntity)invocation.getArguments()[2]).onSuccess(emptyContentList);
            return null;
        }).when(mockedContentListRepository).getMovieListByQuery(eq(query) ,eq(1),
                any(DataSource.GetEntity.class));

        doAnswer(invocation -> {
            ((DataSource.GetEntity)invocation.getArguments()[2]).onSuccess(emptyContentList);
            return null;
        }).when(mockedContentListRepository).getTVShowListByQuery(eq(query) ,eq(1),
                any(DataSource.GetEntity.class));

        presenter.requestContentList(query);
        verify(mockedView).showEmptyListError();
    }

    @Test
    public void requestList(){

        String query = "pir";
        when(mockedView.getMoviePage()).thenReturn(1);
        when(mockedView.getTVShowPage()).thenReturn(1);

        List<ContentList.Content> movieList = new ArrayList<>();
        movieList.add(new ContentList.Content("","","piratas del caribe",""));
        movieList.add(new ContentList.Content("","","piratas del sur",""));
        ContentList mContentList = new ContentList(1,movieList);

        List<ContentList.Content> tvShowList = new ArrayList<>();
        tvShowList.add(new ContentList.Content("","piratas LA SERIE", "",""));
        tvShowList.add(new ContentList.Content("","pera pirata ", "",""));
        ContentList tvContentList = new ContentList(1,tvShowList);



        List<ContentList.Content> mixedList = new ArrayList<>();
        mixedList.add(new ContentList.Content("","","piratas del caribe",""));
        mixedList.add(new ContentList.Content("","","piratas del sur",""));
        mixedList.add(new ContentList.Content("","piratas LA SERIE", "",""));
        mixedList.add(new ContentList.Content("","pera pirata ", "",""));



        doAnswer(invocation -> {

            ((DataSource.GetEntity)invocation.getArguments()[2]).onSuccess(mContentList);
            return null;
        }).when(mockedContentListRepository).getMovieListByQuery(eq(query) ,eq(1),
                any(DataSource.GetEntity.class));

        doAnswer(invocation -> {
            ((DataSource.GetEntity)invocation.getArguments()[2]).onSuccess(tvContentList);
            return null;
        }).when(mockedContentListRepository).getTVShowListByQuery(eq(query) ,eq(1),
                any(DataSource.GetEntity.class));

        presenter.requestContentList(query);
        verify(mockedView).showContentList(refEq(mixedList),eq(2),eq(2));
    }
}