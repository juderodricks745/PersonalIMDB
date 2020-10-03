package com.davidbronn.personalimdb.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidbronn.personalimdb.repository.person.PersonApi
import com.davidbronn.personalimdb.utils.TestUtil
import com.davidbronn.personalimdb.utils.api
import com.davidbronn.personalimdb.utils.enqueueResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Jude on 24/September/2020
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PersonApiTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: PersonApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = mockWebServer.api()
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetch person by id`() {
        runBlocking {
            mockWebServer.enqueueResponse("person-movies.json")
            val response = service.fetchPersonDetails(TestUtil.PERSON_ID).body()
            Assert.assertNotNull(response)
            Assert.assertEquals(response?.name, "Gerard Butler")
        }
    }
}