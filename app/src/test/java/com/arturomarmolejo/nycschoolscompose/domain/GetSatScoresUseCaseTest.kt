package com.arturomarmolejo.nycschoolscompose.domain

import com.arturomarmolejo.nycschoolscompose.data.rest.NYCSchoolsApi
import com.arturomarmolejo.nycschoolscompose.utils.UIState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Test


class GetSatScoresUseCaseTest {

    private val mockNYCSchoolsApi = mockk<NYCSchoolsApi>()
    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockApi = mockk<NYCSchoolsApi>(relaxed = true)

    private lateinit var testSubject: GetSatScoresUseCase
    private val testScope = TestScope(testDispatcher)

    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testSubject = GetSatScoresUseCase(mockNYCSchoolsApi, testDispatcher)
    }


    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `getSatScoresByDbn get SCORES OBJECT when server returns a SUCCESS State`() {
        //AAA
        //ASSIGNMENT
        coEvery { mockApi.getSatScoresByDbn("02M294") } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns mutableListOf(mockk(), mockk(), mockk())
        }

        //ACTION
        val job = testScope.launch {
            testSubject.invoke("02M294").collect {it ->
                if(it is UIState.SUCCESS) {
                    assert(it is UIState.SUCCESS)
                    Assert.assertEquals(3, it.response.size)
                }
            }
        }

        job.cancel()
    }

    @Test
    fun `getSatScoresByDbn get NULL when server returns a ERROR State`() {
        //AAA
        //ASSIGNMENT
        coEvery { mockApi.getSatScoresByDbn("1") } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns null
        }

        //ACTION
        val job = testScope.launch {
            testSubject.invoke("1").collect {it ->
                if(it is UIState.SUCCESS) {
                    assert(it is UIState.ERROR)
                    Assert.assertEquals("Information not available", it.response)
                }
            }
        }

        job.cancel()
    }

    @Test
    fun `getSatScoresByDbn get FAILURE RESPONSE when server returns a ERROR State`() {
        //AAA
        //ASSIGNMENT
        coEvery { mockApi.getSatScoresByDbn("a") } returns mockk{
            every { isSuccessful } returns false
            every { errorBody() } returns mockk {
                every { string() } returns "ERROR"
            }
        }

        //ACTION
        val job = testScope.launch {
            testSubject.invoke("1").collect {it ->
                if(it is UIState.SUCCESS) {
                    assert(it is UIState.ERROR)
                    Assert.assertEquals("Information not available", it.response)
                }
            }
        }

        job.cancel()
    }

    @Test
    fun `getSatScoresByDbn get EXCEPTION when server returns a ERROR State`() {
        //AAA
        //ASSIGNMENT
        coEvery { mockApi.getSatScoresByDbn("-") } returns mockk{
            every { isSuccessful } throws Exception("ERROR: EXCEPTION")
            every { errorBody() } returns mockk {
                every { string() } returns "ERROR"
            }
        }

        //ACTION
        val job = testScope.launch {
            testSubject.invoke("1").collect {it ->
                if(it is UIState.SUCCESS) {
                    assert(it is UIState.ERROR)
                    Assert.assertEquals("Information not available", it.response)
                }
            }
        }

        job.cancel()
    }
}