package com.madd.madd.tmdb.data.entities.Movie;

import com.madd.madd.tmdb.data.http.TMDBApi;
import com.madd.madd.tmdb.data.entities.Movie.Source.Local.MovieCacheDataSource;
import com.madd.madd.tmdb.data.entities.Movie.Source.Remote.MovieRemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieDataModule {

    @Provides
    public MovieDataSource.Repository provideMovieRepository(
            MovieDataSource.Remote movieRemoteDataSource,
            MovieDataSource.Cache movieCacheDataSource){
        return new MovieRepository(movieRemoteDataSource,movieCacheDataSource);
    }

    @Provides
    public MovieDataSource.Remote provideMovieRemoteDataSource(
            TMDBApi api){
        return new MovieRemoteDataSource(api);
    }

    @Singleton
    @Provides
    public MovieDataSource.Cache provideMovieCacheDataSource(){
        return new MovieCacheDataSource();
    }
}
