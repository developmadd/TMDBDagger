package com.madd.madd.tmdb.ui.TVShowCatalog;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madd.madd.tmdb.di.App;
import com.madd.madd.tmdb.data.entities.TVShow.Model.TVShowList;
import com.madd.madd.tmdb.R;
import com.madd.madd.tmdb.ui.TVShowDetail.TVShowDetailActivity;
import com.madd.madd.tmdb.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowCatalogFragment extends Fragment implements TVShowCatalogContract.View {


    public static final int POPULAR_TYPE = 0;
    public static final int ON_AIR_TYPE = 1;
    public static final int TOP_RATED_TYPE = 2;


    @Inject
    TVShowCatalogContract.Presenter presenter;

    @BindView(R.id.CTNR_TVShow_Catalog)  RecyclerView recyclerView;
    @BindView(R.id.SV_TVShow_Catalog)  SearchView searchView;
    @BindView(R.id.TV_TVShow_Catalog_Empty)  TextView textViewError;
    @BindView(R.id.SRL_TVShow_Catalog) SwipeRefreshLayout swipeRefreshLayout;


    private TVShowAdapter tvShowAdapter;
    boolean searchBarAnimationStatus = false;
    int listType;


    public void setListType(int listType) {
        this.listType = listType;
    }




    public TVShowCatalogFragment() {

    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tvshow_catalog, container, false);
        ButterKnife.bind(this,view);
        ((App) Objects.requireNonNull(getActivity()).getApplication()).getComponent().inject(this);
        loadView();


        presenter.setView(this);
        presenter.requestTVShowList();
        return view;

    }







    private void loadView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        tvShowAdapter = new TVShowAdapter(new TVShowAdapter.TVShowEvents() {
            @Override
            public void onTVShowClick(TVShowList.TVShow selectedTVShow) {
                presenter.selectTVShow(selectedTVShow);
            }

            @Override
            public void onRequestNextPage() {
                presenter.requestTVShowList();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(tvShowAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                presenter.filterTVShowList(query);
                if( query.isEmpty() ){
                    Utilities.hideKeyboardFrom(searchView);
                }
                return false;
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // No realizar animaciones mientras una se lleva a cabo
                if ( !searchBarAnimationStatus ) {

                    int firstVisibleItem = ((LinearLayoutManager)
                            Objects.requireNonNull(recyclerView.getLayoutManager()))
                            .findFirstVisibleItemPosition();

                    if (dy > 0 && firstVisibleItem != 0) {
                        Utilities.animateTranslationY(searchView, -40, status -> {
                            searchBarAnimationStatus = status;
                        });
                        Utilities.animateTranslationY(recyclerView,0,null);
                    } else if (dy < 0) {
                        Utilities.animateTranslationY(searchView, 0, status -> {
                            searchBarAnimationStatus = status;
                        });
                        Utilities.animateTranslationY(recyclerView,40,null);
                    }

                }

            }
        });

        swipeRefreshLayout.setOnRefreshListener(() ->
                presenter.refreshMovieList()
        );

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
    }




    @Override
    public void showTVShowList(List<TVShowList.TVShow> tvShowList) {
        int fromIndex = tvShowAdapter.getList().size();
        int toIndex = tvShowAdapter.getList().size() + tvShowList.size();
        tvShowAdapter.getList().addAll(tvShowList);
        tvShowAdapter.notifyItemRangeInserted(fromIndex, toIndex);
    }

    @Override
    public void clearTVShowList() {
        tvShowAdapter.getList().clear();
        tvShowAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyListError() {
        textViewError.setText("Sin resultados");
        textViewError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInternetError() {
        textViewError.setText("Sin conexión a Internet");
        textViewError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        textViewError.setVisibility(View.GONE);
        textViewError.setText("");
    }

    @Override
    public void hideRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void openTVShowDetail(TVShowList.TVShow tvShow) {
        Intent intent = new Intent();
        intent.setClass(requireContext(), TVShowDetailActivity.class);
        intent.putExtra("tvShowId",tvShow.getId());
        startActivity(intent);
    }



    @Override
    public int getListType() {
        return listType;
    }




}
