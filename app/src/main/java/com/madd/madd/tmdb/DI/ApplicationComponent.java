package com.madd.madd.tmdb.DI;

import com.madd.madd.tmdb.UI.Activities.MainActivity;
import com.madd.madd.tmdb.UI.Fragments.MovieDetail.MovieDetailActivity;
import com.madd.madd.tmdb.UI.Fragments.ContentSearch.ContentSearchFragment;
import com.madd.madd.tmdb.UI.Fragments.ContentSearch.ContentSearchModule;
import com.madd.madd.tmdb.HTTP.TMDBModule;
import com.madd.madd.tmdb.UI.Fragments.MovieCatalog.MovieCatalogFragment;
import com.madd.madd.tmdb.UI.Fragments.MovieCatalog.MovieCatalogModule;
import com.madd.madd.tmdb.UI.Fragments.MovieDetail.MovieDetailModule;
import com.madd.madd.tmdb.UI.Fragments.TVShowCatalog.TVShowCatalogFragment;
import com.madd.madd.tmdb.UI.Fragments.TVShowCatalog.TVShowCatalogModule;
import com.madd.madd.tmdb.UI.Fragments.TVShowDetail.TVShowDetailFragment;
import com.madd.madd.tmdb.UI.Fragments.TVShowDetail.TVShowDetailModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {  ApplicationModule.class,
                        MovieCatalogModule.class,
                        TVShowCatalogModule.class,
                        MovieDetailModule.class,
                        TVShowDetailModule.class,
                        ContentSearchModule.class,
                        TMDBModule.class })
public interface ApplicationComponent {

    void inject(MainActivity target);
    void inject(MovieDetailActivity target);
    void inject(MovieCatalogFragment target);
    void inject(TVShowCatalogFragment target);
    void inject(TVShowDetailFragment target);
    void inject(ContentSearchFragment target);
}
