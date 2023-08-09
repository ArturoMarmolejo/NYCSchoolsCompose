package com.arturomarmolejo.nycschoolscompose.presentation.viewmodel

import androidx.compose.runtime.collectAsState
import com.arturomarmolejo.nycschoolscompose.domain.GetAllSchoolsUseCase
import com.arturomarmolejo.nycschoolscompose.domain.GetSatScoresUseCase
import com.arturomarmolejo.nycschoolscompose.utils.UIState
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.Exception


class SchoolsViewModelTest {

    private lateinit var testObject: SchoolsViewModel
    private val mockSchoolsUseCase = mockk<GetAllSchoolsUseCase>(relaxed = true)
    private val mockSatScoresUseCase = mockk<GetSatScoresUseCase>(relaxed = true)
    private val mockDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(mockDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(mockDispatcher)
        testObject = SchoolsViewModel(mockSchoolsUseCase, mockSatScoresUseCase)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `getAllSchools GET ALL SCHOOLS when usecase returns SUCCESS state`() {
        every { mockSchoolsUseCase.invoke() } returns flowOf(
            UIState.SUCCESS(mutableListOf(mockk(), mockk(), mockk()))
        )


        val job = testScope.launch {
            testObject.schoolList.collect {
                if(it is UIState.SUCCESS) {
                    assert(it is UIState.SUCCESS)
                    assertEquals(3, it.response.size)
                }
            }

        }

        testObject.getAllSchools()
        job.cancel()

    }

    @Test
    fun `getAllSchools get FAILURE RESPONSE when usecase returns ERROR state`() {
        every { mockSchoolsUseCase.invoke() } returns flowOf(
            UIState.ERROR(Exception("Error"))
        )


        val job = testScope.launch {
            testObject.schoolList.collect {
                if(it is UIState.ERROR) {
                    assert(it is UIState.ERROR)
                    assertEquals("Error", it.error)
                }
            }

        }

        testObject.getAllSchools()
        job.cancel()
    }

    @Test
    fun `getSatScores get SCORES when usecase returns SUCCESS state`() {
        every { mockSatScoresUseCase.invoke("02M294") } returns flowOf(
            UIState.SUCCESS(mutableListOf(mockk(), mockk(), mockk()))
        )


        val job = testScope.launch {
            testObject.satScoreList.collect {
                if(it is UIState.SUCCESS) {
                    assert(it is UIState.SUCCESS)
                    assertEquals(3, it.response.size)
                }
            }

        }

        testObject.getSatScores("02M2094")
        job.cancel()

    }

    @Test
    fun `getSatScores get FAILURE RESPONSE when usecase returns ERROR state`() {
        every { mockSatScoresUseCase.invoke("1") } returns flowOf(
            UIState.ERROR(Exception("Error"))
        )


        val job = testScope.launch {
            testObject.satScoreList.collect {
                if(it is UIState.ERROR) {
                    assert(it is UIState.ERROR)
                    assertEquals("Error", it.error)
                }
            }

        }

        testObject.getAllSchools()
        job.cancel()
    }




}