package com.davidbronn.personalimdb.repository.person

import com.davidbronn.personalimdb.models.network.Person
import com.davidbronn.personalimdb.utils.misc.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Jude on 12/September/2020
 */
interface PersonRepository {

    fun fetchPersonDetails(personId: Int): Flow<Resource<Person>>
}