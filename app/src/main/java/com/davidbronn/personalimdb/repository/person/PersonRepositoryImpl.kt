package com.davidbronn.personalimdb.repository.person

import com.davidbronn.personalimdb.models.network.Person
import com.davidbronn.personalimdb.models.network.StatusModel
import com.davidbronn.personalimdb.utils.helpers.jsonify
import com.davidbronn.personalimdb.utils.misc.DispatcherProvider
import com.davidbronn.personalimdb.utils.misc.Resource
import com.davidbronn.personalimdb.utils.misc.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Jude on 11/September/2020
 */
class PersonRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val api: PersonApi
) : PersonRepository {

    override fun fetchPersonDetails(personId: Int): Flow<Resource<Person>> {
        return flow {
            val response = api.fetchPersonDetails(personId)
            if (response.isSuccessful) {
                emit(Resource(Status.SUCCESS, response.body(), null))
            } else {
                val statusModel = response.errorBody()?.string()?.jsonify<StatusModel>()
                emit(Resource(Status.ERROR, null, (statusModel ?: return@flow).statusMessage))
            }
        }.flowOn(dispatchers.io())
    }
}