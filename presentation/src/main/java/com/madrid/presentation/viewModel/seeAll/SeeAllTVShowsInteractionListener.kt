package com.madrid.presentation.viewModel.seeAll

interface SeeAllTVShowsInteractionListener {
    fun onSeriesClick(seriesId:Int)
    fun onGenreSelect(genre:String)
    fun onBackClick()
}