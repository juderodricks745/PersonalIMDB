package com.davidbronn.personalimdb.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidbronn.personalimdb.repository.splash.SplashRepository
import com.davidbronn.personalimdb.utils.Event
import com.davidbronn.personalimdb.utils.Result
import kotlinx.coroutines.*

class SplashViewModel(private val splashRepository: SplashRepository) : ViewModel() {

    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main) + job

    private val _event = MutableLiveData<Event<SplashEvent>>()
    val event: LiveData<Event<SplashEvent>> = _event

    init {
        fetchGenres()
    }

    private fun fetchGenres() {
        viewModelScope.launch {
            when(val genres = splashRepository.fetchGenresAsync()) {
                is Result.Success -> {
                    _event.value = Event(SplashEvent.Proceed)
                }
                is Result.Error -> {
                    _event.value = Event(SplashEvent.ShowToast(genres.exception.message ?: ""))
                }
            }
        }
    }

    sealed class SplashEvent {
        object Proceed : SplashEvent()
        class ShowToast(val message: String) : SplashEvent()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}