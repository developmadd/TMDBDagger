package com.madd.madd.tmdb.TVShowCatalog;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madd.madd.tmdb.R;
import com.madd.madd.tmdb.TVShowCatalog.TVShowCatalogContract;
import com.madd.madd.tmdb.TVShowCatalog.TVShowCatalogFragment;
import com.madd.madd.tmdb.Utilities.TabAdapter;
import com.madd.madd.tmdb.Utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowCatalogContainer extends Fragment {

    private View view;
    @BindView(R.id.TAB_TVShow_Search)    TabLayout tabLayout;
    @BindView(R.id.VP_TVShow_Search)    ViewPager viewPager;


    private TVShowCatalogFragment tvShowPopularCatalog;
    private TVShowCatalogFragment tvShowOnAirCatalog;
    private TVShowCatalogFragment tvShowTopRatedCatalog;


    private TVShowCatalogContract.View.TVShowSelected onTVShowSelected;
    public void setOnTVShowSelected(TVShowCatalogContract.View.TVShowSelected onTVShowSelected) {
        this.onTVShowSelected = onTVShowSelected;
    }

    public TVShowCatalogContainer() {

    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if( view == null ) {
            view = inflater.inflate(R.layout.fragment_tvshow_search, container, false);
            ButterKnife.bind(this, view);
            createTVShowCatalogs();
            setViewPager();

        }
        return view;
    }


    private void createTVShowCatalogs(){

        tvShowPopularCatalog = new TVShowCatalogFragment();
        tvShowPopularCatalog.setListType(TVShowCatalogFragment.POPULAR_TYPE);
        tvShowPopularCatalog.setOnTVShowSelected(onTVShowSelected);

        tvShowOnAirCatalog = new TVShowCatalogFragment();
        tvShowOnAirCatalog.setListType(TVShowCatalogFragment.ON_AIR_TYPE);
        tvShowOnAirCatalog.setOnTVShowSelected(onTVShowSelected);

        tvShowTopRatedCatalog = new TVShowCatalogFragment();
        tvShowTopRatedCatalog.setListType(TVShowCatalogFragment.TOP_RATED_TYPE);
        tvShowTopRatedCatalog.setOnTVShowSelected(onTVShowSelected);


    }











    private void setViewPager(){

        TabAdapter tabAdapter = new TabAdapter(getFragmentManager());

        tabAdapter.addFragment(tvShowPopularCatalog,"Más populares");
        tabAdapter.addFragment(tvShowTopRatedCatalog,"Mejor calificadas");
        tabAdapter.addFragment(tvShowOnAirCatalog,"En televisión");


        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(
                ContextCompat.getColor(getContext(), R.color.colorGray),
                ContextCompat.getColor(getContext(), R.color.colorWhite));

        Utilities.hideKeyboardFromTab(tabLayout);

    }




}
