package com.arturomarmolejo.nycschoolscompose.domain

import com.arturomarmolejo.nycschoolscompose.data.rest.NYCSchoolsApi
import com.arturomarmolejo.nycschoolscompose.utils.UIState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


class GetAllSchoolsUseCaseTest {

    private val mockNYCSchoolsApi = mockk<NYCSchoolsApi>()
    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockApi = mockk<NYCSchoolsApi>(relaxed = true)

    private lateinit var testSubject: GetAllSchoolsUseCase
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testSubject = GetAllSchoolsUseCase(mockNYCSchoolsApi, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `getSchoolList Get LIST OF SCHOOLS when server returns a SUCCESS State`() {
        //AAA
        //assignement

        coEvery { mockApi.getAllSchools() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mutableListOf(mockk(), mockk(), mockk())
        }

        //action
        val job = testScope.launch {
            testSubject.invoke().collect {
                if(it is UIState.SUCCESS) {
                    assert(it is UIState.SUCCESS)
                    assertEquals(3, it.response.size)
                }
            }
        }
        job.cancel()
    }

    @Test
    fun `getSchoolList Get NULL when server returns a ERROR State`() {
        coEvery { mockApi.getAllSchools() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }

        val job = testScope.launch {
            testSubject.invoke().collect {
                if(it is UIState.ERROR) {
                    assert(it is UIState.ERROR)
                    assertEquals("information not available", it.error)
                }
            }
        }

        job.cancel()
    }

    @Test
    fun `getSchoolList Get FAILURE RESPONSE when server returns a ERROR State`() {
        coEvery { mockApi.getAllSchools() } returns mockk {
            every { isSuccessful } returns false
            every { errorBody() } returns mockk {
                every { string() } returns "ERROR"
            }
        }

        val job = testScope.launch {
            testSubject.invoke().collect {
                if(it is UIState.ERROR) {
                    assert(it is UIState.ERROR)
                    assertEquals("information not available", it.error)
                }
            }
        }

        job.cancel()
    }

    @Test
    fun `getSchoolList Get EXCEPTION when server returns a ERROR State`() {
        coEvery { mockApi.getAllSchools() } returns mockk {
            every { isSuccessful } throws Exception("ERROR: EXCEPTION")
            every { errorBody() } returns mockk {
                every { string() } returns "ERROR"
            }
        }

        val job = testScope.launch {
            testSubject.invoke().collect {
                if(it is UIState.ERROR) {
                    assert(it is UIState.ERROR)
                    assertEquals("information not available", it.error)
                }
            }
        }

        job.cancel()
    }

}