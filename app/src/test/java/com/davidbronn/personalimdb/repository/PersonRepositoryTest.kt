package com.davidbronn.personalimdb.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidbronn.personalimdb.models.network.Person
import com.davidbronn.personalimdb.repository.person.PersonRepository
import com.davidbronn.personalimdb.utils.CoroutineTestRule
import com.davidbronn.personalimdb.utils.TestUtil
import com.davidbronn.personalimdb.utils.misc.Resource
import com.davidbronn.personalimdb.utils.misc.Status
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Jude on 02/October/2020
 */
@ExperimentalCoroutinesApi
class PersonRepositoryTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var testRule = CoroutineTestRule()

    @MockK
    val repository = mockk<PersonRepository>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test Person`() {
        testRule.testDispatcher.runBlockingTest {
            val personFlow = flow<Resource<Person>> { Resource.success(TestUtil.createPerson()) }
            every { repository.fetchPersonDetails(TestUtil.PERSON_ID) } returns personFlow
            val personResponse = repository.fetchPersonDetails(TestUtil.PERSON_ID)
            personResponse.collect {
                Assert.assertEquals(it.status, Status.SUCCESS)
                Assert.assertEquals(it.data?.name, "Gerard Butler")
            }
        }
    }
}