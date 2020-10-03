package com.davidbronn.personalimdb.ui.people

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbronn.personalimdb.models.network.Person
import com.davidbronn.personalimdb.repository.person.PersonRepository
import com.davidbronn.personalimdb.utils.misc.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PeopleViewModel @ViewModelInject constructor(private val repository: PersonRepository) :
    ViewModel() {

    val person = MutableLiveData<Person>()

    fun fetchPerson(personId: Int) {
        viewModelScope.launch {
            repository.fetchPersonDetails(personId).collect { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { person ->
                            this@PeopleViewModel.person.value = person
                        }
                    }
                }
            }
        }
    }
}