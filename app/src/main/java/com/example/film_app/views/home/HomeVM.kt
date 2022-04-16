package com.example.film_app.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film_app.model.Movie
import com.example.film_app.services.MovieRestClient
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeVM : ViewModel() {
    private var _movieData: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    val movieData: LiveData<List<Movie>>
        get() = _movieData

    private var _errEvent: MutableLiveData<String> = MutableLiveData<String>()
    val errEvent: LiveData<String>
        get() = _errEvent

    fun getNowPlaying() {
        viewModelScope.launch {
            try {
                val movieResp = MovieRestClient.getInstance().api.listNowPlayMovies(
                    language = "vi-VN",
                    page = 1,
                )
                _movieData.postValue(movieResp.results!!)
            } catch (e: Exception) {
                _errEvent.value = e.message
            }
        }
    }

    fun getComingUpMovie() {
        viewModelScope.launch {
            val movieResp = MovieRestClient.getInstance().api.listUpComingMovies(
                language = "en-US",
                page = 2,
            )
            _movieData.postValue(movieResp.results!!)
        }
    }
}